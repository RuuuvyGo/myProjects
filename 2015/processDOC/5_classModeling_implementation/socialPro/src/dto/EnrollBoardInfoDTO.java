package dto;

import java.util.List;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : EnrollBoardInfoDTO.java
//  @ Date : 2015-07-28
//  @ Author : 
//
//




public class EnrollBoardInfoDTO {
	private String title;
	private String content;
	private String member;
	private List<String> tagList;
	private String makeDate;
	private int click;
	public EnrollBoardInfoDTO() {
	}
	public EnrollBoardInfoDTO(String title, String content, String member,
			List<String> tagList, String makeDate, int click) {
		this.title = title;
		this.content = content;
		this.member = member;
		this.tagList = tagList;
		this.makeDate = makeDate;
		this.click = click;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}
	public List<String> getTagList() {
		return tagList;
	}
	public void setTagList(List<String> tagList) {
		this.tagList = tagList;
	}
	public String getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(String makeDate) {
		this.makeDate = makeDate;
	}
	public int getClick() {
		return click;
	}
	public void setClick(int click) {
		this.click = click;
	}
	
	
}
