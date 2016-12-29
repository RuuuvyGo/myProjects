package form;

public class AlarmInfoForm {

	private String alarmCode;
	private String alarmContent;
	private int check;
	public AlarmInfoForm() {
	}
	public AlarmInfoForm(String alarmCode, String alarmContent, int check) {
		this.alarmCode = alarmCode;
		this.alarmContent = alarmContent;
		this.check = check;
	}
	public String getAlarmCode() {
		return alarmCode;
	}
	public void setAlarmCode(String alarmCode) {
		this.alarmCode = alarmCode;
	}
	public String getAlarmContent() {
		return alarmContent;
	}
	public void setAlarmContent(String alarmContent) {
		this.alarmContent = alarmContent;
	}
	public int getCheck() {
		return check;
	}
	public void setCheck(int check) {
		this.check = check;
	}
}
