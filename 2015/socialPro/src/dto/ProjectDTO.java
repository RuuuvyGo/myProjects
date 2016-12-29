package dto;

public class ProjectDTO {

	private String code;
	private String folderCode;
	private String owner;
	private String projectContent;
	public ProjectDTO() {
	}
	public ProjectDTO(String folderCode, String owner) {
		this.folderCode = folderCode;
		this.owner = owner;
	}
	public ProjectDTO(String code, String folderCode, String owner) {
		this.code = code;
		this.folderCode = folderCode;
		this.owner = owner;
	}
	
	public ProjectDTO(String code, String folderCode, String owner,String projectContent) {
		this.code = code;
		this.folderCode = folderCode;
		this.owner = owner;
		this.projectContent = projectContent;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFolderCode() {
		return folderCode;
	}
	public void setFolderCode(String folderCode) {
		this.folderCode = folderCode;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getProjectContent() {
		return projectContent;
	}
	public void setProjectContent(String projectContent) {
		this.projectContent = projectContent;
	}
		
}
