package action;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import model.Alarm;
import model.CommitMessage;
import model.GroupAlarm;
import socialProExceptions.DAOException;


public interface CommitMessageSearchAction {

	//1.	If member is Team Manager 
	// 1-A.	Commit message received from cooperator
	public Map<String,Map<String,Map<Alarm, CommitMessage>>>  loadUnReadTeamListCommitMsgManagerRec(Map<String,Map<String,Map<String,List<String>>>> teamOriginProChildren, String managerCode) throws DAOException, ParseException;
	public Map<String,Map<String,Map<Alarm, CommitMessage>>>  loadReadTeamListCommitMsgManagerRec(Map<String,Map<String,Map<String,List<String>>>> teamOriginProChildren, String managerCode) throws DAOException, ParseException;
	public Map<String,Map<String,Map<Alarm, CommitMessage>>>  loadUnReadTeamProjectListCommitMsgManagerRec(Map<String,Map<String,List<String>>> teamOriginProChildren, String teamCode,String managerCode) throws DAOException, ParseException;
	public Map<String,Map<String,Map<Alarm, CommitMessage>>>  loadReadTeamProjectListCommitMsgManagerRec(Map<String,Map<String,List<String>>> teamOriginProChildren, String teamCode, String managerCode) throws DAOException, ParseException;
	public Map<String,Map<String,Map<Alarm, CommitMessage>>>  loadUnReadTeamProjectCommitMsgManagerRec(Map<String,Map<String,List<String>>> teamCooperCpChildren, String teamCode,String managerCode,String oriProCode) throws DAOException, ParseException;
	public Map<String,Map<String,Map<Alarm, CommitMessage>>>  loadReadTeamProjectCommitMsgManagerRec(Map<String,Map<String,List<String>>> teamCooperCpChildren, String teamCode, String managerCode,String oriProCode) throws DAOException, ParseException;

	// 1-B.	Commit message received from team : New version of project
	public Map<String,Map<String,Map<GroupAlarm,CommitMessage>>> loadUnReadTeamProjectMsgTeamSend(Map<String,List<String>> teamOriProjectMap, String teamCooperatorCode ) throws DAOException, ParseException;
	public Map<String,Map<String,Map<GroupAlarm,CommitMessage>>> loadReadTeamProjectMsgTeamSend(Map<String,List<String>> teamOriProjectMap, String teamCooperatorCode ) throws DAOException, ParseException;
	public Map<String,Map<String,Map<GroupAlarm,CommitMessage>>> loadUnReadTeamProjectListMsgTeamSend(Map<String,List<String>> teamOriProjectMap) throws DAOException, ParseException;
	public Map<String,Map<String,Map<GroupAlarm,CommitMessage>>> loadReadTeamProjectListMsgTeamSend(Map<String,List<String>> teamOriProjectMap) throws DAOException, ParseException;
	public Map<String,Map<String,Map<GroupAlarm,CommitMessage>>> loadUnReadTeamProjectMsgTeamSend(String teamCode,String teamOriginProjectCode) throws DAOException, ParseException;
	public Map<String,Map<String,Map<GroupAlarm,CommitMessage>>> loadReadTeamProjectMsgTeamSend(String teamCode,String teamOriginProjectCode) throws DAOException, ParseException;

