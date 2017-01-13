package dto;

public class ProjectSharedMemVDTO {

	private String projectCode;
	private String projectName;
	private String memberCode;
	private String nickName;
	private String email;
	public ProjectSharedMemVDTO() {
	}
	public ProjectSharedMemVDTO(String projectCode, String projectName,
			String memberCode, String nickName, String email) {
		this.projectCode = projectCode;
		this.projectName = projectName;
		this.memberCode = memberCode;
		this.nickName = nickName;
		this.email = email;
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
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
