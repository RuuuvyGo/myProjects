package dto;

public class CooperatorMessageDTO {

	private String messageCode;
	private int type;
	private String sourceCode;
	public CooperatorMessageDTO(String messageCode, int type, String sourceCode) {
		this.messageCode = messageCode;
		this.type = type;
		this.sourceCode = sourceCode;
	}
	
	public CooperatorMessageDTO() {
	}

	public String getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	
	
		
}
