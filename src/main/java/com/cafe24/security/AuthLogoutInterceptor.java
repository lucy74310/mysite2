package com.cafe24.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthLogoutInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		
		session.removeAttribute("authUser");
		session.invalidate();
		
		
		if( handler instanceof HandlerMethod == true) {
			
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			
			
			if("updatApply".equals(handlerMethod.getMethod().getName()) ) {
				response.sendRedirect(request.getContextPath() + "/update/apply");
				return true;
			}
			
			
		}
		
		
		
		response.sendRedirect(request.getContextPath() + "/main");
		return false;
	}

}
