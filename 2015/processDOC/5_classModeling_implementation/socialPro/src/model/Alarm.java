package model;

import java.util.GregorianCalendar;

public class Alarm {

	private String alarmCode;
	private String targetCode;
	private GregorianCalendar checkDate;
	
	public Alarm(String alarmCode, String targetCode,
			GregorianCalendar checkDate) {
		this.alarmCode = alarmCode;
		this.targetCode = targetCode;
		this.checkDate = checkDate;
	}
	public Alarm() {
	}
	
	public Alarm(String alarmCode, String targetCode) {
		this.alarmCode = alarmCode;
		this.targetCode = targetCode;
	}
	public Alarm(String targetCode, GregorianCalendar checkDate) {
		this.targetCode = targetCode;
		this.checkDate = checkDate;
	}
	public Alarm(String targetCode) {
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
	public GregorianCalendar getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(GregorianCalendar checkDate) {
		this.checkDate = checkDate;
	}
	
}
