package com.cafe24.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVo;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter {
	
	// @Autowired
	// private UserService userService;
	@Override
	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler)
			throws Exception {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		//이렇게 하지 않는다..
		//UserService us = new UserService();
		// 이렇게 하면 UserDao는 null이다.
		// 현재 컨테이너 안에서 컨트롤러-서비스-다오는 autowired로 연결되어 있기 때문
		// 여기서도 autowired로 주입해준다. 
		// autowired 지우고 외부에서 userService 가져오는 방법..?
		// 만들어져 있는 컨테이너 어떻게 가져올까 ?
		ApplicationContext ac = 
				WebApplicationContextUtils.
				getWebApplicationContext(request.getServletContext());
		
		UserService userService = ac.getBean(UserService.class);
		
		UserVo userVo = new UserVo();
		userVo.setEmail(email);
		userVo.setPassword(password);
		
		
		UserVo authUser = userService.getUser(userVo);
		
		if(authUser == null) {
			
			response.sendRedirect(request.getContextPath()+ "/user/login?result=fail");
			return false;
		}
		
		//session 처리
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		response.sendRedirect(request.getContextPath() + "/main");
		
		
		
		return false;
	}

}
