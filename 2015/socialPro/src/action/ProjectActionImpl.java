package action;

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

import model.CopiedFolder;
import model.CopiedProject;
import model.CopiedProjectList;
import model.FileNode;
import model.OriginFile;
import model.OriginFileList;
import model.OriginFolder;
import model.OriginFolderList;
import model.OriginProject;
import model.OriginProjectList;
import model.Team;
import socialProExceptions.CommitExcetion;
import socialProExceptions.DAOException;
import socialProExceptions.FileException;
import socialProExceptions.FolderException;
import socialProExceptions.ProjectException;
import socialProExceptions.RemoteFileException;
import socialProExceptions.TagException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import dao.CooperatorDAO;
import dao.CopiedInfoDAO;
import dao.CopiedProjectVDAO;
import dao.DAO;
import dao.FileDAO;
import dao.FolderDAO;
import dao.MemberDAO;
import dao.ProjectDAO;
import dao.ProjectFolderVDAO;
import dao.TeamCooperVDAO;
import dao.TeamDAO;
import dto.CooperatorDTO;
import dto.CopiedInfoDTO;
import dto.CopiedProjectVDTO;
import dto.FileDTO;
import dto.FolderDTO;
import dto.MemberDTO;
import dto.OriginProjectVDTO;
import dto.ProjectDTO;
import dto.ProjectFolderVDTO;
import dto.ProjectSharedMemVDTO;
import dto.ProjectTagsVDTO;
import dto.TeamCooperVDTO;
import dto.TeamDTO;
import factory.ActionFactory;
import factory.DAOFactory;

public class ProjectActionImpl extends BaseAction implements ProjectSearchAction,ProjectInsertAction,ProjectDropAction,ProjectUpdateAction{

