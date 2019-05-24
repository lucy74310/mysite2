package com.cafe24.mysite.vo;

public class BoardVo {
	private Long no;
	private String title;
	private String contents;
	private int groupNo;
	private int orderNo;
	private int depth;
	private String regDate;
	private int hit;
	private Long userNo;
	private String userName;
	private Byte del;
	
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public int getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	
	public Long getUserNo() {
		return userNo;
	}
	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Byte getDel() {
		return del;
	}
	public void setDel(Byte del) {
		this.del = del;
	}
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", contents=" + contents + ", groupNo=" + groupNo
				+ ", orderNo=" + orderNo + ", depth=" + depth + ", regDate=" + regDate + ", hit=" + hit + ", userNo="
				+ userNo + ", userName=" + userName + ", del=" + del + "]";
	}
	
	
	
	
	
	
}
