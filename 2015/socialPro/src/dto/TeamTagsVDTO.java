package dto;

public class TeamTagsVDTO {

	private String tagCode;
	private String tagName;
	private String teamCode;
	private String teamName;
	private String description;
	public TeamTagsVDTO() {
	}
	public TeamTagsVDTO(String tagCode, String tagName, String teamCode,
			String teamName, String description) {
		this.tagCode = tagCode;
		this.tagName = tagName;
		this.teamCode = teamCode;
		this.teamName = teamName;
		this.description = description;
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
	public String getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
