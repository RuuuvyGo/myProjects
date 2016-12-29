package dto;

public class OriginFileCommitVDTO {

	private String commitCode;
	private String originFileCode;
	private String originOwnerCode;
	private String title;	
	private String description;
	private String insertDate;
	private String content;
	private int merge;
	private int type;
	public OriginFileCommitVDTO() {
	}
	public OriginFileCommitVDTO(String commitCode, int type,String originFileCode) {
		this.commitCode = commitCode;
		this.type = type;
		this.originFileCode = originFileCode;
	}
	
	public OriginFileCommitVDTO(String commitCode, String originFileCode,String originOwnerCode, String title, String description,
			String insertDate, String content, int merge, int type) {
		this.commitCode = commitCode;
		this.originFileCode = originFileCode;
		this.originOwnerCode = originOwnerCode;
		this.title = title;
		this.description = description;
		this.insertDate = insertDate;
		this.content = content;
		this.merge = merge;
		this.type = type;
	}
	
	
	
	public String getCommitCode() {
		return commitCode;
	}
	public void setCommitCode(String commitCode) {
		this.commitCode = commitCode;
	}
	public String getOriginFileCode() {
		return originFileCode;
	}
	public void setOriginFileCode(String originFileCode) {
		this.originFileCode = originFileCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getMerge() {
		return merge;
	}
	public void setMerge(int merge) {
		this.merge = merge;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getOriginOwnerCode() {
		return originOwnerCode;
	}
	public void setOriginOwnerCode(String originOwnerCode) {
		this.originOwnerCode = originOwnerCode;
	}
	
}
