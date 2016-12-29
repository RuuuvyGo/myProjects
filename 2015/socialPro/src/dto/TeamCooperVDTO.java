package dto;

public class TeamCooperVDTO {

	private String teamCode;
	private String teamName;
	private String memberCode;
	private String nickName;
	private String email;
	public TeamCooperVDTO() {
	}
	public TeamCooperVDTO(String teamCode, String teamName, String memberCode,
			String nickName, String email) {
		this.teamCode = teamCode;
		this.teamName = teamName;
		this.memberCode = memberCode;
		this.nickName = nickName;
		this.email = email;
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
