package com.cafe24.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class BoardSearchInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		String kwd = request.getParameter("kwd");
		
		System.out.println("keyword:" + kwd);
		
		
		if("".equals(kwd)) {
			session.removeAttribute("kwd");
		} else {
			session.setAttribute("kwd", kwd);
		}
		
		return true;
	}
	
	
	
	
}
