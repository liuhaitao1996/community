package life.majiang.community.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import life.majiang.community.model.User;
import life.majiang.community.service.UserService;
import life.majiang.community.utils.CookieUtils;

@Controller
public class IndexController {
	
	/**
	 * 主页的URI，即返回位于src/main/resources/templates/index.html静态资源
	 */
	public static final String IDEX_URI = "index"; 
	
	@Autowired
	private UserService userService;
	
	/**
	 * 接收前往主页的请求
	 * @return IDEX_URI
	 */
	@GetMapping("/")
	public String toIndex(HttpServletRequest request) {
		String token = CookieUtils.getCookieValue(AuthorizeController.COOKIE_TOKEN_ATTR);
		User user = userService.findUserByToken(token);
		request.getSession().setAttribute(AuthorizeController.SESSION_USER_ATTR, user);
		return IDEX_URI;
		
	}

}
