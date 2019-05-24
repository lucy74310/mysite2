package com.cafe24.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVo;
import com.cafe24.security.Auth;
import com.cafe24.security.AuthUser;

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
	public String login(HttpServletRequest request) {
		return "user/login";
	}
	
	@Auth
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(@AuthUser UserVo authUser) {
		System.out.println(authUser);
		//ArgumentResolver로 연결해서 받아줌 
		//UserVo vo = (UserVo) session.getAttribute("authUser");
		if( authUser == null ) {
			return "redirect:/user/login";
		}
		
		return "user/update";
	}
	
	
	@RequestMapping(value="/update/apply", method=RequestMethod.POST)
	public String updateApply(
			@ModelAttribute UserVo userVo,
			RedirectAttributes redirect
	) {
		
		//Boolean result = userService.modifyUser(userVo);
		
//		if(result == true) {
//			session.removeAttribute("authUser");
//			session.setAttribute("authUser", userVo);
//		}
		
		// session을 안사용 하려면.. 수정하면 logout 시키는 방식 ? 
		
		
		userService.modifyUser(userVo);
		
		redirect.addFlashAttribute("result", "success");
		return "redirect:/user/login";
		
		//return "redirect:/user/update";
	}
	
	// interceptor가 처리해줌
	/*
	 * @RequestMapping(value="/auth", method=RequestMethod.POST) public String
	 * login(
	 * 
	 * @RequestParam(value="email", required=true, defaultValue="") String email,
	 * 
	 * @RequestParam(value="password", required=true, defaultValue="") String
	 * password, HttpSession session, Model model ) {
	 * 
	 * 
	 * //UserVo authUser = userDao.get(email, password); UserVo authUser =
	 * userService.getUser(new UserVo(email, password));
	 * 
	 * if( authUser == null ) { model.addAttribute("result", "fail"); return
	 * "user/login"; }
	 * 
	 * // session 처리 session.setAttribute("authUser", authUser);
	 * 
	 * return "redirect:/main"; }
	 */
	
	
	// interceptor가 처리해줌
	/*
	 * @RequestMapping("/logout") public String logout(HttpSession session) {
	 * 
	 * session.removeAttribute("authUser"); session.invalidate();
	 * 
	 * return "redirect:/main"; }
	 */
	
	
	
	
//	@ExceptionHandler( UserDaoException.class )
//	public String handleUserDaoException() {
//		System.out.println("handleUSerDaoException");
//		return "error/exception";
//	}
}
