package com.cafe24.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.dto.Paging;
import com.cafe24.mysite.repository.BoardDao;
import com.cafe24.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardDao dao;
	
	
	public Map<String,Object> getPagingList(int nowPage) {
		
		int totalPosts = dao.getTotalPosts();
		Paging paging = new Paging(totalPosts, nowPage) ;
		System.out.println(paging);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("totalPageNum", paging.getTotalPageNum());
		System.out.println("fromPost : " + paging.getFromPost() + " , perPageNum : " + paging.getPerPageNum());
		List<BoardVo> list = dao.getPagingList(paging.getFromPost(), paging.getPerPageNum());
		map.put("list",list);
		
		return map;
	}
	
	// 게시글 삭제
	public void delete(int groupNo, int orderNo, Long no) {
		dao.deleteupdate(groupNo, orderNo);
		dao.delete(no);
		
	}
	// 게시글 수정시 고치는 일 
	public Boolean modify(BoardVo boardVo) {
		return dao.update(boardVo);
	}
	// 게시글 보기 및 게시글 수정 시 가져오기
	public BoardVo getByNo(Long no) {
		return dao.getByNo(no);
	}
	// 게시글 보기 및 게시글 수정 시 가져오기
	public Boolean hitPlus(Long no) {
		return dao.hitUpdate(no);
	}
	
	// 글쓰기
	public Boolean write(BoardVo boardVo) {
		return dao.insert(boardVo);
	}
	
	// 답글쓰기
	public void reply(BoardVo boardVo) {
		dao.orderUpdate(boardVo);
		dao.replyInsert(boardVo);
	}
	// 리스트
	public List<BoardVo> getList() {
		return dao.getList();
	}

}
