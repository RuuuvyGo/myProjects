package dto;

public class CopiedFolderCommitVDTO extends OriginFolderCommitVDTO{

	private String copiedFolderCode,copiedOwnerCode;
	public CopiedFolderCommitVDTO() {
	}
	public CopiedFolderCommitVDTO(String commitCode, int type,String copiedFolderCode, 
			String originFolderCode,String copiedOwnerCode) {
		super(commitCode, type, originFolderCode);
		this.copiedFolderCode = copiedFolderCode;
		this.copiedOwnerCode = copiedOwnerCode;
	}
	public CopiedFolderCommitVDTO(String commitCode, String originFolderCode,String originOwnerCode, String title, 
			String description,String insertDate, String content, int merge, int type,
			String copiedFolderCode,String copiedOwnerCode) {
		super(commitCode, originFolderCode, originOwnerCode, title, description, insertDate, content, merge, type);
		this.copiedFolderCode = copiedFolderCode;
		this.copiedOwnerCode = copiedOwnerCode;
	}
	public String getCopiedFolderCode() {
		return copiedFolderCode;
	}
	public void setCopiedFolderCode(String copiedFolderCode) {
		this.copiedFolderCode = copiedFolderCode;
	}
	public String getCopiedOwnerCode() {
		return copiedOwnerCode;
	}
	public void setCopiedOwnerCode(String copiedOwnerCode) {
		this.copiedOwnerCode = copiedOwnerCode;
	}
	
}
