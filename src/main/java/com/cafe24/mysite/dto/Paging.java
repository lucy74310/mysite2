package com.cafe24.mysite.dto;

public class Paging {
	
	private int totalPosts; 
	private int perPageNum = 5;
	private int totalPageNum;
	private int nowPage;
	private int fromPost;
	private int pageBlockNum = 5;
	private int pageBlockBeginPage;
	private int pageBlockEndPage;
	private Boolean preButtonActive;
	private Boolean nextButtonActive;
	private String keyword;
	
	
	
	public Paging (int totalPosts, int nowPage ) {
		this.totalPosts=totalPosts;
		this.nowPage=nowPage;
		
		//총 페이지 갯수 
		totalPageNum = (int) Math.ceil((float)totalPosts/perPageNum);
		//지금 가져오려는 페이지 시작 포스트 번호 
		fromPost = (nowPage-1)*perPageNum;
		
		//페이지 블록 시작, 끝 페이지
		setPageBlock();
		
		//버튼 엑티브 여부 
		setButtonActive();
	}
	
	
	
	private void setPageBlock() {
		
		int divideResult = (int) Math.floor(nowPage / pageBlockNum);
		int mod = Math.floorMod(nowPage, pageBlockNum);
		if ( mod == 0 ) {
			pageBlockBeginPage = pageBlockNum * (divideResult-1) + 1;
		} else {
			pageBlockBeginPage = pageBlockNum * divideResult + 1;
		}
		
		pageBlockEndPage = pageBlockBeginPage +  (pageBlockNum-1);
		
		if( pageBlockEndPage > totalPageNum ) {
			pageBlockEndPage = totalPageNum;
		}
		
		
	}
	

	
	private void setButtonActive() {
		preButtonActive = pageBlockBeginPage > 1;
		nextButtonActive = totalPageNum > pageBlockEndPage;
	}
	
	
	
	public int getTotalPosts() {
		return totalPosts;
	}



	public void setTotalPosts(int totalPosts) {
		this.totalPosts = totalPosts;
	}


	
	public int getPerPageNum() {
		return perPageNum;
	}


	
	public void setPerPageNum(int perPageNum) {
		this.perPageNum = perPageNum;
	}

	

	public int getTotalPageNum() {
		return totalPageNum;
	}

	
	
	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}



	public int getNowPage() {
		return nowPage;
	}



	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}



	public int getFromPost() {
		return fromPost;
	}



	public void setFromPost(int fromPost) {
		this.fromPost = fromPost;
	}



	public int getPageBlockNum() {
		return pageBlockNum;
	}



	public void setPageBlockNum(int pageBlockNum) {
		this.pageBlockNum = pageBlockNum;
	}

	
	
	public int getPageBlockBeginPage() {
		return pageBlockBeginPage;
	}

	
	
	public void setPageBlockBeginPage(int pageBlockBeginPage) {
		this.pageBlockBeginPage = pageBlockBeginPage;
	}

	
	
	public int getPageBlockEndPage() {
		return pageBlockEndPage;
	}

	
	
	public void setPageBlockEndPage(int pageBlockEndPage) {
		this.pageBlockEndPage = pageBlockEndPage;
	}

	
	
	public Boolean getPreButtonActive() {
		return preButtonActive;
	}

	
	
	public void setPreButtonActive(Boolean preButtonActive) {
		this.preButtonActive = preButtonActive;
	}

	
	
	public Boolean getNextButtonActive() {
		return nextButtonActive;
	}

	
	
	public void setNextButtonActive(Boolean nextButtonActive) {
		this.nextButtonActive = nextButtonActive;
	}



	public String getKeyword() {
		return keyword;
	}



	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}



	@Override
	public String toString() {
		return "Paging [totalPosts=" + totalPosts + ", perPageNum=" + perPageNum + ", totalPageNum=" + totalPageNum
				+ ", nowPage=" + nowPage + ", fromPost=" + fromPost + ", pageBlockNum=" + pageBlockNum
				+ ", pageBlockBeginPage=" + pageBlockBeginPage + ", pageBlockEndPage=" + pageBlockEndPage
				+ ", preButtonActive=" + preButtonActive + ", nextButtonActive=" + nextButtonActive + ", keyword="
				+ keyword + "]";
	}

	
	

	
	
	
	
	

}
