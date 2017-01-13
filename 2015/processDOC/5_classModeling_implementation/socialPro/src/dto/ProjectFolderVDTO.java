package dto;

public class ProjectFolderVDTO {

	private String projectCode;
	private String folderCode;
	private String owner;
	private String name;
	private String description;
	private String path;
	private String insertDate;
	private String alterDate;
	private String parentFolderCode;
	private String folderContent;
	
	public ProjectFolderVDTO() {
	}
	
	public ProjectFolderVDTO(String code, String folderCode, String owner,
			String name, String description, String path, String insertDate,
			String alterDate, String parentFolderCode, String folderContent) {
		this.projectCode = code;
		this.folderCode = folderCode;
		this.owner = owner;
		this.name = name;
		this.description = description;
		this.path = path;
		this.insertDate = insertDate;
		this.alterDate = alterDate;
		this.parentFolderCode = parentFolderCode;
		this.folderContent = folderContent;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}

	public String getAlterDate() {
		return alterDate;
	}

	public void setAlterDate(String alterDate) {
		this.alterDate = alterDate;
	}

	public String getParentFolderCode() {
		return parentFolderCode;
	}

	public void setParentFolderCode(String parentFolderCode) {
		this.parentFolderCode = parentFolderCode;
	}

	public String getFolderContent() {
		return folderContent;
	}

	public void setFolderContent(String folderContent) {
		this.folderContent = folderContent;
	}
}
