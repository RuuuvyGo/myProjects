package manager;

import java.io.FileNotFoundException;
import java.io.IOException;
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
import java.util.StringTokenizer;

import model.Alarm;
import model.CommitInfo;
import model.CommitMessage;
import model.CopiedProject;
import model.GroupAlarm;
import model.Notice;
import model.OriginFile;
import model.OriginFolder;
import model.OriginProject;
import model.Team;
import socialProExceptions.CommitExcetion;
import socialProExceptions.DAOException;
import socialProExceptions.FileException;
import socialProExceptions.FolderException;
import socialProExceptions.MemberException;
import socialProExceptions.ProjectException;
import socialProExceptions.RemoteFileException;
import socialProExceptions.TeamException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import form.CommitMessageInfoForm;
import form.CommitMsgDetails;
import form.FileForm;

public class CommitMessageManager {

	private static CommitMessageManager INSTANCE;
	private Map<String,Map<String,Map<String,Notice>>> teamProjectCommitMap;
	//(teamCode,cooperCode,origin projectCode) 
	private Map<String,Map<String,Notice>> memberProjectCommitMap;
	//(memberCode,origin projectCode)
	
	static{
		INSTANCE = new CommitMessageManager();
	}
	
	private CommitMessageManager(){
		this.teamProjectCommitMap = new HashMap<String, Map<String,Map<String,Notice>>>();
		this.memberProjectCommitMap = new HashMap<String, Map<String,Notice>>();
	}
	
	public static CommitMessageManager getINSTANCE() {
		return INSTANCE;
	}

/////////////////////////////////////////////////  search
	
	public Map<String,List<CommitMessageInfoForm>> searchMemberProjectCommitMessage(String memberCode, String projectCode) throws DAOException, ParseException, FolderException, IOException, FileException, ProjectException, MemberException{
		
		//compare is member
		System.out.println("\nCommitMessageManager   :      searchMemberProjectCommitMessage   line  60");
		System.out.println("memberCode : "+memberCode+"     projectCode  : "+projectCode);
		Map<String,List<CommitMessageInfoForm>> resMap = new HashMap<String,List<CommitMessageInfoForm>>();

		boolean isOriginProject = FileNodeManager.getINSTANCE().isMemberOriginProject(memberCode, projectCode);
		System.out.println("memberCode : "+memberCode+"     projectCode  : "+projectCode+"  is   Origin??  "+isOriginProject);
		
		if(isOriginProject)this.loadForOriginPersonalProject(memberCode, projectCode);
		else this.loadForCopiedPersonalProject(memberCode, projectCode);
		
		String oriProCode;
		if(isOriginProject){oriProCode = projectCode;}
		else{
			//it is copiedProject
			OriginProject oriProject = FileNodeManager.getINSTANCE().searchOriginProjectByCpProject(memberCode, projectCode);
			oriProCode = oriProject.getCode();
		}
		
		System.out.println("memberCode : "+memberCode);
		System.out.println("oriProCode : "+oriProCode);
		Notice memberTmpNote = this.memberProjectCommitMap.get(memberCode).get(oriProCode);
		
		if(memberTmpNote!=null){
			Map<String,List<CommitMessageInfoForm>> tmpResMap = this.makeNoticeToMap(memberTmpNote);
			if(!tmpResMap.isEmpty())resMap.putAll(tmpResMap);
			
		}
		return resMap;
	}
	
	public List<String> searchMemberCommitMessageCodeList(String memberCode,boolean read) throws DAOException, ParseException, FolderException, IOException, FileException, ProjectException, MemberException, SftpException, JSchException, CommitExcetion, TeamException{
		Map<String,List<CommitMessageInfoForm>> resMap = this.searchMemberCommitMessage(memberCode);
		List<String> resList = new ArrayList<String>();
		if(!resMap.isEmpty()){
			if(this.memberProjectCommitMap.containsKey(memberCode)){
			if(!this.memberProjectCommitMap.get(memberCode).isEmpty()){
				if(read){
					for(Notice note : this.memberProjectCommitMap.get(memberCode).values()){
						if(!note.getReadNotice().isEmpty())resList.addAll(note.getReadNotice());
					}
				}else{
					for(Notice note : this.memberProjectCommitMap.get(memberCode).values()){
						if(!note.getUnreadNotice().isEmpty())resList.addAll(note.getUnreadNotice());
					}
				}
			}	
			}
		}
		return resList;
	}
	
	public List<CommitMessageInfoForm> searchMemberCommitMessage(String memberCode,boolean read) throws DAOException, ParseException, FolderException, IOException, FileException, ProjectException, MemberException, SftpException, JSchException, CommitExcetion, TeamException{
		Map<String,List<CommitMessageInfoForm>> resMap = this.searchMemberCommitMessage(memberCode);
		if(!resMap.isEmpty()){
			if(read)return resMap.get("read");
			else return resMap.get("unread");
		}
		return new ArrayList<CommitMessageInfoForm>();
	}

	public Map<String,List<CommitMessageInfoForm>> searchMemberCommitMessage(String memberCode) throws DAOException, ParseException, FolderException, IOException, FileException, ProjectException, MemberException, SftpException, JSchException, CommitExcetion, TeamException{
		
		Map<String,List<CommitMessageInfoForm>> resMap = new HashMap<String,List<CommitMessageInfoForm>>();
		//compare is member
		System.out.println("\n         CommitMessageManager   :      searchMemberCommitMessage   line  128");
		
		Map<String, Notice> memberTmpMap;
		if(!this.memberProjectCommitMap.containsKey(memberCode)){
			System.out.println("\nstart");
			this.loadMemberProjectReadMsg(memberCode);
			this.loadMemberProjectUnReadMsg(memberCode);
			this.loadTeamProjectReadMsgForMember(memberCode);
			this.loadTeamProjectUnReadMsgForMember(memberCode);
			System.out.println("end\n");
		}
		
		//get personal Project
		if(this.memberProjectCommitMap.containsKey(memberCode)){
			if(!this.memberProjectCommitMap.get(memberCode).isEmpty()){
				Map<String,List<CommitMessageInfoForm>> tmpResMap = this.makeNoticeToMap(this.memberProjectCommitMap.get(memberCode));
				if(!tmpResMap.isEmpty())resMap.putAll(tmpResMap);
			}
		}
		
		//get teamProject
		List<String> teamCodeList1 = TeamManager.getINSTANCE().searchTeamCodeListManager(memberCode);
		if(!teamCodeList1.isEmpty()){
			for(String teamCode1 : teamCodeList1){
				if(this.teamProjectCommitMap.containsKey(teamCode1)){
					if(this.teamProjectCommitMap.get(teamCode1).containsKey(memberCode)){
					if(!this.teamProjectCommitMap.get(teamCode1).get(memberCode).isEmpty()){
						Map<String,List<CommitMessageInfoForm>> tmpResMap = this.makeNoticeToMap(this.teamProjectCommitMap.get(teamCode1).get(memberCode));
						if(!tmpResMap.isEmpty())resMap.putAll(tmpResMap);
					}	
					}
				}
			}
		}
		
		//get teamProject
		List<String> teamCodeList2 = TeamManager.getINSTANCE().searchTeamCodeListCooper(memberCode);
		if(!teamCodeList2.isEmpty()){
			for(String teamCode2 : teamCodeList2){
				if(this.teamProjectCommitMap.containsKey(teamCode2)){
					if(this.teamProjectCommitMap.get(teamCode2).containsKey(memberCode)){
					if(!this.teamProjectCommitMap.get(teamCode2).get(memberCode).isEmpty()){
						Map<String,List<CommitMessageInfoForm>> tmpResMap = this.makeNoticeToMap(this.teamProjectCommitMap.get(teamCode2).get(memberCode));
						if(!tmpResMap.isEmpty())resMap.putAll(tmpResMap);
					}	
					}
				}
			}
		}
	
		return resMap;
	}
	
	public CommitMsgDetails searchMemberProjectCommitMsgDetails(String memberCode, String projectCode,String commitMsgCode) throws DAOException, ParseException, FolderException, IOException, ProjectException, FileException, SftpException, JSchException, RemoteFileException, MemberException, TeamException{

		//compare is member
		System.out.println("\nCommitMessageManager   :      searchMemberProjectCommitMsgDetails   line  189");
		
		boolean isOriginProject = FileNodeManager.getINSTANCE().isMemberOriginProject(memberCode, projectCode);
		System.out.println("memberCode : "+memberCode+"     projectCode  : "+projectCode+"  is   Origin??  "+isOriginProject);
		
		if(isOriginProject)this.loadForOriginPersonalProject(memberCode, projectCode);
		else this.loadForCopiedPersonalProject(memberCode, projectCode);
		
		String oriProCode;
		if(isOriginProject){
			//it is originProject
			oriProCode = projectCode;
		}else{
			//it is copiedProject
			OriginProject oriProject = FileNodeManager.getINSTANCE().searchOriginProjectByCpProject(memberCode, projectCode);
			oriProCode = oriProject.getCode();
		}
		
		Notice memberTmpNote = this.memberProjectCommitMap.get(memberCode).get(oriProCode);
		
		if(memberTmpNote!=null){
			CommitMsgDetails res = null;
			CommitMessage commitMsg = MessageManager.getINSTANCE().getCommitMessage(commitMsgCode);
			if(commitMsg!=null){
				return this.makeCommitMsgDetails(memberTmpNote, commitMsg);
			}
			return res;
		}else{System.out.println("memberTmpNote is NULLL.**");}
		return null;
	}
	
