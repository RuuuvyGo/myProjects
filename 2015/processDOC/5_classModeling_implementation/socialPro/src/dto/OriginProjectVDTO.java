package dto;

public class OriginProjectVDTO {

	private String originProjectCode;
	private String originFolderCode;
	private String originPath;
	private String originOwnerCode;
	public OriginProjectVDTO() {
	}
	public OriginProjectVDTO(String originProjectCode, String originFolderCode,
			String originPath, String originOwnerCode) {
		this.originProjectCode = originProjectCode;
		this.originFolderCode = originFolderCode;
		this.originPath = originPath;
		this.originOwnerCode = originOwnerCode;
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
