package model;


//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : LogInfo.java
//  @ Date : 2015-07-28
//  @ Author : 
//
//




public class LogInfo {
	private String code;
	private String objectCode;
	private String kind;
	private String content;//commit Content
	private String date;
	private String memberCode;
	private boolean merge;
	public LogInfo() {
	}
	public LogInfo(String objectCode, String kind, String content,
			String date, String memberCode, boolean merge) {
		this.objectCode = objectCode;
		this.kind = kind;
		this.content = content;
		this.date = date;
		this.memberCode = memberCode;
		this.merge = merge;
	}
	
	public LogInfo(String code, String objectCode, String kind, String content,
			String date, String memberCode, boolean merge) {
		this.code = code;
		this.objectCode = objectCode;
		this.kind = kind;
		this.content = content;
		this.date = date;
		this.memberCode = memberCode;
		this.merge = merge;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getObjectCode() {
		return objectCode;
	}
	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public boolean isMerge() {
		return merge;
	}
	public void setMerge(boolean merge) {
		this.merge = merge;
	}
	
	
}
