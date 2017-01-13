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
import java.util.StringTokenizer;

import model.CommitInfo;
import model.CopiedFile;
import model.CopiedFolder;
import model.FileNode;
import model.OriginFolder;
import model.OriginFolderList;
import remoteAction.RemoteFolderInsertAction;
import socialProExceptions.CommitExcetion;
import socialProExceptions.DAOException;
import socialProExceptions.FileException;
import socialProExceptions.FolderException;
import socialProExceptions.RemoteFileException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import dao.CopiedFolderVDAO;
import dao.CopiedInfoDAO;
import dao.CopiedProjectVDAO;
import dao.DAO;
import dao.FileDAO;
import dao.FolderDAO;
import dto.CopiedFolderDTO;
import dto.CopiedInfoDTO;
import dto.CopiedProjectVDTO;
import dto.FileDTO;
import dto.FolderDTO;
import factory.ActionFactory;
import factory.DAOFactory;
import factory.RemoteActionFactory;

public class FolderActionImpl extends BaseAction implements FolderSearchAction,FolderInsertAction,FolderDropAction,FolderUpdateAction{

	private String remoteRootPath="/home/socialPro";
	public FolderActionImpl() {
		// TODO Auto-generated constructor stub
		this.setDAO((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO"));
	}
	@Override
	public void setDAO(DAO dao) {
		// TODO Auto-generated method stub
		super.dao = (FolderDAO)dao;
	}
	
	public String insertCopiedFolder(CopiedFolder folder)throws DAOException{
		return null;
	}
	
	@Override
	public OriginFolder insertOriginFolder(OriginFolder folder,String ownerCode) throws DAOException, SftpException, JSchException, FolderException {
		// TODO Auto-generated method stub
		System.out.println("FolderActionImpl      insertOriginFolder     73");
		FolderDTO dto = new FolderDTO();
		
		//parent folderPath
		String remotePath =null;
		if(folder.getParentFolder()==null){
			remotePath=remoteRootPath+"/"+ownerCode;
		}else{
			//List<FolderDTO> pDTO=((FolderDAO)this.getDAO()).searchFolderPCode(folder.getParentFolder());
			FolderDTO pDTO=((FolderDAO)this.getDAO()).searchFolderCode(folder.getParentFolder());
			if(pDTO!=null){
				remotePath=pDTO.getFolderContent();
			}
		}
		
		dto.setName(folder.getName());
		dto.setDescription(folder.getDescription());
		dto.setPath(folder.getPath());
		dto.setFolderContent(remotePath);
		
		dto.setInsertDate(folder.getMakeDate());
		
		dto.setSizes(folder.getSize());
		dto.setParentFolderCode(folder.getParentFolder());
		System.out.println("pcode : "+folder.getParentFolder());
		System.out.println("pcode : "+dto.getParentFolderCode());
		String folderCode = this.insertFolder(dto);
		folder.setCode(folderCode);
		return folder;
	}
	@Override
	public OriginFolder insertOriginProjectFolder(OriginFolder folder,String ownerCode) throws DAOException, SftpException, JSchException, FolderException {
		// TODO Auto-generated method stub
		System.out.println("FolderActionImpl      insertOriginProjectFolder      116");
		FolderDTO dto = new FolderDTO();
		
		//parent folderPath
		String remotePath =null;
		if(folder.getParentFolder()==null){
			remotePath=remoteRootPath+"/"+ownerCode;
		}else{
			//List<FolderDTO> pDTO=((FolderDAO)this.getDAO()).searchFolderPCode(folder.getParentFolder());
			FolderDTO pDTO=((FolderDAO)this.getDAO()).searchFolderCode(folder.getParentFolder());
			if(pDTO!=null){
				remotePath=pDTO.getFolderContent();
			}
		}
		
		
		dto.setName(folder.getName());
		dto.setDescription(folder.getDescription());
		dto.setPath(folder.getPath());
		dto.setFolderContent(remotePath);
		
		dto.setInsertDate(folder.getMakeDate());
		
		dto.setSizes(folder.getSize());
		dto.setParentFolderCode(folder.getParentFolder());
		System.out.println("pcode : "+folder.getParentFolder());
		System.out.println("pcode : "+dto.getParentFolderCode());

		//insert commit info
		String folderCode = this.insertFolder(dto);
		folder.setCode(folderCode);
		folder.setParentFolder(folderCode);
		
		System.out.println("pcode : "+folder.getParentFolder());
		System.out.println("pcode : "+dto.getParentFolderCode());

		return folder;
	}
	
	/*@Override
	public OriginFolder insertOriginFolder(OriginFolder folder,String ownerCode) throws DAOException, SftpException, JSchException, FolderException{
		// TODO Auto-generated method stub
		FolderDTO dto = new FolderDTO();
		
		//parent folderPath
		String remotePath =null;
		if(folder.getParentFolder()==null){
			remotePath=remoteRootPath+"/"+ownerCode;
		}else{
			List<FolderDTO> pDTO=((FolderDAO)this.getDAO()).searchFolderPCode(folder.getParentFolder());
			if(!pDTO.isEmpty()){
				remotePath=pDTO.get(0).getFolderContent();
			}
		}
		System.out.println("remotePath : "+remotePath);
	
		dto.setName(folder.getName());
		dto.setDescription(folder.getDescription());
		dto.setPath(folder.getPath());
		dto.setFolderContent(remotePath);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		df.setTimeZone(folder.getMakeDate().getTimeZone());
		dto.setInsertDate(df.format(folder.getMakeDate().getTime()));
		dto.setParentFolderCode(folder.getParentFolder());
		dto.setSizes(folder.getSize());

		String folderCode = this.insertFolder(dto);
		if(folderCode==null)throw new FolderException("Error Insert Folder");
		folder.setCode(folderCode);
		if(folder.getParentFolder()==null) folder.setParentFolder(folderCode);
		return folder;
	}*/
	
	protected String insertFolder(FolderDTO dto)throws DAOException, SftpException, JSchException, FolderException{
		
		System.out.println("insertdate : "+dto.getInsertDate());
		String folderCode = ((FolderDAO)this.getDAO()).insertFolder(dto);
		if(folderCode==null) throw new FolderException("Error Inset Folder");
		String remotePa =dto.getFolderContent()+"/"+folderCode;
		if(folderCode!=null){
			synchronized (folderCode) {
				System.out.println(remotePa);
				((RemoteFolderInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFolderActionImpl")).insertOriginFolder(remotePa);
			}
		}
		return folderCode;
	}
	
	/*@Override
	public Map<String,CopiedFolder> insertTeamFolder(OriginFolder folder,String ownerCode)throws DAOException, SftpException, JSchException, FolderException, CommitExcetion{
		
		Map<String,CopiedFolder> resList = new HashMap<String, CopiedFolder>();  
		
		//insert team shared folder
		OriginFolder shFolder = this.insertOriginFolder(folder, ownerCode);
		
		//insert team member's folder
		List<CopiedFolderDTO> pcfDTOList= ((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchOriginFolderCode(folder.getParentFolder());
		
		resList.put(ownerCode,new CopiedFolder(shFolder.getCode(), shFolder.getName(), shFolder.getDescription(), shFolder.getPath(), shFolder.getMakeDate(), shFolder.getParentFolder(), new ArrayList<String>(), null, null, new GregorianCalendar()));
		String folderName = folder.getName();
		for(CopiedFolderDTO cfDTO : pcfDTOList){
			
			CopiedFolder cf = new CopiedFolder(cfDTO.getCopiedFolderPath()+"\\"+folderName);
			cf.setName(folderName);
			cf.setDescription(folder.getDescription());
			cf.setMakeDate(new GregorianCalendar());
			cf.setSize(0);
			cf.setParentFolder(cfDTO.getCopiedFolderCode());
			cf.setOriginCode(folder.getCode());
			cf.setOriginParentCode(folder.getParentFolder());
			
			resList.put(cfDTO.getCopiedOwnerCode(), this.insertCopiedFolder(cf, ownerCode, folder.getCode(),cfDTO.getCopiedOwnerCode()));
		}
		return resList;
	}*/
	
	@Override
	public Map<String,CopiedFolder> insertTeamFolder(OriginFolder folder,String ownerCode)throws DAOException, SftpException, JSchException, FolderException{

		Map<String,CopiedFolder> resList = new HashMap<String, CopiedFolder>();  
		
		//insert team shared folder
		OriginFolder shFolder = this.insertOriginFolder(folder, ownerCode);
		System.out.println("Origin Folder InsertOriginFolder   ::::::::::    is End  ::  oriCode --->"+shFolder.getCode()+" oriParentCode :::::  "+shFolder.getParentFolder());
		//commit Info
		/*DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		df.setTimeZone(shFolder.getMakeDate().getTimeZone());
		((CommitDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("commitDAO")).insertCommit(new CommitDTO(commitTitle, ownerCode, df.format(shFolder.getMakeDate().getTime()), shFolder.getCode(), commitContent, 0, 0));
		*/
		//insert team member's folder
		List<CopiedFolderDTO> pcfDTOList= ((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchOriginFolderCode(folder.getParentFolder());
		if(pcfDTOList.isEmpty())System.out.println("copied Folder VDTO List is Empty");
		else System.out.println("size   :::::     "+pcfDTOList.size());
		
		resList.put(ownerCode,new CopiedFolder(shFolder.getCode(), shFolder.getPath(), shFolder.getName(), shFolder.getMakeDate(), shFolder.getParentFolder(), shFolder.getDescription(), 0, null,null));
		String folderName = folder.getName();
		for(CopiedFolderDTO cfDTO : pcfDTOList){
			
			CopiedFolder cf = new CopiedFolder(cfDTO.getCopiedFolderPath()+"\\"+folderName);
			cf.setName(folderName);
			cf.setDescription(folder.getDescription());
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			GregorianCalendar cal = new GregorianCalendar();
			df.setTimeZone(cal.getTimeZone());
			folder.setMakeDate(df.format(cal.getTime()));
			
			cf.setSize(0);
			cf.setParentFolder(cfDTO.getCopiedFolderCode());
			cf.setOriginCode(folder.getCode());
			cf.setOriginParentCode(folder.getParentFolder());
			
			resList.put(cfDTO.getCopiedOwnerCode(), this.insertCopiedFolder(cf, ownerCode, folder.getCode(),cfDTO.getCopiedOwnerCode()));
			
		}
		if(resList.isEmpty())System.out.println("resList is empty!!!!!!!!!!!!!!!!!!!!!!!!!");
		else System.out.println("resList size    :::::   =================>  "+resList.size());
		return resList;
	}


	@Override
	public String insertOriTeamFolder(OriginFolder folder, String teamCode) throws DAOException, SftpException,
			JSchException, FolderException {
		// TODO Auto-generated method stub
		FolderDTO dto = new FolderDTO();
		
		String remotePath = remoteRootPath+"/"+teamCode;

		dto.setName(folder.getName());
		dto.setDescription(folder.getDescription());
		dto.setPath(folder.getPath());
		dto.setFolderContent(remotePath);
		
		dto.setInsertDate(folder.getMakeDate());
		
		dto.setSizes(folder.getSize());
		dto.setParentFolderCode(folder.getParentFolder());
		return this.insertFolder(dto);
	}

	@Override
	public CopiedFolder insertCopiedProjectFolder(CopiedFolder folder, String originOwnerCode,String oriFolderCode,String memberCode) throws DAOException,SftpException, JSchException, FolderException, CommitExcetion {
		// TODO Auto-generated method stub
		System.out.println("FolderActionImpl  for projectFolder  insertCopiedProjectFolder...   454");
		FolderDTO dto = new FolderDTO(folder.getPath());
		dto.setName(folder.getName());
		dto.setDescription(folder.getDescription());
		dto.setParentFolderCode(folder.getParentFolder());
		dto.setSizes(folder.getSize());
		
		dto.setInsertDate(folder.getMakeDate());
		dto.setAlterDate(folder.getAlterDate());
		
		//parent folderPath
		String remotePath =null;
		String token= new StringTokenizer(originOwnerCode, "_").nextToken();
		if(folder.getParentFolder()==null){
			if(token.equals("team"))remotePath=remoteRootPath+"/"+originOwnerCode+"/"+memberCode;
			else if(token.equals("member"))remotePath=remoteRootPath+"/"+memberCode;
		}else{
			System.out.println("goto find parentFolder content (remotePath)"+folder.getParentFolder());
			FolderDTO pDTO=((FolderDAO)this.getDAO()).searchFolderCode(folder.getParentFolder());
			if(pDTO!=null){
				remotePath=pDTO.getFolderContent();
				System.out.println(remotePath);
			}
		}
		dto.setFolderContent(remotePath);
		
		//insert data into folder_tb
		String cfCode = this.insertFolder(dto);
		if(cfCode==null) throw new FolderException("Error Insert Team Folder");
		folder.setCode(cfCode);
		folder.setParentFolder(cfCode);
		
		//insert data into copiedinfo_tb
		((CopiedInfoDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedInfoDAO")).insertCopiedInfo(new CopiedInfoDTO(cfCode, folder.getOriginCode()));
		
		//copy commit info
		((CommitInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("commitActionImpl")).copyCommit(memberCode, cfCode, folder.getMakeDate(), oriFolderCode, 0);		
			
		if(folder!=null)System.out.println(" Copied_Folder_Code   :::  "+folder.getCode()+"   Copied_Folder_Parent_Folder_Code  ::: "+folder.getParentFolder());
		else System.out.println("res folder is null...");
		/*synchronized (cfCode) {
			String remotePath = pfDTO.getFolderContent()+"/"+cfCode;
			System.out.println(remotePath);
			((RemoteFolderInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFolderActionImpl")).insertOriginFolder(remotePath);
		}
		*/
		return folder;
	}
	
	/*@Override
	public CopiedFolder insertCopiedFolder(CopiedFolder folder, String originOwnerCode,String oriFolderCode,String memberCode) throws DAOException,SftpException, JSchException, FolderException, CommitExcetion {
		// TODO Auto-generated method stub
		
		FolderDTO dto = new FolderDTO(folder.getPath());
		dto.setName(folder.getName());
		dto.setDescription(folder.getDescription());
		dto.setParentFolderCode(folder.getParentFolder());
		dto.setSizes(folder.getSize());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		df.setTimeZone(folder.getMakeDate().getTimeZone());
		dto.setInsertDate(df.format(folder.getMakeDate().getTime()));
		if(folder.getAlterDate()!=null){
			df.setTimeZone(folder.getAlterDate().getTimeZone());
			dto.setAlterDate(df.format(folder.getAlterDate().getTime()));
		}else{
			dto.setAlterDate(null);
		}
		
		//parent folderPath
		String remotePath =null;
		String token= new StringTokenizer(originOwnerCode, "_").nextToken();
		if(folder.getParentFolder()==null){
			if(token.equals("team"))remotePath=remoteRootPath+"/"+originOwnerCode+"/"+memberCode;
			else if(token.equals("member"))remotePath=remoteRootPath+"/"+memberCode;
		}else{
			System.out.println("goto find parentFolder content (remotePath)"+folder.getParentFolder());
			FolderDTO pDTO=((FolderDAO)this.getDAO()).searchFolderCode(folder.getParentFolder());
			if(pDTO!=null){
				remotePath=pDTO.getFolderContent();
				System.out.println(remotePath);
			}
		}
		dto.setFolderContent(remotePath);
		
		//insert data into folder_tb
		String cfCode = this.insertFolder(dto);
		if(cfCode==null) throw new FolderException("Error Insert Team Folder");
		folder.setCode(cfCode);
		//if(folder.getParentFolder()==null){folder.setParentFolder(cfCode);}
		
		//insert data into copiedinfo_tb
		((CopiedInfoDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedInfoDAO")).insertCopiedInfo(new CopiedInfoDTO(cfCode, folder.getOriginCode()));
		
		//copy commit info
		((CommitInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("commitActionImpl")).copyCommit(memberCode, cfCode, folder.getMakeDate(), oriFolderCode, 0);		
				
		synchronized (cfCode) {
			String remotePath = pfDTO.getFolderContent()+"/"+cfCode;
			System.out.println(remotePath);
			((RemoteFolderInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFolderActionImpl")).insertOriginFolder(remotePath);
		}
		
		return folder;
	}*/
	
	@Override
	public CopiedFolder insertCopiedFolder(CopiedFolder folder,String originOwnerCode,String oriFolderCode,String memberCode)throws DAOException, SftpException, JSchException, FolderException{
		System.out.println("FolderActionImpl   insertCopiedFolder  (for res Folder..) :   567");
		FolderDTO dto = new FolderDTO(folder.getPath());
		dto.setName(folder.getName());
		dto.setDescription(folder.getDescription());
		dto.setParentFolderCode(folder.getParentFolder());
		dto.setSizes(folder.getSize());
		
		dto.setInsertDate(folder.getMakeDate());
		System.out.println(folder.getMakeDate());
		dto.setAlterDate(folder.getAlterDate());
		
		//parent folderPath
		String remotePath =null;
		String token= new StringTokenizer(originOwnerCode, "_").nextToken();
		if(folder.getParentFolder()==null){
			if(token.equals("team"))remotePath=remoteRootPath+"/"+originOwnerCode+"/"+memberCode;
			else if(token.equals("member"))remotePath=remoteRootPath+"/"+memberCode;
		}else{
			System.out.println("goto find parentFolder content (remotePath)"+folder.getParentFolder());
			FolderDTO pDTO=((FolderDAO)this.getDAO()).searchFolderCode(folder.getParentFolder());
			if(pDTO!=null){
				remotePath=pDTO.getFolderContent();
				System.out.println(remotePath);
			}
		}
		dto.setFolderContent(remotePath);
		
		//insert data into folder_tb
		String cfCode = this.insertFolder(dto);
		if(cfCode==null) throw new FolderException("Error Insert Team Folder");
		folder.setCode(cfCode);
		if(folder.getParentFolder()==null){folder.setParentFolder(cfCode);}
		
		//insert data into copiedinfo_tb
		((CopiedInfoDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedInfoDAO")).insertCopiedInfo(new CopiedInfoDTO(cfCode, folder.getOriginCode()));
		
		//copy commit info
		if(folder!=null)System.out.println(" Copied_Folder_Code   :::  "+folder.getCode()+"   Copied_Folder_Parent_Folder_Code  ::: "+folder.getParentFolder());
		else System.out.println("res folder is null...");
		System.out.println("CopiedInfo End....*()*");
		return folder;		
	}
	
	@Override
	public boolean insertCopiedFolders(FileNode folder,String originOwnerCode,String memberCode)throws DAOException, SftpException, JSchException, FolderException, FileNotFoundException, ParseException, IOException, FileException, RemoteFileException, CommitExcetion{
		
		 CopiedProjectVDTO cpDTO = ((CopiedProjectVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedProjectVDAO")).searchOriginProjectCode(folder.getCode(),memberCode);
		 String copiedParentCode = cpDTO.getCopiedFolderCode();
		 return this.insertCopiedFolders(folder, originOwnerCode, memberCode, copiedParentCode);
		
	}
	
	private boolean insertCopiedFolders(FileNode oirginFileNode,String originOwnerCode,String memberCode,String copiedParentCode) throws DAOException, ParseException, FolderException, SftpException, JSchException, FileNotFoundException, IOException, FileException, RemoteFileException, CommitExcetion{
		
		System.out.println("insertCopiedFolders : FolderActionImpl  ;;;;   "+copiedParentCode);
		//get child
	 	FolderDTO copiedFolderDTO = ((FolderDAO)this.getDAO()).searchFolderCode(copiedParentCode);
		List<String> orichildFolderList = oirginFileNode.getChildFolderList();
		List<String> orichildFileList = oirginFileNode.getChildFileList();
		
		for(String oriFileCode : orichildFileList){
			FileDTO oirFileDTO = ((FileDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("fileDAO")).searchFileCode(oriFileCode);
			String path = copiedFolderDTO.getPath()+"\\"+oirFileDTO.getName();
			System.out.println("parent remotePath : "+copiedFolderDTO.getFolderContent());
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			GregorianCalendar cal = new GregorianCalendar();
			df.setTimeZone(cal.getTimeZone());
			
			//(, path, , ,,cfileCode,fileDTO.getFolderCode())
			CopiedFile cpFile = new CopiedFile(oirFileDTO.getName(), path, df.format(cal.getTime()), copiedParentCode, copiedFolderDTO.getFolderContent(), 0,oriFileCode,oirFileDTO.getFolderCode());
			cpFile.setName(oirFileDTO.getName());
			cpFile.setMakeDate(df.format(cal.getTime()));
			cpFile = ((FileInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).insertCopiedFile(cpFile,oirFileDTO.getFileContent());
		}
		
		for(String oriFolderCode : orichildFolderList){
			FolderDTO oriFolderDTO = ((FolderDAO)this.getDAO()).searchFolderCode(oriFolderCode);
			String path = copiedFolderDTO.getPath()+"\\"+oriFolderDTO.getName();
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			GregorianCalendar cal = new GregorianCalendar();
			df.setTimeZone(cal.getTimeZone());
			
			CopiedFolder cpFolder = new CopiedFolder(path, oriFolderDTO.getName(), df.format(cal.getTime()), copiedParentCode, oriFolderDTO.getDescription(), 0, oriFolderDTO.getFolderCode(), oriFolderDTO.getParentFolderCode());
			cpFolder.setParentFolder(copiedParentCode);
			cpFolder.setSize(oriFolderDTO.getSizes());
			cpFolder = this.insertCopiedFolder(cpFolder, originOwnerCode,cpFolder.getOriginCode(), memberCode);
			if(!this.insertCopiedFolders(oirginFileNode.getChild(oriFolderCode), originOwnerCode, memberCode, cpFolder.getCode()))return false;
		}
		return true;
	} 
	
	@Override
	public Map<OriginFolder,CommitInfo> copyFolder(String oriFolderCode, String commitCode, String folderParentPath, String memberCode) throws DAOException, ParseException, FolderException, CommitExcetion, SftpException, JSchException{
		
		System.out.println("\n           FolderActionImpl           copyFolder     line      490");
		Map<OriginFolder,CommitInfo> resMap = new HashMap<OriginFolder, CommitInfo>();
		
		FolderDTO folderDTO = ((FolderDAO)this.getDAO()).searchFolderCode(oriFolderCode);
		
		FolderDTO pFolderDTO = ((FolderDAO)this.getDAO()).searchFolderPath(folderParentPath);
		String newPath = pFolderDTO.getPath()+"\\"+folderDTO.getName();
		String newRemotePath = pFolderDTO.getFolderContent();
		
		GregorianCalendar cal = new GregorianCalendar();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		df.setTimeZone(cal.getTimeZone());
		folderDTO.setInsertDate(df.format(cal.getTime()));
		folderDTO.setAlterDate(null);
		folderDTO.setFolderCode(null);
		folderDTO.setFolderContent(newRemotePath);
		folderDTO.setParentFolderCode(pFolderDTO.getFolderCode());
		folderDTO.setPath(newPath);
		//insert dto
		String folderCode= ((FolderDAO)this.getDAO()).insertFolder(folderDTO);
		if(folderCode==null)return resMap;
		System.out.println("folderCode : "+folderCode);
		folderDTO.setFolderCode(folderCode);
		folderDTO.setFolderContent(newRemotePath+"/"+folderCode);
		OriginFolder oriFolder = this.makeModel(folderDTO);
		
		synchronized (oriFolder) {
			System.out.println(folderDTO.getFolderContent());
			((RemoteFolderInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFolderActionImpl")).insertOriginFolder(folderDTO.getFolderContent());
		}
		
		//copy commit
		CommitInfo commitInfo = ((CommitInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("commentActionImpl")).copyCommit(commitCode, folderCode, memberCode, df.format(cal.getTime()), "create");
		
		resMap.put(oriFolder, commitInfo);
		return resMap;
	}
	
	
	
///////////////////////////////////////////////////////////////////////////   search	
	
	@Override
	public OriginFolder searchFolderName(String folderName, String parentFolderCode)throws DAOException, ParseException, FolderException {
		// TODO Auto-generated method stub
		FolderDTO dto= ((FolderDAO)this.getDAO()).searchFolderName(folderName, parentFolderCode);
		if(dto==null)return null;
	
		return this.makeModel(dto);
	}
	@Override
	public OriginFolder justSearchOriginFolderPath(String folderPath)throws DAOException, ParseException, FolderException {
		// TODO Auto-generated method stub
		//doesn't matter folder is copied or origin just search
		FolderDTO dto= ((FolderDAO)this.getDAO()).searchFolderPath(folderPath);
		if(dto==null)return null;
	
		return this.makeModel(dto);
	}
	@Override
	public OriginFolder justSearchOriginFolderCode(String folderCode)throws DAOException, ParseException, FolderException {
		// TODO Auto-generated method stub
		//doesn't matter folder is copied or origin just search
		FolderDTO dto= ((FolderDAO)this.getDAO()).searchFolderCode(folderCode);
		if(dto==null)return null;
	
		return this.makeModel(dto);
	}
	@Override
	public OriginFolder justSearchFolderNameByCode(String folderName, String parentFolderCode) throws DAOException, ParseException, FolderException{
		FolderDTO dto= ((FolderDAO)this.getDAO()).searchFolderName(folderName, parentFolderCode);
		if(dto==null)return null;
		
		return this.makeModel(dto);
	}
	@Override
	public OriginFolder justSearchFolderNameByPath(String folderName, String parentFolderPath) throws DAOException, ParseException, FolderException{
		
		// TODO Auto-generated method stub
		//doesn't matter folder is copied or origin just search
		FolderDTO pDto= ((FolderDAO)this.getDAO()).searchFolderPath(parentFolderPath);
		if(pDto==null)return null;
	
		FolderDTO dto= ((FolderDAO)this.getDAO()).searchFolderName(folderName, pDto.getFolderCode());
		if(dto==null)return null;
		
		return this.makeModel(dto);
	}
	@Override
	public OriginFolderList justSearchOriginChFoldersByPFCode(String parentFolderCode)throws DAOException, ParseException, FolderException{
		// TODO Auto-generated method stub
		//just search doesn't matter what is real
		OriginFolderList folderList = new OriginFolderList();
		
		List<FolderDTO> folderDTOs=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderListPCode(parentFolderCode);
		if(folderDTOs.isEmpty())return folderList;
		return this.makeModelList(folderDTOs);
	}
	@Override
	public OriginFolderList justSearchOriginChFoldersByPFPath(String parentFolderPath)throws DAOException, ParseException, FolderException{
		//search Only originFolderList with oriParent
		OriginFolderList folderList = new OriginFolderList();
		
		FolderDTO folderDTO = ((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderCode(parentFolderPath);
		List<FolderDTO> folderDTOs=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderListPCode(folderDTO.getPath());
		if(folderDTOs.isEmpty())return folderList;
		return this.makeModelList(folderDTOs);
	}
	@Override
	public OriginFolderList justSearchSiblingOriginFolderCode(String folderCode)throws DAOException, ParseException, FolderException{
		//search siblings
		OriginFolderList resList = new OriginFolderList();
		
		FolderDTO folderDTO=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderCode(folderCode);
		if(folderDTO==null)return resList;
		if(folderDTO.getFolderCode().equals(folderDTO.getParentFolderCode())){return null;}
		
		List<FolderDTO> folderDTOs=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderListPCode(folderDTO.getParentFolderCode());
		if(folderDTOs.isEmpty())return resList;
		return this.makeModelList(folderDTOs);
	}
	@Override
	public OriginFolderList justSearchSiblingOriginFolderPath(String folderPath)throws DAOException, ParseException, FolderException{
		//search siblings
		OriginFolderList resList = new OriginFolderList();
		
		FolderDTO folderDTO=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderPath(folderPath);
		if(folderDTO==null)return resList;
		if(folderDTO.getFolderCode().equals(folderDTO.getParentFolderCode())){return null;}
		
		List<FolderDTO> folderDTOs=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderListPCode(folderDTO.getParentFolderCode());
		if(folderDTOs.isEmpty())return resList;
		return this.makeModelList(folderDTOs);
	}
	@Override
	public OriginFolderList justSearchAllOriginFoldersByOriPFCode(String parentFolderCode)throws DAOException, ParseException, FolderException{
		//search All Childs 
		OriginFolderList resList = new OriginFolderList();
		
		FolderDTO parentFolderDTO=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderCode(parentFolderCode);
		if(parentFolderDTO==null)return resList;
		
		List<FolderDTO> folderDTOs=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchParentFolderPath(parentFolderDTO.getPath());
		if(folderDTOs.isEmpty())return resList;
		return this.makeModelList(folderDTOs);
	}
	@Override
	public OriginFolderList justSearchAllOriginFoldersByOriPFPath(String parentFolderPath)throws DAOException, ParseException, FolderException{
		//search All Childs 
		OriginFolderList resList = new OriginFolderList();
		
		List<FolderDTO> folderDTOs=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchParentFolderPath(parentFolderPath);
		if(folderDTOs.isEmpty())return resList;
		return this.makeModelList(folderDTOs);
	}
	
	
	@Override
	public OriginFolder searchOnlyOriginFolderPath(String folderPath)throws DAOException, ParseException, FolderException{
		
		//return only originFolder or null
		
		CopiedFolderDTO vdto = ((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderPath(folderPath);
		if(vdto!=null)return null;
		
		FolderDTO dto= ((FolderDAO)this.getDAO()).searchFolderPath(folderPath);
		if(dto==null)return null;
		return this.makeModel(dto);
	}
	@Override
	public OriginFolder searchOnlyOriginFolderCode(String folderCode)throws DAOException, ParseException, FolderException{

		//return only originFolder or null
		
		CopiedFolderDTO vdto = ((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderCode(folderCode);
		if(vdto!=null)return null;
		
		FolderDTO dto= ((FolderDAO)this.getDAO()).searchFolderCode(folderCode);
		if(dto==null)return null;
		return this.makeModel(dto);
	}
	@Override
	public OriginFolderList searchOriginChFoldersByOriPFCode(String oriParentFolderCode)throws DAOException, ParseException, FolderException{
		// TODO Auto-generated method stub
		//search Only originFolderList with oriParent
		OriginFolderList folderList = new OriginFolderList();
		
		//check
		CopiedFolderDTO cpDTO = ((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderCode(oriParentFolderCode);
		if(cpDTO!=null)return folderList;
		
		List<FolderDTO> folderDTOs=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderListPCode(oriParentFolderCode);
		if(folderDTOs.isEmpty())return folderList;
		return this.makeModelList(folderDTOs);
	}
	@Override
	public OriginFolderList searchOriginChFoldersByOriPFPath(String oriParentFolderPath)throws DAOException, ParseException, FolderException{
		//search Only originFolderList with oriParent
		OriginFolderList folderList = new OriginFolderList();
		
		//check
		CopiedFolderDTO cpDTO = ((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderPath(oriParentFolderPath);
		if(cpDTO!=null)return folderList;
		
		FolderDTO folderDTO = ((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderCode(oriParentFolderPath);
		List<FolderDTO> folderDTOs=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderListPCode(folderDTO.getPath());
		if(folderDTOs.isEmpty())return folderList;
		return this.makeModelList(folderDTOs);
	}
	@Override
	public OriginFolderList searchSiblingOnlyOriginFolderCode(String oriFolderCode)throws DAOException, ParseException, FolderException{
		//search siblings
		OriginFolderList resList = new OriginFolderList();
		
		//check
		CopiedFolderDTO cpDTO = ((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderCode(oriFolderCode);
		if(cpDTO!=null)return resList;
		
		FolderDTO folderDTO=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderCode(oriFolderCode);
		if(folderDTO==null)return resList;
		if(folderDTO.getFolderCode().equals(folderDTO.getParentFolderCode())){return null;}
		
		List<FolderDTO> folderDTOs=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderListPCode(folderDTO.getParentFolderCode());
		if(folderDTOs.isEmpty())return resList;
		for(FolderDTO fDTO : folderDTOs){
			
			CopiedFolderDTO cDTO = ((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderCode(fDTO.getFolderCode());
			if(cDTO!=null)continue;
			else resList.addOriginFolder(this.makeCopiedModel(fDTO, cDTO));
		}
		return resList;
	}
	@Override
	public OriginFolderList searchSiblingOnlyOriginFolderPath(String oirFolderPath)throws DAOException, ParseException, FolderException{
		//search siblings
		OriginFolderList resList = new OriginFolderList();
		//check
		CopiedFolderDTO cpDTO = ((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderPath(oirFolderPath);
		if(cpDTO!=null)return resList;
		
		FolderDTO folderDTO=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderPath(oirFolderPath);
		if(folderDTO==null)return resList;
		if(folderDTO.getFolderCode().equals(folderDTO.getParentFolderCode())){return null;}
		
		List<FolderDTO> folderDTOs=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderListPCode(folderDTO.getParentFolderCode());
		if(folderDTOs.isEmpty())return resList;
		for(FolderDTO fDTO : folderDTOs){
			
			CopiedFolderDTO cDTO = ((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderCode(fDTO.getFolderCode());
			if(cDTO!=null)continue;
			else resList.addOriginFolder(this.makeCopiedModel(fDTO, cDTO));
		}
		return resList;
	}
	@Override
	public OriginFolderList searchAllOriginFoldersByOriPFCode(String oriParentFolderCode)throws DAOException, ParseException, FolderException{
		//search All Childs only for originPFolder
		OriginFolderList resList = new OriginFolderList();
		
		//check
		CopiedFolderDTO cpDTO = ((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderCode(oriParentFolderCode);
		if(cpDTO!=null)return resList;
		
		FolderDTO parentFolderDTO=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderCode(oriParentFolderCode);
		if(parentFolderDTO==null)return resList;
		
		List<FolderDTO> folderDTOs=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchParentFolderPath(parentFolderDTO.getPath());
		if(folderDTOs.isEmpty())return resList;
		return this.makeModelList(folderDTOs);
	}
	@Override
	public OriginFolderList searchAllOriginFoldersByOriPFPath(String oriParentFolderPath)throws DAOException, ParseException, FolderException
	{
		//search All Childs only for originPFolder
		OriginFolderList resList = new OriginFolderList();
		
		//check
		CopiedFolderDTO cpDTO = ((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderPath(oriParentFolderPath);
		if(cpDTO!=null)return resList;
		
		List<FolderDTO> folderDTOs=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchParentFolderPath(oriParentFolderPath);
		if(folderDTOs.isEmpty())return resList;
		return this.makeModelList(folderDTOs);
	}
	
	
	@Override
	public CopiedFolder searchOnlyCopiedFolderPath(String folderPath)throws DAOException, ParseException, FolderException{
		// TODO Auto-generated method stub

		//return only CopiedFolder or null
		CopiedFolderDTO cfDTO = ((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderPath(folderPath);
		if(cfDTO==null)return null;
		
		FolderDTO dto= ((FolderDAO)this.getDAO()).searchFolderPath(folderPath);
	
		return this.makeCopiedModel(dto, cfDTO);
	}
	@Override
	public CopiedFolder searchOnlyCopiedFolderCode(String folderCode)throws DAOException, ParseException, FolderException{
		// TODO Auto-generated method stub

		//return only CopiedFolder or null
		CopiedFolderDTO cfDTO = ((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderCode(folderCode);
		if(cfDTO==null)return null;
		
		FolderDTO dto= ((FolderDAO)this.getDAO()).searchFolderCode(folderCode);
	
		return this.makeCopiedModel(dto, cfDTO);
	}
	
	
	//search exactlly
	@Override
	public File searchFolderByFolderPath(String folderPath) throws DAOException, ParseException, FolderException{
		
		FolderDTO folderDTO=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderPath(folderPath);
		if(folderDTO==null)return null;
		
		CopiedFolderDTO cpfolderDTO=((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderCode(folderDTO.getFolderCode());
		if(cpfolderDTO==null)return this.makeModel(folderDTO);
		else return this.makeCopiedModel(folderDTO, cpfolderDTO);
	}
	@Override
	public File searchFolderByFolderCode(String folderCode) throws DAOException, ParseException, FolderException{
		
		FolderDTO folderDTO=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderCode(folderCode);
		if(folderDTO==null)return null;
		
		CopiedFolderDTO cpfolderDTO=((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderCode(folderDTO.getFolderCode());
		if(cpfolderDTO==null)return this.makeModel(folderDTO);
		else return this.makeCopiedModel(folderDTO, cpfolderDTO);
	}
	@Override
	public List<File> searchFolderByPFolderPath(String parentFolderPath) throws DAOException, ParseException, FolderException{
		
		List<File> resList = new ArrayList<File>();
		FolderDTO pFDTO=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderPath(parentFolderPath);
		if(pFDTO==null)return null;
		
		List<FolderDTO> folderDTOs=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderListPCode(pFDTO.getFolderCode());
		if(!folderDTOs.isEmpty()){
			for(FolderDTO folderDTO : folderDTOs){
				CopiedFolderDTO cpfolderDTO=((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderCode(folderDTO.getFolderCode());
				if(cpfolderDTO==null){
					OriginFolder oriFolder = this.makeModel(folderDTO);
					if(oriFolder!=null)resList.add(oriFolder);
				}else{
					CopiedFolder cpFolder = this.makeCopiedModel(folderDTO, cpfolderDTO);
					if(cpFolder!=null)resList.add(cpFolder);
				}
			}
		}
		return resList;
	}
	@Override
	public List<File> searchFolderByPFolderCode(String parentFolderCode) throws DAOException, ParseException, FolderException{
		
		List<File> resList = new ArrayList<File>();
		FolderDTO pFDTO=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderCode(parentFolderCode);
		if(pFDTO==null)return null;
		
		List<FolderDTO> folderDTOs=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderListPCode(parentFolderCode);
		if(!folderDTOs.isEmpty()){
			for(FolderDTO folderDTO : folderDTOs){
				CopiedFolderDTO cpfolderDTO=((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderCode(folderDTO.getFolderCode());
				if(cpfolderDTO==null){
					OriginFolder oriFolder = this.makeModel(folderDTO);
					if(oriFolder!=null)resList.add(oriFolder);
				}else{
					CopiedFolder cpFolder = this.makeCopiedModel(folderDTO, cpfolderDTO);
					if(cpFolder!=null)resList.add(cpFolder);
				}
			}
		}
		return resList;
	}
	@Override
	public List<File> searchSiblingFolderByPFolderPath(String folderPath) throws DAOException, ParseException, FolderException{
		
		List<File> resList = new ArrayList<File>();
		FolderDTO mFDTO=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderPath(folderPath);
		if(mFDTO==null)return null;
		
		List<FolderDTO> folderDTOs=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderListPCode(mFDTO.getParentFolderCode());
		if(!folderDTOs.isEmpty()){
			for(FolderDTO folderDTO : folderDTOs){
				CopiedFolderDTO cpfolderDTO=((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderCode(folderDTO.getFolderCode());
				if(cpfolderDTO==null){
					OriginFolder oriFolder = this.makeModel(folderDTO);
					if(oriFolder!=null)resList.add(oriFolder);
				}else{
					CopiedFolder cpFolder = this.makeCopiedModel(folderDTO, cpfolderDTO);
					if(cpFolder!=null)resList.add(cpFolder);
				}
			}
		}
		return resList;
	}
	@Override
	public List<File> searchSiblingFolderByPFolderCode(String folderCode) throws DAOException, ParseException, FolderException{

		List<File> resList = new ArrayList<File>();
		FolderDTO mFDTO=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderCode(folderCode);
		if(mFDTO==null)return null;
		
		List<FolderDTO> folderDTOs=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderListPCode(mFDTO.getParentFolderCode());
		if(!folderDTOs.isEmpty()){
			for(FolderDTO folderDTO : folderDTOs){
				CopiedFolderDTO cpfolderDTO=((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderCode(folderDTO.getFolderCode());
				if(cpfolderDTO==null){
					OriginFolder oriFolder = this.makeModel(folderDTO);
					if(oriFolder!=null)resList.add(oriFolder);
				}else{
					CopiedFolder cpFolder = this.makeCopiedModel(folderDTO, cpfolderDTO);
					if(cpFolder!=null)resList.add(cpFolder);
				}
			}
		}
		return resList;
	}
	@Override
	public List<File> searchAllFoldersByPFCode(String parentFolderCode)throws DAOException, ParseException, FolderException{
		
		List<File> resList = new ArrayList<File>();
		FolderDTO pFolderDTO=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderCode(parentFolderCode);
		
		List<FolderDTO> folderDTOs=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchParentFolderPath(pFolderDTO.getPath());
		if(!folderDTOs.isEmpty()){
			for(FolderDTO folderDTO : folderDTOs){
				CopiedFolderDTO cpfolderDTO=((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderCode(folderDTO.getFolderCode());
				if(cpfolderDTO==null){
					OriginFolder oriFolder = this.makeModel(folderDTO);
					if(oriFolder!=null)resList.add(oriFolder);
				}else{
					CopiedFolder cpFolder = this.makeCopiedModel(folderDTO, cpfolderDTO);
					if(cpFolder!=null)resList.add(cpFolder);
				}
			}
		}
		return resList;
	}
	@Override
	public List<File> searchAllFoldersByPFPath(String parentFolderPath)throws DAOException, ParseException, FolderException{
		
		List<File> resList = new ArrayList<File>();
		
		List<FolderDTO> folderDTOs=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchParentFolderPath(parentFolderPath);
		if(!folderDTOs.isEmpty()){
			for(FolderDTO folderDTO : folderDTOs){
				CopiedFolderDTO cpfolderDTO=((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderCode(folderDTO.getFolderCode());
				if(cpfolderDTO==null){
					OriginFolder oriFolder = this.makeModel(folderDTO);
					if(oriFolder!=null)resList.add(oriFolder);
				}else{
					CopiedFolder cpFolder = this.makeCopiedModel(folderDTO, cpfolderDTO);
					if(cpFolder!=null)resList.add(cpFolder);
				}
			}
		}
		return resList;
	}



////////////////////////////////////////////////////////////////////////////	

	private OriginFolder makeModel(FolderDTO dto) throws ParseException, FolderException, DAOException{
		
		System.out.println("\n   FolderActionImpl   start makeModel(FolderDTO dto)   "+dto.getFolderCode()+"  line  809");
		OriginFolder folder = new OriginFolder(dto.getPath());
		if(dto==null) throw new FolderException("Error Search Folder");
		folder.setCode(dto.getFolderCode());
		folder.setName(dto.getName());
		folder.setDescription(dto.getDescription());
		folder.setPath(dto.getPath());
		
		System.out.println("dfo code : "+dto.getFolderCode()+" dto parent : "+dto.getParentFolderCode());
		
		folder.setMakeDate(dto.getInsertDate());
		folder.setAlterDate(dto.getAlterDate());
		
		folder.setSize(dto.getSizes());
		folder.setParentFolder(dto.getParentFolderCode());
		
		
		List<FolderDTO> chFolders=((FolderDAO)this.getDAO()).searchFolderListPCode(dto.getFolderCode());
		if(!chFolders.isEmpty()){
			List<String> childs = new ArrayList<String>();
			for(FolderDTO forDTO : chFolders){
				if(forDTO.getFolderCode().equals(forDTO.getParentFolderCode()))continue;
				childs.add(forDTO.getFolderCode());
			}
			System.out.println("FolderActionImple   makeModel  (folder)  :  "+ childs.size());
			if(!childs.isEmpty())folder.setChildFolderList(childs);
		}
		
		List<FileDTO> files=((FileDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("fileDAO")).searchFolderCode(dto.getFolderCode());
		if(!files.isEmpty()){
			List<String> chFiles = new ArrayList<String>();
			for(FileDTO file : files){
				System.out.println(file.getFileCode());
				chFiles.add(file.getFileCode());
			}
			System.out.println("FolderActionImple   makeModel  (file) :  "+ chFiles.size());
			if(!chFiles.isEmpty())folder.setChildFileList(chFiles);
		}
		System.out.println("return : "+folder.getPath());
		System.out.println("FolderActionImpl   end   makeModel(FolderDTO dto)");
		return folder;
	}
	
	private OriginFolderList makeModelList(List<FolderDTO> dtoList) throws ParseException, FolderException, DAOException{
		
		System.out.println("\n   FolderActionImpl   makeModelList(List<FolderDTO> dtoList)   line  866");
		if(dtoList.isEmpty()) return new OriginFolderList();
		
		OriginFolderList folderList = new OriginFolderList();
		for(FolderDTO dto:dtoList){
			OriginFolder folder = new OriginFolder(dto.getPath());
			if(dto==null) throw new FolderException("Error Search Folder");
			folder.setCode(dto.getFolderCode());
			folder.setName(dto.getName());
			folder.setDescription(dto.getDescription());
			folder.setPath(dto.getPath());
			
			folder.setMakeDate(dto.getInsertDate());
			folder.setAlterDate(dto.getAlterDate());
			
			folder.setSize(dto.getSizes());
			folder.setParentFolder(dto.getParentFolderCode());
			
			List<FolderDTO> chFolders=((FolderDAO)this.getDAO()).searchFolderListPCode(dto.getFolderCode());
			if(!chFolders.isEmpty()){
				List<String> childs = new ArrayList<String>();
				for(FolderDTO forDTO : chFolders){
					childs.add(forDTO.getFolderCode());
				}
				System.out.println("FolderActionImple   makeList  (folder)  :  "+ childs.size());
				folder.setChildFolderList(childs);
			}
			
			List<FileDTO> files=((FileDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("fileDAO")).searchFolderCode(dto.getFolderCode());
			if(!files.isEmpty()){
				List<String> chFiles = new ArrayList<String>();
				for(FileDTO file : files){
					System.out.println(file.getFileCode());
					chFiles.add(file.getFileCode());
				}
				System.out.println("FolderActionImple   makeList  (file) :  "+ chFiles.size());
				folder.setChildFileList(chFiles);
			}
			folderList.addOriginFolder(folder);
		}
		
		return folderList;
	}
	
	private List<File> makeCopiedList(List<FolderDTO> dtoList) throws ParseException, FolderException, DAOException{
		System.out.println("\n   FolderActionImpl   makeCopiedList(List<FolderDTO> dtoList)   line  924");
		List<File> resFolderList = new ArrayList<File>();
		if(dtoList.isEmpty()) return resFolderList;
		
		for(FolderDTO dto : dtoList){		
			
			//childFolder
			List<FolderDTO> chFolders=((FolderDAO)this.getDAO()).searchFolderListPCode(dto.getFolderCode());
			List<String> childs = new ArrayList<String>();
			if(!chFolders.isEmpty()){
				for(FolderDTO forDTO : chFolders){
					childs.add(forDTO.getFolderCode());
				}
				System.out.println("FolderActionImple   makeList  (folder)  :  "+ childs.size());
			}
			
			//childFile
			List<FileDTO> files=((FileDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("fileDAO")).searchFolderCode(dto.getFolderCode());
			List<String> chFiles = new ArrayList<String>();
			if(!files.isEmpty()){
				for(FileDTO file : files){
					System.out.println(file.getFileCode());
					chFiles.add(file.getFileCode());
				}
				System.out.println("FolderActionImple   makeList  (file) :  "+ chFiles.size());
			}
			CopiedFolderDTO cpDTO = ((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderCode(dto.getFolderCode());
			if(cpDTO!=null){
				//copiedFolder
				
				CopiedFolder cf = new CopiedFolder(dto.getPath());
				cf.setCode(dto.getFolderCode());
				cf.setName(dto.getName());
				cf.setDescription(dto.getDescription());
				cf.setPath(dto.getPath());
				cf.setSize(dto.getSizes());
				cf.setParentFolder(dto.getParentFolderCode());

				cf.setMakeDate(dto.getInsertDate());
				cf.setAlterDate(dto.getAlterDate());
				cf.setChildFolderList(childs);
				cf.setChildFileList(chFiles);
				//cf.setOriginCode(cpDTO.getOriginFolderCode());
				String oriPCode  = ((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderCode(cpDTO.getOriginFolderCode()).getParentFolderCode();
				cf.setOriginCode(cpDTO.getOriginFolderCode());
				cf.setOriginParentCode(oriPCode);
				resFolderList.add(cf);
			}else{
				//originFolder
				OriginFolder folder = new OriginFolder(dto.getPath());
				folder.setCode(dto.getFolderCode());
				folder.setName(dto.getName());
				folder.setDescription(dto.getDescription());
				folder.setPath(dto.getPath());
				folder.setMakeDate(dto.getInsertDate());
				folder.setAlterDate(dto.getAlterDate());
				folder.setSize(dto.getSizes());
				folder.setParentFolder(dto.getParentFolderCode());
				folder.setChildFolderList(childs);
				folder.setChildFileList(chFiles);
				resFolderList.add(folder);
			}
		}
		if(resFolderList.isEmpty()) System.out.println("DB resFolder is Empty...");
		else System.out.println("DB resFolderList size :::  "+resFolderList.size());
		return resFolderList;
	}
	
	private CopiedFolder makeCopiedModel(FolderDTO folderDTO, CopiedFolderDTO cpDTO) throws ParseException, FolderException, DAOException{
		
		System.out.println("\n   FolderActionImpl   makeCopiedModel(FolderDTO folderDTO, CopiedFolderDTO cpDTO)   line  1005");
		
		CopiedFolder cf = new CopiedFolder(folderDTO.getPath());
		
		cf.setCode(folderDTO.getFolderCode());
		cf.setName(folderDTO.getName());
		cf.setDescription(folderDTO.getDescription());
		cf.setPath(folderDTO.getPath());
		
		cf.setMakeDate(folderDTO.getInsertDate());
		cf.setAlterDate(folderDTO.getAlterDate());
		
		cf.setSize(folderDTO.getSizes());
		cf.setParentFolder(folderDTO.getParentFolderCode());
		
		List<FolderDTO> chFolders=((FolderDAO)this.getDAO()).searchFolderListPCode(folderDTO.getFolderCode());
		if(!chFolders.isEmpty()){
			List<String> childs = new ArrayList<String>();
			for(FolderDTO forDTO : chFolders){
				childs.add(forDTO.getFolderCode());
			}
			System.out.println("FolderActionImple   makeList  (folder)  :  "+ childs.size());
			cf.setChildFolderList(childs);
		}
		
		List<FileDTO> files=((FileDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("fileDAO")).searchFolderCode(folderDTO.getFolderCode());
		if(!files.isEmpty()){
			List<String> chFiles = new ArrayList<String>();
			for(FileDTO file : files){
				System.out.println(file.getFileCode());
				chFiles.add(file.getFileCode());
			}
			System.out.println("FolderActionImple   makeList  (file) :  "+ chFiles.size());
			cf.setChildFileList(chFiles);
		}
		
		String oriPCode  = ((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderCode(cpDTO.getOriginFolderCode()).getParentFolderCode();
		cf.setOriginCode(cpDTO.getOriginFolderCode());
		cf.setOriginParentCode(oriPCode);
		if(cf==null) System.out.println("DB resFolder is Nulllll...");
		else System.out.println("DB resFolder Path :::  "+cf.getPath());
		return cf;
	}
	
}