package manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import form.ShareProjectInfo;

public class FileNodeManager {

	private static FileNodeManager INSTANCE;
	private Map<String,Map<String,FileNode>> fileMap;//memberCode, projectCode, Project
	
	static {
		INSTANCE = new FileNodeManager();
	}
	
	private FileNodeManager(){
		this.fileMap = new HashMap<String,Map<String,FileNode>>();
	}

	public static FileNodeManager getINSTANCE() {
		if(INSTANCE==null)INSTANCE = new FileNodeManager();
		return INSTANCE;
	}

	public Map<String, Map<String, FileNode>> getFileMap() {
		return fileMap;
	}

	public void setFileMap(Map<String, Map<String, FileNode>> fileMap) {
		this.fileMap = fileMap;
	}

	public String createProject(String memberCode,String nickName, String name,String description, String path,List<String> tagList,String commitTitle,String commitContent)  throws DAOException, ParseException, SftpException, JSchException, CommitExcetion, FolderException, ProjectException, TagException, FileNotFoundException, IOException, MemberException, FileException, RemoteFileException, TeamException{
		// TODO Auto-generated method stub
		
		String projectCode=ProjectManager.getINSTANCE().createProject(memberCode, nickName, name, description, path, tagList, commitTitle, commitContent);
		MyOriginFile my = new MyOriginFile(projectCode, name, path, "project");
		FileNode node = new FileNode(null, my);
		if(this.fileMap.containsKey(memberCode))(this.fileMap.get(memberCode)).put(projectCode, node);
		else {
			Map<String,FileNode> mapVal = new HashMap<String, FileNode>();
			mapVal.put(projectCode, node);
			this.fileMap.put(memberCode, mapVal);
		}
		return projectCode;
	}
	
	public List<LoadProjectForm> loadProjectMCode(String memberCode) throws DAOException, ParseException, FolderException, IOException, ProjectException{
		
		List<LoadProjectForm> resList = new ArrayList<LoadProjectForm>();
		Map<String, List<File>> resProMap=ProjectManager.getINSTANCE().searchPersonalProjectByOwnerCodeMap(memberCode);
		if(!resProMap.isEmpty()){
			List<File> oriFileList = resProMap.get("originProject");
			if(!oriFileList.isEmpty()){
				List<LoadProjectForm> oriTmpList = this.makeOriginMap(oriFileList, memberCode);
				if(!oriTmpList.isEmpty())resList.addAll(oriTmpList);
			}
			List<File> cpFileList = resProMap.get("copiedProject");
			if(!cpFileList.isEmpty()){
				List<LoadProjectForm> cpTmpList = this.makeCopiedMap(cpFileList, memberCode);
				if(!cpTmpList.isEmpty())resList.addAll(cpTmpList);
			}
		}
		
		return resList;
	}
	
	public List<LoadProjectForm> loadOriginProjectMCode(String memberCode) throws DAOException, ParseException, FolderException, IOException{
		
		System.out.println("FileNodeManager --------- loadOriginProjectMCode");
		List<LoadProjectForm> resList = new ArrayList<LoadProjectForm>();
		OriginProjectList proList=ProjectManager.getINSTANCE().searchOnlyOriginProjectByOwnerCode(memberCode);
		if(!proList.getList().isEmpty()){
			if(!this.fileMap.containsKey(memberCode)){
				return makeOriginMap(proList,memberCode);
			}
			else{
				Map<String,FileNode> projectMap=this.fileMap.get(memberCode);
				List<String> paths = new ArrayList<String>();
				if(!projectMap.isEmpty()){
					OriginProjectList tmpOriProList = new OriginProjectList();
					for(FileNode node:projectMap.values()){
						for(OriginProject pro : proList.getList()){
							if(pro.getPath().equals(node.getPath()))tmpOriProList.addOriginPorject(pro);
						}
					}
					for(OriginProject pro : tmpOriProList.getList()){
						paths.add(pro.getPath());
					}
					
					if(!paths.isEmpty()){
						OriginProjectList proResList = ProjectManager.getINSTANCE().searchOnlyOriginProjectByProjectPath(paths);
						if(!proResList.getList().isEmpty())return makeOriginMap(proResList, memberCode);
						return resList;
					}
				}else{
					for(FileNode pro : projectMap.values()){
						paths.add(pro.getPath());
					}
					if(!paths.isEmpty()){
						OriginProjectList proResList = ProjectManager.getINSTANCE().searchOnlyOriginProjectByProjectPath(paths);
						if(proResList.getList().isEmpty())return resList;
						return makeOriginMap(proResList, memberCode);
					}
				}
				return resList;
			}
		}
		return resList;
	}

	public List<LoadProjectForm> loadCopiedProjectMCode(String memberCode) throws DAOException, ParseException, FolderException, IOException, ProjectException{
		
		System.out.println("FileNodeManager --------- loadOriginProjectMCode");
		List<LoadProjectForm> resList = new ArrayList<LoadProjectForm>();
		CopiedProjectList proList=ProjectManager.getINSTANCE().searchOnlyPersonalCpProjectByOwnerCode(memberCode);
		if(!this.fileMap.containsKey(memberCode)){
			return this.makeCopiedMap(proList, memberCode);
		}
		else{
			Map<String,FileNode> projectMap=this.fileMap.get(memberCode);
			List<String> paths = new ArrayList<String>();
			if(!projectMap.isEmpty()){
				for(FileNode node:projectMap.values()){
					for(CopiedProject pro : proList.getList()){
						if(pro.getPath().equals(node.getPath()))proList.getList().remove(pro);
					}
				}
				for(CopiedProject pro : proList.getList()){
					paths.add(pro.getPath());
				}
				
				if(!paths.isEmpty()){
					CopiedProjectList proResList = ProjectManager.getINSTANCE().searchOnlyCpProjectByProjectPath(paths);
					if(proResList.getList().isEmpty())return resList;
					return makeCopiedMap(proResList, memberCode);
				}
			}else{
				for(FileNode pro : projectMap.values()){
					paths.add(pro.getPath());
				}
				if(!paths.isEmpty()){
					CopiedProjectList proResList = ProjectManager.getINSTANCE().searchOnlyCpProjectByProjectPath(paths);
					if(proResList.getList().isEmpty())return resList;
					return makeCopiedMap(proResList, memberCode);
				}
			}
			return resList;
		}
		
	}
	
	public LoadProjectForm loadOriginProjectMCode(String memberCode,String oriProCode) throws DAOException, ParseException, FolderException, IOException, ProjectException{
		
		System.out.println("FileNodeManager --------- loadOriginProjectMCode");
		LoadProjectForm res = new LoadProjectForm();
		OriginProject oriPro=ProjectManager.getINSTANCE().searchOnlyOriginProjectByProjectCode(oriProCode);
		if(oriPro==null)return null;
		if(!this.fileMap.containsKey(memberCode)){
			return this.makeOriginMap(oriPro, memberCode);
		}else{
			if(!this.fileMap.get(memberCode).containsKey(oriProCode)){
				return this.makeOriginMap(oriPro, memberCode);
			}else{
				FileNode projectNode=this.fileMap.get(memberCode).get(oriProCode);
				if(projectNode==null)return res;
				
				return new LoadProjectForm(oriPro.getName(), oriPro.getCode(), oriPro.getDescription(), oriPro.getMakeDate(), oriPro.getSharedMemberList().size(), "Original Project");
			}
		}
	}

	public LoadProjectForm loadCopiedProjectMCode(String memberCode,String cpProCode) throws DAOException, ParseException, FolderException, IOException, ProjectException{
		
		System.out.println("FileNodeManager --------- loadOriginProjectMCode");
		LoadProjectForm res = new LoadProjectForm();
		CopiedProject cpPro=ProjectManager.getINSTANCE().searchOnlyCpProjectByProjectCode(cpProCode);
		if(cpPro==null)return null;
		if(!this.fileMap.containsKey(memberCode)){
			return this.makeCopiedMap(cpPro, memberCode);
		}
		else{
			if(!this.fileMap.get(memberCode).containsKey(cpProCode)){
				return this.makeCopiedMap(cpPro, memberCode);
			}else{
				FileNode projectNode=this.fileMap.get(memberCode).get(cpProCode);
				if(projectNode==null)return res;
				
				return new LoadProjectForm(cpPro.getName(), cpPro.getCode(), cpPro.getDescription(), cpPro.getMakeDate(), cpPro.getSharedMemberList().size(), "Copied Project");
			}
		}
	}
	
	public Map<String,FileNode> searchProject(String memberCode) throws DAOException, ParseException, FolderException, IOException, ProjectException{
		
		if(!this.fileMap.containsKey(memberCode)){
			this.loadProjectMCode(memberCode);
		}
		return this.fileMap.get(memberCode);
	}
	

	public CopiedProjectList searchCopiedProject(String memberCode) throws DAOException, ParseException, FolderException, IOException, ProjectException {
		
		if(!this.fileMap.containsKey(memberCode)){
			this.loadProjectMCode(memberCode);
		}
		List<String> pathList = new ArrayList<String>();
		if(this.fileMap.containsKey(memberCode)){
			for(FileNode project : this.fileMap.get(memberCode).values()){
				pathList.add(project.getPath());
			}
			if(!pathList.isEmpty())return ProjectManager.getINSTANCE().searchASelectCopiedProject(pathList);
		}
		return new CopiedProjectList();
	}
	
	public CopiedProject searchCopiedProject(String memberCode,String cpProjectCode) throws DAOException, ParseException, FolderException, IOException, ProjectException {
		
		//if project is copied return cpProject else return null
		if(!this.fileMap.containsKey(memberCode)){this.loadProjectMCode(memberCode);}
		
		if(!this.fileMap.get(memberCode).containsKey(cpProjectCode)) this.loadCopiedProjectMCode(memberCode, cpProjectCode);
		
		if(!this.fileMap.containsKey(memberCode))return null;
		if(!this.fileMap.get(memberCode).containsKey(cpProjectCode)) return null;
		
		FileNode node = this.fileMap.get(memberCode).get(cpProjectCode);
		return ProjectManager.getINSTANCE().searchOnlyCpProjectByProjectPath(node.getPath());
	}
	public List<String> searchCopiedProjectCodeList(String memberCode) throws DAOException, ParseException, FolderException, IOException, ProjectException {
		
		List<String> pathList = new ArrayList<String>();
		List<String> codeList = new ArrayList<String>();
		if(!this.fileMap.containsKey(memberCode)){
			this.loadProjectMCode(memberCode);
		}
		if(this.fileMap.containsKey(memberCode)){
			for(FileNode project : this.fileMap.get(memberCode).values()){
				pathList.add(project.getPath());
			}
		}
		
		if(!pathList.isEmpty()){
			CopiedProjectList list = ProjectManager.getINSTANCE().searchASelectCopiedProject(pathList);
			for(CopiedProject project : list.getList()){
				codeList.add(project.getCode());
			}
		}
		
		if(codeList.isEmpty())System.out.println("copiedProject CodeList is empty......");
		else System.out.println("copiedProject CodeList is  not empty......");
		return codeList;
	}

