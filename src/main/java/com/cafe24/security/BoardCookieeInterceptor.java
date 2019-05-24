package com.cafe24.security;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.mysite.service.BoardService;
import com.cafe24.mysite.vo.UserVo;

public class BoardCookieeInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private BoardService boardService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		
		UserVo userVo = (UserVo) session.getAttribute("authUser");
		
		
		Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Long no = Long.parseLong(pathVariables.get("no"));
		
		//1. 쿠키 확인 
		String visitNo = userVo.getNo().toString() + ":";
		
		
		Cookie[] cookies = request.getCookies();
		Boolean contains = false;
		if(cookies != null && cookies.length > 0) {
			for(Cookie c : cookies) {
				if(("visitUser-"+no).equals(c.getName())) {
					String[] noList = c.getValue().split(":");
					contains = Arrays.stream(noList).anyMatch(userVo.getNo().toString()::equals);
					if(!contains) {
						visitNo  +=  c.getValue();	
					} 
				}
			}
		}
		
		// 2. 해당 쿠키 이름이 없거나, 해당 user가 조회한 기록이 없을 때 : 쿠키 수정  
		if(!contains) {
			//조회수 1 증가
			boardService.hitPlus(no);
			//쿠키에 쓰기 
			Cookie cookie = new Cookie("visitUser-"+no, visitNo );
			cookie.setMaxAge(24*60*60);
			cookie.setPath(request.getContextPath()+"/board/view/"+no);
			response.addCookie(cookie);
		}

		
		return true;
	}

}
