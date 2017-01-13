package manager;

import java.io.File;
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
import java.util.TreeMap;

import javax.security.auth.login.LoginException;

import model.CommitInfo;
import model.CommitMessage;
import model.CopiedFile;
import model.CopiedFolder;
import model.CopiedProject;
import model.CopiedProjectList;
import model.FileNode;
import model.MyOriginFile;
import model.OriginFile;
import model.OriginFolder;
import model.OriginProject;
import model.OriginProjectList;
import model.Team;
import socialProExceptions.CommitExcetion;
import socialProExceptions.DAOException;
import socialProExceptions.FileException;
import socialProExceptions.FolderException;
import socialProExceptions.MemberException;
import socialProExceptions.ProjectException;
import socialProExceptions.RemoteFileException;
import socialProExceptions.TagException;
import socialProExceptions.TeamException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import form.FolderForm;
import form.FolderPathForm;
import form.LoadProjectForm;
import form.SimpleMemberForm;

public class TeamFileNodeManager {

	private static TeamFileNodeManager INSTANCE;
	private Map<String,Map<String,FileNode>> teamFileMap;//teamCode, projectCode, Project (shared Project)
	private Map<String,Map<String,Map<String,FileNode>>> teamMemberFileMap;//teamCode,memberCode, projectCode, Project (team member's project copied)
	
	static{
		INSTANCE = new TeamFileNodeManager();
	}
	
	private TeamFileNodeManager(){
		this.teamFileMap = new HashMap<String, Map<String,FileNode>>();
		this.teamMemberFileMap = new HashMap<String, Map<String,Map<String,FileNode>>>();
	}

	public static TeamFileNodeManager getINSTANCE() {
		if(INSTANCE==null)INSTANCE = new TeamFileNodeManager();
		return INSTANCE;
	}


	public String createTeamProject(String teamCode, String teamName,String projectName, String proDes, String path, List<String> tagList) throws DAOException, SftpException, JSchException, FolderException, ProjectException, TeamException, CommitExcetion, TagException {
		// TODO Auto-generated method stub
		
		//check if Team projectList is loaded?
		if(this.teamFileMap.containsKey(teamCode)){
			for(FileNode fileNode : this.teamFileMap.get(teamCode).values()){
				if(fileNode.getPath().equals(path))throw new ProjectException("This team Project Name has alreay used!");
			}
		}
		
		List<File> fileList = ProjectDBManager.getINSTANCE().insertCopiedTeamProject(teamCode, teamName, projectName, proDes, path, tagList);
		String projectCode=null;
		if(!fileList.isEmpty()){
			Map<String, FileNode> teamProMap;
			Map<String,Map<String,FileNode>> teamMemCProMap;
			if(this.teamFileMap.containsKey(teamCode)){
				teamProMap = this.teamFileMap.get(teamCode);
			}
			else{
				teamProMap = new HashMap<String, FileNode>();
			}
			if(this.teamMemberFileMap.containsKey(teamCode)){
				teamMemCProMap = this.teamMemberFileMap.get(teamCode);
			}else{
				teamMemCProMap = new HashMap<String, Map<String,FileNode>>();
			}
			
			for(File file:fileList){
				if(file instanceof CopiedProject){
					CopiedProject pro = (CopiedProject)file;
					if(teamMemCProMap.containsKey(pro.getOwner())){
						(teamMemCProMap.get(pro.getOwner())).put(pro.getCode(), new FileNode(null, new MyOriginFile(pro.getCode(), pro.getName(), pro.getPath(),"project")));
					}else{
						Map<String, FileNode> tmpMemMap = new HashMap<String, FileNode>();
						tmpMemMap.put(pro.getOwner(), new FileNode(null, new MyOriginFile(pro.getCode(), pro.getName(), pro.getPath(),"project")));
						teamMemCProMap.put(pro.getOwner(), tmpMemMap);
					}
				}if(file instanceof OriginProject){
					OriginProject pro = (OriginProject)file;
					projectCode = pro.getCode();
					FileNode newNode = new FileNode(null, new MyOriginFile(pro.getCode(), pro.getName(), pro.getPath(),"project"));
					teamProMap.put(pro.getCode(),newNode);
				}
			}
		}
		return projectCode;
	}
	
	public String createTeamProject(String managerCode,String branchCode,String teamCode, String teamName,String projectName, String proDes, String path, List<String> tagList,String commitTitle, String commitContent) throws DAOException, SftpException, JSchException, FolderException, ProjectException, TeamException, CommitExcetion, TagException, ParseException, IOException, MemberException, FileException, RemoteFileException {
		// TODO Auto-generated method stub
		
		//check is member is team manager
		if(managerCode.equals(TeamManager.getINSTANCE().searchTeamCode(teamCode).getManager())){

			//check if Team projectList is loaded?
			if(this.teamFileMap.containsKey(teamCode)){
				for(FileNode fileNode : this.teamFileMap.get(teamCode).values()){
					if(fileNode.getPath().equals(path))throw new ProjectException("This team Project Name has alreay used!");
				}
			}
			List<File> fileList = ProjectManager.getINSTANCE().createTeamProject(teamCode, teamName, projectName, proDes, path, tagList, commitTitle, commitContent);
			String projectCode=null;
			String oriprojectCode=null;
			if(!fileList.isEmpty()){
			for(File file:fileList){
				if(file instanceof CopiedProject){
					if(((CopiedProject) file).getOwner().equals(managerCode))projectCode = ((CopiedProject) file).getCode();
					this.loadTeamMemProjectByCC(teamCode, ((CopiedProject) file).getOwner(), ((CopiedProject) file).getCode());
				}
				else if(file instanceof OriginProject){
					this.loadSharedTeamProjectCode(teamCode, ((OriginProject) file).getCode());
					oriprojectCode = ((OriginProject) file).getCode();
				}
			}
			}
			if(branchCode.equals(teamCode))return oriprojectCode;
			return projectCode;
		}
		
		return null;
	}

	public String createTeamFolder(String teamCode, String teamName, String branchCode, String memberCode, String projectCode, List<String> folderCodes, String folderName, String folderDes, String commitTitle, String commitContent) throws DAOException, ParseException, SftpException, JSchException, CommitExcetion, FolderException, TeamException, ProjectException, IOException, MemberException, FileException, RemoteFileException{
	
		String managerCode = TeamManager.getINSTANCE().searchTeamCode(teamCode).getManager();
		if(managerCode.equals(memberCode)){return this.createTeamSharedFolder(teamCode, teamName, branchCode, projectCode, folderCodes, folderName, folderDes, commitTitle, commitContent);}
		else{return this.createTeamCooperFolder(teamCode, teamName, branchCode, memberCode, projectCode, folderCodes, folderName, folderDes, commitTitle, commitContent);}
	}
	
	public String createTeamSharedFolder(String teamCode, String teamName, String branchCode, String projectCode, List<String> folderCodes, String folderName, String folderDes, String commitTitle, String commitContent) throws DAOException, ParseException, SftpException, JSchException, CommitExcetion, FolderException, TeamException, ProjectException, IOException, MemberException, FileException, RemoteFileException{
		
		FileNode project;
		boolean isBTEqual=false;
		if(!this.teamFileMap.containsKey(teamCode)){this.loadSharedTeamProject(teamCode);}
		if(!this.teamMemberFileMap.containsKey(teamCode))this.loadCopiedTeamProject(teamCode);
		if(branchCode.equals(teamCode)){
			System.out.println("branch teamCode is same..");
			isBTEqual=true;
			if(!this.teamFileMap.get(teamCode).containsKey(projectCode)){this.loadSharedTeamProjectCode(teamCode, projectCode);}
			project = this.teamFileMap.get(teamCode).get(projectCode);
		}else{
			System.out.println("branch teamCode is diff..");
			Team teamInfo = TeamManager.getINSTANCE().searchTeamCode(teamCode);
			String path = ProjectManager.getINSTANCE().justSearchProjectCode(projectCode).getPath();
			CopiedProject cpProject = ProjectManager.getINSTANCE().searchOnlyCpProjectByProjectPath(path);
			String oriProCode = cpProject.getOriginCode();
			System.out.println("oriProCode    ::  forLoad!!!!!    "+oriProCode+"   copiedOwner   ::  "+cpProject.getOwner()+"  branch  ::: "+branchCode);
			for(String memCode : teamInfo.getCooperatorList()){
				if(!this.teamMemberFileMap.get(teamCode).containsKey(memCode))this.loadTeamMemProjects(teamCode, memCode);
				if(memCode.equals(cpProject.getOwner())){
					if(!this.teamMemberFileMap.get(teamCode).get(memCode).containsKey(projectCode))this.loadTeamMemProjectByOC(teamCode, memCode, oriProCode);
				}
			}
			if(!this.teamMemberFileMap.get(teamCode).get(branchCode).isEmpty()){
				for(String proCode : this.teamMemberFileMap.get(teamCode).get(branchCode).keySet()){
					System.out.println("ppp  "+proCode);
				}
			}
			
			project = this.teamMemberFileMap.get(teamCode).get(branchCode).get(projectCode);
		}
		int cnt = folderCodes.size();
		//System.out.println(project.getChildFolderList().get(0)+"  //  "+folderCodes.get(1)+"  //  cnt :"+cnt);
		for(int i=1;i<cnt;i++){
			project = project.getChild(folderCodes.get(i));
		}
		System.out.println("parentInfo  :::::  "+project.getCode()+"     //    "+project.getPath());
		
		Map<String, CopiedFolder> resMap= FolderManager.getINSTANCE().createCopiedFolderByPFCode(project.getPath(), teamCode, teamName, folderName, folderDes);
		if(!resMap.isEmpty()){
			//insert Commit
			for(String mem : resMap.keySet()){
				CopiedFolder cpFile = resMap.get(mem);
				
				CommitInfo commitInfo = new CommitInfo(cpFile.getCode(), "create", commitContent, cpFile.getMakeDate(), mem, true, commitTitle);
				String proCode;
				if(mem.equals(teamCode)){
					StringTokenizer pathTokens = new StringTokenizer(cpFile.getPath(), "\\");
					String fpath=pathTokens.nextElement()+"\\"+pathTokens.nextElement()+"\\"+pathTokens.nextElement()+"\\"+pathTokens.nextElement();
					proCode = ProjectManager.getINSTANCE().searchASelectOriginProject(fpath).getCode();
				}else {
					StringTokenizer pathTokens = new StringTokenizer(cpFile.getPath(), "\\");
					String fpath=pathTokens.nextElement()+"\\"+pathTokens.nextElement()+"\\"+pathTokens.nextElement()+"\\"+pathTokens.nextElement()+"\\"+pathTokens.nextElement();
					proCode = ProjectManager.getINSTANCE().searchASelectCopiedProject(fpath).getCode();
				}
				CommitInfoManager.getINSTANCE().putTeamCommitInfo(teamCode,mem, proCode, commitInfo);
			}
			Map<String,String> resMap2 = this.insertCopiedTeamFolder(resMap, teamCode);
			
			if(!resMap2.isEmpty()){
				String last;
				if(isBTEqual){
					project = this.teamFileMap.get(teamCode).get(projectCode);
					last = resMap2.get(teamCode);
				}else{
					project = this.teamMemberFileMap.get(teamCode).get(branchCode).get(projectCode);
					last = resMap2.get(branchCode);
				}
				for(int i=1;i<cnt;i++){
					project = project.getChild(folderCodes.get(i));
				}
				System.out.println(isBTEqual+"  "+last+"  parent : "+project.getPath());
				project = project.getChild(last);
				if(project==null)System.out.println("project is null.....");
				System.out.println("  path : "+project.getPath());
				return project.getCode();
			}
		}
		
		return null;
	}

	public String createTeamCooperFolder(String teamCode, String teamName, String branchCode, String memberCode, String projectCode, List<String> folderCodes, String folderName, String folderDes, String commitTitle, String commitContent) throws DAOException, ParseException, SftpException, JSchException, CommitExcetion, FolderException, TeamException, ProjectException, IOException, MemberException, FileException, RemoteFileException{
		
		FileNode project;
		boolean isBTEqual=false;
		if(!this.teamFileMap.containsKey(teamCode)){this.loadSharedTeamProject(teamCode);}
		if(!this.teamMemberFileMap.containsKey(teamCode))this.loadCopiedTeamProject(teamCode);
		
		project = this.teamMemberFileMap.get(teamCode).get(memberCode).get(projectCode);
		
		String ownerName = MemberManager.getINSTANCE().searchMemberCode(memberCode).getNickName();
		OriginFolder oriFolder = FolderManager.getINSTANCE().createOriginTeamCooperFolder(project.getPath(), memberCode, ownerName, folderName, folderDes);
		if(oriFolder!=null){
			//insert commit
			
			CommitInfoManager.getINSTANCE().putTeamCommitInfo(teamCode, memberCode, projectCode, new CommitInfo(oriFolder.getCode(), "create", commitContent, oriFolder.getMakeDate(), memberCode, false, null, commitTitle));
			return oriFolder.getCode();
		}
		return null;
	}
	
	public List<LoadProjectForm> loadProjectMCode(String memberCode, String teamCode) throws DAOException, ParseException, FolderException, IOException, ProjectException, TeamException, SftpException, JSchException, CommitExcetion{
		
		System.out.println("TeamFileNode Manager   ....  loadProjectMCode  **   "+memberCode);
		List<LoadProjectForm> resList = new ArrayList<LoadProjectForm>();
		if(!this.teamFileMap.containsKey(teamCode)){
			System.out.println("must load TEam Shared Project List");
			if(!this.loadSharedTeamProject(teamCode))return resList;
			System.out.println("Team shared Project is successfully loaded!!!!!!!!!  ");
			if(!this.teamFileMap.containsKey(teamCode))return resList;
		}
		if(!memberCode.equals(teamCode)){
			if(!this.teamMemberFileMap.containsKey(teamCode)){
				System.out.println("must load TEam Copied Project List");
				if(!this.loadCopiedTeamProject(teamCode))return resList;
				if(!this.teamMemberFileMap.containsKey(teamCode))return resList;
				if(!this.teamMemberFileMap.get(teamCode).containsKey(memberCode))this.loadTeamMemProjects(teamCode, memberCode);
				if(!this.teamMemberFileMap.get(teamCode).containsKey(memberCode))return resList;
			}
		}
		
		if(memberCode.equals(teamCode)){
			Map<String,FileNode> teamPro= this.teamFileMap.get(teamCode);
			if(!teamPro.isEmpty()){
				for(FileNode proNode : teamPro.values()){
					OriginProject pro = (OriginProject)ProjectManager.getINSTANCE().searchProjectByProjectPath(proNode.getPath());
					
					LoadProjectForm resObj = new LoadProjectForm(pro.getName(), pro.getCode(), pro.getDescription(), pro.getMakeDate(), pro.getSharedMemberList().size(), "teamProejct");
					resList.add(resObj);
				}
			}
		}else{
			if(!this.teamMemberFileMap.get(teamCode).containsKey(memberCode)){
				this.loadTeamMemProjects(teamCode, memberCode);
				if(!this.teamMemberFileMap.get(teamCode).containsKey(memberCode))return resList;
			}
			Map<String,FileNode> teamPro= this.teamMemberFileMap.get(teamCode).get(memberCode);
			if(!teamPro.isEmpty()){
				for(FileNode proNode : teamPro.values()){
					CopiedProject pro = (CopiedProject)ProjectManager.getINSTANCE().searchProjectByProjectPath(proNode.getPath());
					
					LoadProjectForm resObj = new LoadProjectForm(pro.getName(), pro.getCode(), pro.getDescription(), pro.getMakeDate(), pro.getSharedMemberList().size(), "teamProejct");
					resList.add(resObj);
				}
			}
		}
		return resList;
	}
	
