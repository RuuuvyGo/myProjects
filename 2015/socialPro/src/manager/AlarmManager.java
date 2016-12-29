package manager;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Alarm;
import model.GroupAlarm;
import socialProExceptions.DAOException;

public class AlarmManager implements AlarmInsertable,AlarmSearchable,AlarmModifiable{

	private static AlarmManager INSTANCE;
	private Map<String,Alarm> alarmMap;//alarmCode alarm

	static{INSTANCE = new AlarmManager();}
	private AlarmManager() {
		// TODO Auto-generated constructor stub
		this.alarmMap = new HashMap<String, Alarm>();
	}	
	
	public static AlarmManager getINSTANCE() {
		return INSTANCE;
	}


	@Override
	public void putAlarm(Alarm alarm) {
		// TODO Auto-generated method stub
		String alarmCode = alarm.getAlarmCode();
		if(this.alarmMap.containsKey(alarmCode)){
			this.alarmMap.remove(alarmCode);
		}
		if(this.alarmMap.get(alarmCode) instanceof GroupAlarm){
			this.alarmMap.put(alarmCode, (GroupAlarm)alarm);
		}
		else this.alarmMap.put(alarmCode, alarm);
		
	}
	
	@Override
	public void putAlarmList(List<Alarm> alarmList){
		for(Alarm alarm : alarmList){
			String alarmCode = alarm.getAlarmCode();
			if(this.alarmMap.containsKey(alarmCode)){
				this.alarmMap.remove(alarmCode);
			}
			if(this.alarmMap.get(alarmCode) instanceof GroupAlarm){
				this.alarmMap.put(alarmCode, (GroupAlarm)alarm);
			}
			else this.alarmMap.put(alarmCode, alarm);
		}		
	}

	public void putGroupAlarm(GroupAlarm gAlarm){
		String alarmCode = gAlarm.getAlarmCode();
		if(this.alarmMap.containsKey(alarmCode)){
			this.alarmMap.remove(alarmCode);
		}
		
		this.alarmMap.put(alarmCode, gAlarm);
	}
	
	
	@Override
	public List<Alarm> loadMemberAlarm(String memberCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Alarm> loadTeamAlarm(String teamCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> searchAlarm(List<String> targetCodeList) {
		// TODO Auto-generated method stub
		List<String> res = new ArrayList<String>();
		for(String targetCode : targetCodeList){
			for(String alarmCode : this.alarmMap.keySet()){
				
				if(this.alarmMap.get(alarmCode).getTargetCode().equals(targetCode)){
					res.add(alarmCode);
				}
			}
		}
		
		return res;
	}

	@Override
	public String searchAlarm(String targetCode) {
		// TODO Auto-generated method stub
		for(String alarmCode : this.alarmMap.keySet()){
			
			if(this.alarmMap.get(alarmCode).getTargetCode().equals(targetCode)){
				return alarmCode;
			}
		}
		return null;
	}
	
	@Override
	public Map<String,List<Alarm>> searchCompareAlarmCodeList(List<String> alarmCodeList) {
		// TODO Auto-generated method stub
		
		Map<String,List<Alarm>> resMap = new HashMap<String, List<Alarm>>();
		List<Alarm> alarm = new ArrayList<Alarm>();
		List<Alarm> groupAlarm = new ArrayList<Alarm>();
		
		for(String alarmCode : alarmCodeList){
			if(this.alarmMap.get(alarmCode) instanceof GroupAlarm)groupAlarm.add((GroupAlarm)this.alarmMap.get(alarmCode));
			else if(this.alarmMap.get(alarmCode) instanceof Alarm)alarm.add(this.alarmMap.get(alarmCode));
		}
		resMap.put("groupAlarm", groupAlarm);
		resMap.put("alarm", alarm);
		
		return resMap;
	}

	@Override
	public Map<String,Alarm> searchCompareAlarmCode(String alarmCode) {
		// TODO Auto-generated method stub
		Map<String,Alarm> resMap = new HashMap<String, Alarm>();
		if(this.alarmMap.get(alarmCode) instanceof GroupAlarm){
			resMap.put("groupAlarm", (GroupAlarm)this.alarmMap.get(alarmCode));
			resMap.put("alarm", null);
		}
		else if(this.alarmMap.get(alarmCode) instanceof Alarm){
			resMap.put("alarm", this.alarmMap.get(alarmCode));
			resMap.put("groupAlarm", null);
		}
		return resMap;
	}
	
	@Override
	public List<Alarm> searchAlarmCodeList(List<String> alarmCodeList) {
		// TODO Auto-generated method stub
		List<Alarm> res = new ArrayList<Alarm>();
		
		for(String alarmCode : alarmCodeList){
			if(this.alarmMap.get(alarmCode) instanceof Alarm)res.add(this.alarmMap.get(alarmCode));
		}
		
		return res;
	}

	@Override
	public Alarm searchAlarmCode(String alarmCode) {
		// TODO Auto-generated method stub
		if(this.alarmMap.get(alarmCode) instanceof Alarm)return this.alarmMap.get(alarmCode);
		return null;
	}
	@Override
	public List<GroupAlarm> searchGroupAlarmCodeList(List<String> gAlarmCodeList) {
		// TODO Auto-generated method stub
		List<GroupAlarm> res = new ArrayList<GroupAlarm>();
		
		for(String gAlarmCode : gAlarmCodeList){
			if(this.alarmMap.get(gAlarmCode) instanceof GroupAlarm)res.add((GroupAlarm)this.alarmMap.get(gAlarmCode));
		}
		
		return res;
	}

	@Override
	public Alarm searchGroupAlarmCode(String gAlarmCode) {
		// TODO Auto-generated method stub
		if(this.alarmMap.get(gAlarmCode) instanceof GroupAlarm)return (GroupAlarm)this.alarmMap.get(gAlarmCode);
		return null;
	}

	@Override
	public boolean modifyCheckDate(String alarmCode, GregorianCalendar checkDate) {
		// TODO Auto-generated method stub
		if(this.alarmMap.containsKey(alarmCode)){
			this.alarmMap.get(alarmCode).setCheckDate(checkDate);
			return true;
		}
		return false;
	}

////////////////////////////////////////////////////////////////////////////   update start
	
	public String updateCommitMsgAlarmCheckDate(String commitMessageCode) throws DAOException{
		Alarm alarm = AlarmDBManager.getINSTANCE().updateCommitMsgAlarmCheckDate(commitMessageCode);
		String alarmCode = alarm.getAlarmCode();
		if(alarm!=null){
			if(this.alarmMap.containsKey(alarmCode))this.alarmMap.get(alarmCode).setCheckDate(alarm.getCheckDate());
			else this.alarmMap.put(alarmCode, alarm);
			return alarmCode;
		}else System.out.println("return alarm is null.....TT");
		return null;
	}

}