	public OriginProjectList searchOriginProject(String memberCode) throws DAOException, ParseException, FolderException, IOException, ProjectException {
		
		if(!this.fileMap.containsKey(memberCode)){
			this.loadProjectMCode(memberCode);
		}
		List<String> pathList = new ArrayList<String>();
		if(this.fileMap.containsKey(memberCode)){
			for(FileNode project : this.fileMap.get(memberCode).values()){
				pathList.add(project.getPath());
			}
			if(!pathList.isEmpty())return ProjectManager.getINSTANCE().searchASelectOriginProject(pathList); 
		}
		
		return new OriginProjectList();
	}
	public List<String> searchOriginProjectCodeList(String memberCode) throws DAOException, ParseException, FolderException, IOException, ProjectException {
		
		System.out.println("\n          FileNodeManager     searchOriginProjectCodeList - memberCode star line  312");
		List<String> codeList = new ArrayList<String>();
		if(!this.fileMap.containsKey(memberCode)){
			this.loadProjectMCode(memberCode);
			if(!this.fileMap.containsKey(memberCode))return codeList;
		}
		List<String> pathList = new ArrayList<String>();
		
		for(FileNode project : this.fileMap.get(memberCode).values()){
			pathList.add(project.getPath());
		}
		
		if(!pathList.isEmpty()){
			OriginProjectList list = ProjectManager.getINSTANCE().searchASelectOriginProject(pathList);
			for(OriginProject project : list.getList()){
				codeList.add(project.getCode());
			}
			return codeList;
		}
		
		return codeList;
	}
	
	public FileNode searchProject(String memberCode,String projectCode) throws DAOException, ParseException, FolderException, IOException, FileException, ProjectException{
		
		if(!this.fileMap.containsKey(memberCode)){
			this.loadProjectMCode(memberCode);
		}
		if(!this.fileMap.get(memberCode).containsKey(projectCode)){
			this.loadProjectCode(projectCode);
		}
		return this.fileMap.get(memberCode).get(projectCode);
	}
	
	public List<FolderForm> searchParentFolders(String memberCode, String projectCode, List<String> folderCodes)throws DAOException, ParseException, FolderException, IOException, ProjectException, FileException {
		
		// TODO Auto-generated method stub
		
		List<FolderForm> resList = new ArrayList<FolderForm>();
		List<FolderForm> realResList = new ArrayList<FolderForm>();
		
		if(!this.fileMap.containsKey(memberCode)){
			this.loadProjectMCode(memberCode);
		}

		if(!this.fileMap.get(memberCode).containsKey(projectCode)){
			//check is originproject of copied project 
			OriginProject pro=ProjectManager.getINSTANCE().searchOnlyOriginProjectByProjectCode(projectCode);
			if(pro==null)return resList;
			this.loadProjectCode(pro);
		}

		FileNode node=this.fileMap.get(memberCode).get(projectCode);//get whole project tree
		
		FolderForm form = new FolderForm("folder", node.getCode(), node.getName());
		resList.add(form);
		
		int cnt = folderCodes.size();
		for(int i=1;i<cnt;i++){
			node = node.getChild(folderCodes.get(i));
			FolderForm form1 = new FolderForm(node.getType(), node.getCode(), node.getName());
			resList.add(form1);
		}
		
		int n = resList.size();
		for(int i=n-1;i>=0;i--){
			realResList.add(resList.get(i));
		}
		return realResList;
		
	}

	public Map<String,List<FolderForm>> searchFoldersFilesMap(String memberCode, String projectCode, List<String> pFolders) throws IOException, DAOException, ParseException, FolderException, FileException, ProjectException, SftpException, JSchException, TeamException, RemoteFileException, MemberException{
		
		if(!this.fileMap.containsKey(memberCode))this.loadProjectMCode(memberCode);
		
		if(!this.fileMap.get(memberCode).containsKey(projectCode)) loadProjectCode(projectCode);
		
		Map<String,FileNode> memProjectMap = this.fileMap.get(memberCode);
		FileNode node = memProjectMap.get(projectCode);
		int leng = pFolders.size();
		for(int i=1;i<leng;i++){
			node=node.getChild(pFolders.get(i));
		}
	
		if(!node.getChilds().isEmpty()){
			return this.makeFolderFormListByParentFileNode(memberCode, projectCode, node);
		}else{System.out.println("project is empty "+projectCode);}
		
		return new HashMap<String, List<FolderForm>>();
	}
	

	public List<FolderPathForm> searchOnlyOriginFoldersMap(String memberCode, String oriProjectCode, List<String> pFolders) throws IOException, DAOException, ParseException, FolderException, FileException, ProjectException, SftpException, JSchException, TeamException, RemoteFileException, MemberException{
		
		List<FolderPathForm> resList = new ArrayList<FolderPathForm>();
		
		if(!this.fileMap.containsKey(memberCode))this.loadProjectMCode(memberCode);
		if(!this.fileMap.get(memberCode).containsKey(oriProjectCode)) loadProjectCode(oriProjectCode);
		Map<String,FileNode> memProjectMap = this.fileMap.get(memberCode);
		FileNode node = memProjectMap.get(oriProjectCode);
	
		String oriProCode = null;
		String ownerCode = null;
		//get origin
		CopiedProject cpProject = ProjectManager.getINSTANCE().searchOnlyCpProjectByProjectCode(node.getPath());
		if(cpProject!=null){
			oriProCode = cpProject.getOriginCode();
			ownerCode = cpProject.getOriginOwnerCode();
			if(!this.fileMap.containsKey(ownerCode))this.loadProjectMCode(ownerCode);
			if(!this.fileMap.get(ownerCode).containsKey(oriProCode)) loadProjectCode(oriProCode);
		}else {
			oriProCode = oriProjectCode;
			ownerCode = memberCode;
		}
		
		FileNode oriNode = this.fileMap.get(ownerCode).get(oriProCode);
		if(pFolders.size()!=1){
			for(int i=1;i<pFolders.size();i++){
				String code = pFolders.get(i);
				oriNode = oriNode.getChild(code);
			}
		}
		
		FileNode chFolderNode;
		if(oriNode!=null){
			for(String code : oriNode.getChildFolderList()){
				chFolderNode = oriNode.getChild(code);
				resList.add(new FolderPathForm(chFolderNode.getType(), chFolderNode.getCode(), chFolderNode.getName(), chFolderNode.getPath()));
			}
		}
		
		return resList;
	}
	
	public Map<String, List<FolderForm>> searchFolders(String memberCode, String projectCode, List<String> pFolders) throws IOException, DAOException, ParseException, FolderException, FileException, ProjectException, SftpException, JSchException, TeamException, RemoteFileException, MemberException{
		
		List<FolderForm> resList = new ArrayList<FolderForm>();
		
		if(!this.fileMap.containsKey(memberCode))this.loadProjectMCode(memberCode);
		
		if(!this.fileMap.get(memberCode).containsKey(projectCode)) loadProjectCode(projectCode);
		
		Map<String,FileNode> memProjectMap = this.fileMap.get(memberCode);
		FileNode node = memProjectMap.get(projectCode);
		int leng = pFolders.size();
		for(int i=1;i<leng;i++){
			node=node.getChild(pFolders.get(i));
		}
	
		if(!node.getChilds().isEmpty()){
			return this.makeFolderFormListByParentFileNode(memberCode, projectCode, node);
		}else{System.out.println("project is empty "+projectCode);}
		return new HashMap<String, List<FolderForm>>();
	}
	

	private List<String> searchProjectFiles(FileNode node,List<String> list){
		List<String> folderList = node.getChildFolderList();
		List<String> fileList = node.getChildFileList();
		
		if(!folderList.isEmpty()){
			for(String folderCode : folderList){
				FileNode chNode = node.getChild(folderCode);
				this.searchProjectFiles(chNode, list);
			}
		}
		if(!fileList.isEmpty()){
			list.addAll(fileList);
		}
		
		return list;
	}
	
	private List<String> searchProjectFolders(FileNode node,List<String> list){
		System.out.print("     ============== start  find folderList   current node path : "+node.getPath());
		if(!node.getChildFolderList().isEmpty()){
			for(String folderCode : node.getChildFolderList()){
				FileNode chNode = node.getChild(folderCode);
				System.out.println("* chPath : "+chNode.getPath());
				list.addAll(this.searchProjectFolders(chNode,new ArrayList<String>()));
				if(!list.isEmpty()){
					System.out.println("add");
					//fileList.addAll(list);
					for(String code : list){
						System.out.print(code+"  "+code);
					}
					System.out.println("ch aned...*");
				}
			}
			list.addAll(node.getChildFolderList());
		}
		for(String code : list){
			System.out.print(code+"  ");
		}
		System.out.println("return!!!===========");
		return list;
	}
	