	//2.	If member is Team Cooperator
	// 2-A.	search commit message that member send to manager
	public Map<String,Map<String,Map<Alarm,CommitMessage>>> loadUnReadTeamListCommitMsgCooperSend(Map<String,Map<String,Map<String,List<String>>>> originTeamProChildren,String cooperatorCode) throws DAOException, ParseException;
	public Map<String,Map<String,Map<Alarm,CommitMessage>>> loadReadTeamListCommitMsgCooperSend(Map<String,Map<String,Map<String,List<String>>>> originTeamProChildren,String cooperatorCode) throws DAOException, ParseException;
	public Map<String,Map<String,Map<Alarm,CommitMessage>>> loadUnReadTeamProjectListCommitMsgCooperSend(Map<String,Map<String,List<String>>> originTeamProChildren,String teamCode,String cooperatorCode) throws DAOException, ParseException;
	public Map<String,Map<String,Map<Alarm,CommitMessage>>> loadReadTeamProjectListCommitMsgCooperSend(Map<String,Map<String,List<String>>> originTeamProChildren,String teamCode,String cooperatorCode) throws DAOException, ParseException;
	public Map<String,Map<String,Map<Alarm,CommitMessage>>> loadUnReadTeamProjectCommitMsgCooperSend(Map<String,List<String>> cooperCpChildren,String teamCode,String manager,String cooperatorCode, String oirprojectCode) throws DAOException, ParseException;
	public Map<String,Map<String,Map<Alarm,CommitMessage>>> loadReadTeamProjectCommitMsgCooperSend(Map<String,List<String>> cooperCpChildren,String teamCode,String manager,String cooperatorCode, String oirprojectCode) throws DAOException, ParseException;

	// 2-B.	search Team send to cooperator
	public Map<String,Map<String,Map<GroupAlarm,CommitMessage>>> loadUnReadTeamCommitGroupMsgCooperRec(Map<String,List<String>> oriTeamProChildren, String cooperatorCode) throws DAOException, ParseException;
	public Map<String,Map<String,Map<GroupAlarm,CommitMessage>>> loadReadTeamCommitGroupMsgCooperRec(Map<String,List<String>> oriTeamProChildren, String cooperatorCode)throws DAOException, ParseException;
	
	//3.	If Member is owner of personal Project 
	// 3-A.	Project Owner received Commit message from project coopers
	public Map<String,Map<Alarm,CommitMessage>> loadUnReadSharedProListCommitMsgOwnerRec(Map<String,Map<String,List<String>>> presonalCpProChildern,String ownerCode, String oriProCode) throws DAOException, ParseException;
	public Map<String,Map<Alarm,CommitMessage>> loadReadSharedProListCommitMsgOwnerRec(Map<String,Map<String,List<String>>> presonalCpProChildern,String ownerCode, String oriProCode) throws DAOException, ParseException;
	public Map<Alarm,CommitMessage> loadUnReadSharedProCommitMsgOwnerRec(Map<String,List<String>> presonalOriProChildern,String ownerCode) throws DAOException, ParseException;
	public Map<Alarm,CommitMessage> loadReadSharedProCommitMsgOwnerRec(Map<String,List<String>> presonalOriProChildern,String ownerCode) throws DAOException, ParseException;

	// 3-B.	Owner send Shared Project Cooperators
	public Map<String,Map<String,Map<GroupAlarm,CommitMessage>>> loadUnReadOriSharedProCommitGroupMsgOwnerSend (List<String> oriSharedProjectList, String ownerCode) throws DAOException, ParseException;
	public Map<String,Map<String,Map<GroupAlarm,CommitMessage>>> loadReadOriSharedProCommitGroupMsgOwnerSend(List<String> oriSharedProjectList, String ownerCode) throws DAOException, ParseException;
	
	//4.	If member is personal project¡¯s cooperator
	// 4-A.	Search cooperator send message to owner
	public Map<String,Map<Alarm,CommitMessage>> loadUnReadSharedProListCommitMsgCooperSend(Map<String,Map<String,Map<String,List<String>>>> cooperCpMap,String cooperatorCode) throws DAOException, ParseException;
	public Map<String,Map<Alarm,CommitMessage>> loadReadSharedProListCommitMsgCooperSend(Map<String,Map<String,Map<String,List<String>>>>  cooperCpMap, String cooperatorCode) throws DAOException, ParseException;
	public Map<Alarm,CommitMessage> loadUnReadSharedProCommitMsgCooperSend(Map<String,List<String>> sharedProjectChildMap,String ownerCode,String cooperatorCode) throws DAOException, ParseException;
	public Map<Alarm,CommitMessage> loadReadSharedProCommitMsgCooperSend(Map<String,List<String>>  sharedProjectChildMap,String ownerCode, String cooperatorCode) throws DAOException, ParseException;

