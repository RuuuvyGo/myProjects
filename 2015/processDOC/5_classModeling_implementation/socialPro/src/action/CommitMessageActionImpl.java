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

import model.Alarm;
import model.CommitMessage;
import model.GroupAlarm;
import socialProExceptions.DAOException;
import dao.AlarmDAO;
import dao.CommitDAO;
import dao.CommitMessageDAO;
import dao.CommitMessageVDAO;
import dao.CopiedFileCommitAlarmVDAO;
import dao.CopiedFolderCommitAlarmVDAO;
import dao.DAO;
import dao.FileCommitGroupAlarmVDAO;
import dao.FolderCommitGroupAlarmVDAO;
import dao.GroupAlarmDAO;
import dao.MessageDAO;
import dao.OriginFileCommitAlarmVDAO;
import dao.OriginFolderCommitAlarmVDAO;
import dto.AlarmDTO;
import dto.CommitMessageDTO;
import dto.CopiedFileCommitAlarmVDTO;
import dto.CopiedFolderCommitAlarmVDTO;
import dto.FileCommitGroupAlarmVDTO;
import dto.FolderCommitGroupAlarmVDTO;
import dto.GroupAlarmDTO;
import dto.MessageDTO;
import dto.OriginFileCommitAlarmVDTO;
import dto.OriginFolderCommitAlarmVDTO;
import factory.DAOFactory;

public class CommitMessageActionImpl extends BaseAction implements CommitMessageSearchAction,CommitMessageInsertAction,CommitMessageDropAction,CommitMessageUpdateAction{

	private static int CREATE=0,MODIFY=1,DELETE=2;

	@Override
	public void setDAO(DAO dao) {
		// TODO Auto-generated method stub
		this.dao = (CommitMessageVDAO)dao;
	}

////////////////////////////          search             ///////////////////////////////////////////
	
////////////////////////////             load            ///////////////////////////////////////////
	
	// 1-A.	Commit message received from cooperator
	@Override
	public Map<String,Map<String,Map<Alarm, CommitMessage>>> loadUnReadTeamListCommitMsgManagerRec(Map<String, Map<String, Map<String, List<String>>>> teamOriginProChildren,String managerCode) throws DAOException, ParseException {
		// TODO Auto-generated method stub
		
		//return : teamCode, cooperCode, origin ProjectCode
		Map<String,Map<String,Map<Alarm, CommitMessage>>> resMap = new HashMap<String,Map<String,Map<Alarm, CommitMessage>>>();
		
		for(String teamCode : teamOriginProChildren.keySet()){

			Map<String, Map<Alarm, CommitMessage>> mapVal1 = new HashMap<String, Map<Alarm, CommitMessage>>();
			
			for(String oriProCode : teamOriginProChildren.get(teamCode).keySet()){
				
				Map<Alarm, CommitMessage> mapVal2 = new HashMap<Alarm, CommitMessage>();
				
				Map<String, List<String>> oriProChildrenMap= teamOriginProChildren.get(teamCode).get(oriProCode);
				
				if(!oriProChildrenMap.get("folder").isEmpty()){
					//compare folder
					List<String> oirFolderCodeList = oriProChildrenMap.get("folder");
					if(!oirFolderCodeList.isEmpty()){
						List<CopiedFolderCommitAlarmVDTO> cpFolderResList= ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchUnReadReceiverCodeOr(managerCode, oirFolderCodeList);
						if(!cpFolderResList.isEmpty()){
							Map<Alarm, CommitMessage> tmpVal = this.makeModelMFolderListForCpPro(cpFolderResList);
							if(!tmpVal.isEmpty()) mapVal2.putAll(tmpVal);
						}
					}
				}
				if(!oriProChildrenMap.get("file").isEmpty()){
					//compare file
					List<String> fileCodeList = oriProChildrenMap.get("file");
					if(!fileCodeList.isEmpty()){
						List<CopiedFileCommitAlarmVDTO> cpFileResList= ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchUnReadReceiverCodeOr(managerCode, fileCodeList);
						if(!cpFileResList.isEmpty()){
							Map<Alarm, CommitMessage> tmpVal = this.makeModelMFileListForCpPro(cpFileResList);
							if(!tmpVal.isEmpty()) mapVal2.putAll(tmpVal);
						}
					}
				}
				
				if(!mapVal2.isEmpty())mapVal1.put(oriProCode, mapVal2);
			}
			if(!mapVal1.isEmpty())resMap.put(teamCode, mapVal1);
		}
		if(resMap.isEmpty())System.out.println("resMap is Empty....");
		else System.out.println("resMap is not Empty.....");
		return resMap;
	}

	@Override
	public Map<String, Map<String,Map<Alarm, CommitMessage>>> loadReadTeamListCommitMsgManagerRec(Map<String, Map<String, Map<String, List<String>>>> teamOriginProChildren,String managerCode) throws DAOException, ParseException {
		// TODO Auto-generated method stub
		//return : teamCode, cooperCode, origin ProjectCode

		//return : teamCode, cooperCode, origin ProjectCode
		Map<String,Map<String,Map<Alarm, CommitMessage>>> resMap = new HashMap<String,Map<String,Map<Alarm, CommitMessage>>>();
		
		for(String teamCode : teamOriginProChildren.keySet()){

			Map<String, Map<Alarm, CommitMessage>> mapVal1 = new HashMap<String, Map<Alarm, CommitMessage>>();
			
			for(String oriProCode : teamOriginProChildren.get(teamCode).keySet()){
				
				Map<Alarm, CommitMessage> mapVal2 = new HashMap<Alarm, CommitMessage>();
				
				//compare folder
				Map<String, List<String>> oriProChildrenMap= teamOriginProChildren.get(teamCode).get(oriProCode);

				if(!oriProChildrenMap.get("folder").isEmpty()){
					//compare folder
					List<String> oirFolderCodeList = oriProChildrenMap.get("folder");
					if(!oirFolderCodeList.isEmpty()){
						List<CopiedFolderCommitAlarmVDTO> cpFolderResList= ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchReadReceiverCodeOr(managerCode, oirFolderCodeList);
						if(!cpFolderResList.isEmpty()){
							Map<Alarm, CommitMessage> tmpVal = this.makeModelMFolderListForCpPro(cpFolderResList);
							if(!tmpVal.isEmpty()) mapVal2.putAll(tmpVal);
						}
					}
				}
				if(!oriProChildrenMap.get("file").isEmpty()){
					//compare file
					List<String> fileCodeList = oriProChildrenMap.get("file");
					if(!fileCodeList.isEmpty()){
						List<CopiedFileCommitAlarmVDTO> cpFileResList= ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchReadReceiverCodeOr(managerCode, fileCodeList);
						if(!cpFileResList.isEmpty()){
							Map<Alarm, CommitMessage> tmpVal = this.makeModelMFileListForCpPro(cpFileResList);
							if(!tmpVal.isEmpty()) mapVal2.putAll(tmpVal);
						}
					}
				}
				
				if(!mapVal2.isEmpty())mapVal1.put(oriProCode, mapVal2);
			}
			if(!mapVal1.isEmpty())resMap.put(teamCode, mapVal1);
		}
		if(resMap.isEmpty())System.out.println("db resMap is Empty!!!");
		else System.out.println("db resMap size : "+resMap.size());
		return resMap;
	}

