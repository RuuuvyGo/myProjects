package dto;

public class MemberDTO {

	private String code;
	private String nickName;
	private String email;
	private String pw;
	private String school;
	private int entranceYear;
	public MemberDTO() {
	}
	public MemberDTO(String nickName, String email, String pw,
			String school, int entranceYear) {
		this.nickName = nickName;
		this.email = email;
		this.pw = pw;
		this.school = school;
		this.entranceYear = entranceYear;
	}
	public MemberDTO(String code,String nickName, String email, String pw,
			String school, int entranceYear) {
		this.code=code;
		this.nickName = nickName;
		this.email = email;
		this.pw = pw;
		this.school = school;
		this.entranceYear = entranceYear;
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
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public int getEntranceYear() {
		return entranceYear;
	}
	public void setEntranceYear(int entranceYear) {
		this.entranceYear = entranceYear;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
