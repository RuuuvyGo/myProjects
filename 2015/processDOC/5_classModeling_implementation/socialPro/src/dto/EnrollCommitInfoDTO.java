package dto;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : EnrollCommitInfoDTO.java
//  @ Date : 2015-07-28
//  @ Author : 
//
//




public class EnrollCommitInfoDTO {
	private String objectKind;
	private String objectCode;
	private String content;
	private String title;
	private String makeDate;
	private String member;
	private String merge;
	public EnrollCommitInfoDTO() {
	}
	public EnrollCommitInfoDTO(String objectKind, String objectCode,
			String content, String title, String makeDate, String member,
			String merge) {
		this.objectKind = objectKind;
		this.objectCode = objectCode;
		this.content = content;
		this.title = title;
		this.makeDate = makeDate;
		this.member = member;
		this.merge = merge;
	}
	public String getObjectKind() {
		return objectKind;
	}
	public void setObjectKind(String objectKind) {
		this.objectKind = objectKind;
	}
	public String getObjectCode() {
		return objectCode;
	}
	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(String makeDate) {
		this.makeDate = makeDate;
	}
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}
	public String getMerge() {
		return merge;
	}
	public void setMerge(String merge) {
		this.merge = merge;
	}
	
	
}