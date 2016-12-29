package dto;

public class CopiedFileCommitVDTO extends OriginFileCommitVDTO{
	
	private String copiedFileCode;
	private String copiedOwnerCode;
	
	public CopiedFileCommitVDTO() {
		super();
	}
	public CopiedFileCommitVDTO(String commitCode, int type,String copiedFileCode, String originFileCode) {
		super(commitCode, type, originFileCode);
		this.copiedFileCode = copiedFileCode;
	}
	public CopiedFileCommitVDTO(String commitCode, String originFileCode,String originOwnerCode, String title, String description,
			String insertDate, String content, int merge, int type,String copiedFileCode, String copiedOwnerCode) {
		super(commitCode, originFileCode, originOwnerCode, title, description, insertDate, content, merge, type);
		this.copiedFileCode = copiedFileCode;
		this.copiedOwnerCode = copiedOwnerCode;
	}
	public String getCopiedFileCode() {
		return copiedFileCode;
	}
	public void setCopiedFileCode(String copiedFileCode) {
		this.copiedFileCode = copiedFileCode;
	}
	public String getCopiedOwnerCode() {
		return copiedOwnerCode;
	}
	public void setCopiedOwnerCode(String copiedOwnerCode) {
		this.copiedOwnerCode = copiedOwnerCode;
	}
	
}
