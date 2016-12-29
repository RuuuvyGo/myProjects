package dto;

public class CopiedFileVDTO {

	private String copiedFileCode;
	private String copiedPath;
	private String copiedFolderCode;
	private String copiedOwnerCode;
	private String originFileCode;
	private String originPath;
	private String originFolderCode;
	private String originOwnerCode;
	
	public CopiedFileVDTO() {
	}
	public CopiedFileVDTO(String copiedFileCode, String copiedPath,
			String originFileCode, String originPath) {
		this.copiedFileCode = copiedFileCode;
		this.copiedPath = copiedPath;
		this.originFileCode = originFileCode;
		this.originPath = originPath;
	}

	public CopiedFileVDTO(String copiedFileCode, String copiedPath,
			String copiedFolderCode, String copiedOwnerCode,
			String originFileCode, String originPath, String originFolderCode,
			String originOwnerCode) {
		this.copiedFileCode = copiedFileCode;
		this.copiedPath = copiedPath;
		this.copiedFolderCode = copiedFolderCode;
		this.copiedOwnerCode = copiedOwnerCode;
		this.originFileCode = originFileCode;
		this.originPath = originPath;
		this.originFolderCode = originFolderCode;
		this.originOwnerCode = originOwnerCode;
	}
	public String getCopiedFileCode() {
		return copiedFileCode;
	}
	public void setCopiedFileCode(String copiedFileCode) {
		this.copiedFileCode = copiedFileCode;
	}
	public String getCopiedPath() {
		return copiedPath;
	}
	public void setCopiedPath(String copiedPath) {
		this.copiedPath = copiedPath;
	}
	public String getOriginFileCode() {
		return originFileCode;
	}
	public void setOriginFileCode(String originFileCode) {
		this.originFileCode = originFileCode;
	}
	public String getOriginPath() {
		return originPath;
	}
	public void setOriginPath(String originPath) {
		this.originPath = originPath;
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
	public String getOriginFolderCode() {
		return originFolderCode;
	}
	public void setOriginFolderCode(String originFolderCode) {
		this.originFolderCode = originFolderCode;
	}
	public String getOriginOwnerCode() {
		return originOwnerCode;
	}
	public void setOriginOwnerCode(String originOwnerCode) {
		this.originOwnerCode = originOwnerCode;
	}
	
}
