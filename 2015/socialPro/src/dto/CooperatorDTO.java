package dto;

public class CooperatorDTO {

	private String memberCode;
	private String setCode;
	
	public CooperatorDTO() {
	}
	
	public CooperatorDTO(String memberCode, String setCode) {
		this.memberCode = memberCode;
		this.setCode = setCode;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getSetCode() {
		return setCode;
	}

	public void setSetCode(String setCode) {
		this.setCode = setCode;
	}
	
	
}
