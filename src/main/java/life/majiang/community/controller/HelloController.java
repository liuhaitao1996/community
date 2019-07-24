package life.majiang.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
	
	@GetMapping("/hello")
	public String hello(@RequestParam(name = "name") String name, Model model) {
		
		model.addAttribute("name", name); // 把传入进来的参数存入model
		return "hello"; // 返回位于src/main/resources/templates/hello.html静态资源
		
	}

}
