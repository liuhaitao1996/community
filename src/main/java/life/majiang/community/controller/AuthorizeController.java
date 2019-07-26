package life.majiang.community.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUserDTO;
import life.majiang.community.model.User;
import life.majiang.community.provider.GithubProvider;
import life.majiang.community.service.UserService;

@Controller
public class AuthorizeController {

	/**
	 * 存入session的用户的属性名
	 */
	public static final String SESSION_USER_ATTR = "user";
	
	/**
	 * 重定向到主页的请求，重定向controller的请求
	 */
	public static final String INDEX_REQUEST = "redirect:/";
	
	@Autowired
	private GithubProvider githubProvider;
	
	@Autowired
	private UserService userService;
	
	@Value("${github.client.id}")
	private String clientId;
	
	@Value("${github.client.secret}")
	private String clientSecret;
	
	@Value("${github.redirect.uri}")
	private String redirectUri;
	
	
	/**
	 * 接收github登录的返回请求，并不断利用OKhttp模拟请求获得USER
	 * @param code
	 * @param state
	 * @param request
	 * @return INDEX_REQUEST
	 */
	@GetMapping("/callback")
	public String callback(@RequestParam(name = "code") String code, 
			               @RequestParam(name = "state") String state,
			               HttpServletRequest request) {
		
		// 1.https://github.com/login/oauth/authorize return code&state
		
		// 2.create accessToken
		AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
		accessTokenDTO.setClient_id(clientId);
		accessTokenDTO.setClient_secret(clientSecret);
		accessTokenDTO.setCode(code);
		accessTokenDTO.setRedirect_uri(redirectUri);
		accessTokenDTO.setState(state);
		
        // 3.https://github.com/login/oauth/access_token return accessToken
		String accessToken = githubProvider.getAccessToken(accessTokenDTO);
		System.out.println("获得accessToken" + accessToken);
		
		// 4.https://api.github.com/user return user
		GithubUserDTO githubUser = githubProvider.getUser(accessToken);
		System.out.println("获得user信息" + githubUser.getLogin() + " " + githubUser.getId());
		
		// 5.write session and cookie
		if(githubUser != null) {
			// success
			// 1.创建用户
			User user = new User();
			user.setToken(UUID.randomUUID().toString());
			user.setName(githubUser.getLogin());
			user.setAccountId(githubUser.getId().toString());
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModified(System.currentTimeMillis());
			// 2. 插入用户
			userService.insert(user);
			// 3. 将用户传入前台
			request.getSession().setAttribute(SESSION_USER_ATTR, user);
		}
		
		// 6.redirect:/
		return INDEX_REQUEST;
		
		
	}

}