	// 4-B.	Search owner send message to cooper
	public Map<String,Map<String,Map<GroupAlarm,CommitMessage>>> loadUnReadSharedProCommitGroupMsgCooperRec(Map<String,List<String>> sharedProjectMapPerOwner, String cooperatorCode) throws DAOException, ParseException;
	public Map<String,Map<String,Map<GroupAlarm,CommitMessage>>> loadReadSharedProCommitGroupMsgCooperRec(Map<String,List<String>> sharedProjectMapPerOwner, String cooperatorCode) throws DAOException, ParseException;
	
	public  Map<String,Map<Alarm,CommitMessage>> loadUnReadCooperTeamCommitMessageForTeam(Map<String,Map<String,List<String>>> teamOriProjectMap,String teamCode) throws DAOException, ParseException;
	public  Map<String,Map<Alarm,CommitMessage>> loadReadCooperTeamCommitMessageForTeam(Map<String,Map<String,List<String>>> teamOriProjectMap,String teamCode) throws DAOException, ParseException;
	
	public Map<Alarm,CommitMessage> loadUnReadCooperSharedProCommitMessageForProject(Map<String,List<String>> oriProjectMap) throws DAOException, ParseException;
	public Map<Alarm,CommitMessage> loadReadCooperSharedProCommitMessageForProject(Map<String,List<String>> oriProjectMap) throws DAOException, ParseException;
	
	public Map<String,Map<GroupAlarm,CommitMessage>> loadUnReadTeamCommitMessageForTeam(List<String> teamOriProjectList,String teamCode) throws DAOException, ParseException;
	public Map<String,Map<GroupAlarm,CommitMessage>> loadReadTeamCommitMessageForTeam(List<String> teamOriProjectList,String teamCode) throws DAOException, ParseException;
	
	public Map<GroupAlarm,CommitMessage> loadUnReadSharedProCommitMessageForProject(String oriProjectCode) throws DAOException, ParseException;
	public Map<GroupAlarm,CommitMessage> loadReadSharedProCommitMessageForProject(String oriProjectCode) throws DAOException, ParseException;
	
	
	/////////////////////////////////////////////////////
	
	public Map<String,Map<String,Map<Alarm,CommitMessage>>> searchUnReadTeamCommitMessage(String teamCode, Map<String,Map<String,List<String>>> oriProChildren, String managerCode, List<String> cooperCodeList) throws DAOException, ParseException;
	public Map<String,Map<String,Map<Alarm,CommitMessage>>> searchReadTeamCommitMessage(String teamCode, Map<String,Map<String,List<String>>> oriProChildren, String managerCode, List<String> cooperCodeList) throws DAOException, ParseException;

	public Map<String,Map<GroupAlarm,CommitMessage>> searchUnReadTeamGroupCommitMessage(String teamCode, List<String> oriProChildren, List<String> cooperList) throws DAOException, ParseException;
	public Map<String,Map<GroupAlarm,CommitMessage>> searchReadTeamGroupCommitMessage(String teamCode, List<String> oriProChildren, List<String> cooperList) throws DAOException, ParseException;

	public Map<Alarm,CommitMessage> searchReadCooperSharedProCommitMessage(String cooperatorCode, Map<String,List<String>> copiedProjectCode) throws DAOException, ParseException;
	public Map<Alarm,CommitMessage> searchUnReadCooperSharedProCommitMessage(String cooperatorCode, Map<String,List<String>> copiedProjectCode) throws DAOException, ParseException;

	public Map<String,Map<Alarm,CommitMessage>> searchReadSharedProCommitMessageOwnerRec(String ownerCode, Map<String,List<String>> oriProjectCode) throws DAOException, ParseException;
	public Map<String,Map<Alarm,CommitMessage>> searchUnReadSharedProCommitMessageOwnerRec(String ownerCode, Map<String,List<String>> oriProjectCode) throws DAOException, ParseException;

	////////////////////////////////////////////////////////////////////////////////
	
	public Map<Alarm,CommitMessage> searchCommitMessageCode(String msgCode) throws DAOException, ParseException;
	
}
