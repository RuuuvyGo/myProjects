package dto;

public class TagDetailsDTO {
	
	private String tagCode;
	private String sourceCode;
	public TagDetailsDTO(String tagCode, String sourceCode) {
		this.tagCode = tagCode;
		this.sourceCode = sourceCode;
	}
	public TagDetailsDTO() {
	}
	public String getTagCode() {
		return tagCode;
	}
	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	
}
