package com.cafe24.mysite.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


//어노테이션은 인터페이스로 만든다. 
@ControllerAdvice
public class GlobalExceptionHandler {
	
	//컨트롤로 올려진 예외는 모두 여기로 !
	// ~.class 익셉션이 발생하면 모두 여기로 라는 의미 
	@ExceptionHandler( Exception.class )
	public void handleException(
		HttpServletRequest request,
		HttpServletResponse response,
		Exception e
	) throws Exception {
		//1. 로깅
		e.printStackTrace();
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		//LOGGER.error(errors.toString());
		System.out.println(errors.toString());
		
		//2. 안내페이지 가기 + 정상종료(response)
		request.setAttribute("uri", request.getRequestURI());
		request.setAttribute("exception", errors.toString());
		request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
		
		//return "error/exception";
		
		
		
		
	}
	
//	@ExceptionHandler( UserDaoException.class )
//	public String handleUserDaoException() {
//		System.out.println("handleUSerDaoException");
//		return "error/exception";
//	}
//	
//	@ExceptionHandler( UserDaoException.class )
//	public String handleUserDaoException() {
//		System.out.println("handleUSerDaoException");
//		return "error/exception";
//	}
}
