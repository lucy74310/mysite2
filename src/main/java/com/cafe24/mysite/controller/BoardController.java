package com.cafe24.mysite.controller;


import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.service.BoardService;
import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// 리스트
	@RequestMapping({"", "/list"})
	public String list(
			@RequestParam(value="page", required=false, defaultValue="1") int nowPage,
			Model model) 
	{
		
		Map<String,Object> map = boardService.getPagingList(nowPage);
		
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("totalPageNum", map.get("totalPageNum"));
		
		
		return "board/list";
	}
	
	// 답글쓰기 폼
	@RequestMapping(value="/reply/{groupno}/{orderno}/{depth}")
	public String reply(
			@PathVariable(value = "groupno") Long groupNo,
			@PathVariable(value = "orderno") Long orderNo,
			@PathVariable(value = "depth") Long depth,
			HttpSession session, Model model){
		UserVo userVo = (UserVo) session.getAttribute("authUser");
		if(userVo == null) {
			return "redirect:/board";
		}
		
		model.addAttribute("groupno", groupNo);
		model.addAttribute("orderno", orderNo);
		model.addAttribute("depth", depth);
		
		return "board/reply";
	}
	
	// 답글쓰기
	@RequestMapping(value="/reply", method=RequestMethod.POST)
	public String reply(BoardVo boardVo){
		boardService.reply(boardVo);
		return "redirect:/board";
	}

	
	// 글쓰기 폼
	@RequestMapping(value="/write")
	public String write(HttpSession session, Model model){
		UserVo userVo = (UserVo) session.getAttribute("authUser");
		if(userVo == null) {
			return "redirect:/board";
		}
		return "board/write";
	}
	
	// 글쓰기
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(BoardVo boardVo){
		boardService.write(boardVo);
		return "redirect:/board";
	}
	
	
	// 글 하나 보기 
	@RequestMapping(value="view/{no}", method=RequestMethod.GET )
	public String view(
			@PathVariable Long no, 
			Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) 
	{
		//로그인 되어 있는지 확인 
		UserVo userVo = (UserVo) session.getAttribute("authUser");
		if(userVo == null) {
			return "redirect:/board";
		}
		
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
		
		
		// 게시글 가져오기 
		BoardVo boardVo = boardService.getByNo(no);
		if(boardVo == null) {
			return "redirect:/board";
		}
		model.addAttribute("oneView", boardVo);
		System.out.println(boardVo);
		return "/board/view";
	}
	
	
	// 글수정 폼
	@RequestMapping(value="/modify/{no}")
	public String modify(@PathVariable Long no, Model model) {
		BoardVo boardVo = boardService.getByNo(no);
		if(boardVo == null) {
			return "redirect:/board";
		}
		
		model.addAttribute("oneView", boardVo);

		
		
		return "/board/modify";
	}
	
	// 글 수정 
	@RequestMapping(value="/modify")
	public String modify(BoardVo boardVo) {
		boardService.modify(boardVo);
		System.out.println(boardVo);
		return "redirect:/board/view/"+boardVo.getNo();
	}
	
	
	//글 삭제 
	@RequestMapping("/delete/{no}/{groupno}/{orderno}")
	public String delete(
			@PathVariable(value="no") Long no,
			@PathVariable(value="groupno") int groupNo,
			@PathVariable(value="orderno") int orderNo
	) {
		boardService.delete(groupNo, orderNo, no);
		return "redirect:/board";
	}
	
	
}
