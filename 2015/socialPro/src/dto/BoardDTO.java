package dto;

import java.util.List;

public class BoardDTO {

	private String boardCode;
	private String title;
	private String content;
	private String memberCode;
	private List<String> tagList;
	private String insertDate;
	private String alterDate;
	private int recommend;
	public BoardDTO() {
	}
	public BoardDTO(String boardCode, String title, String content,
			String memberCode, String insertDate, int recommend) {
		this.boardCode = boardCode;
		this.title = title;
		this.content = content;
		this.memberCode = memberCode;
		this.insertDate = insertDate;
		this.recommend = recommend;
	}
	public BoardDTO(String boardCode, String title, String content,
			String memberCode, List<String> tagList, String insertDate,
			int recommend) {
		this.boardCode = boardCode;
		this.title = title;
		this.content = content;
		this.memberCode = memberCode;
		this.tagList = tagList;
		this.insertDate = insertDate;
		this.recommend = recommend;
	}
	public BoardDTO(String boardCode, String title, String content,
			String memberCode, List<String> tagList, String insertDate,
			String alterDate, int recommend) {
		this.boardCode = boardCode;
		this.title = title;
		this.content = content;
		this.memberCode = memberCode;
		this.tagList = tagList;
		this.insertDate = insertDate;
		this.alterDate = alterDate;
		this.recommend = recommend;
	}
	public String getBoardCode() {
		return boardCode;
	}
	public void setBoardCode(String boardCode) {
		this.boardCode = boardCode;
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
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public List<String> getTagList() {
		return tagList;
	}
	public void setTagList(List<String> tagList) {
		this.tagList = tagList;
	}
	public String getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}
	public String getAlterDate() {
		return alterDate;
	}
	public void setAlterDate(String alterDate) {
		this.alterDate = alterDate;
	}
	public int getRecommend() {
		return recommend;
	}
	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	
	
	
}
