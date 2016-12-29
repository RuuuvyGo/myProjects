package dto;

public class AlarmDTO {

	private String alarmCode,targetCode,checkDate;
	public AlarmDTO() {
	}
	public AlarmDTO(String alarmCode, String targetCode, String checkDate) {
		this.alarmCode = alarmCode;
		this.targetCode = targetCode;
		this.checkDate = checkDate;
	}
	public AlarmDTO(String alarmCode, String targetCode) {
		this.alarmCode = alarmCode;
		this.targetCode = targetCode;
	}
	public AlarmDTO(String targetCode) {
		this.targetCode = targetCode;
	}
	public String getAlarmCode() {
		return alarmCode;
	}
	public void setAlarmCode(String alarmCode) {
		this.alarmCode = alarmCode;
	}
	public String getTargetCode() {
		return targetCode;
	}
	public void setTargetCode(String targetCode) {
		this.targetCode = targetCode;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	
}
