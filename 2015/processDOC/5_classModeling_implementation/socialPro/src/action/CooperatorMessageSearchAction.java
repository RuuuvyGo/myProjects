package action;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import model.Alarm;
import model.CooperatorMessage;
import socialProExceptions.DAOException;

public interface CooperatorMessageSearchAction {

	//1.	If member is Team Manager
	// 1-A.	cooperatorMessage manager send to join
	public Map<String,Map<String,Map<Alarm,CooperatorMessage>>> loadUnReadTeamCooperMsgManagerSend(List<String> teamCodeList, String managerCode) throws DAOException, ParseException;
	public Map<String,Map<String,Map<Alarm,CooperatorMessage>>> loadReadTeamCooperMsgManagerSend(List<String> teamCodeList, String managerCode) throws DAOException, ParseException;
	
	// 1-B.	cooperatorMessage manager receive from member
	public Map<String,Map<String,Map<Alarm, CooperatorMessage>>> loadUnReadTeamCooperMsgManagerRec(List<String> teamCodeList, String managerCode)throws DAOException, ParseException;
	public Map<String,Map<String,Map<Alarm, CooperatorMessage>>> loadReadTeamCooperMsgManagerRec(List<String> teamCodeList, String managerCode)throws DAOException, ParseException;

	// 2.	if member is cooperator of team
	// 2-A.	cooperator send message to team manager
	public Map<String,Map<String,Map<Alarm,CooperatorMessage>>> loadUnReadTeamCooperMsgMemSend(String senderCode)throws DAOException, ParseException;
	public Map<String,Map<String,Map<Alarm,CooperatorMessage>>> loadReadTeamCooperMsgMemSend(String senderCode)throws DAOException, ParseException;

	// 2-B.	Team manager send cooperator Message to join
	public Map<String,Map<String,Map<Alarm,CooperatorMessage>>> loadUnReadTeamCooperMsgMemRec(String receiverCode)throws DAOException, ParseException;
	public Map<String,Map<String,Map<Alarm,CooperatorMessage>>> loadReadTeamCooperMsgMemRec(String receiverCode)throws DAOException, ParseException;
	
	// 3.	If member is personal project Owner
	// 3-A.	Owner receive cooperator message from other member (share)
	public Map<String,Map<String,Map<Alarm,CooperatorMessage>>> loadUnReadProCooperMsgOwnerRec(List<String> oriProCodeList,String oriOwnerCode)throws DAOException, ParseException;
	public Map<String,Map<String,Map<Alarm,CooperatorMessage>>> loadReadProCooperMsgOwnerRec(List<String> oriProCodeList,String oriOwnerCode)throws DAOException, ParseException;
	
	
	//4.	If member is personal Project¡¯s cooperator
	// 4-A.	 Member want to be cooperator if personal project
	public Map<String,Map<String ,Map<Alarm,CooperatorMessage>>> loadUnReadProCooperMsgMemSend(List<String> oriProCodeList,String senderCode)throws DAOException, ParseException;
	public Map<String,Map<String ,Map<Alarm,CooperatorMessage>>> loadReadProCooperMsgMemSend(List<String> oriProCodeList,String senderCode)throws DAOException, ParseException;

	
	////////////////////////////////
	
	public Map<String,Map<Alarm, CooperatorMessage>> searchUnReadTeamCooperMsg(String teamCode,String managerCode,List<String> cooperCodeList)throws DAOException, ParseException;
	public Map<String,Map<Alarm, CooperatorMessage>> searchReadTeamCooperMsg(String teamCode,String managerCode,List<String> cooperCodeList)throws DAOException, ParseException;

}
