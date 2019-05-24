package com.cafe24.mysite.exception;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cafe24.mysite.dto.JSONResult;
import com.fasterxml.jackson.databind.ObjectMapper;


//어노테이션은 인터페이스로 만든다. 
@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Log LOGGER = LogFactory.getLog(GlobalExceptionHandler.class);
	
	//컨트롤러로 올려진 예외는 모두 여기로 !
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
		
		
		LOGGER.error(errors.toString());
		
		System.out.println(errors.toString());
		
		//요청이 json을 요청했는지 html을 요청했는지 확인
		String accept = request.getHeader("accept");
		if(accept.matches(".*application/json.*")) {
			//JSON 응답
			response.setStatus(HttpServletResponse.SC_OK); // 헤더로 보냄
			JSONResult jsonResult = JSONResult.fail(errors.toString());
			
			String result = new ObjectMapper().writeValueAsString(jsonResult);
			System.out.println(result);
			OutputStream os = response.getOutputStream();
			os.write(result.getBytes("UTF-8"));
			os.close();
			
		} else {
			//2. 안내페이지 가기 + 정상종료(response)
			request.setAttribute("uri", request.getRequestURI());
			request.setAttribute("exception", errors.toString());
			request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);	
		}
			
		
		//return "error/exception";
	}
	
	 private JSONResult JSONResult() {
	      return null;
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
