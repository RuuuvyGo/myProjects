package action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import manager.MemberManager;
import model.Alarm;
import model.CooperatorMessage;
import socialProExceptions.DAOException;
import socialProExceptions.MemberException;
import dao.AlarmDAO;
import dao.CooperatorMessageAlarmVDAO;
import dao.CooperatorMessageDAO;
import dao.DAO;
import dao.MessageDAO;
import dto.AlarmDTO;
import dto.CooperatorMessageAlarmVDTO;
import dto.CooperatorMessageDTO;
import dto.MessageDTO;
import factory.DAOFactory;

public class CooperatorMessageActionImpl extends BaseAction implements CooperatorMessageInsertAction,CooperatorMessageSearchAction,CooperatorMessageUpdateAction,CooperatorMessageDropAction{

	public CooperatorMessageActionImpl() {
		// TODO Auto-generated constructor stub
		this.setDAO((CooperatorMessageAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CooperatorMessageAlarmVDAO"));
	}
	@Override
	public void setDAO(DAO dao) {
		// TODO Auto-generated method stub
		this.dao = (CooperatorMessageAlarmVDAO)dao;
	}

	static int INVITE=0;
	static int DROPOUT=1;
///////////////////////////////////////////   search   ////////////////////////////////////////////////////////
	
//  +++++++++++++++++++++++++++         load		+++++++++++++++++++++++++++            	
	
	// 1.	If member is Team Manager
	// 1-A.	cooperatorMessage manager send to join
	@Override
	public Map<String, Map<String, Map<Alarm, CooperatorMessage>>> loadUnReadTeamCooperMsgManagerSend(List<String> teamCodeList, String managerCode) throws DAOException, ParseException {
		// TODO Auto-generated method stub
		
		//teamCode, memberCode
		Map<String, Map<String, Map<Alarm, CooperatorMessage>>> resMap = new HashMap<String, Map<String, Map<Alarm, CooperatorMessage>>>();
		
		for(String teamCode : teamCodeList){
			
			Map<String,Map<Alarm,CooperatorMessage>> mapVal1 = new HashMap<String, Map<Alarm,CooperatorMessage>>();
			
			List<CooperatorMessageAlarmVDTO> resVdtoList = ((CooperatorMessageAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CooperatorMessageAlarmVDAO")).searchUnReadSenderCode(managerCode, teamCode);
			if(!resVdtoList.isEmpty()){
				mapVal1 = this.makeModelTeamMap(resVdtoList);
				if(!mapVal1.isEmpty()) {resMap.put(teamCode, mapVal1);}
			}
		}
		
		return resMap;
	}


	@Override
	public Map<String, Map<String, Map<Alarm, CooperatorMessage>>> loadReadTeamCooperMsgManagerSend(List<String> teamCodeList, String managerCode) throws DAOException, ParseException {
		// TODO Auto-generated method stub

		//teamCode, memberCode
		Map<String, Map<String, Map<Alarm, CooperatorMessage>>> resMap = new HashMap<String, Map<String, Map<Alarm, CooperatorMessage>>>();
		
		for(String teamCode : teamCodeList){
			
			Map<String,Map<Alarm,CooperatorMessage>> mapVal1 = new HashMap<String, Map<Alarm,CooperatorMessage>>();
			
			List<CooperatorMessageAlarmVDTO> resVdtoList = ((CooperatorMessageAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CooperatorMessageAlarmVDAO")).searchReadSenderCode(managerCode, teamCode);
			if(!resVdtoList.isEmpty()){
				mapVal1 = this.makeModelTeamMap(resVdtoList);
				if(!mapVal1.isEmpty()) {resMap.put(teamCode, mapVal1);}
			}
		}
		
		return resMap;
	}

	
	// 1-B.	cooperatorMessage manager receive from member
	@Override
	public Map<String, Map<String, Map<Alarm, CooperatorMessage>>> loadUnReadTeamCooperMsgManagerRec(List<String> teamCodeList, String managerCode) throws DAOException,ParseException {
		// TODO Auto-generated method stub
		
		//teamCode, memberCode
		Map<String, Map<String, Map<Alarm, CooperatorMessage>>> resMap = new HashMap<String, Map<String, Map<Alarm, CooperatorMessage>>>();

		for(String teamCode : teamCodeList){
			
			Map<String,Map<Alarm,CooperatorMessage>> mapVal1 = new HashMap<String, Map<Alarm,CooperatorMessage>>();
			
			List<CooperatorMessageAlarmVDTO> resVdtoList = ((CooperatorMessageAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CooperatorMessageAlarmVDAO")).searchUnReadReceiverCode(managerCode, teamCode);
			if(!resVdtoList.isEmpty()){
				mapVal1 = this.makeModelTeamMap(resVdtoList);
				if(!mapVal1.isEmpty()) {resMap.put(teamCode, mapVal1);}
			}
		}
		
		return resMap;
	}


	@Override
	public Map<String, Map<String, Map<Alarm, CooperatorMessage>>> loadReadTeamCooperMsgManagerRec(
			List<String> teamCodeList, String managerCode) throws DAOException,
			ParseException {
		// TODO Auto-generated method stub

		//teamCode, memberCode
		Map<String, Map<String, Map<Alarm, CooperatorMessage>>> resMap = new HashMap<String, Map<String, Map<Alarm, CooperatorMessage>>>();

		for(String teamCode : teamCodeList){
			
			Map<String,Map<Alarm,CooperatorMessage>> mapVal1 = new HashMap<String, Map<Alarm,CooperatorMessage>>();
			
			List<CooperatorMessageAlarmVDTO> resVdtoList = ((CooperatorMessageAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CooperatorMessageAlarmVDAO")).searchReadReceiverCode(managerCode, teamCode);
			if(!resVdtoList.isEmpty()){
				mapVal1 = this.makeModelTeamMap(resVdtoList);
				if(!mapVal1.isEmpty()) {resMap.put(teamCode, mapVal1);}
			}
		}
		
		return resMap;
	}


	// 2.	if member is cooperator of team
	//2-A.	cooperator send message to team manager
	@Override
	public Map<String, Map<String, Map<Alarm, CooperatorMessage>>> loadUnReadTeamCooperMsgMemSend(String senderCode) throws DAOException, ParseException {
		// TODO Auto-generated method stub
		
		//TeamCode, managerCode
		Map<String, Map<String, Map<Alarm, CooperatorMessage>>> resMap = new HashMap<String, Map<String, Map<Alarm, CooperatorMessage>>>();
		
		List<CooperatorMessageAlarmVDTO> resVdtoList = ((CooperatorMessageAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CooperatorMessageAlarmVDAO")).searchUnReadSenderCodePro(senderCode);
		if(!resVdtoList.isEmpty()){
			
			for(CooperatorMessageAlarmVDTO cooperMessge : resVdtoList){

				Map<String,Map<Alarm,CooperatorMessage>> mapVal1 = this.makeModelTeamMap(cooperMessge);
				resMap.put(cooperMessge.getSourceCode(), mapVal1);
			}
		}
		
		return resMap;
	}


	@Override
	public Map<String, Map<String, Map<Alarm, CooperatorMessage>>> loadReadTeamCooperMsgMemSend(String senderCode) throws DAOException, ParseException {
		// TODO Auto-generated method stub

		//TeamCode, managerCode
		Map<String, Map<String, Map<Alarm, CooperatorMessage>>> resMap = new HashMap<String, Map<String, Map<Alarm, CooperatorMessage>>>();
		
		List<CooperatorMessageAlarmVDTO> resVdtoList = ((CooperatorMessageAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CooperatorMessageAlarmVDAO")).searchReadSenderCodePro(senderCode);
		if(!resVdtoList.isEmpty()){
			
			for(CooperatorMessageAlarmVDTO cooperMessge : resVdtoList){
				
				Map<String,Map<Alarm,CooperatorMessage>> mapVal1 = this.makeModelTeamMap(cooperMessge);
				resMap.put(cooperMessge.getSourceCode(), mapVal1);
			}
		}
		
		return resMap;
	}

	// 2-B.	Team manager send cooperator Message to join
	@Override
	public Map<String, Map<String, Map<Alarm, CooperatorMessage>>> loadUnReadTeamCooperMsgMemRec(String receiverCode) throws DAOException, ParseException {
		// TODO Auto-generated method stub
		
		//teamCode, ownerCode
		Map<String, Map<String, Map<Alarm, CooperatorMessage>>> resMap = new HashMap<String, Map<String, Map<Alarm, CooperatorMessage>>>();
		
		List<CooperatorMessageAlarmVDTO> resVdtoList = ((CooperatorMessageAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CooperatorMessageAlarmVDAO")).searchUnReadReceiverCodePro(receiverCode);
		if(!resVdtoList.isEmpty()){
			
			for(CooperatorMessageAlarmVDTO cooperMessge : resVdtoList){
				
				Map<String,Map<Alarm,CooperatorMessage>> mapVal1 = this.makeModelTeamMap(cooperMessge);
				resMap.put(cooperMessge.getSourceCode(), mapVal1);
			}
		}
		
		return resMap;
	}


	@Override
	public Map<String, Map<String, Map<Alarm, CooperatorMessage>>> loadReadTeamCooperMsgMemRec(String receiverCode) throws DAOException, ParseException {
		// TODO Auto-generated method stub

		//teamCode, ownerCode
		Map<String, Map<String, Map<Alarm, CooperatorMessage>>> resMap = new HashMap<String, Map<String, Map<Alarm, CooperatorMessage>>>();
		
		List<CooperatorMessageAlarmVDTO> resVdtoList = ((CooperatorMessageAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CooperatorMessageAlarmVDAO")).searchReadReceiverCodeTeam(receiverCode);
		if(!resVdtoList.isEmpty()){
			
			for(CooperatorMessageAlarmVDTO cooperMessge : resVdtoList){
				
				Map<String,Map<Alarm,CooperatorMessage>> mapVal1 = this.makeModelTeamMap(cooperMessge);
				resMap.put(cooperMessge.getSourceCode(), mapVal1);
			}
		}
		
		return resMap;
	}
	
	
	// 3.	If member is personal project Owner
	// 3-A.	Owner receive cooperator message from other member (share)
	@Override
	public Map<String, Map<String, Map<Alarm, CooperatorMessage>>> loadUnReadProCooperMsgOwnerRec(List<String> oriProCodeList,String oriOwnerCode) throws DAOException, ParseException {
		// TODO Auto-generated method stub
		
		// originProjectCode, cooperatorCode
		Map<String, Map<String, Map<Alarm, CooperatorMessage>>> resMap = new HashMap<String, Map<String, Map<Alarm, CooperatorMessage>>>();
		
		for(String oriProCode : oriProCodeList){
			
			List<CooperatorMessageAlarmVDTO> resVdtoList = ((CooperatorMessageAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CooperatorMessageAlarmVDAO")).searchUnReadReceiverCode(oriOwnerCode, oriProCode);
			if(!resVdtoList.isEmpty()){
				Map<String,Map<Alarm,CooperatorMessage>> tmpMap = this.makeModelTeamMap(resVdtoList);
				resMap.put(oriProCode, tmpMap);
			}
		}
		return resMap;
	}


	@Override
	public Map<String, Map<String, Map<Alarm, CooperatorMessage>>> loadReadProCooperMsgOwnerRec(List<String> oriProCodeList,String oriOwnerCode) throws DAOException, ParseException {
		// TODO Auto-generated method stub

		// originProjectCode, cooperatorCode
		Map<String, Map<String, Map<Alarm, CooperatorMessage>>> resMap = new HashMap<String, Map<String, Map<Alarm, CooperatorMessage>>>();
		
		for(String oriProCode : oriProCodeList){
			
			List<CooperatorMessageAlarmVDTO> resVdtoList = ((CooperatorMessageAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CooperatorMessageAlarmVDAO")).searchReadReceiverCode(oriOwnerCode, oriProCode);
			if(!resVdtoList.isEmpty()){
				Map<String,Map<Alarm,CooperatorMessage>> tmpMap = this.makeModelTeamMap(resVdtoList);
				resMap.put(oriProCode, tmpMap);
			}
		}
		return resMap;
	}
	
	
	//4.	If member is personal Project¡¯s cooperator
	// 4-A.	 Member want to be cooperator if personal project
	@Override
	public Map<String, Map<String, Map<Alarm, CooperatorMessage>>> loadUnReadProCooperMsgMemSend(List<String> oriProCodeList,String senderCode) throws DAOException, ParseException {
		// TODO Auto-generated method stub
		
		// OwnerCode,  projectCode
		Map<String, Map<String, Map<Alarm, CooperatorMessage>>> resMap = new HashMap<String, Map<String, Map<Alarm, CooperatorMessage>>>();
		
		List<CooperatorMessageAlarmVDTO> resVdtoList = ((CooperatorMessageAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CooperatorMessageAlarmVDAO")).searchUnReadSenderCode(senderCode, oriProCodeList);
		if(!resVdtoList.isEmpty()){
			Map<Alarm,CooperatorMessage> tmpMap = this.makeModelList(resVdtoList);
			if(!tmpMap.isEmpty()){
				for(Alarm alarm : tmpMap.keySet()){
					CooperatorMessage message = tmpMap.get(alarm);
					String oriOwnerCode = message.getReceiverCode();
					String oriProCode = message.getGroupCode();
					if(resMap.containsKey(oriOwnerCode)){
						if(resMap.get(oriOwnerCode).containsKey(oriProCode)){
							resMap.get(oriOwnerCode).get(oriProCode).put(alarm, message);
						}else{
							Map<Alarm,CooperatorMessage> mapVal2 = new HashMap<Alarm, CooperatorMessage>();
							mapVal2.put(alarm, message);
							resMap.get(oriOwnerCode).put(oriProCode, mapVal2);
						}
					}else{
						Map<String,Map<Alarm,CooperatorMessage>> mapVal1 = new HashMap<String, Map<Alarm,CooperatorMessage>>();
						Map<Alarm,CooperatorMessage> mapVal2 = new HashMap<Alarm, CooperatorMessage>();
						mapVal2.put(alarm, message);
						mapVal1.put(oriProCode, mapVal2);
						resMap.put(oriOwnerCode, mapVal1);
					}
				}
			}
		}
		
		return resMap;
	}


	@Override
	public Map<String, Map<String, Map<Alarm, CooperatorMessage>>> loadReadProCooperMsgMemSend(List<String> oriProCodeList,String senderCode) throws DAOException, ParseException {
		// TODO Auto-generated method stub

		// OwnerCode,  projectCode
		Map<String, Map<String, Map<Alarm, CooperatorMessage>>> resMap = new HashMap<String, Map<String, Map<Alarm, CooperatorMessage>>>();
		
		List<CooperatorMessageAlarmVDTO> resVdtoList = ((CooperatorMessageAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CooperatorMessageAlarmVDAO")).searchReadSenderCode(senderCode, oriProCodeList);
		if(!resVdtoList.isEmpty()){
			Map<Alarm,CooperatorMessage> tmpMap = this.makeModelList(resVdtoList);
			if(!tmpMap.isEmpty()){
				for(Alarm alarm : tmpMap.keySet()){
					CooperatorMessage message = tmpMap.get(alarm);
					String oriOwnerCode = message.getReceiverCode();
					String oriProCode = message.getGroupCode();
					if(resMap.containsKey(oriOwnerCode)){
						if(resMap.get(oriOwnerCode).containsKey(oriProCode)){
							resMap.get(oriOwnerCode).get(oriProCode).put(alarm, message);
						}else{
							Map<Alarm,CooperatorMessage> mapVal2 = new HashMap<Alarm, CooperatorMessage>();
							mapVal2.put(alarm, message);
							resMap.get(oriOwnerCode).put(oriProCode, mapVal2);
						}
					}else{
						Map<String,Map<Alarm,CooperatorMessage>> mapVal1 = new HashMap<String, Map<Alarm,CooperatorMessage>>();
						Map<Alarm,CooperatorMessage> mapVal2 = new HashMap<Alarm, CooperatorMessage>();
						mapVal2.put(alarm, message);
						mapVal1.put(oriProCode, mapVal2);
						resMap.put(oriOwnerCode, mapVal1);
					}
				}
			}
		}
		
		return resMap;
	}
	
//  +++++++++++++++++++++++++++         load end		+++++++++++++++++++++++++++  
	@Override
	public Map<String, Map<Alarm, CooperatorMessage>> searchUnReadTeamCooperMsg(String teamCode, String managerCode, List<String> cooperCodeList)throws DAOException, ParseException {
		// TODO Auto-generated method stub
		// cooperCode
		Map<String, Map<Alarm, CooperatorMessage>> resMap = new HashMap<String, Map<Alarm, CooperatorMessage>>();
		
		for(String cooperCode : cooperCodeList){
			
			List<CooperatorMessageAlarmVDTO> resVdtoList = ((CooperatorMessageAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CooperatorMessageAlarmVDAO")).searchUnReadSendRec(managerCode, cooperCode, teamCode);
			if(!resVdtoList.isEmpty()){
				Map<Alarm,CooperatorMessage> tmpMap = this.makeModelList(resVdtoList);
				resMap.put(cooperCode, tmpMap);
			}
		}
		return resMap;
	}


	@Override
	public Map<String, Map<Alarm, CooperatorMessage>> searchReadTeamCooperMsg(String teamCode, String managerCode, List<String> cooperCodeList)throws DAOException, ParseException {
		// TODO Auto-generated method stub
		// cooperCode
		Map<String, Map<Alarm, CooperatorMessage>> resMap = new HashMap<String, Map<Alarm, CooperatorMessage>>();
		
		for(String cooperCode : cooperCodeList){
			
			List<CooperatorMessageAlarmVDTO> resVdtoList = ((CooperatorMessageAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CooperatorMessageAlarmVDAO")).searchReadSendRec(managerCode, cooperCode, teamCode);
			if(!resVdtoList.isEmpty()){
				Map<Alarm,CooperatorMessage> tmpMap = this.makeModelList(resVdtoList);
				resMap.put(cooperCode, tmpMap);
			}
		}
		return resMap;
	}
	
////////////////////////////////////////////////          insert              ////////////////////////////////////////////////
	

	@Override
	public Map<Alarm, CooperatorMessage> insertTeamCooperatorInviteMsg(String teamCode,String teamName,String teamManagerCode, List<String> cooperList) throws DAOException, MemberException, ParseException {
		// TODO Auto-generated method stub
		
		String senderNick = MemberManager.getINSTANCE().searchMemberCode(teamManagerCode).getNickName();
		List<CooperatorMessageAlarmVDTO> tmpList = new ArrayList<CooperatorMessageAlarmVDTO>();
		for(String cooperCode : cooperList){
			
			//insert data in message_tb
			String receiverNick = MemberManager.getINSTANCE().searchMemberCode(cooperCode).getNickName();
			String title="Invited from "+teamName,content=senderNick+" (teamManager) invite "+receiverNick+" to be a cooperator of "+teamName+" (teamName).";
			GregorianCalendar date = new GregorianCalendar();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			df.setTimeZone(date.getTimeZone());
			String today = df.format(date.getTime());
			String msgCode = ((MessageDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("MessageDAO")).insertMesage(new MessageDTO(teamManagerCode, cooperCode, today, title, content));
			
			//insert data in cooperatorMessage_tb
			((CooperatorMessageDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CooperatorMessageDAO")).insertCooperatorMessage(new CooperatorMessageDTO(msgCode, INVITE, teamCode));
			
			//insert data in alarm_tb
			String alarmCode = ((AlarmDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("alarmDAO")).insertAlarm(new AlarmDTO(msgCode));
			
			CooperatorMessageAlarmVDTO tmp = new CooperatorMessageAlarmVDTO(msgCode, teamManagerCode, cooperCode, title, content, today, INVITE, teamCode, alarmCode, null);
			tmpList.add(tmp);
		}
		
		return this.makeModelList(tmpList);
	}


	@Override
	public Map<Alarm, CooperatorMessage> insertProjectCooperatorMsg(String oriProCode,String oriProName,
			String oriProOwnerCode,String cooperCode, String cooperNick) throws DAOException,MemberException, ParseException {
		// TODO Auto-generated method stub

		//insert data in message_tb
		String title="share "+oriProName,content=cooperNick+" wants to share "+oriProName+"(projectName).";
		GregorianCalendar date = new GregorianCalendar();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		df.setTimeZone(date.getTimeZone());
		String today = df.format(date.getTime());
		String msgCode = ((MessageDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("MessageDAO")).insertMesage(new MessageDTO(cooperCode,oriProOwnerCode, today, title, content));
		
		//insert data in cooperatorMessage_tb
		((CooperatorMessageDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CooperatorMessageDAO")).insertCooperatorMessage(new CooperatorMessageDTO(msgCode, INVITE, oriProCode));
		
		//insert data in alarm_tb
		String alarmCode = ((AlarmDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("alarmDAO")).insertAlarm(new AlarmDTO(msgCode));
		
		CooperatorMessageAlarmVDTO tmp = new CooperatorMessageAlarmVDTO(msgCode, cooperCode, oriProOwnerCode,  title, content, today, INVITE, oriProCode, alarmCode, null);
		return this.makeModel(tmp);
	}
	@Override
	public Map<Alarm, CooperatorMessage> insertProjectCooperatorMsgSendToOwner(String oriProCode,String oriProName,
			String oriProOwnerCode,String cooperCode, String cooperNick) throws DAOException,MemberException, ParseException {
		// TODO Auto-generated method stub

		//insert data in message_tb
		String title="share "+oriProName,content=cooperNick+" wants to share "+oriProName+"(projectName).";
		GregorianCalendar date = new GregorianCalendar();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		df.setTimeZone(date.getTimeZone());
		String today = df.format(date.getTime());
		String msgCode = ((MessageDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("MessageDAO")).insertMesage(new MessageDTO(cooperCode,oriProOwnerCode, today, title, content));
		
		//insert data in cooperatorMessage_tb
		((CooperatorMessageDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CooperatorMessageDAO")).insertCooperatorMessage(new CooperatorMessageDTO(msgCode, INVITE, oriProCode));
		
		//insert data in alarm_tb
		String alarmCode = ((AlarmDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("alarmDAO")).insertAlarm(new AlarmDTO(msgCode));
		
		CooperatorMessageAlarmVDTO tmp = new CooperatorMessageAlarmVDTO(msgCode, oriProOwnerCode, cooperCode, title, content, today, INVITE, oriProCode, alarmCode, null);
		return this.makeModel(tmp);
	}
	
	
/////////////////////////////////////////////////////   update   /////////////////////////////////
	
	
/////////////////////////////////////////////   end   /////////////////////////////////////////////////////////////////	
	
	private Map<String,Map<Alarm,CooperatorMessage>> makeModelTeamMap(List<CooperatorMessageAlarmVDTO> vdtoList) throws ParseException{
		
		Map<String,Map<Alarm,CooperatorMessage>> resMap = new HashMap<String,Map<Alarm,CooperatorMessage>>();
		
		for(CooperatorMessageAlarmVDTO vdto : vdtoList){
			
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date date = df.parse(vdto.getSendDate());
			GregorianCalendar cal1 = (GregorianCalendar)Calendar.getInstance();
			cal1.setTime(date);
			
			DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			GregorianCalendar cal2=null;
			if(vdto.getCheckDate()!=null){
				Date date2 = df2.parse(vdto.getCheckDate());
				cal2 = (GregorianCalendar)Calendar.getInstance();
				cal2.setTime(date2);
			}
			Alarm alarm = new Alarm(vdto.getAlarmCode(), vdto.getMessageCode(), cal2);
			CooperatorMessage message = new CooperatorMessage(vdto.getMessageCode(), vdto.getTitle(), vdto.getContent(), vdto.getSenderCode(), vdto.getReceiverCode(), cal1, vdto.getSourceCode(), vdto.getType());
			String receiver = message.getReceiverCode();
			
			if(resMap.containsKey(receiver)){
				resMap.get(receiver).put(alarm, message);
			}else{
				Map<Alarm,CooperatorMessage> mapVal1 = new HashMap<Alarm,CooperatorMessage>();
				mapVal1.put(alarm, message);
				resMap.put(receiver, mapVal1);
			}
		}
		
		return resMap;
	}
	
	private Map<String,Map<Alarm,CooperatorMessage>> makeModelTeamMap(CooperatorMessageAlarmVDTO vdto) throws ParseException{
		
		Map<String,Map<Alarm,CooperatorMessage>> resMap = new HashMap<String,Map<Alarm,CooperatorMessage>>();
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date date = df.parse(vdto.getSendDate());
		GregorianCalendar cal1 = (GregorianCalendar)Calendar.getInstance();
		cal1.setTime(date);
		
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		GregorianCalendar cal2=null;
		if(vdto.getCheckDate()!=null){
			Date date2 = df2.parse(vdto.getCheckDate());
			cal2 = (GregorianCalendar)Calendar.getInstance();
			cal2.setTime(date2);
		}
		Alarm alarm = new Alarm(vdto.getAlarmCode(), vdto.getMessageCode(), cal2);
		CooperatorMessage message = new CooperatorMessage(vdto.getMessageCode(), vdto.getTitle(), vdto.getContent(), vdto.getSenderCode(), vdto.getReceiverCode(), cal1, vdto.getSourceCode(), vdto.getType());
		String receiver = message.getReceiverCode();
		
		Map<Alarm,CooperatorMessage> mapVal1 = new HashMap<Alarm,CooperatorMessage>();
		mapVal1.put(alarm, message);
		resMap.put(receiver, mapVal1);
		
		return resMap;
	}
	
	private Map<Alarm,CooperatorMessage> makeModelList(List<CooperatorMessageAlarmVDTO> vdtoList) throws ParseException{
		
		Map<Alarm,CooperatorMessage> resList = new HashMap<Alarm,CooperatorMessage>();
		
		for(CooperatorMessageAlarmVDTO vdto : vdtoList){
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date date = df.parse(vdto.getSendDate());
			GregorianCalendar cal1 = (GregorianCalendar)Calendar.getInstance();
			cal1.setTime(date);
			
			DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			GregorianCalendar cal2=null;
			if(vdto.getCheckDate()!=null){
				Date date2 = df2.parse(vdto.getCheckDate());
				cal2 = (GregorianCalendar)Calendar.getInstance();
				cal2.setTime(date2);
			}
			resList.put(new Alarm(vdto.getAlarmCode(), vdto.getMessageCode(), cal2),new CooperatorMessage(vdto.getMessageCode(), vdto.getTitle(), vdto.getContent(), vdto.getSenderCode(), vdto.getReceiverCode(), cal1, vdto.getSourceCode(), vdto.getType()));
		}
		
		return resList;
	}

	private Map<Alarm,CooperatorMessage> makeModel(CooperatorMessageAlarmVDTO vdto) throws ParseException{
		
		Map<Alarm,CooperatorMessage> resList = new HashMap<Alarm,CooperatorMessage>();
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date date = df.parse(vdto.getSendDate());
		GregorianCalendar cal1 = (GregorianCalendar)Calendar.getInstance();
		cal1.setTime(date);
		
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		GregorianCalendar cal2=null;
		if(vdto.getCheckDate()!=null){
			Date date2 = df2.parse(vdto.getCheckDate());
			cal2 = (GregorianCalendar)Calendar.getInstance();
			cal2.setTime(date2);
		}
		resList.put(new Alarm(vdto.getAlarmCode(), vdto.getMessageCode(), cal2),new CooperatorMessage(vdto.getMessageCode(), vdto.getTitle(), vdto.getContent(), vdto.getSenderCode(), vdto.getReceiverCode(), cal1, vdto.getSourceCode(), vdto.getType()));
		
		return resList;
	}
}
