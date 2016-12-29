package model;

import java.util.GregorianCalendar;
import java.util.List;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : BoardInfo.java
//  @ Date : 2015-07-28
//  @ Author : 
//
//




public class BoardInfo {
	private String code;
	private String title;
	private String content;
	private String member;
	private List<String> tagList;
	private GregorianCalendar makeDate;
	private GregorianCalendar alterDate;
	private int click;
	public BoardInfo() {
	}
	
	public BoardInfo(String title, String content, String member,
			List<String> tagList, GregorianCalendar makeDate, int click) {
		this.title = title;
		this.content = content;
		this.member = member;
		this.tagList = tagList;
		this.makeDate = makeDate;
		this.click = click;
	}
	
	public BoardInfo(String code,String title, String content, String member,
			List<String> tagList, GregorianCalendar makeDate, int click) {
		this.code = code;
		this.title = title;
		this.content = content;
		this.member = member;
		this.tagList = tagList;
		this.makeDate = makeDate;
		this.click = click;
	}
	
	public BoardInfo(String title, String content, String member,
			GregorianCalendar makeDate, int click) {
		this.title = title;
		this.content = content;
		this.member = member;
		this.makeDate = makeDate;
		this.click = click;
	}
	
	public BoardInfo(String code,String title, String content, String member,
			GregorianCalendar makeDate, int click) {
		this.code = code;
		this.title = title;
		this.content = content;
		this.member = member;
		this.makeDate = makeDate;
		this.click = click;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public GregorianCalendar getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(GregorianCalendar makeDate) {
		this.makeDate = makeDate;
	}

	public GregorianCalendar getAlterDate() {
		return alterDate;
	}

	public void setAlterDate(GregorianCalendar alterDate) {
		this.alterDate = alterDate;
	}

	public int getClick() {
		return click;
	}

	public void setClick(int click) {
		this.click = click;
	}
	
	
	
}