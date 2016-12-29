package form;

public class SimpleMemberForm {

	private String memberCode;
	private String memberNickName;
	private String projectCode;
	public SimpleMemberForm() {
	}
	public SimpleMemberForm(String memberCode, String memberNickName) {
		this.memberCode = memberCode;
		this.memberNickName = memberNickName;
	}
	
	public SimpleMemberForm(String memberCode, String memberNickName,
			String projectCode) {
		this.memberCode = memberCode;
		this.memberNickName = memberNickName;
		this.projectCode = projectCode;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getMemberNickName() {
		return memberNickName;
	}
	public void setMemberNickName(String memberNickName) {
		this.memberNickName = memberNickName;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
}
