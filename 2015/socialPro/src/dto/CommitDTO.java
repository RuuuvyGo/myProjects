package dto;

public class CommitDTO {

	private String commitCode;
	private String title;
	private String ownerCode;
	private String insertDate;
	private String setCode;
	private String content;
	private int merge;
	private int type;
	//setCode\commitCode = remotePath
	
	public CommitDTO() {
	}
	public CommitDTO(String title, String memberCode,String insertDate, String setCode, String content, int merge) {
		this.title = title;
		this.ownerCode = memberCode;
		this.insertDate = insertDate;
		this.setCode = setCode;
		this.content = content;
		this.merge = merge;
	}
	
	public CommitDTO(String commitCode, String title, String memberCode,String insertDate, String setCode, String content, int merge) {
		this.commitCode = commitCode;
		this.title = title;
		this.ownerCode = memberCode;
		this.insertDate = insertDate;
		this.setCode = setCode;
		this.content = content;
		this.merge = merge;
	}
	
	public CommitDTO(int type) {
		this.type = type;
	}
	public CommitDTO(String title, String memberCode,String insertDate, String setCode, String content, int merge,int type) {
		this.title = title;
		this.ownerCode = memberCode;
		this.insertDate = insertDate;
		this.setCode = setCode;
		this.content = content;
		this.merge = merge;
		this.type = type;
	}
	
	public CommitDTO(String commitCode, String title, String memberCode,String insertDate, String setCode, String content, int merge,int type) {
		this.commitCode = commitCode;
		this.title = title;
		this.ownerCode = memberCode;
		this.insertDate = insertDate;
		this.setCode = setCode;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOwnerCode() {
		return ownerCode;
	}
	public void setOwnerCode(String memberCode) {
		this.ownerCode = memberCode;
	}
	public String getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}
	public String getSetCode() {
		return setCode;
	}
	public void setSetCode(String setCode) {
		this.setCode = setCode;
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
	
	
}
