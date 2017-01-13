package dto;

public class CopiedProjectVDTO {

	private String copiedProjectCode;
	private String copiedFolderCode;
	private String copiedPath;
	private String copiedOwnerCode;
	private String originProjectCode;
	private String originFolderCode;
	private String originPath;
	private String originOwnerCode;
	public CopiedProjectVDTO() {
	}
	public CopiedProjectVDTO(String copiedProjectCode, String copiedFolderCode,
			String copiedPath, String copiedOwnerCode,
			String originProjectCode, String originFolderCode,
			String originPath, String originOwnerCode) {
		this.copiedProjectCode = copiedProjectCode;
		this.copiedFolderCode = copiedFolderCode;
		this.copiedPath = copiedPath;
		this.copiedOwnerCode = copiedOwnerCode;
		this.originProjectCode = originProjectCode;
		this.originFolderCode = originFolderCode;
		this.originPath = originPath;
		this.originOwnerCode = originOwnerCode;
	}
	public String getCopiedProjectCode() {
		return copiedProjectCode;
	}
	public void setCopiedProjectCode(String copiedProjectCode) {
		this.copiedProjectCode = copiedProjectCode;
	}
	public String getCopiedFolderCode() {
		return copiedFolderCode;
	}
	public void setCopiedFolderCode(String copiedFolderCode) {
		this.copiedFolderCode = copiedFolderCode;
	}
	public String getCopiedPath() {
		return copiedPath;
	}
	public void setCopiedPath(String copiedPath) {
		this.copiedPath = copiedPath;
	}
	public String getCopiedOwnerCode() {
		return copiedOwnerCode;
	}
	public void setCopiedOwnerCode(String copiedOwnerCode) {
		this.copiedOwnerCode = copiedOwnerCode;
	}
	public String getOriginProjectCode() {
		return originProjectCode;
	}
	public void setOriginProjectCode(String originProjectCode) {
		this.originProjectCode = originProjectCode;
	}
	public String getOriginFolderCode() {
		return originFolderCode;
	}
	public void setOriginFolderCode(String originFolderCode) {
		this.originFolderCode = originFolderCode;
	}
	public String getOriginPath() {
		return originPath;
	}
	public void setOriginPath(String originPath) {
		this.originPath = originPath;
	}
	public String getOriginOwnerCode() {
		return originOwnerCode;
	}
	public void setOriginOwnerCode(String originOwnerCode) {
		this.originOwnerCode = originOwnerCode;
	}
	
	
}
