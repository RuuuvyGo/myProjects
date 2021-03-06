package manager;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import manager.CooperatorMessageManager;
import socialProExceptions.CommitExcetion;
import socialProExceptions.DAOException;
import socialProExceptions.FileException;
import socialProExceptions.FolderException;
import socialProExceptions.MemberException;
import socialProExceptions.ProjectException;
import socialProExceptions.TeamException;
import model.Alarm;

public class MemberAlarmManager {

	private static MemberAlarmManager INSTANCE;
	private Map<String,Map<String,List<String>>> memberAlarmMap;//memberCode commitMessage/cooperMessage alarmCodeList
	
	static{ INSTANCE = new MemberAlarmManager();	}
	
	private MemberAlarmManager(){
		this.memberAlarmMap = new HashMap<String,Map<String,List<String>>>();
	}
	
	public static MemberAlarmManager getINSTANCE() {
		if(INSTANCE==null)INSTANCE = new MemberAlarmManager();
		return INSTANCE;
	}
	
	public void insertNewAlarm(String memberCode, String type, String alarmCode){
		if(!this.memberAlarmMap.containsKey(memberCode)){
			List<String> list = new ArrayList<String>();
			list.add(alarmCode);
			Map<String,List<String>> map = new HashMap<String, List<String>>();
			map.put(type, list);
			this.memberAlarmMap.put(memberCode, map);
		}else{
			Map<String,List<String>> map = this.memberAlarmMap.get(memberCode); 
			if(!map.containsKey(type)){
				List<String> list = new ArrayList<String>();
				list.add(alarmCode);
				map.put(type, list);
			}else{
				if(!map.get(type).contains(alarmCode))map.get(type).add(alarmCode);
			}
		}
	}
	public void insertNewGroupAlarm(List<String> memberList,String type, String groupAlarmCode){
		
		for(String memberCode : memberList){
			String alarmCode = groupAlarmCode;
 			if(!this.memberAlarmMap.containsKey(memberCode)){
				List<String> list = new ArrayList<String>();
				list.add(alarmCode);
				Map<String,List<String>> map = new HashMap<String, List<String>>();
				map.put(type, list);
				this.memberAlarmMap.put(memberCode, map);
			}else{
				Map<String,List<String>> map = this.memberAlarmMap.get(memberCode); 
				if(!map.containsKey(type)){
					List<String> list = new ArrayList<String>();
					list.add(alarmCode);
					map.put(type, list);
				}else{
					if(!map.get(type).contains(alarmCode))map.get(type).add(alarmCode);
				}
			}
		}
	}
	
//////////////////////////////////////////////////////////////////////////////////  search
	
	public void loadNewAlarm(String memberCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, TeamException, FileException, SftpException, JSchException, CommitExcetion, MemberException{
	
		//load Message
		System.out.println("\n     MemmberAlarmManager loadNewAlarm line 85  "+memberCode);
		CommitMessageManager.getINSTANCE().searchMemberCommitMessage(memberCode, false);
		List<String> messageCodeList = CommitMessageManager.getINSTANCE().searchMemberCommitMessageCodeList(memberCode, false);
		if(!messageCodeList.isEmpty()){
			List<String> alarmCodeList= AlarmManager.getINSTANCE().searchAlarm(messageCodeList);
			if(!alarmCodeList.isEmpty()){
				Map<String,List<String>> val = new HashMap<String, List<String>>();
				val.put("commitMessage", alarmCodeList);
				this.memberAlarmMap.put(memberCode, val);
			}
		}
		
		//cooper Message
		CooperatorMessageManager.getINSTANCE().loadUnReadCooperMessage(memberCode);
		List<String> cooperCodeList = CooperatorMessageManager.getINSTANCE().searchMemberUnReadList(memberCode);
		if(!cooperCodeList.isEmpty()){
			List<String> alarmCodeList= AlarmManager.getINSTANCE().searchAlarm(cooperCodeList);
			if(!alarmCodeList.isEmpty()){
				Map<String,List<String>> val = new HashMap<String, List<String>>();
				val.put("cooperMessage", alarmCodeList);
				this.memberAlarmMap.put(memberCode, val);
			}
		}
	}

	public List<Alarm> searchNewAlarm(String memberCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, TeamException, FileException, SftpException, JSchException, CommitExcetion, MemberException{
		
		List<Alarm> res = new ArrayList<Alarm>();
		if(!this.memberAlarmMap.containsKey(memberCode))this.loadNewAlarm(memberCode);
		
		Map<String,List<String>> newAlarmCodeMap = this.memberAlarmMap.get(memberCode);
		if(!newAlarmCodeMap.isEmpty()){
			for(String key : newAlarmCodeMap.keySet()){
				List<Alarm> alarmList = AlarmManager.getINSTANCE().searchAlarmCodeList(newAlarmCodeMap.get(key));
				if(!alarmList.isEmpty())res.addAll(alarmList);
			}
		}
		
		return res;
	}
	
	///////////////////////////////////////////////////////////////////////////   update
	
	public boolean removeAlarm(String memberCode, String type, String alarmCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, TeamException, FileException, SftpException, JSchException, CommitExcetion, MemberException{
		
		if(!this.memberAlarmMap.containsKey(memberCode)){
			this.loadNewAlarm(memberCode);
			if(this.memberAlarmMap.containsKey(memberCode)){
				if(this.memberAlarmMap.get(memberCode).containsKey(type)){
					if(!this.memberAlarmMap.get(memberCode).get(type).isEmpty()){
						if(this.memberAlarmMap.get(memberCode).get(type).contains(alarmCode)){
							this.memberAlarmMap.get(memberCode).get(type).remove(alarmCode);
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
