package dto;

public class CopiedFolderDTO {

	private String originFolderCode;
	private String originFolderPath;
	private String oParentFolderCode;
	private String originOwnerCode;
	private String copiedFolderCode;
	private String copiedFolderPath;
	private String cParentFolderCode;
	private String copiedOwnerCode;
	public CopiedFolderDTO() {
	}
	public CopiedFolderDTO(String originFolderCode, String originFolderPath, String copiedFolderCode,String copiedFolderPath) {
		this.originFolderCode = originFolderCode;
		this.originFolderPath = originFolderPath;
		this.copiedFolderCode = copiedFolderCode;
		this.copiedFolderPath = copiedFolderPath;
	}
	
	public CopiedFolderDTO(String originFolderCode, String originFolderPath,String oParentFolderCode, String originOwner,
			String copiedFolderCode, String copiedFolderPath,String cParentFolderCode, String copiedOwner) {
		
		this.originFolderCode = originFolderCode;
		this.originFolderPath = originFolderPath;
		this.oParentFolderCode = oParentFolderCode;
		this.originOwnerCode = originOwner;
		this.copiedFolderCode = copiedFolderCode;
		this.copiedFolderPath = copiedFolderPath;
		this.cParentFolderCode = cParentFolderCode;
		this.copiedOwnerCode = copiedOwner;
	}
	public String getOriginFolderCode() {
		return originFolderCode;
	}
	public void setOriginFolderCode(String originFolderCode) {
		this.originFolderCode = originFolderCode;
	}
	public String getOriginFolderPath() {
		return originFolderPath;
	}
	public void setOriginFolderPath(String originFolderPath) {
		this.originFolderPath = originFolderPath;
	}
	public String getCopiedFolderCode() {
		return copiedFolderCode;
	}
	public void setCopiedFolderCode(String copiedFolderCode) {
		this.copiedFolderCode = copiedFolderCode;
	}
	public String getCopiedFolderPath() {
		return copiedFolderPath;
	}
	public void setCopiedFolderPath(String copiedFolderPath) {
		this.copiedFolderPath = copiedFolderPath;
	}
	public String getoParentFolderCode() {
		return oParentFolderCode;
	}
	public void setoParentFolderCode(String oParentFolderCode) {
		this.oParentFolderCode = oParentFolderCode;
	}
	public String getOriginOwnerCode() {
		return originOwnerCode;
	}
	public void setOriginOwnerCode(String originOwner) {
		this.originOwnerCode = originOwner;
	}
	public String getcParentFolderCode() {
		return cParentFolderCode;
	}
	public void setcParentFolderCode(String cParentFolderCode) {
		this.cParentFolderCode = cParentFolderCode;
	}
	public String getCopiedOwnerCode() {
		return copiedOwnerCode;
	}
	public void setCopiedOwnerCode(String copiedOwner) {
		this.copiedOwnerCode = copiedOwner;
	}
	
	
}
