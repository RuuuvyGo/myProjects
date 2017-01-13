package manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import model.CommitInfo;
import model.CopiedFile;
import model.CopiedFolder;
import model.CopiedProject;
import model.CopiedProjectList;
import model.FileNode;
import model.MyOriginFile;
import model.OriginProject;
import model.OriginProjectList;
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

import form.ProjectSearchedForm;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : ProjectManager.java
//  @ Date : 2015-07-28
//  @ Author : 
//
//




public class ProjectManager implements ProjectInfoModifiable, ProjectCreatable, ProjectDeletable, ProjectSearchable, ProjectSharable  {

	private static ProjectManager INSTANCE;
	private Map<String,File> projectMap; //path, OriginFolder or CopiedFolder
	
	static {
		INSTANCE = new ProjectManager();
	}
	
	private ProjectManager() {
		// TODO Auto-generated constructor stub
		this.projectMap = new HashMap<String, File>();
	}
	
	
	public static ProjectManager getINSTANCE() {
		if(INSTANCE==null)INSTANCE = new ProjectManager();
		return INSTANCE;
	}

	@Override
	public boolean shareProjectCode(String nickName, String projectCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shareProjectName(String nickName, String path) {
		// TODO Auto-generated method stub
		return false;
	}

	


	@Override
	public boolean deleteProjectTeam(String nickName, String name, String path) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteProjectTeam(String nickName, String code) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String createProject(String memberCode,String nickName, String name,String description, String path,List<String> tagList)  throws DAOException, ParseException, SftpException, JSchException, ProjectException, FolderException, TagException{
		// TODO Auto-generated method stub
		//1. check projectName
		System.out.println("This is ProjectManager.... "+path);
		if(this.projectMap.containsKey(path))throw new ProjectException("This Project Name has already used!!!");
		OriginProject pro=ProjectDBManager.getINSTANCE().insertOriginProject(memberCode, nickName, name, description, path, tagList);
		if(pro!=null){
			this.projectMap.put(pro.getCode(), pro);
			return pro.getCode();
		}
		else{return null;}
	}
		
		
	
	@Override
	public String createProject(String memberCode,String nickName, String name,String description, String path,List<String> tagList,String commitTitle,String commitContent)  throws DAOException, ParseException, SftpException, JSchException, CommitExcetion, FolderException, ProjectException, TagException, FileNotFoundException, IOException, MemberException, FileException, RemoteFileException, TeamException{
		// TODO Auto-generated method stub
		
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  call from ProjectServlet creatProject
		
		//1. check projectName
		System.out.println("This is ProjectManager.... "+path);
		
		if(this.projectMap.containsKey(path))throw new ProjectException("This Project Name has already used!!!");
		
		OriginProject oriPro = ProjectDBManager.getINSTANCE().insertOriginProject(memberCode, nickName, name, description, path, tagList);
		
		CommitInfoManager.getINSTANCE().putCommitInfo(memberCode, oriPro.getCode(), new CommitInfo(oriPro.getCode(), "create", commitContent, oriPro.getMakeDate(), memberCode, false, null, commitTitle));
		CommitInfoManager.getINSTANCE().putCommitInfo(memberCode, oriPro.getParentFolder(), new CommitInfo(oriPro.getParentFolder(), "create", commitContent, oriPro.getMakeDate(), memberCode, false, null, commitTitle));
		this.projectMap.put(oriPro.getPath(), oriPro);
		return oriPro.getCode();
	}
	
	@Override
	public String createProject(String memberCode,String nickName, String name, String path, List<String> tagList) throws DAOException, ParseException, SftpException, JSchException, ProjectException, FolderException, TagException {
		// TODO Auto-generated method stub
		//1. check projectName
		if(this.projectMap.containsKey(path))throw new ProjectException("This Project Name has already used!!!");
		
		OriginProject pro=ProjectDBManager.getINSTANCE().insertOriginProject(memberCode, nickName, name, path, tagList);
		if(pro!=null){
			this.projectMap.put(pro.getCode(), pro);
			return pro.getCode();
		}
		else{return null;}
	}

	@Override
	public boolean modifyProject(String nickName, String name,String descriptioin, String path, String parentFolder,List<String> tagList) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<File> createTeamProject(String teamCode, String teamName,String projectName, String proDes, String path, List<String> tagList,String commitTitle, String commitContent) throws DAOException, SftpException, JSchException, FolderException, ProjectException, TeamException, CommitExcetion, TagException, FileNotFoundException, ParseException, IOException, MemberException, FileException, RemoteFileException {
		// TODO Auto-generated method stub
		
		TeamManager.getINSTANCE().searchTeamCode(teamCode);
		List<File> resList = ProjectDBManager.getINSTANCE().insertCopiedTeamProject(teamCode, teamName, projectName, proDes, path, tagList);
		if(!resList.isEmpty()){
			for(File file : resList){
				String memberCode=null;
				String projectCode=null;
				String folderCode = null;
				String cal=null;
				if(file instanceof CopiedProject){
					CopiedProject pro = ((CopiedProject)file);
					memberCode = pro.getOwner();
					projectCode = pro.getCode();
					folderCode = pro.getParentFolder();
					cal = pro.getMakeDate();
					this.projectMap.put(pro.getCode(), pro);
				}else if(file instanceof OriginProject){
					OriginProject pro = ((OriginProject)file);
					memberCode = pro.getOwner();
					projectCode = pro.getCode();
					folderCode = pro.getParentFolder();
					cal = pro.getMakeDate();
					this.projectMap.put(pro.getCode(), pro);
				}
				
				CommitInfoManager.getINSTANCE().putTeamCommitInfo(teamCode, memberCode, projectCode, new CommitInfo(projectCode, "create", commitContent, cal, memberCode, true, null, commitTitle));
				CommitInfoManager.getINSTANCE().putTeamCommitInfo(teamCode, memberCode, projectCode, new CommitInfo(folderCode, "create", commitContent, cal, memberCode, true, null, commitTitle));
			}
		}		
		return resList;
	}
	
	@Override
	public Map<String,FileNode> copyTeamProjects(List<FileNode> projectList,String memberCode,String teamCode) throws FileNotFoundException, DAOException, MemberException, TeamException, SftpException, JSchException, FolderException, ProjectException, ParseException, IOException, FileException, RemoteFileException, CommitExcetion {
		
		Map<String,FileNode> resList = new HashMap<String,FileNode>();
		Map<String,Map<String,File>> resMap= ProjectDBManager.getINSTANCE().copyTeamProjects(projectList, memberCode, teamCode);
		List<CopiedFolder> insertFolderList = new ArrayList<CopiedFolder>();
		List<CopiedFile> insertFileList = new ArrayList<CopiedFile>();
		
		for(String cpProCode : resMap.keySet()){
			
			Map<String,File> cpProChilds = resMap.get(cpProCode);
			CopiedProject cpro = (CopiedProject)cpProChilds.get(cpProCode);
			this.projectMap.put(cpProCode, cpro);
			
			MyOriginFile moFile= new MyOriginFile(cpro.getCode(), cpro.getName(), cpro.getPath(), "project");
			moFile.setChildFileList(cpro.getChildFileList());
			moFile.setChildFolderList(cpro.getChildFolderList());
			FileNode fileN = new FileNode(null,moFile);
			Map<String,MyOriginFile> childs = new HashMap<String,MyOriginFile>();
			
			cpProChilds.remove(cpProCode);
			for(String cpChildCode : cpProChilds.keySet()){
				if(new StringTokenizer(cpChildCode, "_").nextToken().equals("folder")){
					CopiedFolder cfolder= (CopiedFolder)cpProChilds.get(cpChildCode);
					insertFolderList.add(cfolder);
					MyOriginFile mocFile = new MyOriginFile(cpChildCode, cfolder.getName(), cfolder.getPath(), "folder");
					mocFile.setChildFileList(cfolder.getChildFileList());
					mocFile.setChildFolderList(cfolder.getChildFolderList());
					childs.put(cpChildCode, mocFile);
				}else{
					CopiedFile cfolder= (CopiedFile)cpProChilds.get(cpChildCode);
					insertFileList.add(cfolder);
					MyOriginFile mocFile = new MyOriginFile(cpChildCode, cfolder.getName(), cfolder.getPath(), "file");
					childs.put(cpChildCode, mocFile);
				}
			}
			fileN.initFileNode(childs);
			resList.put(cpProCode,fileN);
		}
		
		return resList;
	}
	
	@Override
	public FileNode copyOriginProject(FileNode project,String memberCode,String ownerCode) throws DAOException, MemberException, TeamException, SftpException, JSchException, IOException, FolderException, ProjectException, ParseException, FileException, RemoteFileException, CommitExcetion{
		
		System.out.println("ProjectManager  :   copyOriginProject  line  456");
		Map<String,File> cpProChilds= ProjectDBManager.getINSTANCE().copyOriginProject(project, memberCode, ownerCode);
		
		if(!cpProChilds.isEmpty()){
			String cpCode=null;
			for(String code : cpProChilds.keySet()){
				cpCode = code;
				if(new StringTokenizer(code, "_").nextToken().equals("project")){System.out.println(cpCode);break;}
				else cpCode=null;
			}
			if(cpCode!=null){
				CopiedProject cpro = (CopiedProject)cpProChilds.get(cpCode);
				this.projectMap.put(cpCode, cpro);
				
				MyOriginFile moFile= new MyOriginFile(cpro.getCode(), cpro.getName(), cpro.getPath(), "project");
				moFile.setChildFileList(cpro.getChildFileList());
				moFile.setChildFolderList(cpro.getChildFolderList());
				FileNode fileN = new FileNode(null,moFile);
				Map<String,MyOriginFile> childs = new HashMap<String,MyOriginFile>();
				
				cpProChilds.remove(project.getCode());
				String fileOrFolder;
				for(String cpChildCode : cpProChilds.keySet()){
					fileOrFolder= new StringTokenizer(cpChildCode, "_").nextToken();
					if(fileOrFolder.equals("folder")){
						CopiedFolder cfolder= (CopiedFolder)cpProChilds.get(cpChildCode);
						System.out.println("copiedFolder : "+cfolder.getPath());
						FolderManager.getINSTANCE().insertCopiedFolder(cfolder);
						MyOriginFile mocFile = new MyOriginFile(cpChildCode, cfolder.getName(), cfolder.getPath(), "folder");
						mocFile.setChildFileList(cfolder.getChildFileList());
						mocFile.setChildFolderList(cfolder.getChildFolderList());
						childs.put(cpChildCode, mocFile);
					}else if(fileOrFolder.equals("file")){
						CopiedFile cfolder= (CopiedFile)cpProChilds.get(cpChildCode);
						System.out.println("copiedFile : "+cfolder.getPath());
						FileManager.getINSTANCE().insertCopiedFile(cfolder);
						MyOriginFile mocFile = new MyOriginFile(cpChildCode, cfolder.getName(), cfolder.getPath(), "file");
						childs.put(cpChildCode, mocFile);
					}
				}
				fileN.initFileNode(childs);
				return fileN;
			}
		}
		
		return null;
	}

	

	@Override
	public boolean deleteProjectMemPath(String nickName, String name,
			String path) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteProjectMemCode(String memberCode, String storageCode,
			String projectCode) throws DAOException, ParseException,
			ProjectException {
		// TODO Auto-generated method stub
		return false;
	}

///////////////////////////////////////////////////////////////////////////  search
	

	//search exactlly
	@Override
	public List<File> searchPersonalProjectByOwnerCode(String ownerCode) throws DAOException, ParseException, FolderException{
		
		List<File> resList =  new ArrayList<File>();
		
		resList = ProjectDBManager.getINSTANCE().searchPersonalProjectByOwnerCode(ownerCode);
		if(!resList.isEmpty()){
			for(File file : resList){
				String projectPath = file.getPath();
				if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
				if(file instanceof CopiedProject) this.projectMap.put(projectPath, (CopiedProject)file);
				else this.projectMap.put(projectPath, (OriginProject)file);
			}
		}
		
		return resList;
	}
	@Override
	public Map<String,List<File>> searchPersonalProjectByOwnerCodeMap(String ownerCode) throws DAOException, ParseException, FolderException{

		Map<String,List<File>> resMap = new HashMap<String, List<File>>();
		List<File> origin =  new ArrayList<File>();
		List<File> copy =  new ArrayList<File>();
		
		List<File> resList = ProjectDBManager.getINSTANCE().searchPersonalProjectByOwnerCode(ownerCode);
		if(!resList.isEmpty()){
			for(File file : resList){
				String projectPath = file.getPath();
				if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
				if(file instanceof CopiedProject) {
					copy.add((CopiedProject)file);
					this.projectMap.put(projectPath, (CopiedProject)file);
				}
				else {
					origin.add((OriginProject)file);
					this.projectMap.put(projectPath, (OriginProject)file);
				}
			}
		}
		resMap.put("originProject", origin);
		resMap.put("copiedProject", copy);
		return resMap;
	}
	@Override
	public Map<String,List<File>> searchPersonalProjectByOwnerCode(List<String> ownerCodeList) throws DAOException, ParseException, FolderException{
		
		Map<String,List<File>> resMap = new HashMap<String, List<File>>();
		for(String ownerCode : ownerCodeList){

			List<File> resList =  new ArrayList<File>();
			
			resList = ProjectDBManager.getINSTANCE().searchPersonalProjectByOwnerCode(ownerCode);
			if(!resList.isEmpty()){
				for(File file : resList){
					String projectPath = file.getPath();
					if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
					if(file instanceof CopiedProject) this.projectMap.put(projectPath, (CopiedProject)file);
					else this.projectMap.put(projectPath, (OriginProject)file);
				}
				resMap.put(ownerCode, resList);
			}
		}
		return resMap;
	}
	@Override
	public File searchProjectByProjectCode(String projectCode) throws DAOException, ParseException, FolderException{
		
		File res = ProjectDBManager.getINSTANCE().searchProjectByProjectCode(projectCode);
		if(res!=null){
			String projectPath = res.getPath();
			if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
			if(res instanceof CopiedProject) this.projectMap.put(projectPath, (CopiedProject)res);
			else this.projectMap.put(projectPath, (OriginProject)res);
		}
		return res;
	}
	@Override
	public List<File> searchProjectByProjectCode(List<String> projectCodeList) throws DAOException, ParseException, FolderException{
		
		List<File> resList = new ArrayList<File>();
		for(String projectCode : projectCodeList){

			File res = ProjectDBManager.getINSTANCE().searchProjectByProjectCode(projectCode);
			if(res!=null){
				String projectPath = res.getPath();
				if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
				if(res instanceof CopiedProject) this.projectMap.put(projectPath, (CopiedProject)res);
				else this.projectMap.put(projectPath, (OriginProject)res);
				resList.add(res);
			}
		}
		return resList;
	}
	@Override
	public File searchProjectByProjectPath(String projectPath) throws DAOException, ParseException, FolderException{
		
		if(this.projectMap.containsKey(projectPath)){
			return this.projectMap.get(projectPath);
		}else{
			File res = ProjectDBManager.getINSTANCE().searchProjectByProjectPath(projectPath);
			if(res!=null){
				if(res instanceof CopiedProject) this.projectMap.put(projectPath, (CopiedProject)res);
				else this.projectMap.put(projectPath, (OriginProject)res);
			}
			return res;
		}
	}
	@Override
	public List<File> searchProjectByProjectPath(List<String> projectPathList) throws DAOException, ParseException, FolderException{
		
		List<File> resList = new ArrayList<File>();
		for(String projectPath : projectPathList){
			if(this.projectMap.containsKey(projectPath)){
				resList.add(this.projectMap.get(projectPath));
			}else{
				File res = ProjectDBManager.getINSTANCE().searchProjectByProjectPath(projectPath);
				if(res!=null){
					if(res instanceof CopiedProject) this.projectMap.put(projectPath, (CopiedProject)res);
					else this.projectMap.put(projectPath, (OriginProject)res);
				}
				resList.add(res);
			}
		}
		return resList;
	}
	
	
	
	
	//just search originProject doesn't matter what is real
	@Override
	public OriginProject justSearchProjectCode(String projectCode) throws DAOException, ParseException, FolderException{
		
		return ProjectDBManager.getINSTANCE().justSearchProjectCode(projectCode);
	}
	@Override
	public OriginProject justSearchProjectPath(String projectPath) throws DAOException, ParseException, FolderException{
		if(this.projectMap.containsKey(projectPath))return (OriginProject) this.projectMap.get(projectPath);
		return ProjectDBManager.getINSTANCE().justSearchProjectPath(projectPath);
	}
	@Override
	public OriginProject justSearchProjectFolderCode(String folderCode) throws DAOException, ParseException, FolderException{
		
		return ProjectDBManager.getINSTANCE().justSearchProjectFolderCode(folderCode);
	}
	
	
	
	//return only real OriginProject
	@Override
	public OriginProjectList searchOnlyOriginProjectByOwnerCode(String ownerCode) throws DAOException, ParseException, FolderException{
		
		System.out.println("ownerCode : "+ownerCode);
		OriginProjectList resList = ProjectDBManager.getINSTANCE().searchOnlyOriginProjectByOwnerCode(ownerCode);
		System.out.println("onlyoriginProject list size  : "+resList.getList().size());
		if(!resList.getList().isEmpty()){
			for(OriginProject oriProject : resList.getList()){
				String projectPath = oriProject.getPath();
				if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
				this.projectMap.put(projectPath, oriProject);
			}
		}
		return resList;
	}
	@Override
	public Map<String,OriginProjectList> searchOnlyOriginProjectByOwnerCode(List<String> ownerCodeList) throws DAOException, ParseException, FolderException{
		
		Map<String,OriginProjectList> resMap = new HashMap<String, OriginProjectList>();
		for(String ownerCode : ownerCodeList){
			OriginProjectList resList = ProjectDBManager.getINSTANCE().searchOnlyOriginProjectByOwnerCode(ownerCode);
			if(!resList.getList().isEmpty()){
				for(OriginProject oriProject : resList.getList()){
					String projectPath = oriProject.getPath();
					if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
					this.projectMap.put(projectPath, oriProject);
				}
				resMap.put(ownerCode, resList);
			}
		}
		return resMap;
	}
	@Override
	public OriginProject searchOnlyOriginProjectByProjectCode(String projectCode) throws DAOException, ParseException, FolderException{
		
		OriginProject res = ProjectDBManager.getINSTANCE().searchOnlyOriginProjectByProjectCode(projectCode);
		if(res!=null){
			String projectPath = res.getPath();
			if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
			this.projectMap.put(projectPath, res);
		}
		return res;
	}
	@Override
	public OriginProjectList searchOnlyOriginProjectByProjectCode(List<String> projectCodeList) throws DAOException, ParseException, FolderException{
		
		OriginProjectList resList = new OriginProjectList();
		
		for(String projectCode : projectCodeList){
			OriginProject res = ProjectDBManager.getINSTANCE().searchOnlyOriginProjectByProjectCode(projectCode);
			if(res!=null){
				String projectPath = res.getPath();
				if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
				this.projectMap.put(projectPath, res);
				resList.addOriginPorject(res);
			}
		}
		
		return resList;
	}
	@Override
	public OriginProject searchOnlyOriginProjectByProjectPath(String projectPath) throws DAOException, ParseException, FolderException{
		
		if(this.projectMap.containsKey(projectPath)){
			return (OriginProject) this.projectMap.get(projectPath);
		}else{
			OriginProject res = ProjectDBManager.getINSTANCE().searchOnlyOriginProjectByProjectPath(projectPath);
			if(res!=null){this.projectMap.put(projectPath, res);}
			return res;
		}
	}
	@Override
	public OriginProjectList searchOnlyOriginProjectByProjectPath(List<String> projectPathList) throws DAOException, ParseException, FolderException{
		
		OriginProjectList resList = new OriginProjectList();
		
		for(String projectPath : projectPathList){

			if(this.projectMap.containsKey(projectPath)){
				resList.addOriginPorject((OriginProject) this.projectMap.get(projectPath));
			}else{
				OriginProject res = ProjectDBManager.getINSTANCE().searchOnlyOriginProjectByProjectPath(projectPath);
				if(res!=null){this.projectMap.put(projectPath, res);}
				resList.addOriginPorject(res);
			}
		}
		
		return resList;
	}
	@Override
	public OriginProject searchOnlyOriginProjectByFolderCode(String folderCode) throws DAOException, ParseException, FolderException{
		
		OriginProject res = ProjectDBManager.getINSTANCE().searchOnlyOriginProjectByFolderCode(folderCode);
		if(res!=null){
			String projectPath = res.getPath();
			if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
			this.projectMap.put(projectPath, res);
		}
		return res;
	}
	@Override
	public OriginProjectList searchOnlyOriginProjectByFolderCode(List<String> folderCodeList) throws DAOException, ParseException, FolderException{
		
		OriginProjectList resList = new OriginProjectList();
		
		for(String folderCode : folderCodeList){

			OriginProject res = ProjectDBManager.getINSTANCE().searchOnlyOriginProjectByFolderCode(folderCode);
			if(res!=null){
				String projectPath = res.getPath();
				if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
				this.projectMap.put(projectPath, res);
				resList.addOriginPorject(res);
			}
		}
		
		return resList;
	}
	@Override
	public OriginProjectList searchOnlyOriginProjectByTagNameList(List<String> tagNameList) throws DAOException, ParseException, FolderException{
		
		OriginProjectList resList = ProjectDBManager.getINSTANCE().searchOnlyOriginProjectByTagNameList(tagNameList);
		if(!resList.getList().isEmpty()){
			for(OriginProject oriProject : resList.getList()){
				String projectPath = oriProject.getPath();
				if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
				this.projectMap.put(projectPath, oriProject);
			}
		}
		return resList;
	}

	
	
	//return only real CopiedProject
	@Override
	public CopiedProjectList searchOnlyPersonalCpProjectByOwnerCode(String ownerCode) throws DAOException, ParseException, FolderException, ProjectException{
		
		CopiedProjectList resList = ProjectDBManager.getINSTANCE().searchOnlyPersonalCpProjectByOwnerCode(ownerCode);
		
		if(!resList.getList().isEmpty()){
			for(CopiedProject cpProject : resList.getList()){
				String projectPath = cpProject.getPath();
				if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
				this.projectMap.put(projectPath, cpProject);
			}
		}
		
		return resList;
	}
	@Override
	public Map<String,CopiedProjectList> searchOnlyPersonalCpProjectByOwnerCode(List<String> ownerCodeList) throws DAOException, ParseException, FolderException, ProjectException{
		
		Map<String,CopiedProjectList> resMap = new HashMap<String, CopiedProjectList>();
		
		for(String ownerCode : ownerCodeList){

			CopiedProjectList resList = ProjectDBManager.getINSTANCE().searchOnlyPersonalCpProjectByOwnerCode(ownerCode);
			
			if(!resList.getList().isEmpty()){
				for(CopiedProject cpProject : resList.getList()){
					String projectPath = cpProject.getPath();
					if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
					this.projectMap.put(projectPath, cpProject);
				}
				resMap.put(ownerCode, resList);
			}
		}
		
		return resMap;
	}
	@Override
	public CopiedProjectList searchOnlyTeamCpProjectByOwnerCode(String teamCode, String cooper) throws DAOException, ParseException, FolderException, ProjectException{

		CopiedProjectList resList = ProjectDBManager.getINSTANCE().searchOnlyTeamCpProjectByOwnerCode(teamCode, cooper);
		
		if(!resList.getList().isEmpty()){
			for(CopiedProject cpProject : resList.getList()){
				String projectPath = cpProject.getPath();
				if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
				this.projectMap.put(projectPath, cpProject);
			}
		}
		
		return resList;
	}
	@Override
	public Map<String,CopiedProjectList> searchOnlyTeamCpProjectByOwnerCode(String teamCode, List<String> cooperList) throws DAOException, ParseException, FolderException, ProjectException{

		Map<String,CopiedProjectList> resMap = new HashMap<String, CopiedProjectList>();
		
		for(String cooper : cooperList){

			CopiedProjectList resList = ProjectDBManager.getINSTANCE().searchOnlyTeamCpProjectByOwnerCode(teamCode, cooper);
			
			if(!resList.getList().isEmpty()){
				for(CopiedProject cpProject : resList.getList()){
					String projectPath = cpProject.getPath();
					if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
					this.projectMap.put(projectPath, cpProject);
				}
				resMap.put(cooper, resList);
			}
		}
		
		return resMap;
	}
	@Override
	public Map<String,Map<String,CopiedProjectList>> searchOnlyTeamCpProjectByOwnerCode(Map<String,List<String>> teamCooperMap) throws DAOException, ParseException, FolderException, ProjectException{
		
		Map<String,Map<String,CopiedProjectList>> resMap = new HashMap<String, Map<String,CopiedProjectList>>();
		
		for(String teamCode : teamCooperMap.keySet()){
			List<String> cooperList = teamCooperMap.get(teamCode);
			if(!cooperList.isEmpty()){
				Map<String,CopiedProjectList> tmpMap = new HashMap<String, CopiedProjectList>();
				
				for(String cooper : cooperList){

					CopiedProjectList resList = ProjectDBManager.getINSTANCE().searchOnlyTeamCpProjectByOwnerCode(teamCode, cooper);
					
					if(!resList.getList().isEmpty()){
						for(CopiedProject cpProject : resList.getList()){
							String projectPath = cpProject.getPath();
							if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
							this.projectMap.put(projectPath, cpProject);
						}
						tmpMap.put(cooper, resList);
					}
				}
				if(!tmpMap.isEmpty())resMap.put(teamCode, tmpMap);
			}
		}
		
		return resMap;
	}
	@Override
	public CopiedProject searchOnlyCpProjectByOriProCode(String oriProCode, String cooper) throws DAOException, ParseException, FolderException, ProjectException{
		CopiedProject res = ProjectDBManager.getINSTANCE().searchOnlyCpProjectByOriProCode(oriProCode, cooper);
		if(res!=null){
			String projectPath = res.getPath();
			if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
			this.projectMap.put(projectPath, res);
		}
		return res;
	}
	@Override
	public Map<String,CopiedProject> searchOnlyCpProjectByOriProCode(String oriProCode) throws DAOException, ParseException, FolderException, ProjectException{
		
		Map<String,CopiedProject> resMap = new HashMap<String, CopiedProject>();
		
		CopiedProjectList resList = ProjectDBManager.getINSTANCE().searchOnlyCpProjectByOriProCode(oriProCode);
		if(!resList.getList().isEmpty()){
			for(CopiedProject res : resList.getList()){
				String projectPath = res.getPath();
				String ownerCode = res.getOwner(); 
				if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
				this.projectMap.put(projectPath, res);
				
				resMap.put(ownerCode, res);
			}
		}
		
		return resMap;
	}
	@Override
	public CopiedProject searchOnlyCpProjectByOriProPath(String oriProPath, String cooper) throws DAOException, ParseException, FolderException, ProjectException{
		
		String oriProCode;
		if(this.projectMap.containsKey(oriProPath)){
			File file = this.projectMap.get(oriProPath);
			if(file instanceof CopiedProject)return null; 
			oriProCode = ((OriginProject)file).getCode(); 
		}else{
			OriginProject oriPro = ProjectDBManager.getINSTANCE().searchOnlyOriginProjectByProjectPath(oriProPath);
			this.projectMap.put(oriProPath, oriPro);
			oriProCode = oriPro.getCode();
		}
		
		CopiedProject res = ProjectDBManager.getINSTANCE().searchOnlyCpProjectByOriProCode(oriProCode, cooper);
		if(res!=null){
			String projectPath = res.getPath();
			if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
			this.projectMap.put(projectPath, res);
		}
		return res;
	}
	@Override
	public Map<String,CopiedProject> searchOnlyCpProjectByOriProPath(String oriProPath) throws DAOException, ParseException, FolderException, ProjectException{
		
		String oriProCode;
		if(this.projectMap.containsKey(oriProPath)){
			File file = this.projectMap.get(oriProPath);
			if(file instanceof CopiedProject)return null; 
			oriProCode = ((OriginProject)file).getCode(); 
		}else{
			OriginProject oriPro = ProjectDBManager.getINSTANCE().searchOnlyOriginProjectByProjectPath(oriProPath);
			this.projectMap.put(oriProPath, oriPro);
			oriProCode = oriPro.getCode();
		}
		Map<String,CopiedProject> resMap = new HashMap<String, CopiedProject>();
		
		CopiedProjectList resList = ProjectDBManager.getINSTANCE().searchOnlyCpProjectByOriProCode(oriProCode);
		if(!resList.getList().isEmpty()){
			for(CopiedProject res : resList.getList()){
				String projectPath = res.getPath();
				String ownerCode = res.getOwner(); 
				if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
				this.projectMap.put(projectPath, res);
				
				resMap.put(ownerCode, res);
			}
		}
		
		return resMap;
	}
	@Override
	public CopiedProject searchOnlyCpProjectByProjectPath(String projectPath) throws DAOException, ParseException, FolderException, ProjectException{
		
		CopiedProject cpPro = ProjectDBManager.getINSTANCE().searchOnlyCpProjectByProjectPath(projectPath);
		if(cpPro!=null){
			if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
			this.projectMap.put(projectPath, cpPro);
		}
		return cpPro;
	}
	@Override
	public CopiedProjectList searchOnlyCpProjectByProjectPath(List<String> projectPathList) throws DAOException, ParseException, FolderException, ProjectException{
		
		CopiedProjectList resList = new CopiedProjectList();
		for(String projectPath : projectPathList){

			CopiedProject cpPro = ProjectDBManager.getINSTANCE().searchOnlyCpProjectByProjectPath(projectPath);
			if(cpPro!=null){
				if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
				this.projectMap.put(projectPath, cpPro);
				resList.addCopiedProject(cpPro);
			}
		}
		return resList;
	}
	@Override
	public CopiedProject searchOnlyCpProjectByProjectCode(String projectCode) throws DAOException, ParseException, FolderException, ProjectException{

		CopiedProject cpPro = ProjectDBManager.getINSTANCE().searchOnlyCpProjectByProjectCode(projectCode);
		if(cpPro!=null){
			String projectPath = cpPro.getPath();
			if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
			this.projectMap.put(projectPath, cpPro);
		}
		return cpPro;
	}
	@Override
	public CopiedProjectList searchOnlyCpProjectByProjectCode(List<String> projectCodeList) throws DAOException, ParseException, FolderException, ProjectException{

		CopiedProjectList resList = new CopiedProjectList();
		for(String projectCode : projectCodeList){

			CopiedProject cpPro = ProjectDBManager.getINSTANCE().searchOnlyCpProjectByProjectCode(projectCode);
			if(cpPro!=null){
				String projectPath = cpPro.getPath();
				if(this.projectMap.containsKey(projectPath))this.projectMap.remove(projectPath);
				this.projectMap.put(projectPath, cpPro);
				resList.addCopiedProject(cpPro);
			}
		}
		return resList;
	}
	
	
	@Override
	public CopiedProjectList searchASelectCopiedProject(List<String> pathList) throws DAOException, ParseException, FolderException, ProjectException {
		
		CopiedProjectList resList = new CopiedProjectList();
		for(String path : pathList){
			System.out.println(path);
			if(!this.projectMap.containsKey(path)){
				CopiedProject pro=ProjectDBManager.getINSTANCE().searchOnlyCpProjectByProjectPath(path);
				if(pro!=null){
					this.projectMap.put(pro.getPath(), pro);
					resList.addCopiedProject(pro);
				}
			}else {
				File file= this.projectMap.get(path);
				if(file instanceof CopiedProject){
					resList.addCopiedProject((CopiedProject)file);
				}
			}
		}
		return resList;
	}
	
	@Override
	public OriginProjectList searchASelectOriginProject(List<String> pathList) throws DAOException, ParseException, FolderException, ProjectException {
		
		OriginProjectList resList = new OriginProjectList();
		for(String path : pathList){
			if(!this.projectMap.containsKey(path)){
				OriginProject pro=ProjectDBManager.getINSTANCE().searchOnlyOriginProjectByProjectPath(path);
				if(pro!=null)this.projectMap.put(pro.getPath(), pro);
				resList.addOriginPorject(pro);
			}else {
				File file= this.projectMap.get(path);
				if(!(file instanceof CopiedProject)){
					resList.addOriginPorject((OriginProject)file);
				}
			}
		}
		if(resList.getList().isEmpty()){
			System.out.println("res OriginProject List is EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEmpty");
		}else System.out.println("res OriginProject List is      nooooooooooooooooooooooottttt   EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEmpty");
		return resList;
	}

	@Override
	public CopiedProject searchASelectCopiedProject(String path) throws DAOException, ParseException, FolderException, ProjectException {

		System.out.println(path);
		if(!this.projectMap.containsKey(path)){
			CopiedProject pro=ProjectDBManager.getINSTANCE().searchOnlyCpProjectByProjectPath(path);
			if(pro!=null){
				this.projectMap.put(pro.getPath(), pro);
				return pro;
			}
		}else {
			File file= this.projectMap.get(path);
			if(file instanceof CopiedProject){
				return ((CopiedProject)file);
			}
		}
		return null;
	}
	@Override
	public OriginProject searchASelectOriginProject(String path) throws DAOException, ParseException, FolderException, ProjectException {
		
		if(!this.projectMap.containsKey(path)){
			OriginProject pro=ProjectDBManager.getINSTANCE().searchOnlyOriginProjectByProjectPath(path);
			if(pro!=null)this.projectMap.put(pro.getPath(), pro);
			return pro;
		}else {
			File file= this.projectMap.get(path);
			if(!(file instanceof CopiedProject)){
				return ((OriginProject)file);
			}
		}
		return null;
	}
	
	@Override
	public Map<String,OriginProjectList> searchSharedOriProjectMCode(String memberCode) throws DAOException, ParseException, FolderException{
		
		Map<String,OriginProjectList> resMap = new HashMap<String, OriginProjectList>();
		OriginProjectList sResList = ProjectDBManager.getINSTANCE().searchOnlyOriginProjectByOwnerCode(memberCode);
		
		OriginProjectList sharedList = new OriginProjectList();
		OriginProjectList normalList = new OriginProjectList();
		for(OriginProject ori : sResList.getList()){
			if(ori.getSharedMemberList().isEmpty())normalList.addOriginPorject(ori);
			else sharedList.addOriginPorject(ori);
			
			if(this.projectMap.containsKey(ori.getPath()))this.projectMap.remove(ori.getPath());
			this.projectMap.put(ori.getPath(), ori);
		}
		
		resMap.put("shared", sharedList);
		resMap.put("normal", normalList);
		return resMap;
	}

	@Override
	public Map<String,List<ProjectSearchedForm>> searchProjectTagList(List<String> tagList) throws DAOException, ParseException, FolderException, ProjectException, MemberException, TeamException {
		// TODO Auto-generated method stub
		
		Map<String,List<ProjectSearchedForm>> resMap = new HashMap<String,List<ProjectSearchedForm>>();
		List<ProjectSearchedForm> personalProList = new ArrayList<ProjectSearchedForm>();
		List<ProjectSearchedForm> teamProList = new ArrayList<ProjectSearchedForm>();
		
		OriginProjectList oriProList = ProjectDBManager.getINSTANCE().searchOnlyOriginProjectByTagNameList(tagList);
		if(!oriProList.getList().isEmpty()){
			for(OriginProject oriProject : oriProList.getList()){
				String oriProCode = oriProject.getCode();
				if(!this.projectMap.containsKey(oriProCode))this.projectMap.put(oriProCode, oriProject);
				
				String ownerCode= oriProject.getOwner(),ownerNickName;
				System.out.println(ownerCode);
				if(new StringTokenizer(ownerCode, "_").nextToken().equals("member")){
					ownerNickName = MemberManager.getINSTANCE().searchMemberCode(ownerCode).getNickName();
					personalProList.add(new ProjectSearchedForm(oriProject.getName(), oriProject.getCode(), ownerCode, ownerNickName, oriProject.getDescription(), oriProject.getMakeDate(), oriProject.getSharedMemberList().size(), "Personal Project"));
				}else {
					ownerNickName = TeamManager.getINSTANCE().searchTeamCode(ownerCode).getName();
					teamProList.add(new ProjectSearchedForm(oriProject.getName(), oriProject.getCode(), ownerCode, ownerNickName, oriProject.getDescription(), oriProject.getMakeDate(), oriProject.getSharedMemberList().size(), "Personal Project"));
				}
			}
			resMap.put("personal", personalProList);
			resMap.put("team", teamProList);
		}
		
		return resMap ;
	}


	@Override
	public boolean checkProjectName(String memberCode, String projectName) throws DAOException, ParseException, FolderException {
		// TODO Auto-generated method stub
		
		return ProjectDBManager.getINSTANCE().checkProjectName(projectName, memberCode);
	}
	
}
