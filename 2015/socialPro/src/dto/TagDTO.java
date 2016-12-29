package dto;

public class TagDTO {

	private String tagCode;
	private String tagName;
	public TagDTO(String tagCode, String tagName) {
		this.tagCode = tagCode;
		this.tagName = tagName;
	}
	public TagDTO(String tagName) {
		this.tagName = tagName;
	}
	public TagDTO() {
	}
	public String getTagCode() {
		return tagCode;
	}
	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	
}