	@Override
	public Map<String,Map<String,Map<Alarm, CommitMessage>>>  loadUnReadTeamProjectListCommitMsgManagerRec(Map<String,Map<String,List<String>>> teamOriginProChildren, String teamCode, String managerCode) throws DAOException, ParseException{

		//return : teamCode, cooperCode, origin ProjectCode
		Map<String,Map<String,Map<Alarm, CommitMessage>>> resMap = new HashMap<String,Map<String,Map<Alarm, CommitMessage>>>();
		Map<String, Map<Alarm, CommitMessage>> mapVal1 = new HashMap<String, Map<Alarm, CommitMessage>>();
		for(String oriProCode : teamOriginProChildren.keySet()){
			
			Map<Alarm, CommitMessage> mapVal2 = new HashMap<Alarm, CommitMessage>();
			
			Map<String, List<String>> oriProChildrenMap= teamOriginProChildren.get(oriProCode);
			
			if(!oriProChildrenMap.get("folder").isEmpty()){
				//compare folder
				//for manager
				List<String> folderCodeList = oriProChildrenMap.get("folder");
				if(!folderCodeList.isEmpty()){
					List<OriginFolderCommitAlarmVDTO> oriFolderResList= ((OriginFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFolderCommitAlarmVDAO")).searchUnReadReceiverCodeOr(managerCode, folderCodeList);
					if(!oriFolderResList.isEmpty()){
						Map<Alarm, CommitMessage> tmpVal = this.makeModelMFolderListForPro(oriFolderResList);
						if(!tmpVal.isEmpty()) mapVal2.putAll(tmpVal);
					}
				}
			}
			if(!oriProChildrenMap.get("file").isEmpty()){
				//compare file
				List<String> fileCodeList = oriProChildrenMap.get("file");
				if(!fileCodeList.isEmpty()){
					List<OriginFileCommitAlarmVDTO> fileResList= ((OriginFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFileCommitAlarmVDAO")).searchUnReadReceiverCodeOr(managerCode, fileCodeList);
					if(!fileResList.isEmpty()){
						Map<Alarm, CommitMessage> tmpVal = this.makeModelMFileListForPro(fileResList);
						if(!tmpVal.isEmpty()) mapVal2.putAll(tmpVal);
					}
				}
			}
			
			if(!mapVal2.isEmpty())mapVal1.put(oriProCode, mapVal2);
		}
		if(!mapVal1.isEmpty())resMap.put(teamCode, mapVal1);
		
		if(resMap.isEmpty())System.out.println("resMap is Empty....");
		else System.out.println("resMap is not Empty.....");
		return resMap;
	}
	
	@Override
	public Map<String,Map<String,Map<Alarm, CommitMessage>>>  loadReadTeamProjectListCommitMsgManagerRec(Map<String,Map<String,List<String>>> teamOriginProChildren, String teamCode, String managerCode) throws DAOException, ParseException{

		//return : teamCode, cooperCode, origin ProjectCode
		Map<String,Map<String,Map<Alarm, CommitMessage>>> resMap = new HashMap<String,Map<String,Map<Alarm, CommitMessage>>>();
		Map<String, Map<Alarm, CommitMessage>> mapVal1 = new HashMap<String, Map<Alarm, CommitMessage>>();
		for(String oriProCode : teamOriginProChildren.keySet()){
			
			Map<Alarm, CommitMessage> mapVal2 = new HashMap<Alarm, CommitMessage>();
			
			Map<String, List<String>> oriProChildrenMap= teamOriginProChildren.get(oriProCode);
			List<String> folderCodeList = oriProChildrenMap.get("folder");
			List<String> fileCodeList = oriProChildrenMap.get("file");
			
			if(!folderCodeList.isEmpty()){
				//compare folder
				//only for manager
				if(!folderCodeList.isEmpty()){
					List<OriginFolderCommitAlarmVDTO> oriFolderResList= ((OriginFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFolderCommitAlarmVDAO")).searchReadReceiverCodeOr(managerCode, folderCodeList);
					if(!oriFolderResList.isEmpty()){
						Map<Alarm, CommitMessage> tmpVal = this.makeModelMFolderListForPro(oriFolderResList);
						if(!tmpVal.isEmpty()) mapVal2.putAll(tmpVal);
					}
				}
			}

			//compare file
			if(!fileCodeList.isEmpty()){
				
				List<OriginFileCommitAlarmVDTO> oirFileResList= ((OriginFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFileCommitAlarmVDAO")).searchReadReceiverCodeOr(managerCode, fileCodeList);
				if(!oirFileResList.isEmpty()){
					Map<Alarm, CommitMessage> tmpVal = this.makeModelMFileListForPro(oirFileResList);
					if(!tmpVal.isEmpty()) mapVal2.putAll(tmpVal);
				}
			}
			
			
			if(!mapVal2.isEmpty())mapVal1.put(oriProCode, mapVal2);
		}
		if(!mapVal1.isEmpty())resMap.put(teamCode, mapVal1);
		
		if(resMap.isEmpty())System.out.println("resMap is Empty....");
		else System.out.println("resMap is not Empty.....");
		return resMap;
	}

	@Override
	public Map<String,Map<String,Map<Alarm, CommitMessage>>>  loadUnReadTeamProjectCommitMsgManagerRec(Map<String,Map<String,List<String>>> teamCooperCpChildren, String teamCode,String managerCode,String oriProCode) throws DAOException, ParseException{

		//return : teamCode, origin ProjectCode
		Map<String,Map<String,Map<Alarm, CommitMessage>>> resMap = new HashMap<String,Map<String,Map<Alarm, CommitMessage>>>();
		Map<String, Map<Alarm, CommitMessage>> mapVal1 = new HashMap<String, Map<Alarm, CommitMessage>>();
		Map<Alarm, CommitMessage> mapVal2 = new HashMap<Alarm, CommitMessage>();

		for(String cooperCode : teamCooperCpChildren.keySet()){
			Map<String,List<String>> cooperCpChildren = teamCooperCpChildren.get(cooperCode);
			List<String> cpFileList = cooperCpChildren.get("file");
			List<String> cpFolderList = cooperCpChildren.get("folder");

			List<CopiedFolderCommitAlarmVDTO> cpFolderResList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
			List<OriginFolderCommitAlarmVDTO> oriFolderResList = new ArrayList<OriginFolderCommitAlarmVDTO>();
			if(!cpFolderList.isEmpty()){
				//for maanger
				cpFolderResList = ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchUnReadSendAndRecCp(cooperCode, managerCode, cpFolderList);
				oriFolderResList = ((OriginFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFolderCommitAlarmVDAO")).searchUnReadSendAndRecOr(cooperCode, managerCode, cpFolderList);
			}
			List<CopiedFileCommitAlarmVDTO> cpFileResList = new ArrayList<CopiedFileCommitAlarmVDTO>();
			List<OriginFileCommitAlarmVDTO> oriFileResList = new ArrayList<OriginFileCommitAlarmVDTO>();
			if(!cpFileList.isEmpty()){
				//for maanger
				cpFileResList = ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchUnReadSendAndRecCp(cooperCode, managerCode, cpFileList);
				oriFileResList = ((OriginFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFileCommitAlarmVDAO")).searchUnReadSendAndRecOr(cooperCode, managerCode, cpFileList);
			}
			mapVal2 = this.makeModelMsgMapForCpPro(cpFolderResList, oriFolderResList, cpFileResList, oriFileResList);
		}
		if(!mapVal2.isEmpty())mapVal1.put(oriProCode, mapVal2);
		
		if(!mapVal1.isEmpty())resMap.put(teamCode, mapVal1);
		
		if(resMap.isEmpty())System.out.println("resMap is Empty....");
		else System.out.println("resMap is not Empty.....");
		return resMap;
	}
	
	@Override
	public Map<String,Map<String,Map<Alarm, CommitMessage>>>  loadReadTeamProjectCommitMsgManagerRec(Map<String,Map<String,List<String>>> teamCooperCpChildren, String teamCode, String managerCode,String oriProCode) throws DAOException, ParseException{

		//return : teamCode, origin ProjectCode
		Map<String,Map<String,Map<Alarm, CommitMessage>>> resMap = new HashMap<String,Map<String,Map<Alarm, CommitMessage>>>();
		Map<String, Map<Alarm, CommitMessage>> mapVal1 = new HashMap<String, Map<Alarm, CommitMessage>>();
		Map<Alarm, CommitMessage> mapVal2 = new HashMap<Alarm, CommitMessage>();

		for(String cooperCode : teamCooperCpChildren.keySet()){
			Map<String,List<String>> cooperCpChildren = teamCooperCpChildren.get(cooperCode);
			List<String> cpFileList = cooperCpChildren.get("file");
			List<String> cpFolderList = cooperCpChildren.get("folder");
			
			List<CopiedFolderCommitAlarmVDTO> cpFolderResList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
			List<OriginFolderCommitAlarmVDTO> oriFolderResList = new ArrayList<OriginFolderCommitAlarmVDTO>();
			if(!cpFolderList.isEmpty()){
				//for maanger
				cpFolderResList = ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchReadSendAndRecCp(cooperCode, managerCode, cpFolderList);
				oriFolderResList = ((OriginFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFolderCommitAlarmVDAO")).searchReadSendAndRecOr(cooperCode, managerCode, cpFolderList);
			}
			List<CopiedFileCommitAlarmVDTO> cpFileResList = new ArrayList<CopiedFileCommitAlarmVDTO>();
			List<OriginFileCommitAlarmVDTO> oriFileResList = new ArrayList<OriginFileCommitAlarmVDTO>();
			if(!cpFileList.isEmpty()){
				//for maanger
				cpFileResList = ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchReadSendAndRecCp(cooperCode, managerCode, cpFileList);
				oriFileResList = ((OriginFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFileCommitAlarmVDAO")).searchReadSendAndRecOr(cooperCode, managerCode, cpFileList);
			}
			mapVal2 = this.makeModelMsgMapForCpPro(cpFolderResList, oriFolderResList, cpFileResList, oriFileResList);
		}
		if(!mapVal2.isEmpty())mapVal1.put(oriProCode, mapVal2);
		
		if(!mapVal1.isEmpty())resMap.put(teamCode, mapVal1);
		
		if(resMap.isEmpty())System.out.println("resMap is Empty....");
		else System.out.println("resMap is not Empty.....");
		return resMap;
	}

	
	// 1-B.	Commit message received from team : New version of project
	@Override
	public Map<String,Map<String, Map<GroupAlarm,CommitMessage>>> loadUnReadTeamProjectMsgTeamSend(Map<String, List<String>> teamOriProjectMap,
			String teamCooperatorCode) throws DAOException, ParseException {
		// TODO Auto-generated method stub
		
		// teamCode,projectCode, 
		Map<String,Map<String, Map<GroupAlarm,CommitMessage>>> resMap = new HashMap<String,Map<String, Map<GroupAlarm,CommitMessage>>>();
		
		for(String teamCode : teamOriProjectMap.keySet()){
			
			Map<String, Map<GroupAlarm,CommitMessage>> mapVal1 = new HashMap<String, Map<GroupAlarm,CommitMessage>>();
			
			for(String oriProjectCode : teamOriProjectMap.get(teamCode)){
				
				Map<GroupAlarm,CommitMessage> mapVal2 = new HashMap<GroupAlarm,CommitMessage>();
				
				List<FolderCommitGroupAlarmVDTO> resFolderList= ((FolderCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FolderCommitGroupAlarmVDAO")).searchUnReadSendAnRec(teamCode, teamCooperatorCode, oriProjectCode);
				if(!resFolderList.isEmpty()){
					Map<GroupAlarm,CommitMessage> tmpMap= this.makeModelFolderListForPro(resFolderList);
					if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
				}

				List<FileCommitGroupAlarmVDTO> resFileList= ((FileCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FileCommitGroupAlarmVDAO")).searchUnReadSendAnRec(teamCode, teamCooperatorCode, oriProjectCode);
				if(!resFileList.isEmpty()){
					Map<GroupAlarm,CommitMessage> tmpMap= this.makeModelFileListForPro(resFileList);
					if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
				}
				
				if(!mapVal2.isEmpty())mapVal1.put(oriProjectCode, mapVal2);
			}
			if(!mapVal1.isEmpty())resMap.put(teamCode, mapVal1);
		}
		if(resMap.isEmpty())System.out.println("db resMap is Empty!!!");
		else System.out.println("db resMap Size   ::  "+resMap.size());
		return resMap;
	}

	@Override
	public Map<String,Map<String, Map<GroupAlarm,CommitMessage>>> loadReadTeamProjectMsgTeamSend(Map<String, List<String>> teamOriProjectMap,String teamCooperatorCode)  throws DAOException, ParseException{
		// TODO Auto-generated method stub

		// teamCode,projectCode, 
		Map<String,Map<String, Map<GroupAlarm,CommitMessage>>> resMap = new HashMap<String,Map<String, Map<GroupAlarm,CommitMessage>>>();
		
		for(String teamCode : teamOriProjectMap.keySet()){
			
			Map<String, Map<GroupAlarm,CommitMessage>> mapVal1 = new HashMap<String, Map<GroupAlarm,CommitMessage>>();
			
			for(String oriProjectCode : teamOriProjectMap.get(teamCode)){
				
				Map<GroupAlarm,CommitMessage> mapVal2 = new HashMap<GroupAlarm,CommitMessage>();
				
				List<FolderCommitGroupAlarmVDTO> resFolderList= ((FolderCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FolderCommitGroupAlarmVDAO")).searchReadSendAnRec(teamCode, teamCooperatorCode, oriProjectCode);
				if(!resFolderList.isEmpty()){
					 Map<GroupAlarm,CommitMessage> tmpMap= this.makeModelFolderListForPro(resFolderList);
					if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
				}

				List<FileCommitGroupAlarmVDTO> resFileList= ((FileCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FileCommitGroupAlarmVDAO")).searchReadSendAnRec(teamCode, teamCooperatorCode, oriProjectCode);
				if(!resFileList.isEmpty()){
					 Map<GroupAlarm,CommitMessage> tmpMap= this.makeModelFileListForPro(resFileList);
					if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
				}
				
				if(!mapVal2.isEmpty())mapVal1.put(oriProjectCode, mapVal2);
			}
			if(!mapVal1.isEmpty())resMap.put(teamCode, mapVal1);
		}
		if(resMap.isEmpty())System.out.println("db resMap is Empty!!!");
		else System.out.println("db resMap size :  "+resMap.size());
		return resMap;
	}

	@Override
	public Map<String,Map<String,Map<GroupAlarm,CommitMessage>>> loadUnReadTeamProjectListMsgTeamSend(Map<String,List<String>> teamOriProjectMap) throws DAOException, ParseException{
		// teamCode,projectCode, 
		Map<String,Map<String, Map<GroupAlarm,CommitMessage>>> resMap = new HashMap<String,Map<String, Map<GroupAlarm,CommitMessage>>>();
		
		for(String teamCode : teamOriProjectMap.keySet()){
			
			Map<String, Map<GroupAlarm,CommitMessage>> mapVal1 = new HashMap<String, Map<GroupAlarm,CommitMessage>>();
			
			for(String oriProjectCode : teamOriProjectMap.get(teamCode)){
				
				Map<GroupAlarm,CommitMessage> mapVal2 = new HashMap<GroupAlarm,CommitMessage>();

				List<FolderCommitGroupAlarmVDTO> resFolderList= ((FolderCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FolderCommitGroupAlarmVDAO")).searchUnReadSenderCodePro(teamCode, oriProjectCode);
				if(!resFolderList.isEmpty()){
					Map<GroupAlarm,CommitMessage> tmpMap= this.makeModelFolderListForPro(resFolderList);
					if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
				}

				List<FileCommitGroupAlarmVDTO> resFileList= ((FileCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FileCommitGroupAlarmVDAO")).searchUnReadSenderCodePro(teamCode, oriProjectCode);
				if(!resFileList.isEmpty()){
					Map<GroupAlarm,CommitMessage> tmpMap= this.makeModelFileListForPro(resFileList);
					if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
				}
				
				if(!mapVal2.isEmpty())mapVal1.put(oriProjectCode, mapVal2);
			}
			if(!mapVal1.isEmpty())resMap.put(teamCode, mapVal1);
		}
		return resMap;		
	}
	
	@Override
	public Map<String,Map<String,Map<GroupAlarm,CommitMessage>>> loadReadTeamProjectListMsgTeamSend(Map<String,List<String>> teamOriProjectMap) throws DAOException, ParseException{
		
		Map<String,Map<String, Map<GroupAlarm,CommitMessage>>> resMap = new HashMap<String,Map<String, Map<GroupAlarm,CommitMessage>>>();
		
		for(String teamCode : teamOriProjectMap.keySet()){
			
			Map<String, Map<GroupAlarm,CommitMessage>> mapVal1 = new HashMap<String, Map<GroupAlarm,CommitMessage>>();
			
			for(String oriProjectCode : teamOriProjectMap.get(teamCode)){
				
				Map<GroupAlarm,CommitMessage> mapVal2 = new HashMap<GroupAlarm,CommitMessage>();

				List<FolderCommitGroupAlarmVDTO> resFolderList= ((FolderCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FolderCommitGroupAlarmVDAO")).searchReadSenderCodePro(teamCode, oriProjectCode);
				if(!resFolderList.isEmpty()){
					Map<GroupAlarm,CommitMessage> tmpMap= this.makeModelFolderListForPro(resFolderList);
					if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
				}

				List<FileCommitGroupAlarmVDTO> resFileList= ((FileCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FileCommitGroupAlarmVDAO")).searchReadSenderCodePro(teamCode, oriProjectCode);
				if(!resFileList.isEmpty()){
					Map<GroupAlarm,CommitMessage> tmpMap= this.makeModelFileListForPro(resFileList);
					if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
				}
				
				if(!mapVal2.isEmpty())mapVal1.put(oriProjectCode, mapVal2);
			}
			if(!mapVal1.isEmpty())resMap.put(teamCode, mapVal1);
		}
		return resMap;
	}

	@Override
	public Map<String,Map<String,Map<GroupAlarm,CommitMessage>>> loadUnReadTeamProjectMsgTeamSend(String teamCode,String teamOriginProjectCode) throws DAOException, ParseException{
		// teamCode,projectCode, 
		Map<String,Map<String, Map<GroupAlarm,CommitMessage>>> resMap = new HashMap<String,Map<String, Map<GroupAlarm,CommitMessage>>>();
		Map<String, Map<GroupAlarm,CommitMessage>> mapVal1 = new HashMap<String, Map<GroupAlarm,CommitMessage>>();
		Map<GroupAlarm,CommitMessage> mapVal2 = new HashMap<GroupAlarm,CommitMessage>();

		List<FolderCommitGroupAlarmVDTO> resFolderList= ((FolderCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FolderCommitGroupAlarmVDAO")).searchUnReadSenderCodePro(teamCode, teamOriginProjectCode);
		if(!resFolderList.isEmpty()){
			Map<GroupAlarm,CommitMessage> tmpMap= this.makeModelFolderListForPro(resFolderList);
			if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
		}

		List<FileCommitGroupAlarmVDTO> resFileList= ((FileCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FileCommitGroupAlarmVDAO")).searchUnReadSenderCodePro(teamCode, teamOriginProjectCode);
		if(!resFileList.isEmpty()){
			Map<GroupAlarm,CommitMessage> tmpMap= this.makeModelFileListForPro(resFileList);
			if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
		}
		
		if(!mapVal2.isEmpty())mapVal1.put(teamOriginProjectCode, mapVal2);
		
		if(!mapVal1.isEmpty())resMap.put(teamCode, mapVal1);
		
		return resMap;
	}
	
	@Override
	public Map<String,Map<String,Map<GroupAlarm,CommitMessage>>> loadReadTeamProjectMsgTeamSend(String teamCode,String teamOriginProjectCode) throws DAOException, ParseException{
		// teamCode,projectCode, 
		Map<String,Map<String, Map<GroupAlarm,CommitMessage>>> resMap = new HashMap<String,Map<String, Map<GroupAlarm,CommitMessage>>>();
		Map<String, Map<GroupAlarm,CommitMessage>> mapVal1 = new HashMap<String, Map<GroupAlarm,CommitMessage>>();
		Map<GroupAlarm,CommitMessage> mapVal2 = new HashMap<GroupAlarm,CommitMessage>();

		List<FolderCommitGroupAlarmVDTO> resFolderList= ((FolderCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FolderCommitGroupAlarmVDAO")).searchReadSenderCodePro(teamCode, teamOriginProjectCode);
		if(!resFolderList.isEmpty()){
			Map<GroupAlarm,CommitMessage> tmpMap= this.makeModelFolderListForPro(resFolderList);
			if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
		}

		List<FileCommitGroupAlarmVDTO> resFileList= ((FileCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FileCommitGroupAlarmVDAO")).searchReadSenderCodePro(teamCode, teamOriginProjectCode);
		if(!resFileList.isEmpty()){
			Map<GroupAlarm,CommitMessage> tmpMap= this.makeModelFileListForPro(resFileList);
			if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
		}
		
		if(!mapVal2.isEmpty())mapVal1.put(teamOriginProjectCode, mapVal2);
		
		if(!mapVal1.isEmpty())resMap.put(teamCode, mapVal1);
		
		return resMap;
	}

	
	//2.	If member is Team Cooperator
	// 2-A.	search commit message that member send to manager
	@Override
	public Map<String, Map<String, Map<Alarm, CommitMessage>>> loadUnReadTeamListCommitMsgCooperSend(Map<String, Map<String, Map<String, List<String>>>> originTeamProChildren,String cooperatorCode) throws DAOException, ParseException {
		// TODO Auto-generated method stub
		
		// teamCode, projectCode
		Map<String, Map<String, Map<Alarm, CommitMessage>>> resMap = new HashMap<String, Map<String, Map<Alarm, CommitMessage>>>();
		
		for(String teamCode : originTeamProChildren.keySet()){
			
			Map<String, Map<Alarm, CommitMessage>> mapVal1 = new HashMap<String, Map<Alarm, CommitMessage>>();
			
			Map<String, Map<String, List<String>>> oriProListMap= originTeamProChildren.get(teamCode);
			if(!oriProListMap.isEmpty()){
				for(String oriProCode : oriProListMap.keySet()){
					
					Map<Alarm, CommitMessage> mapVal2 = new HashMap<Alarm, CommitMessage>();
					Map<String,List<String>> oriProChildren = oriProListMap.get(oriProCode);
					
					if(!oriProChildren.isEmpty()){
						List<String> oriFolderList = oriProChildren.get("folder");
						List<String> oriFileList = oriProChildren.get("folder");
						
						if(!oriFolderList.isEmpty()){
							//compare cpFolderList							
							//for cooperator
							List<CopiedFolderCommitAlarmVDTO> cpFolderResList= ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchUnReadSenderCodeOr(cooperatorCode, oriFolderList);
							if(!cpFolderResList.isEmpty()){
								Map<Alarm, CommitMessage> tmpVal = this.makeModelMFolderListForCpPro(cpFolderResList);
								if(!tmpVal.isEmpty()) mapVal2.putAll(tmpVal);
							}
						}
						
						if(!oriFileList.isEmpty()){
							//compare cpFileList
							List<CopiedFileCommitAlarmVDTO> cpFileResList= ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchUnReadSenderCodeOr(cooperatorCode, oriFileList);
							if(!cpFileResList.isEmpty()){
								Map<Alarm,CommitMessage> tmpMap= this.makeModelMFileListForCpPro(cpFileResList);
								if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);					
							}
						}
					}
					if(!mapVal2.isEmpty())mapVal1.put(oriProCode, mapVal2);
				}
			}
			if(!mapVal1.isEmpty())resMap.put(teamCode, mapVal1);
		}
		if(resMap.isEmpty())System.out.println("resMap is Empty...........");
		else System.out.println("resMap is    NOT   ---------- Empty........... : "+resMap.size());
		return resMap;
	}

	@Override
	public Map<String, Map<String, Map<Alarm, CommitMessage>>> loadReadTeamListCommitMsgCooperSend(Map<String, Map<String, Map<String, List<String>>>> originTeamProChildren,String cooperatorCode)  throws DAOException, ParseException {
		// TODO Auto-generated method stub

		// teamCode, projectCode
		Map<String, Map<String, Map<Alarm, CommitMessage>>> resMap = new HashMap<String, Map<String, Map<Alarm, CommitMessage>>>();
		
		for(String teamCode : originTeamProChildren.keySet()){
			
			Map<String, Map<Alarm, CommitMessage>> mapVal1 = new HashMap<String, Map<Alarm, CommitMessage>>();
			
			Map<String, Map<String, List<String>>> oriProListMap= originTeamProChildren.get(teamCode);
			for(String originProCode : oriProListMap.keySet()){

				Map<Alarm, CommitMessage> mapVal2 = new HashMap<Alarm, CommitMessage>();
				Map<String,List<String>> oriProChildren = oriProListMap.get(originProCode);
				
				if(!oriProChildren.isEmpty()){
					List<String> oriFolderList = oriProChildren.get("folder");
					List<String> oriFileList = oriProChildren.get("folder");
					
					if(!oriFolderList.isEmpty()){
						//compare cpFolderList							
						//for cooperator
						List<CopiedFolderCommitAlarmVDTO> cpFolderResList= ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchReadSenderCodeOr(cooperatorCode, oriFolderList);
						if(!cpFolderResList.isEmpty()){
							Map<Alarm, CommitMessage> tmpVal = this.makeModelMFolderListForCpPro(cpFolderResList);
							if(!tmpVal.isEmpty()) mapVal2.putAll(tmpVal);
						}
					}
					
					if(!oriFileList.isEmpty()){
						//compare cpFileList
						List<CopiedFileCommitAlarmVDTO> cpFileResList= ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchReadSenderCodeOr(cooperatorCode, oriFileList);
						if(!cpFileResList.isEmpty()){
							Map<Alarm,CommitMessage> tmpMap= this.makeModelMFileListForCpPro(cpFileResList);
							if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);					
						}
					}
				}
				if(!mapVal2.isEmpty())mapVal1.put(originProCode, mapVal2);
			}
			if(!mapVal1.isEmpty())resMap.put(teamCode, mapVal1);
		}
		if(resMap.isEmpty())System.out.println("db resMap is Empty!!!");
		else System.out.println("db resMap Size  :  "+resMap.size());
		return resMap;
	}
	
	@Override
	public Map<String,Map<String,Map<Alarm,CommitMessage>>> loadUnReadTeamProjectListCommitMsgCooperSend(Map<String,Map<String,List<String>>> originTeamProChildren,String teamCode,String cooperatorCode) throws DAOException, ParseException{

		// teamCode, projectCode
		Map<String, Map<String, Map<Alarm, CommitMessage>>> resMap = new HashMap<String, Map<String, Map<Alarm, CommitMessage>>>();
		Map<String, Map<Alarm, CommitMessage>> mapVal1 = new HashMap<String, Map<Alarm, CommitMessage>>();
		
		if(!originTeamProChildren.isEmpty()){
			for(String oriProCode : originTeamProChildren.keySet()){
				
				Map<Alarm, CommitMessage> mapVal2 = new HashMap<Alarm, CommitMessage>();
				Map<String,List<String>> oriProChildren = originTeamProChildren.get(oriProCode);
				
				if(!oriProChildren.isEmpty()){
					List<String> oriFolderList = oriProChildren.get("folder");
					List<String> oriFileList = oriProChildren.get("folder");
					
					if(!oriFolderList.isEmpty()){
						//compare cpFolderList
						List<CopiedFolderCommitAlarmVDTO> cpFolderResList= ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchUnReadSenderCodeOr(cooperatorCode, oriFolderList);
						if(!cpFolderResList.isEmpty()){
							Map<Alarm,CommitMessage> tmpMap= this.makeModelMFolderListForCpPro(cpFolderResList);
							if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);					
						}
					}
					
					if(!oriFileList.isEmpty()){
						//compare cpFileList
						List<CopiedFileCommitAlarmVDTO> cpFileResList= ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchUnReadSenderCodeOr(cooperatorCode, oriFileList);
						if(!cpFileResList.isEmpty()){
							Map<Alarm,CommitMessage> tmpMap= this.makeModelMFileListForCpPro(cpFileResList);
							if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);					
						}
					}
				}
				if(!mapVal2.isEmpty())mapVal1.put(oriProCode, mapVal2);
			}
		}
		if(!mapVal1.isEmpty())resMap.put(teamCode, mapVal1);
	
		if(resMap.isEmpty())System.out.println("resMap is Empty...........");
		else System.out.println("resMap is    NOT   ---------- Empty...........");
		return resMap;
	}
	
	@Override
	public Map<String,Map<String,Map<Alarm,CommitMessage>>> loadReadTeamProjectListCommitMsgCooperSend(Map<String,Map<String,List<String>>> originTeamProChildren,String teamCode,String cooperatorCode) throws DAOException, ParseException{

		// teamCode, projectCode
		Map<String, Map<String, Map<Alarm, CommitMessage>>> resMap = new HashMap<String, Map<String, Map<Alarm, CommitMessage>>>();
		Map<String, Map<Alarm, CommitMessage>> mapVal1 = new HashMap<String, Map<Alarm, CommitMessage>>();
		
		if(!originTeamProChildren.isEmpty()){
			for(String oriProCode : originTeamProChildren.keySet()){
				
				Map<Alarm, CommitMessage> mapVal2 = new HashMap<Alarm, CommitMessage>();
				Map<String,List<String>> oriProChildren = originTeamProChildren.get(oriProCode);
				
				if(!oriProChildren.isEmpty()){
					List<String> oriFolderList = oriProChildren.get("folder");
					List<String> oriFileList = oriProChildren.get("folder");
					
					if(!oriFolderList.isEmpty()){
						//compare cpFolderList
						List<CopiedFolderCommitAlarmVDTO> cpFolderResList= ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchReadSenderCodeOr(cooperatorCode, oriFolderList);
						if(!cpFolderResList.isEmpty()){
							Map<Alarm,CommitMessage> tmpMap= this.makeModelMFolderListForCpPro(cpFolderResList);
							if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);					
						}
					}
					
					if(!oriFileList.isEmpty()){
						//compare cpFileList
						List<CopiedFileCommitAlarmVDTO> cpFileResList= ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchReadSenderCodeOr(cooperatorCode, oriFileList);
						if(!cpFileResList.isEmpty()){
							Map<Alarm,CommitMessage> tmpMap= this.makeModelMFileListForCpPro(cpFileResList);
							if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);					
						}
					}
				}
				if(!mapVal2.isEmpty())mapVal1.put(oriProCode, mapVal2);
			}
		}
		if(!mapVal1.isEmpty())resMap.put(teamCode, mapVal1);
	
		if(resMap.isEmpty())System.out.println("resMap is Empty...........");
		else System.out.println("resMap is    NOT   ---------- Empty...........");
		return resMap;
	}
	
	@Override
	public Map<String,Map<String,Map<Alarm,CommitMessage>>> loadUnReadTeamProjectCommitMsgCooperSend(Map<String,List<String>> cooperCpChildren,String teamCode,String manager,String cooperatorCode, String oirprojectCode) throws DAOException, ParseException{

		// teamCode, projectCode
		Map<String, Map<String, Map<Alarm, CommitMessage>>> resMap = new HashMap<String, Map<String, Map<Alarm, CommitMessage>>>();
		Map<String, Map<Alarm, CommitMessage>> mapVal1 = new HashMap<String, Map<Alarm, CommitMessage>>();
		Map<Alarm, CommitMessage> mapVal2 = new HashMap<Alarm, CommitMessage>();
		
		if(!cooperCpChildren.isEmpty()){
			
			List<String> cpFolderList = cooperCpChildren.get("folder");
			List<String> cpFileList = cooperCpChildren.get("file");

			List<CopiedFolderCommitAlarmVDTO> cpFolderResList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
			List<OriginFolderCommitAlarmVDTO> oriFolderResList = new ArrayList<OriginFolderCommitAlarmVDTO>();
			if(!cpFolderList.isEmpty()){
				//for maanger
				cpFolderResList = ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchUnReadSendAndRecCp(cooperatorCode, manager, cpFolderList);
				oriFolderResList = ((OriginFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFolderCommitAlarmVDAO")).searchUnReadSendAndRecOr(cooperatorCode, manager, cpFolderList);
			}
			List<CopiedFileCommitAlarmVDTO> cpFileResList = new ArrayList<CopiedFileCommitAlarmVDTO>();
			List<OriginFileCommitAlarmVDTO> oriFileResList = new ArrayList<OriginFileCommitAlarmVDTO>();
			if(!cpFileList.isEmpty()){
				//for maanger
				cpFileResList = ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchUnReadSendAndRecCp(cooperatorCode, manager, cpFileList);
				oriFileResList = ((OriginFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFileCommitAlarmVDAO")).searchUnReadSendAndRecOr(cooperatorCode, manager, cpFileList);
			}
			mapVal2 = this.makeModelMsgMapForCpPro(cpFolderResList, oriFolderResList, cpFileResList, oriFileResList);
		}
		if(!mapVal2.isEmpty())mapVal1.put(oirprojectCode, mapVal2);
			
		
		if(!mapVal1.isEmpty())resMap.put(teamCode, mapVal1);
	
		if(resMap.isEmpty())System.out.println("resMap is Empty...........");
		else System.out.println("resMap is    NOT   ---------- Empty...........");
		return resMap;
	}
	
	@Override
	public Map<String,Map<String,Map<Alarm,CommitMessage>>> loadReadTeamProjectCommitMsgCooperSend(Map<String,List<String>> cooperCpChildren,String teamCode,String manager,String cooperatorCode, String oirprojectCode) throws DAOException, ParseException{


		// teamCode, projectCode
		Map<String, Map<String, Map<Alarm, CommitMessage>>> resMap = new HashMap<String, Map<String, Map<Alarm, CommitMessage>>>();
		Map<String, Map<Alarm, CommitMessage>> mapVal1 = new HashMap<String, Map<Alarm, CommitMessage>>();
		Map<Alarm, CommitMessage> mapVal2 = new HashMap<Alarm, CommitMessage>();
		
		if(!cooperCpChildren.isEmpty()){
			
			List<String> cpFolderList = cooperCpChildren.get("folder");
			List<String> cpFileList = cooperCpChildren.get("file");

			List<CopiedFolderCommitAlarmVDTO> cpFolderResList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
			List<OriginFolderCommitAlarmVDTO> oriFolderResList = new ArrayList<OriginFolderCommitAlarmVDTO>();
			if(!cpFolderList.isEmpty()){
				//for maanger
				cpFolderResList = ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchReadSendAndRecCp(cooperatorCode, manager, cpFolderList);
				oriFolderResList = ((OriginFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFolderCommitAlarmVDAO")).searchReadSendAndRecOr(cooperatorCode, manager, cpFolderList);
			}
			List<CopiedFileCommitAlarmVDTO> cpFileResList = new ArrayList<CopiedFileCommitAlarmVDTO>();
			List<OriginFileCommitAlarmVDTO> oriFileResList = new ArrayList<OriginFileCommitAlarmVDTO>();
			if(!cpFileList.isEmpty()){
				//for maanger
				cpFileResList = ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchReadSendAndRecCp(cooperatorCode, manager, cpFileList);
				oriFileResList = ((OriginFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFileCommitAlarmVDAO")).searchReadSendAndRecOr(cooperatorCode, manager, cpFileList);
			}
			mapVal2 = this.makeModelMsgMapForCpPro(cpFolderResList, oriFolderResList, cpFileResList, oriFileResList);
		}
		if(!mapVal2.isEmpty())mapVal1.put(oirprojectCode, mapVal2);
			
		
		if(!mapVal1.isEmpty())resMap.put(teamCode, mapVal1);
	
		if(resMap.isEmpty())System.out.println("resMap is Empty...........");
		else System.out.println("resMap is    NOT   ---------- Empty...........");
		return resMap;
	}

	
	// 2-B.	search Team send to cooperator
	@Override
	public Map<String, Map<String, Map<GroupAlarm,CommitMessage>>> loadUnReadTeamCommitGroupMsgCooperRec(Map<String,List<String>> oriTeamProChildren,String cooperatorCode) throws DAOException, ParseException {
		// TODO Auto-generated method stub
		
		//teamCode, projectCode
		Map<String, Map<String, Map<GroupAlarm,CommitMessage>>> resMap = new HashMap<String, Map<String, Map<GroupAlarm,CommitMessage>>>();
		
		for(String teamCode : oriTeamProChildren.keySet()){
			
			Map<String, Map<GroupAlarm,CommitMessage>> mapVal1 = new HashMap<String, Map<GroupAlarm,CommitMessage>>();
			
			for(String oriProCode : oriTeamProChildren.get(teamCode)){
				
				Map<GroupAlarm,CommitMessage> mapVal2 = new HashMap<GroupAlarm,CommitMessage>();
				
				List<FileCommitGroupAlarmVDTO> fileResList= ((FileCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FileCommitGroupAlarmVDAO")).searchUnReadReceiverCodePro(cooperatorCode, oriProCode);
				if(!fileResList.isEmpty()){
					Map<GroupAlarm,CommitMessage> tmpMap = this.makeModelFileListForPro(fileResList);
					if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
				}
				List<FolderCommitGroupAlarmVDTO> folderResList= ((FolderCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FolderCommitGroupAlarmVDAO")).searchUnReadReceiverCodePro(cooperatorCode, oriProCode);
				if(!folderResList.isEmpty()){
					Map<GroupAlarm,CommitMessage> tmpMap = this.makeModelFolderListForPro(folderResList);
					if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
				}
				
				if(!mapVal2.isEmpty())mapVal1.put(oriProCode, mapVal2);
			}
			if(!mapVal1.isEmpty())resMap.put(teamCode, mapVal1);
		}		
		
		return resMap;
	}

	@Override
	public Map<String, Map<String, Map<GroupAlarm,CommitMessage>>> loadReadTeamCommitGroupMsgCooperRec(Map<String,List<String>> oriTeamProChildren,String cooperatorCode) throws DAOException, ParseException{
		// TODO Auto-generated method stub

		//teamCode, projectCode
		Map<String, Map<String, Map<GroupAlarm,CommitMessage>>> resMap = new HashMap<String, Map<String, Map<GroupAlarm,CommitMessage>>>();
		
		for(String teamCode : oriTeamProChildren.keySet()){
			
			Map<String, Map<GroupAlarm,CommitMessage>> mapVal1 = new HashMap<String, Map<GroupAlarm,CommitMessage>>();
			
			for(String oriProCode : oriTeamProChildren.get(teamCode)){
				
				Map<GroupAlarm,CommitMessage> mapVal2 = new HashMap<GroupAlarm,CommitMessage>();
				
				List<FileCommitGroupAlarmVDTO> fileResList= ((FileCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FileCommitGroupAlarmVDAO")).searchReadReceiverCodePro(cooperatorCode, oriProCode);
				if(!fileResList.isEmpty()){
					Map<GroupAlarm,CommitMessage> tmpMap = this.makeModelFileListForPro(fileResList);
					if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
				}
				List<FolderCommitGroupAlarmVDTO> folderResList= ((FolderCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FolderCommitGroupAlarmVDAO")).searchReadReceiverCodePro(cooperatorCode, oriProCode);
				if(!folderResList.isEmpty()){
					Map<GroupAlarm,CommitMessage> tmpMap = this.makeModelFolderListForPro(folderResList);
					if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
				}
				
				if(!mapVal2.isEmpty())mapVal1.put(oriProCode, mapVal2);
			}
			if(!mapVal1.isEmpty())resMap.put(teamCode, mapVal1);
		}		
		
		return resMap;
	}

	//3.	If Member is owner of personal Project 
	// 3-A.	Project Owner received Commit message from project coopers
	@Override
	public Map<String,Map<Alarm,CommitMessage>> loadUnReadSharedProListCommitMsgOwnerRec(Map<String, Map<String, List<String>>> presonalCpProChildern,String ownerCode,String oiriProCode) throws DAOException, ParseException {
		// TODO Auto-generated method stub
		System.out.println("\n           CommitMessageActionImpl       loadUnReadSharedProListCommitMsgOwnerRec    line     873 ");
		//argument : cooperatorCode,copiedFolderList,copiedFileList		
		//projectCode, cooperCode
		Map<String,Map<Alarm,CommitMessage>> resMap = new HashMap<String,Map<Alarm,CommitMessage>>();
		
		for(String cooperCode : presonalCpProChildern.keySet()){
			
			Map<String,List<String>>  proChild = presonalCpProChildern.get(cooperCode);
			Map<Alarm, CommitMessage> mapVal1 = new HashMap<Alarm, CommitMessage>();
			List<String> cpFolderList = proChild.get("folder");
			List<String> cpFileList = proChild.get("file");

			List<CopiedFolderCommitAlarmVDTO> cpFolderResList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
			List<OriginFolderCommitAlarmVDTO> oriFolderResList = new ArrayList<OriginFolderCommitAlarmVDTO>();
			if(!cpFolderList.isEmpty()){
				//for maanger
				cpFolderResList = ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchUnReadSendAndRecCp(cooperCode, ownerCode, cpFolderList);
				oriFolderResList = ((OriginFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFolderCommitAlarmVDAO")).searchUnReadSendAndRecOr(cooperCode, ownerCode, cpFolderList);
			}
			List<CopiedFileCommitAlarmVDTO> cpFileResList = new ArrayList<CopiedFileCommitAlarmVDTO>();
			List<OriginFileCommitAlarmVDTO> oriFileResList = new ArrayList<OriginFileCommitAlarmVDTO>();
			if(!cpFileList.isEmpty()){
				//for maanger
				cpFileResList = ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchUnReadSendAndRecCp(cooperCode, ownerCode, cpFileList);
				oriFileResList = ((OriginFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFileCommitAlarmVDAO")).searchUnReadSendAndRecOr(cooperCode, ownerCode, cpFileList);
			}
			mapVal1 = this.makeModelMsgMapForCpPro(cpFolderResList, oriFolderResList, cpFileResList, oriFileResList);
			
			if(!mapVal1.isEmpty()){
				resMap.put(cooperCode, mapVal1);
			}
		}
		if(resMap.isEmpty())System.out.println("db resMap is Empty!!!!!");
		else System.out.println("db resMap size  ::  "+resMap.size());
		return resMap;
	}

	@Override
	public Map<String,Map<Alarm,CommitMessage>> loadReadSharedProListCommitMsgOwnerRec(Map<String, Map<String, List<String>>> presonalCpProChildern,String ownerCode,String oiriProCode) throws DAOException, ParseException {
		// TODO Auto-generated method stub
		
		//projectCode, cooperCode
		Map<String,Map<Alarm,CommitMessage>> resMap = new HashMap<String,Map<Alarm,CommitMessage>>();
		
		for(String cooperCode : presonalCpProChildern.keySet()){
			
			Map<String,List<String>>  proChild = presonalCpProChildern.get(cooperCode);
			Map<Alarm, CommitMessage> mapVal1 = new HashMap<Alarm, CommitMessage>();
			List<String> cpFolderList = proChild.get("folder");
			List<String> cpFileList = proChild.get("file");

			List<CopiedFolderCommitAlarmVDTO> cpFolderResList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
			List<OriginFolderCommitAlarmVDTO> oriFolderResList = new ArrayList<OriginFolderCommitAlarmVDTO>();
			if(!cpFolderList.isEmpty()){
				//for maanger
				cpFolderResList = ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchReadSendAndRecCp(cooperCode, ownerCode, cpFolderList);
				oriFolderResList = ((OriginFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFolderCommitAlarmVDAO")).searchReadSendAndRecOr(cooperCode, ownerCode, cpFolderList);
			}
			List<CopiedFileCommitAlarmVDTO> cpFileResList = new ArrayList<CopiedFileCommitAlarmVDTO>();
			List<OriginFileCommitAlarmVDTO> oriFileResList = new ArrayList<OriginFileCommitAlarmVDTO>();
			if(!cpFileList.isEmpty()){
				//for maanger
				cpFileResList = ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchReadSendAndRecCp(cooperCode, ownerCode, cpFileList);
				oriFileResList = ((OriginFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFileCommitAlarmVDAO")).searchReadSendAndRecOr(cooperCode, ownerCode, cpFileList);
			}
			mapVal1 = this.makeModelMsgMapForCpPro(cpFolderResList, oriFolderResList, cpFileResList, oriFileResList);
			
			if(!mapVal1.isEmpty()){
				resMap.put(cooperCode, mapVal1);
			}
		}
		if(resMap.isEmpty())System.out.println("db  resMap is Empty !!!!!!");
		else System.out.println("db  size  : "+resMap.size());
		return resMap;
	}
	
	@Override
	public Map<Alarm,CommitMessage> loadUnReadSharedProCommitMsgOwnerRec(Map<String,List<String>> presonalOriProChildern,String ownerCode) throws DAOException, ParseException{

		//projectCode, cooperCode
		Map<Alarm,CommitMessage> resMap = new HashMap<Alarm,CommitMessage>();
		List<String> oriFolderList = presonalOriProChildern.get("folder");
		List<String> oriFileList = presonalOriProChildern.get("file");
		
		if(!oriFolderList.isEmpty()){
			List<CopiedFolderCommitAlarmVDTO> cpFolderResList= ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchUnReadReceiverCodeOr(ownerCode, oriFolderList);
			if(!cpFolderResList.isEmpty()){
				Map<Alarm,CommitMessage> tmpMap = this.makeModelMFolderListForCpPro(cpFolderResList);
				if(!tmpMap.isEmpty()) resMap.putAll(tmpMap);
			}
		}
		
		if(!oriFileList.isEmpty()){
			List<CopiedFileCommitAlarmVDTO> cpFileResList= ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchUnReadReceiverCodeOr(ownerCode, oriFileList);
			if(!cpFileResList.isEmpty()){
				Map<Alarm,CommitMessage> tmpMap = this.makeModelMFileListForCpPro(cpFileResList);
				if(!tmpMap.isEmpty()) resMap.putAll(tmpMap);
			}
		}
		
		return resMap;
	}
	
	@Override
	public Map<Alarm,CommitMessage> loadReadSharedProCommitMsgOwnerRec(Map<String,List<String>> presonalOriProChildern,String ownerCode) throws DAOException, ParseException{

		//projectCode, cooperCode
		Map<Alarm,CommitMessage> resMap = new HashMap<Alarm,CommitMessage>();
		List<String> oriFolderList = presonalOriProChildern.get("folder");
		List<String> oriFileList = presonalOriProChildern.get("file");
		
		if(!oriFolderList.isEmpty()){
			List<CopiedFolderCommitAlarmVDTO> cpFolderResList= ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchReadReceiverCodeOr(ownerCode, oriFolderList);
			if(!cpFolderResList.isEmpty()){
				Map<Alarm,CommitMessage> tmpMap = this.makeModelMFolderListForCpPro(cpFolderResList);
				if(!tmpMap.isEmpty()) resMap.putAll(tmpMap);
			}
		}
		
		if(!oriFileList.isEmpty()){
			List<CopiedFileCommitAlarmVDTO> cpFileResList= ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchReadReceiverCodeOr(ownerCode, oriFileList);
			if(!cpFileResList.isEmpty()){
				Map<Alarm,CommitMessage> tmpMap = this.makeModelMFileListForCpPro(cpFileResList);
				if(!tmpMap.isEmpty()) resMap.putAll(tmpMap);
			}
		}
		
		return resMap;
	}

	
	// 3-B.	Owner send Shared Project Cooperators
	@Override
	public Map<String, Map<String, Map<GroupAlarm,CommitMessage>>> loadUnReadOriSharedProCommitGroupMsgOwnerSend(List<String> oriSharedProjectList,String ownerCode) throws DAOException, ParseException {
		// TODO Auto-generated method stub
		
		// oriOnwer,personalOriProCode
		Map<String, Map<String, Map<GroupAlarm,CommitMessage>>> resMap = new HashMap<String, Map<String, Map<GroupAlarm,CommitMessage>>>();
		Map<String, Map<GroupAlarm,CommitMessage>> mapVal1 = new HashMap<String, Map<GroupAlarm,CommitMessage>>();
		for(String oriProCode : oriSharedProjectList){
			
			Map<GroupAlarm,CommitMessage> mapVal2 = new HashMap<GroupAlarm,CommitMessage>();
			List<FileCommitGroupAlarmVDTO> fileResList= ((FileCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FileCommitGroupAlarmVDAO")).searchUnReadSenderCodePro(ownerCode, oriProCode);
			if(!fileResList.isEmpty()){
				Map<GroupAlarm,CommitMessage> tmpMap = this.makeModelFileListForPro(fileResList);
				if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
			}
			
			List<FolderCommitGroupAlarmVDTO> folderResList= ((FolderCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FolderCommitGroupAlarmVDAO")).searchUnReadSenderCodePro(ownerCode, oriProCode);
			if(!folderResList.isEmpty()){
				Map<GroupAlarm,CommitMessage> tmpMap = this.makeModelFolderListForPro(folderResList);
				if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
			}
			if(!mapVal2.isEmpty())mapVal1.put(oriProCode, mapVal2);
		}
		if(!mapVal1.isEmpty())resMap.put(ownerCode, mapVal1);
		
		return resMap;
	}

	@Override
	public Map<String, Map<String, Map<GroupAlarm,CommitMessage>>> loadReadOriSharedProCommitGroupMsgOwnerSend(
			List<String> oriSharedProjectList,String ownerCode) throws DAOException, ParseException {
		// TODO Auto-generated method stub

		// oriOnwer,personalOriProCode
		Map<String, Map<String, Map<GroupAlarm,CommitMessage>>> resMap = new HashMap<String, Map<String, Map<GroupAlarm,CommitMessage>>>();
		Map<String, Map<GroupAlarm,CommitMessage>> mapVal1 = new HashMap<String, Map<GroupAlarm,CommitMessage>>();
		for(String oriProCode : oriSharedProjectList){
			
			Map<GroupAlarm,CommitMessage> mapVal2 = new HashMap<GroupAlarm,CommitMessage>();
			List<FileCommitGroupAlarmVDTO> fileResList= ((FileCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FileCommitGroupAlarmVDAO")).searchReadSenderCodePro(ownerCode, oriProCode);
			if(!fileResList.isEmpty()){
				Map<GroupAlarm,CommitMessage> tmpMap = this.makeModelFileListForPro(fileResList);
				if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
			}
			
			List<FolderCommitGroupAlarmVDTO> folderResList= ((FolderCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FolderCommitGroupAlarmVDAO")).searchReadSenderCodePro(ownerCode, oriProCode);
			if(!folderResList.isEmpty()){
				Map<GroupAlarm,CommitMessage> tmpMap = this.makeModelFolderListForPro(folderResList);
				if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
			}
			if(!mapVal2.isEmpty())mapVal1.put(oriProCode, mapVal2);
		}
		if(!mapVal1.isEmpty())resMap.put(ownerCode, mapVal1);
		
		return resMap;
	}

	//4.	If member is personal project¡¯s cooperator
	// 4-A.	Search cooperator send message to owner
	@Override
	public Map<String, Map<Alarm, CommitMessage>> loadUnReadSharedProListCommitMsgCooperSend(Map<String, Map<String, Map<String, List<String>>>> cooperCpMap,String cooperatorCode) throws DAOException, ParseException {
		// TODO Auto-generated method stub
		
		//ownerCode, projectCode
		Map<String, Map<Alarm, CommitMessage>> resMap = new HashMap<String, Map<Alarm, CommitMessage>>();
		
		for(String ownerCode : cooperCpMap.keySet()){
			
			Map<String, Map<String,List<String>>> cpProMap = cooperCpMap.get(ownerCode);
			for(String oriProCode : cpProMap.keySet()){
				
				Map<Alarm, CommitMessage> mapVal1 = new HashMap<Alarm, CommitMessage>();
				
				Map<String,List<String>> cpproChildren = cpProMap.get(oriProCode);
				List<String> cpFileList = cpproChildren.get("file");
				List<String> cpFolderList = cpproChildren.get("folder");

				List<CopiedFolderCommitAlarmVDTO> cpFolderResList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
				List<OriginFolderCommitAlarmVDTO> oriFolderResList = new ArrayList<OriginFolderCommitAlarmVDTO>();
				if(!cpFolderList.isEmpty()){
					//for maanger
					cpFolderResList = ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchReadSendAndRecCp(cooperatorCode, ownerCode, cpFolderList);
					oriFolderResList = ((OriginFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFolderCommitAlarmVDAO")).searchReadSendAndRecOr(cooperatorCode, ownerCode, cpFolderList);
				}
				List<CopiedFileCommitAlarmVDTO> cpFileResList = new ArrayList<CopiedFileCommitAlarmVDTO>();
				List<OriginFileCommitAlarmVDTO> oriFileResList = new ArrayList<OriginFileCommitAlarmVDTO>();
				if(!cpFileList.isEmpty()){
					//for maanger
					cpFileResList = ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchReadSendAndRecCp(cooperatorCode, ownerCode, cpFileList);
					oriFileResList = ((OriginFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFileCommitAlarmVDAO")).searchReadSendAndRecOr(cooperatorCode, ownerCode, cpFileList);
				}
				mapVal1 = this.makeModelMsgMapForCpPro(cpFolderResList, oriFolderResList, cpFileResList, oriFileResList);
				
				if(!mapVal1.isEmpty())resMap.put(oriProCode, mapVal1);
			}
		}
		System.out.println("CommitMessageActionImpl   ::::::::============   resMap.size()  : "+resMap.size());
		if(!resMap.isEmpty()){
			String key = resMap.keySet().iterator().next();
			Alarm alarm = resMap.get(key).keySet().iterator().next();
			CommitMessage coMsg = resMap.get(key).get(alarm);
			
			System.out.println(key);
			System.out.println(alarm.getAlarmCode());
			System.out.println(coMsg.getMessageCode());
			System.out.println(coMsg.getContent());
		}
		if(resMap.isEmpty())System.out.println("db resMap is Empty!!!!!");
		else System.out.println("db resMap size  ::  "+resMap.size());
		return resMap;
	}

	@Override
	public Map<String,Map<Alarm,CommitMessage>> loadReadSharedProListCommitMsgCooperSend(Map<String, Map<String, Map<String, List<String>>>> cooperCpMap,String cooperatorCode) throws DAOException, ParseException {
		// TODO Auto-generated method stub

		//ownerCode, projectCode
		Map<String,Map<Alarm,CommitMessage>> resMap = new HashMap<String,Map<Alarm,CommitMessage>>();
		
		for(String ownerCode : cooperCpMap.keySet()){
			
			Map<String, Map<String,List<String>>> cpProMap = cooperCpMap.get(ownerCode);
			for(String oriProCode : cpProMap.keySet()){
				
				Map<Alarm, CommitMessage> mapVal1 = new HashMap<Alarm, CommitMessage>();
				
				Map<String,List<String>> cpproChildren = cpProMap.get(oriProCode);
				List<String> cpFileList = cpproChildren.get("file");
				List<String> cpFolderList = cpproChildren.get("folder");

				List<CopiedFolderCommitAlarmVDTO> cpFolderResList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
				List<OriginFolderCommitAlarmVDTO> oriFolderResList = new ArrayList<OriginFolderCommitAlarmVDTO>();
				if(!cpFolderList.isEmpty()){
					//for maanger
					cpFolderResList = ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchUnReadSendAndRecCp(cooperatorCode, ownerCode, cpFolderList);
					oriFolderResList = ((OriginFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFolderCommitAlarmVDAO")).searchUnReadSendAndRecOr(cooperatorCode, ownerCode, cpFolderList);
				}
				List<CopiedFileCommitAlarmVDTO> cpFileResList = new ArrayList<CopiedFileCommitAlarmVDTO>();
				List<OriginFileCommitAlarmVDTO> oriFileResList = new ArrayList<OriginFileCommitAlarmVDTO>();
				if(!cpFileList.isEmpty()){
					//for maanger
					cpFileResList = ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchUnReadSendAndRecCp(cooperatorCode, ownerCode, cpFileList);
					oriFileResList = ((OriginFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFileCommitAlarmVDAO")).searchUnReadSendAndRecOr(cooperatorCode, ownerCode, cpFileList);
				}
				mapVal1 = this.makeModelMsgMapForCpPro(cpFolderResList, oriFolderResList, cpFileResList, oriFileResList);
				
				if(!mapVal1.isEmpty())resMap.put(oriProCode, mapVal1);
			}
		}
		if(resMap.isEmpty())System.out.println("db reaMap is EMpty!!!!...");
		else System.out.println("db resMap size  : "+resMap.size());
		return resMap;
	}

	@Override
	public Map<Alarm,CommitMessage> loadUnReadSharedProCommitMsgCooperSend(Map<String,List<String>> sharedProjectChildMap,String ownerCode,String cooperatorCode) throws DAOException, ParseException{

		//ownerCode, projectCode
		Map<Alarm,CommitMessage> resMap = new HashMap<Alarm,CommitMessage>();
		List<String> oriFileList = sharedProjectChildMap.get("file");
		List<String> oriFolderList = sharedProjectChildMap.get("folder");
		
		if(!oriFileList.isEmpty()){
			List<CopiedFileCommitAlarmVDTO> cpFileResList= ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchUnReadSendAndRecOr(cooperatorCode, ownerCode, oriFileList);
			if(!cpFileResList.isEmpty()){
				Map<Alarm,CommitMessage> tmpMap = this.makeModelMFileListForCpPro(cpFileResList);
				if(!tmpMap.isEmpty()) resMap.putAll(tmpMap);
			}
		}
		
		if(!oriFolderList.isEmpty()){
			List<CopiedFolderCommitAlarmVDTO> cpFolderResList= ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchUnReadSendAndRecOr(cooperatorCode, ownerCode, oriFolderList);
			if(!cpFolderResList.isEmpty()){
				Map<Alarm,CommitMessage> tmpMap = this.makeModelMFolderListForCpPro(cpFolderResList);
				if(!tmpMap.isEmpty()) resMap.putAll(tmpMap);
			}
		}
		return resMap;
	}
	
	@Override
	public Map<Alarm,CommitMessage> loadReadSharedProCommitMsgCooperSend(Map<String,List<String>> sharedProjectChildMap,String ownerCode, String cooperatorCode) throws DAOException, ParseException{

		//ownerCode, projectCode
		Map<Alarm,CommitMessage> resMap = new HashMap<Alarm,CommitMessage>();
		List<String> oriFileList = sharedProjectChildMap.get("file");
		List<String> oriFolderList = sharedProjectChildMap.get("folder");
		
		if(!oriFileList.isEmpty()){
			List<CopiedFileCommitAlarmVDTO> cpFileResList= ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchReadSendAndRecOr(cooperatorCode, ownerCode, oriFileList);
			if(!cpFileResList.isEmpty()){
				Map<Alarm,CommitMessage> tmpMap = this.makeModelMFileListForCpPro(cpFileResList);
				if(!tmpMap.isEmpty()) resMap.putAll(tmpMap);
			}
		}
		
		if(!oriFolderList.isEmpty()){
			List<CopiedFolderCommitAlarmVDTO> cpFolderResList= ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchReadSendAndRecOr(cooperatorCode, ownerCode, oriFolderList);
			if(!cpFolderResList.isEmpty()){
				Map<Alarm,CommitMessage> tmpMap = this.makeModelMFolderListForCpPro(cpFolderResList);
				if(!tmpMap.isEmpty()) resMap.putAll(tmpMap);
			}
		}
		return resMap;
	}

	
	// 4-B.	Search owner send message to cooper
	@Override
	public Map<String, Map<String, Map<GroupAlarm,CommitMessage>>> loadUnReadSharedProCommitGroupMsgCooperRec(
			Map<String,List<String>> sharedProjectMapPerOwner,String cooperatorCode) throws DAOException, ParseException {
		// TODO Auto-generated method stub
		
		// ownerCode, oriProCode
		Map<String, Map<String, Map<GroupAlarm,CommitMessage>>> resMap = new HashMap<String, Map<String, Map<GroupAlarm,CommitMessage>>>();
		
		for(String ownerCode : sharedProjectMapPerOwner.keySet()){
			
			Map<String, Map<GroupAlarm,CommitMessage>> mapVal1 = new HashMap<String, Map<GroupAlarm,CommitMessage>>();
			
			List<String> shOriProList = sharedProjectMapPerOwner.get(ownerCode);
			for(String shOriProCode : shOriProList){
				
				Map<GroupAlarm,CommitMessage> mapVal2 = new HashMap<GroupAlarm,CommitMessage>();
				
				List<FileCommitGroupAlarmVDTO> fileResList= ((FileCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FileCommitGroupAlarmVDAO")).searchUnReadSendAnRec(ownerCode, cooperatorCode, shOriProCode);
				if(!fileResList.isEmpty()){
					Map<GroupAlarm,CommitMessage> tmpMap = this.makeModelFileListForPro(fileResList);
					if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
				}
				List<FolderCommitGroupAlarmVDTO> folderResList= ((FolderCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FolderCommitGroupAlarmVDAO")).searchUnReadSendAnRec(ownerCode, cooperatorCode, shOriProCode);
				if(!folderResList.isEmpty()){
					Map<GroupAlarm,CommitMessage> tmpMap = this.makeModelFolderListForPro(folderResList);
					if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
				}
				
				if(!mapVal2.isEmpty())mapVal1.put(shOriProCode, mapVal2);
			}
			if(!mapVal1.isEmpty())resMap.put(ownerCode, mapVal1);
		}
		
		return resMap;
	}

	@Override
	public Map<String, Map<String, Map<GroupAlarm,CommitMessage>>> loadReadSharedProCommitGroupMsgCooperRec(Map<String,List<String>> sharedProjectMapPerOwner,String cooperatorCode) throws DAOException, ParseException {
		// TODO Auto-generated method stub

		// ownerCode, oriProCode
		Map<String, Map<String, Map<GroupAlarm,CommitMessage>>> resMap = new HashMap<String, Map<String, Map<GroupAlarm,CommitMessage>>>();
		
		for(String ownerCode : sharedProjectMapPerOwner.keySet()){
			
			Map<String, Map<GroupAlarm,CommitMessage>> mapVal1 = new HashMap<String, Map<GroupAlarm,CommitMessage>>();
			
			List<String> shOriProList = sharedProjectMapPerOwner.get(ownerCode);
			for(String shOriProCode : shOriProList){
				
				Map<GroupAlarm,CommitMessage> mapVal2 = new HashMap<GroupAlarm,CommitMessage>();
				
				List<FileCommitGroupAlarmVDTO> fileResList= ((FileCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FileCommitGroupAlarmVDAO")).searchReadSendAnRec(ownerCode, cooperatorCode, shOriProCode);
				if(!fileResList.isEmpty()){
					Map<GroupAlarm,CommitMessage> tmpMap = this.makeModelFileListForPro(fileResList);
					if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
				}
				List<FolderCommitGroupAlarmVDTO> folderResList= ((FolderCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FolderCommitGroupAlarmVDAO")).searchReadSendAnRec(ownerCode, cooperatorCode, shOriProCode);
				if(!folderResList.isEmpty()){
					Map<GroupAlarm,CommitMessage> tmpMap = this.makeModelFolderListForPro(folderResList);
					if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
				}
				
				if(!mapVal2.isEmpty())mapVal1.put(shOriProCode, mapVal2);
			}
			if(!mapVal1.isEmpty())resMap.put(ownerCode, mapVal1);
		}
		
		return resMap;
	}
	
	@Override
	public Map<String,Map<Alarm,CommitMessage>> loadUnReadCooperTeamCommitMessageForTeam(Map<String,Map<String,List<String>>> teamOriProjectMap,String teamCode) throws DAOException, ParseException{
		
		 Map<String,Map<Alarm,CommitMessage>> resMap = new HashMap<String,Map<Alarm,CommitMessage>>();
		
		for(String oriProCode : teamOriProjectMap.keySet()){
			
			Map<Alarm,CommitMessage> mapVal = new HashMap<Alarm,CommitMessage>();
			Map<String, List<String>> proMap = teamOriProjectMap.get(oriProCode);
			if(!proMap.isEmpty()){

				if(!proMap.get("file").isEmpty()){
					List<CopiedFileCommitAlarmVDTO> fileResList= ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchUnReadOriginFileCode(proMap.get("file"));
					if(!fileResList.isEmpty()){
						Map<Alarm,CommitMessage> tmpMap = this.makeModelMFileListForCpPro(fileResList);
						if(!tmpMap.isEmpty()) mapVal.putAll(tmpMap);
					}
				}
				
				if(!proMap.get("folder").isEmpty()){
					List<CopiedFolderCommitAlarmVDTO> folderResList= ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchUnReadOriginFolderCode(proMap.get("folder"));
					if(!folderResList.isEmpty()){
						Map<Alarm,CommitMessage> tmpMap = this.makeModelMFolderListForCpPro(folderResList);
						if(!tmpMap.isEmpty()) mapVal.putAll(tmpMap);
					}
				}
				
			}
			if(!mapVal.isEmpty())resMap.put(oriProCode, mapVal);
		}
		
		return resMap;
	}
	
	@Override
	public  Map<String,Map<Alarm,CommitMessage>> loadReadCooperTeamCommitMessageForTeam(Map<String,Map<String,List<String>>> teamOriProjectMap,String teamCode) throws DAOException, ParseException
	{

		 Map<String,Map<Alarm,CommitMessage>> resMap = new HashMap<String,Map<Alarm,CommitMessage>>();
		
		for(String oriProCode : teamOriProjectMap.keySet()){
			
			Map<Alarm,CommitMessage> mapVal = new HashMap<Alarm,CommitMessage>();
			Map<String, List<String>> proMap = teamOriProjectMap.get(oriProCode);
			if(!proMap.isEmpty()){
				List<String> oriFileList = proMap.get("file");
				List<String> oriFolderList = proMap.get("folder");
				
				if(!oriFileList.isEmpty()){
					/*List<OriginFileCommitAlarmVDTO> oriFileResList= ((OriginFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFileCommitAlarmVDAO")).searchReadOriginFileCode(oriFileList);
					if(!oriFileResList.isEmpty()){
						Map<Alarm,CommitMessage> tmpMap = this.makeModelMFileListForPro(oriFileResList);
						if(!tmpMap.isEmpty()) mapVal.putAll(tmpMap);
					}
					*/
					List<CopiedFileCommitAlarmVDTO> cpFileResList= ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchReadOriginFileCode(oriFileList);
					if(!cpFileResList.isEmpty()){
						Map<Alarm,CommitMessage> tmpMap = this.makeModelMFileListForCpPro(cpFileResList);
						if(!tmpMap.isEmpty()) mapVal.putAll(tmpMap);
					}
				}
				
				if(!oriFolderList.isEmpty()){
					/*List<OriginFolderCommitAlarmVDTO> oriFolderResList= ((OriginFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFolderCommitAlarmVDAO")).searchReadOriginFolderCode(oriFolderList);
					if(!oriFolderResList.isEmpty()){
						Map<Alarm,CommitMessage> tmpMap = this.makeModelMFolderListForPro(oriFolderResList);
						if(!tmpMap.isEmpty()) mapVal.putAll(tmpMap);
					}*/
					
					List<CopiedFolderCommitAlarmVDTO> cpFolderResList= ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchReadOriginFolderCode(oriFolderList);
					if(!cpFolderResList.isEmpty()){
						Map<Alarm,CommitMessage> tmpMap = this.makeModelMFolderListForCpPro(cpFolderResList);
						if(!tmpMap.isEmpty()) mapVal.putAll(tmpMap);
					}
				}
			}
			if(!mapVal.isEmpty())resMap.put(oriProCode, mapVal);
		}
		
		return resMap;
	}
	
	public Map<Alarm,CommitMessage> loadUnReadCooperSharedProCommitMessageForProject(Map<String,List<String>> oriProjectMap) throws DAOException, ParseException{

		Map<Alarm,CommitMessage> resMap = new HashMap<Alarm,CommitMessage>();

		List<String> oriFileList = oriProjectMap.get("file");
		List<String> oriFolderList = oriProjectMap.get("folder");
		
		if(!oriFileList.isEmpty()){
			/*List<OriginFileCommitAlarmVDTO> oriFileResList= ((OriginFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFileCommitAlarmVDAO")).searchUnReadOriginFileCode(oriFileList);
			if(!oriFileResList.isEmpty()){
				Map<Alarm,CommitMessage> tmpMap = this.makeModelMFileListForPro(oriFileResList);
				if(!tmpMap.isEmpty()) resMap.putAll(tmpMap);
			}
			*/
			List<CopiedFileCommitAlarmVDTO> cpFileResList= ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchUnReadOriginFileCode(oriFileList);
			if(!cpFileResList.isEmpty()){
				Map<Alarm,CommitMessage> tmpMap = this.makeModelMFileListForCpPro(cpFileResList);
				if(!tmpMap.isEmpty()) resMap.putAll(tmpMap);
			}
		}
		
		if(!oriFolderList.isEmpty()){
			/*List<OriginFolderCommitAlarmVDTO> oriFolderResList= ((OriginFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFolderCommitAlarmVDAO")).searchUnReadOriginFolderCode(oriFolderList);
			if(!oriFolderResList.isEmpty()){
				Map<Alarm,CommitMessage> tmpMap = this.makeModelMFolderListForPro(oriFolderResList);
				if(!tmpMap.isEmpty()) resMap.putAll(tmpMap);
			}*/
			
			List<CopiedFolderCommitAlarmVDTO> cpFolderResList= ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchUnReadOriginFolderCode(oriFolderList);
			if(!cpFolderResList.isEmpty()){
				Map<Alarm,CommitMessage> tmpMap = this.makeModelMFolderListForCpPro(cpFolderResList);
				if(!tmpMap.isEmpty()) resMap.putAll(tmpMap);
			}
		}
		
		return resMap;
	}
	
	public Map<Alarm,CommitMessage> loadReadCooperSharedProCommitMessageForProject(Map<String,List<String>> oriProjectMap) throws DAOException, ParseException{

		Map<Alarm,CommitMessage> resMap = new HashMap<Alarm,CommitMessage>();
		List<String> oriFileList = oriProjectMap.get("file");
		List<String> oriFolderList = oriProjectMap.get("folder");
		
		if(!oriFileList.isEmpty()){
			/*List<OriginFileCommitAlarmVDTO> oriFileResList= ((OriginFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFileCommitAlarmVDAO")).searchReadOriginFileCode(oriFileList);
			if(!oriFileResList.isEmpty()){
				Map<Alarm,CommitMessage> tmpMap = this.makeModelMFileListForPro(oriFileResList);
				if(!tmpMap.isEmpty()) resMap.putAll(tmpMap);
			}*/
			
			List<CopiedFileCommitAlarmVDTO> cpFileResList= ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchReadOriginFileCode(oriFileList);
			if(!cpFileResList.isEmpty()){
				Map<Alarm,CommitMessage> tmpMap = this.makeModelMFileListForCpPro(cpFileResList);
				if(!tmpMap.isEmpty()) resMap.putAll(tmpMap);
			}
		}
		
		if(!oriFolderList.isEmpty()){
			/*List<OriginFolderCommitAlarmVDTO> oriFolderResList= ((OriginFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFolderCommitAlarmVDAO")).searchReadOriginFolderCode(oriFolderList);
			if(!oriFolderResList.isEmpty()){
				Map<Alarm,CommitMessage> tmpMap = this.makeModelMFolderListForPro(oriFolderResList);
				if(!tmpMap.isEmpty()) resMap.putAll(tmpMap);
			}*/
			
			List<CopiedFolderCommitAlarmVDTO> cpFolderResList= ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchReadOriginFolderCode(oriFolderList);
			if(!cpFolderResList.isEmpty()){
				Map<Alarm,CommitMessage> tmpMap = this.makeModelMFolderListForCpPro(cpFolderResList);
				if(!tmpMap.isEmpty()) resMap.putAll(tmpMap);
			}
		}
		return resMap;
	}
	
	
	@Override
	public Map<String,Map<GroupAlarm,CommitMessage>> loadUnReadTeamCommitMessageForTeam(List<String> teamOriProjectList,String teamCode) throws DAOException, ParseException{
	
		Map<String,Map<GroupAlarm,CommitMessage>> resMap = new HashMap<String,Map<GroupAlarm,CommitMessage>>();
		
		for(String oriProCode : teamOriProjectList){
			
			Map<GroupAlarm,CommitMessage> mapVal2 = new HashMap<GroupAlarm,CommitMessage>();
			
			List<FileCommitGroupAlarmVDTO> fileResList= ((FileCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FileCommitGroupAlarmVDAO")).searchUnReadOriginProjectCode(oriProCode);
			if(!fileResList.isEmpty()){
				Map<GroupAlarm,CommitMessage> tmpMap = this.makeModelFileListForPro(fileResList);
				if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
			}
			List<FolderCommitGroupAlarmVDTO> folderResList= ((FolderCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FolderCommitGroupAlarmVDAO")).searchUnReadOriginProject(oriProCode);
			if(!folderResList.isEmpty()){
				Map<GroupAlarm,CommitMessage> tmpMap = this.makeModelFolderListForPro(folderResList);
				if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
			}
			
			if(!mapVal2.isEmpty())resMap.put(oriProCode, mapVal2);
		}
		
		return resMap;
	}
	
	@Override
	public Map<String,Map<GroupAlarm,CommitMessage>> loadReadTeamCommitMessageForTeam(List<String> teamOriProjectList,String teamCode) throws DAOException, ParseException{

		Map<String,Map<GroupAlarm,CommitMessage>> resMap = new HashMap<String,Map<GroupAlarm,CommitMessage>>();
		
		for(String oriProCode : teamOriProjectList){
			
			Map<GroupAlarm,CommitMessage> mapVal2 = new HashMap<GroupAlarm,CommitMessage>();
			
			List<FileCommitGroupAlarmVDTO> fileResList= ((FileCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FileCommitGroupAlarmVDAO")).searchReadOriginProjectCode(oriProCode);
			if(!fileResList.isEmpty()){
				Map<GroupAlarm,CommitMessage> tmpMap = this.makeModelFileListForPro(fileResList);
				if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
			}
			List<FolderCommitGroupAlarmVDTO> folderResList= ((FolderCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FolderCommitGroupAlarmVDAO")).searchReadOriginProject(oriProCode);
			if(!folderResList.isEmpty()){
				Map<GroupAlarm,CommitMessage> tmpMap = this.makeModelFolderListForPro(folderResList);
				if(!tmpMap.isEmpty()) mapVal2.putAll(tmpMap);
			}
			
			if(!mapVal2.isEmpty())resMap.put(oriProCode, mapVal2);
		}
		
		return resMap;
	}

	@Override
	public Map<GroupAlarm,CommitMessage> loadUnReadSharedProCommitMessageForProject(String oriProjectCode) throws DAOException, ParseException{
		
		Map<GroupAlarm,CommitMessage> resMap = new HashMap<GroupAlarm,CommitMessage>();
		
		List<FileCommitGroupAlarmVDTO> fileResList= ((FileCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FileCommitGroupAlarmVDAO")).searchUnReadOriginProjectCode(oriProjectCode);
		if(!fileResList.isEmpty()){
			Map<GroupAlarm,CommitMessage> tmpMap = this.makeModelFileListForPro(fileResList);
			if(!tmpMap.isEmpty()) resMap.putAll(tmpMap);
		}
		
		List<FolderCommitGroupAlarmVDTO> folderResList= ((FolderCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FolderCommitGroupAlarmVDAO")).searchUnReadOriginProject(oriProjectCode);
		if(!folderResList.isEmpty()){
			Map<GroupAlarm,CommitMessage> tmpMap = this.makeModelFolderListForPro(folderResList);
			if(!tmpMap.isEmpty()) resMap.putAll(tmpMap);
		}
		
		
		return resMap;
	}
	
	@Override
	public Map<GroupAlarm,CommitMessage> loadReadSharedProCommitMessageForProject(String oriProjectCode) throws DAOException, ParseException{

		System.out.println("CommitMessageActionImpl loadReadSharedProCommitMessageForProject(String oriProjectCode)");
		Map<GroupAlarm,CommitMessage> resMap = new HashMap<GroupAlarm,CommitMessage>();
		
		List<FileCommitGroupAlarmVDTO> fileResList= ((FileCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FileCommitGroupAlarmVDAO")).searchReadOriginFileCode(oriProjectCode);
		if(!fileResList.isEmpty()){
			Map<GroupAlarm,CommitMessage> tmpMap = this.makeModelFileListForPro(fileResList);
			if(!tmpMap.isEmpty()) resMap.putAll(tmpMap);
		}
		
		List<FolderCommitGroupAlarmVDTO> folderResList= ((FolderCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FolderCommitGroupAlarmVDAO")).searchReadOriginProject(oriProjectCode);
		if(!folderResList.isEmpty()){
			Map<GroupAlarm,CommitMessage> tmpMap = this.makeModelFolderListForPro(folderResList);
			if(!tmpMap.isEmpty()) resMap.putAll(tmpMap);
		}
		
		if(resMap.isEmpty())System.out.println("resMap is Empty...");
		return resMap;
	}
	
	

///////////////////////////////////////            load end            ////////////////////////////    

	@Override
	public Map<String,Map<String,Map<Alarm,CommitMessage>>> searchUnReadTeamCommitMessage(String teamCode, Map<String,Map<String,List<String>>> oriProChildren, String managerCode, List<String> cooperCodeList) throws DAOException, ParseException {
		// TODO Auto-generated method stub
		
		//memberCode, oriprojectCode
		Map<String,Map<String,Map<Alarm,CommitMessage>>> resMap = new HashMap<String,Map<String,Map<Alarm,CommitMessage>>>();
		
		for(String cooperCode : cooperCodeList){
			
			Map<String,Map<Alarm,CommitMessage>> mapVal1 = new HashMap<String,Map<Alarm,CommitMessage>>();
			
			for(String oriProCode : oriProChildren.keySet()){
				
				List<OriginFileCommitAlarmVDTO> resVdtoList= ((OriginFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFileCommitAlarmVDAO")).searchUnReadSendAndRecOr(cooperCode, managerCode, oriProChildren.get(oriProCode).get("file"));
				if(!resVdtoList.isEmpty()){
					Map<Alarm,CommitMessage> tmpMap = this.makeModelMFileListForPro(resVdtoList);
					if(!tmpMap.isEmpty()) mapVal1.put(oriProCode, tmpMap);
				}

				List<OriginFolderCommitAlarmVDTO> resVdtoList2 = ((OriginFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFolderCommitAlarmVDAO")).searchUnReadSendAndRecOr(cooperCode, managerCode, oriProChildren.get(oriProCode).get("folder"));
				if(!resVdtoList2.isEmpty()){
					Map<Alarm,CommitMessage> tmpMap = this.makeModelMFolderListForPro(resVdtoList2);
					if(!tmpMap.isEmpty()) mapVal1.get(oriProCode).putAll(tmpMap);
				}
			}
			
			resMap.put(cooperCode, mapVal1);
		}
		
		return resMap;
	}
	

	@Override
	public Map<String,Map<String,Map<Alarm,CommitMessage>>> searchReadTeamCommitMessage(String teamCode, Map<String,Map<String,List<String>>> oriProChildren, String managerCode, List<String> cooperCodeList) throws DAOException, ParseException {
		// TODO Auto-generated method stub

		//memberCode, oriprojectCode
		Map<String,Map<String,Map<Alarm,CommitMessage>>> resMap = new HashMap<String,Map<String,Map<Alarm,CommitMessage>>>();
		
		for(String cooperCode : cooperCodeList){
			
			Map<String,Map<Alarm,CommitMessage>> mapVal1 = new HashMap<String,Map<Alarm,CommitMessage>>();
			
			for(String oriProCode : oriProChildren.keySet()){
				
				List<OriginFileCommitAlarmVDTO> resVdtoList= ((OriginFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFileCommitAlarmVDAO")).searchReadSendAndRecOr(cooperCode, managerCode, oriProChildren.get(oriProCode).get("file"));
				if(!resVdtoList.isEmpty()){
					Map<Alarm,CommitMessage> tmpMap = this.makeModelMFileListForPro(resVdtoList);
					if(!tmpMap.isEmpty()) mapVal1.put(oriProCode, tmpMap);
				}

				List<OriginFolderCommitAlarmVDTO> resVdtoList2 = ((OriginFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFolderCommitAlarmVDAO")).searchReadSendAndRecOr(cooperCode, managerCode, oriProChildren.get(oriProCode).get("folder"));
				if(!resVdtoList2.isEmpty()){
					Map<Alarm,CommitMessage> tmpMap = this.makeModelMFolderListForPro(resVdtoList2);
					if(!tmpMap.isEmpty()) mapVal1.get(oriProCode).putAll(tmpMap);
				}
			}
			resMap.put(cooperCode, mapVal1);
		}
		
		return resMap;
	}


	@Override
	public Map<String, Map<GroupAlarm,CommitMessage>> searchUnReadTeamGroupCommitMessage(String teamCode, List<String> oriProChildren, List<String> cooperList) throws DAOException, ParseException {
		// TODO Auto-generated method stub
		Map<String,Map<GroupAlarm,CommitMessage>> resMap =  new HashMap<String,Map<GroupAlarm,CommitMessage>>();
		
		for(String oriProjectCode : oriProChildren){
			
			Map<GroupAlarm,CommitMessage> mapVal1 = new HashMap<GroupAlarm,CommitMessage>();
			for(String cooperCode : cooperList){
			
				List<FolderCommitGroupAlarmVDTO> resFolderList= ((FolderCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FolderCommitGroupAlarmVDAO")).searchUnReadSendAnRec(teamCode, cooperCode, oriProjectCode);
				if(!resFolderList.isEmpty()){
					Map<GroupAlarm,CommitMessage> tmpMap= this.makeModelFolderListForPro(resFolderList);
					if(!tmpMap.isEmpty()) mapVal1.putAll(tmpMap);
				}

				List<FileCommitGroupAlarmVDTO> resFileList= ((FileCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FileCommitGroupAlarmVDAO")).searchUnReadSendAnRec(teamCode, cooperCode, oriProjectCode);
				if(!resFileList.isEmpty()){
					Map<GroupAlarm,CommitMessage> tmpMap= this.makeModelFileListForPro(resFileList);
					if(!tmpMap.isEmpty()) mapVal1.putAll(tmpMap);
				}
			}
			resMap.put(oriProjectCode, mapVal1);
		}
		
		return resMap;
	}

	@Override
	public Map<String, Map<GroupAlarm,CommitMessage>> searchReadTeamGroupCommitMessage(String teamCode, List<String> oriProChildren, List<String> cooperList) throws DAOException, ParseException {
		// TODO Auto-generated method stub
		Map<String,Map<GroupAlarm,CommitMessage>> resMap =  new HashMap<String,Map<GroupAlarm,CommitMessage>>();
		
		for(String oriProjectCode : oriProChildren){
			
			Map<GroupAlarm,CommitMessage> mapVal1 = new HashMap<GroupAlarm,CommitMessage>();
			for(String cooperCode : cooperList){
			
				List<FolderCommitGroupAlarmVDTO> resFolderList= ((FolderCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FolderCommitGroupAlarmVDAO")).searchReadSendAnRec(teamCode, cooperCode, oriProjectCode);
				if(!resFolderList.isEmpty()){
					Map<GroupAlarm,CommitMessage> tmpMap= this.makeModelFolderListForPro(resFolderList);
					if(!tmpMap.isEmpty()) mapVal1.putAll(tmpMap);
				}

				List<FileCommitGroupAlarmVDTO> resFileList= ((FileCommitGroupAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("FileCommitGroupAlarmVDAO")).searchReadSendAnRec(teamCode, cooperCode, oriProjectCode);
				if(!resFileList.isEmpty()){
					Map<GroupAlarm,CommitMessage> tmpMap= this.makeModelFileListForPro(resFileList);
					if(!tmpMap.isEmpty()) mapVal1.putAll(tmpMap);
				}
			}
			resMap.put(oriProjectCode, mapVal1);
		}
		
		return resMap;
	}
	
	
	public Map<Alarm,CommitMessage> searchReadCooperSharedProCommitMessage(String cooperatorCode, Map<String,List<String>> copiedProjectCode) throws DAOException, ParseException{
		
		Map<Alarm,CommitMessage> resMap = new HashMap<Alarm, CommitMessage>();

		//compare cpFolderList
		List<CopiedFolderCommitAlarmVDTO> folderResList= ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchReadSenderCodeCp(cooperatorCode, copiedProjectCode.get("folder"));
		if(!folderResList.isEmpty()){
			Map<Alarm,CommitMessage> tmpMap= this.makeModelMFolderListForCpPro(folderResList);
			if(!tmpMap.isEmpty()) resMap.putAll(tmpMap);					
		}
		
		//compare cpFileList
		List<CopiedFileCommitAlarmVDTO> fileResList= ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchReadSenderCodeCp(cooperatorCode, copiedProjectCode.get("file"));
		if(!fileResList.isEmpty()){
			Map<Alarm,CommitMessage> tmpMap= this.makeModelMFileListForCpPro(fileResList);
			if(!tmpMap.isEmpty()) resMap.putAll(tmpMap);					
		}
		
		return resMap;
	}
	
	public Map<Alarm,CommitMessage> searchUnReadCooperSharedProCommitMessage(String cooperatorCode, Map<String,List<String>> copiedProjectCode) throws DAOException, ParseException{

		Map<Alarm,CommitMessage> resMap = new HashMap<Alarm, CommitMessage>();

		List<String> cpFolderList = copiedProjectCode.get("folder");
		List<String> cpFileList = copiedProjectCode.get("file");
		
		//compare cpFolderList
		if(!cpFolderList.isEmpty()){
			List<CopiedFolderCommitAlarmVDTO> cpFolderResList= ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchUnReadSenderCodeCp(cooperatorCode, cpFolderList);
			if(!cpFolderResList.isEmpty()){
				Map<Alarm,CommitMessage> tmpMap= this.makeModelMFolderListForCpPro(cpFolderResList);
				if(!tmpMap.isEmpty()) resMap.putAll(tmpMap);					
			}
		}
		
		//compare cpFileList
		if(!cpFileList.isEmpty()){
			List<CopiedFileCommitAlarmVDTO> cpFileResList= ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchUnReadSenderCodeCp(cooperatorCode, cpFileList);
			if(!cpFileResList.isEmpty()){
				Map<Alarm,CommitMessage> tmpMap= this.makeModelMFileListForCpPro(cpFileResList);
				if(!tmpMap.isEmpty()) resMap.putAll(tmpMap);					
			}
		}
		
		
		return resMap;
	}
	
	public Map<String,Map<Alarm,CommitMessage>> searchReadSharedProCommitMessageOwnerRec(String ownerCode, Map<String,List<String>> oriProjectCode) throws DAOException, ParseException{

		Map<String,Map<Alarm,CommitMessage>> resMap = new HashMap<String,Map<Alarm,CommitMessage>>();

		List<String> oriFolderList = oriProjectCode.get("folder");
		List<String> oriFileList = oriProjectCode.get("file");
		
		//compare cpFolderList
		if(!oriFolderList.isEmpty()){
			List<CopiedFolderCommitAlarmVDTO> cpFolderResList= ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchReadReceiverCodeOr(ownerCode, oriFolderList);
			if(!cpFolderResList.isEmpty()){
				Map<Alarm,CommitMessage> tmpMap= this.makeModelMFolderListForCpPro(cpFolderResList);
				Map<String,Map<Alarm,CommitMessage>> val = this.makeModelmemberMapForManager(tmpMap);
				if(!tmpMap.isEmpty()) resMap.putAll(val);					
			}
		}
		
		//compare cpFileList
		if(!oriFileList.isEmpty()){
			List<CopiedFileCommitAlarmVDTO> cpFileResList= ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchReadReceiverCodeOr(ownerCode, oriFileList);
			if(!cpFileResList.isEmpty()){
				Map<Alarm,CommitMessage> tmpMap= this.makeModelMFileListForCpPro(cpFileResList);
				Map<String,Map<Alarm,CommitMessage>> val = this.makeModelmemberMapForManager(tmpMap);
				if(!tmpMap.isEmpty()) resMap.putAll(val);					
			}
		}
		
		return resMap;
	}
	public Map<String,Map<Alarm,CommitMessage>> searchUnReadSharedProCommitMessageOwnerRec(String ownerCode, Map<String,List<String>> oriProjectCode) throws DAOException, ParseException{

		Map<String,Map<Alarm,CommitMessage>> resMap = new HashMap<String,Map<Alarm,CommitMessage>>();

		List<String> oriFolderList = oriProjectCode.get("folder");
		List<String> oriFileList = oriProjectCode.get("file");
		
		//compare cpFolderList
		if(!oriFolderList.isEmpty()){
			List<CopiedFolderCommitAlarmVDTO> cpFolderResList= ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchUnReadReceiverCodeOr(ownerCode, oriFolderList);
			if(!cpFolderResList.isEmpty()){
				Map<Alarm,CommitMessage> tmpMap= this.makeModelMFolderListForCpPro(cpFolderResList);
				Map<String,Map<Alarm,CommitMessage>> val = this.makeModelmemberMapForManager(tmpMap);
				if(!tmpMap.isEmpty()) resMap.putAll(val);					
			}
		}
		
		//compare cpFileList
		if(!oriFileList.isEmpty()){
			List<CopiedFileCommitAlarmVDTO> cpFileResList= ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchUnReadReceiverCodeOr(ownerCode, oriFileList);
			if(!cpFileResList.isEmpty()){
				Map<Alarm,CommitMessage> tmpMap= this.makeModelMFileListForCpPro(cpFileResList);
				Map<String,Map<Alarm,CommitMessage>> val = this.makeModelmemberMapForManager(tmpMap);
				if(!tmpMap.isEmpty()) resMap.putAll(val);					
			}
		}
		
		return resMap;
	}

	
	public Map<Alarm,CommitMessage> searchCommitMessageCode(String msgCode) throws DAOException, ParseException{
		
		List<OriginFolderCommitAlarmVDTO> oriFolderResList = ((OriginFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFolderCommitAlarmVDAO")).searchMessageCode(msgCode);
		List<OriginFileCommitAlarmVDTO> oriFileResList = new ArrayList<OriginFileCommitAlarmVDTO>();
		if(oriFolderResList.isEmpty()){
			oriFileResList= ((OriginFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("OriginFileCommitAlarmVDAO")).searchMessageCode(msgCode);
			if(!oriFileResList.isEmpty())return this.makeModelMFileListForPro(oriFileResList);
		}else{
			return this.makeModelMFolderListForPro(oriFolderResList);
		}
	
		List<CopiedFolderCommitAlarmVDTO> cpFolderResList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		if(oriFileResList.isEmpty()){
			cpFolderResList = ((CopiedFolderCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFolderCommitAlarmVDAO")).searchMessageCode(msgCode);
		}
		
		if(cpFolderResList.isEmpty()){
			List<CopiedFileCommitAlarmVDTO> cpFileResList = ((CopiedFileCommitAlarmVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CopiedFileCommitAlarmVDAO")).searchMessageCode(msgCode);
			if(!cpFileResList.isEmpty()) return this.makeModelMFileListForCpPro(cpFileResList);
		}
		else return this.makeModelMFolderListForCpPro(cpFolderResList);
		
		return new HashMap<Alarm, CommitMessage>();
	}
////////////////////////////           insert             ///////////////////////////////////////////
	

	@Override
	public Map<Alarm,CommitMessage> insertTeamCommitMessageSendToOwner(CommitMessage commitMsg) throws DAOException {
		// TODO Auto-generated method stub
		
		Map<Alarm,CommitMessage> resMap = new HashMap<Alarm,CommitMessage>();
		//insert message_tb
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		df.setTimeZone(commitMsg.getSendDate().getTimeZone());
		String messageCode = ((MessageDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("MessageDAO")).insertMesage(new MessageDTO(commitMsg.getSenderCode(), commitMsg.getReceiverCode(), df.format(commitMsg.getSendDate().getTime()), commitMsg.getTitle(), commitMsg.getContent()));
		if(messageCode==null)return resMap;
		commitMsg.setMessageCode(messageCode);
		
		//insert commitMessage_tb
		for(List<String> commitCodeList : commitMsg.getCommitMap().values()){
			if(!commitCodeList.isEmpty()){
			for(String commitCode : commitCodeList){
				((CommitMessageDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CommitMessageDAO")).insertCommitMessage(new CommitMessageDTO(messageCode, commitCode));
			}	
			}
		}	
		
		//insert Alarm_tb
		String alarmCode = ((AlarmDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("alarmDAO")).insertAlarm(new AlarmDTO(messageCode));
		if(alarmCode==null)return resMap;
		Alarm alarm = new Alarm(alarmCode, messageCode, null);
		
		//commit_tb merge true;
		List<String> coCodeList = new ArrayList<String>();
		for(List<String> commitCodeList : commitMsg.getCommitMap().values()){
			if(!commitCodeList.isEmpty()){
				coCodeList.addAll(commitCodeList); 
			}
		}	
		if(!coCodeList.isEmpty())((CommitDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("commitDAO")).updateCommitMerge(coCodeList, 1);
		resMap.put(alarm, commitMsg);
		return resMap;
	}

	@Override
	public CommitMessage insertSharedProjectCommitMessageSendToOwner(CommitMessage commitMsg) throws DAOException {
		// TODO Auto-generated method stub
		System.out.println("\n     CommitMEssageManger   insertSharedProjectCommitMessageSendToOwner   line  1112");
		Map<Alarm,CommitMessage> resMap = new HashMap<Alarm, CommitMessage>();
		
		//insert message_tb
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		df.setTimeZone(commitMsg.getSendDate().getTimeZone());
		String messageCode = ((MessageDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("MessageDAO")).insertMesage(new MessageDTO(commitMsg.getSenderCode(), commitMsg.getReceiverCode(), df.format(commitMsg.getSendDate().getTime()), commitMsg.getTitle(), commitMsg.getContent()));
		if(messageCode==null)return null;
		System.out.println("mssCode  "+messageCode);
		commitMsg.setMessageCode(messageCode);
		
		//insert commitMessage_tb
		//insert commitMessage_tb
		for(List<String> commitCodeList : commitMsg.getCommitMap().values()){
			if(!commitCodeList.isEmpty()){
			for(String commitCode : commitCodeList){
				System.out.println(commitCode);
				((CommitMessageDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CommitMessageDAO")).insertCommitMessage(new CommitMessageDTO(messageCode, commitCode));
			}	
			}
		}		
		
		return commitMsg;
	}

	@Override
	public CommitMessage insertTeamCommitMsgFromOwner(CommitMessage commitMsg)
			throws DAOException {
		// TODO Auto-generated method stub
		//insert message_tb
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		df.setTimeZone(commitMsg.getSendDate().getTimeZone());
		String messageCode = ((MessageDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("MessageDAO")).insertMesage(new MessageDTO(commitMsg.getSenderCode(), commitMsg.getReceiverCode(), df.format(commitMsg.getSendDate().getTime()), commitMsg.getTitle(), commitMsg.getContent()));
		
		//insert commitMessage_tb
		for(List<String> commitCodeList : commitMsg.getCommitMap().values()){
			if(!commitCodeList.isEmpty()){
			for(String commitCode : commitCodeList){
				((CommitMessageDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CommitMessageDAO")).insertCommitMessage(new CommitMessageDTO(messageCode, commitCode));
			}	
			}
		}
		return null;
	}
	
	@Override
	public Map<CommitMessage,List<GroupAlarm>> insertSharedProCommitMsgFromOwner(CommitMessage commitMsg,List<String> cooperList) throws DAOException {
		// TODO Auto-generated method stub
		
		Map<CommitMessage,List<GroupAlarm>> resMap = new HashMap<CommitMessage,List<GroupAlarm>>();
		//insert message_tb
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		df.setTimeZone(commitMsg.getSendDate().getTimeZone());
		String messageCode = ((MessageDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("MessageDAO")).insertMesage(new MessageDTO(commitMsg.getSenderCode(), commitMsg.getReceiverCode(), df.format(commitMsg.getSendDate().getTime()), commitMsg.getTitle(), commitMsg.getContent()));
		if(messageCode!=null){
			commitMsg.setMessageCode(messageCode);

			//insert commitMessage_tb
			for(List<String> commitCodeList : commitMsg.getCommitMap().values()){
				if(!commitCodeList.isEmpty()){
				for(String commitCode : commitCodeList){
					((CommitMessageDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("CommitMessageDAO")).insertCommitMessage(new CommitMessageDTO(messageCode, commitCode));
				}	
				}
			}
			
			//insert Alarm_tb
			List<GroupAlarm> list = new ArrayList<GroupAlarm>();
			for(String cooperCode : cooperList){
				GroupAlarmDTO galarm= new GroupAlarmDTO(messageCode, cooperCode);
				String gAlarmCode = ((GroupAlarmDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("GroupAlarmDAO")).insertGroupAlarm(galarm);
				if(gAlarmCode!=null){
					GroupAlarm gAlarm = new GroupAlarm(gAlarmCode,messageCode);
					gAlarm.putMemberCheckDate(cooperCode, null);
					list.add(gAlarm);
				}
			}
			if(!list.isEmpty())resMap.put(commitMsg, list);
			
			//commit_tb merge true;
			List<String> coCodeList = new ArrayList<String>();
			for(List<String> commitCodeList : commitMsg.getCommitMap().values()){
				if(!commitCodeList.isEmpty()){
					coCodeList.addAll(commitCodeList); 
				}
			}	
			if(!coCodeList.isEmpty())((CommitDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("commitDAO")).updateCommitMerge(coCodeList, 1);
			
			//test
			System.out.println("For... check is there a GoupAlarm???   :  "+resMap.get(commitMsg).iterator().next().getAlarmCode());
			System.out.println("For check....    "+ resMap.keySet().iterator().next().getMessageCode());
		}
		
		return resMap;
	}
	
/////////////////////////////            update            /////////////////////////////////////////////////
	

	@Override
	public String updateAlarmCheckDate(String messageCode, String alarmCode) throws DAOException {
		// TODO Auto-generated method stub
		GregorianCalendar cal = new GregorianCalendar();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		df.setTimeZone(cal.getTimeZone());
		String date =  df.format(cal.getTime());
		if(((AlarmDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("alarmDAO")).updateChecks(alarmCode,date))return date;
		return null;
	}
	
	
////////////////////////////           make             ///////////////////////////////////////////	

	private Map<String,Map<Alarm, CommitMessage>> makeModelmemberMapForManager(Map<Alarm, CommitMessage> alarmMap) throws ParseException, DAOException{
		
		
		Map<String,Map<Alarm, CommitMessage>> resMap = new HashMap<String,Map<Alarm, CommitMessage>>();
		
		for(Alarm alarm : alarmMap.keySet()){
			CommitMessage commitM = alarmMap.get(alarm);
			String member = commitM.getSenderCode();
			if(!resMap.containsKey(member)){
				Map<Alarm, CommitMessage> val = new HashMap<Alarm, CommitMessage>();
				val.put(alarm, alarmMap.get(alarm));
				resMap.put(member, val);
			}else{
				resMap.get(member).put(alarm,alarmMap.get(alarm));
			}
		}
		
		return resMap;
	}
	
	private Map<GroupAlarm,CommitMessage> makeModelFileListForPro(List<FileCommitGroupAlarmVDTO> resProjectGroupList) throws ParseException, DAOException{
		
		System.out.println("CommitMessageActionImpl     makeModelFileListForPro");

		Map<GroupAlarm,CommitMessage> resMap = new HashMap<GroupAlarm,CommitMessage>();
		List<String> gAlarmCodeList = new ArrayList<String>();
		List<String> commitMsgCodeList = new ArrayList<String>();
		
		for(FileCommitGroupAlarmVDTO vdto : resProjectGroupList){

			System.out.println("vdto   ::  "+vdto.getMessageCode());
			GregorianCalendar cal1=null;
			if(vdto.getCheckDate()!=null){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date = df.parse(vdto.getCheckDate());
				cal1 = (GregorianCalendar)Calendar.getInstance();
				cal1.setTime(date);
			}
			
			String msgCode = vdto.getMessageCode();
			String commitCode = vdto.getCommitCode();
			String oriFileCode = vdto.getOriginFileCode();
			String gAlarmCode = vdto.getGroupAlarmCode();
			String memberCode = vdto.getReceiverCode();
			GregorianCalendar checkDate = null;
			if(vdto.getCheckDate()!=null){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date = df.parse(vdto.getCheckDate());
				checkDate = (GregorianCalendar)Calendar.getInstance();
				checkDate.setTime(date);
			}
			
			if(gAlarmCodeList.contains(gAlarmCode)){
				for(GroupAlarm gAlarm : resMap.keySet()){
					if(gAlarm.getAlarmCode().equals(gAlarm)){
						CommitMessage commitMsg = resMap.get(gAlarm);
						commitMsg.addCommitCode(commitCode, oriFileCode);
						gAlarm.putMemberCheckDate(memberCode, checkDate);
					}
				}
			}else{
				MessageDTO mdto = ((MessageDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("MessageDAO")).searchMessageCode(vdto.getMessageCode());
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date = df.parse(mdto.getSendDate());
				GregorianCalendar send = (GregorianCalendar)Calendar.getInstance();
				send.setTime(date);
				CommitMessage m = new CommitMessage(vdto.getMessageCode(), mdto.getTitle(), mdto.getContent(), vdto.getSenderCode(), vdto.getOriginProjectCode(), send);
				m.addCommitCode(commitCode, oriFileCode);
				commitMsgCodeList.add(msgCode);
				
				GroupAlarm alarm = new GroupAlarm(gAlarmCode, msgCode);
				alarm.putMemberCheckDate(memberCode, cal1);
				gAlarmCodeList.add(gAlarmCode);
				
				resMap.put(alarm,m);
			}
		}
		
		return resMap;
	}

	private Map<GroupAlarm,CommitMessage> makeModelFolderListForPro(List<FolderCommitGroupAlarmVDTO> resProjectGroupList) throws ParseException, DAOException{
		
		System.out.println("CommitMessageActionImpl     makeModelFolderListForPro");
		Map<GroupAlarm,CommitMessage> resMap = new HashMap<GroupAlarm,CommitMessage>();
		List<String> gAlarmCodeList = new ArrayList<String>();
		List<String> commitMsgCodeList = new ArrayList<String>();
		
		for(FolderCommitGroupAlarmVDTO vdto : resProjectGroupList){

			System.out.println("vdto : "+vdto.getMessageCode());
			GregorianCalendar cal1=null;
			if(vdto.getCheckDate()!=null){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date = df.parse(vdto.getCheckDate());
				cal1 = (GregorianCalendar)Calendar.getInstance();
				cal1.setTime(date);
			}
			
			String msgCode = vdto.getMessageCode();
			String commitCode = vdto.getCommitCode();
			String oriFolderCode = vdto.getOriginFolderCode();
			String gAlarmCode = vdto.getGroupAlarmCode();
			String memberCode = vdto.getReceiverCode();
			GregorianCalendar checkDate = null;
			if(vdto.getCheckDate()!=null){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date = df.parse(vdto.getCheckDate());
				checkDate = (GregorianCalendar)Calendar.getInstance();
				checkDate.setTime(date);
			}
			
			if(gAlarmCodeList.contains(gAlarmCode)){
				for(GroupAlarm gAlarm : resMap.keySet()){
					if(gAlarm.getAlarmCode().equals(gAlarm)){
						CommitMessage commitMsg = resMap.get(gAlarm);
						commitMsg.addCommitCode(commitCode, oriFolderCode);
						gAlarm.putMemberCheckDate(memberCode, checkDate);
					}
				}
			}else{
				MessageDTO mdto = ((MessageDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("MessageDAO")).searchMessageCode(vdto.getMessageCode());
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date = df.parse(mdto.getSendDate());
				GregorianCalendar send = (GregorianCalendar)Calendar.getInstance();
				send.setTime(date);
				CommitMessage m = new CommitMessage(vdto.getMessageCode(), mdto.getTitle(), mdto.getContent(), vdto.getSenderCode(), vdto.getOriginProjectCode(), send);
				m.addCommitCode(commitCode, oriFolderCode);
				commitMsgCodeList.add(msgCode);
				
				GroupAlarm alarm = new GroupAlarm(gAlarmCode, msgCode);
				alarm.putMemberCheckDate(memberCode, cal1);
				gAlarmCodeList.add(gAlarmCode);
				
				resMap.put(alarm,m);
			}
		}
		
		return resMap;
	}
	

	private Map<Alarm, CommitMessage> makeModelMFileListForPro(List<OriginFileCommitAlarmVDTO> resProjectList) throws ParseException, DAOException{
		
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!        CommitMessageActionImpl        makeModelMFileListForPro");
		Map<Alarm, CommitMessage> resMap = new HashMap<Alarm, CommitMessage>();
		List<String> alarmCodeList = new ArrayList<String>();
		System.out.println("list size : "+resProjectList.size());
		
		for(OriginFileCommitAlarmVDTO vdto : resProjectList){
			
			System.out.println("vdto  : : : "+vdto.getMessageCode());
			GregorianCalendar cal1=null;
			String alarmCode = vdto.getAlarmCode();
			if(vdto.getCheckDate()!=null){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date = df.parse(vdto.getCheckDate());
				cal1 = (GregorianCalendar)Calendar.getInstance();
				cal1.setTime(date);
			}
			
			String memberCode = vdto.getReceiverCode();
			String commitCode = vdto.getCommitCode();
			String oriFileCode = vdto.getOriginFileCode();
			
			if(alarmCodeList.contains(alarmCode)){
				for(Alarm alarm : resMap.keySet()){
					if(alarm.getAlarmCode().equals(alarmCode)){
						
						//add commitCode
						resMap.get(alarm).addCommitCode(commitCode, oriFileCode);
					}
				}
			}else {
				alarmCodeList.add(alarmCode);
				MessageDTO mdto = ((MessageDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("MessageDAO")).searchMessageCode(vdto.getMessageCode());
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date = df.parse(mdto.getSendDate());
				GregorianCalendar send = (GregorianCalendar)Calendar.getInstance();
				send.setTime(date);
				System.out.println("sed date  ::::  "+send);
				System.out.println("sed date  ::::  "+mdto.getContent());
				CommitMessage m = new CommitMessage(vdto.getMessageCode(), mdto.getTitle(), mdto.getContent(), vdto.getSenderCode(), memberCode, send);
				m.addCommitCode(commitCode, oriFileCode);
				if(m.getSendDate()==null)System.out.println("m.getSendDate()==null");
				else System.out.println("m.getSendDate()!=null");
				
				Alarm alarm = new Alarm(alarmCode, vdto.getMessageCode(), cal1);
				
				resMap.put(alarm, m);
			}
		}
		
		return resMap;
	}

	private void makeModelMFileListForPro(List<OriginFileCommitAlarmVDTO> resProjectList, Map<Alarm, CommitMessage> resMap,List<String> alarmCodeList) throws ParseException, DAOException{
		
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!        CommitMessageActionImpl        makeModelMFileListForPro");
		System.out.println("list size : "+resProjectList.size());
		
		for(OriginFileCommitAlarmVDTO vdto : resProjectList){
			
			System.out.println("vdto  : : : "+vdto.getMessageCode());
			GregorianCalendar cal1=null;
			String alarmCode = vdto.getAlarmCode();
			if(vdto.getCheckDate()!=null){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date = df.parse(vdto.getCheckDate());
				cal1 = (GregorianCalendar)Calendar.getInstance();
				cal1.setTime(date);
			}
			
			String memberCode = vdto.getReceiverCode();
			String commitCode = vdto.getCommitCode();
			String oriFileCode = vdto.getOriginFileCode();
			
			if(alarmCodeList.contains(alarmCode)){
				for(Alarm alarm : resMap.keySet()){
					if(alarm.getAlarmCode().equals(alarmCode)){
						
						//add commitCode
						resMap.get(alarm).addCommitCode(commitCode, oriFileCode);
					}
				}
			}else {
				alarmCodeList.add(alarmCode);
				MessageDTO mdto = ((MessageDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("MessageDAO")).searchMessageCode(vdto.getMessageCode());
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date = df.parse(mdto.getSendDate());
				GregorianCalendar send = (GregorianCalendar)Calendar.getInstance();
				send.setTime(date);
				System.out.println("sed date  ::::  "+send);
				System.out.println("sed date  ::::  "+mdto.getContent());
				CommitMessage m = new CommitMessage(vdto.getMessageCode(), mdto.getTitle(), mdto.getContent(), vdto.getSenderCode(), memberCode, send);
				m.addCommitCode(commitCode, oriFileCode);
				if(m.getSendDate()==null)System.out.println("m.getSendDate()==null");
				else System.out.println("m.getSendDate()!=null");
				
				Alarm alarm = new Alarm(alarmCode, vdto.getMessageCode(), cal1);
				
				resMap.put(alarm, m);
			}
		}
	}

	private Map<Alarm, CommitMessage> makeModelMFileListForCpPro(List<CopiedFileCommitAlarmVDTO> resProjectList) throws ParseException, DAOException{
		
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!        CommitMessageActionImpl        makeModelMFileListForPro");
		Map<Alarm, CommitMessage> resMap = new HashMap<Alarm, CommitMessage>();
		List<String> alarmCodeList = new ArrayList<String>();
		System.out.println("list size : "+resProjectList.size());
		
		for(CopiedFileCommitAlarmVDTO vdto : resProjectList){
			
			System.out.println("vdto  : : : "+vdto.getMessageCode());
			GregorianCalendar cal1=null;
			String alarmCode = vdto.getAlarmCode();
			if(vdto.getCheckDate()!=null){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date = df.parse(vdto.getCheckDate());
				cal1 = (GregorianCalendar)Calendar.getInstance();
				cal1.setTime(date);
			}
			
			String memberCode = vdto.getReceiverCode();
			String commitCode = vdto.getCommitCode();
			String cpFileCode = vdto.getCopiedFileCode();
			
			if(alarmCodeList.contains(alarmCode)){
				for(Alarm alarm : resMap.keySet()){
					if(alarm.getAlarmCode().equals(alarmCode)){
						
						//add commitCode
						resMap.get(alarm).addCommitCode(commitCode, cpFileCode);
					}
				}
			}else {
				alarmCodeList.add(alarmCode);
				MessageDTO mdto = ((MessageDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("MessageDAO")).searchMessageCode(vdto.getMessageCode());
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date = df.parse(mdto.getSendDate());
				GregorianCalendar send = (GregorianCalendar)Calendar.getInstance();
				send.setTime(date);
				System.out.println("sed date  ::::  "+send);
				System.out.println("sed date  ::::  "+mdto.getContent());
				CommitMessage m = new CommitMessage(vdto.getMessageCode(), mdto.getTitle(), mdto.getContent(), vdto.getSenderCode(), memberCode, send);
				m.addCommitCode(commitCode, cpFileCode);
				if(m.getSendDate()==null)System.out.println("m.getSendDate()==null");
				else System.out.println("m.getSendDate()!=null");
				
				Alarm alarm = new Alarm(alarmCode, vdto.getMessageCode(), cal1);
				
				resMap.put(alarm, m);
			}
		}
		
		return resMap;
	}

	private void makeModelMFileListForCpPro(List<CopiedFileCommitAlarmVDTO> resProjectList, Map<Alarm, CommitMessage> resMap, List<String> alarmCodeList) throws ParseException, DAOException{
		
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!        CommitMessageActionImpl        makeModelMFileListForPro");
		System.out.println("list size : "+resProjectList.size());
		
		for(CopiedFileCommitAlarmVDTO vdto : resProjectList){
			
			System.out.println("vdto  : : : "+vdto.getMessageCode());
			GregorianCalendar cal1=null;
			String alarmCode = vdto.getAlarmCode();
			if(vdto.getCheckDate()!=null){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date = df.parse(vdto.getCheckDate());
				cal1 = (GregorianCalendar)Calendar.getInstance();
				cal1.setTime(date);
			}
			
			String memberCode = vdto.getReceiverCode();
			String commitCode = vdto.getCommitCode();
			String cpFolderCode = vdto.getCopiedFileCode();
			
			if(alarmCodeList.contains(alarmCode)){
				for(Alarm alarm : resMap.keySet()){
					if(alarm.getAlarmCode().equals(alarmCode)){
						
						//add commitCode
						resMap.get(alarm).addCommitCode(commitCode, cpFolderCode);
					}
				}
			}else {
				alarmCodeList.add(alarmCode);
				MessageDTO mdto = ((MessageDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("MessageDAO")).searchMessageCode(vdto.getMessageCode());
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date = df.parse(mdto.getSendDate());
				GregorianCalendar send = (GregorianCalendar)Calendar.getInstance();
				send.setTime(date);
				System.out.println("sed date  ::::  "+send);
				System.out.println("sed date  ::::  "+mdto.getContent());
				CommitMessage m = new CommitMessage(vdto.getMessageCode(), mdto.getTitle(), mdto.getContent(), vdto.getSenderCode(), memberCode, send);
				m.addCommitCode(commitCode, cpFolderCode);
				if(m.getSendDate()==null)System.out.println("m.getSendDate()==null");
				else System.out.println("m.getSendDate()!=null");
				
				Alarm alarm = new Alarm(alarmCode, vdto.getMessageCode(), cal1);
				
				resMap.put(alarm, m);
			}
		}
		
	}

	private Map<Alarm, CommitMessage> makeModelMFolderListForPro(List<OriginFolderCommitAlarmVDTO> resProjectList) throws ParseException, DAOException{
		
		Map<Alarm, CommitMessage> resMap = new HashMap<Alarm, CommitMessage>();
		List<String> alarmCodeList = new ArrayList<String>();
		
		for(OriginFolderCommitAlarmVDTO oriVDTO : resProjectList){
			
			GregorianCalendar cal1=null;
			String alarmCode = oriVDTO.getAlarmCode();
			if(oriVDTO.getCheckDate()!=null){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date = df.parse(oriVDTO.getCheckDate());
				cal1 = (GregorianCalendar)Calendar.getInstance();
				cal1.setTime(date);
			}
			
			String commitCode = oriVDTO.getCommitCode();
			String oriFolderCdoe = oriVDTO.getOriginFolderCode();
			
			if(alarmCodeList.contains(alarmCode)){
				for(Alarm alarm : resMap.keySet()){
					if(alarm.getAlarmCode().equals(alarmCode)){
						
						//add commitCode
						resMap.get(alarm).addCommitCode(commitCode, oriFolderCdoe);
					}
				}
			}else {
				alarmCodeList.add(alarmCode);
				MessageDTO mdto = ((MessageDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("MessageDAO")).searchMessageCode(oriVDTO.getMessageCode());
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date = df.parse(mdto.getSendDate());
				GregorianCalendar send = (GregorianCalendar)Calendar.getInstance();
				send.setTime(date);
				CommitMessage m = new CommitMessage(oriVDTO.getMessageCode(), mdto.getTitle(), mdto.getContent(), oriVDTO.getSenderCode(), oriVDTO.getReceiverCode(), send);
				m.addCommitCode(commitCode, oriFolderCdoe);

				Alarm alarm = new Alarm(alarmCode, oriVDTO.getMessageCode(), cal1);
				
				resMap.put(alarm, m);				
			}
		}
		
		return resMap;
	}

	private void makeModelMFolderListForPro(List<OriginFolderCommitAlarmVDTO> resProjectList, Map<Alarm, CommitMessage> resMap,List<String> alarmCodeList) throws ParseException, DAOException{
				
		for(OriginFolderCommitAlarmVDTO oriVDTO : resProjectList){
			
			GregorianCalendar cal1=null;
			String alarmCode = oriVDTO.getAlarmCode();
			if(oriVDTO.getCheckDate()!=null){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date = df.parse(oriVDTO.getCheckDate());
				cal1 = (GregorianCalendar)Calendar.getInstance();
				cal1.setTime(date);
			}
			
			String commitCode = oriVDTO.getCommitCode();
			String oriFolderCode = oriVDTO.getOriginFolderCode();
			
			if(alarmCodeList.contains(alarmCode)){
				for(Alarm alarm : resMap.keySet()){
					if(alarm.getAlarmCode().equals(alarmCode)){
						
						//add commitCode
						resMap.get(alarm).addCommitCode(commitCode, oriFolderCode);
					}
				}
			}else {
				alarmCodeList.add(alarmCode);
				MessageDTO mdto = ((MessageDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("MessageDAO")).searchMessageCode(oriVDTO.getMessageCode());
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date = df.parse(mdto.getSendDate());
				GregorianCalendar send = (GregorianCalendar)Calendar.getInstance();
				send.setTime(date);
				CommitMessage m = new CommitMessage(oriVDTO.getMessageCode(), mdto.getTitle(), mdto.getContent(), oriVDTO.getSenderCode(), oriVDTO.getReceiverCode(), send);
				m.addCommitCode(commitCode, oriFolderCode);

				Alarm alarm = new Alarm(alarmCode, oriVDTO.getMessageCode(), cal1);
				
				resMap.put(alarm, m);				
			}
		}
		
		
	}

	private Map<Alarm, CommitMessage> makeModelMFolderListForCpPro(List<CopiedFolderCommitAlarmVDTO> resProjectList) throws ParseException, DAOException{
		
		Map<Alarm, CommitMessage> resMap = new HashMap<Alarm, CommitMessage>();
		List<String> alarmCodeList = new ArrayList<String>();
		
		for(CopiedFolderCommitAlarmVDTO cpVDTO : resProjectList){
			
			GregorianCalendar cal1=null;
			String alarmCode = cpVDTO.getAlarmCode();
			if(cpVDTO.getCheckDate()!=null){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date = df.parse(cpVDTO.getCheckDate());
				cal1 = (GregorianCalendar)Calendar.getInstance();
				cal1.setTime(date);
			}
			
			String commitCode = cpVDTO.getCommitCode();
			String cpFolderCode = cpVDTO.getCopiedFolderCode();
			
			if(alarmCodeList.contains(alarmCode)){
				for(Alarm alarm : resMap.keySet()){
					if(alarm.getAlarmCode().equals(alarmCode)){
						
						//add commitCode
						resMap.get(alarm).addCommitCode(commitCode, cpFolderCode);
					}
				}
			}else {
				alarmCodeList.add(alarmCode);
				MessageDTO mdto = ((MessageDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("MessageDAO")).searchMessageCode(cpVDTO.getMessageCode());
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date = df.parse(mdto.getSendDate());
				GregorianCalendar send = (GregorianCalendar)Calendar.getInstance();
				send.setTime(date);
				CommitMessage m = new CommitMessage(cpVDTO.getMessageCode(), mdto.getTitle(), mdto.getContent(), cpVDTO.getSenderCode(), cpVDTO.getReceiverCode(), send);
				m.addCommitCode(commitCode, cpFolderCode);

				Alarm alarm = new Alarm(alarmCode, cpVDTO.getMessageCode(), cal1);
				
				resMap.put(alarm, m);				
			}
		}
		
		return resMap;
	}
	private void makeModelMFolderListForCpPro(List<CopiedFolderCommitAlarmVDTO> resProjectList, Map<Alarm, CommitMessage> resMap, List<String> alarmCodeList) throws ParseException, DAOException{
		
		for(CopiedFolderCommitAlarmVDTO cpVDTO : resProjectList){
			
			GregorianCalendar cal1=null;
			String alarmCode = cpVDTO.getAlarmCode();
			if(cpVDTO.getCheckDate()!=null){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date = df.parse(cpVDTO.getCheckDate());
				cal1 = (GregorianCalendar)Calendar.getInstance();
				cal1.setTime(date);
			}
			
			String commitCode = cpVDTO.getCommitCode();
			String cpFolderCode = cpVDTO.getCopiedFolderCode();
			
			if(alarmCodeList.contains(alarmCode)){
				for(Alarm alarm : resMap.keySet()){
					if(alarm.getAlarmCode().equals(alarmCode)){
						
						//add commitCode
						resMap.get(alarm).addCommitCode(commitCode, cpFolderCode);
					}
				}
			}else {
				alarmCodeList.add(alarmCode);
				MessageDTO mdto = ((MessageDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("MessageDAO")).searchMessageCode(cpVDTO.getMessageCode());
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date = df.parse(mdto.getSendDate());
				GregorianCalendar send = (GregorianCalendar)Calendar.getInstance();
				send.setTime(date);
				CommitMessage m = new CommitMessage(cpVDTO.getMessageCode(), mdto.getTitle(), mdto.getContent(), cpVDTO.getSenderCode(), cpVDTO.getReceiverCode(), send);
				m.addCommitCode(commitCode, cpFolderCode);

				Alarm alarm = new Alarm(alarmCode, cpVDTO.getMessageCode(), cal1);
				
				resMap.put(alarm, m);				
			}
		}
	}



	private Map<Alarm, CommitMessage> makeModelMsgMapForCpPro(List<CopiedFolderCommitAlarmVDTO> resCpFolderList,List<OriginFolderCommitAlarmVDTO> resOriFolderList,List<CopiedFileCommitAlarmVDTO> resCpFileList,List<OriginFileCommitAlarmVDTO> resOriFileList) throws ParseException, DAOException{
		
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!        CommitMessageActionImpl        makeModelMFileListForPro");
		Map<Alarm, CommitMessage> resMap = new HashMap<Alarm, CommitMessage>();
		List<String> alarmCodeList = new ArrayList<String>();

		if(!resOriFolderList.isEmpty())this.makeModelMFolderListForPro(resOriFolderList, resMap, alarmCodeList);
		if(!resOriFileList.isEmpty())this.makeModelMFileListForPro(resOriFileList, resMap, alarmCodeList);
		
		if(!resCpFolderList.isEmpty())this.makeModelMFolderListForCpPro(resCpFolderList, resMap, alarmCodeList);
		if(!resCpFileList.isEmpty())this.makeModelMFileListForCpPro(resCpFileList, resMap, alarmCodeList);
		
		//test Data
		System.out.println("------------------------------------------");
		if(!resMap.isEmpty()){
			for(Alarm alarm : resMap.keySet()){
				System.out.println("start alarmCode ==>  "+alarm.getAlarmCode());
				CommitMessage commitMsg = resMap.get(alarm);
				if(commitMsg!=null){
					System.out.println("msgCode : "+commitMsg.getMessageCode());
					Map<String,List<String>> commitMap= commitMsg.getCommitMap();
					if(!commitMap.isEmpty()){
						for(String objCode : commitMap.keySet()){
							System.out.print("objCode  :  "+objCode+"  ");
							List<String> commitCodeList = commitMap.get(objCode);
							if(!commitCodeList.isEmpty()){
								for(String commitCode : commitCodeList){
									System.out.print(commitCode+" // ");
								}
							}
							
						}
					}
				}
				System.out.println("end alarmCode ==>  "+alarm.getAlarmCode());
			}
		}
		System.out.println("------------------------------------------");
		return resMap;
	}
}
