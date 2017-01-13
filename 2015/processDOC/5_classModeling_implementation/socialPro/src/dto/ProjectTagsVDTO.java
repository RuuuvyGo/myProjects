package dto;

public class ProjectTagsVDTO {

	private String tagCode;
	private String tagName;
	private String projectCode;
	private String projectName;
	public ProjectTagsVDTO() {
	}
	public ProjectTagsVDTO(String tagCode, String tagName, String projectCode,
			String projectName) {
		this.tagCode = tagCode;
		this.tagName = tagName;
		this.projectCode = projectCode;
		this.projectName = projectName;
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
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	
}
