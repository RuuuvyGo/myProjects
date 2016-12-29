package model;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class GroupAlarm extends Alarm{

	private Map<String,GregorianCalendar> memberCheckMap;

	
	public GroupAlarm() {
		super();
		this.memberCheckMap = new HashMap<String, GregorianCalendar>();
	}
	public GroupAlarm(String alarmCode, String targetCode) {
		super(alarmCode, targetCode);
		this.memberCheckMap = new HashMap<String, GregorianCalendar>();
	}
	public GroupAlarm(String targetCode) {
		super(targetCode);
		this.memberCheckMap = new HashMap<String, GregorianCalendar>();
	}
	public GroupAlarm(String alarmCode, String targetCode, Map<String,GregorianCalendar> memberCheckMap) {
		super(alarmCode, targetCode);
		this.memberCheckMap = memberCheckMap;
	}
	public GroupAlarm(String targetCode, Map<String,GregorianCalendar> memberCheckMap) {
		super(targetCode);
		this.memberCheckMap = memberCheckMap;
	}
	public Map<String,GregorianCalendar> getMemberCheckMap() {
		return memberCheckMap;
	}

	public void setMemberCheckMap(Map<String,GregorianCalendar> memberCheckMap) {
		this.memberCheckMap = memberCheckMap;
	}
	public void putMemberCheckDate(String memberCode, GregorianCalendar checkDate){
		if(this.memberCheckMap.containsKey(memberCode))this.memberCheckMap.remove(memberCode);
		this.memberCheckMap.put(memberCode, checkDate);
	}
	public GregorianCalendar getMemberCheckDate(String memberCode){
		if(!this.memberCheckMap.containsKey(memberCode))return null;
		return this.memberCheckMap.get(memberCode);
	}
	public boolean isMemberCheckDateNull(String memberCode){
		if(!this.memberCheckMap.containsKey(memberCode))return true;
		if(this.memberCheckMap.get(memberCode)==null)return true;
		return false;
	}
}