	private String remoteRootPath;
	private String localRootPath;
	public ProjectActionImpl() {
		// TODO Auto-generated constructor stub
		this.setDAO((ProjectDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectDAO"));
		this.remoteRootPath = "/home/socialPro";
		this.localRootPath = "C:\\socialPro";
	}

	public ProjectActionImpl(String remoteRootPath,String localRootPath) {
		// TODO Auto-generated method stub
		this.setDAO((ProjectDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectDAO"));
		this.remoteRootPath = remoteRootPath;
		this.localRootPath = localRootPath;
	}
	
	@Override
	public void setDAO(DAO dao) {
		// TODO Auto-generated method stub
		super.dao=(ProjectDAO)dao;
	}
	
	protected String insertProject(ProjectDTO dto)throws DAOException{
		
		return ((ProjectDAO)this.getDAO()).insertProject(dto);
	}
	
	/*@Override
	public OriginProject insertOriginProject(String memberCode,String nickName, String name, String description, String path,  List<String> tagList)throws DAOException, SftpException, JSchException, FolderException, ProjectException{
		// TODO Auto-generated method stub
		
		//OriginProject project = new OriginProject(path);
		OriginFolder folder = new OriginFolder(path);
		
		//create folder
		folder.setName(name);
		folder.setDescription(description);
		folder.setMakeDate(new GregorianCalendar());
		folder.setSize(0);
		folder = ((FolderInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("folderActionImpl")).insertOriginFolder(folder,memberCode);
		if(folder==null)throw new ProjectException("Error Insert Project");
		
		//projectTags_tb
		Map<String,List<String>> tagMap=((TagSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("TagActionImpl")).searchTagsExist(tagList);
		List<String> existTags=tagMap.get("exist");
		List<String> nonexistTags=tagMap.get("nonexist");
		
		//create project
		System.out.println("This is created folder... "+folder.getCode());
		ProjectDTO dto=new ProjectDTO();
		dto.setFolderCode(folder.getCode());
		dto.setOwner(memberCode);
		String projectCode = this.insertProject(dto);
		if(projectCode==null)return null;
		dto.setCode(projectCode);
		
		//insert new Tags
		if(!nonexistTags.isEmpty()){
			if(((TagInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("TagActionImpl")).insertTag(nonexistTags, projectCode)<=0)return null;
		}
		if(!existTags.isEmpty()){
			if(((TagInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("TagActionImpl")).insertTagDetails(existTags, projectCode)<=0)return null;
		}
		System.out.println("insertOriginProject"+projectCode);
		
		return this.makeModel(folder, dto, tagList);
	}*/
	
	
	@Override
	public OriginProject insertOriginProject(String memberCode,String nickName, String name, String description, String path,  List<String> tagList)throws DAOException, SftpException, JSchException, FolderException, ProjectException, TagException{
		// TODO Auto-generated method stub
		
		//  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		
		//OriginProject project = new OriginProject(path);
		OriginFolder folder = new OriginFolder(path);
		
		//create folder
		folder.setName(name);
		folder.setDescription(description);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		GregorianCalendar cal = new GregorianCalendar();
		df.setTimeZone(cal.getTimeZone());
		folder.setMakeDate(df.format(cal.getTime()));
		folder.setSize(0);
		folder = ((FolderInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("folderActionImpl")).insertOriginProjectFolder(folder,memberCode);
		
		//projectTags_tb
		Map<String,List<String>> tagMap=((TagSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("TagActionImpl")).searchTagsExist(tagList);
		List<String> existTags=tagMap.get("exist");
		List<String> nonexistTags=tagMap.get("nonexist");
		
		//create project
		System.out.println("This is created folder... "+folder.getCode());
		ProjectDTO dto=new ProjectDTO();
		dto.setFolderCode(folder.getCode());
		dto.setOwner(memberCode);
		String projectCode = this.insertProject(dto);
		if(projectCode==null)throw new ProjectException("Error when create project..");
		dto.setCode(projectCode);
		
		//insert new Tags
		if(!nonexistTags.isEmpty()){
			if(((TagInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("TagActionImpl")).insertTag(nonexistTags, projectCode)<=0)throw new TagException("Error create New Tag For Project");
		}
		if(!existTags.isEmpty()){
			if(((TagInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("TagActionImpl")).insertTagDetails(existTags, projectCode)<=0)throw new TagException("Error add existed Tag For Project");
		}
		System.out.println("insertOriginProject"+projectCode);
		return this.makeModel(folder, dto,tagList);
	}

	/*@Override
	public CopiedProject insertCopiedProject(String oriOwnerCode,String oriProjectCode,String oriFolderCode,String memberCode,String nickName, String teamCode,String teamName,String projectName, String path)throws DAOException, SftpException, JSchException, FolderException, ProjectException, CommitExcetion{
		// TODO Auto-generated method stub
		
		//OriginProject project = new OriginProject(path);
		CopiedFolder folder = new CopiedFolder(path);
		
		//create folder
		folder.setName(projectName);
		folder.setMakeDate(new GregorianCalendar());
		folder.setSize(0);
		folder.setOriginCode(oriFolderCode);
		folder.setOriginParentCode(oriFolderCode);
		folder = ((FolderInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("folderActionImpl")).insertCopiedProjectFolder(folder, teamCode, oriFolderCode, memberCode);
		if(folder==null)throw new FolderException("Error insert CopiedFolder");
		
		System.out.println("cp folderCode  :  "+folder.getCode()+"  ///   shared folderCode  : "+oriFolderCode);
		//if(((CopiedInfoDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedInfoDAO")).insertCopiedInfo(new CopiedInfoDTO(folder.getCode(),oriFolderCode))!=1)return null;
		
		//create project
		System.out.println("This is created folder... "+folder.getCode());
		ProjectDTO dto=new ProjectDTO();
		dto.setFolderCode(folder.getCode());
		dto.setOwner(memberCode);
		String projectCode = this.insertProject(dto);
		if(projectCode==null)throw new ProjectException("Error insert CopiedProject");	
		dto.setCode(projectCode);
		
		if(((CopiedInfoDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedInfoDAO")).insertCopiedInfo(new CopiedInfoDTO(projectCode,oriProjectCode))!=1)return null;
		((CooperatorDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("cooperatorDAO")).insertCooperator(new CooperatorDTO(memberCode, oriProjectCode));

		CopiedProject cpProject = makeCopiedModel(folder, dto, oriProjectCode,oriOwnerCode);
		if(cpProject!=null)System.out.println("copied_Project_Code  :::::   "+cpProject.getCode()+"Copied_Project_Folder_Parent_FolderCode  ===>>>  "+cpProject.getParentFolder());
		else System.out.println("cpProject is null........");
		return cpProject;
	}*/
	@Override
	public CopiedProject insertCopiedProject(String oriOwnerCode,String oriProjectCode,String oriFolderCode,String memberCode,
			String nickName, String teamCode,String teamName,String projectName, String path)throws DAOException, SftpException, JSchException, FolderException, ProjectException{
		// TODO Auto-generated method stub
		
		//OriginProject project = new OriginProject(path);
		CopiedFolder folder = new CopiedFolder(path);
		
		//create folder
		folder.setName(projectName);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		GregorianCalendar cal = new GregorianCalendar();
		df.setTimeZone(cal.getTimeZone());
		folder.setMakeDate(df.format(cal.getTime()));
		
		folder.setSize(0);
		folder.setOriginCode(oriFolderCode);
		folder.setOriginParentCode(oriFolderCode);
		folder = ((FolderInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("folderActionImpl")).insertCopiedFolder(folder, teamCode, oriFolderCode, memberCode);
		if(folder==null)throw new FolderException("Error insert CopiedFolder");
		
		System.out.println("cp folderCode  :  "+folder.getCode()+"  ///   shared folderCode  : "+oriFolderCode);
		//if(((CopiedInfoDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedInfoDAO")).insertCopiedInfo(new CopiedInfoDTO(folder.getCode(),oriFolderCode))!=1)return null;
		
		//create project
		System.out.println("This is created folder... "+folder.getCode());
		ProjectDTO dto=new ProjectDTO();
		dto.setFolderCode(folder.getCode());
		dto.setOwner(memberCode);
		String projectCode = this.insertProject(dto);
		if(projectCode==null)throw new ProjectException("Error insert CopiedProject");	
		dto.setCode(projectCode);
		if(((CopiedInfoDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedInfoDAO")).insertCopiedInfo(new CopiedInfoDTO(projectCode,oriProjectCode))!=1)return null;
		((CooperatorDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("cooperatorDAO")).insertCooperator(new CooperatorDTO(memberCode, oriProjectCode));

		return makeCopiedModel(folder, dto, oriProjectCode,oriOwnerCode);
	}
	
	@Override
	public Map<String,CopiedProject> insertCopiedProjectList(List<FileNode> oriProjectList, String memberCode, String nickName,String teamCode, String teamName)throws DAOException, SftpException, JSchException, FolderException, ProjectException, FileNotFoundException, ParseException, IOException, FileException, RemoteFileException, CommitExcetion{
		
		Map<String,CopiedProject> resMap = new HashMap<String,CopiedProject>(); 
		for(FileNode oriProject : oriProjectList){
			ProjectDTO proDTO = ((ProjectDAO)this.getDAO()).searchProjectCode(oriProject.getCode());
			CopiedProject cp = this.insertCopiedProject(proDTO.getOwner(),oriProject.getCode(), proDTO.getFolderCode(), memberCode, nickName, teamCode, teamName, oriProject.getName(), oriProject.getPath());
			((FolderInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("folderActionImpl")).insertCopiedFolders(oriProject, teamCode, memberCode);
			OriginFolderList oriFolderList = ((FolderSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("folderActionImpl")).justSearchOriginChFoldersByPFCode(cp.getParentFolder());
			OriginFileList oriFileList = ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).justSearchFileByFCode(cp.getParentFolder());
			List<String> ch = new ArrayList<String>();
			for(OriginFolder of : oriFolderList.getList()){
				ch.add(of.getCode());
			}
			cp.setChildFolderList(ch);
			ch = new ArrayList<String>();
			for(OriginFile of : oriFileList.getList()){
				ch.add(of.getCode());
			}
			cp.setChildFileList(ch);
			resMap.put(cp.getCode(), cp);
		}
		return resMap;
	}
	
	@Override
	public CopiedProject insertCopiedProject(FileNode oriProject,String memberCode, String nickName,String ownerCode, String ownerName)throws DAOException, SftpException, JSchException, FolderException, ProjectException, FileNotFoundException, ParseException, IOException, FileException, RemoteFileException, CommitExcetion{
		
		ProjectDTO proDTO = ((ProjectDAO)this.getDAO()).searchProjectCode(oriProject.getCode());
		String copiedProjectPath = this.localRootPath+"\\"+nickName+"\\"+oriProject.getName();
		//insert folder_tb and project_tb copiedInfo_tb and cooperator_tb
		CopiedProject res = this.insertCopiedProject(proDTO.getOwner(),oriProject.getCode(), proDTO.getFolderCode(), memberCode, nickName, ownerCode, ownerName, oriProject.getName(), copiedProjectPath);
		
		((FolderInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("folderActionImpl")).insertCopiedFolders(oriProject, ownerCode, memberCode);
		OriginFolderList oriFolderList = ((FolderSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("folderActionImpl")).justSearchOriginChFoldersByPFCode(res.getParentFolder());
		OriginFileList oriFileList = ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).justSearchFileByFCode(res.getParentFolder());
		List<String> ch = new ArrayList<String>();
		for(OriginFolder of : oriFolderList.getList()){
			ch.add(of.getCode());
		}
		res.setChildFolderList(ch);
		ch = new ArrayList<String>();
		for(OriginFile of : oriFileList.getList()){
			ch.add(of.getCode());
		}
		res.setChildFileList(ch);
		
		return res;
	}
	
	@Override
	public String insertCopiedProject(String memberCode,String nickName, String name, String description, String path,  List<String> tagList,String originProjectCode)throws DAOException{
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public List<File> insertCopiedTeamProject(String teamCode, String teamName,String projectName, String proDes,String path,List<String> tagList) throws DAOException, SftpException, JSchException, FolderException, ProjectException, TagException {
		// TODO Auto-generated method stub
		
		List<File> resList = new ArrayList<File>();
		//make remotePath
		String realRemoteRootPath = remoteRootPath+"/"+teamCode;
		String realLocalRootPath = localRootPath+"\\"+teamName;
		
		//1. insert Shared Origin Project
		OriginProject teamOriPro=this.insertOriginProject(teamCode, teamName, projectName, proDes, path+"\\"+projectName, tagList);
		if(teamOriPro==null)throw new ProjectException("Error Insert Team's shared Project");
		resList.add(teamOriPro);
		String teamOriProCode = teamOriPro.getCode();
		String teamOriPaProCode = teamOriPro.getParentFolder();
		System.out.println("THis is shared ProjectCode  :  "+teamOriProCode+" ~~ folderCode  : "+teamOriPaProCode);
		
		//2. get team Manager code
		TeamDTO teamDTO=((TeamDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamDAO")).searchTeamCode(teamCode);
		if(teamDTO==null) return null;
		MemberDTO memDTO=((MemberDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("memberDAO")).searchMemberCode(teamDTO.getManager());
		if(memDTO==null)return null;
		
		//2. get team memberCode
		String memNickName;
		String memCode;
		List<TeamCooperVDTO> teamCooperVDTOList=((TeamCooperVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamCooperVDAO")).searchTeamCode(teamCode);
		if(teamCooperVDTOList.isEmpty()){ 
			if(teamDTO==null) return null;
		}else{
			int cnt = teamCooperVDTOList.size();
			int ciRes;

			for(int i=0;i<cnt;i++){
				memCode = teamCooperVDTOList.get(i).getMemberCode();
				memNickName = teamCooperVDTOList.get(i).getNickName();
				//action for team member
				CopiedProject cpro=this.insertCopiedProject(teamCode,teamOriProCode,teamOriPaProCode,memCode, memNickName, teamCode,teamName,projectName, path+"\\"+memNickName+"\\"+projectName);
				if(cpro==null)throw new ProjectException("Error Insert Copied Project");
				resList.add(cpro);
			}
			//action for team manager
			memCode = memDTO.getCode();
			memNickName = memDTO.getNickName();
			CopiedProject cpro=this.insertCopiedProject(teamCode,teamOriProCode,teamOriPro.getParentFolder(),memCode, memNickName, teamCode,teamName,projectName, path+"\\"+memNickName+"\\"+projectName);
			if(cpro==null)throw new ProjectException("Error Insert Copied Project");
			resList.add(cpro);
		}
		return resList;
	}
	
	@Override
	public List<File> insertCopiedTeamProject(Team teamInfo,String projectName, String proDes,String path,List<String> tagList) throws DAOException, SftpException, JSchException, FolderException, ProjectException, CommitExcetion, TagException {
		// TODO Auto-generated method stub
		
		String teamCode = teamInfo.getCode();
		String teamName = teamInfo.getName();
		List<File> resList = new ArrayList<File>();
		
		//make remotePath
		String realRemoteRootPath = remoteRootPath+"/"+teamCode;
		String realLocalRootPath = localRootPath+"\\"+teamName;
		
		//1. insert Shared Origin Project
		OriginProject teamOriPro=this.insertOriginProject(teamCode, teamName, projectName, proDes, path+"\\"+projectName, tagList);
		if(teamOriPro==null)throw new ProjectException("Error Insert Team's shared Project");
		resList.add(teamOriPro);
		String teamOriProCode = teamOriPro.getCode();		
				
		
		//2. get team Manager code
		MemberDTO memDTO=((MemberDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("memberDAO")).searchMemberCode(teamInfo.getManager());
		if(memDTO==null)return null;
		
		//2. get team memberCode
		String memNickName;
		String memCode;
		List<TeamCooperVDTO> teamCooperVDTOList=((TeamCooperVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamCooperVDAO")).searchTeamCode(teamCode);
		if(!teamCooperVDTOList.isEmpty()){ 
			int cnt = teamCooperVDTOList.size();
			int ciRes;

			for(int i=0;i<cnt;i++){
				memCode = teamCooperVDTOList.get(i).getMemberCode();
				memNickName = teamCooperVDTOList.get(i).getNickName();
				//action for team member
				CopiedProject cpro=this.insertCopiedProject(teamCode,teamOriProCode,teamOriPro.getParentFolder(),memCode, memNickName, teamCode,teamName,projectName, path+"\\"+memNickName+"\\"+projectName);
				if(cpro==null)throw new ProjectException("Error Insert Copied Project");
				resList.add(cpro);
			}
			//action for team manager
			memCode = memDTO.getCode();
			memNickName = memDTO.getNickName();
			CopiedProject cpro=this.insertCopiedProject(teamCode,teamOriProCode,teamOriPro.getParentFolder(),memCode, memNickName, teamCode,teamName,projectName, path+"\\"+memNickName+"\\"+projectName);
			if(cpro==null)throw new ProjectException("Error Insert Copied Project");
			resList.add(cpro);
		}
		return resList;
	}

/////////////////////////////////////////////////////////////////////////////   search	
	
	//just search originProject doesn't matter is that real originProject
	@Override
	public OriginProject justSearchOriProjectName(String name,String owner)throws DAOException, ParseException, FolderException{
		// TODO Auto-generated method stub
		//search with projectName
		//just search originProject doesn't matter is that real originProject
		System.out.println("THis is ProjectActionImpl searchProjectName");
		ProjectFolderVDTO proFolderVDTO = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchOwneCode(owner, name);
		if(proFolderVDTO==null)return null;
		
		return this.makeModel(proFolderVDTO);
	}
	@Override
	public OriginProjectList justSearchOriProjectName(String name)throws DAOException, ParseException{
		// TODO Auto-generated method stub
		//just search originProject doesn't matter is that real originProject
		System.out.println("THis is ProjectActionImpl searchProjectName");
		List<ProjectFolderVDTO> proFolderVDTOs = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchProjectName(name);
		if(proFolderVDTOs.isEmpty())return null;
		
		return this.makeModelList(proFolderVDTOs);
	}
	@Override
	public OriginProject justSearchOriProjectCode(String projectCode)throws DAOException, ParseException, FolderException{
		// TODO Auto-generated method stub
		//just search originProject doesn't matter is that real originProject
		System.out.println("THis is ProjectActionImpl searchProjectCode  "+projectCode);
		ProjectFolderVDTO proFolderVDTO = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchProjectCode(projectCode);
		if(proFolderVDTO==null)return null;
		
		return this.makeModel(proFolderVDTO);
	}
	@Override
	public OriginProject justSearchOriProjectPath(String projectPath)throws DAOException, ParseException, FolderException{
		// TODO Auto-generated method stub
		//just search originProject doesn't matter is that real originProject
		System.out.println("THis is ProjectActionImpl searchProjectCode  "+projectPath);
		ProjectFolderVDTO proFolderVDTO = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchPath(projectPath);
		if(proFolderVDTO==null)return null;
		
		return this.makeModel(proFolderVDTO);
	}
	@Override
	public OriginProjectList justSearchOriginProjectTag(String projectTag)throws DAOException, ParseException, FolderException{

		// TODO Auto-generated method stub
		//just search originProject doesn't matter is that real originProject
		
		OriginProjectList oriProList = new OriginProjectList();
		List<ProjectFolderVDTO> oriProVDTOs = new ArrayList<ProjectFolderVDTO>();
		// search tag
		List<ProjectTagsVDTO> proVDTOList=((ProjectTagsVSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("projectTagsVActionImpl")).searchTagName(projectTag);
		if(proVDTOList.isEmpty())return oriProList;
		
		for(ProjectTagsVDTO proTagsVDTO : proVDTOList){
			String projectCode = proTagsVDTO.getProjectCode();
				
			ProjectFolderVDTO proFolderVDTO = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchProjectCode(projectCode);
			if(proFolderVDTO==null)continue;
			oriProVDTOs.add(proFolderVDTO);
		}
		if(!oriProVDTOs.isEmpty())return this.makeModelList(oriProVDTOs);
		return oriProList;
	}
	@Override
	public OriginProjectList justSearchOriginProjectTags(List<String> projectTagList)throws DAOException, ParseException, FolderException{
		// TODO Auto-generated method stub
		//just search originProject doesn't matter is that real originProject
		OriginProjectList oriProList = new OriginProjectList();
		List<ProjectFolderVDTO> oriProVDTOs = new ArrayList<ProjectFolderVDTO>();
		
		// search tag
		List<ProjectTagsVDTO> proVDTOList=((ProjectTagsVSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("projectTagsVActionImpl")).searchTagName(projectTagList);
		if(proVDTOList.isEmpty())return oriProList;
		
		for(ProjectTagsVDTO proTagsVDTO : proVDTOList){
			String projectCode = proTagsVDTO.getProjectCode();
			ProjectFolderVDTO proFolderVDTO = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchProjectCode(projectCode);
			if(proFolderVDTO==null)continue;
			oriProVDTOs.add(proFolderVDTO);
		}
		if(!oriProVDTOs.isEmpty())return this.makeModelList(oriProVDTOs);
		return oriProList;
	}
	@Override
	public OriginProject justSearchOriginProjectFCode(String folderCode)throws DAOException, ParseException, FolderException{
		// TODO Auto-generated method stub
		//just search originProject doesn't matter is that real originProject
		System.out.println(folderCode);
		
		ProjectFolderVDTO proFolderVDTO = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchFolderCode(folderCode);
		if(proFolderVDTO==null)return null;
		
		return this.makeModel(proFolderVDTO);
	}
	@Override
	public OriginProject checkProModifiable(String ownerCode, String projectCode)throws DAOException {
		// TODO Auto-generated method stub
		//no check
		ProjectDTO dto=((ProjectDAO)this.getDAO()).searchProjectCode(ownerCode, projectCode);
		if(dto==null)return null;
		
		OriginProject res = new OriginProject("");
		res.setOwner(dto.getOwner());
		return res;
	}
	
	
	//search Only OriginProject if not return null
	@Override
	public OriginProject searchOnlyOriginProjectPath(String oriProjectPath)throws DAOException, ParseException, FolderException{
		// TODO Auto-generated method stub
		//search Only OriginProject if not return null
		
		//check is origin
		CopiedProjectVDTO cpVDTO = ((CopiedProjectVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedProjectVDAO")).searchCopiedFolderPath(oriProjectPath);
		if(cpVDTO!=null)return null;
			
		ProjectFolderVDTO proFolderVDTO = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchPath(oriProjectPath);
		if(proFolderVDTO==null)return null;
		
		return this.makeModel(proFolderVDTO);
	}
	@Override
	public OriginProject searchOnlyOriginProjectCode(String oriProjectCode)throws DAOException, ParseException, FolderException {

		// TODO Auto-generated method stub
		//search Only OriginProject if not return null
		
		//check is origin
		CopiedProjectVDTO cpVDTO = ((CopiedProjectVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedProjectVDAO")).searchCopiedProjectCode(oriProjectCode);
		if(cpVDTO!=null)return null;
			
		ProjectFolderVDTO proFolderVDTO = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchProjectCode(oriProjectCode);
		if(proFolderVDTO==null)return null;
		
		return this.makeModel(proFolderVDTO);
	}
	@Override
	public OriginProjectList searchOnlyOriginProjectTag(String projectTag)throws DAOException, ParseException, FolderException {

		// TODO Auto-generated method stub
		//search Only OriginProject if not return null
		
		OriginProjectList oriProList = new OriginProjectList();
		List<ProjectFolderVDTO> oriProVDTOs = new ArrayList<ProjectFolderVDTO>();
		// search tag
		List<ProjectTagsVDTO> proVDTOList=((ProjectTagsVSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("projectTagsVActionImpl")).searchTagName(projectTag);
		if(proVDTOList.isEmpty())return oriProList;
		
		for(ProjectTagsVDTO proTagsVDTO : proVDTOList){
			String projectCode = proTagsVDTO.getProjectCode();
			CopiedProjectVDTO cpVDTO = ((CopiedProjectVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedProjectVDAO")).searchCopiedProjectCode(projectCode);
			if(cpVDTO!=null)continue;
				
			ProjectFolderVDTO proFolderVDTO = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchProjectCode(projectCode);
			if(proFolderVDTO==null)continue;
			oriProVDTOs.add(proFolderVDTO);
		}
		if(!oriProVDTOs.isEmpty())return this.makeModelList(oriProVDTOs);
		return oriProList;
	}
	@Override
	public OriginProjectList searchOnlyOriginProjectTags(List<String> projectTagList)throws DAOException, ParseException, FolderException {
		// TODO Auto-generated method stub
		//search Only OriginProject if not return null
		OriginProjectList oriProList = new OriginProjectList();
		List<ProjectFolderVDTO> oriProVDTOs = new ArrayList<ProjectFolderVDTO>();
		
		// search tag
		List<ProjectTagsVDTO> proVDTOList=((ProjectTagsVSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("projectTagsVActionImpl")).searchTagName(projectTagList);
		if(proVDTOList.isEmpty())return oriProList;
		
		for(ProjectTagsVDTO proTagsVDTO : proVDTOList){
			String projectCode = proTagsVDTO.getProjectCode();
			CopiedProjectVDTO cpVDTO = ((CopiedProjectVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedProjectVDAO")).searchCopiedProjectCode(projectCode);
			if(cpVDTO!=null)continue;
				
			ProjectFolderVDTO proFolderVDTO = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchProjectCode(projectCode);
			if(proFolderVDTO==null)continue;
			oriProVDTOs.add(proFolderVDTO);
		}
		if(!oriProVDTOs.isEmpty())return this.makeModelList(oriProVDTOs);
		return oriProList;
	}
	@Override
	public OriginProjectList searchOnlyOriginProjectOwner(String ownerCode)throws DAOException, ParseException, FolderException {
		
		// TODO Auto-generated method stub
		//search Only OriginProject if not return null
		OriginProjectList oriProList = new OriginProjectList();
		List<ProjectFolderVDTO> oriProVDTOs = new ArrayList<ProjectFolderVDTO>();
		
		List<ProjectFolderVDTO> proList= ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchOwneCode(ownerCode);
		if(proList.isEmpty()){return oriProList;}
		 
		for(ProjectFolderVDTO proFolderVDTO : proList){
			String projectCode = proFolderVDTO.getProjectCode(); 
			CopiedInfoDTO cpInfoDTO = ((CopiedInfoDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedInfoDAO")).searchCopiedCode(projectCode);
			if(cpInfoDTO!=null)continue;
			
			oriProVDTOs.add(proFolderVDTO);
		}
		
		if(!oriProVDTOs.isEmpty())return this.makeModelList(oriProVDTOs);
		return oriProList;
	}
	@Override
	public OriginProject searchOnlyOriginProjectOwner(String ownerCode, String projectName)throws DAOException, ParseException, FolderException{
		// TODO Auto-generated method stub
		//search Only OriginProject if not return null
		
		System.out.println("THis is ProjectActionImpl searchProjectName");
		ProjectFolderVDTO proFolderVDTO = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchOwneCode(ownerCode, projectName);
		if(proFolderVDTO==null)return null;
		
		CopiedInfoDTO cpInfoDTO = ((CopiedInfoDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedInfoDAO")).searchCopiedCode(proFolderVDTO.getProjectCode());
		if(cpInfoDTO==null)return this.makeModel(proFolderVDTO);
		return null;
	}
	@Override
	public OriginProjectList searchOnlySharedOriProjectOwner(String ownerCode)throws DAOException, ParseException, FolderException{
		
		// TODO Auto-generated method stub
		//search Only OriginProject if not return null
		List<OriginProjectVDTO> resList = ((CopiedProjectVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedProjectVDAO")).searchOriginOwner(ownerCode);
		List<ProjectFolderVDTO> vdtoList = new ArrayList<ProjectFolderVDTO>();
		
		if(!resList.isEmpty()){
			for(OriginProjectVDTO dto : resList){
				String projectCode = dto.getOriginProjectCode();
				
				ProjectFolderVDTO proFolderVDTO = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchProjectCode(projectCode);
				if(proFolderVDTO==null)continue;
				vdtoList.add(proFolderVDTO);
			}
		}
		
		return this.makeModelList(vdtoList);
	}
	@Override
	public OriginProject searchOnlyOriginProjectFCode(String oriFolderCode)throws DAOException, ParseException, FolderException {
		// TODO Auto-generated method stub
		//search Only OriginProject if not return null
		System.out.println(oriFolderCode);
		
		//check is really origin
		//check is origin
		CopiedProjectVDTO cpVDTO = ((CopiedProjectVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedProjectVDAO")).searchCopiedFolderCode(oriFolderCode);
		if(cpVDTO!=null)return null;
		
		ProjectFolderVDTO proFolderVDTO = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchFolderCode(oriFolderCode);
		if(proFolderVDTO==null)return null;
		
		return this.makeModel(proFolderVDTO);
	}
	
	
	
	//search Only CopiedProject if not return null
	@Override
	public CopiedProject searchOnlyCpProByCpProCode(String copiedProjectCode)throws DAOException, ParseException {

		// TODO Auto-generated method stub
		//search Only CopiedProject if not return null
		System.out.println("THis is ProjectActionImpl searchProjectCode  "+copiedProjectCode);
		
		CopiedProjectVDTO cpVDTO = ((CopiedProjectVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedProjectVDAO")).searchCopiedProjectCode(copiedProjectCode);
		if(cpVDTO==null)return null;
		
		ProjectFolderVDTO vdto = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchProjectCode(cpVDTO.getCopiedProjectCode()); 
		return this.makeCopiedModel(vdto, cpVDTO);
	}
	@Override
	public CopiedProject searchOnlyCpProByCpProPath(String copiedProjectPath)throws DAOException, ParseException{
		
		// TODO Auto-generated method stub
		//search Only CopiedProject if not return null
		System.out.println("THis is ProjectActionImpl searchProjectCode  "+copiedProjectPath);
		
		CopiedProjectVDTO cpVDTO = ((CopiedProjectVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedProjectVDAO")).searchCopiedFolderPath(copiedProjectPath);
		if(cpVDTO==null)return null;
		
		ProjectFolderVDTO vdto = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchProjectCode(cpVDTO.getCopiedProjectCode()); 
		return this.makeCopiedModel(vdto, cpVDTO);
	}
	@Override
	public CopiedProjectList searchOnlyCpProByCpOwnerCode(String cpOwnerCode)throws DAOException, ParseException, FolderException, ProjectException{
		List<CopiedProjectVDTO> cpDTOList = ((CopiedProjectVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedProjectVDAO")).searchCopiedFolderOwner(cpOwnerCode);
		if(!cpDTOList.isEmpty()){return this.makeCopiedModelList(cpDTOList);}
		return new CopiedProjectList();
	}
	@Override
	public CopiedProjectList searchOnlyCpSharedProByCpOwnerCode(String cpOwnerCode)throws DAOException, ParseException, FolderException, ProjectException{
		
		System.out.println("start  :  projectActionImpl searchCopiedSharedProject(String memberCode)");
		//search copied personal project
		List<CopiedProjectVDTO> cpList= ((CopiedProjectVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedProjectVDAO")).searchCopiedMemFolderOwner(cpOwnerCode);
		if(!cpList.isEmpty()){return this.makeCopiedModelList(cpList);}
		
		return new CopiedProjectList();
	}
	@Override
	public CopiedProjectList searchOnlyCpTeamProByCpOwnerCode(String cpOwnerCode)throws DAOException, ParseException, FolderException, ProjectException{
		
		//search Only CopiedProject if not return null
		List<CopiedProjectVDTO> cpList= ((CopiedProjectVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedProjectVDAO")).searchCopiedTeamFolderOwner(cpOwnerCode);
		if(!cpList.isEmpty()){return this.makeCopiedModelList(cpList);}
		
		return new CopiedProjectList();
	}
	@Override
	public CopiedProject searchOnlyCpProByCpOwnerCodeProName(String cpOwnerCode, String projectName)throws DAOException, ParseException, FolderException, ProjectException{
		// TODO Auto-generated method stub
		//search Only CopiedProject if not return null
		
		ProjectFolderVDTO pfVdto = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchOwneCode(cpOwnerCode, projectName);
		if(pfVdto==null)return null;
		
		CopiedProjectVDTO cpDTO = ((CopiedProjectVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedProjectVDAO")).searchCopiedProjectCode(pfVdto.getProjectCode());
		if(cpDTO==null)return null;
		
		return this.makeCopiedModel(pfVdto, cpDTO);
	}
	@Override
	public CopiedProject searchOnlyCpProByCpOwnerCodeOriProCode(String cpOwnerCode,String oriProjectCode)throws DAOException, ParseException{
		// TODO Auto-generated method stub
		//search Only CopiedProject if not return null
		
		CopiedProjectVDTO cpDTO = ((CopiedProjectVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedProjectVDAO")).searchCopiedFolderOwner(cpOwnerCode, oriProjectCode);
		if(cpDTO==null)return null;
		
		ProjectFolderVDTO pfVdto = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchProjectCode(cpDTO.getCopiedProjectCode());
		if(pfVdto==null)return null; 		
		
		return this.makeCopiedModel(pfVdto, cpDTO);
	}
	@Override
	public CopiedProjectList searchOnlyCpProByOriProCode(String oriProjectCode)throws DAOException, ParseException{
		List<CopiedProjectVDTO> cpDTOList = ((CopiedProjectVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedProjectVDAO")).searchOriginProjectCode(oriProjectCode);
		if(!cpDTOList.isEmpty()){return this.makeCopiedModelList(cpDTOList);}
		return new CopiedProjectList();
	}
	@Override
	public CopiedProjectList searchOnlyCpProByOriOwnerCodeCpOwner(String oriOwnerCode,String cpOwnerCode)throws DAOException, ParseException, FolderException, ProjectException{
		// TODO Auto-generated method stub
		//search Only CopiedProject if not return null
		List<CopiedProjectVDTO> cpDTOList = ((CopiedProjectVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedProjectVDAO")).searchOwnerCode(oriOwnerCode, cpOwnerCode);
		if(!cpDTOList.isEmpty()){return this.makeCopiedModelList(cpDTOList);}
		return new CopiedProjectList();
	}
	@Override
	public CopiedProjectList searchOnlyCpProByOriOwner(String oriOwnerCode)throws DAOException, ParseException, FolderException, ProjectException{
		List<CopiedProjectVDTO> cpDTOList = ((CopiedProjectVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedProjectVDAO")).searchOriginFolderOwner(oriOwnerCode);
		if(!cpDTOList.isEmpty()){return this.makeCopiedModelList(cpDTOList);}
		return new CopiedProjectList();
	}
	@Override
	public OriginProject searchOnlyCpProByCpFCode(String cpFolderCode)throws DAOException, ParseException, FolderException{
		// TODO Auto-generated method stub
		//search Only CopiedProject if not return null
		System.out.println("THis is ProjectActionImpl searchProjectCode  "+cpFolderCode);
		
		CopiedProjectVDTO cpVDTO = ((CopiedProjectVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedProjectVDAO")).searchCopiedFolderCode(cpFolderCode);
		if(cpVDTO==null)return null;
		
		ProjectFolderVDTO vdto = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchProjectCode(cpVDTO.getCopiedProjectCode()); 
		return this.makeCopiedModel(vdto, cpVDTO);
	}
	
	
	
	//search
	@Override
	public File searchProjectPath(String projectPath)throws DAOException, ParseException, FolderException{
		CopiedProjectVDTO cpVDTO= ((CopiedProjectVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedProjectVDAO")).searchCopiedFolderPath(projectPath);
		ProjectFolderVDTO vdto = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchPath(projectPath);
		if(cpVDTO!=null){
			return this.makeCopiedModel(vdto, cpVDTO);
		}else return makeModel(vdto);
	}
	@Override
	public File searchProjectCode(String projectCode)throws DAOException, ParseException, FolderException{
		CopiedProjectVDTO cpVDTO= ((CopiedProjectVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedProjectVDAO")).searchCopiedProjectCode(projectCode);
		ProjectFolderVDTO vdto = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchProjectCode(projectCode);
		if(cpVDTO!=null){
			return this.makeCopiedModel(vdto, cpVDTO);
		}else return makeModel(vdto);
	}
	@Override
	public List<File> searchProjectOwner(String ownerCode)throws DAOException, ParseException, FolderException{
		
		List<File> resList = new ArrayList<File>();
		
		List<ProjectFolderVDTO> vdtoList = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchOwneCode(ownerCode);
		if(!vdtoList.isEmpty()){
			Map<ProjectFolderVDTO, CopiedProjectVDTO> cpMap = new HashMap<ProjectFolderVDTO, CopiedProjectVDTO>();
			List<ProjectFolderVDTO> oriList = new ArrayList<ProjectFolderVDTO>();
			for(ProjectFolderVDTO vdto : vdtoList){
				String projectCode = vdto.getProjectCode();
				CopiedProjectVDTO cpVDTO= ((CopiedProjectVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedProjectVDAO")).searchCopiedProjectCode(projectCode);
				if(cpVDTO!=null) oriList.add(vdto);
				else cpMap.put(vdto, cpVDTO);
			}
			OriginProjectList oriProList = this.makeModelList(oriList);
			resList.addAll(oriProList.getList());
			if(!cpMap.isEmpty()){
				CopiedProjectList cpProList = this.makeCopiedModelList(cpMap);
				resList.addAll(cpProList.getList());
			}
		}
		return resList;
		//searchCopiedMemFolderOwner
	}
	@Override
	public List<File> searchPersonalProjectOwner(String ownerCode)throws DAOException, ParseException, FolderException{
		
		List<File> resList = new ArrayList<File>();
		
		//1. origin personal Project
		List<ProjectFolderVDTO> vdtoList = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchOwneCode(ownerCode);
		if(!vdtoList.isEmpty()){
			for(ProjectFolderVDTO vdto : vdtoList){
				//check
				CopiedInfoDTO cpInfoDTO = ((CopiedInfoDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedInfoDAO")).searchCopiedCode(vdto.getProjectCode());
				if(cpInfoDTO==null)resList.add(this.makeModel(vdto));
			}
		}
		
		//2. copied personal Project
		List<CopiedProjectVDTO> cpList= ((CopiedProjectVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedProjectVDAO")).searchCopiedMemFolderOwner(ownerCode);
		if(!cpList.isEmpty()){
			for(CopiedProjectVDTO cpDTO : cpList){
				ProjectFolderVDTO vdto = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchProjectCode(cpDTO.getCopiedProjectCode());
				if(vdto!=null)resList.add(this.makeCopiedModel(vdto, cpDTO));
			}
		}
		
		return resList;
	}
	@Override
	public File searchProjectFCode(String folderCode)throws DAOException, ParseException, FolderException{
		CopiedProjectVDTO cpVDTO= ((CopiedProjectVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedProjectVDAO")).searchCopiedFolderCode(folderCode);
		ProjectFolderVDTO vdto = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchFolderCode(folderCode);
		if(cpVDTO!=null){
			return this.makeCopiedModel(vdto, cpVDTO);
		}else return makeModel(vdto);
	}
	
	/*
	private CopiedProject searchCopiedProjectCode(CopiedProjectVDTO cpVDTO)throws DAOException, ParseException, FolderException, ProjectException {

		// TODO Auto-generated method stub
		System.out.println("THis is ProjectActionImpl searchProjectCode  "+cpVDTO.getCopiedProjectCode());
		ProjectDTO projectDTO=((ProjectDAO)this.getDAO()).searchProjectCode(cpVDTO.getCopiedProjectCode());
		if(projectDTO==null)throw new ProjectException("Error search copied Project");
		
		OriginFolder folder=null;
		
		folder=((FolderSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("folderActionImpl")).searchFolderCode(projectDTO.getFolderCode());
		if(folder==null)return null;
		
		return this.makeCopiedModel(this.makeModel(folder, projectDTO),cpVDTO.getOriginProjectCode(),cpVDTO.getOriginOwnerCode());
	}

	private CopiedProjectList searchCopiedProjectCode(List<CopiedProjectVDTO> cpVDTOList)throws DAOException, ParseException, FolderException, ProjectException {

		// TODO Auto-generated method stub
		System.out.println("THis is ProjectActionImpl searchProjectCode  ");
		CopiedProjectList resList = new CopiedProjectList();
		for(CopiedProjectVDTO cpVDTO : cpVDTOList){

			ProjectDTO projectDTO=((ProjectDAO)this.getDAO()).searchProjectCode(cpVDTO.getCopiedProjectCode());
			if(projectDTO==null)throw new ProjectException("Error search copied Project");
			
			OriginFolder folder=null;
			
			folder=((FolderSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("folderActionImpl")).searchFolderCode(projectDTO.getFolderCode());
			if(folder==null)return null;
			
			CopiedProject cp = this.makeCopiedModel(this.makeModel(folder, projectDTO),cpVDTO.getOriginProjectCode(),cpVDTO.getOriginOwnerCode());
			if(cp!=null)resList.addCopiedProject(cp); 
		}
		return resList;
	}*/
		/*@Override
	public List<File> insertCopiedTeamProject(String teamCode, String teamName,String projectName, String proDes,String path,List<String> tagList) throws DAOException, SftpException, JSchException, FolderException, ProjectException, CommitExcetion, TagException {
		// TODO Auto-generated method stub
		
		List<File> resList = new ArrayList<File>();
		//make remotePath
		String realRemoteRootPath = remoteRootPath+"/"+teamCode;
		String realLocalRootPath = localRootPath+"\\"+teamName;
		
		//1. insert Shared Origin Project
		OriginProject teamOriPro=this.insertOriginProject(teamCode, teamName, projectName, proDes, path+"\\"+projectName, tagList);
		if(teamOriPro==null)throw new ProjectException("Error Insert Team's shared Project");
		resList.add(teamOriPro);
		String teamOriProCode = teamOriPro.getCode();	
		
		//2. get team Manager code
		TeamDTO teamDTO=((TeamDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamDAO")).searchTeamCode(teamCode);
		if(teamDTO==null) return null;
		MemberDTO memDTO=((MemberDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("memberDAO")).searchMemberCode(teamDTO.getManager());
		if(memDTO==null)return null;
		
		//2. get team memberCode
		String memNickName;
		String memCode;
		List<TeamCooperVDTO> teamCooperVDTOList=((TeamCooperVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamCooperVDAO")).searchTeamCode(teamCode);
		if(teamCooperVDTOList.isEmpty()){ 
			if(teamDTO==null) return null;
		}else{
			int cnt = teamCooperVDTOList.size();
			int ciRes;

			for(int i=0;i<cnt;i++){
				memCode = teamCooperVDTOList.get(i).getMemberCode();
				memNickName = teamCooperVDTOList.get(i).getNickName();
				//action for team member
				CopiedProject cpro=this.insertCopiedProject(teamCode,teamOriProCode,teamOriPro.getParentFolder(),memCode, memNickName, teamCode,teamName,projectName, path+"\\"+memNickName+"\\"+projectName);
				if(cpro==null)throw new ProjectException("Error Insert Copied Project");
				resList.add(cpro);
			}
			//action for team manager
			memCode = memDTO.getCode();
			memNickName = memDTO.getNickName();
			CopiedProject cpro=this.insertCopiedProject(teamCode,teamOriProCode,teamOriPro.getParentFolder(),memCode, memNickName, teamCode,teamName,projectName, path+"\\"+memNickName+"\\"+projectName);
			if(cpro==null)throw new ProjectException("Error Insert Copied Project");
			resList.add(cpro);
		}
		return resList;
	}*/
	

	///////////////////////////////////////////////////
	
	/////////////////////////////////   private    ////////////////////////////////
	

	private OriginProject makeModel(OriginFolder folder, ProjectDTO projectDTO,List<String> tags) throws FolderException, ProjectException{
		
		if(folder==null)throw new FolderException("Error Convert FolderInfo");
		if(projectDTO==null)throw new ProjectException("Error Convert ProjectInfo");
		
		OriginProject project = new OriginProject(folder.getPath());
		project.setCode(projectDTO.getCode());
		project.setName(folder.getName());
		project.setDescription(folder.getDescription());
		
		project.setMakeDate(folder.getMakeDate());
		project.setAlterDate(folder.getAlterDate());
		
		project.setSize(folder.getSize());
		project.setParentFolder(folder.getParentFolder());
		project.setChildFolderList(folder.getChildFolderList());
		project.setChildFileList(folder.getChildFileList());
		
		project.setOwner(projectDTO.getOwner());
		project.setTagList(tags);
		System.out.println("make model : "+project.getParentFolder()+" : "+project.getCode());
		return project;
	}
	
	private OriginProject makeModel(ProjectFolderVDTO vdto) throws DAOException, ParseException{
		
		//childFolders
		System.out.println("vdot foldercode : "+vdto.getFolderCode());
		List<FolderDTO> chFolders=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderListPCode(vdto.getFolderCode());
		List<String> childs = new ArrayList<String>();
		if(!chFolders.isEmpty()){
			for(FolderDTO forDTO : chFolders){
				if(forDTO.getFolderCode().equals(forDTO.getParentFolderCode()))continue;
				childs.add(forDTO.getFolderCode());
			}
			System.out.println("FolderActionImple   makeModel  (folder)  :  "+ childs.size());
			
		}
		//childFiles
		List<FileDTO> files=((FileDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("fileDAO")).searchFolderCode(vdto.getFolderCode());
		List<String> chFiles = new ArrayList<String>();
		if(!files.isEmpty()){
			for(FileDTO file : files){
				System.out.println(file.getFileCode());
				chFiles.add(file.getFileCode());
			}
			System.out.println("FolderActionImple   makeModel  (file) :  "+ chFiles.size());
			
		}
		
		//tag
		List<ProjectTagsVDTO> tags=((ProjectTagsVActionImpl)ActionFactory.getACTIONFACTORY_INSTANCE().create("projectTagsVActionImpl")).searchPCode(vdto.getProjectCode());
		List<String> tagList = new ArrayList<String>();
		if(!tags.isEmpty()){
			System.out.println("Yes Tag!!!");
			for(int j=0;j<tags.size();j++){
				tagList.add(tags.get(j).getTagName());
			}
			
		}
		//sharedMember
		List<ProjectSharedMemVDTO> sharedList=((ProjectSharedMemVActionImpl)ActionFactory.getACTIONFACTORY_INSTANCE().create("projectSharedMemVActionImpl")).searchProjectCode(vdto.getProjectCode());
		List<String> shareds = new ArrayList<String>();
		if(!sharedList.isEmpty()){
			System.out.println("Yes shared!!!");
			for(int j=0;j<sharedList.size();j++){
				shareds.add(sharedList.get(j).getMemberCode());
			}
			
		}
		OriginProject project = new OriginProject(vdto.getPath());
		
		project.setCode(vdto.getProjectCode());
		project.setOwner(vdto.getOwner());
		project.setName(vdto.getName());
		project.setDescription(vdto.getDescription());
		project.setPath(vdto.getPath());
		project.setMakeDate(vdto.getInsertDate());
		project.setAlterDate(vdto.getAlterDate());
		project.setParentFolder(vdto.getParentFolderCode());
		project.setChildFolderList(childs);
		project.setChildFileList(chFiles);
		project.setTagList(tagList);
		project.setSharedMemberList(shareds);
		
		return project;
	}
	private OriginProjectList makeModelList(List<ProjectFolderVDTO> vdtoList) throws DAOException, ParseException{
		
		OriginProjectList resList = new OriginProjectList();
		
		for(ProjectFolderVDTO vdto : vdtoList){
			
			//childFolders
			List<FolderDTO> chFolders=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderListPCode(vdto.getFolderCode());
			List<String> childs = new ArrayList<String>();
			if(!chFolders.isEmpty()){
				for(FolderDTO forDTO : chFolders){
					if(forDTO.getFolderCode().equals(forDTO.getParentFolderCode()))continue;
					childs.add(forDTO.getFolderCode());
				}
				System.out.println("FolderActionImple   makeModel  (folder)  :  "+ childs.size());
				
			}
			//childFiles
			List<FileDTO> files=((FileDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("fileDAO")).searchFolderCode(vdto.getFolderCode());
			List<String> chFiles = new ArrayList<String>();
			if(!files.isEmpty()){
				for(FileDTO file : files){
					System.out.println(file.getFileCode());
					chFiles.add(file.getFileCode());
				}
				System.out.println("FolderActionImple   makeModel  (file) :  "+ chFiles.size());
				
			}
			
			//tag
			List<ProjectTagsVDTO> tags=((ProjectTagsVActionImpl)ActionFactory.getACTIONFACTORY_INSTANCE().create("projectTagsVActionImpl")).searchPCode(vdto.getProjectCode());
			List<String> tagList = new ArrayList<String>();
			if(!tags.isEmpty()){
				System.out.println("Yes Tag!!!");
				for(int j=0;j<tags.size();j++){
					tagList.add(tags.get(j).getTagName());
				}
				
			}
			//sharedMember
			List<ProjectSharedMemVDTO> sharedList=((ProjectSharedMemVActionImpl)ActionFactory.getACTIONFACTORY_INSTANCE().create("projectSharedMemVActionImpl")).searchProjectCode(vdto.getProjectCode());
			List<String> shareds = new ArrayList<String>();
			if(!sharedList.isEmpty()){
				System.out.println("Yes shared!!!");
				for(int j=0;j<sharedList.size();j++){
					shareds.add(sharedList.get(j).getMemberCode());
				}
				
			}
			OriginProject project = new OriginProject(vdto.getPath());
			
			project.setCode(vdto.getProjectCode());
			project.setOwner(vdto.getOwner());
			project.setName(vdto.getName());
			project.setDescription(vdto.getDescription());
			project.setPath(vdto.getPath());
			project.setMakeDate(vdto.getInsertDate());
			project.setAlterDate(vdto.getAlterDate());
			project.setParentFolder(vdto.getParentFolderCode());
			project.setChildFolderList(childs);
			project.setChildFileList(chFiles);
			project.setTagList(tagList);
			project.setSharedMemberList(shareds);
			
			resList.addOriginPorject(project);
		}

		return resList;
	}


	private CopiedProject makeCopiedModel(OriginFolder cfolder, ProjectDTO cprojectDTO,String originCode,String originOwnerCode) throws FolderException, ProjectException{
		
		if(cfolder==null)throw new FolderException("Error Convert FolderInfo");
		if(cprojectDTO==null)throw new ProjectException("Error Convert ProjectInfo");
		
		CopiedProject project = new CopiedProject(cfolder.getPath(), originCode, originOwnerCode,cfolder.getAlterDate());
		project.setCode(cprojectDTO.getCode());
		project.setName(cfolder.getName());
		project.setDescription(cfolder.getDescription());
		
		project.setMakeDate(cfolder.getMakeDate());
		project.setAlterDate(cfolder.getAlterDate());
		
		project.setSize(cfolder.getSize());
		project.setParentFolder(cfolder.getParentFolder());
		project.setChildFolderList(cfolder.getChildFolderList());
		project.setChildFileList(cfolder.getChildFileList());
		
		project.setOwner(cprojectDTO.getOwner());
		return project;
	}
	
	private CopiedProject makeCopiedModel(ProjectFolderVDTO vdto, CopiedProjectVDTO cpVdto) throws DAOException, ParseException{
		
		
		//childFolders
		List<FolderDTO> chFolders=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderListPCode(vdto.getFolderCode());
		List<String> childs = new ArrayList<String>();
		if(!chFolders.isEmpty()){
			for(FolderDTO forDTO : chFolders){
				if(forDTO.getFolderCode().equals(forDTO.getParentFolderCode()))continue;
				childs.add(forDTO.getFolderCode());
			}
			System.out.println("FolderActionImple   makeModel  (folder)  :  "+ childs.size());
			
		}
		//childFiles
		List<FileDTO> files=((FileDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("fileDAO")).searchFolderCode(vdto.getFolderCode());
		List<String> chFiles = new ArrayList<String>();
		if(!files.isEmpty()){
			for(FileDTO file : files){
				System.out.println(file.getFileCode());
				chFiles.add(file.getFileCode());
			}
			System.out.println("FolderActionImple   makeModel  (file) :  "+ chFiles.size());
			
		}
		
		//tag
		List<ProjectTagsVDTO> tags=((ProjectTagsVActionImpl)ActionFactory.getACTIONFACTORY_INSTANCE().create("projectTagsVActionImpl")).searchPCode(vdto.getProjectCode());
		List<String> tagList = new ArrayList<String>();
		if(!tags.isEmpty()){
			System.out.println("Yes Tag!!!");
			for(int j=0;j<tags.size();j++){
				tagList.add(tags.get(j).getTagName());
			}
			
		}
		//sharedMember
		List<ProjectSharedMemVDTO> sharedList=((ProjectSharedMemVActionImpl)ActionFactory.getACTIONFACTORY_INSTANCE().create("projectSharedMemVActionImpl")).searchProjectCode(vdto.getProjectCode());
		List<String> shareds = new ArrayList<String>();
		if(!sharedList.isEmpty()){
			System.out.println("Yes shared!!!");
			for(int j=0;j<sharedList.size();j++){
				shareds.add(sharedList.get(j).getMemberCode());
			}
			
		}
		CopiedProject project = new CopiedProject(vdto.getPath(),cpVdto.getOriginProjectCode(),cpVdto.getOriginOwnerCode(),vdto.getAlterDate());
		
		project.setCode(vdto.getProjectCode());
		project.setOwner(vdto.getOwner());
		project.setName(vdto.getName());
		project.setDescription(vdto.getDescription());
		project.setPath(vdto.getPath());
		project.setMakeDate(vdto.getInsertDate());
		project.setAlterDate(vdto.getAlterDate());
		project.setParentFolder(vdto.getParentFolderCode());
		project.setChildFolderList(childs);
		project.setChildFileList(chFiles);
		project.setTagList(tagList);
		project.setSharedMemberList(shareds);
		
		
		return project;
	}
	
	private CopiedProjectList makeCopiedModelList(Map<ProjectFolderVDTO,CopiedProjectVDTO> vdtoMap) throws DAOException, ParseException{
		
		CopiedProjectList resList = new CopiedProjectList();
		if(vdtoMap.isEmpty())return resList;
		
		for(ProjectFolderVDTO vdto : vdtoMap.keySet()){
			CopiedProjectVDTO cpVdto = vdtoMap.get(vdto);
			
			//childFolders
			List<FolderDTO> chFolders=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderListPCode(vdto.getFolderCode());
			List<String> childs = new ArrayList<String>();
			if(!chFolders.isEmpty()){
				for(FolderDTO forDTO : chFolders){
					if(forDTO.getFolderCode().equals(forDTO.getParentFolderCode()))continue;
					childs.add(forDTO.getFolderCode());
				}
				System.out.println("FolderActionImple   makeModel  (folder)  :  "+ childs.size());
				
			}
			//childFiles
			List<FileDTO> files=((FileDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("fileDAO")).searchFolderCode(vdto.getFolderCode());
			List<String> chFiles = new ArrayList<String>();
			if(!files.isEmpty()){
				for(FileDTO file : files){
					System.out.println(file.getFileCode());
					chFiles.add(file.getFileCode());
				}
				System.out.println("FolderActionImple   makeModel  (file) :  "+ chFiles.size());
				
			}
			
			//tag
			List<ProjectTagsVDTO> tags=((ProjectTagsVActionImpl)ActionFactory.getACTIONFACTORY_INSTANCE().create("projectTagsVActionImpl")).searchPCode(vdto.getProjectCode());
			List<String> tagList = new ArrayList<String>();
			if(!tags.isEmpty()){
				System.out.println("Yes Tag!!!");
				for(int j=0;j<tags.size();j++){
					tagList.add(tags.get(j).getTagName());
				}
				
			}
			//sharedMember
			List<ProjectSharedMemVDTO> sharedList=((ProjectSharedMemVActionImpl)ActionFactory.getACTIONFACTORY_INSTANCE().create("projectSharedMemVActionImpl")).searchProjectCode(vdto.getProjectCode());
			List<String> shareds = new ArrayList<String>();
			if(!sharedList.isEmpty()){
				System.out.println("Yes shared!!!");
				for(int j=0;j<sharedList.size();j++){
					shareds.add(sharedList.get(j).getMemberCode());
				}
				
			}
			CopiedProject project = new CopiedProject(vdto.getPath(),cpVdto.getOriginProjectCode(),cpVdto.getOriginOwnerCode(),vdto.getAlterDate());
			
			project.setCode(vdto.getProjectCode());
			project.setOwner(vdto.getOwner());
			project.setName(vdto.getName());
			project.setDescription(vdto.getDescription());
			project.setPath(vdto.getPath());
			project.setMakeDate(vdto.getInsertDate());
			project.setAlterDate(vdto.getAlterDate());
			project.setParentFolder(vdto.getParentFolderCode());
			project.setChildFolderList(childs);
			project.setChildFileList(chFiles);
			project.setTagList(tagList);
			project.setSharedMemberList(shareds);
			
			resList.addCopiedProject(project);
		}
		
		return resList;
	}

	private CopiedProjectList makeCopiedModelList(List<CopiedProjectVDTO> cpVdtoList) throws DAOException, ParseException{
		
		CopiedProjectList resList = new CopiedProjectList();
		if(cpVdtoList.isEmpty())return resList;
		
		for(CopiedProjectVDTO cpVdto : cpVdtoList){
			ProjectFolderVDTO vdto = ((ProjectFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectFolderVDAO")).searchProjectCode(cpVdto.getCopiedProjectCode());
			if(cpVdto==null)continue;
			
			//childFolders
			List<FolderDTO> chFolders=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderListPCode(vdto.getFolderCode());
			List<String> childs = new ArrayList<String>();
			if(!chFolders.isEmpty()){
				for(FolderDTO forDTO : chFolders){
					if(forDTO.getFolderCode().equals(forDTO.getParentFolderCode()))continue;
					childs.add(forDTO.getFolderCode());
				}
				System.out.println("FolderActionImple   makeModel  (folder)  :  "+ childs.size());
				
			}
			//childFiles
			List<FileDTO> files=((FileDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("fileDAO")).searchFolderCode(vdto.getFolderCode());
			List<String> chFiles = new ArrayList<String>();
			if(!files.isEmpty()){
				for(FileDTO file : files){
					System.out.println(file.getFileCode());
					chFiles.add(file.getFileCode());
				}
				System.out.println("FolderActionImple   makeModel  (file) :  "+ chFiles.size());
				
			}
			
			//tag
			List<ProjectTagsVDTO> tags=((ProjectTagsVActionImpl)ActionFactory.getACTIONFACTORY_INSTANCE().create("projectTagsVActionImpl")).searchPCode(vdto.getProjectCode());
			List<String> tagList = new ArrayList<String>();
			if(!tags.isEmpty()){
				System.out.println("Yes Tag!!!");
				for(int j=0;j<tags.size();j++){
					tagList.add(tags.get(j).getTagName());
				}
				
			}
			//sharedMember
			List<ProjectSharedMemVDTO> sharedList=((ProjectSharedMemVActionImpl)ActionFactory.getACTIONFACTORY_INSTANCE().create("projectSharedMemVActionImpl")).searchProjectCode(vdto.getProjectCode());
			List<String> shareds = new ArrayList<String>();
			if(!sharedList.isEmpty()){
				System.out.println("Yes shared!!!");
				for(int j=0;j<sharedList.size();j++){
					shareds.add(sharedList.get(j).getMemberCode());
				}
				
			}
			CopiedProject project = new CopiedProject(vdto.getPath(),cpVdto.getOriginProjectCode(),cpVdto.getOriginOwnerCode(),vdto.getAlterDate());
			
			project.setCode(vdto.getProjectCode());
			project.setOwner(vdto.getOwner());
			project.setName(vdto.getName());
			project.setDescription(vdto.getDescription());
			project.setPath(vdto.getPath());
			project.setMakeDate(vdto.getInsertDate());
			project.setAlterDate(vdto.getAlterDate());
			project.setParentFolder(vdto.getParentFolderCode());
			project.setChildFolderList(childs);
			project.setChildFileList(chFiles);
			project.setTagList(tagList);
			project.setSharedMemberList(shareds);
			
			resList.addCopiedProject(project);
		}
		
		return resList;
	}
}
