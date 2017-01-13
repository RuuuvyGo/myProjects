package dto;

public class AlarmReceiverDTO {

	private String alarmCode;
	private String memberCode;
	private String checkDate;
	public AlarmReceiverDTO() {
	}
	public AlarmReceiverDTO(String alarmCode, String memberCode,
			String checkDate) {
		this.alarmCode = alarmCode;
		this.memberCode = memberCode;
		this.checkDate = checkDate;
	}
	public String getAlarmCode() {
		return alarmCode;
	}
	public void setAlarmCode(String alarmCode) {
		this.alarmCode = alarmCode;
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
