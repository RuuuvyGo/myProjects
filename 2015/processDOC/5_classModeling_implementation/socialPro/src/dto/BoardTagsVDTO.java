package dto;

public class BoardTagsVDTO {

	private String tagCode;
	private String tagName;
	private String boardCode;
	private String boardTitle;
	private String insertDate;
	private int recommend;
	public BoardTagsVDTO() {
	}
	public BoardTagsVDTO(String tagCode, String tagName, String boardCode,
			String boardTitle, String insertDate, int recommend) {
		this.tagCode = tagCode;
		this.tagName = tagName;
		this.boardCode = boardCode;
		this.boardTitle = boardTitle;
		this.insertDate = insertDate;
		this.recommend = recommend;
	}
	public String getTagCode() {
		return tagCode;
	}
	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getBoardCode() {
		return boardCode;
	}
	public void setBoardCode(String boardCode) {
		this.boardCode = boardCode;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}
	public int getRecommend() {
		return recommend;
	}
	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	
	
}