	public Map<String,List<CommitMessageInfoForm>> searchTeamProjectCommitMessage(String memberCode, String teamCode,String projectCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion, MemberException, TeamException{
		
		//compare
		System.out.println("\n         @@ CommitMessageManager      searchTeamProjectCommitMessage   line   218");
		Map<String,List<CommitMessageInfoForm>> resMap = new HashMap<String,List<CommitMessageInfoForm>>();
		
		String oriProCode=projectCode;
		if(teamCode.equals(memberCode)){
			this.loadForOriginTeamProject(teamCode, projectCode);
		}else{
			oriProCode = TeamFileNodeManager.getINSTANCE().searchTeamOriProject(teamCode, memberCode, projectCode).getCode();
			this.loadForCopiedTeamProject(teamCode, memberCode, oriProCode);
		}
		
		Notice memberTmpNote = this.teamProjectCommitMap.get(teamCode).get(memberCode).get(oriProCode);
		
		if(memberTmpNote!=null){
			Map<String,List<CommitMessageInfoForm>> tmpResMap = this.makeNoticeToMap(memberTmpNote);
			if(!tmpResMap.isEmpty())resMap.putAll(tmpResMap);
		}
		return resMap;
	}

	public CommitMsgDetails searchTeamProjectCommitMsgDetails(String teamCode, String memberCode, String projectCode, String commitMsgCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion, MemberException, TeamException, FileException, RemoteFileException{
		//searchMemberProjectCommitMsgDetails(memberCode, projectCode,commitMsgCode)
		//compare
		System.out.println("\n         @@ CommitMessageManager      searchTeamProjectCommitMsgDetails   line   241");
		CommitMsgDetails res = new CommitMsgDetails();
		
		String oriProCode=projectCode;
		if(teamCode.equals(memberCode)){
			this.loadForOriginTeamProject(teamCode, projectCode);
		}else{
			oriProCode = TeamFileNodeManager.getINSTANCE().searchTeamOriProject(teamCode, memberCode, projectCode).getCode();
			System.out.println("originCode : "+oriProCode);
			this.loadForCopiedTeamProject(teamCode, memberCode, oriProCode);
		}
		
		Notice memberTmpNote = this.teamProjectCommitMap.get(teamCode).get(memberCode).get(oriProCode);
		
		if(memberTmpNote!=null){
			CommitMessage commitMsg = MessageManager.getINSTANCE().getCommitMessage(commitMsgCode);
			if(commitMsg!=null){
				return this.makeCommitMsgDetails(memberTmpNote, commitMsg);
			}
			return res;
		}
		return res;
	}
	
	///////////////////////////////////////////   For Load (init memberProjectCommitMap and teamProjectCommitMap)
	
////load
	
	
	public void loadForOriginPersonalProject(String oirOwnerCode,String oriProCode) throws DAOException, ParseException, FolderException, IOException, ProjectException, FileException, MemberException{
		
		System.out.println("\n     @@ CommitMessageManager     :   loadForOriginPersonalProject    line   277  " + oriProCode);
		
		if(!this.memberProjectCommitMap.containsKey(oirOwnerCode)){
			System.out.println("\nstart");
			this.loadMemberProjectReadMsg(oirOwnerCode);
			this.loadMemberProjectUnReadMsg(oirOwnerCode);
			System.out.println("end\n");
		}
		if(!this.memberProjectCommitMap.containsKey(oirOwnerCode)){System.out.println("what?????     no memberCode    "+oirOwnerCode);return ;}
		System.out.println("commitMap key : -->  "+this.memberProjectCommitMap.keySet().iterator().next()+"\n");
		Map<String, Notice> memberTmpMap;
		memberTmpMap = this.memberProjectCommitMap.get(oirOwnerCode);
		
		if(!memberTmpMap.containsKey(oriProCode)){
			System.out.println("\nstart   specific project find!!!!!!!!!!!!");
			/*if(cpProject!=null){
				this.loadMemberProjectReadMsgForCooper(oirOwnerCode, projectCode);
				this.loadMemberProjectUnReadMsgForCooper(oirOwnerCode, projectCode);
			}*/
			this.loadMemberProjectReadMsgForOwner(oirOwnerCode, oriProCode);
			this.loadMemberProjectUnReadMsgForOwner(oirOwnerCode, oriProCode);
			System.out.println("End   specific project find!!!!!!!!!!!!\n");
		}else{
			Notice note = memberTmpMap.get(oriProCode);
			if(note.getReadNotice().isEmpty())this.loadMemberProjectReadMsgForOwner(oirOwnerCode, oriProCode);
			if(note.getUnreadNotice().isEmpty())this.loadMemberProjectUnReadMsgForOwner(oirOwnerCode, oriProCode);
		}
	}

	public void loadForCopiedPersonalProject(String cpOwnerCode,String cpProCode) throws DAOException, ParseException, FolderException, IOException, ProjectException, FileException, MemberException{
		
		System.out.println("\n      @@ CommitMessageManager     :   loadForCopiedPersonalProject    line   299");
		
		if(!this.memberProjectCommitMap.containsKey(cpOwnerCode)){
			System.out.println("\nstart");
			this.loadMemberProjectReadMsg(cpOwnerCode);
			this.loadMemberProjectUnReadMsg(cpOwnerCode);
			System.out.println("end\n");
		}
		if(!this.memberProjectCommitMap.containsKey(cpOwnerCode)){System.out.println("what?????     no memberCode    "+cpOwnerCode);return ;}
		System.out.println("commitMap key : -->  "+this.memberProjectCommitMap.keySet().iterator().next()+"\n");
		Map<String, Notice> memberTmpMap;
		memberTmpMap = this.memberProjectCommitMap.get(cpOwnerCode);
		
		if(!memberTmpMap.containsKey(cpProCode)){
			System.out.println("\nstart   specific project find!!!!!!!!!!!!");
			this.loadMemberProjectReadMsgForCooper(cpOwnerCode, cpProCode);
			this.loadMemberProjectUnReadMsgForCooper(cpOwnerCode, cpProCode);
			System.out.println("End   specific project find!!!!!!!!!!!!\n");
		}else{
			Notice note = memberTmpMap.get(cpProCode);
			if(note.getReadNotice().isEmpty())this.loadMemberProjectReadMsgForCooper(cpOwnerCode, cpProCode);
			if(note.getUnreadNotice().isEmpty())this.loadMemberProjectUnReadMsgForCooper(cpOwnerCode, cpProCode);
		}
	}
	
	public void loadForOriginTeamProject(String teamCode, String oriTeamProjectCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion, MemberException, TeamException{
		System.out.println("\n       @@ CommitMessageManager     loadForOriginTeamProject      line   294");
		//load for team! just teamCode
		if(!this.teamProjectCommitMap.containsKey(teamCode)){
			this.loadTeamProjectListUnReadMsgForTeam(teamCode);
			this.loadTeamProjectListReadMsgForTeam(teamCode);
		}
		if(!this.teamProjectCommitMap.containsKey(teamCode))return ;
		
		if(!this.teamProjectCommitMap.get(teamCode).get(teamCode).containsKey(oriTeamProjectCode)){
			this.loadTeamProjectUnReadMsgForTeam(teamCode, oriTeamProjectCode);
			this.loadTeamProjectReadMsgForTeam(teamCode, oriTeamProjectCode);
		}
		if(!this.teamProjectCommitMap.get(teamCode).get(teamCode).containsKey(oriTeamProjectCode))return ;
	}
	public void loadForCopiedTeamProject(String teamCode, String memberCode, String oriTeamProjectCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion, MemberException, TeamException{
		System.out.println("\n       @@ CommitMessageManager     loadForCopiedTeamProject      line   309");
		if(!this.teamProjectCommitMap.containsKey(teamCode)){
			this.loadTeamProjectUnReadMsg(teamCode);
			this.loadTeamProjectReadMsg(teamCode);
		}
		if(!this.teamProjectCommitMap.containsKey(teamCode))return ;
		
		if(!this.teamProjectCommitMap.get(teamCode).get(memberCode).containsKey(oriTeamProjectCode)){
			this.loadTeamProjectUnReadMsg(teamCode, oriTeamProjectCode);
			this.loadTeamProjectReadMsg(teamCode, oriTeamProjectCode);
		}
		if(!this.teamProjectCommitMap.get(teamCode).get(memberCode).containsKey(oriTeamProjectCode))return ;
	}
	
	public void loadMemberProjectReadMsg(String memberCode) throws DAOException, ParseException, FolderException, IOException, ProjectException, FileException, MemberException{
		
		System.out.println("\n            CommitMessageManager  :  loadMemberProjectReadMsg   line 352");
		boolean read = true;
		//* if Member is Manager of personalProject (sharedProject)
		//1. get project info 
		System.out.println("if Member is Manager of personalProject (sharedProject)");
		List<String> oriProjectList = FileNodeManager.getINSTANCE().searchOriginProjectCodeList(memberCode);
		if(!oriProjectList.isEmpty()){
			System.out.println("Yes!!!  "+memberCode+"  have originProjectList");
			//2.get CommitMsg from db
			Map<String,Map<Alarm,CommitMessage>> resValMap = MessageManager.getINSTANCE().loadReadSharedProjectCommitMsgForOwner(memberCode, oriProjectList);
			if(!resValMap.isEmpty()){
				for(String oriProjectCode : resValMap.keySet()){
					Map<Alarm,CommitMessage> proMsgMap = resValMap.get(oriProjectCode);
					if(!proMsgMap.isEmpty()){
						//3. insert in manager
						this.manageMessageAlarm(oriProjectCode, proMsgMap, read);
					}
				}
			}
		}else System.out.println("NO!!!  "+memberCode+"  No origin_ProjectList");
		
		
		//* if Member is cooperator of shared Project
		//1. get copiedproject info
		System.out.println("if Member is cooperator of shared Project\n");
		List<String> cpProjectList = FileNodeManager.getINSTANCE().searchCopiedProjectCodeList(memberCode);
		if(!cpProjectList.isEmpty()){
			System.out.println("Yes!!!  "+memberCode+"  have Copied ProjectList");
			//2.get CommitMsg from db
			Map<String,Map<Alarm,CommitMessage>> resValMap = MessageManager.getINSTANCE().loadReadSharedProjectListCommitMsgForCooperator(memberCode, cpProjectList);
			if(!resValMap.isEmpty()){
				for(String oriProjectCode : resValMap.keySet()){
					Map<Alarm,CommitMessage> proMsgMap = resValMap.get(oriProjectCode);
					if(!proMsgMap.isEmpty()){
						//3. insert in manager
						this.manageMessageAlarm(oriProjectCode, proMsgMap, read);
					}
				}
			}
		}else System.out.println("No!!!  "+memberCode+"  No Copied_ProjectList");
		
	} 

	public void loadMemberProjectUnReadMsg(String memberCode) throws DAOException, ParseException, FolderException, IOException, ProjectException, FileException, MemberException{
		
		System.out.println("\n               CommitMessageManager   loadMemberProjectUnReadMsg      line       397");
		boolean read = false;
		//* if Member is Manager of personalProject (sharedProject)
		//1. get project info 
		System.out.println("\n   if Member is Manager of personalProject (sharedProject)");
		List<String> oriProjectList = FileNodeManager.getINSTANCE().searchOriginProjectCodeList(memberCode);
		if(!oriProjectList.isEmpty()){
			
			//2.get CommitMsg from db
			Map<String,Map<Alarm,CommitMessage>> resValMap = MessageManager.getINSTANCE().loadUnReadSharedProjectCommitMsgForOwner(memberCode, oriProjectList);
			if(!resValMap.isEmpty()){
				for(String oriProjectCode : resValMap.keySet()){
					System.out.println("oriProCode  : "+oriProjectCode);
					Map<Alarm,CommitMessage> proMsgMap = resValMap.get(oriProjectCode);
					if(!proMsgMap.isEmpty()){
						//3. insert in manager
						this.manageMessageAlarm(oriProjectCode, proMsgMap, read);
					}
				}
			}
		}else System.out.println("member : "+memberCode+"  has no originProject......");
		
		System.out.println("");
		//* if Member is cooperator of shared Project
		//1. get copiedproject info
		System.out.println("\n    if Member is cooperator of shared Project");
		List<String> cpProjectList = FileNodeManager.getINSTANCE().searchCopiedProjectCodeList(memberCode);
		if(!cpProjectList.isEmpty()){
			
			//2.get CommitMsg from db
			Map<String,Map<Alarm,CommitMessage>> resValMap = MessageManager.getINSTANCE().loadUnReadSharedProjectListCommitMsgForCooperator(memberCode, cpProjectList);
			if(!resValMap.isEmpty()){
				System.out.println("resValMap is not empty...^^");
				for(String oriProjectCode : resValMap.keySet()){
					System.out.println("oriProCode : "+oriProjectCode);
					Map<Alarm,CommitMessage> proMsgMap = resValMap.get(oriProjectCode);
					if(!proMsgMap.isEmpty()){
						System.out.println("proMsgMap is not empty...^^");
						//3. insert in manager
						this.manageMessageAlarm(oriProjectCode, proMsgMap, read);
					}else System.out.println("proMsgMap is     empty...T+T");
				}
			}else System.out.println("resValMap is  empty...T_T");
		}
		
	} 

	public void loadMemberProjectReadMsgForOwner(String memberCode,String originProjectCode) throws DAOException, ParseException, FolderException, IOException, ProjectException{
		
		boolean read = true;
		//* if Member is Manager of personalProject (sharedProject)
		//1.get CommitMsg from db
		Map<Alarm,CommitMessage> resValMap = MessageManager.getINSTANCE().loadReadSharedProjectCommitMsgForOwner(memberCode, originProjectCode);
		if(!resValMap.isEmpty()){
			//3. insert in manager
			this.manageMessageAlarm(originProjectCode, resValMap, read);
		}
	}

	public void loadMemberProjectUnReadMsgForOwner(String memberCode,String originProjectCode) throws DAOException, ParseException, FolderException, IOException, ProjectException{
		
		boolean read = false;
		//* if Member is Manager of personalProject (sharedProject)
		//1.get CommitMsg from db
		Map<Alarm,CommitMessage> resValMap = MessageManager.getINSTANCE().loadUnReadSharedProjectCommitMsgForOwner(memberCode, originProjectCode);
		if(!resValMap.isEmpty()){
			//3. insert in manager
			this.manageMessageAlarm(originProjectCode, resValMap, read);
		}
	}

	public void loadMemberProjectReadMsgForCooper(String cooperatorCode,String cpProjectCode) throws DAOException, ParseException, FolderException, IOException, ProjectException{
		
		boolean read = true;
		//* if Member is Manager of personalProject (sharedProject)
		//1.get CommitMsg from db
		Map<Alarm, CommitMessage> resValMap = MessageManager.getINSTANCE().loadReadSharedProjectCommitMsgForCooperator(cooperatorCode, cpProjectCode);
		if(!resValMap.isEmpty()){
			//3. insert in manager
			String oriProjectCode = FileNodeManager.getINSTANCE().searchCopiedProject(cooperatorCode, cpProjectCode).getOriginCode();
			this.manageMessageAlarm(oriProjectCode, resValMap, read);
		}
	}

	public void loadMemberProjectUnReadMsgForCooper(String cooperatorCode,String cpProjectCode) throws DAOException, ParseException, FolderException, IOException, ProjectException{

		boolean read = false;
		//* if Member is Manager of personalProject (sharedProject)
		//1.get CommitMsg from db
		Map<Alarm, CommitMessage> resValMap = MessageManager.getINSTANCE().loadUnReadSharedProjectCommitMsgForCooperator(cooperatorCode, cpProjectCode);
		if(!resValMap.isEmpty()){
			//3. insert in manager
			String oriProjectCode = FileNodeManager.getINSTANCE().searchCopiedProject(cooperatorCode, cpProjectCode).getOriginCode();
			this.manageMessageAlarm(oriProjectCode, resValMap, read);
		}
	}
	
	public void loadTeamProjectReadMsgForMember(String memberCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion, MemberException, TeamException{
		
		System.out.println("\n      CommitMessageManger   :   loadTeamProjectReadMsgForMember   line  457");
		boolean read = true;
		//*if member is manager of team
		//1. get teamList(member is manager)
		System.out.println("if member is manager of team");
		List<String> teamList = TeamManager.getINSTANCE().searchTeamCodeListManager(memberCode);
		if(!teamList.isEmpty()){
			
			//2. get CommitMessage
			Map<String, Map<String, Map<Alarm, CommitMessage>>> resMap = MessageManager.getINSTANCE().loadReadTeamListProjectCommitMsgForOwner(memberCode, teamList);
			if(!resMap.isEmpty()){
				for(String teamCode : resMap.keySet()){
					Map<String,Map<Alarm, CommitMessage>> tmpMap = resMap.get(teamCode);
					if(!tmpMap.isEmpty()){
						for(String oriProjectCode : tmpMap.keySet()){
							Map<Alarm,CommitMessage> proMsgMap = tmpMap.get(oriProjectCode);
							if(!proMsgMap.isEmpty()){
								//3. insert in manager
								this.manageMessageTeamAlarm(teamCode, oriProjectCode, proMsgMap, read);
							}
						}
					}
				}
			}
		}
		
		//* if member is cooperator of team
		//1. get teamList(member is manager)
		System.out.println("if member is cooperator of team");
		List<String> teamCodeList = TeamManager.getINSTANCE().searchTeamCodeListCooper(memberCode);
		if(!teamCodeList.isEmpty()){
			
			//2. get CommitMessage
			Map<String, Map<String, Map<Alarm, CommitMessage>>> resMap = MessageManager.getINSTANCE().loadReadTeamListProjectCommitMsgForCooper(memberCode, teamCodeList);
			if(!resMap.isEmpty()){
				for(String teamCode : resMap.keySet()){
					Map<String,Map<Alarm, CommitMessage>> tmpMap = resMap.get(teamCode);
					if(!tmpMap.isEmpty()){
						for(String oriProjectCode : tmpMap.keySet()){
							Map<Alarm,CommitMessage> proMsgMap = tmpMap.get(oriProjectCode);
							if(!proMsgMap.isEmpty()){
								//3. insert in manager
								this.manageMessageTeamAlarm(teamCode, oriProjectCode, proMsgMap, read);
							}
						}
					}
				}
			}
		}
		
		//* if member or cooerator of team (receive from 'team')
		System.out.println("if member or cooerator of team (receive from 'team')");
		if(!teamList.isEmpty()){
			teamCodeList.addAll(teamList);
			
			//2. get CommitMessage
			Map<String, Map<String, Map<GroupAlarm, CommitMessage>>> resMap = MessageManager.getINSTANCE().loadReadTeamProjectCommitMessageForTeam(memberCode, teamCodeList);
			if(!resMap.isEmpty()){
				for(String teamCode : resMap.keySet()){
					Map<String,Map<GroupAlarm,CommitMessage>> tmpMap = resMap.get(teamCode);
					if(!tmpMap.isEmpty()){
						for(String oriProjectCode : tmpMap.keySet()){
							Map<GroupAlarm,CommitMessage> proMsgMap = tmpMap.get(oriProjectCode);
							if(!proMsgMap.isEmpty()){this.manageMessageGroupAlarm(teamCode, oriProjectCode, proMsgMap, read);}
						}
					}
				}
			}
		}
	}

	public void loadTeamProjectUnReadMsgForMember(String memberCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion, MemberException, TeamException{
		
		System.out.println("\n      CommitMessageManger   :   loadTeamProjectUnReadMsgForMember   line  530");
		boolean read = false;
		//*if member is manager of team
		//1. get teamList(member is manager)
		System.out.println("if member is manager of team");
		List<String> teamList = TeamManager.getINSTANCE().searchTeamCodeListManager(memberCode);
		if(!teamList.isEmpty()){
			
			//2. get CommitMessage
			Map<String, Map<String, Map<Alarm, CommitMessage>>> resMap = MessageManager.getINSTANCE().loadUnReadTeamListProjectCommitMsgForOwner(memberCode, teamList);
			if(!resMap.isEmpty()){
				for(String teamCode : resMap.keySet()){
					System.out.println("teamCode------------------  "+teamCode);
					Map<String,Map<Alarm, CommitMessage>> tmpMap = resMap.get(teamCode);
					if(!tmpMap.isEmpty()){
						for(String oriProjectCode : tmpMap.keySet()){
							System.out.println("oriProjectCode------------------  "+oriProjectCode);
							Map<Alarm,CommitMessage> proMsgMap = tmpMap.get(oriProjectCode);
							if(!proMsgMap.isEmpty()){
								//3. insert in manager
								System.out.println("make!!!!!!!-----------------start");
								this.manageMessageTeamAlarm(teamCode, oriProjectCode, proMsgMap, read);
								System.out.println("make!!!!!!!-----------------ENND");
							}
						}
					}
				}
			}
		}
		
		//* if member is cooperator of team
		//1. get teamList(member is manager)
		System.out.println("if member is cooperator of team");
		List<String> teamCodeList = TeamManager.getINSTANCE().searchTeamCodeListCooper(memberCode);
		if(!teamCodeList.isEmpty()){
			
			//2. get CommitMessage
			Map<String, Map<String, Map<Alarm, CommitMessage>>> resMap = MessageManager.getINSTANCE().loadUnReadTeamListProjectCommitMsgForCooper(memberCode, teamCodeList);
			if(!resMap.isEmpty()){
				for(String teamCode : resMap.keySet()){
					Map<String,Map<Alarm, CommitMessage>> tmpMap = resMap.get(teamCode);
					if(!tmpMap.isEmpty()){
						for(String oriProjectCode : tmpMap.keySet()){
							Map<Alarm,CommitMessage> proMsgMap = tmpMap.get(oriProjectCode);
							if(!proMsgMap.isEmpty()){
								//3. insert in manager
								this.manageMessageTeamAlarm(teamCode, oriProjectCode, proMsgMap, read);
							}
						}
					}
				}
			}
		}
		
		//* if member or cooerator of team (receive from 'team')
		System.out.println("if member or cooerator of team (receive from 'team')");
		if(!teamList.isEmpty()){
			teamCodeList.addAll(teamList);
			
			//2. get CommitMessage
			Map<String, Map<String, Map<GroupAlarm, CommitMessage>>> resMap = MessageManager.getINSTANCE().loadUnReadTeamProjectCommitMessageForTeam(memberCode, teamCodeList);
			if(!resMap.isEmpty()){
				for(String teamCode : resMap.keySet()){
					Map<String,Map<GroupAlarm,CommitMessage>> tmpMap = resMap.get(teamCode);
					if(!tmpMap.isEmpty()){
						for(String oriProjectCode : tmpMap.keySet()){
							Map<GroupAlarm,CommitMessage> proMsgMap = tmpMap.get(oriProjectCode);
							if(!proMsgMap.isEmpty()){this.manageMessageGroupAlarm(teamCode, oriProjectCode, proMsgMap, read);}
						}
					}
				}
			}
		}
	}

	public void loadTeamProjectListReadMsgForTeam(String teamCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion, MemberException, TeamException{
		
		boolean read = true;
		//team send commitMsg to cooperator
		List<String> teamSharedProjectList = TeamFileNodeManager.getINSTANCE().searchTeamProjectList(teamCode);
		if(!teamSharedProjectList.isEmpty()){
			
			Map<String,List<String>> teamSharedProjectMap = new HashMap<String, List<String>>();
			teamSharedProjectMap.put(teamCode, teamSharedProjectList);
			Map<String, Map<String, Map<GroupAlarm, CommitMessage>>> resMap = MessageManager.getINSTANCE().loadReadTeamProjectListCommitMessageForTeam(teamSharedProjectMap);
			if(!resMap.isEmpty()){
				for(String teamCode2 : resMap.keySet()){
					Map<String,Map<GroupAlarm,CommitMessage>> tmpMap = resMap.get(teamCode2);
					if(!tmpMap.isEmpty()){
						for(String oriProjectCode : tmpMap.keySet()){
							Map<GroupAlarm,CommitMessage> proMsgMap = tmpMap.get(oriProjectCode);
							if(!proMsgMap.isEmpty()){this.manageMessageGroupAlarm(teamCode2, oriProjectCode, proMsgMap, read);}
						}
					}
				}
			}
		}
	}
	public void loadTeamProjectListUnReadMsgForTeam(String teamCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion, MemberException, TeamException{
		
		boolean read = false;
		//team send commitMsg to cooperator
		List<String> teamSharedProjectList = TeamFileNodeManager.getINSTANCE().searchTeamProjectList(teamCode);
		if(!teamSharedProjectList.isEmpty()){
			
			Map<String,List<String>> teamSharedProjectMap = new HashMap<String, List<String>>();
			teamSharedProjectMap.put(teamCode, teamSharedProjectList);
			Map<String, Map<String, Map<GroupAlarm, CommitMessage>>> resMap = MessageManager.getINSTANCE().loadUnReadTeamProjectListCommitMessageForTeam(teamSharedProjectMap);
			if(!resMap.isEmpty()){
				for(String teamCode2 : resMap.keySet()){
					Map<String,Map<GroupAlarm,CommitMessage>> tmpMap = resMap.get(teamCode2);
					if(!tmpMap.isEmpty()){
						for(String oriProjectCode : tmpMap.keySet()){
							Map<GroupAlarm,CommitMessage> proMsgMap = tmpMap.get(oriProjectCode);
							if(!proMsgMap.isEmpty()){this.manageMessageGroupAlarm(teamCode2, oriProjectCode, proMsgMap, read);}
						}
					}
				}
			}
		}
	}

	public void loadTeamProjectReadMsgForTeam(String teamCode, String teamOriProCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion, MemberException, TeamException{
		
		boolean read = true;
		//team send commitMsg to cooperator
		Map<String, Map<String, Map<GroupAlarm, CommitMessage>>> resMap = MessageManager.getINSTANCE().loadReadTeamProjectCommitMessageForTeam(teamCode, teamOriProCode);
		if(!resMap.isEmpty()){
			for(String teamCode2 : resMap.keySet()){
				Map<String,Map<GroupAlarm,CommitMessage>> tmpMap = resMap.get(teamCode2);
				if(!tmpMap.isEmpty()){
					for(String oriProjectCode : tmpMap.keySet()){
						Map<GroupAlarm,CommitMessage> proMsgMap = tmpMap.get(oriProjectCode);
						if(!proMsgMap.isEmpty()){this.manageMessageGroupAlarm(teamCode2, oriProjectCode, proMsgMap, read);}
					}
				}
			}
		}
	}
	public void loadTeamProjectUnReadMsgForTeam(String teamCode, String teamOriProCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion, MemberException, TeamException{

		boolean read = false;
		//team send commitMsg to cooperator
		Map<String, Map<String, Map<GroupAlarm, CommitMessage>>> resMap = MessageManager.getINSTANCE().loadUnReadTeamProjectCommitMessageForTeam(teamCode, teamOriProCode);
		if(!resMap.isEmpty()){
			for(String teamCode2 : resMap.keySet()){
				Map<String,Map<GroupAlarm,CommitMessage>> tmpMap = resMap.get(teamCode2);
				if(!tmpMap.isEmpty()){
					for(String oriProjectCode : tmpMap.keySet()){
						Map<GroupAlarm,CommitMessage> proMsgMap = tmpMap.get(oriProjectCode);
						if(!proMsgMap.isEmpty()){this.manageMessageGroupAlarm(teamCode2, oriProjectCode, proMsgMap, read);}
					}
				}
			}
		}
	}
	

	public void loadTeamProjectReadMsg(String teamCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion, MemberException, TeamException{
		
		boolean read = true;
		//get teaminfo
		Team teamInfo = TeamManager.getINSTANCE().searchTeamCode(teamCode);
		String managerCode = teamInfo.getManager();
		
		this.loadTeamProjectListReadMsgForTeam(teamCode);
		
		//for team manager
		//2. get CommitMessage
		Map<String, Map<String, Map<Alarm, CommitMessage>>> resMap = MessageManager.getINSTANCE().loadReadTeamProjectListCommitMsgForOwner(managerCode, teamCode);
		if(!resMap.isEmpty()){
			for(String teamCode2 : resMap.keySet()){
				Map<String,Map<Alarm, CommitMessage>> tmpMap = resMap.get(teamCode2);
				if(!tmpMap.isEmpty()){
					for(String oriProjectCode : tmpMap.keySet()){
						Map<Alarm,CommitMessage> proMsgMap = tmpMap.get(oriProjectCode);
						if(!proMsgMap.isEmpty()){
							//3. insert in manager
							this.manageMessageTeamAlarm(teamCode2, oriProjectCode, proMsgMap, read);
						}
					}
				}
			}
		}
		
		//for team cooperator
		//2. get CommitMessage
		if(!teamInfo.getCooperatorList().isEmpty()){
			List<String> cooperList = teamInfo.getCooperatorList();
			for(String cooperCode : cooperList){
				Map<String, Map<String, Map<Alarm, CommitMessage>>> resMap2 = MessageManager.getINSTANCE().loadReadTeamProjectListCommitMsgForCooper(cooperCode, teamCode);
				if(!resMap2.isEmpty()){
					for(String teamCode3 : resMap2.keySet()){
						Map<String,Map<Alarm, CommitMessage>> tmpMap = resMap2.get(teamCode3);
						if(!tmpMap.isEmpty()){
							for(String oriProjectCode : tmpMap.keySet()){
								Map<Alarm,CommitMessage> proMsgMap = tmpMap.get(oriProjectCode);
								if(!proMsgMap.isEmpty()){
									//3. insert in manager
									this.manageMessageTeamAlarm(teamCode3, oriProjectCode, proMsgMap, read);
								}
							}
						}
					}
				}
			}
		}
		
	}

	public void loadTeamProjectUnReadMsg(String teamCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion, MemberException, TeamException{
		
		boolean read = false;
		//get teaminfo
		Team teamInfo = TeamManager.getINSTANCE().searchTeamCode(teamCode);
		String managerCode = teamInfo.getManager();
		
		this.loadTeamProjectListUnReadMsgForTeam(teamCode);
		
		//for team manager
		//2. get CommitMessage
		Map<String, Map<String, Map<Alarm, CommitMessage>>> resMap = MessageManager.getINSTANCE().loadUnReadTeamProjectListCommitMsgForOwner(managerCode, teamCode);
		if(!resMap.isEmpty()){
			for(String teamCode2 : resMap.keySet()){
				Map<String,Map<Alarm, CommitMessage>> tmpMap = resMap.get(teamCode2);
				if(!tmpMap.isEmpty()){
					for(String oriProjectCode : tmpMap.keySet()){
						Map<Alarm,CommitMessage> proMsgMap = tmpMap.get(oriProjectCode);
						if(!proMsgMap.isEmpty()){
							//3. insert in manager
							this.manageMessageTeamAlarm(teamCode2, oriProjectCode, proMsgMap, read);
						}
					}
				}
			}
		}
		
		//for team cooperator
		//2. get CommitMessage
		if(!teamInfo.getCooperatorList().isEmpty()){
			List<String> cooperList = teamInfo.getCooperatorList();
			for(String cooperCode : cooperList){
				Map<String, Map<String, Map<Alarm, CommitMessage>>> resMap2 = MessageManager.getINSTANCE().loadUnReadTeamProjectListCommitMsgForCooper(cooperCode, teamCode);
				if(!resMap2.isEmpty()){
					for(String teamCode3 : resMap2.keySet()){
						Map<String,Map<Alarm, CommitMessage>> tmpMap = resMap2.get(teamCode3);
						if(!tmpMap.isEmpty()){
							for(String oriProjectCode : tmpMap.keySet()){
								Map<Alarm,CommitMessage> proMsgMap = tmpMap.get(oriProjectCode);
								if(!proMsgMap.isEmpty()){
									//3. insert in manager
									this.manageMessageTeamAlarm(teamCode3, oriProjectCode, proMsgMap, read);
								}
							}
						}
					}
				}
			}
		}
		
	}


	public void loadTeamProjectReadMsg(String teamCode,String originProjectCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion, MemberException, TeamException{
		
		boolean read = true;
		//get teaminfo
		Team teamInfo = TeamManager.getINSTANCE().searchTeamCode(teamCode);
		String managerCode = teamInfo.getManager();
		
		this.loadTeamProjectReadMsgForTeam(teamCode, originProjectCode);
		
		//for team manager
		//2. get CommitMessage
		Map<String, Map<String, Map<Alarm, CommitMessage>>> resMap = MessageManager.getINSTANCE().loadReadTeamProjectCommitMsgForOwner(managerCode, teamCode, originProjectCode);
		if(!resMap.isEmpty()){
			for(String teamCode2 : resMap.keySet()){
				Map<String,Map<Alarm, CommitMessage>> tmpMap = resMap.get(teamCode2);
				if(!tmpMap.isEmpty()){
					for(String oriProjectCode : tmpMap.keySet()){
						Map<Alarm,CommitMessage> proMsgMap = tmpMap.get(oriProjectCode);
						if(!proMsgMap.isEmpty()){
							//3. insert in manager
							this.manageMessageTeamAlarm(teamCode2, oriProjectCode, proMsgMap, read);
						}
					}
				}
			}
		}
		
		//for team cooperator
		//2. get CommitMessage
		if(!teamInfo.getCooperatorList().isEmpty()){
			List<String> cooperList = teamInfo.getCooperatorList();
			for(String cooperCode : cooperList){
				Map<String, Map<String, Map<Alarm, CommitMessage>>> resMap2 = MessageManager.getINSTANCE().loadReadTeamProjectCommitMsgForCooper(originProjectCode, cooperCode, teamCode);
				if(!resMap2.isEmpty()){
					for(String teamCode3 : resMap2.keySet()){
						Map<String,Map<Alarm, CommitMessage>> tmpMap = resMap2.get(teamCode3);
						if(!tmpMap.isEmpty()){
							for(String oriProjectCode : tmpMap.keySet()){
								Map<Alarm,CommitMessage> proMsgMap = tmpMap.get(oriProjectCode);
								if(!proMsgMap.isEmpty()){
									//3. insert in manager
									this.manageMessageTeamAlarm(teamCode3, oriProjectCode, proMsgMap, read);
								}
							}
						}
					}
				}
			}
		}
		
	}

	public void loadTeamProjectUnReadMsg(String teamCode,String originProjectCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion, MemberException, TeamException{
		
		boolean read = false;
		//get teaminfo
		Team teamInfo = TeamManager.getINSTANCE().searchTeamCode(teamCode);
		String managerCode = teamInfo.getManager();
		
		this.loadTeamProjectUnReadMsgForTeam(teamCode, originProjectCode);
		
		//for team manager
		//2. get CommitMessage
		Map<String, Map<String, Map<Alarm, CommitMessage>>> resMap = MessageManager.getINSTANCE().loadUnReadTeamProjectCommitMsgForOwner(managerCode, teamCode, originProjectCode);
		if(!resMap.isEmpty()){
			for(String teamCode2 : resMap.keySet()){
				Map<String,Map<Alarm, CommitMessage>> tmpMap = resMap.get(teamCode2);
				if(!tmpMap.isEmpty()){
					for(String oriProjectCode : tmpMap.keySet()){
						Map<Alarm,CommitMessage> proMsgMap = tmpMap.get(oriProjectCode);
						if(!proMsgMap.isEmpty()){
							//3. insert in manager
							this.manageMessageTeamAlarm(teamCode2, oriProjectCode, proMsgMap, read);
						}
					}
				}
			}
		}
		
		//for team cooperator
		//2. get CommitMessage
		if(!teamInfo.getCooperatorList().isEmpty()){
			List<String> cooperList = teamInfo.getCooperatorList();
			for(String cooperCode : cooperList){
				Map<String, Map<String, Map<Alarm, CommitMessage>>> resMap2 = MessageManager.getINSTANCE().loadUnReadTeamProjectCommitMsgForCooper(originProjectCode, cooperCode, teamCode);
				if(!resMap2.isEmpty()){
					for(String teamCode3 : resMap2.keySet()){
						Map<String,Map<Alarm, CommitMessage>> tmpMap = resMap2.get(teamCode3);
						if(!tmpMap.isEmpty()){
							for(String oriProjectCode : tmpMap.keySet()){
								Map<Alarm,CommitMessage> proMsgMap = tmpMap.get(oriProjectCode);
								if(!proMsgMap.isEmpty()){
									//3. insert in manager
									this.manageMessageTeamAlarm(teamCode3, oriProjectCode, proMsgMap, read);
								}
							}
						}
					}
				}
			}
		}
		
	}
///////////////////////////////////////////////     insert
	
	
	public boolean insertSharedProjectCommitMessage(String memberCode, String copiedProjectCode,String title, String content) throws DAOException, ParseException, FolderException, ProjectException, IOException, TeamException, FileException, SftpException, JSchException, RemoteFileException, MemberException, CommitExcetion{
		
		//send commit message to personal project owner
		Map<String, List<String>> commitCodeMap = CommitInfoManager.getINSTANCE().sendMergeCommitList(memberCode, copiedProjectCode);
		
		if(!commitCodeMap.isEmpty()){
			
			CopiedProject cpProject = ProjectManager.getINSTANCE().searchOnlyCpProjectByProjectPath(FileNodeManager.getINSTANCE().searchProject(memberCode, copiedProjectCode).getPath());
			
			
			CommitMessage commitMsg = new CommitMessage(title, content, memberCode, cpProject.getOriginOwnerCode(), new GregorianCalendar(), commitCodeMap);
			//SharedProjectCommitMessageSendToOwner(commitMsg);
			Map<Alarm,CommitMessage> resCommitMsgAlarm = MessageManager.getINSTANCE().putShareProjectCommitMessageSendToOwner(commitMsg, cpProject.getOriginCode());
			if(!resCommitMsgAlarm.isEmpty()){
				CommitInfoManager.getINSTANCE().sendMergeCommitList(memberCode, copiedProjectCode);
				this.managerMessageAlarm(commitMsg, cpProject.getOriginCode(),false);
				return true;
			}
		}
		return false;
	}

	public boolean insertSharedProjectCommitMessage(String memberCode, String copiedProjectCode,String title, String content,List<String> commitCodeList) throws DAOException, ParseException, FolderException, ProjectException, IOException, TeamException, FileException, SftpException, JSchException, RemoteFileException, MemberException, CommitExcetion{
		
		//check is copied Project
		//send commit message to personal project owner
		Map<String, List<String>> commitCodeMap = CommitInfoManager.getINSTANCE().searchCommitCodeListForCodeMap(commitCodeList);
		
		if(!commitCodeMap.isEmpty()){
			
			//test data
			for(String objCode : commitCodeMap.keySet()){
				System.out.println("objcode : "+objCode);
				List<String> commitList = commitCodeMap.get(objCode);
				if(!commitList.isEmpty()){
				for(String commitCode : commitList){
					System.out.println("commitCode : "+commitCode);
				}
				}
				System.out.println(";;;;;;;");
			}
			
			CopiedProject cpProject = ProjectManager.getINSTANCE().searchOnlyCpProjectByProjectPath(FileNodeManager.getINSTANCE().searchProject(memberCode, copiedProjectCode).getPath());
			String receiver = cpProject.getOriginOwnerCode();
			
			CommitMessage commitMsg = new CommitMessage(title, content, memberCode, receiver, new GregorianCalendar(), commitCodeMap);
			//SharedProjectCommitMessageSendToOwner(commitMsg);
			Map<Alarm,CommitMessage> resCommitMsgAlarm = MessageManager.getINSTANCE().putShareProjectCommitMessageSendToOwner(commitMsg, cpProject.getOriginCode());
			if(!resCommitMsgAlarm.isEmpty()){
				//CommitInfoManager.getINSTANCE().sendMergeCommitList(memberCode, copiedProjectCode);

				if(!this.memberProjectCommitMap.containsKey(receiver)){
					this.searchMemberProjectCommitMessage(receiver, cpProject.getOriginCode());
				}
				if(!this.memberProjectCommitMap.containsKey(memberCode)){
					this.searchMemberProjectCommitMessage(memberCode, cpProject.getOriginCode());
				}
				
				this.manageMessageAlarm(cpProject.getOriginCode(), resCommitMsgAlarm, false);
				//this.managerMessageAlarm(commitMsg, cpProject.getOriginCode(),false);
				//MemberAlarmManager.getINSTANCE().insertNewAlarm(receiver,"commitMessage", );
				return true;
			}
		}
		return false;
	}
	
	/*public boolean insertSharedProCommitMsgFromOwner(String memberCode, String originProCode,String title, String content,List<String> commitCodeList) throws DAOException, ParseException, FolderException, ProjectException, IOException, TeamException, FileException, SftpException, JSchException, RemoteFileException, MemberException, CommitExcetion{
		
		System.out.println("insertSharedProCommitMsgFromOwner");
		//send commit message to personal project owner
		CommitMessage commitMsg = new CommitMessage(title, content, memberCode, originProCode, new GregorianCalendar(), commitCodeList);

		if(!commitCodeList.isEmpty()){
			Map<CommitMessage, List<GroupAlarm>> resCommitMsgAlarm = MessageDBManager.getINSTANCE().insertSharedProCommitMsgFromOwner(commitMsg);
			if(!resCommitMsgAlarm.isEmpty()){
				commitMsg = resCommitMsgAlarm.keySet().iterator().next();
				if(commitMsg.getMessageCode()!=null){
					List<GroupAlarm> gAlarmList = resCommitMsgAlarm.get(commitMsg);
					if(!gAlarmList.isEmpty()){
						CommitInfoManager.getINSTANCE().sendMergeCommitList(memberCode, originProCode, commitCodeList);
						this.manageMessageAlarm(oriOwnerCode, oriProCode, commitMsgCode, gAlarmList, read);
						this.manageMessageAlarm(memberCode, originProCode, commitMsg.getMessageCode(), gAlarmList, false);
						return true;
					}else System.out.println("gAlarmList is null");
				}else System.out.println("commitMsg is null");
			}else System.out.println("no resCommitMsgAlarm from DB");
		}
		
		return false;
	}*/

	public boolean insertTeamProCommitMsg(String teamCode, String branchCode,String memberCode, String projectCode, String title, String content,List<String> commitCodeList) throws DAOException, ParseException, FolderException, ProjectException, IOException, TeamException, FileException, SftpException, JSchException, RemoteFileException, MemberException, CommitExcetion{
		
		Map<String,List<String>> commitCodeMap = new HashMap<String, List<String>>();
		if(!commitCodeList.isEmpty()){
			for(String commitCode : commitCodeList){
				commitCodeMap = CommitInfoManager.getINSTANCE().searchCommitCodeListForCodeMap(commitCodeList);
			}
		}
		if(teamCode.equals(branchCode)){return this.insertTeamProCommitMsgFromTeam(teamCode, projectCode, title, content, commitCodeMap);}
		else{return this.insertTeamProCommitMsgFromCooper(teamCode, memberCode, projectCode, title, content, commitCodeMap);}
	}
	
	public boolean insertTeamProCommitMsgFromTeam(String teamCode,String originProCode,String title, String content,Map<String,List<String>> commitCodeMap) throws DAOException, ParseException, FolderException, ProjectException, IOException, TeamException, FileException, SftpException, JSchException, RemoteFileException, MemberException, CommitExcetion{
		
		//send commit message to personal project owner
		String manager = TeamManager.getINSTANCE().searchTeamCode(teamCode).getManager();
		CommitMessage commitMsg = new CommitMessage(title, content, teamCode, originProCode, new GregorianCalendar(), commitCodeMap);
		
		if(!commitCodeMap.isEmpty()){
			Map<GroupAlarm, CommitMessage> resMap = MessageManager.getINSTANCE().putTeamCommitMessageSendFromOwner(commitMsg, originProCode);
			
			if(!resMap.isEmpty()){
				if(!resMap.values().isEmpty()){
					CommitInfoManager.getINSTANCE().sendMergeTeamCommitList(teamCode, teamCode, originProCode, commitCodeMap);
					this.manageMessageGroupAlarm(teamCode, originProCode, resMap, false);
					return true;
				}
			}
		}
		
		return false;
	}

	
	public boolean insertTeamProCommitMsgFromCooper(String teamCode, String memberCode, String cpProjectCode,String title, String content,Map<String,List<String>> commitCodeMap) throws DAOException, TeamException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion, FileException, RemoteFileException, MemberException{
		
		System.out.println("\n          CommitMessageManager : insertTeamProCommitMsgFromCooper line 981");
		String manager = TeamManager.getINSTANCE().searchTeamCode(teamCode).getManager();
		CommitMessage commitMsg = new CommitMessage(title, content, memberCode, manager, new GregorianCalendar(), commitCodeMap);
		String oriProjectCode = TeamFileNodeManager.getINSTANCE().searchTeamCopiedProject(teamCode, memberCode, cpProjectCode).getOriginCode();
		System.out.println("copiedproCode : "+cpProjectCode+"  oriProCode : "+oriProjectCode);
		
		if(!commitCodeMap.isEmpty()){
			//insert in to db
			Map<Alarm, CommitMessage> resCodeMap = MessageManager.getINSTANCE().putShareProjectCommitMessageSendToOwner(commitMsg,oriProjectCode);
			if(!resCodeMap.isEmpty()){
				CommitInfoManager.getINSTANCE().sendMergeTeamCommitList(teamCode, memberCode, cpProjectCode, commitCodeMap);
				this.manageMessageTeamAlarm(teamCode, oriProjectCode, resCodeMap, false);
				return true;
			}
		}
		
		return false;
	}
	
	/*public boolean insertTeamProjectCommitMessage(String memberCode, String teamCode ,String copiedProjectCode,String title, String content) throws DAOException, ParseException, FolderException, ProjectException, IOException, TeamException, FileException, SftpException, JSchException, RemoteFileException, MemberException, CommitExcetion{
		
		List<String> commitCodeList = CommitInfoManager.getINSTANCE().sendMergeTeamCommitList(memberCode, teamCode,copiedProjectCode);
		if(!commitCodeList.isEmpty()){
			Team team = TeamManager.getINSTANCE().searchTeamCode(teamCode);
			CopiedProject cpProject = ProjectManager.getINSTANCE().searchCopiedProject(FileNodeManager.getINSTANCE().searchProject(memberCode, copiedProjectCode).getPath());
			
			CommitMessage commitMsg = new CommitMessage(title, content, memberCode, team.getManager(), new GregorianCalendar(), commitCodeList);
			Map<Alarm,CommitMessage> resCommitMsgAlarm = MessageDBManager.getINSTANCE().insertSharedProjectCommitMessageSendToOwner(commitMsg);
			if(!resCommitMsgAlarm.isEmpty()){
				Alarm alarm = resCommitMsgAlarm.keySet().iterator().next();
				commitMsg = resCommitMsgAlarm.get(alarm);
				MessageManager.getINSTANCE().putMessage(commitMsg);
				AlarmManager.getINSTANCE().putAlarm(alarm);
				this.manageMessageTeamAlarm(teamCode, cpProject.getOriginCode(), resCommitMsgAlarm, false);
				return true;
			}
		}
		
		return false;
	}*/

	

	

//////////////////////////////////////////////   modify
	
	public boolean readSharedProjectCommitMessages(String memberCode,String storageCode, String oriProjectCode,String messageCode) throws DAOException, ParseException{
		
		CommitMessage commitMsg = MessageManager.getINSTANCE().getCommitMessage(messageCode);
		if(commitMsg.getReceiverCode().equals(memberCode)){
			String alarmCode = AlarmManager.getINSTANCE().searchAlarm(messageCode);
			String date = MessageDBManager.getINSTANCE().checkCommitMessageAlarm(messageCode, alarmCode);
			if(date!=null){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date1 = df.parse(date);
				GregorianCalendar check = (GregorianCalendar)Calendar.getInstance();
				check.setTime(date1);
				AlarmManager.getINSTANCE().modifyCheckDate(alarmCode, check);
				return this.memberProjectCommitMap.get(commitMsg.getReceiverCode()).get(oriProjectCode).checkUnReadNoticeCode(messageCode);
			}
			return false;
		}else return false;
	}
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void managerMessageAlarm(CommitMessage commitMsg,String originProCode,boolean read){

		String sender = commitMsg.getSenderCode(),receiver = commitMsg.getReceiverCode();
		System.out.println("sender  : "+sender+"   receiver : "+receiver);
		String commitMsgCode = commitMsg.getMessageCode();
		MessageManager.getINSTANCE().putMessage(commitMsg);
		
		if(!this.memberProjectCommitMap.containsKey(sender)){
			Map<String,Notice> notice1Map = new HashMap<String, Notice>();
			Notice note1 = new Notice();
			
			if(read){
				note1.addReadNoticeCode(commitMsgCode);
			}else{
				note1.addUnReadNoticeCode(commitMsgCode);
			}
			notice1Map.put(sender, note1);
			this.memberProjectCommitMap.put(sender, notice1Map);
		}else{
			if(!this.memberProjectCommitMap.get(sender).containsKey(originProCode)){
				Notice note1 = new Notice();

				if(read){
					note1.addReadNoticeCode(commitMsgCode);
				}else{
					note1.addUnReadNoticeCode(commitMsgCode);
				}
				this.memberProjectCommitMap.get(sender).put(originProCode, note1);
			}else{

				if(read){
					this.memberProjectCommitMap.get(sender).get(originProCode).addReadNoticeCode(commitMsgCode);
				}else{
					this.memberProjectCommitMap.get(sender).get(originProCode).addUnReadNoticeCode(commitMsgCode);
				}
			}
		}
	

		if(!this.memberProjectCommitMap.containsKey(receiver)){
			Map<String,Notice> notice1Map = new HashMap<String, Notice>();
			Notice note1 = new Notice();
			
			if(read){
				note1.addReadNoticeCode(commitMsgCode);
			}else{
				note1.addUnReadNoticeCode(commitMsgCode);
			}
			notice1Map.put(originProCode, note1);
			this.memberProjectCommitMap.put(receiver, notice1Map);
		}else{
			if(!this.memberProjectCommitMap.get(receiver).containsKey(originProCode)){
				Notice note1 = new Notice();
				
				if(read){
					note1.addReadNoticeCode(commitMsgCode);
				}else{
					note1.addUnReadNoticeCode(commitMsgCode);
				}
				this.memberProjectCommitMap.get(receiver).put(originProCode, note1);
			}else{
				if(read){
					this.memberProjectCommitMap.get(receiver).get(originProCode).addReadNoticeCode(commitMsgCode);
				}else{
					this.memberProjectCommitMap.get(receiver).get(originProCode).addUnReadNoticeCode(commitMsgCode);
				}
			}
		}
	}	
	
	private void manageMessageTeamAlarm(String teamCode, String oriProjectCode,Map<Alarm,CommitMessage> proMsgMap,boolean read){
		
		for(Alarm alarm : proMsgMap.keySet()){
			System.out.println("alarmCode------------------  "+alarm.getAlarmCode());
			CommitMessage commitMsg = proMsgMap.get(alarm);
			System.out.println("commitMsg------------------  "+commitMsg.getMessageCode());
			String sender = commitMsg.getSenderCode();
			String receiver = commitMsg.getReceiverCode();
			String msgCode = commitMsg.getMessageCode();
			MessageManager.getINSTANCE().putMessage(commitMsg);
			
			if(!this.teamProjectCommitMap.containsKey(teamCode)){
				Map<String,Map<String,Notice>> map1 = new HashMap<String, Map<String,Notice>>();
				Map<String,Notice> val1 = new HashMap<String, Notice>();
				Notice note = new Notice();
				
				if(read){
					note.addReadNoticeCode(msgCode);
				}else{
					note.addUnReadNoticeCode(msgCode);
				}
				val1.put(oriProjectCode, note);
				map1.put(sender, val1);
				this.teamProjectCommitMap.put(teamCode, map1);
			}else{
				if(!this.teamProjectCommitMap.get(teamCode).containsKey(sender)){
					Map<String,Notice> notice1Map = new HashMap<String, Notice>();
					Notice note1 = new Notice();

					if(read){
						note1.addReadNoticeCode(msgCode);
					}else{
						note1.addUnReadNoticeCode(msgCode);
					}
					notice1Map.put(oriProjectCode, note1);
					this.teamProjectCommitMap.get(teamCode).put(sender, notice1Map);
				}else{
					
					if(!this.teamProjectCommitMap.get(teamCode).get(sender).containsKey(oriProjectCode)){
						Notice note1 = new Notice();
						
						if(read){
							note1.addReadNoticeCode(msgCode);
						}else{
							note1.addUnReadNoticeCode(msgCode);
						}
						this.teamProjectCommitMap.get(teamCode).get(sender).put(oriProjectCode, note1);
					}else{
						if(read){
							this.teamProjectCommitMap.get(teamCode).get(sender).get(oriProjectCode).addReadNoticeCode(msgCode);
						}else{
							this.teamProjectCommitMap.get(teamCode).get(sender).get(oriProjectCode).addUnReadNoticeCode(msgCode);
						}
					}
				}
			}
			if(!this.teamProjectCommitMap.get(teamCode).containsKey(receiver)){
				Map<String,Notice> notice1Map = new HashMap<String, Notice>();
				Notice note1 = new Notice();

				if(read){
					note1.addReadNoticeCode(msgCode);
				}else{
					MemberAlarmManager.getINSTANCE().insertNewAlarm(receiver, "commitMessage", alarm.getAlarmCode());
					MemberAlarmManager.getINSTANCE().insertNewAlarm(sender, "commitMessage", alarm.getAlarmCode());
					note1.addUnReadNoticeCode(msgCode);
				}
				notice1Map.put(oriProjectCode, note1);
				this.teamProjectCommitMap.get(teamCode).put(receiver, notice1Map);
			}else{
				
				if(!this.teamProjectCommitMap.get(teamCode).get(receiver).containsKey(oriProjectCode)){
					Notice note1 = new Notice();
					
					if(read){
						note1.addReadNoticeCode(msgCode);
					}else{
						MemberAlarmManager.getINSTANCE().insertNewAlarm(receiver, "commitMessage", alarm.getAlarmCode());
						MemberAlarmManager.getINSTANCE().insertNewAlarm(sender, "commitMessage", alarm.getAlarmCode());
						note1.addUnReadNoticeCode(msgCode);
					}
					this.teamProjectCommitMap.get(teamCode).get(receiver).put(oriProjectCode, note1);
				}else{
					if(read){
						this.teamProjectCommitMap.get(teamCode).get(receiver).get(oriProjectCode).addReadNoticeCode(msgCode);
					}else{
						MemberAlarmManager.getINSTANCE().insertNewAlarm(receiver, "commitMessage", alarm.getAlarmCode());
						MemberAlarmManager.getINSTANCE().insertNewAlarm(sender, "commitMessage", alarm.getAlarmCode());
						this.teamProjectCommitMap.get(teamCode).get(receiver).get(oriProjectCode).addUnReadNoticeCode(msgCode);
					}
				}
			}
		}
	}
	
	private void manageMessageAlarm(String oriProjectCode,Map<Alarm,CommitMessage> proMsgMap,boolean read) throws DAOException, ParseException, FolderException, IOException{
		
		System.out.println("\n      CommitMessageManager     manageMessageAlarm   line   1286");
		String receiver = new String();
		String sender;
		for(Alarm alarm : proMsgMap.keySet()){
			
			CommitMessage commitMsg = proMsgMap.get(alarm);
			//test
			System.out.println("8888888888888888888888888888888888888888888");
			if(!commitMsg.getCommitMap().isEmpty()){
			for(String objCode : commitMsg.getCommitMap().keySet()){
				System.out.println("start  objCode : "+objCode);
				List<String> li = commitMsg.getCommitMap().get(objCode);
				if(!li.isEmpty()){
					for(String c : li){
						System.out.print(c+"  //  ");
					}
				}
				System.out.println("end  objCode : "+objCode);
			}
			}
			System.out.println("8888888888888888888888888888888888888888888");
			
			System.out.println("CommitMessage  : "+commitMsg.getMessageCode());
			sender = commitMsg.getSenderCode();
			receiver = commitMsg.getReceiverCode();
			//load Message
			//MemberAlarmManager.getINSTANCE().loadNewAlarm(model.getCode());
			//insert alarm
			AlarmManager.getINSTANCE().putAlarm(alarm);
			
			String msgCode = commitMsg.getMessageCode();
			//
			MessageManager.getINSTANCE().putMessage(commitMsg);
			
			if(!this.memberProjectCommitMap.containsKey(sender)){
				Map<String,Map<String,Notice>> map1 = new HashMap<String, Map<String,Notice>>();
				Map<String,Notice> val1 = new HashMap<String, Notice>();
				Notice note = new Notice();
				
				if(read){
					note.addReadNoticeCode(msgCode);
				}else{
					note.addUnReadNoticeCode(msgCode);
				}
				val1.put(oriProjectCode, note);
				this.memberProjectCommitMap.put(sender, val1);
			}else{
				if(!this.memberProjectCommitMap.get(sender).containsKey(oriProjectCode)){
					Notice note1 = new Notice();

					if(read){
						note1.addReadNoticeCode(msgCode);
					}else{
						MemberAlarmManager.getINSTANCE().insertNewAlarm(receiver, "commitMessage", alarm.getAlarmCode());
						MemberAlarmManager.getINSTANCE().insertNewAlarm(sender, "commitMessage", alarm.getAlarmCode());
						note1.addUnReadNoticeCode(msgCode);
					}
					this.memberProjectCommitMap.get(sender).put(oriProjectCode, note1);
				}else{
					if(read){
						this.memberProjectCommitMap.get(sender).get(oriProjectCode).addReadNoticeCode(msgCode);
					}else{
						MemberAlarmManager.getINSTANCE().insertNewAlarm(receiver, "commitMessage", alarm.getAlarmCode());
						MemberAlarmManager.getINSTANCE().insertNewAlarm(sender, "commitMessage", alarm.getAlarmCode());
						this.memberProjectCommitMap.get(sender).get(oriProjectCode).addUnReadNoticeCode(msgCode);
					}
				}
			}

			if(!this.memberProjectCommitMap.containsKey(receiver)){
				Map<String,Map<String,Notice>> map1 = new HashMap<String, Map<String,Notice>>();
				Map<String,Notice> val1 = new HashMap<String, Notice>();
				Notice note = new Notice();
				
				if(read){
					note.addReadNoticeCode(msgCode);
				}else{
					MemberAlarmManager.getINSTANCE().insertNewAlarm(receiver, "commitMessage", alarm.getAlarmCode());
					MemberAlarmManager.getINSTANCE().insertNewAlarm(sender, "commitMessage", alarm.getAlarmCode());
					note.addUnReadNoticeCode(msgCode);
				}
				val1.put(oriProjectCode, note);
				this.memberProjectCommitMap.put(receiver, val1);
			}else{
				if(!this.memberProjectCommitMap.get(receiver).containsKey(oriProjectCode)){
					Notice note1 = new Notice();

					if(read){
						note1.addReadNoticeCode(msgCode);
					}else{
						MemberAlarmManager.getINSTANCE().insertNewAlarm(receiver, "commitMessage", alarm.getAlarmCode());
						MemberAlarmManager.getINSTANCE().insertNewAlarm(sender, "commitMessage", alarm.getAlarmCode());
						note1.addUnReadNoticeCode(msgCode);
					}
					this.memberProjectCommitMap.get(receiver).put(oriProjectCode, note1);
				}else{
					if(read){
						this.memberProjectCommitMap.get(receiver).get(oriProjectCode).addReadNoticeCode(msgCode);
					}else{
						MemberAlarmManager.getINSTANCE().insertNewAlarm(receiver, "commitMessage", alarm.getAlarmCode());
						MemberAlarmManager.getINSTANCE().insertNewAlarm(sender, "commitMessage", alarm.getAlarmCode());
						this.memberProjectCommitMap.get(receiver).get(oriProjectCode).addUnReadNoticeCode(msgCode);
					}
				}
			}
		}
		
		//test
		if(this.memberProjectCommitMap.containsKey(receiver)){
			if(this.memberProjectCommitMap.get(receiver).containsKey(oriProjectCode)){
				List<String> code = this.memberProjectCommitMap.get(receiver).get(oriProjectCode).getUnreadNotice();
				if(!code.isEmpty()){
					int s = code.size();
					System.out.println("insert    "+code.get(s-1)+"  en d");
				}else System.out.println("code is Empty");
			}else System.out.println("no project : "+oriProjectCode);
			
		}else System.out.println("no receiver : "+receiver);
		System.out.println("      CommitMessageManager     manageMessageAlarm   line   END");
	}
	
	private void manageMessageGroupAlarm(String teamCode, String oriProCode, Map<GroupAlarm,CommitMessage> msgMap,boolean read) throws DAOException, MemberException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion, TeamException{
		
		System.out.println("\n*CommitMessageManager    :   manageMessageGroupAlarm(String teamCode, String oriProCode, Map<CommitMessage,List<GroupAlarm>> msgMap,boolean read)");
		
		for(GroupAlarm gAlarm : msgMap.keySet()){
			
			AlarmManager.getINSTANCE().putGroupAlarm(gAlarm);
			CommitMessage commitMsg = msgMap.get(gAlarm);
			String commitMsgCode = commitMsg.getMessageCode();
			MessageManager.getINSTANCE().putMessage(commitMsg);
			Map<String,GregorianCalendar> memberCheckDateMap = gAlarm.getMemberCheckMap();
			String gAlarmCode = gAlarm.getAlarmCode(); 
			List<String> cooperCodeList = new ArrayList<String>();
			if(!memberCheckDateMap.isEmpty()){
				
				for(String cooperCode : memberCheckDateMap.keySet()){
					
					cooperCodeList.add(cooperCode);
					if(!this.teamProjectCommitMap.containsKey(teamCode)){
						Map<String, Map<String,Notice>> valMap1 = new HashMap<String, Map<String,Notice>>();
						Map<String,Notice> notice1Map = new HashMap<String, Notice>();
						Notice note1 = new Notice();
						
						if(read){
							note1.addReadNoticeCode(commitMsgCode);
						}else{
							note1.addUnReadNoticeCode(commitMsgCode);
						}
						notice1Map.put(oriProCode, note1);
						valMap1.put(cooperCode, notice1Map);
						this.teamProjectCommitMap.put(teamCode, valMap1);
					}else{
						if(!this.teamProjectCommitMap.get(teamCode).containsKey(cooperCode)){
							Map<String,Notice> notice1Map = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							
							if(read){
								note1.addReadNoticeCode(commitMsgCode);
							}else{
								note1.addUnReadNoticeCode(commitMsgCode);
							}
							notice1Map.put(oriProCode, note1);
							this.teamProjectCommitMap.get(teamCode).put(cooperCode, notice1Map);
						}else{
							if(!this.teamProjectCommitMap.get(teamCode).get(cooperCode).containsKey(oriProCode)){
								Notice note1 = new Notice();
								
								if(read){
									note1.addReadNoticeCode(commitMsgCode);
								}else{
									note1.addUnReadNoticeCode(commitMsgCode);
								}
								this.teamProjectCommitMap.get(teamCode).get(cooperCode).put(oriProCode, note1);
							}else{
								if(read){
									this.teamProjectCommitMap.get(teamCode).get(cooperCode).get(oriProCode).addReadNoticeCode(commitMsgCode);
								}else{
									this.teamProjectCommitMap.get(teamCode).get(cooperCode).get(oriProCode).addUnReadNoticeCode(commitMsgCode);
								}
							}
						}
					}
				}
				if(!read)MemberAlarmManager.getINSTANCE().insertNewGroupAlarm(cooperCodeList, "commitMessage", gAlarmCode);
				
				if(!this.teamProjectCommitMap.get(teamCode).containsKey(teamCode)){
					Map<String,Notice> notice1Map = new HashMap<String, Notice>();
					Notice note1 = new Notice();
					
					if(read){
						note1.addReadNoticeCode(commitMsgCode);
					}else{
						note1.addUnReadNoticeCode(commitMsgCode);
					}
					notice1Map.put(oriProCode, note1);
					this.teamProjectCommitMap.get(teamCode).put(teamCode, notice1Map);
				}else{
					if(!this.teamProjectCommitMap.get(teamCode).get(teamCode).containsKey(oriProCode)){
						Notice note1 = new Notice();
						
						if(read){
							note1.addReadNoticeCode(commitMsgCode);
						}else{
							note1.addUnReadNoticeCode(commitMsgCode);
						}
						this.teamProjectCommitMap.get(teamCode).get(teamCode).put(oriProCode, note1);
					}else{
						if(read){
							this.teamProjectCommitMap.get(teamCode).get(teamCode).get(oriProCode).addReadNoticeCode(commitMsgCode);
						}else{
							this.teamProjectCommitMap.get(teamCode).get(teamCode).get(oriProCode).addUnReadNoticeCode(commitMsgCode);
						}
					}
				}
				
			}
		}
		
		System.out.println("*CommitMessageManager (end)    :   manageMessageGroupAlarm(String teamCode, String oriProCode, String commitMsgCode, List<GroupAlarm> alarmList,boolean read)");
	}
	
	private Map<String,List<CommitMessageInfoForm>> makeNoticeToMap(Map<String,Notice> noteMap) throws DAOException, ParseException, MemberException{
		Map<String,List<CommitMessageInfoForm>> resMap = new HashMap<String,List<CommitMessageInfoForm>>();
		for(Notice memberTmpNote : noteMap.values()){
			System.out.println("\nmake---------  it");
			List<String> unreadList = memberTmpNote.getUnreadNotice();
			List<String> readList = memberTmpNote.getReadNotice();
			System.out.println("readList  : "+readList.size()+"  unreadList : "+unreadList.size());
			
			List<CommitMessageInfoForm> valList = new ArrayList<CommitMessageInfoForm>();
			List<CommitMessageInfoForm> valList2 = new ArrayList<CommitMessageInfoForm>();
			
			if(!unreadList.isEmpty()){
				for(String unreadCode : unreadList){
					CommitMessage commitMsg = MessageManager.getINSTANCE().getCommitMessage(unreadCode);
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					df.setTimeZone(commitMsg.getSendDate().getTimeZone());
					String senderNickName = MemberManager.getINSTANCE().searchMemberCode(commitMsg.getSenderCode()).getNickName();
					String receiverNickName = MemberManager.getINSTANCE().searchMemberCode(commitMsg.getReceiverCode()).getNickName();
					valList2.add(new CommitMessageInfoForm(unreadCode, commitMsg.getTitle(), commitMsg.getSenderCode(), commitMsg.getReceiverCode(),receiverNickName, df.format(commitMsg.getSendDate().getTime()),senderNickName));
				}
			}

			if(!readList.isEmpty()){
				if(!readList.isEmpty()){
					
					for(String unreadCode : readList){
						CommitMessage commitMsg = MessageManager.getINSTANCE().getCommitMessage(unreadCode);
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
						df.setTimeZone(commitMsg.getSendDate().getTimeZone());
						String senderNickName = MemberManager.getINSTANCE().searchMemberCode(commitMsg.getSenderCode()).getNickName();
						String receiverNickName = MemberManager.getINSTANCE().searchMemberCode(commitMsg.getReceiverCode()).getNickName();
						valList.add(new CommitMessageInfoForm(unreadCode, commitMsg.getTitle(), commitMsg.getSenderCode(), commitMsg.getReceiverCode(),receiverNickName, df.format(commitMsg.getSendDate().getTime()),senderNickName));
					}
				}
			}
			resMap.put("unread", valList2);
			resMap.put("read", valList);
			System.out.println("make   end---------  it\n");
		}
		
		return resMap;
	}

	private Map<String,List<CommitMessageInfoForm>> makeNoticeToMap(Notice memberTmpNote) throws DAOException, ParseException, MemberException{
		Map<String,List<CommitMessageInfoForm>> resMap = new HashMap<String,List<CommitMessageInfoForm>>();
		System.out.println("\nmake---------  it");
		List<String> unreadList = memberTmpNote.getUnreadNotice();
		List<String> readList = memberTmpNote.getReadNotice();
		System.out.println("readList  : "+readList.size()+"  unreadList : "+unreadList.size());
		
		List<CommitMessageInfoForm> valList = new ArrayList<CommitMessageInfoForm>();
		List<CommitMessageInfoForm> valList2 = new ArrayList<CommitMessageInfoForm>();
		
		if(!unreadList.isEmpty()){
			for(String unreadCode : unreadList){
				CommitMessage commitMsg = MessageManager.getINSTANCE().getCommitMessage(unreadCode);
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				df.setTimeZone(commitMsg.getSendDate().getTimeZone());
				String senderNickName = MemberManager.getINSTANCE().searchMemberCode(commitMsg.getSenderCode()).getNickName();
				String receiverNickName = MemberManager.getINSTANCE().searchMemberCode(commitMsg.getReceiverCode()).getNickName();
				valList2.add(new CommitMessageInfoForm(unreadCode, commitMsg.getTitle(), commitMsg.getSenderCode(), commitMsg.getReceiverCode(),receiverNickName, df.format(commitMsg.getSendDate().getTime()),senderNickName));
			}
		}

		if(!readList.isEmpty()){
			if(!readList.isEmpty()){
				
				for(String unreadCode : readList){
					CommitMessage commitMsg = MessageManager.getINSTANCE().getCommitMessage(unreadCode);
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					df.setTimeZone(commitMsg.getSendDate().getTimeZone());
					String senderNickName = MemberManager.getINSTANCE().searchMemberCode(commitMsg.getSenderCode()).getNickName();
					String receiverNickName = MemberManager.getINSTANCE().searchMemberCode(commitMsg.getReceiverCode()).getNickName();
					valList.add(new CommitMessageInfoForm(unreadCode, commitMsg.getTitle(), commitMsg.getSenderCode(), commitMsg.getReceiverCode(),receiverNickName, df.format(commitMsg.getSendDate().getTime()),senderNickName));
				}
			}
		}
		resMap.put("unread", valList2);
		resMap.put("read", valList);
		System.out.println("make   end---------  it\n");
		
		return resMap;
	}
	
	private CommitMsgDetails makeCommitMsgDetails(Notice memberTmpNote,CommitMessage commitMsg) throws FileNotFoundException, DAOException, FileException, SftpException, IOException, JSchException, RemoteFileException, ParseException, FolderException, MemberException, ProjectException, TeamException{
		System.out.println("\n      CommitMessageManager     makeCommitMsgDetails     line   1579");
		CommitMsgDetails res = null; 
		Map<String, List<String>> commitMap = commitMsg.getCommitMap();
		if(commitMap.isEmpty())return new CommitMsgDetails();
		
		List<String> commitList = new ArrayList<String>();
		for(String objCode : commitMap.keySet()){
			System.out.println("objCode : "+objCode);
			List<String> commList = commitMap.get(objCode);
			if(!commList.isEmpty())commitList.addAll(commList);
		}
		
		if(!commitList.isEmpty()){
			res = new CommitMsgDetails();
			res.setMsgContent(commitMsg.getContent());
			List<FileForm> fileFormList = new ArrayList<FileForm>();
			Map<String,List<CommitInfo>> commitInfoMap = CommitInfoManager.getINSTANCE().searchCommitCodeListForInfoMap(commitList);
			
			for(String objCode : commitInfoMap.keySet()){
			System.out.println("code : "+objCode);
			List<CommitInfo> commitInfoList = commitInfoMap.get(objCode);
			if(!commitInfoList.isEmpty()){
				
				for(CommitInfo commitInfo : commitInfoList){
					String commitObjCode = commitInfo.getObjectCode();
					String objName=null;
					
					StringTokenizer tokens = new StringTokenizer(commitObjCode, "_");
					if(tokens.nextToken().equals("folder")){
						//if folder
						OriginFolder folder = (OriginFolder)FolderManager.getINSTANCE().searchFolderByFolderCode(commitObjCode);
						if(folder!=null)objName = folder.getName();
						fileFormList.add(new FileForm(commitInfo.getCode(), objName, commitInfo.getOriginFileDescriptioin(), commitInfo.getDate(), commitInfo.getDescription(), commitInfo.getTitle(), commitInfo.getContent()));
					}else{
						//if file
						OriginFile file = (OriginFile)FileManager.getINSTANCE().searchFileCode(commitObjCode);
						if(file!=null)objName = file.getName();
						String fileDes=null;
						String fileOriDes = null;
						if(commitInfo.getDescription()==null){
							fileDes=((OriginFile)FileManager.getINSTANCE().searchFileContentCode(objCode)).getContent();
							fileOriDes = fileDes;
						}else {
							fileDes = commitInfo.getDescription();
							fileOriDes = commitInfo.getOriginFileDescriptioin();
						}
						fileFormList.add(new FileForm(commitInfo.getCode(), objName, fileOriDes, commitInfo.getDate(), fileDes, commitInfo.getTitle(), commitInfo.getContent()));
					}
					
					System.out.println("add!!! : "+objName+"  objCode : "+commitObjCode);
					
					
				}
			}
			}
			res.setFileList(fileFormList);
			System.out.println("fileFormList  : "+fileFormList.size());
			return res;
		}
		else{System.out.println("commitList is NULLL.**");}
		return res;
	}
}