	public Map<String,Map<String,List<String>>> searchMemProjectChilds(String memberCode) throws DAOException, ParseException, FolderException, ProjectException, IOException{
		
		if(!this.fileMap.containsKey(memberCode))this.loadProjectMCode(memberCode);
		Map<String,Map<String,List<String>>> resMap = new HashMap<String, Map<String,List<String>>>();
		
		Map<String,FileNode> projectMap= this.fileMap.get(memberCode);
		if(projectMap.isEmpty())return resMap;
		
		for(String projectCode : projectMap.keySet()){
			Map<String,List<String>> proVal = new HashMap<String, List<String>>();
			proVal.put("folder", this.searchProjectFolders(projectMap.get(projectCode), new ArrayList<String>()));
			proVal.put("file", this.searchProjectFiles(projectMap.get(projectCode), new ArrayList<String>()));
			resMap.put(projectCode, proVal);
		}
		return resMap;
	}
	public Map<String,Map<String,List<String>>> searchMemCopiedProjectChilds(String memberCode) throws DAOException, ParseException, FolderException, ProjectException, IOException{
		
		if(!this.fileMap.containsKey(memberCode))this.loadProjectMCode(memberCode);
		Map<String,Map<String,List<String>>> resMap = new HashMap<String, Map<String,List<String>>>();
		
		Map<String,FileNode> projectMap= this.fileMap.get(memberCode);
		if(projectMap.isEmpty())return resMap;
		
		CopiedProjectList cpList = this.searchCopiedProject(memberCode);
		for(CopiedProject cpProject : cpList.getList()){
			String projectCode = cpProject.getCode();
			Map<String,List<String>> proVal = new HashMap<String, List<String>>();
			proVal.put("folder", this.searchProjectFolders(projectMap.get(projectCode), new ArrayList<String>()));
			proVal.put("file", this.searchProjectFiles(projectMap.get(projectCode), new ArrayList<String>()));
			resMap.put(projectCode, proVal);
		}
		return resMap;
	}
	public Map<String,Map<String,List<String>>> searchMemOriginProjectChilds(String memberCode) throws DAOException, ParseException, FolderException, ProjectException, IOException{
		
		if(!this.fileMap.containsKey(memberCode)){
			this.loadProjectMCode(memberCode);
			if(this.fileMap.containsKey(memberCode)){
				Map<String,Map<String,List<String>>> resMap = new HashMap<String, Map<String,List<String>>>();
				
				Map<String,FileNode> projectMap= this.fileMap.get(memberCode);
				if(projectMap.isEmpty())return resMap;
				
				OriginProjectList oriProList = this.searchOriginProject(memberCode);
				for(OriginProject oriPro : oriProList.getList()){
					String projectCode = oriPro.getCode();
					Map<String,List<String>> proVal = new HashMap<String, List<String>>();
					proVal.put("folder", this.searchProjectFolders(projectMap.get(projectCode), new ArrayList<String>()));
					proVal.put("file", this.searchProjectFiles(projectMap.get(projectCode), new ArrayList<String>()));
					resMap.put(projectCode, proVal);
				}
				return resMap;
			}
		}
		return new HashMap<String,Map<String,List<String>>>();
	}
	
	public Map<String,List<String>> searchMemProjectChilds(String memberCode,String projectCode) throws DAOException, ParseException, FolderException, ProjectException, IOException{
		
		if(!this.fileMap.containsKey(memberCode))this.loadProjectMCode(memberCode);
		Map<String,List<String>> resMap = new HashMap<String,List<String>>();
		
		Map<String,FileNode> projectMap= this.fileMap.get(memberCode);
		if(projectMap.isEmpty())return resMap;
		FileNode project = projectMap.get(projectCode);
		if(project==null)return resMap;

		Map<String,List<String>> proVal = new HashMap<String, List<String>>();
		proVal.put("folder", this.searchProjectFolders(project, new ArrayList<String>()));
		proVal.put("file", this.searchProjectFiles(project, new ArrayList<String>()));

		return proVal;
	}
	
	
	private boolean loadProjectCode(String projectCode) throws IOException, DAOException, ParseException, FolderException, FileException, ProjectException{
		
		//load origin project
		File pro=ProjectManager.getINSTANCE().searchProjectByProjectCode(projectCode);
		if(pro!=null){
			if(pro instanceof CopiedProject)return this.loadProjectCode((CopiedProject)pro);
			else return this.loadProjectCode((OriginProject)pro);
		}
		return false;
	}
	
 	private boolean loadProjectCode(OriginProject pro) throws IOException, DAOException, ParseException, FolderException, FileException{
		
		Map<String,MyOriginFile> fileMap = new HashMap<String, MyOriginFile>();
		List<File> folderList = FolderManager.getINSTANCE().searchAllFoldersByPFPath(pro.getPath());
		List<File> fileList = FileManager.getINSTANCE().searchAllFileByFPath(pro.getPath());
		MyOriginFile myfolder = new MyOriginFile(pro.getCode(), pro.getName(), pro.getPath(), "project");
		myfolder.setChildFileList(pro.getChildFileList());
		myfolder.setChildFolderList(pro.getChildFolderList());
		
		if(!folderList.isEmpty()){
			for(File folder : folderList){
				OriginFolder oriFolder = (OriginFolder)folder;
				MyOriginFile myFold = new MyOriginFile(oriFolder.getCode(), oriFolder.getName(), oriFolder.getPath(), "folder");
				myFold.setChildFileList(oriFolder.getChildFileList());
				myFold.setChildFolderList(oriFolder.getChildFolderList());
				fileMap.put(oriFolder.getCode(), myFold);
			}	
		}
		
		if(!fileList.isEmpty()){
		for(File file : fileList){
			OriginFile oriFile = (OriginFile)file;
			MyOriginFile myFile = new MyOriginFile(oriFile.getCode(), oriFile.getName(), oriFile.getPath(), "file");
			System.out.println(myFile.getCode());
			fileMap.put(oriFile.getCode(), myFile);
		}
		}
		
		
		fileMap.put(pro.getCode(), myfolder);
		FileNode node = new FileNode(null, myfolder);
		node.initFileNode(fileMap);
		
		if(this.fileMap.containsKey(pro.getOwner())){
			Map<String,FileNode> memPro=this.fileMap.get(pro.getOwner());
			memPro.put(pro.getCode(), node);
		}else{
			Map<String,FileNode> projectMap = new HashMap<String, FileNode>();
			projectMap.put(pro.getCode(), node);
			this.fileMap.put(pro.getOwner(), projectMap);
		}
		
		return true;
	}
 	

 	private boolean loadProjectCode(CopiedProject pro) throws IOException, DAOException, ParseException, FolderException, FileException{
		
		Map<String,MyOriginFile> fileMap = new HashMap<String, MyOriginFile>();
		List<File> folderList = FolderManager.getINSTANCE().searchAllFoldersByPFPath(pro.getPath());
		List<File> fileList = FileManager.getINSTANCE().searchAllFileByFPath(pro.getPath());
		MyOriginFile myfolder = new MyOriginFile(pro.getCode(), pro.getName(), pro.getPath(), "project");
		myfolder.setChildFileList(pro.getChildFileList());
		myfolder.setChildFolderList(pro.getChildFolderList());
		
		if(!folderList.isEmpty()){
			for(File folder : folderList){
				OriginFolder oriFolder = (OriginFolder)folder;
				MyOriginFile myFold = new MyOriginFile(oriFolder.getCode(), oriFolder.getName(), oriFolder.getPath(), "folder");
				myFold.setChildFileList(oriFolder.getChildFileList());
				myFold.setChildFolderList(oriFolder.getChildFolderList());
				fileMap.put(oriFolder.getCode(), myFold);
			}	
		}
		
		if(!fileList.isEmpty()){
		for(File file : fileList){
			OriginFile oriFile = (OriginFile)file;
			MyOriginFile myFile = new MyOriginFile(oriFile.getCode(), oriFile.getName(), oriFile.getPath(), "file");
			System.out.println(myFile.getCode());
			fileMap.put(oriFile.getCode(), myFile);
		}
		}
		

		myfolder.setChildFileList(pro.getChildFileList());
		myfolder.setChildFolderList(pro.getChildFolderList());
		fileMap.put(pro.getCode(), myfolder);
		FileNode node = new FileNode(null, myfolder);
		node.initFileNode(fileMap);
		
		if(this.fileMap.containsKey(pro.getOwner())){
			Map<String,FileNode> memPro=this.fileMap.get(pro.getOwner());
			memPro.put(pro.getCode(), node);
		}else{
			Map<String,FileNode> projectMap = new HashMap<String, FileNode>();
			projectMap.put(pro.getCode(), node);
			this.fileMap.put(pro.getOwner(), projectMap);
		}
		
		return true;
	}
	
	
 	public Map<String,List<FolderForm>> searchSiblingFolders(String memberCode, String projectCode, List<String> pFolders)throws DAOException, ParseException, FolderException, IOException, FileException, ProjectException, SftpException, JSchException, TeamException, RemoteFileException, MemberException {

 		// TODO Auto-generated method stub
 		List<FolderForm> resList = new ArrayList<FolderForm>();
		
		if(!this.fileMap.containsKey(memberCode))this.loadProjectMCode(memberCode);
		
		if(!this.fileMap.get(memberCode).containsKey(projectCode)) loadProjectCode(projectCode);
		
		Map<String,FileNode> memProjectMap = this.fileMap.get(memberCode);
		FileNode node = memProjectMap.get(projectCode);
		int leng = pFolders.size()-1;
		for(int i=1;i<leng;i++){
			node=node.getChild(pFolders.get(i));
		}
	
		if(!node.getChilds().isEmpty()){
			return this.makeFolderFormListByParentFileNode(memberCode, projectCode, node);
		}
		return new HashMap<String, List<FolderForm>>();
	}

 	public Map<String,List<FolderForm>> searchSiblingFoldersMap(String memberCode, String projectCode, List<String> pFolders)throws DAOException, ParseException, FolderException, IOException, FileException, ProjectException, SftpException, JSchException, TeamException, RemoteFileException, MemberException {
 		// TODO Auto-generated method stub
 		
		if(!this.fileMap.containsKey(memberCode))this.loadProjectMCode(memberCode);
		
		if(!this.fileMap.get(memberCode).containsKey(projectCode)) loadProjectCode(projectCode);
		
		Map<String,FileNode> memProjectMap = this.fileMap.get(memberCode);
		FileNode node = memProjectMap.get(projectCode);
		int leng = pFolders.size()-1;
		for(int i=1;i<leng;i++){
			node=node.getChild(pFolders.get(i));
		}
		System.out.println("current Point  :: "+node.getPath());
	
		if(!node.getChilds().isEmpty()){
			return this.makeFolderFormListByParentFileNode(memberCode, projectCode, node);
		}
		return new HashMap<String, List<FolderForm>>();
	}
 	
