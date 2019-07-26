package life.majiang.community.provider;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUserDTO;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class GithubProvider {
	
	/**
	 * 获得accessToken
	 * @param accessTokenDTO
	 * @return accessToken
	 */
	public String getAccessToken(AccessTokenDTO accessTokenDTO) {
		MediaType mediaType = MediaType.get("application/json; charset=utf-8");
	    OkHttpClient client = new OkHttpClient();
	    
	    RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
	    Request request = new Request.Builder()
	    		.url("https://github.com/login/oauth/access_token")
	    		.post(body)
	    		.build();
	    try (Response response = client.newCall(request).execute()) {
	    	String string = response.body().string();
	    	String accessToken = string.split("&")[0].split("=")[1];
	    	return accessToken;
	    } catch (IOException e) {
			e.printStackTrace();
		}
		return null;			
	}
	
	/**
	 * 获得user
	 * @param accessToken
	 * @return githubUserDTO
	 */
	public GithubUserDTO getUser(String accessToken) {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
		      .url("https://api.github.com/user?access_token=" + accessToken)
		      .build();
		try (Response response = client.newCall(request).execute()) {
			String string = response.body().string();
			GithubUserDTO githubUserDTO = JSON.parseObject(string, GithubUserDTO.class);
		    return githubUserDTO;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;		
	}
}
