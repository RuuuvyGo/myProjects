package model;

import java.util.GregorianCalendar;

public class Commit {

	private String commitCode,title,content,description,setCode;
	private GregorianCalendar insertDate;
	public Commit() {
	}
	public Commit(String commitCode, String title, String content,
			String description, String setCode, GregorianCalendar insertDate) {
		this.commitCode = commitCode;
		this.title = title;
		this.content = content;
		this.description = description;
		this.setCode = setCode;
		this.insertDate = insertDate;
	}
	public String getCommitCode() {
		return commitCode;
	}
	public void setCommitCode(String commitCode) {
		this.commitCode = commitCode;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSetCode() {
		return setCode;
	}
	public void setSetCode(String setCode) {
		this.setCode = setCode;
	}
	public GregorianCalendar getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(GregorianCalendar insertDate) {
		this.insertDate = insertDate;
	}
	
}