	private boolean loadSharedTeamProject(String teamCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion{
		
		System.out.println("\n    @@ TeamFileNodeManager      loadSharedTeamProject     line     314");
		OriginProjectList proList=ProjectManager.getINSTANCE().searchOnlyOriginProjectByOwnerCode(teamCode);
		
		if(!proList.getList().isEmpty()){
			System.out.println("team SHared Project is not null!!!");
			this.makeOriginMap(proList, teamCode);
		}else System.out.println("team SHared Project is null!!!");
		return true;
	}

	private boolean loadCopiedTeamProject(String teamCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, TeamException{
		
		Team teamInfo= TeamManager.getINSTANCE().searchTeamCode(teamCode);
		List<String>  coopers = teamInfo.getCooperatorList();
		coopers.add(teamInfo.getManager());
		if(!teamInfo.getCooperatorList().isEmpty()){
		for(String mem : teamInfo.getCooperatorList()){
			CopiedProjectList cpList = ProjectManager.getINSTANCE().searchOnlyTeamCpProjectByOwnerCode(teamCode, mem);
			if(!cpList.getList().isEmpty()){
				this.makeMapForMember(cpList, teamCode, mem);
			}
		}	
		}
		
		return true;
	}
	
	private boolean loadTeamMemProjects(String teamCode, String memberCode) throws DAOException, ParseException, FolderException, ProjectException, IOException{
		
		System.out.println("laodTeamMEmProjects  :  teamcode  ->"+teamCode+"    memberCode  ->"+memberCode);
		Map<String,FileNode> teamMemProMap = new HashMap<String, FileNode>();
		
		CopiedProjectList cpList = ProjectManager.getINSTANCE().searchOnlyTeamCpProjectByOwnerCode(teamCode, memberCode);
		if(!cpList.getList().isEmpty()){
			this.makeMapForMember(cpList, teamCode, memberCode);
		}
		System.out.println("laodTeamMEmProjects  :  teamcode  ->"+teamCode+"    memberCode  ->"+memberCode+"   End!\n");
		return false;
	}
	
	private boolean loadTeamMemProjectByOC(String teamCode, String memberCode, String oriProjectCode) throws DAOException, ParseException, FolderException, ProjectException, IOException{
		System.out.println("loadTeamMemProjectByOC  :  teamcode  ->"+teamCode+"    memberCode  ->"+memberCode+"oriProCode   ::  "+oriProjectCode);
		CopiedProject cp= ProjectManager.getINSTANCE().searchOnlyCpProjectByOriProCode(oriProjectCode, memberCode);
		makeCopiedMap(cp, teamCode, memberCode);
		System.out.println("loadTeamMemProjectByOC  :  teamcode  ->"+teamCode+"    memberCode  ->"+memberCode+"oriProCode   ::  "+oriProjectCode+"  END.\n");
		return true;
	}
	
	private boolean loadTeamMemProjectByCC(String teamCode, String memberCode, String copiedProjectCode) throws DAOException, ParseException, FolderException, ProjectException, IOException{
		
		CopiedProject cp= ProjectManager.getINSTANCE().searchOnlyCpProjectByProjectCode(copiedProjectCode);
		makeCopiedMap(cp, teamCode, memberCode);
		return true;
	}
	
	private boolean loadSharedTeamProjectCode(String teamCode,String projectCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion{
		
		if(!this.teamFileMap.containsKey(teamCode)){
			if(loadSharedTeamProject(teamCode)){
				if(this.teamFileMap.get(teamCode).containsKey(projectCode))return true;
			}
		}
		
		OriginProject pro=ProjectManager.getINSTANCE().searchOnlyOriginProjectByProjectCode(projectCode);
		if(pro==null)System.out.println("22222222222222222222222222222222    originPRoject is null.....................********");
		this.makeOriginMap(pro, teamCode);
		return true;
	}
	
	public List<String> searchTeamProjectList(String teamCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion{
		
		System.out.println("TEamFileNodeManager : searchTeamProjectList line 365");
		if(!this.teamFileMap.containsKey(teamCode)){
			System.out.println("no TEam Shared Project info go DB");
			this.loadSharedTeamProject(teamCode);
			if(!this.teamFileMap.containsKey(teamCode))return new ArrayList<String>();
		}
		List<String> res  = new ArrayList<String>();
		for(String code : this.teamFileMap.get(teamCode).keySet()){
			res.add(code);
		}
		return res;
	}
	
	public String searchTeamCooperatorProjectCode(String teamCode, String cooperatorCode, String originProjectCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, TeamException, SftpException, JSchException, CommitExcetion{
		if(!this.teamFileMap.containsKey(teamCode)) this.loadSharedTeamProject(teamCode);
		if(!this.teamFileMap.get(teamCode).containsKey(originProjectCode)) this.loadSharedTeamProjectCode(teamCode, originProjectCode);
		if(!this.teamMemberFileMap.containsKey(teamCode)) this.loadCopiedTeamProject(teamCode);
		if(!this.teamMemberFileMap.get(teamCode).containsKey(cooperatorCode))this.loadTeamMemProjects(teamCode, cooperatorCode);
		
		Map<String,FileNode> memberProMap= this.teamMemberFileMap.get(teamCode).get(cooperatorCode);
		for(FileNode cpPro : memberProMap.values()){
			CopiedProject cp = ProjectManager.getINSTANCE().searchOnlyCpProjectByProjectPath(cpPro.getPath());
			if(cp!=null){
				if(cp.getOriginCode().equals(originProjectCode))return cp.getCode();
			}
		}
		return null;
	}
	
	public List<String> searchTeamProjectPathList(String teamCode,String memberCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, TeamException{
		
		if(!this.teamMemberFileMap.containsKey(teamCode))this.loadCopiedTeamProject(teamCode);
		if(!this.teamMemberFileMap.get(teamCode).containsKey(memberCode)){
			Team teamInfo = TeamManager.getINSTANCE().searchTeamCode(teamCode);
			int res=0;
			for(String member : teamInfo.getCooperatorList()){
				if(member.equals(memberCode))res=1;break;
			}
			if(teamInfo.getManager().equals(memberCode))res=1;
			if(res==1){
				this.loadTeamMemProjects(teamCode, memberCode);
			}else return new ArrayList<String>();
		}
		
		List<String> pathList = new ArrayList<String>();
		for(FileNode fNode : this.teamMemberFileMap.get(teamCode).get(memberCode).values()){
			pathList.add(fNode.getPath());
		}
		return pathList;
	}
	
	private List<String> searchProjectFiles(FileNode node,List<String> list){
		
		List<String> folderList = node.getChildFolderList();
		List<String> fileList = node.getChildFileList();
		
		if(!folderList.isEmpty()){
			for(String folderCode : folderList){
				this.searchProjectFiles(node.getChild(folderCode), list);
			}
		}
		if(!fileList.isEmpty()){
			list.addAll(fileList);
		}
		return list;
	}
	
	private List<String> searchProjectFolders(FileNode node,List<String> list){
		System.out.println("curent Node : "+node.getPath()+" code : "+node.getCode());
		if(!node.getChildFolderList().isEmpty()){
			for(String folderCode : node.getChildFolderList()){
				list.addAll(this.searchProjectFolders(node.getChild(folderCode), new ArrayList<String>()));
			}
			list.addAll(node.getChildFolderList());
		}
		
		return list;
	}
	
	public Map<String,Map<String,List<String>>> searchTeamSharedProjectChilds(String teamCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion{
		
		if(!this.teamFileMap.containsKey(teamCode)){
			this.loadSharedTeamProject(teamCode);
		}
		Map<String,Map<String,List<String>>> resMap = new HashMap<String, Map<String,List<String>>>();
		
		Map<String,FileNode> projectMap= this.teamFileMap.get(teamCode);
		if(projectMap==null)return new HashMap<String, Map<String,List<String>>>();
		if(projectMap.isEmpty())return resMap;
		
		for(String projectCode : projectMap.keySet()){
			Map<String,List<String>> proVal = new HashMap<String, List<String>>();
			proVal.put("folder", this.searchProjectFolders(projectMap.get(projectCode), new ArrayList<String>()));
			proVal.put("file", this.searchProjectFiles(projectMap.get(projectCode), new ArrayList<String>()));
			resMap.put(projectCode, proVal);
		}
		return resMap;
	}
	
	public Map<String,List<String>> searchTeamSharedProjectChilds(String teamCode,String oriProjectCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion{
		
		if(!this.teamFileMap.containsKey(teamCode)){
			this.loadSharedTeamProject(teamCode);
		}
		if(!this.teamFileMap.get(teamCode).containsKey(oriProjectCode)){
			this.loadSharedTeamProjectCode(teamCode, oriProjectCode);
		}
		FileNode project = this.teamFileMap.get(teamCode).get(oriProjectCode);
		
		Map<String,List<String>> proVal = new HashMap<String, List<String>>();
		proVal.put("folder", this.searchProjectFolders(project, new ArrayList<String>()));
		proVal.put("file", this.searchProjectFiles(project, new ArrayList<String>()));
		
		return proVal;
	}
	

	public Map<String,Map<String,List<String>>> searchTeamCopiedProjectChilds(String teamCode,String memberCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, TeamException, SftpException, JSchException, CommitExcetion{
		
		if(!this.teamFileMap.containsKey(teamCode)){
			this.loadSharedTeamProject(teamCode);
		}
		if(!this.teamMemberFileMap.containsKey(teamCode))this.loadCopiedTeamProject(teamCode);
		if(!this.teamMemberFileMap.get(teamCode).containsKey(memberCode))this.loadTeamMemProjects(teamCode, memberCode);
		
		Map<String,Map<String,List<String>>> resMap = new HashMap<String, Map<String,List<String>>>();
		
		for(String cpProjectCode : this.teamMemberFileMap.get(teamCode).get(memberCode).keySet()){
			FileNode projectMap = this.teamMemberFileMap.get(teamCode).get(memberCode).get(cpProjectCode);
			Map<String,List<String>> proVal = new HashMap<String, List<String>>();
			proVal.put("folder", this.searchProjectFolders(projectMap, new ArrayList<String>()));
			proVal.put("file", this.searchProjectFiles(projectMap, new ArrayList<String>()));
			resMap.put(cpProjectCode, proVal);
		}
		
		return resMap;
	}

	public Map<String,List<String>> searchTeamCopiedProjectChilds(String teamCode,String memberCode,String cpProjectCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, TeamException, SftpException, JSchException, CommitExcetion{
		
		if(!this.teamFileMap.containsKey(teamCode)){
			this.loadSharedTeamProject(teamCode);
		}
		if(!this.teamMemberFileMap.containsKey(teamCode))this.loadCopiedTeamProject(teamCode);
		if(!this.teamMemberFileMap.get(teamCode).containsKey(memberCode))this.loadTeamMemProjects(teamCode, memberCode);
		
		FileNode projectMap = this.teamMemberFileMap.get(teamCode).get(memberCode).get(cpProjectCode);
		Map<String,List<String>> proVal = new HashMap<String, List<String>>();
		proVal.put("folder", this.searchProjectFolders(projectMap, new ArrayList<String>()));
		proVal.put("file", this.searchProjectFiles(projectMap, new ArrayList<String>()));
		
		
		List<String> folderList = proVal.get("folder");
		if(!folderList.isEmpty()){
			System.out.print(" folderCodes  :::::   ");
			for(String code : folderList)System.out.print(code+"  _  ");
		}
		List<String> fileList = proVal.get("file");
		if(!fileList.isEmpty()){
			System.out.print(" fileCodes  :::::   ");
			for(String code : fileList)System.out.print(code+"  _  ");
		}
		System.out.println();
		return proVal;
	}

	public CopiedProject searchTeamCopiedProject(String teamCode,String memberCode,String cpProjectCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, TeamException, SftpException, JSchException, CommitExcetion{
		
		if(!this.teamFileMap.containsKey(teamCode)){
			this.loadSharedTeamProject(teamCode);
		}
		if(!this.teamMemberFileMap.containsKey(teamCode))this.loadCopiedTeamProject(teamCode);
		if(!this.teamMemberFileMap.get(teamCode).containsKey(memberCode))this.loadTeamMemProjects(teamCode, memberCode);
		
		String path = this.teamMemberFileMap.get(teamCode).get(memberCode).get(cpProjectCode).getPath();
		CopiedProject res = ProjectManager.getINSTANCE().searchOnlyCpProjectByProjectPath(path);
		return res;
	}

	public OriginProject searchTeamOriProject(String teamCode,String memberCode,String cpProjectCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, TeamException, SftpException, JSchException, CommitExcetion, MemberException{
		
		System.out.println("\n          @@ TeamFileNodeManager   searchTeamOriProject  line   569 ");
		if(!this.teamFileMap.containsKey(teamCode)){
			this.loadSharedTeamProject(teamCode);
		}
		if(!this.teamMemberFileMap.containsKey(teamCode))this.loadCopiedTeamProject(teamCode);
		if(!this.teamMemberFileMap.get(teamCode).containsKey(memberCode))this.loadTeamMemProjects(teamCode, memberCode);
		
		FileNode proNode = this.teamMemberFileMap.get(teamCode).get(memberCode).get(cpProjectCode);
		if(proNode==null)return null;
		
		String memberNickName = MemberManager.getINSTANCE().searchMemberCode(memberCode).getNickName();
		
		StringTokenizer tokens = new StringTokenizer(proNode.getPath(), "\\"+memberNickName);
		String oriPath = tokens.nextToken()+tokens.nextToken();
		System.out.println("oriPath : "+oriPath);
		
		return ProjectManager.getINSTANCE().searchOnlyOriginProjectByProjectPath(oriPath);
	}
	public Map<String,List<String>> searchSharedProjectChilds(String teamCode, String projectCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion{
		
		if(!this.teamFileMap.containsKey(teamCode)){
			this.loadSharedTeamProject(teamCode);
		}
		Map<String,List<String>> resMap = new HashMap<String, List<String>>();
		if(!this.teamFileMap.get(teamCode).containsKey(projectCode))return resMap;
		
		resMap.put("folder", this.searchProjectFolders(this.teamFileMap.get(teamCode).get(projectCode), new ArrayList<String>()));
		resMap.put("file", this.searchProjectFiles(this.teamFileMap.get(teamCode).get(projectCode), new ArrayList<String>()));
		return resMap;
	}
	
	public Map<String,FileNode> searchTeamProject(String teamCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion{
		
		if(!this.teamFileMap.containsKey(teamCode)){
			this.loadSharedTeamProject(teamCode);
		}
		
		return this.teamFileMap.get(teamCode);
	}
	
	public List<SimpleMemberForm> searchTeamProjectCodes(String teamCode, String memberCode, String projectCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion, TeamException, MemberException{
		List<SimpleMemberForm> resList = new ArrayList<SimpleMemberForm>();
		
		if(!this.teamFileMap.containsKey(teamCode)){
			this.loadSharedTeamProject(teamCode);
		}
		if(!this.teamMemberFileMap.containsKey(teamCode))this.loadCopiedTeamProject(teamCode);
		if(!this.teamMemberFileMap.get(teamCode).containsKey(memberCode))this.loadTeamMemProjects(teamCode, memberCode);
		
		Team team = TeamManager.getINSTANCE().searchTeamCode(teamCode);
		List<String> coopers = team.getCooperatorList();
		coopers.add(team.getManager());
		String teamName = team.getName();
		String t1,t2;
		if(teamCode.equals(memberCode)){
			FileNode oriFIleNode = this.teamFileMap.get(teamCode).get(projectCode);
			StringTokenizer tokens = new StringTokenizer(oriFIleNode.getPath(), teamName);
			t1 = tokens.nextToken();
			t2 = tokens.nextToken();
			
			for(String cooperCode : coopers){
				String nick = MemberManager.getINSTANCE().searchMemberCode(cooperCode).getNickName();
				String cpPath = t1+teamName+"\\"+nick+t2;
				String cpCode = ProjectManager.getINSTANCE().searchOnlyCpProjectByProjectPath(cpPath).getCode();
				resList.add(new SimpleMemberForm(cooperCode, nick, cpCode));
			}
			resList.add(new SimpleMemberForm(teamCode, teamName, projectCode));
		}else{
			String memberName = MemberManager.getINSTANCE().searchMemberCode(memberCode).getNickName();
			FileNode oriFIleNode = this.teamMemberFileMap.get(teamCode).get(memberCode).get(projectCode);
			StringTokenizer tokens = new StringTokenizer(oriFIleNode.getPath(), memberName);
			t1 = tokens.nextToken();
			t2 = tokens.nextToken();
			
			for(String cooperCode : coopers){
				String nick = MemberManager.getINSTANCE().searchMemberCode(cooperCode).getNickName();
				String cpPath = t1+nick+t2;
				String cpCode = ProjectManager.getINSTANCE().searchOnlyCpProjectByProjectPath(cpPath).getCode();
				resList.add(new SimpleMemberForm(cooperCode, nick, cpCode));
			}
			String cpCode = ProjectManager.getINSTANCE().searchOnlyOriginProjectByProjectPath(t1+t2).getCode();
			resList.add(new SimpleMemberForm(teamCode, teamName, cpCode));
		}
		
		return resList;
	}

	public FileNode searchTeamProject(String teamCode,String projectCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion{
		
		if(!this.teamFileMap.containsKey(teamCode)){
			this.loadSharedTeamProject(teamCode);
		}
		if(!this.teamFileMap.get(teamCode).containsKey(projectCode)){this.loadSharedTeamProjectCode(teamCode, projectCode);}
		
		return this.teamFileMap.get(teamCode).get(projectCode);
	}
	public Map<String,CopiedProject> searchTeamCopiedProject(String teamCode,String oriProCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion, MemberException{
		
		if(!this.teamFileMap.containsKey(teamCode)){this.loadSharedTeamProject(teamCode);}
		if(!this.teamFileMap.get(teamCode).containsKey(oriProCode)){this.loadSharedTeamProjectCode(teamCode, oriProCode);}
		
		Map<String,CopiedProject> resMap = new HashMap<String, CopiedProject>();
		
		String oriProPath = this.teamFileMap.get(teamCode).get(oriProCode).getPath();
		OriginProject oriPro = (OriginProject)ProjectManager.getINSTANCE().searchProjectByProjectPath(oriProPath);
		String proName = oriPro.getName();
		List<String> cooperList = oriPro.getSharedMemberList();
		
		if(cooperList.isEmpty())return resMap;
		
		for(String cooper : cooperList){
			String cooperName = MemberManager.getINSTANCE().searchMemberCode(cooper).getNickName();
			String cooperPath = oriProPath.replaceFirst(proName, cooperName+"\\"+proName);
			System.out.println("cooperPath  : "+cooperPath);
			CopiedProject cp = (CopiedProject)ProjectManager.getINSTANCE().searchOnlyCpProjectByProjectPath(cooperPath);
			
			if(cp!=null)resMap.put(cooper, cp);
		}
		
		
		return resMap;
	}
	
	public List<String> searchProjectFiles(String teamCode,String projectCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion{
		
		if(!this.teamFileMap.containsKey(teamCode)){
			this.loadSharedTeamProject(teamCode);
		}
		
		return this.searchProjectFiles(this.teamFileMap.get(teamCode).get(projectCode), new ArrayList<String>());
	}
	
	public Map<String,List<FolderForm>> searchTeamFolderSiblings(String teamCode, String branchCode,String projectCode, List<String> pFolders) throws IOException, DAOException, ParseException, FolderException, FileException, ProjectException, SftpException, JSchException, TeamException, CommitExcetion{
		
		int size = pFolders.size();
		String f = pFolders.remove(size-1);
		System.out.println("last  :::  "+f);
		return this.searchFolders(teamCode, branchCode, projectCode, pFolders);
	}
	public Map<String,List<FolderForm>> searchFolders(String teamCode, String branchCode,String projectCode, List<String> pFolders) throws IOException, DAOException, ParseException, FolderException, FileException, ProjectException, SftpException, JSchException, TeamException, CommitExcetion{
		
		System.out.println("searchFolders!!  teamCode :: "+teamCode+"  "+branchCode);
		Map<String,List<FolderForm>> resMap = new HashMap<String, List<FolderForm>>();
		List<FolderForm> fileList = new ArrayList<FolderForm>();
		List<FolderForm> folderList = new ArrayList<FolderForm>();
		FileNode projectNode;
		if(teamCode.equals(branchCode)){
			System.out.println("TEam Shared..........Project");
			if(!this.teamFileMap.containsKey(teamCode))this.loadSharedTeamProject(teamCode);
			if(!this.teamFileMap.get(teamCode).containsKey(projectCode))this.loadSharedTeamProjectCode(teamCode, projectCode);
			projectNode = this.teamFileMap.get(teamCode).get(projectCode);
		}else{
			System.out.println("TEam member's..........Project  "+branchCode);
			if(!this.teamMemberFileMap.containsKey(teamCode))this.loadCopiedTeamProject(teamCode);
			if(!this.teamMemberFileMap.get(teamCode).containsKey(branchCode))this.loadTeamMemProjects(teamCode, branchCode);
			if(!this.teamMemberFileMap.get(teamCode).get(branchCode).containsKey(projectCode))this.loadTeamMemProjectByOC(teamCode, branchCode, projectCode);
			projectNode= this.teamMemberFileMap.get(teamCode).get(branchCode).get(projectCode);
		}
		
		if(!pFolders.isEmpty()){
			int cnt=pFolders.size();
			for(int i=1;i<cnt;i++){
				System.out.println("i  :  "+i+"   code : "+pFolders.get(i));
				projectNode=projectNode.getChild(pFolders.get(i));
				System.out.println("00000000000000000000  :: "+projectNode.getPath());
			}
			if(!projectNode.getChilds().isEmpty()){
				TreeMap<String,FileNode> childs= projectNode.getChilds();
				for(FileNode node : childs.values()){
					System.out.println("**  searchFolders  : "+node.getPath());
					if(node.isFile()){
						OriginFile pro = (OriginFile)FileManager.getINSTANCE().searchFilePath(node.getPath());
						//OriginProject pro = ProjectManager.getINSTANCE().searchProjectPath();
						
						fileList.add(new FolderForm(node.getType(), node.getCode(), node.getName(), pro.getMakeDate(), pro.getAlterDate(),null));
					}
					else{
					
						OriginFolder pro = (OriginFolder)FolderManager.getINSTANCE().searchFolderByFolderPath(node.getPath());
						//OriginProject pro = ProjectManager.getINSTANCE().searchProjectPath();
						
						String des = FolderManager.getINSTANCE().justSearchFolderPath(node.getPath()).getDescription();
						folderList.add(new FolderForm(node.getType(), node.getCode(), node.getName(), pro.getMakeDate(), pro.getAlterDate(),des));
					}
				}
				resMap.put("fileList", fileList);
				resMap.put("folderList", folderList);
			}
		}else System.out.println("empty...........");
		if(resMap.isEmpty())System.out.println("resList    is     Nulllll");
		else System.out.println("resList is not null");
		return resMap;
	}	
	
	public List<FolderPathForm> searchOnlyTeamSharedFolders(String teamCode, String branchCode,String projectCode, List<String> pFolders) throws IOException, DAOException, ParseException, FolderException, FileException, ProjectException, SftpException, JSchException, TeamException, CommitExcetion, MemberException{
		
		System.out.println("searchFolders!!  teamCode :: "+teamCode+"  "+branchCode);
		List<FolderPathForm> resList = new ArrayList<FolderPathForm>();
		
		OriginProject oriProject = this.searchTeamOriProject(teamCode, branchCode, projectCode);
		System.out.println("owner : "+oriProject.getOwner()+"  projectCode : "+oriProject.getCode());
		
		FileNode oriProNode = this.teamFileMap.get(teamCode).get(oriProject.getCode());
		if(oriProNode==null)return resList;
		if(pFolders.size()!=1){
			for(int i=1;i<pFolders.size();i++){
				String code = pFolders.get(i);
				oriProNode = oriProNode.getChild(code);
			}
		}
		
		FileNode chFolder;
		if(oriProNode!=null){
			for(String chFolderCode : oriProNode.getChildFolderList()){
				chFolder = oriProNode.getChild(chFolderCode);
				System.out.println("**  searchFolders  : "+chFolder.getPath());
				resList.add(new FolderPathForm(chFolder.getType(), chFolder.getCode(), chFolder.getName(), chFolder.getPath()));
			}
		}else System.out.println("empty...........");
		if(resList.isEmpty())System.out.println("resList    is     Nulllll");
		else System.out.println("resList is not null");
		return resList;
	}
	public List<FolderForm> searchParentFolders(String teamCode, String branchCode, String projectCode,List<String> folderCodes) throws DAOException, ParseException, FolderException, ProjectException, IOException, TeamException, SftpException, JSchException, CommitExcetion{
		System.out.println("TeamFileNodeManger   -->>>>>  searchParentFolders ## "+teamCode+" "+branchCode+" "+projectCode);
		List<FolderForm> resList = new ArrayList<FolderForm>();
		FileNode projectNode;
		if(branchCode==null){
			if(!this.teamFileMap.containsKey(teamCode))this.loadSharedTeamProject(teamCode);
			if(!this.teamFileMap.get(teamCode).containsKey(projectCode))this.loadSharedTeamProjectCode(teamCode, projectCode);
			projectNode = this.teamFileMap.get(teamCode).get(projectCode);
		}else{
			if(!this.teamMemberFileMap.containsKey(teamCode))this.loadCopiedTeamProject(teamCode);
			if(!this.teamMemberFileMap.get(teamCode).containsKey(branchCode))this.loadTeamMemProjects(teamCode, branchCode);
			if(!this.teamMemberFileMap.get(teamCode).get(branchCode).containsKey(projectCode))this.loadTeamMemProjectByOC(teamCode, branchCode, projectCode);
			projectNode= this.teamMemberFileMap.get(teamCode).get(branchCode).get(projectCode);
		}
		System.out.println(projectNode.getPath());
		resList.add(new FolderForm(projectNode.getType(), projectNode.getCode(), projectNode.getName()));

		int cnt = folderCodes.size();
		for(int i=1;i<cnt;i++){
			projectNode=projectNode.getChild(folderCodes.get(i));
			if(projectNode!=null){
				resList.add(new FolderForm(projectNode.getType(), projectNode.getCode(), projectNode.getName()));
			}
		}
		return resList;
	}

	public String createTeamFile(String teamCode,String teamName,String branchCode, String projectCode, List<String> folderCodes,String fileName , String content, String commitTitle, String commitContent) 
			throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException, FolderException, CommitExcetion, ProjectException, TeamException, MemberException{
		// TODO Auto-generated method stub
		
		if(branchCode.equals(teamCode)){return createSharedTeamFile(teamCode, teamName, branchCode, projectCode, folderCodes, fileName, content, commitTitle, commitContent);}
		//else if(branchCode.equals(manager)){return createSharedTeamFile(teamCode, teamName, branchCode, projectCode, folderCodes, fileName, content, commitTitle, commitContent);}
		else{
			return createMemOriginFile(teamCode, teamName, branchCode, projectCode, folderCodes, fileName, content, commitTitle, commitContent);
		}
	}
	
	private String createSharedTeamFile(String teamCode,String teamName, String branchCode, String projectCode, List<String> folderCodes,String fileName , String content, String commitTitle, String commitContent) 
			throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException, FolderException, CommitExcetion, ProjectException, TeamException, MemberException{
		
		System.out.println("TeamFileNodeManager    createSharedTeamFile...");
		FileNode project;
		boolean isBTEqual=false;
		if(!this.teamFileMap.containsKey(teamCode)){this.loadSharedTeamProject(teamCode);}
		if(!this.teamMemberFileMap.containsKey(teamCode))this.loadCopiedTeamProject(teamCode);
		if(branchCode.equals(teamCode)){
			System.out.println("branch teamCode is same..");
			isBTEqual=true;
			if(!this.teamFileMap.get(teamCode).containsKey(projectCode)){this.loadSharedTeamProjectCode(teamCode, projectCode);}
			project = this.teamFileMap.get(teamCode).get(projectCode);
		}else{
			System.out.println("branch teamCode is diff..");
			Team teamInfo = TeamManager.getINSTANCE().searchTeamCode(teamCode);
			String path = ProjectManager.getINSTANCE().justSearchProjectCode(projectCode).getPath();
			CopiedProject cpProject = ProjectManager.getINSTANCE().searchOnlyCpProjectByProjectPath(path);
			String oriProCode = cpProject.getOriginCode();
			System.out.println("oriProCode    ::  forLoad!!!!!    "+oriProCode+"   copiedOwner   ::  "+cpProject.getOwner()+"  branch  ::: "+branchCode);
			for(String memCode : teamInfo.getCooperatorList()){
				if(!this.teamMemberFileMap.get(teamCode).containsKey(memCode))this.loadTeamMemProjects(teamCode, memCode);
				if(memCode.equals(cpProject.getOwner())){
					if(!this.teamMemberFileMap.get(teamCode).get(memCode).containsKey(projectCode))this.loadTeamMemProjectByOC(teamCode, memCode, oriProCode);
				}
			}
			project = this.teamMemberFileMap.get(teamCode).get(branchCode).get(projectCode);
		}
		int cnt = folderCodes.size();
		System.out.println(project.getChildFolderList().get(0)+"  //  "+folderCodes.get(1)+"  //  cnt :"+cnt);
		for(int i=1;i<cnt;i++){
			project = project.getChild(folderCodes.get(i));
		}
		System.out.println("parentInfo  :::::  "+project.getCode()+"     //    "+project.getPath());
		
		TreeMap<String, FileNode> children=project.getChilds();
		for(FileNode node : children.values()){
			if(node.getName().equals(fileName))throw new FileException("FileNmae is alreay used!");
		}
		
		System.out.println("createFileCode");
		Map<String,CopiedFile> fileMap = FileManager.getINSTANCE().createCommitTeamFile(teamCode,teamName,fileName, project.getPath(), content);
		if(!fileMap.isEmpty()){
			
			//insert commit
			for(String memberCode : fileMap.keySet()){
				CopiedFile cpFile = fileMap.get(memberCode);
				
				CommitInfo commitInfo = new CommitInfo(cpFile.getCode(), "create", commitContent, cpFile.getMakeDate(), memberCode, true, commitTitle);
				String proCode;
				if(memberCode.equals(teamCode)){
					StringTokenizer pathTokens = new StringTokenizer(cpFile.getPath(), "\\");
					String fpath=pathTokens.nextElement()+"\\"+pathTokens.nextElement()+"\\"+pathTokens.nextElement()+"\\"+pathTokens.nextElement();
					proCode = ProjectManager.getINSTANCE().searchASelectOriginProject(fpath).getCode();
				}else {
					StringTokenizer pathTokens = new StringTokenizer(cpFile.getPath(), "\\");
					String fpath=pathTokens.nextElement()+"\\"+pathTokens.nextElement()+"\\"+pathTokens.nextElement()+"\\"+pathTokens.nextElement()+"\\"+pathTokens.nextElement();
					proCode = ProjectManager.getINSTANCE().searchASelectCopiedProject(fpath).getCode();
				}
				CommitInfoManager.getINSTANCE().putTeamCommitInfo(teamCode,memberCode, proCode, commitInfo);
			}
			
			Map<String,String>  resMap2= this.insertCopiedTeamFile(fileMap, teamCode);
			if(!resMap2.isEmpty()){
				String last;
				if(isBTEqual){
					project = this.teamFileMap.get(teamCode).get(projectCode);
					last = resMap2.get(teamCode);
				}else{
					project = this.teamMemberFileMap.get(teamCode).get(branchCode).get(projectCode);
					last = resMap2.get(branchCode);
				}
				for(int i=1;i<cnt;i++){
					project = project.getChild(folderCodes.get(i));
				}
				System.out.println(isBTEqual+"  "+last+"  parent : "+project.getPath());
				project = project.getChild(last);
				if(project==null)System.out.println("project is null.....");
				System.out.println("  path : "+project.getPath());
				return project.getCode();
			}
		}
		
		return null;
	}
/*
	private String mergeCommitToSharedFile(String teamCode, String commiterCode, String cpProjectCode, String commitCode) 
			throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException, FolderException, CommitExcetion, ProjectException, TeamException{
		
		FileNode project;
		boolean isBTEqual=false;
		if(!this.teamFileMap.containsKey(teamCode)){this.loadSharedTeamProject(teamCode);}
		if(!this.teamMemberFileMap.containsKey(teamCode))this.loadCopiedTeamProject(teamCode);
		if(branchCode.equals(teamCode)){
			System.out.println("branch teamCode is same..");
			isBTEqual=true;
			if(!this.teamFileMap.get(teamCode).containsKey(projectCode)){this.loadSharedTeamProjectCode(teamCode, projectCode);}
			project = this.teamFileMap.get(teamCode).get(projectCode);
		}else{
			System.out.println("branch teamCode is diff..");
			Team teamInfo = TeamManager.getINSTANCE().searchTeamCode(teamCode);
			String path = ProjectManager.getINSTANCE().searchProjectByCode(projectCode).getPath();
			CopiedProject cpProject = ProjectManager.getINSTANCE().searchCopiedProject(path);
			String oriProCode = cpProject.getOriginCode();
			System.out.println("oriProCode    ::  forLoad!!!!!    "+oriProCode+"   copiedOwner   ::  "+cpProject.getOwner()+"  branch  ::: "+branchCode);
			for(String memCode : teamInfo.getCooperatorList()){
				if(!this.teamMemberFileMap.get(teamCode).containsKey(memCode))this.loadTeamMemProjects(teamCode, memCode);
				if(memCode.equals(cpProject.getOwner())){
					if(!this.teamMemberFileMap.get(teamCode).get(memCode).containsKey(projectCode))this.loadTeamMemProjectByOC(teamCode, memCode, oriProCode);
				}
			}
			project = this.teamMemberFileMap.get(teamCode).get(branchCode).get(projectCode);
		}
		int cnt = folderCodes.size();
		System.out.println(project.getChildFolderList().get(0)+"  //  "+folderCodes.get(1)+"  //  cnt :"+cnt);
		for(int i=1;i<cnt;i++){
			project = project.getChild(folderCodes.get(i));
		}
		System.out.println("parentInfo  :::::  "+project.getCode()+"     //    "+project.getPath());
		
		TreeMap<String, FileNode> children=project.getChilds();
		for(FileNode node : children.values()){
			if(node.getName().equals(fileName))throw new FileException("FileNmae is alreay used!");
		}
		
		System.out.println("createFileCode");
		Map<String,CopiedFile> fileMap = FileManager.getINSTANCE().createCommitTeamFile(teamCode,teamName,fileName, project.getPath(), content, commitTitle, commitContent);
		if(!fileMap.isEmpty()){
			Map<String,String>  resMap2= this.insertCopiedTeamFile(fileMap, teamCode);
			if(!resMap2.isEmpty()){
				String last;
				if(isBTEqual){
					project = this.teamFileMap.get(teamCode).get(projectCode);
					last = resMap2.get(teamCode);
				}else{
					project = this.teamMemberFileMap.get(teamCode).get(branchCode).get(projectCode);
					last = resMap2.get(branchCode);
				}
				for(int i=1;i<cnt;i++){
					project = project.getChild(folderCodes.get(i));
				}
				System.out.println(isBTEqual+"  "+last+"  parent : "+project.getPath());
				project = project.getChild(last);
				if(project==null)System.out.println("project is null.....");
				System.out.println("  path : "+project.getPath());
				return project.getCode();
			}
		}
		
		return null;
	}*/
	
	private String createMemOriginFile(String teamCode,String teamName,String branchCode, String projectCode, List<String> folderCodes,String fileName , String content, String commitTitle, String commitContent) 
			throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException, FolderException, CommitExcetion, ProjectException, TeamException, MemberException{
		
		if(!this.teamMemberFileMap.containsKey(teamCode))this.loadCopiedTeamProject(teamCode);
		if(!this.teamMemberFileMap.get(teamCode).containsKey(branchCode))this.loadTeamMemProjects(teamCode, branchCode);
		if(!this.teamMemberFileMap.get(teamCode).get(branchCode).containsKey(projectCode))this.loadTeamMemProjectByCC(teamCode, branchCode, projectCode);
		
		FileNode project = this.teamMemberFileMap.get(teamCode).get(branchCode).get(projectCode);
		
		int cnt = folderCodes.size();
		for(int i=1;i<cnt;i++){
			project=project.getChild(folderCodes.get(i));
		}
		
		TreeMap<String, FileNode> children=project.getChilds();
		for(FileNode node : children.values()){
			if(node.getName().equals(fileName))throw new FileException("FileNmae is alreay used!");
		}
		
		System.out.println("createFileCode");
		OriginFile oriFile= FileManager.getINSTANCE().createCommitFileCode(branchCode, fileName, project.getCode(), content);
		
		project.createChildFile(new MyOriginFile(oriFile.getCode(), fileName, oriFile.getPath(), "file"));
		CommitInfoManager.getINSTANCE().putTeamCommitInfo(teamCode, branchCode, projectCode, new CommitInfo(oriFile.getCode(), "create", commitContent, oriFile.getMakeDate(), branchCode, false, null, commitTitle));
		return oriFile.getCode();
	}

	public String searchFileContent(String teamCode, String branchCode, String projectCode, List<String> folderCodes, String fileCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, TeamException, SftpException, JSchException, CommitExcetion{
		
		System.out.println("TeamFileNodeManger   searchFileContent   line   975");
		System.out.println("branchCode : "+branchCode);
		if(branchCode.equals(teamCode)){
			System.out.println("get  origin Team File Content....");
			return this.searchSharedFileContent(teamCode, branchCode, projectCode, folderCodes, fileCode);
		}
		else return this.searchCooperFileContent(teamCode, branchCode, projectCode, folderCodes, fileCode);
	}
	
	private String searchSharedFileContent(String teamCode, String branchCode, String projectCode, List<String> folderCodes, String fileCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, SftpException, JSchException, CommitExcetion{
		
		if(!this.teamFileMap.containsKey(teamCode)){System.out.println(this.loadSharedTeamProject(teamCode));}
		if(!this.teamFileMap.get(teamCode).containsKey(projectCode))this.loadSharedTeamProjectCode(teamCode, projectCode);
		
		FileNode project = this.teamFileMap.get(teamCode).get(projectCode);
		
		int cnt=folderCodes.size();
		for(int i=1;i<cnt;i++){
			project = project.getChild(folderCodes.get(i));
		}
		
		project = project.getChild(fileCode);
		
		return FileManager.getINSTANCE().searchOnlyOriginFilePathContent(project.getPath()).getContent();
	}
	
	private String searchCooperFileContent(String teamCode, String branchCode, String projectCode, List<String> folderCodes, String fileCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, TeamException, SftpException, JSchException{
		
		if(!this.teamMemberFileMap.containsKey(teamCode))this.loadCopiedTeamProject(teamCode);
		if(this.teamMemberFileMap.containsKey(teamCode)){
			Team teamInfo = TeamManager.getINSTANCE().searchTeamCode(teamCode);
			for(String memCode : teamInfo.getCooperatorList()){
				if(!this.teamMemberFileMap.get(teamCode).containsKey(memCode))this.loadTeamMemProjects(teamCode, memCode);
			}
		
			if(!this.teamMemberFileMap.get(teamCode).get(branchCode).containsKey(projectCode))this.loadTeamMemProjectByCC(teamCode, branchCode, projectCode);
			FileNode project = this.teamMemberFileMap.get(teamCode).get(branchCode).get(projectCode);
			
			int cnt = folderCodes.size();
			for(int i=1;i<cnt;i++){
				project = project.getChild(folderCodes.get(i));
			}
			
			project = project.getChild(fileCode);
			System.out.println("find!!!   project Code : "+project.getCode()+"     projectPath : "+project.getPath());

			return ((OriginFile)FileManager.getINSTANCE().searchFileContentPath(project.getPath())).getContent();
		}
		return null;
	}

	/*private String searchCopiedFileContent(String teamCode, String branchCode, String projectCode, List<String> folderCodes, String fileCode) throws DAOException, ParseException, FolderException, ProjectException, IOException, TeamException, SftpException, JSchException{
		
		if(!this.teamMemberFileMap.containsKey(teamCode))this.loadCopiedTeamProject(teamCode);
		Team teamInfo = TeamManager.getINSTANCE().searchTeamCode(teamCode);
		for(String memCode : teamInfo.getCooperatorList()){
			if(!this.teamMemberFileMap.get(teamCode).containsKey(memCode))this.loadTeamMemProjects(teamCode, memCode);
		}
	
		if(!this.teamMemberFileMap.get(teamCode).get(branchCode).containsKey(projectCode))this.loadTeamMemProjectByCC(teamCode, branchCode, projectCode);
		FileNode project = this.teamMemberFileMap.get(teamCode).get(branchCode).get(projectCode);
		
		int cnt = folderCodes.size();
		for(int i=1;i<cnt;i++){
			project = project.getChild(folderCodes.get(i));
		}
		
		project = project.getChild(fileCode);
		FileManager.getINSTANCE().searchASelectOriginFile(path)
		return FileManager.getINSTANCE().searchCopiedFileContent(project.getPath()).getContent();
	}*/

	public boolean modifyFileContent(String storageCode,String branchCode,String projectCode, List<String> folderCodes, String fileCode,String newContent, 
			String commitTitle, String commitContent)throws FileNotFoundException, LoginException, DAOException,SftpException, IOException, JSchException, FileException,ParseException, RemoteFileException, CommitExcetion, FolderException,ProjectException, TeamException, MemberException {
		
		// TODO Auto-generated method stub
		System.out.println("TeamFileNodeManager    modifyFileContent    line   1047 ");
		System.out.println("branchCode : "+branchCode);
		if(branchCode.equals(storageCode))return this.modifyTeamFileContent(storageCode, projectCode, folderCodes, fileCode, newContent, commitTitle, commitContent);
		else return this.modifyCopiedFileContent(storageCode, branchCode, projectCode, folderCodes, fileCode, newContent, commitTitle, commitContent);
		
	}
	
	private boolean modifyTeamFileContent(String teamCode,String projectCode, List<String> folderCodes, String fileCode,
			String newContent, String commitTitle, String commitContent)throws FileNotFoundException, LoginException, DAOException,
			SftpException, IOException, JSchException, FileException,ParseException, RemoteFileException, CommitExcetion, FolderException,
			ProjectException, TeamException, MemberException {
		
		if(!this.teamFileMap.containsKey(teamCode))this.loadSharedTeamProject(teamCode);
		
		if(!this.teamFileMap.get(teamCode).containsKey(projectCode))this.loadSharedTeamProjectCode(teamCode, projectCode);
		
		FileNode node = this.teamFileMap.get(teamCode).get(projectCode);
		int leng = folderCodes.size();
		for(int i=1;i<leng;i++){
			node=node.getChild(folderCodes.get(i));
		}
		
		node=node.getChild(fileCode);
		CommitInfo commitInfo = FileManager.getINSTANCE().modifyFileContent(teamCode, teamCode, projectCode, folderCodes.get(leng-1), fileCode,node.getPath(), newContent,commitTitle,commitContent);
		if(commitInfo==null)return false;
		return CommitInfoManager.getINSTANCE().putOnlyTeamCommitInfo(teamCode, teamCode, projectCode, commitInfo);
	}
	
	private boolean modifyCopiedFileContent(String teamCode,String branchCode,String projectCode, List<String> folderCodes, String fileCode,String newContent, 
			String commitTitle, String commitContent)throws FileNotFoundException, LoginException, DAOException,SftpException, IOException, JSchException, FileException,ParseException, RemoteFileException, CommitExcetion, FolderException,ProjectException, TeamException, MemberException {
		
		if(!this.teamMemberFileMap.containsKey(teamCode))this.loadCopiedTeamProject(teamCode);
		
		if(!this.teamMemberFileMap.get(teamCode).containsKey(branchCode))this.loadTeamMemProjects(teamCode, branchCode);
		
		if(!this.teamMemberFileMap.get(teamCode).get(branchCode).containsKey(projectCode))this.loadTeamMemProjectByCC(teamCode, branchCode, projectCode);
		
		FileNode project = this.teamMemberFileMap.get(teamCode).get(branchCode).get(projectCode);
		int leng = folderCodes.size();
		for(int i=1;i<leng;i++){
			project = project.getChild(folderCodes.get(i));
		}
		project = project.getChild(fileCode);
		
		CommitInfo commitInfo = FileManager.getINSTANCE().modifyFileContent(branchCode, teamCode, projectCode, folderCodes.get(leng-1), fileCode,project.getPath(), newContent,commitTitle,commitContent);
		if(commitInfo==null)return false;
		return CommitInfoManager.getINSTANCE().putOnlyTeamCommitInfo(teamCode, branchCode, projectCode, commitInfo);
	}

	public String searchDiffFile(String storageCode, String branchCode,String projectCode,List<String> folderCodes,String fileCode,String fileContent) throws DAOException, ParseException, FolderException, IOException, FileException, ProjectException, LoginException, SftpException, JSchException, RemoteFileException, CommitExcetion, TeamException{
 		
		if(branchCode.equals(storageCode)){
			return this.searchSharedDiffFile(storageCode, projectCode, folderCodes, fileCode, fileContent);
		}else{
			return this.searchMemDiffFile(storageCode, branchCode, projectCode, folderCodes, fileCode, fileContent);
		}
 	}
	

 	public void checkBeforeMerge(){
 		
 		//check is manager going to merge commit from team member
 		
 		//check is team member going to merge commit from manager 
 	}
	
	public String checkFileDiff(String teamCode, String branchCode, String memberCode, String memProjectCode, String commiter, String commitCode,String msgCode) throws DAOException, ParseException, IOException, FolderException, FileException, ProjectException, SftpException, JSchException, RemoteFileException, MemberException, TeamException, CommitExcetion{

 		/*
 		 * return
 		 * 
 		 "needToSelectpath"
 		 * "justCopy"
 		 * "noCopy"
 		 * "justCopyContent"
 		 * 
 		 * THis is for only when team Manager merge member's file and folder
 		 * */
 		
		//check is team Manger
		Team team = TeamManager.getINSTANCE().searchTeamCode(teamCode);
		String teamManagerCode = team.getManager();
		String teamName = team.getName();
		if(!teamManagerCode.equals(memberCode))return null;
		
 		System.out.println("\n       TeamFileNodeManager   checkFileDiff() start!   line    1159");
 		//merge file!!!!
 		if(!this.teamMemberFileMap.containsKey(teamCode))this.loadCopiedTeamProject(teamCode);
		if(!this.teamMemberFileMap.get(teamCode).containsKey(branchCode))this.loadTeamMemProjects(teamCode, branchCode);
		if(!this.teamMemberFileMap.get(teamCode).get(branchCode).containsKey(memProjectCode))this.loadTeamMemProjectByCC(teamCode, branchCode, memProjectCode);
 		
		OriginProject oriProject = this.searchTeamOriProject(teamCode, memberCode, memProjectCode);
		if(oriProject==null)return null;
		String oriProCode = oriProject.getCode();
		
 		if(!this.teamFileMap.containsKey(teamCode))this.loadSharedTeamProject(teamCode);
		if(!this.teamFileMap.get(teamCode).containsKey(oriProCode))this.loadSharedTeamProjectCode(teamCode, oriProCode);
		//settign end
 		
		CommitInfo commitInfo = CommitInfoManager.getINSTANCE().searchCommitCode(commitCode);
		CommitInfo newCommit=null;		

		CommitMessage commitMsg = MessageManager.getINSTANCE().getCommitMessage(msgCode);
		String commiterNickName = MemberManager.getINSTANCE().searchMemberCode(commiter).getNickName();
		String memberNickName = MemberManager.getINSTANCE().searchMemberCode(memberCode).getNickName();
		
		System.out.println("Commit objCode : "+commitInfo.getObjectCode());
		
		Map<String,List<CommitInfo>> msgCommitMap = MessageManager.getINSTANCE().searchCommitMsgCommitList(msgCode);
		if(msgCommitMap==null)return null;
		
		//just search is there any new file or folder that team member create start
		List<CommitInfo> tmpCommitInfoList = new ArrayList<CommitInfo>();//order by latest date
		if(!msgCommitMap.isEmpty()){
			tmpCommitInfoList = msgCommitMap.get(commitInfo.getObjectCode());
			if(!tmpCommitInfoList.isEmpty()){
				for(CommitInfo cInfo : tmpCommitInfoList){
					System.out.println("CommitCode : "+cInfo.getCode()+"    kind : "+cInfo.getKind());
					if(cInfo.getKind().equals("create")){
						String code = cInfo.getObjectCode();
						if(new StringTokenizer(code, "_").nextToken().equals("folder")){
							//if folder
							//check parent
							OriginFolder folder = FolderManager.getINSTANCE().justSearchFolderCode(code);
							if(folder!=null){
								String parentFolderPath = folder.getParentFolderPath();
								System.out.println("commitParentFolderPath : "+parentFolderPath+"Commitor nickName : "+commiterNickName);
								if(parentFolderPath!=null){
									String realFolderPath = parentFolderPath.replaceFirst(teamName+"\\"+commiterNickName, teamName);
									System.out.println("ParentFolderPath : "+realFolderPath+"member nickName : "+memberNickName);
									OriginFolder isitRealFolder = FolderManager.getINSTANCE().justSearchFolderPath(realFolderPath);
									if(isitRealFolder==null)return "needToSelectpath";
									else return "justCopy";
								}
							}
						}else{
							//if file
							OriginFile file = FileManager.getINSTANCE().justSearchOriginFileCode(code);
							if(file!=null){
								String folderPath = file.getFolderPath();
								System.out.println("commitParentFolderPath : "+folderPath+"Commitor nickName : "+commiterNickName);
								if(folderPath!=null){
									String realFolderPath = folderPath.replaceFirst(teamName+"\\"+commiterNickName, teamName);
									System.out.println("ParentFolderPath : "+realFolderPath+"member nickName : "+memberNickName);
									OriginFolder isitRealFolder = FolderManager.getINSTANCE().justSearchFolderPath(realFolderPath);
									if(isitRealFolder==null)return "needToSelectpath";
									else return "justCopy";
								}
							}
						}
					}
				}
			}
		}
		//just search is there any new file or folder that team member create end
		
		
		//organize with makeDate
		List<CommitInfo> commitInfoList = new ArrayList<CommitInfo>();
		if(!tmpCommitInfoList.isEmpty()){
			//test code
			tmpCommitInfoList = CommitInfoManager.getINSTANCE().searchCommitInfoContentDetails(commitInfo.getObjectCode(), tmpCommitInfoList);
			System.out.println("00000000000000000000000000000000000000000000000000000000000000000000000000000");
			System.out.println("tmpList sieze : "+tmpCommitInfoList.size());
			for(CommitInfo co : tmpCommitInfoList){
				System.out.println("00000000000000000000000000000000000000000000000000000000000000000000000000000");
				System.out.println(co.getCode());
				System.out.println("�� ���  :   "+co.getDate());
				if(co.getOriginFileDescriptioin()!=null)System.out.println(co.getOriginFileDescriptioin());
				System.out.println("00000000000000000000000000000000000000000000000000000000000000000000000000000");
			}
			if(!tmpCommitInfoList.isEmpty()){
				int size = tmpCommitInfoList.size();
				int i=0;
				for(;i<size;i++){
					CommitInfo co = tmpCommitInfoList.get(i);
					if(co.getCode().equals(commitInfo.getCode()))break;
				}
				commitInfoList = tmpCommitInfoList.subList(i, size);
			}
		}
		
		CopiedFile copiedFile = FileManager.getINSTANCE().searchOnlyCpFileCode(commitInfo.getObjectCode());
		if(copiedFile!=null){
			OriginFile oriFile = FileManager.getINSTANCE().searchOnlyOriginFileCode(copiedFile.getOriginFileCode());
			for(CommitInfo co : commitInfoList){
				System.out.println("------------------------------------------------------------------------------");
				System.out.println(co.getCode());
				System.out.println("�� ���  :   "+co.getDate());
				if(co.getOriginFileDescriptioin()!=null)System.out.println(co.getOriginFileDescriptioin());
				System.out.println("------------------------------------------------------------------------------");
			}
			if(FileManager.getINSTANCE().checkFileDiff(oriFile.getCode(), commitInfoList))return "justCopyContent";
			else return "noCopy";
		}
		else return "noCopy";
 	}

 	public boolean mergeFileContent(String teamCode, String branchCode, String memberCode, String memProjectCode, String commiter, String commitCode,String msgCode)throws FileNotFoundException, LoginException, DAOException,
			SftpException, IOException, JSchException, FileException,ParseException, RemoteFileException, CommitExcetion, FolderException, ProjectException, TeamException, MemberException {
		// TODO Auto-generated method stub
		
 		//for just copy
 		System.out.println("\n       TeamFileNodeManager   mergeFileContent() start!   line    1274");
 		//check is team Manger
		Team team = TeamManager.getINSTANCE().searchTeamCode(teamCode);
		String teamManager = team.getManager();
		if(!teamManager.equals(memberCode))return false;
		
 		//merge file!!!!
 		if(!this.teamMemberFileMap.containsKey(teamCode))this.loadCopiedTeamProject(teamCode);
		if(!this.teamMemberFileMap.get(teamCode).containsKey(branchCode))this.loadTeamMemProjects(teamCode, branchCode);
		if(!this.teamMemberFileMap.get(teamCode).get(branchCode).containsKey(memProjectCode))this.loadTeamMemProjectByCC(teamCode, branchCode, memProjectCode);
 		
		OriginProject oriProject = this.searchTeamOriProject(teamCode, memberCode, memProjectCode);
		if(oriProject==null)return false;
		String oriProCode = oriProject.getCode();
		
 		if(!this.teamFileMap.containsKey(teamCode))this.loadSharedTeamProject(teamCode);
		if(!this.teamFileMap.get(teamCode).containsKey(oriProCode))this.loadSharedTeamProjectCode(teamCode, oriProCode);
		//settign end
		
		//get msg
		CommitInfo commitInfo = CommitInfoManager.getINSTANCE().searchCommitCode(commitCode);
		String objCode = commitInfo.getObjectCode();
		StringTokenizer tokens = new StringTokenizer(objCode, "_");
		CopiedFile cpFile = FileManager.getINSTANCE().searchOnlyCpFileCode(objCode);
		String objPath = cpFile.getPath();
		
		String newContent = CommitInfoManager.getINSTANCE().searchCommitOriginFileContent(commitCode);
		if(newContent==null)newContent = ((OriginFile)FileManager.getINSTANCE().searchFileContentPath(objPath)).getContent();
		List<CommitInfo> commitInfoList = MessageManager.getINSTANCE().searchCommitMsgCommitList(msgCode, objCode);
		List<String> commitCodeList = new ArrayList<String>();
		
		if(!commitInfoList.isEmpty()){
			int i=0,cnt = commitInfoList.size();
			for(i=0;i<cnt;i++){
				if(commitInfoList.get(i).getCode().equals(commitCode))break;
			}
			for(;i<cnt;i++){
				commitCodeList.add(commitInfoList.get(i).getCode());
			}
		}
		String filePath = FileManager.getINSTANCE().justSearchOriginFileCode(cpFile.getOriginFileCode()).getPath();
		CommitInfo resCommit = FileManager.getINSTANCE().mergeFileContent(memberCode, filePath, newContent, objCode, commiter, commitCode, commitCodeList);
		if(resCommit!=null){
			CommitInfoManager.getINSTANCE().putOnlyTeamCommitInfo(teamCode, teamCode, oriProCode, commitInfo);
			return true;
		//CommitInfoManager.getINSTANCE().putOnlyCommitInfo(memberCode, oriProjectCode, commitInfo);
		}
		
		return false;
	}
	
 	public boolean mergeNewObj(String teamCode, String branchCode, String memberCode, String memProjectCode, String commiter, String commitCode,String msgCode,List<String> folderCodes)throws FileNotFoundException, LoginException, DAOException,
			SftpException, IOException, JSchException, FileException,ParseException, RemoteFileException, CommitExcetion, FolderException, ProjectException, TeamException, MemberException {
		// TODO Auto-generated method stub
		
 		//for just copy
 		System.out.println("\n       TeamFileNodeManager   mergeNewObj() start!   line    1274");
 		//check is team Manger
		Team team = TeamManager.getINSTANCE().searchTeamCode(teamCode);
		String teamManager = team.getManager();
		if(!teamManager.equals(memberCode))return false;
		
 		//merge file!!!!
 		if(!this.teamMemberFileMap.containsKey(teamCode))this.loadCopiedTeamProject(teamCode);
		if(!this.teamMemberFileMap.get(teamCode).containsKey(branchCode))this.loadTeamMemProjects(teamCode, branchCode);
		if(!this.teamMemberFileMap.get(teamCode).get(branchCode).containsKey(memProjectCode))this.loadTeamMemProjectByCC(teamCode, branchCode, memProjectCode);
 		
		OriginProject oriProject = this.searchTeamOriProject(teamCode, memberCode, memProjectCode);
		if(oriProject==null)return false;
		String oriProCode = oriProject.getCode();
		
 		if(!this.teamFileMap.containsKey(teamCode))this.loadSharedTeamProject(teamCode);
		if(!this.teamFileMap.get(teamCode).containsKey(oriProCode))this.loadSharedTeamProjectCode(teamCode, oriProCode);
		//settign end
		
		//get msg
		CommitInfo commitInfo = CommitInfoManager.getINSTANCE().searchCommitCode(commitCode);
		String objCode = commitInfo.getObjectCode();
		StringTokenizer tokens = new StringTokenizer(objCode, "_");
		boolean isFolder = false;
		if(tokens.nextToken().equals("folder"))isFolder=true;
		String objPath = null;
		
		if(isFolder){
			OriginFolder folder = FolderManager.getINSTANCE().justSearchFolderCode(objCode);
			objPath = folder.getPath();
		}
		else {
			OriginFile oriFile = FileManager.getINSTANCE().justSearchOriginFileCode(objCode);
			objPath = oriFile.getPath();
		}		
		
		FileNode proNode = this.teamFileMap.get(teamCode).get(oriProCode);
		for(int i=1;i<folderCodes.size();i++){
			String folderCode = folderCodes.get(i);
			proNode = proNode.getChild(folderCode);
		}
		String pFolderPath = proNode.getPath(); 
		
		if(isFolder){
			Map<OriginFolder,CommitInfo> resMap = FolderManager.getINSTANCE().copyFolder(objCode, commitCode, commiter, pFolderPath, teamCode);
			if(!resMap.isEmpty()){
				OriginFolder oriFolder = resMap.keySet().iterator().next();
				proNode.createChildFolder(new MyOriginFile(oriFolder.getCode(), oriFolder.getName(), oriFolder.getPath(), "folder"));
				CommitInfo resCommitInfo = resMap.get(oriFolder);
				return CommitInfoManager.getINSTANCE().putOnlyTeamCommitInfo(teamCode, teamCode, oriProCode, resCommitInfo);
			}
		}else{
			String newContent = ((OriginFile)FileManager.getINSTANCE().searchFileContentPath(objPath)).getContent();
			List<CommitInfo> commitInfoList = MessageManager.getINSTANCE().searchCommitMsgCommitList(msgCode, objCode);
			List<String> commitCodeList = new ArrayList<String>();
			
			if(!commitInfoList.isEmpty()){
				int i=0,cnt = commitInfoList.size();
				for(i=0;i<cnt;i++){
					if(commitInfoList.get(i).getCode().equals(commitCode))break;
				}
				for(;i<cnt;i++){
					commitCodeList.add(commitInfoList.get(i).getCode());
				}
			}
			Map<OriginFile,CommitInfo> resMap = FileManager.getINSTANCE().mergeNewFileContent(teamCode, pFolderPath, newContent, objCode, commiter, commitCode, commitCodeList);
			if(!resMap.isEmpty()){
				OriginFile oriFile = resMap.keySet().iterator().next();
				proNode.createChildFolder(new MyOriginFile(oriFile.getCode(), oriFile.getName(), oriFile.getPath(), "file"));
				CommitInfo resCommitInfo = resMap.get(oriFile);
				return CommitInfoManager.getINSTANCE().putOnlyTeamCommitInfo(teamCode, teamCode, oriProCode, resCommitInfo);
				//CommitInfoManager.getINSTANCE().putOnlyCommitInfo(memberCode, oriProjectCode, commitInfo);
			}
		}
		
		return false;
	}
	
	public boolean mergeNewObj(String teamCode, String branchCode, String memberCode, String memProjectCode,  String commiter, String commitCode,String msgCode)throws FileNotFoundException, LoginException, DAOException,
	SftpException, IOException, JSchException, FileException,ParseException, RemoteFileException, CommitExcetion, FolderException, ProjectException, TeamException, MemberException {
		// TODO Auto-generated method stub
		
		//this is real :  when Manager merge file or folder commit
 		//for just copy
 		System.out.println("\n       FileNodeManager   mergeNewObj() start!   line    1492");
		//merge file!!!!
 		if(!this.teamMemberFileMap.containsKey(teamCode))this.loadCopiedTeamProject(teamCode);
		if(!this.teamMemberFileMap.get(teamCode).containsKey(branchCode))this.loadTeamMemProjects(teamCode, branchCode);
		if(!this.teamMemberFileMap.get(teamCode).get(branchCode).containsKey(memProjectCode))this.loadTeamMemProjectByCC(teamCode, branchCode, memProjectCode);
		
		//get team sharedProject
		OriginProject oriProject = this.searchTeamOriProject(teamCode, memberCode, memProjectCode);
		if(oriProject==null)return false;
		String oriProCode = oriProject.getCode();
		
 		if(!this.teamFileMap.containsKey(teamCode))this.loadSharedTeamProject(teamCode);
		if(!this.teamFileMap.get(teamCode).containsKey(oriProCode))this.loadSharedTeamProjectCode(teamCode, oriProCode);
		//settign end
		
		//get msg
		CommitInfo commitInfo = CommitInfoManager.getINSTANCE().searchCommitCode(commitCode);
		String objCode = commitInfo.getObjectCode();
		StringTokenizer tokens = new StringTokenizer(objCode, "_");
		boolean isFolder = false;
		if(tokens.nextToken().equals("folder"))isFolder=true;
		String objPath = null;
		
		String teamName = TeamManager.getINSTANCE().searchTeamCode(teamCode).getName();
		String commiterNick = MemberManager.getINSTANCE().searchMemberCode(commiter).getNickName();
		String realPFolderPath=null;
		if(isFolder){
			OriginFolder oriFolder = FolderManager.getINSTANCE().justSearchFolderCode(objCode);
			objPath = oriFolder.getPath();
			CopiedFolder cpFolder = FolderManager.getINSTANCE().searchOnlyCopiedFolderPath(oriFolder.getParentFolderPath());
			
			realPFolderPath = FolderManager.getINSTANCE().justSearchFolderCode(cpFolder.getOriginCode()).getPath();
			Map<OriginFolder,CommitInfo> resMap = FolderManager.getINSTANCE().copyFolder(objCode, commitCode, commiter, realPFolderPath, memberCode);
			if(!resMap.isEmpty()){return this.insertFolderMap(resMap, teamName, teamCode, teamCode);}
		}
		else {
			OriginFile oriFile = FileManager.getINSTANCE().justSearchOriginFileCode(objCode);
			objPath = oriFile.getPath();
			CopiedFolder cpFolder = FolderManager.getINSTANCE().searchOnlyCopiedFolderPath(oriFile.getFolderPath());
			
			realPFolderPath = FolderManager.getINSTANCE().justSearchFolderCode(cpFolder.getOriginCode()).getPath();
			
			String newContent = CommitInfoManager.getINSTANCE().searchCommitOriginFileContent(commitCode);
			if(newContent==null)newContent = ((OriginFile)FileManager.getINSTANCE().searchFileContentPath(objPath)).getContent();
			List<CommitInfo> commitInfoList = MessageManager.getINSTANCE().searchCommitMsgCommitList(msgCode, objCode);
			List<String> commitCodeList = new ArrayList<String>();
			
			if(!commitInfoList.isEmpty()){
				int i=0,cnt = commitInfoList.size();
				for(i=0;i<cnt;i++){
					if(commitInfoList.get(i).getCode().equals(commitCode))break;
				}
				for(;i<cnt;i++){
					System.out.println("commitInfoCodeList  in         :           "+commitInfoList.get(i).getCode());
					commitCodeList.add(commitInfoList.get(i).getCode());
				}
			}
			Map<OriginFile,CommitInfo> resMap = FileManager.getINSTANCE().mergeNewFileContent(memberCode, realPFolderPath, newContent, objCode, commiter, commitCode, commitCodeList);
			if(!resMap.isEmpty()){return this.insertFileMap(resMap, teamName, teamCode, teamCode);}
		}
		System.out.println("OriginParentPath   :  "+realPFolderPath);
		
		return false;
	}
	
	private String searchSharedDiffFile(String storageCode,String projectCode,List<String> folderCodes,String fileCode,String fileContent) throws DAOException, ParseException, FolderException, IOException, FileException, ProjectException, LoginException, SftpException, JSchException, RemoteFileException, CommitExcetion{
	
 		if(!this.teamFileMap.containsKey(storageCode))this.loadSharedTeamProject(storageCode);
		
		if(!this.teamFileMap.get(storageCode).containsKey(projectCode)) this.loadSharedTeamProjectCode(storageCode, projectCode);
		
		FileNode node = this.teamFileMap.get(storageCode).get(projectCode);
		int leng = folderCodes.size();
		for(int i=1;i<leng;i++){
			node=node.getChild(folderCodes.get(i));
		}
		
		node=node.getChild(fileCode);
		return FileManager.getINSTANCE().searchDiffFile(storageCode, storageCode, projectCode, folderCodes.get(leng-1), fileCode, fileContent);
	}
	
	public String searchMemDiffFile(String storageCode, String branchCode,String projectCode,List<String> folderCodes,String fileCode,String fileContent) throws DAOException, ParseException, FolderException, IOException, FileException, ProjectException, LoginException, SftpException, JSchException, RemoteFileException, CommitExcetion, TeamException{
		
		if(!this.teamMemberFileMap.containsKey(storageCode))this.loadCopiedTeamProject(storageCode);
		if(!this.teamMemberFileMap.get(storageCode).containsKey(branchCode))this.loadTeamMemProjects(storageCode, branchCode);
		if(!this.teamMemberFileMap.get(storageCode).get(branchCode).containsKey(projectCode))this.loadTeamMemProjectByCC(storageCode, branchCode, projectCode);
		
		FileNode node = this.teamMemberFileMap.get(storageCode).get(branchCode).get(projectCode);
		int leng = folderCodes.size();
		for(int i=1;i<leng;i++){
			node=node.getChild(folderCodes.get(i));
		}
		
		node=node.getChild(fileCode);
		return FileManager.getINSTANCE().searchDiffFile(branchCode, storageCode, projectCode, folderCodes.get(leng-1), fileCode, fileContent);
	}
	 	
	public List<LoadProjectForm> copyTeamProjects(String teamCode, String memberCode) throws FileNotFoundException, DAOException, MemberException, TeamException, SftpException, JSchException, FolderException, ProjectException, ParseException, IOException, FileException, RemoteFileException, CommitExcetion{
		
		if(!this.teamMemberFileMap.containsKey(teamCode))this.loadCopiedTeamProject(teamCode);
		
		Map<String,FileNode> copiedProMap= ProjectManager.getINSTANCE().copyTeamProjects((List<FileNode>)this.teamFileMap.get(teamCode).values(), memberCode, teamCode);
		this.teamMemberFileMap.get(teamCode).put(memberCode, copiedProMap);
		
		//String name, String id, String des, String makeDate,int sharedList, String status
		List<LoadProjectForm> resList = new ArrayList<LoadProjectForm>();
		if(!copiedProMap.isEmpty()){
			for(String projectCode : copiedProMap.keySet()){
				CopiedProject project = ProjectManager.getINSTANCE().searchOnlyCpProjectByProjectPath(copiedProMap.get(projectCode).getPath());
				
				resList.add(new LoadProjectForm(project.getName(), project.getCode(), project.getDescription(), project.getMakeDate(), project.getSharedMemberList().size(), "teamProejct"));
			}
		}
		
		return resList;
	}
	
////////////////////////////////////////////////////////////////////////////////////////
	

 	private List<LoadProjectForm> makeOriginMap(OriginProjectList proList,String teamCode) throws DAOException, ParseException, FolderException, IOException{
 		
 		System.out.println("\nTeamFIleNodeManager  : makeOriginMap  line 1061 start!");
 		Map<String,FileNode> projectMap = new HashMap<String, FileNode>();
 		List<LoadProjectForm> resList = new ArrayList<LoadProjectForm>();
 		for(OriginProject pro : proList.getList()){
			
			System.out.println("THis is FileNode teamSHared  load :    "+pro.getCode()+"  // "+pro.getParentFolder());
			
			Map<String,MyOriginFile> fileMap = new HashMap<String, MyOriginFile>();
			List<File> folderList = FolderManager.getINSTANCE().searchAllFoldersByPFPath(pro.getPath());
			List<File> fileList = FileManager.getINSTANCE().searchAllFileByFPath(pro.getPath());
			MyOriginFile myfolder = new MyOriginFile(pro.getCode(), pro.getName(), pro.getPath(), "project");
			myfolder.setChildFileList(pro.getChildFileList());
			myfolder.setChildFolderList(pro.getChildFolderList());
			
			if(!folderList.isEmpty()){
				for(File folder : folderList){
					OriginFolder oriFolder = (OriginFolder)folder;
					System.out.println("oriFolder __________folderCode :: "+oriFolder.getCode()+" Path : "+oriFolder.getPath());
					MyOriginFile myFold = new MyOriginFile(oriFolder.getCode(), oriFolder.getName(), oriFolder.getPath(), "folder");
					myFold.setChildFileList(oriFolder.getChildFileList());
					myFold.setChildFolderList(oriFolder.getChildFolderList());
					fileMap.put(oriFolder.getCode(), myFold);
				}
			}
			if(!fileList.isEmpty()){
				for(File file : fileList){
					OriginFile oriFile = (OriginFile)file;
					System.out.println("oriFile __________fileCode :: "+oriFile.getCode()+" Path : "+oriFile.getPath());
					MyOriginFile myFile = new MyOriginFile(oriFile.getCode(), oriFile.getName(), oriFile.getPath(), "file");
					System.out.println(myFile.getCode());
					fileMap.put(oriFile.getCode(), myFile);
				}
			}
			FileNode node;
			fileMap.put(pro.getCode(), myfolder);
			node = new FileNode(null, myfolder);
			node.initFileNode(fileMap);
			projectMap.put(pro.getCode(), node);
			
			LoadProjectForm resObj = new LoadProjectForm(pro.getName(), pro.getCode(), pro.getDescription(), pro.getMakeDate(), pro.getSharedMemberList().size(), "Original Project");
			resList.add(resObj);

			if(this.teamFileMap.containsKey(teamCode)){this.teamFileMap.get(teamCode).remove(pro.getCode());}
			this.teamFileMap.put(teamCode, projectMap);
			
		}
 		return resList;
 	}

 	private List<LoadProjectForm> makeOriginMap(OriginProject pro,String teamCode) throws DAOException, ParseException, FolderException, IOException{
 		Map<String,FileNode> projectMap = new HashMap<String, FileNode>();
 		List<LoadProjectForm> resList = new ArrayList<LoadProjectForm>();
		
		//System.out.println("THis is FileNode teamSHared  load :    "+pro.getCode()+"  // "+pro.getParentFolder());
		
		Map<String,MyOriginFile> fileMap = new HashMap<String, MyOriginFile>();
		List<File> folderList = FolderManager.getINSTANCE().searchAllFoldersByPFPath(pro.getPath());
		List<File> fileList = FileManager.getINSTANCE().searchAllFileByFPath(pro.getPath());
		MyOriginFile myfolder = new MyOriginFile(pro.getCode(), pro.getName(), pro.getPath(), "project");
		myfolder.setChildFileList(pro.getChildFileList());
		myfolder.setChildFolderList(pro.getChildFolderList());
		
		if(!folderList.isEmpty()){
			for(File folder : folderList){
				OriginFolder oriFolder = (OriginFolder)folder;
				System.out.println("oriFolder __________folderCode :: "+oriFolder.getCode()+" Path : "+oriFolder.getPath());
				MyOriginFile myFold = new MyOriginFile(oriFolder.getCode(), oriFolder.getName(), oriFolder.getPath(), "folder");
				myFold.setChildFileList(oriFolder.getChildFileList());
				myFold.setChildFolderList(oriFolder.getChildFolderList());
				fileMap.put(oriFolder.getCode(), myFold);
			}
		}
		if(!fileList.isEmpty()){
			for(File file : fileList){
				OriginFile oriFile = (OriginFile)file;
				System.out.println("oriFile __________fileCode :: "+oriFile.getCode()+" Path : "+oriFile.getPath());
				MyOriginFile myFile = new MyOriginFile(oriFile.getCode(), oriFile.getName(), oriFile.getPath(), "file");
				System.out.println(myFile.getCode());
				fileMap.put(oriFile.getCode(), myFile);
			}
		}
		FileNode node;
		fileMap.put(pro.getCode(), myfolder);
		node = new FileNode(null, myfolder);
		node.initFileNode(fileMap);
		projectMap.put(pro.getCode(), node);
		
		LoadProjectForm resObj = new LoadProjectForm(pro.getName(), pro.getCode(), pro.getDescription(), pro.getMakeDate(), pro.getSharedMemberList().size(), "Original Project");
		resList.add(resObj);

		if(!this.teamFileMap.containsKey(teamCode)){
			this.teamFileMap.put(teamCode, projectMap);
		}
		else {
			if(this.teamFileMap.get(teamCode).containsKey(pro.getCode())){
				this.teamFileMap.get(teamCode).remove(pro.getCode());
			}
			this.teamFileMap.get(teamCode).put(pro.getCode(),node);
		}
		return resList;
 	}
 	

 	private List<LoadProjectForm> makeCopiedMap(Map<String,CopiedProjectList> proMap,String teamCode) throws DAOException, ParseException, FolderException, IOException{
 		
 		System.out.println("start    :    makeCopiedMap(CopiedProjectList proList,String memberCode)");
 		
		List<LoadProjectForm> resList = new ArrayList<LoadProjectForm>();
		
		for(String memberCode : proMap.keySet()){
			CopiedProjectList proList = proMap.get(memberCode);
			if(!proList.getList().isEmpty()){
			for(CopiedProject pro : proList.getList()){
				Map<String,FileNode> projectMap = new HashMap<String, FileNode>();//cpCode and project
				
				System.out.println("\nTHis is FileNode Manager load :    "+pro.getCode()+"  // ");
				
				Map<String,MyOriginFile> fileMap = new HashMap<String, MyOriginFile>();
				List<File> folderList = FolderManager.getINSTANCE().searchAllFoldersByPFPath(pro.getPath());
				List<File> fileList = FileManager.getINSTANCE().searchAllFileByFPath(pro.getPath());
				MyOriginFile myfolder = new MyOriginFile(pro.getCode(), pro.getName(), pro.getPath(), "project");
				myfolder.setChildFileList(pro.getChildFileList());
				myfolder.setChildFolderList(pro.getChildFolderList());
				
				if(!folderList.isEmpty()){
					for(File folder : folderList){
						OriginFolder oriFolder = (OriginFolder)folder;
						System.out.println("cpFolder __________folderCode :: "+oriFolder.getCode()+" Path : "+oriFolder.getPath());
						MyOriginFile myFold = new MyOriginFile(oriFolder.getCode(), oriFolder.getName(), oriFolder.getPath(), "folder");
						myFold.setChildFileList(oriFolder.getChildFileList());
						myFold.setChildFolderList(oriFolder.getChildFolderList());
						fileMap.put(oriFolder.getCode(), myFold);
					}
				}
				if(!fileList.isEmpty()){
					for(File file : fileList){
						OriginFile oriFile = (OriginFile)file;
						System.out.println("cpFile __________fileCode :: "+oriFile.getCode()+" Path : "+oriFile.getPath());
						MyOriginFile myFile = new MyOriginFile(oriFile.getCode(), oriFile.getName(), oriFile.getPath(), "file");
						System.out.println(myFile.getCode());
						fileMap.put(oriFile.getCode(), myFile);
					}
				}
				FileNode node;
				fileMap.put(pro.getCode(), myfolder);
				node = new FileNode(null, myfolder);
				node.initFileNode(fileMap);
				projectMap.put(pro.getCode(), node);
				
				LoadProjectForm resObj = new LoadProjectForm(pro.getName(), pro.getCode(), pro.getDescription(), pro.getMakeDate(), pro.getSharedMemberList().size(), "Copied Project");
				resList.add(resObj);

				if(!this.teamMemberFileMap.containsKey(teamCode)){
					Map<String,Map<String, FileNode>> memberPerMap = new HashMap<String, Map<String,FileNode>>();//cooperCOde, cpCode and project
					memberPerMap.put(memberCode, projectMap);
					this.teamMemberFileMap.put(teamCode,memberPerMap);
				}
				else if(!this.teamMemberFileMap.get(teamCode).containsKey(memberCode)){
					Map<String,Map<String, FileNode>> memberPerMap = new HashMap<String, Map<String,FileNode>>();//cooperCOde, cpCode and project
					memberPerMap.put(memberCode, projectMap);
					this.teamMemberFileMap.get(teamCode).put(memberCode, projectMap);
				}else if(!this.teamMemberFileMap.get(teamCode).get(memberCode).containsKey(pro.getCode())){
					this.teamMemberFileMap.get(teamCode).get(memberCode).remove(pro.getCode());
					this.teamMemberFileMap.get(teamCode).get(memberCode).put(pro.getCode(),node);
				}
			}	
			}
		}
		System.out.println("end    :    makeCopiedMap(CopiedProjectList proList,String memberCode)");
		return resList;
 	}
 	
 	
 	private List<LoadProjectForm> makeCopiedMap(CopiedProjectList proList,String teamCode,String memberCode) throws DAOException, ParseException, FolderException, IOException{
 		
 		System.out.println("start    :    makeCopiedMap(CopiedProjectList proList,String memberCode)");
 		
		List<LoadProjectForm> resList = new ArrayList<LoadProjectForm>();
		
		int size = proList.getList().size();
		for(int i=0;i<size;i++){
			Map<String,FileNode> projectMap = new HashMap<String, FileNode>();//cpCode and project
			CopiedProject pro = proList.getList().get(i);
			System.out.println("\nTHis is FileNode Manager load :    "+pro.getCode()+"  // ");
			
			Map<String,MyOriginFile> fileMap = new HashMap<String, MyOriginFile>();
			List<File> folderList = FolderManager.getINSTANCE().searchAllFoldersByPFPath(pro.getPath());
			List<File> fileList = FileManager.getINSTANCE().searchAllFileByFPath(pro.getPath());
			MyOriginFile myfolder = new MyOriginFile(pro.getCode(), pro.getName(), pro.getPath(), "project");
			myfolder.setChildFileList(pro.getChildFileList());
			myfolder.setChildFolderList(pro.getChildFolderList());
			
			if(!folderList.isEmpty()){
				for(File folder : folderList){
					OriginFolder oriFolder = (OriginFolder)folder;
					System.out.println("cpFolder __________folderCode :: "+oriFolder.getCode()+" Path : "+oriFolder.getPath());
					MyOriginFile myFold = new MyOriginFile(oriFolder.getCode(), oriFolder.getName(), oriFolder.getPath(), "folder");
					myFold.setChildFileList(oriFolder.getChildFileList());
					myFold.setChildFolderList(oriFolder.getChildFolderList());
					fileMap.put(oriFolder.getCode(), myFold);
				}
			}
			if(!fileList.isEmpty()){
				for(File file : fileList){
					OriginFile oriFile = (OriginFile)file;
					System.out.println("cpFile __________fileCode :: "+oriFile.getCode()+" Path : "+oriFile.getPath());
					MyOriginFile myFile = new MyOriginFile(oriFile.getCode(), oriFile.getName(), oriFile.getPath(), "file");
					System.out.println(myFile.getCode());
					fileMap.put(oriFile.getCode(), myFile);
				}
			}
			FileNode node;
			fileMap.put(pro.getCode(), myfolder);
			node = new FileNode(null, myfolder);
			node.initFileNode(fileMap);
			projectMap.put(pro.getCode(), node);
			
			LoadProjectForm resObj = new LoadProjectForm(pro.getName(), pro.getCode(), pro.getDescription(), pro.getMakeDate(), pro.getSharedMemberList().size(), "Copied Project");
			resList.add(resObj);

			if(!this.teamMemberFileMap.containsKey(teamCode)){
				Map<String,Map<String, FileNode>> memberPerMap = new HashMap<String, Map<String,FileNode>>();//cooperCOde, cpCode and project
				memberPerMap.put(memberCode, projectMap);
				this.teamMemberFileMap.put(teamCode,memberPerMap);
			}
			else if(!this.teamMemberFileMap.get(teamCode).containsKey(memberCode)){
				Map<String,Map<String, FileNode>> memberPerMap = new HashMap<String, Map<String,FileNode>>();//cooperCOde, cpCode and project
				memberPerMap.put(memberCode, projectMap);
				this.teamMemberFileMap.get(teamCode).put(memberCode, projectMap);
			}else if(!this.teamMemberFileMap.get(teamCode).get(memberCode).containsKey(pro.getCode())){
				this.teamMemberFileMap.get(teamCode).get(memberCode).remove(pro.getCode());
				this.teamMemberFileMap.get(teamCode).get(memberCode).put(pro.getCode(),node);
			}
			
		}
		System.out.println("end    :    makeCopiedMap(CopiedProjectList proList,String memberCode)");
		return resList;
 	}

 	private List<LoadProjectForm> makeCopiedMap(CopiedProject pro,String teamCode,String memberCode) throws DAOException, ParseException, FolderException, IOException{
 		
 		System.out.println("start    :    makeCopiedMap(CopiedProjectList proList,String memberCode)");
 		
		List<LoadProjectForm> resList = new ArrayList<LoadProjectForm>();
	
		Map<String,FileNode> projectMap = new HashMap<String, FileNode>();//cpCode and project
		System.out.println("\nTHis is FileNode Manager load :    "+pro.getCode()+"  // ");
		
		Map<String,MyOriginFile> fileMap = new HashMap<String, MyOriginFile>();
		List<File> folderList = FolderManager.getINSTANCE().searchAllFoldersByPFPath(pro.getPath());
		List<File> fileList = FileManager.getINSTANCE().searchAllFileByFPath(pro.getPath());
		MyOriginFile myfolder = new MyOriginFile(pro.getCode(), pro.getName(), pro.getPath(), "project");
		myfolder.setChildFileList(pro.getChildFileList());
		myfolder.setChildFolderList(pro.getChildFolderList());
		
		if(!folderList.isEmpty()){
			for(File folder : folderList){
				OriginFolder oriFolder = (OriginFolder)folder;
				System.out.println("cpFolder __________folderCode :: "+oriFolder.getCode()+" Path : "+oriFolder.getPath());
				MyOriginFile myFold = new MyOriginFile(oriFolder.getCode(), oriFolder.getName(), oriFolder.getPath(), "folder");
				myFold.setChildFileList(oriFolder.getChildFileList());
				myFold.setChildFolderList(oriFolder.getChildFolderList());
				fileMap.put(oriFolder.getCode(), myFold);
			}
		}
		if(!fileList.isEmpty()){
			for(File file : fileList){
				OriginFile oriFile = (OriginFile)file;
				System.out.println("cpFile __________fileCode :: "+oriFile.getCode()+" Path : "+oriFile.getPath());
				MyOriginFile myFile = new MyOriginFile(oriFile.getCode(), oriFile.getName(), oriFile.getPath(), "file");
				System.out.println(myFile.getCode());
				fileMap.put(oriFile.getCode(), myFile);
			}
		}
		FileNode node;
		fileMap.put(pro.getCode(), myfolder);
		node = new FileNode(null, myfolder);
		node.initFileNode(fileMap);
		projectMap.put(pro.getCode(), node);
		
		LoadProjectForm resObj = new LoadProjectForm(pro.getName(), pro.getCode(), pro.getDescription(), pro.getMakeDate(), pro.getSharedMemberList().size(), "Copied Project");
		resList.add(resObj);

		if(!this.teamMemberFileMap.containsKey(teamCode)){
			Map<String,Map<String, FileNode>> memberPerMap = new HashMap<String, Map<String,FileNode>>();//cooperCOde, cpCode and project
			memberPerMap.put(memberCode, projectMap);
			this.teamMemberFileMap.put(teamCode,memberPerMap);
		}
		else if(!this.teamMemberFileMap.get(teamCode).containsKey(memberCode)){
			Map<String,Map<String, FileNode>> memberPerMap = new HashMap<String, Map<String,FileNode>>();//cooperCOde, cpCode and project
			memberPerMap.put(memberCode, projectMap);
			this.teamMemberFileMap.get(teamCode).put(memberCode, projectMap);
		}else if(!this.teamMemberFileMap.get(teamCode).get(memberCode).containsKey(pro.getCode())){
			this.teamMemberFileMap.get(teamCode).get(memberCode).remove(pro.getCode());
			this.teamMemberFileMap.get(teamCode).get(memberCode).put(pro.getCode(),node);
		}
		
		System.out.println("end    :    makeCopiedMap(CopiedProjectList proList,String memberCode)");
		return resList;
 	}
 	

 	private List<LoadProjectForm> makeMapForMember(Map<String,CopiedProjectList> proMap,String teamCode) throws DAOException, ParseException, FolderException, IOException{
 		
 		System.out.println("start    :    makeMapForMember(CopiedProjectList proList,String memberCode)  line 1363");
 		
		List<LoadProjectForm> resList = new ArrayList<LoadProjectForm>();
		
		for(String memberCode : proMap.keySet()){
			CopiedProjectList proList = proMap.get(memberCode);
			if(!proList.getList().isEmpty()){
			for(CopiedProject pro : proList.getList()){
				Map<String,FileNode> projectMap = new HashMap<String, FileNode>();//cpCode and project
				
				System.out.println("\nTHis is FileNode Manager load :    "+pro.getCode()+"  // ");
				
				Map<String,MyOriginFile> fileMap = new HashMap<String, MyOriginFile>();
				List<File> folderList = FolderManager.getINSTANCE().searchAllFoldersByPFPath(pro.getPath());
				List<File> fileList = FileManager.getINSTANCE().searchAllFileByFPath(pro.getPath());
				MyOriginFile myfolder = new MyOriginFile(pro.getCode(), pro.getName(), pro.getPath(), "project");
				myfolder.setChildFileList(pro.getChildFileList());
				myfolder.setChildFolderList(pro.getChildFolderList());
				
				if(!folderList.isEmpty()){
					for(File folder : folderList){
						OriginFolder oriFolder = (OriginFolder)folder;
						System.out.println("cpFolder __________folderCode :: "+oriFolder.getCode()+" Path : "+oriFolder.getPath());
						MyOriginFile myFold = new MyOriginFile(oriFolder.getCode(), oriFolder.getName(), oriFolder.getPath(), "folder");
						myFold.setChildFileList(oriFolder.getChildFileList());
						myFold.setChildFolderList(oriFolder.getChildFolderList());
						fileMap.put(oriFolder.getCode(), myFold);
					}
				}
				if(!fileList.isEmpty()){
					for(File file : fileList){
						OriginFile oriFile = (OriginFile)file;
						System.out.println("cpFile __________fileCode :: "+oriFile.getCode()+" Path : "+oriFile.getPath());
						MyOriginFile myFile = new MyOriginFile(oriFile.getCode(), oriFile.getName(), oriFile.getPath(), "file");
						System.out.println(myFile.getCode());
						fileMap.put(oriFile.getCode(), myFile);
					}
				}
				FileNode node;
				fileMap.put(pro.getCode(), myfolder);
				node = new FileNode(null, myfolder);
				node.initFileNode(fileMap);
				projectMap.put(pro.getCode(), node);
				
				LoadProjectForm resObj = new LoadProjectForm(pro.getName(), pro.getCode(), pro.getDescription(), pro.getMakeDate(), pro.getSharedMemberList().size(), "Copied Project");
				resList.add(resObj);

				if(!this.teamMemberFileMap.containsKey(teamCode)){
					Map<String,Map<String, FileNode>> memberPerMap = new HashMap<String, Map<String,FileNode>>();//cooperCOde, cpCode and project
					memberPerMap.put(memberCode, projectMap);
					this.teamMemberFileMap.put(teamCode,memberPerMap);
				}
				else if(!this.teamMemberFileMap.get(teamCode).containsKey(memberCode)){
					Map<String,Map<String, FileNode>> memberPerMap = new HashMap<String, Map<String,FileNode>>();//cooperCOde, cpCode and project
					memberPerMap.put(memberCode, projectMap);
					this.teamMemberFileMap.get(teamCode).put(memberCode, projectMap);
				}else if(!this.teamMemberFileMap.get(teamCode).get(memberCode).containsKey(pro.getCode())){
					this.teamMemberFileMap.get(teamCode).get(memberCode).remove(pro.getCode());
					this.teamMemberFileMap.get(teamCode).get(memberCode).put(pro.getCode(),node);
				}
			}	
			}
		}
		System.out.println("end    :    makeCopiedMap(CopiedProjectList proList,String memberCode)");
		return resList;
 	}
 	
 	private List<LoadProjectForm> makeMapForMember(CopiedProjectList proList,String teamCode,String memberCode) throws DAOException, ParseException, FolderException, IOException{
 		
 		System.out.println("\nstart    :    makeMapForMember(CopiedProjectList proList,String memberCode)  line 1452");
 		
		List<LoadProjectForm> resList = new ArrayList<LoadProjectForm>();
		
		int size = proList.getList().size();
		for(int i=0;i<size;i++){
			Map<String,FileNode> projectMap = new HashMap<String, FileNode>();//cpCode and project
			CopiedProject pro = proList.getList().get(i);
			System.out.println("    THis is FileNode Manager load :    "+pro.getCode()+"  // ");
			
			Map<String,MyOriginFile> fileMap = new HashMap<String, MyOriginFile>();
			List<File> folderList = FolderManager.getINSTANCE().searchAllFoldersByPFPath(pro.getPath());
			List<File> fileList = FileManager.getINSTANCE().searchAllFileByFPath(pro.getPath());
			MyOriginFile myfolder = new MyOriginFile(pro.getCode(), pro.getName(), pro.getPath(), "project");
			myfolder.setChildFileList(pro.getChildFileList());
			myfolder.setChildFolderList(pro.getChildFolderList());
			
			if(!folderList.isEmpty()){
				for(File folder : folderList){
					OriginFolder oriFolder = (OriginFolder)folder;
					System.out.println("cpFolder __________folderCode :: "+oriFolder.getCode()+" Path : "+oriFolder.getPath());
					MyOriginFile myFold = new MyOriginFile(oriFolder.getCode(), oriFolder.getName(), oriFolder.getPath(), "folder");
					myFold.setChildFileList(oriFolder.getChildFileList());
					myFold.setChildFolderList(oriFolder.getChildFolderList());
					fileMap.put(oriFolder.getCode(), myFold);
				}
			}
			if(!fileList.isEmpty()){
				for(File file : fileList){
					OriginFile oriFile = (OriginFile)file;
					System.out.println("cpFile __________fileCode :: "+oriFile.getCode()+" Path : "+oriFile.getPath());
					MyOriginFile myFile = new MyOriginFile(oriFile.getCode(), oriFile.getName(), oriFile.getPath(), "file");
					System.out.println(myFile.getCode());
					fileMap.put(oriFile.getCode(), myFile);
				}
			}
			FileNode node;
			fileMap.put(pro.getCode(), myfolder);
			node = new FileNode(null, myfolder);
			node.initFileNode(fileMap);
			projectMap.put(pro.getCode(), node);
			
			LoadProjectForm resObj = new LoadProjectForm(pro.getName(), pro.getCode(), pro.getDescription(), pro.getMakeDate(), pro.getSharedMemberList().size(), "Copied Project");
			resList.add(resObj);

			if(!this.teamMemberFileMap.containsKey(teamCode)){
				Map<String,Map<String, FileNode>> memberPerMap = new HashMap<String, Map<String,FileNode>>();//cooperCOde, cpCode and project
				memberPerMap.put(memberCode, projectMap);
				this.teamMemberFileMap.put(teamCode,memberPerMap);
			}
			else if(!this.teamMemberFileMap.get(teamCode).containsKey(memberCode)){
				Map<String,Map<String, FileNode>> memberPerMap = new HashMap<String, Map<String,FileNode>>();//cooperCOde, cpCode and project
				memberPerMap.put(memberCode, projectMap);
				this.teamMemberFileMap.get(teamCode).put(memberCode, projectMap);
			}else if(!this.teamMemberFileMap.get(teamCode).get(memberCode).containsKey(pro.getCode())){
				this.teamMemberFileMap.get(teamCode).get(memberCode).remove(pro.getCode());
				this.teamMemberFileMap.get(teamCode).get(memberCode).put(pro.getCode(),node);
			}
			FileNode noode = this.teamMemberFileMap.get(teamCode).get(memberCode).get(node.getCode());
			System.out.println("teamCode : "+teamCode+"  branchCode : "+memberCode+"  projectCode : "+node.getCode()+"  path : "+noode.getPath()+"\n");
		}
		System.out.println("end    :    makeCopiedMap(CopiedProjectList proList,String memberCode)\n");
		return resList;
 	}

 	private List<LoadProjectForm> makeMapForMember(CopiedProject pro,String teamCode,String memberCode) throws DAOException, ParseException, FolderException, IOException{
 		
 		System.out.println("start    :    makeMapForMember(CopiedProjectList proList,String memberCode)  line 1518");
 		
		List<LoadProjectForm> resList = new ArrayList<LoadProjectForm>();
	
		Map<String,FileNode> projectMap = new HashMap<String, FileNode>();//cpCode and project
		System.out.println("\nTHis is FileNode Manager load :    "+pro.getCode()+"  // ");
		
		Map<String,MyOriginFile> fileMap = new HashMap<String, MyOriginFile>();
		List<File> folderList = FolderManager.getINSTANCE().searchAllFoldersByPFPath(pro.getPath());
		List<File> fileList = FileManager.getINSTANCE().searchAllFileByFPath(pro.getPath());
		MyOriginFile myfolder = new MyOriginFile(pro.getCode(), pro.getName(), pro.getPath(), "project");
		myfolder.setChildFileList(pro.getChildFileList());
		myfolder.setChildFolderList(pro.getChildFolderList());
		
		if(!folderList.isEmpty()){
			for(File folder : folderList){
				OriginFolder oriFolder = (OriginFolder)folder;
				System.out.println("cpFolder __________folderCode :: "+oriFolder.getCode()+" Path : "+oriFolder.getPath());
				MyOriginFile myFold = new MyOriginFile(oriFolder.getCode(), oriFolder.getName(), oriFolder.getPath(), "folder");
				myFold.setChildFileList(oriFolder.getChildFileList());
				myFold.setChildFolderList(oriFolder.getChildFolderList());
				fileMap.put(oriFolder.getCode(), myFold);
			}
		}
		if(!fileList.isEmpty()){
			for(File file : fileList){
				OriginFile oriFile = (OriginFile)file;
				System.out.println("cpFile __________fileCode :: "+oriFile.getCode()+" Path : "+oriFile.getPath());
				MyOriginFile myFile = new MyOriginFile(oriFile.getCode(), oriFile.getName(), oriFile.getPath(), "file");
				System.out.println(myFile.getCode());
				fileMap.put(oriFile.getCode(), myFile);
			}
		}
		FileNode node;
		fileMap.put(pro.getCode(), myfolder);
		node = new FileNode(null, myfolder);
		node.initFileNode(fileMap);
		projectMap.put(node.getCode(), node);
			
		LoadProjectForm resObj = new LoadProjectForm(pro.getName(), pro.getCode(), pro.getDescription(), pro.getMakeDate(), pro.getSharedMemberList().size(), "Copied Project");
		resList.add(resObj);

		if(!this.teamMemberFileMap.containsKey(teamCode)){
			Map<String,Map<String, FileNode>> memberPerMap = new HashMap<String, Map<String,FileNode>>();//cooperCOde, cpCode and project
			memberPerMap.put(memberCode, projectMap);
			this.teamMemberFileMap.put(teamCode,memberPerMap);
		}
		else if(!this.teamMemberFileMap.get(teamCode).containsKey(memberCode)){
			Map<String,Map<String, FileNode>> memberPerMap = new HashMap<String, Map<String,FileNode>>();//cooperCOde, cpCode and project
			this.teamMemberFileMap.get(teamCode).put(memberCode, projectMap);
		}else if(!this.teamMemberFileMap.get(teamCode).get(memberCode).containsKey(node.getCode())){
			this.teamMemberFileMap.get(teamCode).get(memberCode).remove(node.getCode());
			this.teamMemberFileMap.get(teamCode).get(memberCode).put(node.getCode(),node);
		}
		
		System.out.println("end    :    makeCopiedMap(CopiedProjectList proList,String memberCode)");
		return resList;
 	}
 	
 	private Map<String,String> insertCopiedTeamFolder(Map<String,CopiedFolder> CopiedFolderMap, String teamCode) throws DAOException, ParseException, FolderException, SftpException, JSchException, CommitExcetion, ProjectException{
 		
 		Map<String,String> resMap = new HashMap<String, String>();
 		//1. insert Origin Team Folder
 		System.out.println("size : "+CopiedFolderMap.size());
		OriginFolder of = CopiedFolderMap.get(teamCode);
		FolderManager.getINSTANCE().insertOriginFolder((OriginFolder)of);
		StringTokenizer tokens = new StringTokenizer(of.getPath(), "\\");
		int cnt = tokens.countTokens()-1;System.out.println("token cont : "+cnt);
		String oriProPath = tokens.nextToken()+"\\"+tokens.nextToken()+"\\"+tokens.nextToken()+"\\"+tokens.nextToken();
		System.out.println(oriProPath);
		OriginProject oriProject = ProjectManager.getINSTANCE().searchASelectOriginProject(oriProPath);
		String code = oriProject.getCode();
		FileNode oriProNode = this.teamFileMap.get(teamCode).get(code);
		code=null;
		
		for(int i=4;i<cnt;i++){
			oriProPath+="\\"+tokens.nextToken();
			System.out.println(oriProPath);
			OriginFolder oriFolder = FolderManager.getINSTANCE().searchOnlyOriFoByOriFoPath(oriProPath);
			code = oriFolder.getCode();
			oriProNode=oriProNode.getChild(code);
			System.out.println(oriProPath+"   "+code);
			code=null;
		}
		System.out.println("for end.  "+of.getPath()+"   "+of.getCode());
		oriProNode.createChildFolder(new MyOriginFile(of.getCode(), of.getName(), of.getPath(), "folder"));
		resMap.put(teamCode, of.getCode());
		
		//2. insert Copied Team Folder
		CopiedFolderMap.remove(teamCode);
		System.out.println("size : "+CopiedFolderMap.size());
		for(String memCode : CopiedFolderMap.keySet()){
			
			CopiedFolder cf = CopiedFolderMap.get(memCode);
			StringTokenizer pathTokens = new StringTokenizer(cf.getPath(), "\\");
			List<String> codeList = new ArrayList<String>();
			
			int count = pathTokens.countTokens()-1; System.out.println("token cont : "+count);
			String fpath=pathTokens.nextElement()+"\\"+pathTokens.nextElement()+"\\"+pathTokens.nextElement()+"\\"+pathTokens.nextElement()+"\\"+pathTokens.nextElement();
			System.out.println("\n"+fpath);
			code = ((CopiedProject)ProjectManager.getINSTANCE().searchOnlyCpProjectByProjectPath(fpath)).getCode();
			System.out.println(fpath+"   "+code);
			FileNode copiedFileNode = this.teamMemberFileMap.get(teamCode).get(memCode).get(code);
			code=null;
			
			for(int k=5;k<count;k++){
				fpath+="\\"+pathTokens.nextToken();
				System.out.println(fpath);
				code = FolderManager.getINSTANCE().searchOnlyCopiedFolderPath(fpath).getCode();
				System.out.println(fpath+"   "+code);
				copiedFileNode=copiedFileNode.getChild(code);
				code=null;
			}
			System.out.println("for end.  "+copiedFileNode.getPath()+"   "+copiedFileNode.getCode());
			System.out.println("for end.  "+cf.getPath()+"   "+cf.getCode());
			
			copiedFileNode.createChildFolder(new MyOriginFile(cf.getCode(), cf.getName(), cf.getPath(), "folder"));
			resMap.put(memCode, cf.getCode());
		}
		return resMap;
 	}
 	

 	private Map<String,String> insertCopiedTeamFile(Map<String,CopiedFile> CopiedFileMap, String teamCode) throws DAOException, ParseException, FolderException, SftpException, JSchException, CommitExcetion, ProjectException{
 		
 		Map<String,String> resMap = new HashMap<String, String>();
 		//1. insert Origin Team Folder
 		System.out.println("size : "+CopiedFileMap.size());
		OriginFile of = CopiedFileMap.get(teamCode);
		//FileManager.getINSTANCE().insertOriginFile(of);
		StringTokenizer tokens = new StringTokenizer(of.getPath(), "\\");
		int cnt = tokens.countTokens()-1;System.out.println("token cont : "+cnt);
		String oriProPath = tokens.nextToken()+"\\"+tokens.nextToken()+"\\"+tokens.nextToken()+"\\"+tokens.nextToken();
		System.out.println(oriProPath);
		OriginProject oriProject = ProjectManager.getINSTANCE().searchASelectOriginProject(oriProPath);
		String code = oriProject.getCode();
		FileNode oriProNode = this.teamFileMap.get(teamCode).get(code);
		code=null;
		
		for(int i=4;i<cnt;i++){
			oriProPath+="\\"+tokens.nextToken();
			System.out.println(oriProPath);
			OriginFolder oriFolder = FolderManager.getINSTANCE().searchOnlyOriFoByOriFoPath(oriProPath);
			code = oriFolder.getCode();
			oriProNode=oriProNode.getChild(code);
			System.out.println(oriProPath+"   "+code);
			code=null;
		}
		System.out.println("for end.  "+of.getPath()+"   "+of.getCode());
		oriProNode.createChildFile(new MyOriginFile(of.getCode(), of.getName(), of.getPath(), "file"));
		resMap.put(teamCode, of.getCode());
		
		//2. insert Copied Team Folder
		CopiedFileMap.remove(teamCode);
		System.out.println("size : "+CopiedFileMap.size());
		for(String memCode : CopiedFileMap.keySet()){
			
			CopiedFile cf = CopiedFileMap.get(memCode);
			StringTokenizer pathTokens = new StringTokenizer(cf.getPath(), "\\");
			List<String> codeList = new ArrayList<String>();
			
			int count = pathTokens.countTokens()-1; System.out.println("token cont : "+count);
			String fpath=pathTokens.nextElement()+"\\"+pathTokens.nextElement()+"\\"+pathTokens.nextElement()+"\\"+pathTokens.nextElement()+"\\"+pathTokens.nextElement();
			System.out.println("\n"+fpath);
			code = ((CopiedProject)ProjectManager.getINSTANCE().searchOnlyCpProjectByProjectPath(fpath)).getCode();
			System.out.println(fpath+"   "+code);
			FileNode copiedFileNode = this.teamMemberFileMap.get(teamCode).get(memCode).get(code);
			code=null;
			
			for(int k=5;k<count;k++){
				fpath+="\\"+pathTokens.nextToken();
				System.out.println(fpath);
				code = FolderManager.getINSTANCE().searchOnlyCopiedFolderPath(fpath).getCode();
				System.out.println(fpath+"   "+code);
				copiedFileNode=copiedFileNode.getChild(code);
				code=null;
			}
			System.out.println("for end.  "+copiedFileNode.getPath()+"   "+copiedFileNode.getCode());
			System.out.println("for end.  "+cf.getPath()+"   "+cf.getCode());
			
			copiedFileNode.createChildFile(new MyOriginFile(cf.getCode(), cf.getName(), cf.getPath(), "file"));
			resMap.put(memCode, cf.getCode());
		}
		return resMap;
 	}
 	private boolean insertFolderMap(Map<OriginFolder, CommitInfo> teamSharedMap, String teamName, String teamCode, String memberCode) throws DAOException, ParseException, FolderException, FileNotFoundException, CommitExcetion, SftpException, IOException, JSchException, ProjectException, MemberException, FileException, RemoteFileException, TeamException{
 		
 		OriginFolder oriFolder = teamSharedMap.keySet().iterator().next();
		String oriFolderPath = oriFolder.getPath();
		StringTokenizer token1 = new StringTokenizer(oriFolderPath, teamName);
		String head = token1.nextToken()+"\\"+teamName;
		StringTokenizer token2 = new StringTokenizer(token1.nextToken(), "\\");
		head+="\\"+token2.nextToken();//~/teamName/projectName
		String sharedProCode = ProjectManager.getINSTANCE().justSearchProjectPath(head).getCode();
		String code;
		FileNode sharedProNode = this.teamFileMap.get(teamCode).get(sharedProCode);
		while(token2.hasMoreTokens()){
			head+="\\"+token2.nextToken();
			code = FolderManager.getINSTANCE().justSearchFolderPath(head).getCode();
			if(code!=oriFolder.getCode()){
				sharedProNode = sharedProNode.getChild(code);
			}
		}
		sharedProNode.createChildFolder(new MyOriginFile(oriFolder.getCode(), oriFolder.getName(), oriFolder.getPath(), "folder"));
		CommitInfo commitInfo = teamSharedMap.get(oriFolder);
		return CommitInfoManager.getINSTANCE().putOnlyTeamCommitInfo(teamCode, memberCode, sharedProCode, commitInfo);
		//CommitInfoManager.getINSTANCE().putOnlyCommitInfo(memberCode, oriProjectCode, commitInfo);
 	}
 	private boolean insertFileMap(Map<OriginFile, CommitInfo> newSharedFileMap, String teamName, String teamCode, String memberCode) throws DAOException, ParseException, FolderException, FileNotFoundException, CommitExcetion, SftpException, IOException, JSchException, ProjectException, MemberException, FileException, RemoteFileException, TeamException{
 		
 		OriginFile oriFile = newSharedFileMap.keySet().iterator().next();
		String oriFilePath = oriFile.getPath();
		StringTokenizer token1 = new StringTokenizer(oriFilePath, teamName);
		String head = token1.nextToken()+"\\"+teamName;
		StringTokenizer token2 = new StringTokenizer(token1.nextToken(), "\\");
		head+="\\"+token2.nextToken();//~/teamName/projectName
		String sharedProCode = ProjectManager.getINSTANCE().justSearchProjectPath(head).getCode();
		String code;
		FileNode sharedProNode = this.teamFileMap.get(teamCode).get(sharedProCode);
		while(token2.hasMoreTokens()){
			head+="\\"+token2.nextToken();
			OriginFolder oriFolder = FolderManager.getINSTANCE().justSearchFolderPath(head);
			if(oriFolder!=null){
				code = oriFolder.getCode();
				sharedProNode = sharedProNode.getChild(code);
			}
		}
		sharedProNode.createChildFolder(new MyOriginFile(oriFile.getCode(), oriFile.getName(), oriFile.getPath(), "file"));
		CommitInfo commitInfo = newSharedFileMap.get(oriFile);
		return CommitInfoManager.getINSTANCE().putOnlyTeamCommitInfo(teamCode, memberCode, sharedProCode, commitInfo);
		//CommitInfoManager.getINSTANCE().putOnlyCommitInfo(memberCode, oriProjectCode, commitInfo);
 	}
 	
}