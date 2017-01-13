package dto;

public class GroupAlarmDTO {

	private String groupAlarmCode,targetCode,memberCode,checkDate;

	public GroupAlarmDTO() {
	}

	public GroupAlarmDTO(String targetCode, String memberCode) {
		this.targetCode = targetCode;
		this.memberCode = memberCode;
		this.checkDate = null;
	}

	public GroupAlarmDTO(String groupAlarmCode, String targetCode,
			String memberCode) {
		this.groupAlarmCode = groupAlarmCode;
		this.targetCode = targetCode;
		this.memberCode = memberCode;
	}

	public GroupAlarmDTO(String groupAlarmCode, String targetCode,
			String memberCode, String checkDate) {
		this.groupAlarmCode = groupAlarmCode;
		this.targetCode = targetCode;
		this.memberCode = memberCode;
		this.checkDate = checkDate;
	}

	public String getGroupAlarmCode() {
		return groupAlarmCode;
	}

	public void setGroupAlarmCode(String groupAlarmCode) {
		this.groupAlarmCode = groupAlarmCode;
	}

	public String getTargetCode() {
		return targetCode;
	}

	public void setTargetCode(String targetCode) {
		this.targetCode = targetCode;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	
	
}
