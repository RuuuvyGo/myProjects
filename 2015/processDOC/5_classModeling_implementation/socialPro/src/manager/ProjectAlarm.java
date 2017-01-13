package manager;

import java.util.ArrayList;
import java.util.List;

public class ProjectAlarm {

	private String projectCode;
	private List<String> alarmCodeList;
	private List<String> newAlarmCodeList;
	public ProjectAlarm() {
		this.alarmCodeList = new ArrayList<String>();
		this.newAlarmCodeList = new ArrayList<String>();
	}
	public ProjectAlarm(String projectCode, List<String> alarmCodeList,
			List<String> newAlarmCodeList) {
		this.projectCode = projectCode;
		this.alarmCodeList = alarmCodeList;
		this.newAlarmCodeList = newAlarmCodeList;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public List<String> getAlarmCodeList() {
		return alarmCodeList;
	}
	public void setAlarmCodeList(List<String> alarmCodeList) {
		this.alarmCodeList = alarmCodeList;
	}
	public List<String> getNewAlarmCodeList() {
		return newAlarmCodeList;
	}
	public void setNewAlarmCodeList(List<String> newAlarmCodeList) {
		this.newAlarmCodeList = newAlarmCodeList;
	}
	
	
}
