package com.cafe24.mysite.controller;


import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.service.BoardService;
import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.security.Auth;


@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// 리스트
	@RequestMapping({"", "/list"})
	public String list(
		@RequestParam(value="page", required=true, defaultValue="1") int nowPage,
		@RequestParam(value="kwd", required=false) String kwd,
		Model model
	){
		//페이징 처리 한 리스트
		System.out.println(kwd);
		Map<String,Object> map = boardService.getPagingList(nowPage, kwd);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("paging", map.get("paging"));
		
		return "board/list";
	}
	
	@Auth
	// 글쓰기 폼
	@RequestMapping(value="/write/{kind}")
	public String write(
		@PathVariable String kind,
		@RequestParam(value="board", required=false) BoardVo boardVo,
		@RequestParam(value="g", required=true, defaultValue="0" ) int groupNo,
		@RequestParam(value="o", required=true, defaultValue="0") int orderNo,
		@RequestParam(value="d", required=true, defaultValue="0") int depth,
		Model model
	){	
		// 답글쓰기 일때 필요한 정보들 
		model.addAttribute("groupno", groupNo);
		model.addAttribute("orderno", orderNo);
		model.addAttribute("depth", depth);
		model.addAttribute("kind", kind);
		return "board/write";
	}
	
	@Auth
	// 글쓰기
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(
		@ModelAttribute BoardVo boardVo,
		@RequestParam("kind") String kind
	){
		
		if("new".equals(kind)) {
			//새글쓰기
			boardService.write(boardVo);
		} else {
			//답글쓰기
			boardService.reply(boardVo);
		}
		
		return "redirect:/board";
	}
	
	@Auth
	// 글 하나 보기 
	@RequestMapping(value="view/{no}", method=RequestMethod.GET )
	public String view(
		@PathVariable Long no, 
		Model model
		//@AuthUser UserVo userVo
	){
		//로그인확인 - AuthLoginInterceptor 가 해줌
		
		/*
		 * //1. 쿠키 확인 String visitNo = userVo.getNo().toString() + ":";
		 * 
		 * 
		 * Cookie[] cookies = request.getCookies(); Boolean contains = false; if(cookies
		 * != null && cookies.length > 0) { for(Cookie c : cookies) {
		 * if(("visitUser-"+no).equals(c.getName())) { String[] noList =
		 * c.getValue().split(":"); contains =
		 * Arrays.stream(noList).anyMatch(userVo.getNo().toString()::equals);
		 * if(!contains) { visitNo += c.getValue(); } } } }
		 * 
		 * // 2. 해당 쿠키 이름이 없거나, 해당 user가 조회한 기록이 없을 때 : 쿠키 수정 if(!contains) { //조회수 1 증가
		 * boardService.hitPlus(no); //쿠키에 쓰기 Cookie cookie = new Cookie("visitUser-"+no
		 * + ")", visitNo ); cookie.setMaxAge(24*60*60);
		 * cookie.setPath(request.getContextPath()+"/board/view/"+no);
		 * response.addCookie(cookie); }
		 */
		
		
		// 게시글 가져오기 
		BoardVo boardVo = boardService.getByNo(no);
		if(boardVo == null) {
			return "redirect:/board";
		}
		model.addAttribute("oneView", boardVo);
		return "/board/view";
	}
	
	@Auth
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
	
	@Auth
	// 글 수정 
	@RequestMapping(value="/modify")
	public String modify(BoardVo boardVo) {
		boardService.modify(boardVo);
		return "redirect:/board/view/"+boardVo.getNo();
	}
	
	@Auth
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
	
	@Auth
	@RequestMapping("/search")
	public String search(@RequestParam String kwd) {
		System.out.println("kwd:"+kwd);
		return "redirect:/board";
	}
	
}
