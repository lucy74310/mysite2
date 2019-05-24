package com.cafe24.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.dto.Paging;
import com.cafe24.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//게시물 전체갯수
	public int getTotalPosts() {
		return sqlSession.selectOne("board.getTotalPosts");
	}
	
	//키워드 포함하는 게시물 전체 갯수
	public int getTotalPostsKwd(String keyword) {
		return sqlSession.selectOne("board.getTotalPostsKwd" , keyword);
	}
	
	// 게시글 삭제
	public Boolean delete(Long no) {
		return 1 == sqlSession.delete("board.delete", no);
	}
	
	// 게시글 삭제 시 해당 게시글 그룹 순서 변경 
	public Boolean deleteupdate(int groupNo, int orderNo) {
		Map<String, Integer> map = new HashMap<String,Integer>();
		map.put("groupNo", groupNo);
		map.put("orderNo", orderNo);
		return 1 == sqlSession.delete("board.deleteupdate", map);
	}
	
	// 게시글 수정시 고치는 일 
	public Boolean update(BoardVo boardVo) {
		return 1 == sqlSession.update("board.update", boardVo);
		
	}
	// 게시글 보기 및 게시글 수정 시 가져오기
	public BoardVo getByNo(Long no) {  
		return sqlSession.selectOne("board.getByNo", no);
	}
	
	public Boolean hitUpdate(Long no) {  
		return 1 == sqlSession.update("board.hitupdate", no);
	}
	
	// 글쓰기
	public Boolean insert(BoardVo vo) {
		return 1 == sqlSession.insert("board.insert", vo);
	}
	// 답글쓰기
	public Boolean replyInsert(BoardVo vo) {
		return 1 == sqlSession.insert("board.replyinsert", vo);
	}
	// 답글쓸때 순서변경
	public Boolean orderUpdate(BoardVo boardVo) {
		return 1 == sqlSession.update("board.orderupdate",boardVo );
	}
	
	//페이징 처리 한 리스트
	public List<BoardVo> getPagingList(int fromPost, int perPageNum){
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("fromPost", fromPost);
		map.put("perPageNum", perPageNum);
		return sqlSession.selectList("board.getPagingList", map);
		
	}
	//키워드 있는 경우 리스트 
	public List<BoardVo> getPagingListKwd(Paging paging){
		return sqlSession.selectList("board.getPagingListKwd", paging);
		
	}
	
	//리스트
	public List<BoardVo> getList() {
		return sqlSession.selectList("board.getList");
	}
}
