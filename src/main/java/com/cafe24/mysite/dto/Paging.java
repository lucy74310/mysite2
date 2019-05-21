package com.cafe24.mysite.dto;

public class Paging {
	
	private int totalPosts; 
	private int perPageNum = 3;
	private int totalPageNum;
	private int nowPage;
	private int fromPost;
	
	
	
	public Paging (int totalPosts, int nowPage ) {
		this.totalPosts=totalPosts;
		this.nowPage=nowPage;
		
		this.totalPageNum = (int) Math.ceil(totalPosts/perPageNum);
		this.fromPost = (nowPage-1)*perPageNum;
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



	@Override
	public String toString() {
		return "Paging [totalPosts=" + totalPosts + ", perPageNum=" + perPageNum + ", totalPageNum=" + totalPageNum
				+ ", nowPage=" + nowPage + ", fromPost=" + fromPost + "]";
	}



	

}
