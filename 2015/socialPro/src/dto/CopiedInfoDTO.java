package dto;

public class CopiedInfoDTO {

	private String copiedInfoCode;
	private String originInfoCode;
	public CopiedInfoDTO() {
	}
	public CopiedInfoDTO(String copiedInfoCode, String originInfoCode) {
		this.copiedInfoCode = copiedInfoCode;
		this.originInfoCode = originInfoCode;
	}
	public String getCopiedInfoCode() {
		return copiedInfoCode;
	}
	public void setCopiedInfoCode(String copiedInfoCode) {
		this.copiedInfoCode = copiedInfoCode;
	}
	public String getOriginInfoCode() {
		return originInfoCode;
	}
	public void setOriginInfoCode(String originInfoCode) {
		this.originInfoCode = originInfoCode;
	}
	
	
}
