package com.cafe24.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.exception.UserDaoException;
import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo) {
		
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(
			@ModelAttribute @Valid UserVo userVo, 
			BindingResult result,
			Model model) {
		
		if( result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			
			for (ObjectError err : list) {
				System.out.println(err);
			}
			
			model.addAllAttributes(result.getModel());// 여러개 모아서 맵으로 세팅 
			
			return "/user/join";
			}
		
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}
	
	
	@RequestMapping(value="/joinsuccess")
	public String joinsuccess() {
		return "redirect:/user/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		
		return "user/login";
	}
	
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(
			@RequestParam(value="email", required=true, defaultValue="") String email, 
			@RequestParam(value="password", required=true, defaultValue="") String password,
			HttpSession session,
			Model model
	) {
		
		
		//UserVo authUser = userDao.get(email, password);
		UserVo authUser = userService.getUser(new UserVo(email, password));
		
		if( authUser == null ) {
			model.addAttribute("result", "fail");
			return "user/login";
		}
		
		// session 처리
		session.setAttribute("authUser", authUser);
		
		return "redirect:/main";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		
		session.removeAttribute("authUser");
		session.invalidate();
		
		return "redirect:/main";
	} 
	
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(HttpSession session) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if( authUser == null ) {
			return "redirect:/user/login";
		}
		
		return "user/update";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(
			@ModelAttribute UserVo userVo,
			HttpSession session
	) {
		
		Boolean result = userService.modifyUser(userVo);
		
		if(result == true) {
			session.removeAttribute("authUser");
			session.setAttribute("authUser", userVo);
		}
		
		return "redirect:/user/update?result=success";
	}
	
	
//	@ExceptionHandler( UserDaoException.class )
//	public String handleUserDaoException() {
//		System.out.println("handleUSerDaoException");
//		return "error/exception";
//	}
}