 	public String createOriginFolderByPFCode(String memberCode,String memberName, String projectCode, String folderName, String description,List<String> parentFolderCode,String CommitTitle,String commitContent) throws DAOException, ParseException, SftpException, JSchException, CommitExcetion, FolderException, IOException, FileException, ProjectException, MemberException, RemoteFileException, TeamException {
		
 		// TODO Auto-generated method stub
 		List<FolderForm> resList = new ArrayList<FolderForm>();
		
		if(!this.fileMap.containsKey(memberCode))this.loadProjectMCode(memberCode);
		
		if(!this.fileMap.get(memberCode).containsKey(projectCode)) loadProjectCode(projectCode);
		
		Map<String,FileNode> memProjectMap = this.fileMap.get(memberCode);
		FileNode node = memProjectMap.get(projectCode);
		int leng = parentFolderCode.size();
		for(int i=1;i<leng;i++){
			node=node.getChild(parentFolderCode.get(i));
		}
	
		String path = node.getPath()+"\\"+folderName;
		if(!node.getChilds().isEmpty()){
			for(FileNode chNode : node.getChilds().values()){
				if(chNode.getName().equals(folderName))throw new FolderException("This name has already used...");
			}
		}
		
		OriginFolder folder=FolderManager.getINSTANCE().createOriginFolderByPFCode(memberCode, memberName, folderName, description, parentFolderCode.get(leng-1));
		node.createChildFolder(new MyOriginFile(folder.getCode(), folderName, path, "folder"));
		System.out.println(path+"     is import..."+node.getChild(folder.getCode()).getName());
		
		//insert Commit
		
		CommitInfoManager.getINSTANCE().putCommitInfo(memberCode, projectCode, new CommitInfo(folder.getCode(), "create", commitContent, folder.getMakeDate(), memberCode, false, description, CommitTitle));
		return folder.getCode();
	}
 	
 	public ShareProjectInfo createCopiedProject(String memberCode, String ownerCode, String projectCode) throws DAOException, ParseException, SftpException, JSchException, FolderException, IOException, FileException, ProjectException, MemberException, TeamException, RemoteFileException, CommitExcetion{
		
 		// TODO Auto-generated method stub
 		List<FolderForm> resList = new ArrayList<FolderForm>();
		
		if(!this.fileMap.containsKey(memberCode))this.loadProjectMCode(memberCode);
		if(!this.fileMap.containsKey(ownerCode))this.loadProjectMCode(ownerCode);
		
		if(!this.fileMap.get(ownerCode).containsKey(projectCode)) loadProjectCode(projectCode);
		
		FileNode project = this.fileMap.get(ownerCode).get(projectCode);
		
		FileNode resNode = ProjectManager.getINSTANCE().copyOriginProject(project, memberCode, ownerCode);
		
		if(resNode!=null){
			System.out.println("projectCode : "+resNode.getCode());
			if(!this.fileMap.containsKey(memberCode)){
				Map<String,FileNode> proNode = new HashMap<String, FileNode>();
				proNode.put(resNode.getCode(), resNode);
				this.fileMap.put(memberCode, proNode);
			}else{this.fileMap.get(memberCode).put(resNode.getCode(), resNode);}
			System.out.println(this.fileMap.get(memberCode).keySet().iterator().next());
			CooperatorMessageManager.getINSTANCE().createProCooperatorMessage(ownerCode, projectCode, memberCode);
			ShareProjectInfo res = new ShareProjectInfo(resNode.getName(),resNode.getCode());
			return res;
		}
		return null;
	}

 	public String createFileCode(String memberCode,  String projectCode, List<String> folderCodes,String fileName , String content) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException, FolderException, ProjectException{
		// TODO Auto-generated method stub
 		
 		if(!this.fileMap.containsKey(memberCode))this.loadProjectMCode(memberCode);
		
		if(!this.fileMap.get(memberCode).containsKey(projectCode)) loadProjectCode(projectCode);
		
		Map<String,FileNode> memProjectMap = this.fileMap.get(memberCode);
		FileNode node = memProjectMap.get(projectCode);
		int leng = folderCodes.size()-1;
		for(int i=1;i<leng;i++){
			node=node.getChild(folderCodes.get(i));
		}
		
		if(node.getChilds().containsKey(fileName))throw new FileException("This file name has already used");
 		
		System.out.println("createFileCode");
		OriginFile file= FileManager.getINSTANCE().createFileCode(memberCode, fileName, node.getCode(),content);
		
		node.createChildFile(new MyOriginFile(file.getCode(), file.getName(), file.getPath(), "file"));
		
		return file.getCode();
	}
 	
 	public String createFileCode(String memberCode,  String projectCode, List<String> folderCodes,String fileName , String content, String commitTitle, String commitContent) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException, FolderException, CommitExcetion, ProjectException, MemberException, TeamException{
		// TODO Auto-generated method stub

 		if(!this.fileMap.containsKey(memberCode))this.loadProjectMCode(memberCode);
		
		if(!this.fileMap.get(memberCode).containsKey(projectCode)) loadProjectCode(projectCode);
		
		Map<String,FileNode> memProjectMap = this.fileMap.get(memberCode);
		FileNode node = memProjectMap.get(projectCode);
		int leng = folderCodes.size();
		for(int i=1;i<leng;i++){
			node=node.getChild(folderCodes.get(i));
		}
		
		if(node.getChilds().containsKey(fileName))throw new FileException("This file name has already used");
 		
		System.out.println("createFileCode");
		OriginFile file= FileManager.getINSTANCE().createCommitFileCode(memberCode, fileName, node.getCode(),content);
		node.createChildFile(new MyOriginFile(file.getCode(), file.getName(), file.getPath(), "file"));
		
		//insert commit
		CommitInfoManager.getINSTANCE().putCommitInfo(memberCode, projectCode, new CommitInfo(file.getCode(), "create", commitContent, file.getMakeDate(), memberCode, false, null, commitTitle));
		return file.getCode();
	}
 	
 	public String searchFileContent(String memberCode, String projectCode, List<String> pFolders,String fileCode) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FolderException, FileException, ProjectException {
		// TODO Auto-generated method stub
		System.out.println("\n     FileNodeManager  : searchFileContent  line  796");
		if(!this.fileMap.containsKey(memberCode))this.loadProjectMCode(memberCode);
		
		if(!this.fileMap.get(memberCode).containsKey(projectCode)) loadProjectCode(projectCode);
		
		Map<String,FileNode> memProjectMap = this.fileMap.get(memberCode);
		FileNode node = memProjectMap.get(projectCode);

		int leng = pFolders.size();
		for(int i=1;i<leng;i++){
			node=node.getChild(pFolders.get(i));
		}
		
		node=node.getChild(fileCode);
		System.out.println(node.getPath()+"  "+node.getCode());
		OriginFile file=FileManager.getINSTANCE().searchOnlyOriginFilePathContent(node.getPath());
		if(file!=null)return file.getContent();
		else{
			CopiedFile cpFile = FileManager.getINSTANCE().searchOnlyCpFileContent(node.getPath());
			if(cpFile!=null)return cpFile.getContent();
		}		
		return null;
	}
 	
 	public boolean modifyFileContent(String memberCode, String storageCode,String projectCode, List<String> folderCodes, String fileCode,String newContent, String commitTitle, String commitContent)throws FileNotFoundException, LoginException, DAOException,
			SftpException, IOException, JSchException, FileException,ParseException, RemoteFileException, CommitExcetion, FolderException, ProjectException, TeamException, MemberException {
		// TODO Auto-generated method stub
		//simply save with commit content
 		System.out.println("\nFileNodeManager  :: modifyFileContent start  @");
 		if(!this.fileMap.containsKey(memberCode))this.loadProjectMCode(memberCode);
		
		if(!this.fileMap.get(memberCode).containsKey(projectCode)) loadProjectCode(projectCode);
		
		Map<String,FileNode> memProjectMap = this.fileMap.get(memberCode);
		FileNode node = memProjectMap.get(projectCode);
		int leng = folderCodes.size();
		for(int i=1;i<leng;i++){
			node=node.getChild(folderCodes.get(i));
		}
		
		node=node.getChild(fileCode);
		CommitInfo commitInfo = FileManager.getINSTANCE().modifyFileContent(memberCode, storageCode, projectCode, folderCodes.get(leng-1), fileCode,node.getPath(), newContent,commitTitle,commitContent);
		if(commitInfo==null)return false;
		return CommitInfoManager.getINSTANCE().putOnlyCommitInfo(memberCode, projectCode, commitInfo);
	}
 	
