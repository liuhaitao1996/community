package life.majiang.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	/**
	 * 主页的URI，即返回位于src/main/resources/templates/index.html静态资源
	 */
	public static final String IDEX_URI = "index"; 
	
	/**
	 * 接收前往主页的请求
	 * @return IDEX_URI
	 */
	@GetMapping("/")
	public String toIndex() {
		
		return IDEX_URI; // 
		
	}

}
