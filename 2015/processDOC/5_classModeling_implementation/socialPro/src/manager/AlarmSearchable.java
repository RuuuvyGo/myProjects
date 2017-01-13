package manager;

import java.util.List;
import java.util.Map;

import model.Alarm;
import model.GroupAlarm;

public interface AlarmSearchable {

	public List<Alarm> loadMemberAlarm(String memberCode);
	public List<Alarm> loadTeamAlarm(String teamCode);
	
	public List<String> searchAlarm(List<String> targetCodeList);
	public String searchAlarm(String targetCode);
	public List<Alarm> searchAlarmCodeList(List<String> alarmCodeList);
	public Alarm searchAlarmCode(String alarmCode);
	public List<GroupAlarm> searchGroupAlarmCodeList(List<String> gAlarmCodeList);
	public Alarm searchGroupAlarmCode(String gAlarmCode);
	public Map<String,List<Alarm>> searchCompareAlarmCodeList(List<String> alarmCodeList);
	public Map<String,Alarm> searchCompareAlarmCode(String alarmCode);
}