 	public String checkFileDiff(String memberCode, String oriProjectCode, String commiter, String commitCode,String msgCode) throws DAOException, ParseException, IOException, FolderException, FileException, ProjectException, SftpException, JSchException, RemoteFileException, MemberException, TeamException{

 		/*
 		 * return
 		 * 
 		 * "needToSelectpath"
 		 * "justCopy"
 		 * "noCopy"
 		 * "justCopyContent"
 		 * 
 		 * */
 		
 		System.out.println("\n       FileNodeManager   checkFileDiff() start!   line    904");
 		//merge file!!!!
 		if(!this.fileMap.containsKey(memberCode))this.loadProjectMCode(memberCode);
		if(!this.fileMap.get(memberCode).containsKey(oriProjectCode)) loadProjectCode(oriProjectCode);
		
		CommitInfo commitInfo = CommitInfoManager.getINSTANCE().searchCommitCode(commitCode);
		CommitInfo newCommit=null;		

		CommitMessage commitMsg = MessageManager.getINSTANCE().getCommitMessage(msgCode);
		String commiterNickName = MemberManager.getINSTANCE().searchMemberCode(commiter).getNickName();
		String memberNickName = MemberManager.getINSTANCE().searchMemberCode(memberCode).getNickName();
		
		System.out.println("objCode : "+commitInfo.getObjectCode());
		
		//just search is there any new file or folder that team member create
		Map<String,List<CommitInfo>> resMap = MessageManager.getINSTANCE().searchCommitMsgCommitList(msgCode);
		List<CommitInfo> tmpCommitInfoList = new ArrayList<CommitInfo>();
		if(!resMap.isEmpty()){
			tmpCommitInfoList = resMap.get(commitInfo.getObjectCode());
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
									String realFolderPath = parentFolderPath.replaceFirst(commiterNickName, memberNickName);
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
									String realFolderPath = folderPath.replaceFirst(commiterNickName, memberNickName);
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
		
		List<CommitInfo> commitInfoList = new ArrayList<CommitInfo>();
		if(!tmpCommitInfoList.isEmpty()){
			//test code
			tmpCommitInfoList = CommitInfoManager.getINSTANCE().searchCommitInfoContentDetails(commitInfo.getObjectCode(), tmpCommitInfoList);
			System.out.println("00000000000000000000000000000000000000000000000000000000000000000000000000000");
			System.out.println("tmpList sieze : "+tmpCommitInfoList.size());
			for(CommitInfo co : tmpCommitInfoList){
				System.out.println("00000000000000000000000000000000000000000000000000000000000000000000000000000");
				System.out.println(co.getCode());
				System.out.println("비교 대상  :   "+co.getDate());
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
				System.out.println("비교 대상  :   "+co.getDate());
				if(co.getOriginFileDescriptioin()!=null)System.out.println(co.getOriginFileDescriptioin());
				System.out.println("------------------------------------------------------------------------------");
			}
			if(FileManager.getINSTANCE().checkFileDiff(oriFile.getCode(), commitInfoList))return "justCopyContent";
			else return "noCopy";
		}
		else return "noCopy";
 	}


 	public boolean mergeNewObj(String memberCode, String oriProjectCode, String commiter, String commitCode,String msgCode,List<String> folderCodes)throws FileNotFoundException, LoginException, DAOException,
			SftpException, IOException, JSchException, FileException,ParseException, RemoteFileException, CommitExcetion, FolderException, ProjectException, TeamException, MemberException {
		// TODO Auto-generated method stub
		
 		//create New File into really New Path
 		//for just copy
 		System.out.println("\n         FileNodeManager   mergeNewObj() start!   line    989");
 		//merge file!!!!
 		if(!this.fileMap.containsKey(memberCode))this.loadProjectMCode(memberCode);
		if(!this.fileMap.get(memberCode).containsKey(oriProjectCode)) loadProjectCode(oriProjectCode);
		
		//get msg
		CommitInfo commitInfo = CommitInfoManager.getINSTANCE().searchCommitCode(commitCode);
		String objCode = commitInfo.getObjectCode();
		StringTokenizer tokens = new StringTokenizer(objCode, "_");
		boolean isFolder = false;
		if(tokens.nextToken().equals("folder"))isFolder=true;
		String objPath = null;
		
		FileNode proNode = this.fileMap.get(memberCode).get(oriProjectCode);
		String proName = proNode.getName();
		
		if(folderCodes.size()>1){
			for(int i=1;i<folderCodes.size();i++){
				String folderCode = folderCodes.get(i);
				System.out.println("i'm searching FolderPath!!  : "+folderCode);
				proNode = proNode.getChild(folderCode);
			}
		}
		
		String pFolderPath = proNode.getPath();
		System.out.println("parentFolderpath : "+pFolderPath);
		
		
		if(isFolder){
			OriginFolder folder = FolderManager.getINSTANCE().justSearchFolderCode(objCode);
			objPath = folder.getPath();
			
			Map<OriginFolder,CommitInfo> resMap = FolderManager.getINSTANCE().copyFolder(objCode, commitCode, commiter, pFolderPath, memberCode);
			if(!resMap.isEmpty()){
				OriginFolder oriFolder = resMap.keySet().iterator().next();
				proNode.createChildFolder(new MyOriginFile(oriFolder.getCode(), oriFolder.getName(), oriFolder.getPath(), "folder"));
				CommitInfo resCommitInfo = resMap.get(oriFolder);
				return CommitInfoManager.getINSTANCE().putCommitInfo(memberCode, proNode.getCode(), resCommitInfo);
			}
		}
		else {
			OriginFile oriFile = FileManager.getINSTANCE().justSearchOriginFileCode(objCode);
			objPath = oriFile.getPath();
			
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
			
			Map<OriginFile,CommitInfo> resMap = FileManager.getINSTANCE().mergeNewFileContent(memberCode, pFolderPath, newContent, objCode, commiter, commitCode, commitCodeList);
			if(!resMap.isEmpty()){
				OriginFile resOriFile = resMap.keySet().iterator().next();
				proNode.createChildFolder(new MyOriginFile(resOriFile.getCode(), resOriFile.getName(), resOriFile.getPath(), "file"));
				CommitInfo resCommitInfo = resMap.get(resOriFile);
				return CommitInfoManager.getINSTANCE().putCommitInfo(memberCode, proNode.getCode(), resCommitInfo);
				//CommitInfoManager.getINSTANCE().putOnlyCommitInfo(memberCode, oriProjectCode, commitInfo);
			}
		}
		
		return false;
	}
 	public boolean mergeNewObj(String memberCode, String oriProjectCode, String commiter, String commitCode,String msgCode)throws FileNotFoundException, LoginException, DAOException,
			SftpException, IOException, JSchException, FileException,ParseException, RemoteFileException, CommitExcetion, FolderException, ProjectException, TeamException, MemberException {
		// TODO Auto-generated method stub
		
 		//for just copy
 		System.out.println("\n       FileNodeManager   mergeNewObj() start!   line    1048");
 		//merge file!!!!
 		if(!this.fileMap.containsKey(memberCode))this.loadProjectMCode(memberCode);
		if(!this.fileMap.get(memberCode).containsKey(oriProjectCode)) loadProjectCode(oriProjectCode);
		
		//get msg
		CommitInfo commitInfo = CommitInfoManager.getINSTANCE().searchCommitCode(commitCode);
		String objCode = commitInfo.getObjectCode();
		StringTokenizer tokens = new StringTokenizer(objCode, "_");
		boolean isFolder = false;
		if(tokens.nextToken().equals("folder"))isFolder=true;
		String objPath = null;
		
		String memNickName = MemberManager.getINSTANCE().searchMemberCode(memberCode).getNickName();
		String commiterNick = MemberManager.getINSTANCE().searchMemberCode(commiter).getNickName();
		String objName = null;
		String realPFolderPath=null;
		if(isFolder){
			OriginFolder oriFolder = FolderManager.getINSTANCE().justSearchFolderCode(objCode);
			objPath = oriFolder.getPath();
			objName = oriFolder.getName();
			CopiedFolder cpFolder = FolderManager.getINSTANCE().searchOnlyCopiedFolderPath(oriFolder.getParentFolderPath());
			
			realPFolderPath = FolderManager.getINSTANCE().justSearchFolderCode(cpFolder.getOriginCode()).getPath();
			Map<OriginFolder,CommitInfo> resMap = FolderManager.getINSTANCE().copyFolder(objCode, commitCode, commiter, realPFolderPath, memberCode);
			if(!resMap.isEmpty()){return this.insertFolderMap(resMap, memNickName, memberCode);}
		}
		else {
			OriginFile oriFile = FileManager.getINSTANCE().justSearchOriginFileCode(objCode);
			objPath = oriFile.getPath();
			objName = oriFile.getName();
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
			if(!resMap.isEmpty()){return this.insertFileMap(resMap, memNickName, memberCode);}
		}
		System.out.println("OriginParentPath   :  "+realPFolderPath);
		
		return false;
	}

 	public boolean mergeFileContent(String memberCode, String oriProjectCode, String commiter, String commitCode,String msgCode)throws FileNotFoundException, LoginException, DAOException,
			SftpException, IOException, JSchException, FileException,ParseException, RemoteFileException, CommitExcetion, FolderException, ProjectException, TeamException, MemberException {
		// TODO Auto-generated method stub
		
 		//for just copy
 		System.out.println("\n\n       FileNodeManager   mergeFileContent() start!   line    1135");
 		//merge file!!!!
 		if(!this.fileMap.containsKey(memberCode))this.loadProjectMCode(memberCode);
		if(!this.fileMap.get(memberCode).containsKey(oriProjectCode)) loadProjectCode(oriProjectCode);
		
		//get msg
		CommitInfo commitInfo = CommitInfoManager.getINSTANCE().searchCommitCode(commitCode);
		String objCode = commitInfo.getObjectCode();
		CopiedFile cpFile = FileManager.getINSTANCE().searchOnlyCpFileCode(objCode);
		String objPath = cpFile.getPath();
		System.out.println("objCode  :     "+objCode+"  objPath : "+objPath);
		
		String newContent = CommitInfoManager.getINSTANCE().searchCommitOriginFileContent(commitCode);
		if(newContent==null)newContent = ((OriginFile)FileManager.getINSTANCE().searchFileContentPath(objPath)).getContent();
		List<CommitInfo> tmpCommitInfoList = MessageManager.getINSTANCE().searchCommitMsgCommitList(msgCode, objCode);
		List<String> commitCodeList = new ArrayList<String>();
		
		if(!tmpCommitInfoList.isEmpty()){
			int i=0,cnt = tmpCommitInfoList.size();
			for(i=0;i<cnt;i++){
				if(tmpCommitInfoList.get(i).getCode().equals(commitCode))break;
			}
			List<CommitInfo> commitInfoList = tmpCommitInfoList.subList(i, cnt);
			if(!commitInfoList.isEmpty()){
				for(CommitInfo co : commitInfoList){
					System.out.println("commit   :  "+co.getCode()+"    /////   "+co.getDate());
					commitCodeList.add(co.getCode());
				}
			}
		}
		String filePath = FileManager.getINSTANCE().justSearchOriginFileCode(cpFile.getOriginFileCode()).getPath();
		System.out.println("originFilePath : "+filePath);
		CommitInfo resCommit = FileManager.getINSTANCE().mergeFileContent(memberCode, filePath, newContent, objCode, commiter, commitCode, commitCodeList);
		if(resCommit!=null){
			CommitInfoManager.getINSTANCE().putOnlyCommitInfo(memberCode, oriProjectCode, commitInfo);
			//CommitInfoManager.getINSTANCE().putCommitInfo(memberCode, oriProjectCode, commitInfo);
			return true;
		//CommitInfoManager.getINSTANCE().putOnlyCommitInfo(memberCode, oriProjectCode, commitInfo);
		}
		
		return false;
	}
 	
 	public boolean modifyFileContent(String memberCode, String storageCode,String projectCode, List<String> folderCodes, String fileCode,String newContent) throws FileNotFoundException, LoginException, DAOException, SftpException, IOException, JSchException, FileException, ParseException, RemoteFileException, FolderException, ProjectException, CommitExcetion, TeamException{
 		
 		if(!this.fileMap.containsKey(memberCode))this.loadProjectMCode(memberCode);
		
		if(!this.fileMap.get(memberCode).containsKey(projectCode)) loadProjectCode(projectCode);
		
		Map<String,FileNode> memProjectMap = this.fileMap.get(memberCode);
		FileNode node = memProjectMap.get(projectCode);
		int leng = folderCodes.size();
		for(int i=1;i<leng;i++){
			node=node.getChild(folderCodes.get(i));
		}
		
		node=node.getChild(fileCode);
 		
		return FileManager.getINSTANCE().modifyFileContent(memberCode, storageCode, projectCode, folderCodes.get(leng-1), fileCode,node.getPath(), newContent);
	
 	}
 		
 	public String searchDiffFile(String memberCode,String storageCode,String projectCode,List<String> folderCodes,String fileCode,String fileContent) throws DAOException, ParseException, FolderException, IOException, FileException, ProjectException, LoginException, SftpException, JSchException, RemoteFileException, CommitExcetion{
 		
 		if(!this.fileMap.containsKey(memberCode))this.loadProjectMCode(memberCode);
		
		if(!this.fileMap.get(memberCode).containsKey(projectCode)) loadProjectCode(projectCode);
		
		Map<String,FileNode> memProjectMap = this.fileMap.get(memberCode);
		FileNode node = memProjectMap.get(projectCode);
		int leng = folderCodes.size();
		for(int i=1;i<leng;i++){
			node=node.getChild(folderCodes.get(i));
		}
		
		node=node.getChild(fileCode);
 		
 		return FileManager.getINSTANCE().searchDiffFile(memberCode, storageCode, projectCode, folderCodes.get(leng-1), fileCode, fileContent);
 	}
 	
 	public List<String> searchMemberProjectCodeList(String memberCode){
 		List<String> resList = new ArrayList<String>();
 		
 		if(!this.fileMap.containsKey(memberCode))return resList;
 		for(String proCode : this.fileMap.get(memberCode).keySet()){
 			resList.add(proCode);
 		}
 		return resList;
 	}

 	public List<String> searchMemberProjectPathList(String memberCode){
 		List<String> resList = new ArrayList<String>();
 		
 		if(!this.fileMap.containsKey(memberCode))return resList;
 		for(FileNode proCode : this.fileMap.get(memberCode).values()){
 			resList.add(proCode.getPath());
 		}
 		return resList;
 	}
 	
 	public OriginProject searchMemberProject(String memberCode, String projectCode) throws DAOException, ParseException, FolderException, IOException, FileException, ProjectException{
 		
 		//if project is copied return originProejct
 		//if proejct if origin return
 		if(!this.fileMap.containsKey(memberCode))this.loadProjectMCode(memberCode);
		
		if(!this.fileMap.get(memberCode).containsKey(projectCode)) loadProjectCode(projectCode);
		
		if(!this.fileMap.containsKey(memberCode))return null;
		if(!this.fileMap.get(memberCode).containsKey(projectCode)) return null;
		FileNode projectNode = this.fileMap.get(memberCode).get(projectCode);
		if(projectNode!=null){
			String projectPath = projectNode.getPath();
			return (OriginProject) ProjectManager.getINSTANCE().searchProjectByProjectPath(projectPath);
		}
		return null;		
 	}
 	
 	public boolean isMemberOriginProject(String memberCode, String projectCode) throws DAOException, ParseException, FolderException, IOException, FileException, ProjectException{
 		
 		if(!this.fileMap.containsKey(memberCode)){
 			this.loadOriginProjectMCode(memberCode);
 			this.loadCopiedProjectMCode(memberCode);
 		}
		
 		if(this.fileMap.containsKey(memberCode)){
 			if(!this.fileMap.get(memberCode).containsKey(projectCode)){
 				LoadProjectForm oriPro = this.loadOriginProjectMCode(memberCode, projectCode);
 				if(oriPro==null){
 					this.loadCopiedProjectMCode(memberCode, projectCode);
 					return false;
 				}
 			}
 			if(this.fileMap.get(memberCode).containsKey(projectCode)){
 				
 				File resFile = ProjectManager.getINSTANCE().searchProjectByProjectPath(this.fileMap.get(memberCode).get(projectCode).getPath());
 				if(resFile instanceof CopiedProject)return false;
 	 			return true;
 			}
 		}
 		return false;
 	}
 	
 	public Map<String,CopiedProject> searchCoopersCpProjectListByOriProject(String oriOwnerCode, String oriProjectCode) throws DAOException, ParseException, FolderException, IOException, ProjectException, FileException, MemberException{
 	
 		if(!this.fileMap.containsKey(oriOwnerCode))this.loadProjectMCode(oriOwnerCode);
		
		if(!this.fileMap.get(oriOwnerCode).containsKey(oriProjectCode)) loadProjectCode(oriProjectCode);
		
		Map<String,CopiedProject> resMap = new HashMap<String,CopiedProject>();
		FileNode projectNode = this.fileMap.get(oriOwnerCode).get(oriProjectCode);
		if(projectNode!=null){
			String oriProjectPath = projectNode.getPath();
			String ownerNickName = MemberManager.getINSTANCE().searchMemberCode(oriOwnerCode).getNickName();
			OriginProject oriProject = ProjectManager.getINSTANCE().searchOnlyOriginProjectByProjectPath(oriProjectPath);
			
			List<String> cooperList = oriProject.getSharedMemberList();
			if(!cooperList.isEmpty()){
			for(String cooperCode : cooperList){
				String cooperNickName = MemberManager.getINSTANCE().searchMemberCode(cooperCode).getNickName();
				String cpPath = oriProjectPath.replaceAll(ownerNickName, cooperNickName);
				System.out.println("cooperPath  :  "+cpPath);
				CopiedProject cooperPro = (CopiedProject)ProjectManager.getINSTANCE().searchProjectByProjectPath(cpPath);
				if(this.fileMap.containsKey(cooperCode)){
					if(!this.fileMap.get(cooperCode).containsKey(cooperPro.getCode())) loadProjectCode(cooperPro.getCode());
				}
				resMap.put(cooperCode, cooperPro);
			}	
			}
			return resMap;
		}
		return resMap; 
 	}

 	public Map<String,CopiedProject> searchCoopersCpProjectListByCpProject(String cpOwnerCode, String cpProjectCode) throws DAOException, ParseException, FolderException, IOException, ProjectException, FileException{
 	
 		Map<String,CopiedProject> resMap = new HashMap<String,CopiedProject>();
 		if(!this.fileMap.containsKey(cpOwnerCode))this.loadProjectMCode(cpOwnerCode);
		if(!this.fileMap.get(cpOwnerCode).containsKey(cpProjectCode)) loadProjectCode(cpProjectCode);
		if(!this.fileMap.containsKey(cpOwnerCode))return resMap;
		if(!this.fileMap.get(cpOwnerCode).containsKey(cpProjectCode))return resMap;
		
		FileNode cpNode = this.fileMap.get(cpOwnerCode).get(cpProjectCode);
 		if(cpNode==null)return resMap;
 		
 		String cpProjectPath = cpNode.getPath();
 		CopiedProject cpProject = ProjectManager.getINSTANCE().searchOnlyCpProjectByProjectPath(cpProjectPath);
 		String oriOwnerCode = cpProject.getOriginOwnerCode();
 		String oriProjectCode = cpProject.getOriginCode();
 		
 		if(!this.fileMap.containsKey(oriOwnerCode))this.loadProjectMCode(cpOwnerCode);
		if(!this.fileMap.get(oriOwnerCode).containsKey(oriProjectCode)) loadProjectCode(oriProjectCode);
		if(!this.fileMap.containsKey(oriOwnerCode))return resMap;
		if(!this.fileMap.get(oriOwnerCode).containsKey(oriProjectCode))return resMap;
		
		FileNode oriNode = this.fileMap.get(oriOwnerCode).get(oriProjectCode);
		if(oriNode==null)return resMap;
		OriginProject oriProject = ProjectManager.getINSTANCE().searchOnlyOriginProjectByProjectPath(oriNode.getPath());
		if(oriProject==null)return resMap;
		
		List<String> sharedMemnerCodeList = oriProject.getSharedMemberList();
		if(sharedMemnerCodeList.isEmpty())return resMap;
		
		resMap = ProjectManager.getINSTANCE().searchOnlyCpProjectByOriProCode(oriProjectCode);
		return resMap; 
 	}
 	public OriginProject searchOriginProjectByCpProject(String cpOwnerCode, String cpProjectCode) throws DAOException, ParseException, FolderException, IOException, ProjectException, FileException{
 		
 		OriginProject res = null;
 		
 		if(!this.fileMap.containsKey(cpOwnerCode))this.loadProjectMCode(cpOwnerCode);
		if(!this.fileMap.get(cpOwnerCode).containsKey(cpProjectCode)) loadProjectCode(cpProjectCode);
		if(!this.fileMap.containsKey(cpOwnerCode))return res;
		if(!this.fileMap.get(cpOwnerCode).containsKey(cpProjectCode))return res;
		
		FileNode cpNode = this.fileMap.get(cpOwnerCode).get(cpProjectCode);
 		if(cpNode==null)return res;
 		
 		String cpProjectPath = cpNode.getPath();
 		CopiedProject cpProject = ProjectManager.getINSTANCE().searchOnlyCpProjectByProjectPath(cpProjectPath);
 		String oriOwnerCode = cpProject.getOriginOwnerCode();
 		String oriProjectCode = cpProject.getOriginCode();
 		
 		if(!this.fileMap.containsKey(oriOwnerCode))this.loadProjectMCode(cpOwnerCode);
		if(!this.fileMap.get(oriOwnerCode).containsKey(oriProjectCode)) loadProjectCode(oriProjectCode);
		if(!this.fileMap.containsKey(oriOwnerCode))return res;
		if(!this.fileMap.get(oriOwnerCode).containsKey(oriProjectCode))return res;
		
		FileNode oriNode = this.fileMap.get(oriOwnerCode).get(oriProjectCode);
		return ProjectManager.getINSTANCE().searchOnlyOriginProjectByProjectPath(oriNode.getPath()); 		
 	}
 	
 	
///////////////////////////////////////////////////////////////////////////////////////////////////
 	
 	

 	private List<LoadProjectForm> makeOriginMap(List<File> proList,String memberCode) throws DAOException, ParseException, FolderException, IOException{
 		Map<String,FileNode> projectMap = new HashMap<String, FileNode>();
 		List<LoadProjectForm> resList = new ArrayList<LoadProjectForm>();
 		for(File proFile : proList){
 			OriginProject pro = (OriginProject)proFile;
			System.out.println("THis is FileNode Manager load :    "+pro.getCode()+"  // "+pro.getParentFolder());
			
			Map<String,MyOriginFile> fileMap = new HashMap<String, MyOriginFile>();
			List<File> folderList = FolderManager.getINSTANCE().searchAllFoldersByPFPath(pro.getPath());
			List<File> fileList = FileManager.getINSTANCE().searchAllFileByFPath(pro.getPath());
			MyOriginFile myfolder = new MyOriginFile(pro.getCode(), pro.getName(), pro.getPath(), "project");
			myfolder.setChildFileList(pro.getChildFileList());
			myfolder.setChildFolderList(pro.getChildFolderList());
			
			if(!folderList.isEmpty()){
				for(File folder : folderList){
					OriginFolder oriFolder = (OriginFolder)folder;
					MyOriginFile myFold = new MyOriginFile(oriFolder.getCode(), oriFolder.getName(), oriFolder.getPath(), "folder");
					myFold.setChildFileList(oriFolder.getChildFileList());
					myFold.setChildFolderList(oriFolder.getChildFolderList());
					fileMap.put(oriFolder.getCode(), myFold);
				}	
			}
			
			if(!fileList.isEmpty()){
			for(File file : fileList){
				OriginFile oriFile = (OriginFile)file;
				MyOriginFile myFile = new MyOriginFile(oriFile.getCode(), oriFile.getName(), oriFile.getPath(), "file");
				System.out.println(myFile.getCode());
				fileMap.put(oriFile.getCode(), myFile);
			}
			}
			
			fileMap.put(pro.getCode(), myfolder);
			FileNode node = new FileNode(null, myfolder);
			node.initFileNode(fileMap);
			projectMap.put(pro.getCode(), node);
			
			LoadProjectForm resObj = new LoadProjectForm(pro.getName(), pro.getCode(), pro.getDescription(), pro.getMakeDate(), pro.getSharedMemberList().size(), "Original Project");
			resList.add(resObj);

			if(!this.fileMap.containsKey(memberCode)){this.fileMap.put(memberCode, projectMap);}
			else if(!this.fileMap.get(memberCode).containsKey(pro.getCode())){this.fileMap.get(memberCode).put(pro.getCode(), node);}
			
		}
 		return resList;
 	}
 	
 	private List<LoadProjectForm> makeOriginMap(OriginProjectList proList,String memberCode) throws DAOException, ParseException, FolderException, IOException{
 		Map<String,FileNode> projectMap = new HashMap<String, FileNode>();
 		List<LoadProjectForm> resList = new ArrayList<LoadProjectForm>();
 		for(OriginProject pro : proList.getList()){
			
			System.out.println("THis is FileNode Manager load :    "+pro.getCode()+"  // "+pro.getParentFolder());
			
			Map<String,MyOriginFile> fileMap = new HashMap<String, MyOriginFile>();
			List<File> folderList = FolderManager.getINSTANCE().searchAllFoldersByPFPath(pro.getPath());
			List<File> fileList = FileManager.getINSTANCE().searchAllFileByFPath(pro.getPath());
			MyOriginFile myfolder = new MyOriginFile(pro.getCode(), pro.getName(), pro.getPath(), "project");
			myfolder.setChildFileList(pro.getChildFileList());
			myfolder.setChildFolderList(pro.getChildFolderList());
			
			if(!folderList.isEmpty()){
				for(File folder : folderList){
					OriginFolder oriFolder = (OriginFolder)folder;
					MyOriginFile myFold = new MyOriginFile(oriFolder.getCode(), oriFolder.getName(), oriFolder.getPath(), "folder");
					myFold.setChildFileList(oriFolder.getChildFileList());
					myFold.setChildFolderList(oriFolder.getChildFolderList());
					fileMap.put(oriFolder.getCode(), myFold);
				}	
			}
			
			if(!fileList.isEmpty()){
			for(File file : fileList){
				OriginFile oriFile = (OriginFile)file;
				MyOriginFile myFile = new MyOriginFile(oriFile.getCode(), oriFile.getName(), oriFile.getPath(), "file");
				System.out.println(myFile.getCode());
				fileMap.put(oriFile.getCode(), myFile);
			}
			}
			
			fileMap.put(pro.getCode(), myfolder);
			FileNode node = new FileNode(null, myfolder);
			node.initFileNode(fileMap);
			projectMap.put(pro.getCode(), node);
			
			LoadProjectForm resObj = new LoadProjectForm(pro.getName(), pro.getCode(), pro.getDescription(), pro.getMakeDate(), pro.getSharedMemberList().size(), "Original Project");
			resList.add(resObj);

			if(!this.fileMap.containsKey(memberCode)){this.fileMap.put(memberCode, projectMap);}
			else if(!this.fileMap.get(memberCode).containsKey(pro.getCode())){this.fileMap.get(memberCode).put(pro.getCode(), node);}
			
		}
 		return resList;
 	}
 	private List<LoadProjectForm> makeCopiedMap(List<File> proList,String memberCode) throws DAOException, ParseException, FolderException, IOException{
 		System.out.println("start    :    makeCopiedMap(CopiedProjectList proList,String memberCode)");
 		Map<String,FileNode> projectMap = new HashMap<String, FileNode>();
		List<LoadProjectForm> resList = new ArrayList<LoadProjectForm>();
		for(File proFile : proList){
 			CopiedProject pro = (CopiedProject)proFile;
			
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
					System.out.println("cpFolder __________ Path : "+oriFolder.getPath());
					MyOriginFile myFold = new MyOriginFile(oriFolder.getCode(), oriFolder.getName(), oriFolder.getPath(), "folder");
					myFold.setChildFileList(oriFolder.getChildFileList());
					myFold.setChildFolderList(oriFolder.getChildFolderList());
					fileMap.put(oriFolder.getCode(), myFold);
				}	
			}
			
			if(!fileList.isEmpty()){
			for(File file : fileList){
				OriginFile oriFile = (OriginFile)file;
				System.out.println("cpFile __________ Path : "+oriFile.getPath());
				MyOriginFile myFile = new MyOriginFile(oriFile.getCode(), oriFile.getName(), oriFile.getPath(), "file");
				System.out.println(myFile.getCode());
				fileMap.put(oriFile.getCode(), myFile);
			}
			}
			
			System.out.println("\n\nGo To Init()\n\n");
			
			fileMap.put(pro.getCode(), myfolder);
			FileNode node = new FileNode(null, myfolder);
			node.initFileNode(fileMap);
			projectMap.put(pro.getCode(), node);
			
			LoadProjectForm resObj = new LoadProjectForm(pro.getName(), pro.getCode(), pro.getDescription(), pro.getMakeDate(), pro.getSharedMemberList().size(), "Copied Project");
			resList.add(resObj);

			if(!this.fileMap.containsKey(memberCode)){this.fileMap.put(memberCode, projectMap);}
			else if(!this.fileMap.get(memberCode).containsKey(pro.getCode())){this.fileMap.get(memberCode).put(pro.getCode(), node);}
			
		}
		System.out.println("end    :    makeCopiedMap(CopiedProjectList proList,String memberCode)");
		return resList;
 	}
 	
 	private List<LoadProjectForm> makeCopiedMap(CopiedProjectList proList,String memberCode) throws DAOException, ParseException, FolderException, IOException{
 		System.out.println("start    :    makeCopiedMap(CopiedProjectList proList,String memberCode)");
 		Map<String,FileNode> projectMap = new HashMap<String, FileNode>();
		List<LoadProjectForm> resList = new ArrayList<LoadProjectForm>();
		for(CopiedProject pro : proList.getList()){
			
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
					System.out.println("cpFolder __________ Path : "+oriFolder.getPath());
					MyOriginFile myFold = new MyOriginFile(oriFolder.getCode(), oriFolder.getName(), oriFolder.getPath(), "folder");
					myFold.setChildFileList(oriFolder.getChildFileList());
					myFold.setChildFolderList(oriFolder.getChildFolderList());
					fileMap.put(oriFolder.getCode(), myFold);
				}	
			}
			
			if(!fileList.isEmpty()){
			for(File file : fileList){
				OriginFile oriFile = (OriginFile)file;
				System.out.println("cpFile __________ Path : "+oriFile.getPath());
				MyOriginFile myFile = new MyOriginFile(oriFile.getCode(), oriFile.getName(), oriFile.getPath(), "file");
				System.out.println(myFile.getCode());
				fileMap.put(oriFile.getCode(), myFile);
			}
			}
			
			System.out.println("\n\nGo To Init()\n\n");
			
			fileMap.put(pro.getCode(), myfolder);
			FileNode node = new FileNode(null, myfolder);
			node.initFileNode(fileMap);
			projectMap.put(pro.getCode(), node);
			
			LoadProjectForm resObj = new LoadProjectForm(pro.getName(), pro.getCode(), pro.getDescription(), pro.getMakeDate(), pro.getSharedMemberList().size(), "Copied Project");
			resList.add(resObj);

			if(!this.fileMap.containsKey(memberCode)){this.fileMap.put(memberCode, projectMap);}
			else if(!this.fileMap.get(memberCode).containsKey(pro.getCode())){this.fileMap.get(memberCode).put(pro.getCode(), node);}
			
		}
		System.out.println("end    :    makeCopiedMap(CopiedProjectList proList,String memberCode)");
		return resList;
 	}
 	

 	private LoadProjectForm makeOriginMap(OriginProject pro,String memberCode) throws DAOException, ParseException, FolderException, IOException{
 		Map<String,FileNode> projectMap = new HashMap<String, FileNode>();

		System.out.println("THis is FileNode Manager load :    "+pro.getCode()+"  // "+pro.getParentFolder());
		
		Map<String,MyOriginFile> fileMap = new HashMap<String, MyOriginFile>();
		List<File> folderList = FolderManager.getINSTANCE().searchAllFoldersByPFPath(pro.getPath());
		List<File> fileList = FileManager.getINSTANCE().searchAllFileByFPath(pro.getPath());
		MyOriginFile myfolder = new MyOriginFile(pro.getCode(), pro.getName(), pro.getPath(), "project");
		myfolder.setChildFileList(pro.getChildFileList());
		myfolder.setChildFolderList(pro.getChildFolderList());
		
		if(!folderList.isEmpty()){
			for(File folder : folderList){
				OriginFolder oriFolder = (OriginFolder)folder;
				MyOriginFile myFold = new MyOriginFile(oriFolder.getCode(), oriFolder.getName(), oriFolder.getPath(), "folder");
				myFold.setChildFileList(oriFolder.getChildFileList());
				myFold.setChildFolderList(oriFolder.getChildFolderList());
				fileMap.put(oriFolder.getCode(), myFold);
			}	
		}
		
		if(!fileList.isEmpty()){
		for(File file : fileList){
			OriginFile oriFile = (OriginFile)file;
			MyOriginFile myFile = new MyOriginFile(oriFile.getCode(), oriFile.getName(), oriFile.getPath(), "file");
			System.out.println(myFile.getCode());
			fileMap.put(oriFile.getCode(), myFile);
		}
		}
		fileMap.put(pro.getCode(), myfolder);
		FileNode node = new FileNode(null, myfolder);
		node.initFileNode(fileMap);
		projectMap.put(pro.getCode(), node);
		
		LoadProjectForm resObj = new LoadProjectForm(pro.getName(), pro.getCode(), pro.getDescription(), pro.getMakeDate(), pro.getSharedMemberList().size(), "Original Project");
		
		if(!this.fileMap.containsKey(memberCode)){this.fileMap.put(memberCode, projectMap);}
		else if(!this.fileMap.get(memberCode).containsKey(pro.getCode())){this.fileMap.get(memberCode).put(pro.getCode(), node);}
		
 		return resObj;
 	}
 	
 	private LoadProjectForm makeCopiedMap(CopiedProject pro,String memberCode) throws DAOException, ParseException, FolderException, IOException{
 		
 		Map<String,FileNode> projectMap = new HashMap<String, FileNode>();
 		
		System.out.println("THis is FileNode Manager load :    "+pro.getCode()+"  // "+pro.getParentFolder());
		
		Map<String,MyOriginFile> fileMap = new HashMap<String, MyOriginFile>();
		List<File> folderList = FolderManager.getINSTANCE().searchAllFoldersByPFPath(pro.getPath());
		List<File> fileList = FileManager.getINSTANCE().searchAllFileByFPath(pro.getPath());
		MyOriginFile myfolder = new MyOriginFile(pro.getCode(), pro.getName(), pro.getPath(), "project");
		myfolder.setChildFileList(pro.getChildFileList());
		myfolder.setChildFolderList(pro.getChildFolderList());
		
		if(!folderList.isEmpty()){
			for(File folder : folderList){
				OriginFolder oriFolder = (OriginFolder)folder;
				MyOriginFile myFold = new MyOriginFile(oriFolder.getCode(), oriFolder.getName(), oriFolder.getPath(), "folder");
				myFold.setChildFileList(oriFolder.getChildFileList());
				myFold.setChildFolderList(oriFolder.getChildFolderList());
				fileMap.put(oriFolder.getCode(), myFold);
			}	
		}
		
		if(!fileList.isEmpty()){
		for(File file : fileList){
			OriginFile oriFile = (OriginFile)file;
			MyOriginFile myFile = new MyOriginFile(oriFile.getCode(), oriFile.getName(), oriFile.getPath(), "file");
			System.out.println(myFile.getCode());
			fileMap.put(oriFile.getCode(), myFile);
		}
		}
		fileMap.put(pro.getCode(), myfolder);
		FileNode node = new FileNode(null, myfolder);
		node.initFileNode(fileMap);
		projectMap.put(pro.getCode(), node);
		
		LoadProjectForm resObj = new LoadProjectForm(pro.getName(), pro.getCode(), pro.getDescription(), pro.getMakeDate(), pro.getSharedMemberList().size(), "Copied Project");

		if(!this.fileMap.containsKey(memberCode)){this.fileMap.put(memberCode, projectMap);}
		else if(!this.fileMap.get(memberCode).containsKey(pro.getCode())){this.fileMap.get(memberCode).put(pro.getCode(), node);}
		
		return resObj;
 	}
 	
 //////////////////////////////////////////////////////////////////// for FolderForm
 	
 	private Map<String,List<FolderForm>> makeFolderFormListByParentFileNode(String memberCode,String projectCode,FileNode parentNode) throws DAOException, ParseException, FolderException, FileNotFoundException, SftpException, IOException, JSchException, ProjectException, TeamException, FileException, RemoteFileException, MemberException{
 		
 		Map<String,List<FolderForm>> resMap = new HashMap<>();
		List<FolderForm> folderList = new ArrayList<FolderForm>();
		List<FolderForm> fileList = new ArrayList<FolderForm>();
 		
 		TreeMap<String, FileNode> t = parentNode.getChilds();
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss.SSS");

		System.out.println("갯구"+t.values().size());
		for(FileNode chNode : t.values() ){
			System.out.println("searchFolders  :   "+chNode.getPath()+" type :: "+chNode.getType());
			if(chNode.getType().equals("folder")){
				File folder= FolderManager.getINSTANCE().searchFolderByFolderPath(chNode.getPath());
				OriginFolder fo = (OriginFolder)folder;
			
				String commitContent = fo.getDescription();
				folderList.add(new FolderForm(chNode.getType(), chNode.getCode(), chNode.getName(), fo.getMakeDate(),fo.getAlterDate(),commitContent));
			}
			else if(chNode.getType().equals("file")){
				File file = FileManager.getINSTANCE().searchFilePath(chNode.getPath());
				OriginFile fo = (OriginFile)file;
				
				fileList.add(new FolderForm(chNode.getType(), chNode.getCode(), chNode.getName(), fo.getMakeDate(),fo.getAlterDate(),null));
			}
		}
		resMap.put("folderList", folderList);
		resMap.put("fileList", fileList);
		return resMap;
 	}
 	

 	private List<FolderPathForm> makeOnlyFolderPathFormListByParentFileNode(String memberCode,String projectCode,FileNode parentNode) throws DAOException, ParseException, FolderException, FileNotFoundException, SftpException, IOException, JSchException, ProjectException, TeamException, FileException, RemoteFileException, MemberException{
 		
		List<FolderPathForm> folderList = new ArrayList<FolderPathForm>();
		
 		TreeMap<String, FileNode> t = parentNode.getChilds();
		
		for(FileNode chNode : t.values() ){
			System.out.println("searchFolders  :   "+chNode.getPath()+" type :: "+chNode.getType());
			if(chNode.getType().equals("folder")){				
				folderList.add(new FolderPathForm(chNode.getType(), chNode.getCode(), chNode.getName(),chNode.getPath()));
			}
		}
		return folderList;
 	}
 	private boolean insertFolderMap(Map<OriginFolder, CommitInfo> resMap, String memNickName, String memberCode) throws DAOException, ParseException, FolderException, FileNotFoundException, CommitExcetion, SftpException, IOException, JSchException, ProjectException, MemberException, FileException, RemoteFileException, TeamException{
 		
 		System.out.println("\n            FileNodeManager     insertFolderMap     line    1727");
 		OriginFolder oriFolder = resMap.keySet().iterator().next();
		String oriFolderPath = oriFolder.getPath();
		StringTokenizer token1 = new StringTokenizer(oriFolderPath, memNickName);
		String head = token1.nextToken()+"\\"+memNickName;
		StringTokenizer token2 = new StringTokenizer(token1.nextToken(), "\\");
		head+="\\"+token2.nextToken();
		System.out.println("head : "+head);
		String code = ProjectManager.getINSTANCE().justSearchProjectPath(head).getCode();
		FileNode proNode = this.fileMap.get(memberCode).get(code);
		while(token2.hasMoreTokens()){
			head+="\\"+token2.nextToken();
			System.out.println("head : "+head);
			code = FolderManager.getINSTANCE().justSearchFolderPath(head).getCode();
			if(code!=oriFolder.getCode()){
				proNode = proNode.getChild(code);
			}
		}
		System.out.println("proNode path : "+proNode.getPath());
		proNode.createChildFolder(new MyOriginFile(oriFolder.getCode(), oriFolder.getName(), oriFolder.getPath(), "folder"));
		CommitInfo commitInfo = resMap.get(oriFolder);
		return CommitInfoManager.getINSTANCE().putCommitInfo(memberCode, proNode.getCode(), commitInfo);
		//CommitInfoManager.getINSTANCE().putOnlyCommitInfo(memberCode, oriProjectCode, commitInfo);
 	}
 	private boolean insertFileMap(Map<OriginFile, CommitInfo> resMap, String memNickName, String memberCode) throws DAOException, ParseException, FolderException, FileNotFoundException, CommitExcetion, SftpException, IOException, JSchException, ProjectException, MemberException, FileException, RemoteFileException, TeamException{
 		
 		System.out.println("\n            FileNodeManager     insertFileMap     line    1736");
 		OriginFile oriFile = resMap.keySet().iterator().next();
		String oriFolderPath = oriFile.getPath();
		StringTokenizer token1 = new StringTokenizer(oriFolderPath, memNickName);
		String head = token1.nextToken()+"\\"+memNickName;
		StringTokenizer token2 = new StringTokenizer(token1.nextToken(), "\\");
		head+="\\"+token2.nextToken();
		System.out.println("head : "+head);
		String code = ProjectManager.getINSTANCE().justSearchProjectPath(head).getCode();
		FileNode proNode = this.fileMap.get(memberCode).get(code);
		while(token2.hasMoreTokens()){
			head+="\\"+token2.nextToken();
			System.out.println("head : "+head);
			OriginFolder oriFolder = FolderManager.getINSTANCE().justSearchFolderPath(head);
			if(oriFolder!=null){
				code = oriFolder.getCode();
				proNode = proNode.getChild(code);
			}
		}
		System.out.println("proNode path : "+proNode.getPath());
		proNode.createChildFolder(new MyOriginFile(oriFile.getCode(), oriFile.getName(), oriFile.getPath(), "file"));
		CommitInfo commitInfo = resMap.get(oriFile);
		return CommitInfoManager.getINSTANCE().putCommitInfo(memberCode, proNode.getCode(), commitInfo);
		//CommitInfoManager.getINSTANCE().putOnlyCommitInfo(memberCode, oriProjectCode, commitInfo);
 	}
 	
}
