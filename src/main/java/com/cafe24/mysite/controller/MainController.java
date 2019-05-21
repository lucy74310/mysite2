package com.cafe24.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.mysite.vo.UserVo;

@Controller
public class MainController {
	
	@RequestMapping({"/","/main"})
	public String main() {
		
		
		return "main/index";
	}
	
	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "<h1>안녕하세요!</h1>";// 한글 바꿔주기 - 메세지 컨버터 
	}
	
	
	@ResponseBody
	@RequestMapping("/hello2") public UserVo hello2() { 
		UserVo vo = new UserVo();
		vo.setNo(10L); 
		vo.setName("조부광"); 
		vo.setEmail("lucy74310@gmail.com");
		return vo; // json으로 넘김
	}
	 
}
