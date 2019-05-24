package com.cafe24.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cafe24.security.Auth;
import com.cafe24.security.Auth.Role;

//Auth에서 ElementType.TYPE 해주면 오류 사라짐  
// Admin 컨트롤러에 접근하려면 권한이 필요한데, 그 권한은 ADMIN
//@Auth(role=Auth.Role.ADMIN)
// 이걸 구현 하기

@Auth(role=Role.ADMIN)
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@RequestMapping({"","main"})
	public String main() {
		System.out.println("admin/main");
		return "admin/main";
	}
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
}
