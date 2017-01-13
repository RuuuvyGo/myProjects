package action;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

import javax.security.auth.login.LoginException;

import model.CommitInfo;
import model.CopiedFile;
import model.OriginFile;
import model.OriginFileList;
import remoteAction.DeleteDiffFileAction;
import remoteAction.DiffFileActionImpl;
import remoteAction.InsertDiffFileAction;
import remoteAction.RemoteFileInsertAction;
import remoteAction.RemoteFileSearchAction;
import remoteAction.SearchDiffFileAction;
import socialProExceptions.CommitExcetion;
import socialProExceptions.DAOException;
import socialProExceptions.FileException;
import socialProExceptions.FolderException;
import socialProExceptions.RemoteFileException;
import socialProExceptions.TeamException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import dao.CommitDAO;
import dao.CopiedFileVDAO;
import dao.CopiedFolderVDAO;
import dao.CopiedInfoDAO;
import dao.DAO;
import dao.FileDAO;
import dao.FolderDAO;
import dto.CommitDTO;
import dto.CopiedFileVDTO;
import dto.CopiedFolderDTO;
import dto.CopiedInfoDTO;
import dto.FileDTO;
import dto.FolderDTO;
import factory.ActionFactory;
import factory.DAOFactory;
import factory.RemoteActionFactory;


public class FileActionImpl extends BaseAction implements FileSearchAction,FileInsertAction,FileDropAction,FileUpdateAction{

	private String remoteRootPath="/home/socialPro/tmp";
	public FileActionImpl() {
		// TODO Auto-generated constructor stub
		this.setDAO((FileDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("fileDAO"));
	}
	
	@Override
	public void setDAO(DAO dao) {
		// TODO Auto-generated method stub
		this.dao=(FileDAO)dao;
	}

	protected String insertOriginFile(FileDTO dto) throws DAOException {
		System.out.println("inserOriginFile > FileAction...");
		return ((FileDAO)this.getDAO()).insertFile(dto);
	}
	@Override
	public OriginFile insertOriginFile(OriginFile file) throws DAOException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException {
		// TODO Auto-generated method stub
		
		System.out.println("             insertOriginFile ...........> FileAction  ");
		
		FileDTO dto = new FileDTO(file.getFolderCode(), file.getName(), file.getPath(), file.getMakeDate(), file.getSizes());
		System.out.println(file.getPath());
		String remotePath=null;
		
		FolderDTO folderDTO=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderCode(dto.getFolderCode());
		if(folderDTO!=null){
			remotePath=folderDTO.getFolderContent();
			dto.setFileContent(remotePath);
		}
	
		String fileCode = this.insertOriginFile(dto);
		if(fileCode==null){throw new FileException("Error InsertOringin File");}
		file.setCode(fileCode);
		
		remotePath+="/"+fileCode;
		
		//make realFile
		File localFile = new File("C:\\socialPro\\"+fileCode+".txt");
		if(!(localFile.getParentFile()).exists()){(localFile.getParentFile()).mkdirs();}
		System.out.println("parent Path : "+(localFile.getParentFile()).getPath());
		System.out.println("Path : "+localFile.getPath());

		synchronized (localFile) {
			FileOutputStream fos = null;
			FileDescriptor fd = null;
			
			try {
				
				fos = new FileOutputStream(localFile);
				fd = fos.getFD();
				
				fos.write(file.getContent().getBytes());
				fos.flush();
				//fd.sync();
				
				//((RemoteFileInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).insertOriginFileContent(file.getPath(),remotePath);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			} finally{
				if(fos!=null){fos.close();}
			}
		}
		
		synchronized (localFile) {
		//make remoteFile  save FileContent(ftp)
			System.out.println("sync..RemotFile");
			if(!((RemoteFileInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).insertOriginFileContent(localFile.getPath(),remotePath)) throw new RemoteFileException("Error insert Remote File Content");
		}			
		if(localFile.exists())localFile.delete();	
		
		return file; 
	}


	@Override
	public OriginFile insertOriginCommitFile(OriginFile file,String memberCode)throws DAOException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException{
		// TODO Auto-generated method stub
		
		System.out.println("insertOriginCommitFile.. fileA\n"+memberCode);
		file=this.insertOriginFile(file);
		return file;
	}
	
	@Override
	public Map<String,CopiedFile> insertCommitTeamFile(OriginFile fileInfo,String ownerCode)throws DAOException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException, FolderException{
	
		Map<String,CopiedFile> resMap = new HashMap<String, CopiedFile>();
		
		fileInfo = this.insertOriginFile(fileInfo);
		System.out.println("TeamSharedFile is insert!!!!   : "+fileInfo.getCode());
		
		resMap.put(ownerCode, new CopiedFile(fileInfo.getCode(), fileInfo.getName(), fileInfo.getPath(), fileInfo.getMakeDate(), fileInfo.getFolderCode(), fileInfo.getContent(),0,null,null));
		
		List<CopiedFolderDTO> cfDTOList= ((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchOriginFolderCode(fileInfo.getFolderCode());
		if(cfDTOList.isEmpty())throw new FolderException("Error search Folder Information");
		
		for(CopiedFolderDTO cfDTO : cfDTOList){
			
			CopiedFile cf = new CopiedFile(cfDTO.getCopiedFolderPath()+"\\"+fileInfo.getName());
			cf.setOriginFileCode(fileInfo.getCode());
			cf.setOriginFolderCode(fileInfo.getFolderCode());
			
			cf.setName(fileInfo.getName());
			cf.setMakeDate(fileInfo.getMakeDate());
			cf.setAlterDate(fileInfo.getAlterDate());
			cf.setFolderCode(cfDTO.getCopiedFolderCode());
			cf.setContent(fileInfo.getContent());
			cf.setSizes(0);
			
			cf = this.insertCopiedFile(cf);
			
			resMap.put(cfDTO.getCopiedOwnerCode(), cf);
		}
		
		return resMap;
	}
	
	@Override
	public CopiedFile insertCopiedFile(CopiedFile file) throws DAOException, FileException, IOException, RemoteFileException, SftpException, JSchException {
		// TODO Auto-generated method stub
		
		System.out.println("insertCopiedFile > FileAction");
		
		FileDTO dto = new FileDTO(file.getFolderCode(), file.getName(), file.getPath(), file.getMakeDate(), file.getSizes());
		System.out.println(file.getPath());
		String remotePath=null;
		
		FolderDTO folderDTO=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderCode(dto.getFolderCode());
		if(folderDTO!=null){
			remotePath=folderDTO.getFolderContent();
			dto.setFileContent(remotePath);
		}
	
		String fileCode = this.insertOriginFile(dto);
		if(fileCode==null){throw new FileException("Error InsertOringin File");}
		file.setCode(fileCode);
		
		remotePath+="/"+fileCode;
		
		//make realFile
		File localFile = new File("C:\\socialPro\\"+fileCode+".txt");
		if(!(localFile.getParentFile()).exists()){(localFile.getParentFile()).mkdirs();}
		System.out.println("parent Path : "+(localFile.getParentFile()).getPath());
		System.out.println("Path : "+localFile.getPath());

		synchronized (localFile) {
			FileOutputStream fos = null;
			FileDescriptor fd = null;
			
			try {
				
				fos = new FileOutputStream(localFile);
				fd = fos.getFD();
				
				fos.write(file.getContent().getBytes());
				fos.flush();
				//fd.sync();
				
				((RemoteFileInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).insertOriginFileContent(localFile.getPath(),remotePath);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			} finally{
				if(fos!=null){fos.close();}
			}
		}
		
		synchronized (localFile) {
		//make remoteFile  save FileContent(ftp)
			System.out.println("sync..RemotFile");
			if(!((RemoteFileInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).insertOriginFileContent(localFile.getPath(),remotePath)) throw new RemoteFileException("Error insert Remote File Content");
		}			
		if(localFile.exists())localFile.delete();	
		
		
		((CopiedInfoDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedInfoDAO")).insertCopiedInfo(new CopiedInfoDTO(file.getCode(), file.getOriginFileCode()));
		return file; 
	}
	
	@Override
	public CopiedFile insertCopiedFile(CopiedFile file,String remoteOriFile)throws DAOException, FileException, FileNotFoundException, IOException, RemoteFileException, SftpException, JSchException{
		// TODO Auto-generated method stub
		System.out.println("insertCopiedFile > FileAction");
		
		FileDTO dto = new FileDTO(file.getFolderCode(), file.getName(), file.getPath(), file.getMakeDate(), file.getSizes());
		System.out.println(file.getPath());
		String remotePath=null;
		
		if(file.getContent()==null){
			FolderDTO folderDTO=((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderCode(dto.getFolderCode());
			if(folderDTO!=null){
				remotePath=folderDTO.getFolderContent();
			}
		}else{
			remotePath = file.getContent();
		}
		dto.setFileContent(remotePath);
	
		String fileCode = this.insertOriginFile(dto);
		if(fileCode==null){throw new FileException("Error InsertOringin File");}
		file.setCode(fileCode);
		
		remotePath+="/"+fileCode;
		
		synchronized (file) {
		//make remoteFile  save FileContent(ftp)
			System.out.println("sync..RemotFile");
			//if(!((InsertDiffFileAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("DiffFileActionImpl")).copyFile(remoteOriFile)) throw new RemoteFileException("Error insert Remote File Content");
			if(!((RemoteFileInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).insertCopiedFileContent(remoteOriFile, remotePath)) throw new RemoteFileException("Error insert Remote File Content");
		}			
		
		((CopiedInfoDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedInfoDAO")).insertCopiedInfo(new CopiedInfoDTO(file.getCode(), file.getOriginFileCode()));
		return file; 
	}
	

	@Override
	public OriginFile searchFileName(String folderCode, String fileName) throws DAOException, ParseException {
		// TODO Auto-generated method stub
		
		//return only originFile even that is copiedFile 
		FileDTO fileDTO=((FileDAO)this.getDAO()).searchFolderCode(folderCode, fileName);
		if(fileDTO==null)return null;
		
		OriginFile oriFile = new OriginFile(fileDTO.getFileCode(), fileDTO.getName(), fileDTO.getPath(), fileDTO.getInsertDate(), fileDTO.getFolderCode());
		oriFile.setSizes(fileDTO.getSizes());
		oriFile.setAlterDate(fileDTO.getAlterDate());
		
		return oriFile;
	}

	@Override
	public OriginFile searchOnlyOriginFileCode(String fileCode)throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FolderException{

		//return only originFile or null
		FileDTO fileDTO=((FileDAO)this.getDAO()).searchFileCode(fileCode);
		if(fileDTO==null)return null;
		
		//check is copiedinfo exist
		CopiedInfoDTO cpDTO = ((CopiedInfoDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedInfoDAO")).searchCopiedCode(fileDTO.getFileCode());
		if(cpDTO!=null)return null;
		
		OriginFile oriFile = makeModel(fileDTO);

		synchronized (oriFile) {
			//create real file in local
			//copy file in local path
			String localPath = "C:\\socialPro\\"+fileDTO.getFileCode();
			System.out.println("localPath  :  "+localPath);
			File resFile = ((RemoteFileSearchAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).searchFile(fileDTO.getFileContent(), localPath);
			OriginFile resOriFile = new OriginFile(resFile.getPath());
			if(resOriFile.readFile()){
				oriFile.setContent(resOriFile.getContent());
			}
			if(resFile.exists())resFile.delete();
			if(resOriFile.exists())resOriFile.delete();
		}
		
		return oriFile;
	}
	
	@Override
	public OriginFile searchOnlyOriginFilePath(String filePath) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FolderException {
		// TODO Auto-generated method stub
		
		//return only originFile or null
		FileDTO fileDTO=((FileDAO)this.getDAO()).searchFilePath(filePath);
		if(fileDTO==null)return null;
		
		//check is copiedinfo exist
		CopiedInfoDTO cpDTO = ((CopiedInfoDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedInfoDAO")).searchCopiedCode(fileDTO.getFileCode());
		if(cpDTO!=null)return null;
		
		OriginFile oriFile = makeModel(fileDTO);
		return oriFile;
	}
	
	@Override
	public OriginFile searchOnlyOriginFilePathContent(String filePath) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FolderException{
	
		System.out.println("\n          FileActionImpl   searchOnlyOriginFilePathContent(String filePath)   line  359  :"+filePath);
		//return only originFile or null
		FileDTO fileDTO=((FileDAO)this.getDAO()).searchFilePath(filePath);
		if(fileDTO==null)return null;
		
		//check is copiedinfo exist
		CopiedInfoDTO cpDTO = ((CopiedInfoDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedInfoDAO")).searchCopiedCode(fileDTO.getFileCode());
		if(cpDTO!=null)return null;
		OriginFile oriFile = makeModel(fileDTO);
		
		synchronized (oriFile) {
			//create real file in local
			//copy file in local path
			String localPath = "C:\\socialPro\\"+fileDTO.getFileCode();
			System.out.println("localPath  :  "+localPath);
			File resFile = ((RemoteFileSearchAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).searchFile(fileDTO.getFileContent(), localPath);
			OriginFile resOriFile = new OriginFile(resFile.getPath());
			if(resOriFile.readFile()){
				System.out.println(resOriFile.getContent());
				oriFile.setContent(resOriFile.getContent());
			}
			if(resFile.exists())resFile.delete();
			if(resOriFile.exists())resOriFile.delete();
		}
		
		return oriFile;
	}
	
	@Override
	public CopiedFile searchOnlyCpFileCode(String fileCode) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException{

		//return only CopiedFile or Null
		FileDTO fileDTO=((FileDAO)this.getDAO()).searchFileCode(fileCode);
		if(fileDTO==null)return null;
		
		//check is copiedinfo exist
		CopiedFileVDTO cfVDTO= ((CopiedFileVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFileVDAO")).searchCopiedFileCode(fileDTO.getFileCode());
		if(cfVDTO==null)return null;
		CopiedFile cpFile = this.makeModel(fileDTO, cfVDTO);
		return cpFile;
	}
	
	@Override
	public CopiedFile searchOnlyCpFilePath(String filePath) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException{

		//return only CopiedFile or Null
		FileDTO fileDTO=((FileDAO)this.getDAO()).searchFilePath(filePath);
		if(fileDTO==null)return null;
		
		//check is copiedinfo exist
		CopiedFileVDTO cfVDTO= ((CopiedFileVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFileVDAO")).searchCopiedFileCode(fileDTO.getFileCode());
		if(cfVDTO==null)return null;
		CopiedFile cpFile = this.makeModel(fileDTO, cfVDTO);
		return cpFile;
	}
	
	@Override
	public CopiedFile searchOnlyCpFileContent(String filePath) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException{
		
		//return only CopiedFile or Null
		FileDTO fileDTO=((FileDAO)this.getDAO()).searchFilePath(filePath);
		if(fileDTO==null)return null;
		
		//check is copiedinfo exist
		CopiedFileVDTO cfVDTO= ((CopiedFileVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFileVDAO")).searchCopiedFileCode(fileDTO.getFileCode());
		if(cfVDTO==null)return null;
		CopiedFile cpFile = this.makeModel(fileDTO, cfVDTO);
		
		synchronized (cpFile) {
			//create real file in local
			//copy file in local path
			String localPath = "C:\\socialPro\\"+fileDTO.getFileCode();
			File resFile = ((RemoteFileSearchAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).searchFile(fileDTO.getFileContent(), localPath);
			OriginFile resOriFile = new OriginFile(resFile.getPath());
			if(resOriFile.readFile()){
				cpFile.setContent(resOriFile.getContent());
			}
			if(resFile.exists())resFile.delete();
			if(resOriFile.exists())resOriFile.delete();
		}
		
		return cpFile;
	}
	
	@Override
	public OriginFileList searchAllFilesByOnlyOriginFCode(String folderCode)throws DAOException, ParseException, FolderException{
		// TODO Auto-generated method stub
		//return only originFileList or empty list
		OriginFileList list=new OriginFileList();
		
		//search is originFolderCode
		List<CopiedFileVDTO> cfVDTO= ((CopiedFileVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFileVDAO")).searchCopiedFolderCode(folderCode);
		if(!cfVDTO.isEmpty())return list;
		
		FolderDTO folderDTO = ((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderCode(folderCode);
		List<FileDTO> fileDTOList=((FileDAO)this.getDAO()).searchFileByFPath(folderDTO.getPath());
		if(fileDTOList.isEmpty())return list; 
		list = makeModelList(fileDTOList);
		return list;
	}
	
	@Override
	public OriginFileList searchAllFilesByOnlyOriginFPath(String folderPath)throws DAOException, ParseException, FolderException{
		// TODO Auto-generated method stub
		
		//return only originFileList or empty list
		OriginFileList list=new OriginFileList();
		
		//search is originFolderCode
		CopiedFolderDTO cfVDTO= ((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderPath(folderPath);
		if(cfVDTO!=null)return list;
		
		List<FileDTO> fileDTOList=((FileDAO)this.getDAO()).searchFileByFPath(folderPath);
		if(fileDTOList.isEmpty())return list; 
		list = makeModelList(fileDTOList);
		return list;
	}
	
	@Override
	public List<File> searchAllCpFilesByFCode(String cpFolderCode)throws DAOException, ParseException, FolderException{
		List<File> resList = new ArrayList<File>();
		//search is originFolderCode
		List<CopiedFileVDTO> cfVDTO= ((CopiedFileVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFileVDAO")).searchCopiedFolderCode(cpFolderCode);
		if(cfVDTO.isEmpty())return resList;
		
		FolderDTO folderDTO = ((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderCode(cpFolderCode);
		List<FileDTO> fileDTOList=((FileDAO)this.getDAO()).searchFileByFPath(folderDTO.getPath());
		if(fileDTOList.isEmpty())return resList; 
		resList = makeCopiedModelList(fileDTOList);
		return resList;
	}
	@Override
	public List<File> searchAllCpFilesByFPath(String cpFolderPath)throws DAOException, ParseException, FolderException{
		// TODO Auto-generated method stub
		//return only CopiedFileList even cpFolder has originFIle (if folder has originFile then error)
		
		List<File> resList = new ArrayList<File>();
		
		//search is originFolderCode
		CopiedFolderDTO cfVDTO= ((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderPath(cpFolderPath);
		if(cfVDTO==null)return resList;
		
		List<FileDTO> fileDTOList=((FileDAO)this.getDAO()).searchFileByFPath(cpFolderPath);
		if(fileDTOList.isEmpty())return resList; 
		resList = makeCopiedModelList(fileDTOList);
		
		return resList;
	}
	
	@Override
	public OriginFileList searchOnlyOriginFileByFolderPath(String oriFolderPath)throws DAOException, ParseException, FolderException{

		OriginFileList list = new OriginFileList();
		//search is originFolderCode
		CopiedFolderDTO cfVDTO= ((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderPath(oriFolderPath);
		if(cfVDTO!=null)return list;
		FolderDTO folderDTO = ((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderPath(oriFolderPath);
		
		List<FileDTO> fileDTOList=((FileDAO)this.getDAO()).searchFolderCode(folderDTO.getFolderCode());
		if(fileDTOList.isEmpty())return list;
		
	    list = makeModelList(fileDTOList);
		return list;
	}
	@Override
	public OriginFileList searchOnlyOriginFileByFolderCode(String oriFolderCode)throws DAOException, ParseException, FolderException{

		OriginFileList list = new OriginFileList();
		//search is originFolderCode
		CopiedFolderDTO cfVDTO= ((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderCode(oriFolderCode);
		if(cfVDTO!=null)return list;
		
		List<FileDTO> fileDTOList=((FileDAO)this.getDAO()).searchFolderCode(oriFolderCode);
		if(fileDTOList.isEmpty())return list;
		
	    list = makeModelList(fileDTOList);
		return list;
	}
	
	@Override
	public List<File> searchOnlyCpFileByFolderPath(String cpFolderPath)throws DAOException, ParseException, FolderException{
		//return only CopiedFileList even cpFolder has originFIle (if folder has originFile then error)
		
		List<File> list = new ArrayList<File>();
		//search is originFolderCode
		CopiedFolderDTO cfVDTO= ((CopiedFolderVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFolderVDAO")).searchCopiedFolderPath(cpFolderPath);
		if(cfVDTO==null)return list;
		
		List<FileDTO> fileDTOList=((FileDAO)this.getDAO()).searchFolderCode(cfVDTO.getCopiedFolderCode());
		if(fileDTOList.isEmpty())return list;
		
	    list = makeCopiedModelList(fileDTOList);
		return list;
	}
	@Override
	public List<File> searchOnlyCpFileByFolderCode(String cpFolderCode)throws DAOException, ParseException, FolderException{
		
		// TODO Auto-generated method stub
		//return only CopiedFileList even cpFolder has originFIle (if folder has originFile then error)
		
		List<File> list = new ArrayList<File>();
		
		//search is copiedFolder
		List<CopiedFileVDTO> cfVDTO= ((CopiedFileVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFileVDAO")).searchCopiedFolderCode(cpFolderCode);
		if(cfVDTO.isEmpty())return list;
		
		List<FileDTO> fileDTOList=((FileDAO)this.getDAO()).searchFolderCode(cpFolderCode);
		if(fileDTOList.isEmpty())return list;
		
	    list = makeCopiedModelList(fileDTOList);
		return list;
	}

	
	@Override
	public OriginFileList justSearchAllFilesByFCode(String folderCode)throws DAOException, ParseException, FolderException{
		
		//doesn't matter is copiedFolder or originFolder
		OriginFileList list=new OriginFileList();
		FolderDTO folderDTO = ((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderCode(folderCode);
		List<FileDTO> fileDTOList=((FileDAO)this.getDAO()).searchFileByFPath(folderDTO.getPath());
		if(fileDTOList.isEmpty())return list; 
		list = this.makeModelList(fileDTOList);
		
		return list;
	}
	@Override
	public OriginFileList justSearchAllFilesByFPath(String folderPath)throws DAOException, ParseException, FolderException{
		
		//doesn't matter is copiedFolder or originFolder
		OriginFileList list=new OriginFileList();
		List<FileDTO> fileDTOList=((FileDAO)this.getDAO()).searchFileByFPath(folderPath);
		if(fileDTOList.isEmpty())return list; 
		list = this.makeModelList(fileDTOList);
		
		return list;
	}
	@Override
	public OriginFileList justSearchFileByFCode(String folderCode)throws DAOException, ParseException, FolderException{
		//doesn't matter is copiedFolder or originFolder
		OriginFileList list=new OriginFileList();
		List<FileDTO> fileDTOList=((FileDAO)this.getDAO()).searchFolderCode(folderCode);
		if(fileDTOList.isEmpty())return list; 
		list = this.makeModelList(fileDTOList);
		
		return list;
	}
	@Override
	public OriginFile justSearchFileByFCode(String folderCode,String name)throws DAOException, ParseException, FolderException{
		//doesn't matter is copiedFolder or originFolder

		FileDTO fileDTO=((FileDAO)this.getDAO()).searchFolderCode(folderCode, name);
		if(fileDTO!=null)return this.makeModel(fileDTO);
		return null;
	}
	@Override
	public OriginFileList justSearchFileByFPath(String folderPath)throws DAOException, ParseException, FolderException{
		//doesn't matter is copiedFolder or originFolder
		OriginFileList list=new OriginFileList();
		FolderDTO folderDTO = ((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderPath(folderPath);
		List<FileDTO> fileDTOList=((FileDAO)this.getDAO()).searchFolderCode(folderDTO.getFolderCode());
		if(fileDTOList.isEmpty())return list; 
		list = this.makeModelList(fileDTOList);
		
		return list;
	}
	@Override
	public OriginFile justSearchOriginFileCode(String fileCode)throws DAOException, ParseException, FolderException, JSchException, IOException, SftpException{
		//return only originFile or null
		FileDTO fileDTO=((FileDAO)this.getDAO()).searchFileCode(fileCode);
		if(fileDTO==null)return null;
		
		OriginFile oriFile = makeModel(fileDTO);
		synchronized (oriFile) {
			//create real file in local
			//copy file in local path
			String localPath = "C:\\socialPro\\"+fileDTO.getFileCode();
			System.out.println("localPath  :  "+localPath);
			File resFile = ((RemoteFileSearchAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).searchFile(fileDTO.getFileContent(), localPath);
			OriginFile resOriFile = new OriginFile(resFile.getPath());
			if(resOriFile.readFile()){
				oriFile.setContent(resOriFile.getContent());
			}
			if(resFile.exists())resFile.delete();
			if(resOriFile.exists())resOriFile.delete();
		}
		return oriFile;
	}
	@Override
	public OriginFile justSearchOriginFilePath(String filePath)throws DAOException, ParseException, FolderException,JSchException, IOException, SftpException{
		FileDTO fileDTO=((FileDAO)this.getDAO()).searchFilePath(filePath);
		if(fileDTO==null)return null;
		
		OriginFile oriFile = makeModel(fileDTO);
		synchronized (oriFile) {
			//create real file in local
			//copy file in local path
			String localPath = "C:\\socialPro\\"+fileDTO.getFileCode();
			System.out.println("localPath  :  "+localPath);
			File resFile = ((RemoteFileSearchAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).searchFile(fileDTO.getFileContent(), localPath);
			OriginFile resOriFile = new OriginFile(resFile.getPath());
			if(resOriFile.readFile()){
				oriFile.setContent(resOriFile.getContent());
			}
			if(resFile.exists())resFile.delete();
			if(resOriFile.exists())resOriFile.delete();
		}
		return oriFile;
	}

	@Override
	public String searchDiffFile(String fileCode) throws FileException, DAOException, CommitExcetion, JSchException, IOException, RemoteFileException, SftpException {
		// TODO Auto-generated method stub
		
		//just for search diff content
		//search file
		FileDTO fileDTO=((FileDAO)this.getDAO()).searchFileCode(fileCode);
		if(fileDTO==null)throw new FileException("Error when search File..");
		
		List<CommitDTO> commitDTOList=((CommitDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("commitDAO")).searchCommitsCode(fileCode);
		if(commitDTOList.isEmpty())throw new CommitExcetion("Error search Commit");
		
		CommitDTO latestCommitDTO = commitDTOList.get(0);
		if(latestCommitDTO.getType()==1){

			//copy origin
			String remoteDiffPath = (fileDTO.getFileContent()).replaceAll(fileDTO.getFileCode(), latestCommitDTO.getCommitCode());
			System.out.println(remoteDiffPath);
			//get diff content
			String localPath = "C:\\socialPro\\"+latestCommitDTO.getCommitCode();
			String res=null;
			File resFile = ((RemoteFileSearchAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).searchFile(remoteDiffPath, localPath);
			if(resFile!=null){
				OriginFile resOriFile = new OriginFile(resFile);
				resOriFile.readFile(localPath);
				res = resOriFile.getContent();
				if(resFile.exists())resFile.delete();
				if(resOriFile.exists())resOriFile.delete();
				//remove changed file and return ori file
			}
			return res;
		}
		return null;
	}
	
	@Override
	public File searchFilePath(String filePath)throws DAOException, ParseException, FolderException{

		FileDTO fileDTO=((FileDAO)this.getDAO()).searchFilePath(filePath);
		if(fileDTO==null)return null;
		
		//check is copiedinfo exist
		CopiedFileVDTO cpDTO = ((CopiedFileVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFileVDAO")).searchCopiedFilePath(filePath);
		if(cpDTO==null){
			return this.makeModel(fileDTO);
		}else{
			return this.makeModel(fileDTO, cpDTO);
		}
	}
	@Override
	public File searchFileContentPath(String filePath) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FolderException{
		
		//return only CopiedFile or Null
		FileDTO fileDTO=((FileDAO)this.getDAO()).searchFilePath(filePath);
		if(fileDTO==null)return null;
		
		String fileContent=new String();

		synchronized (fileContent) {
			//create real file in local
			//copy file in local path
			String localPath = "C:\\socialPro\\"+fileDTO.getFileCode();
			File resFile = ((RemoteFileSearchAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).searchFile(fileDTO.getFileContent(), localPath);
			OriginFile resOriFile = new OriginFile(resFile.getPath());
			if(resOriFile.readFile()){
				fileContent = resOriFile.getContent();
			}
			if(resFile.exists())resFile.delete();
			if(resOriFile.exists())resOriFile.delete();
		}
		
		//check is copiedinfo exist
		CopiedFileVDTO cpDTO = ((CopiedFileVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFileVDAO")).searchCopiedFilePath(filePath);
		if(cpDTO==null){
			OriginFile res = this.makeModel(fileDTO);
			res.setContent(fileContent);
			return res;
		}else{
			CopiedFile res = this.makeModel(fileDTO, cpDTO);
			res.setContent(fileContent);
			return res;
		}
		
	}
	@Override
	public File searchFileContentCode(String fileCode) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FolderException{

		//return only CopiedFile or Null
		FileDTO fileDTO=((FileDAO)this.getDAO()).searchFileCode(fileCode);
		if(fileDTO==null)return null;
		
		String fileContent=new String();

		synchronized (fileContent) {
			//create real file in local
			//copy file in local path
			String localPath = "C:\\socialPro\\"+fileDTO.getFileCode();
			File resFile = ((RemoteFileSearchAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).searchFile(fileDTO.getFileContent(), localPath);
			OriginFile resOriFile = new OriginFile(resFile.getPath());
			if(resOriFile.readFile()){
				fileContent = resOriFile.getContent();
			}
			if(resFile.exists())resFile.delete();
			if(resOriFile.exists())resOriFile.delete();
		}
		
		//check is copiedinfo exist
		CopiedFileVDTO cpDTO = ((CopiedFileVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFileVDAO")).searchCopiedFileCode(fileCode);
		if(cpDTO==null){
			OriginFile res = this.makeModel(fileDTO);
			res.setContent(fileContent);
			return res;
		}else{
			CopiedFile res = this.makeModel(fileDTO, cpDTO);
			res.setContent(fileContent);
			return res;
		}
	}
	@Override
	public File searchFileCode(String fileCode)throws DAOException, ParseException, FolderException{

		//return only originFile or null
		FileDTO fileDTO=((FileDAO)this.getDAO()).searchFileCode(fileCode);
		if(fileDTO==null)return null;
		System.out.println("fileDTO path : "+fileDTO.getPath());
		
		//check is copiedinfo exist
		CopiedFileVDTO cpDTO = ((CopiedFileVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFileVDAO")).searchCopiedFileCode(fileCode);
		if(cpDTO==null){
			return this.makeModel(fileDTO);
		}else{
			return this.makeModel(fileDTO, cpDTO);
		}
	}
	@Override
	public List<File> searchSiblingFileByPath(String filePath)throws DAOException, ParseException, FolderException{
		List<File> resList = new ArrayList<File>();
		FileDTO file=((FileDAO)this.getDAO()).searchFilePath(filePath);
		if(file==null)return resList;
		String folderCode = file.getFolderCode();
		
		//return only originFile or null
		List<FileDTO> fileDTOs=((FileDAO)this.getDAO()).searchFolderCode(folderCode);
		if(!fileDTOs.isEmpty()){
			for(FileDTO fileDTO : fileDTOs){
			
				//check is copiedinfo exist
				CopiedFileVDTO cpDTO = ((CopiedFileVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFileVDAO")).searchCopiedFileCode(fileDTO.getFileCode());
				if(cpDTO==null){
					resList.add(this.makeModel(fileDTO));
				}else{
					resList.add(this.makeModel(fileDTO, cpDTO));
				}
			}
		}
		
		return resList;
	}
	@Override
	public List<File> searchSiblingFileByCode(String fileCode)throws DAOException, ParseException, FolderException{
		List<File> resList = new ArrayList<File>();
		FileDTO file=((FileDAO)this.getDAO()).searchFileCode(fileCode);
		if(file==null)return resList;
		String folderCode = file.getFolderCode();
		
		//return only originFile or null
		List<FileDTO> fileDTOs=((FileDAO)this.getDAO()).searchFolderCode(folderCode);
		if(!fileDTOs.isEmpty()){
			for(FileDTO fileDTO : fileDTOs){
			
				//check is copiedinfo exist
				CopiedFileVDTO cpDTO = ((CopiedFileVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFileVDAO")).searchCopiedFileCode(fileDTO.getFileCode());
				if(cpDTO==null){
					resList.add(this.makeModel(fileDTO));
				}else{
					resList.add(this.makeModel(fileDTO, cpDTO));
				}
			}
		}
		
		return resList;
	}
	@Override
	public List<File> searchFileByFolderPath(String folderPath)throws DAOException, ParseException, FolderException{
		
		List<File> resList = new ArrayList<File>();
		FolderDTO folderDTO = ((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderPath(folderPath);
		if(folderDTO==null)return resList;
		String folderCode = folderDTO.getFolderCode();
		
		//return only originFile or null
		List<FileDTO> fileDTOs=((FileDAO)this.getDAO()).searchFolderCode(folderCode);
		if(!fileDTOs.isEmpty()){
			for(FileDTO fileDTO : fileDTOs){
			
				//check is copiedinfo exist
				CopiedFileVDTO cpDTO = ((CopiedFileVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFileVDAO")).searchCopiedFileCode(fileDTO.getFileCode());
				if(cpDTO==null){
					resList.add(this.makeModel(fileDTO));
				}else{
					resList.add(this.makeModel(fileDTO, cpDTO));
				}
			}
		}
		
		return resList;
	}
	@Override
	public List<File> searchFileByFolderCode(String folderCode)throws DAOException, ParseException, FolderException{
		//return only originFile or null
		List<File> resList = new ArrayList<File>();
		
		List<FileDTO> fileDTOs=((FileDAO)this.getDAO()).searchFolderCode(folderCode);
		if(!fileDTOs.isEmpty()){
			for(FileDTO fileDTO : fileDTOs){
			
				//check is copiedinfo exist
				CopiedFileVDTO cpDTO = ((CopiedFileVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFileVDAO")).searchCopiedFileCode(fileDTO.getFileCode());
				if(cpDTO==null){
					resList.add(this.makeModel(fileDTO));
				}else{
					resList.add(this.makeModel(fileDTO, cpDTO));
				}
			}
		}
		
		return resList;
	}
	@Override
	public List<File> searchAllFileByFolderPath(String folderPath)throws DAOException, ParseException, FolderException{
		
		List<File> resList = new ArrayList<File>();
		
		//return only originFile or null
		List<FileDTO> fileDTOs=((FileDAO)this.getDAO()).searchFileByFPath(folderPath);
		if(!fileDTOs.isEmpty()){
			for(FileDTO fileDTO : fileDTOs){
			
				//check is copiedinfo exist
				CopiedFileVDTO cpDTO = ((CopiedFileVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFileVDAO")).searchCopiedFileCode(fileDTO.getFileCode());
				if(cpDTO==null){
					resList.add(this.makeModel(fileDTO));
				}else{
					resList.add(this.makeModel(fileDTO, cpDTO));
				}
			}
		}
		
		return resList;
	}
	@Override
	public List<File> searchAllFileByFolderCode(String folderCode)throws DAOException, ParseException, FolderException{

		List<File> resList = new ArrayList<File>();
		FolderDTO folderDTO = ((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderCode(folderCode);
		if(folderDTO==null)return resList;
		String folderPath = folderDTO.getPath();
		
		//return only originFile or null
		List<FileDTO> fileDTOs=((FileDAO)this.getDAO()).searchFileByFPath(folderPath);
		if(!fileDTOs.isEmpty()){
			for(FileDTO fileDTO : fileDTOs){
			
				//check is copiedinfo exist
				CopiedFileVDTO cpDTO = ((CopiedFileVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFileVDAO")).searchCopiedFileCode(fileDTO.getFileCode());
				if(cpDTO==null){
					resList.add(this.makeModel(fileDTO));
				}else{
					resList.add(this.makeModel(fileDTO, cpDTO));
				}
			}
		}
		
		return resList;
	}
	
	@Override
	public boolean checkFileDiff(String oriFileCode, List<CommitInfo> commitInfoList) throws FileNotFoundException, DAOException, FileException, SftpException, IOException, JSchException, RemoteFileException, ParseException{
		
		System.out.println("\n         FileActionImpl      checkFileDiff     line    892   "+oriFileCode);
		//commitInfoList = ((CommitSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("commitActionImpl")).searchCommitContent(commitInfoList);
		FileDTO fileDTO = ((FileDAO)this.getDAO()).searchFileCode(oriFileCode);
		
		boolean resBoolean = false;
		
		//copy originFileContent to tmp
		System.out.println(oriFileCode);
		((DiffFileActionImpl)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("DiffFileActionImpl")).copyFileToTmp(fileDTO.getFileContent(), oriFileCode);
		System.out.println("1. copy orignFileContent to tmp : path : "+fileDTO.getFileContent()+"   tmp_Path  =>  /home/socialPro/tmp/"+oriFileCode);
		
		int cnt=commitInfoList.size();
		String remoteTmpOriFilePath = "/home/socialPro/tmp/"+oriFileCode;
		if(commitInfoList.size()!=1){
			for(int i=0;i<cnt;i++){
				
				CommitInfo commitInfo = commitInfoList.get(i);
				System.out.println("code : "+commitInfo.getCode());
				System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				if(commitInfo.getKind().equals("modify")){
					
					String localCommitPath = "C://socialPro//"+commitInfo.getCode();
					String remoteCommitPath = "/home/socialPro/tmp/"+commitInfo.getCode();
					String remoteCommitPath_ = remoteCommitPath+"_";

					OriginFile localCommitFile = new OriginFile(localCommitPath);
					localCommitFile.writeFile(localCommitPath, commitInfo.getOriginFileDescriptioin());

					System.out.println("\n+++++++   fileContent  start    +++++++++");
					System.out.println(localCommitFile.getContent());
					System.out.println("+++++++   fileContent  end  +++++++++\n");
					
					synchronized (localCommitFile) {
					//make remoteFile  save FileContent(ftp)
						System.out.println("sync..RemotFile");
						if(!((RemoteFileInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).insertOriginFileContent(localCommitPath,remoteCommitPath)) throw new RemoteFileException("Error insert Remote File Content");
					}
					
					//2_3. make diff File 
					if(!((InsertDiffFileAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("DiffFileActionImpl")).insertDiffFile(remoteTmpOriFilePath, remoteCommitPath,remoteCommitPath_)){
						throw new RemoteFileException("Error when insert diff File");
					}

					localCommitFile.delete();
					
					//get diff content
					File diffFile = ((RemoteFileSearchAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).searchDiffFile(remoteCommitPath_, commitInfo.getCode());
					OriginFile oriFile = new OriginFile(diffFile.getPath());
					System.out.println("diff file path : "+diffFile.getPath());
					
					//delete
					((DeleteDiffFileAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("DiffFileActionImpl")).justRemoveTmpFile(remoteCommitPath);
					System.out.println(resBoolean);
					//delete
					((DeleteDiffFileAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("DiffFileActionImpl")).justRemoveTmpFile(remoteCommitPath_);
					System.out.println(resBoolean);
					
					if(oriFile.readFile()){
						System.out.println("\n***************   oriFile Content (diff res) start  **********");
						if(oriFile.getContent()==null){
							diffFile.delete(); oriFile.delete();
							resBoolean=true;
							System.out.println("oh!!! stop!!");
							break;
						}else System.out.println(oriFile.getContent());
						System.out.println("***************   oriFile Content (diff res) end   **********\n");
					}
					diffFile.delete(); oriFile.delete();
				}
				System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
			}
		}
		
		//move to realPath
		//((DiffFileActionImpl)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("DiffFileActionImpl")).copyFile("/home/socialPro/tmp/"+oriFileCode, fileDTO.getFileContent());
		
		//delete
		System.out.println(oriFileCode);
		((DeleteDiffFileAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("DiffFileActionImpl")).justRemoveTmpFile("/home/socialPro/tmp/"+oriFileCode);
		System.out.println("  RES  :::     "+resBoolean);
		return resBoolean;
	}
	
////////////////////////////////////////////////////   update	
	
	@Override
	public boolean updateFileContent(String memberCode, String storageCode,String projectCode, String folderCode, String fileCode,
			String newContent) throws DAOException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException, CommitExcetion {
		// TODO Auto-generated method stub
		
		return updateOriginFileContent(memberCode, storageCode, projectCode, folderCode, fileCode, newContent);
	}
	
	@Override
	public CommitInfo mergeFileContent(String memberCode, String fileCode, String newContent, String commitFileCode, String commiter, String commitCode, List<String> commitCodeList) throws DAOException, FileNotFoundException, SftpException, IOException, JSchException, FileException, LoginException, RemoteFileException, CommitExcetion, TeamException, ParseException{
		
		System.out.println("\n        FileActionImpl   mergeFileContent()  start   line    1034   "+commiter);
		
		System.out.println("fileCode : "+fileCode+"     commitFielCode : "+commitFileCode);
		//1_1. get FileDTO
		FileDTO fileDTO=((FileDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("fileDAO")).searchFileCode(fileCode);
		if(fileDTO==null)throw new FileException("Error when searchFile..");
		
		//1_2. FileIO
		String localPath = "C:\\socialPro\\"+fileCode;
		OriginFile oriFile = new OriginFile(fileDTO.getPath());
		oriFile.setContent(newContent);
		if(!oriFile.writeFile(localPath,newContent))return null;
		
		//make diff file
		//insert info into commit
		CommitInfo commitInfo=null;
		if(fileDTO!=null) {
			commitInfo = ((CommitInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("commitActionImpl")).mergeCommit(commitCode, fileDTO.getFileCode());
			if(commitCode==null)throw new CommitExcetion("Error when insert Commit");
			if(oriFile.exists())oriFile.delete();
		}
		String appliedCommitCode=commitInfo.getCode();
		System.out.println("newCommitCode  :    "+appliedCommitCode);
		
		//make diff 
		// 2_1. cp originFile
		if(!((InsertDiffFileAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("DiffFileActionImpl")).copyFile(fileDTO.getFileContent())){
			throw new RemoteFileException("Error when copy originFile");
		}
		
		//2_2. upload new Content
		if(!((RemoteFileInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).insertOriginFileContent(localPath, fileDTO.getFileContent())){
			throw new RemoteFileException("Error when update originFile");
		}
		
		//2_3. make diff File 
		String diffPath=(fileDTO.getFileContent()).replaceAll(fileDTO.getFileCode(), appliedCommitCode);
		System.out.println(diffPath);
		if(!((InsertDiffFileAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("DiffFileActionImpl")).insertDiffFile(fileDTO.getFileContent(), diffPath)){
			throw new RemoteFileException("Error when insert diff File");
		}
		
		//remove copied copied File
		if(!((DeleteDiffFileAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("DiffFileActionImpl")).removeCpFile(fileDTO.getFileContent())){
			throw new RemoteFileException("Error when remove file");
		}
		
		//filechangeDate
		
		((FileDAO)this.getDAO()).updateFileADtae(fileCode, commitInfo.getDate());
		
		if(!commitCodeList.isEmpty())((CommitDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("commitDAO")).updateCommitMerge(commitCodeList, 1);
		System.out.println("FileActionImpl   mergeFileContent()  end ");
		return commitInfo;
	}
	@Override
	public Map<OriginFile,CommitInfo> mergeNewFileContent(String memberCode, String folderPath, String newContent, String commitFileCode,String commiter, String commitCode, List<String> commitCodeList) throws DAOException, FileNotFoundException, SftpException, IOException, JSchException, FileException, LoginException, RemoteFileException , TeamException, CommitExcetion, ParseException, FolderException{
		
		System.out.println("\n           FileActionImpl    mergeNewFileContent       line   1163");
		Map<OriginFile,CommitInfo> resMap = new HashMap<OriginFile, CommitInfo>();
		FileDTO commitFileDTO = ((FileDAO)this.getDAO()).searchFileCode(commitFileCode);
		
		FolderDTO folderDTO = ((FolderDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("folderDAO")).searchFolderPath(folderPath);
		GregorianCalendar cal = new GregorianCalendar();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		df.setTimeZone(cal.getTimeZone());
		commitFileDTO.setInsertDate(df.format(cal.getTime()));
		commitFileDTO.setFileCode(null);
		commitFileDTO.setFolderCode(folderDTO.getFolderCode());
		commitFileDTO.setFileContent(folderDTO.getFolderContent());
		commitFileDTO.setPath(folderPath+"\\"+commitFileDTO.getName());
		
		String fileCode = ((FileDAO)this.getDAO()).insertFile(commitFileDTO);
		if(fileCode==null)return resMap;
		commitFileDTO.setFileCode(fileCode);
		commitFileDTO.setFileContent(commitFileDTO.getFileContent()+"/"+fileCode);
		OriginFile oriFile = this.makeModel(commitFileDTO);
		
		//1_2. FileIO
		String localPath = "C:\\socialPro\\"+fileCode;
		oriFile.setContent(newContent);
		if(!oriFile.writeFile(localPath,newContent))return resMap;

		System.out.println(commitFileDTO.getFileContent());
		//2_2. upload new Content
		if(!((RemoteFileInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).insertOriginFileContent(localPath, commitFileDTO.getFileContent())){
			throw new RemoteFileException("Error when update originFile");
		}
		if(oriFile.exists())oriFile.delete();
		
		CommitInfo commitInfo = ((CommitInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("commitActionImpl")).copyCommit(commitCode, fileCode, memberCode, df.format(cal.getTime()), "create");
		if(!commitCodeList.isEmpty())((CommitDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("commitDAO")).updateCommitMerge(commitCodeList, 1);
		
		
		//((CopiedInfoDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedInfoDAO")).insertCopiedInfo(new CopiedInfoDTO(commitFileCode, fileCode));
		resMap.put(oriFile, commitInfo);
		return resMap;
	}
	private boolean updateOriginFileContent(String memberCode, String storageCode,String projectCode, String folderCode, String fileCode,
			String newContent) throws DAOException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException, CommitExcetion{
		//1_1. get FileDTO
		FileDTO fileDTO=((FileDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("fileDAO")).searchFileCode(fileCode);
		if(fileDTO==null)throw new FileException("Error when searchFile..");
		
		//1_2. FileIO
		String localPath = "C:\\socialPro\\"+fileCode;
		OriginFile oriFile = new OriginFile(fileDTO.getPath());
		oriFile.setContent(newContent);
		if(!oriFile.writeFile(localPath,newContent))return false;
		
		//make diff file
		//insert info into commit
		String commitCode=null;
		CommitInfo commitInfo =null;
		if(fileDTO!=null) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			GregorianCalendar cal = new GregorianCalendar();
			df.setTimeZone(cal.getTimeZone());
			commitCode = ((CommitDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("commitDAO")).insertCommit(new CommitDTO(null, memberCode, df.format(cal.getTime()), fileCode, null, 0,1));
			if(commitCode==null)throw new CommitExcetion("Error when insert Commit");
		}
		System.out.println(commitCode);
		
		//make diff 
		
		// 2_1. cp originFile
		if(!((InsertDiffFileAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("DiffFileActionImpl")).copyFile(fileDTO.getFileContent())){
			throw new RemoteFileException("Error when copy originFile");
		}
		
		//2_2. upload new Content
		/*if(!((RemoteFileInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).insertOriginFileContent(fileDTO.getPath(), fileDTO.getFileContent())){
			throw new RemoteFileException("Error when update originFile");
		}*/
		if(!((RemoteFileInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).insertOriginFileContent(localPath, fileDTO.getFileContent())){
			throw new RemoteFileException("Error when update originFile");
		}
		
		//2_3. make diff File 
		String diffPath=(fileDTO.getFileContent()).replaceAll(fileDTO.getFileCode(), commitCode);
		System.out.println(diffPath);
		if(!((InsertDiffFileAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("DiffFileActionImpl")).insertDiffFile(fileDTO.getFileContent(), diffPath)){
			throw new RemoteFileException("Error when insert diff File");
		}
		
		//remove copied tmpFile
		if(!((DeleteDiffFileAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("DiffFileActionImpl")).removeCpFile(fileDTO.getFileContent())){
			throw new RemoteFileException("Error when remove file");
		}
		
		//filechangeDate
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		GregorianCalendar cal = new GregorianCalendar();
		df.setTimeZone(cal.getTimeZone());
		((FileDAO)this.getDAO()).updateFileADtae(fileCode, df.format(cal.getTime()));
		return true;
	}
	
	private FileDTO updateLocalOriginFileContent(String fileCode,String newContent) throws DAOException, FileException, FileNotFoundException, IOException{
		//1_1. get FileDTO
		System.out.println(fileCode);
		FileDTO fileDTO=((FileDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("fileDAO")).searchFileCode(fileCode);
		if(fileDTO==null)throw new FileException("Error when searchFile..");
		
		//1_2. FileIO
		OriginFile oriFile = new OriginFile(fileDTO.getPath());
		//oriFile.writeFile(fileDTO.getPath());
		oriFile.writeFile("C:\\socialPro\\"+fileDTO.getFileCode(),newContent);
		return fileDTO;
	}
	
	
	private synchronized boolean updateOriginFileContent(String memberCode, String storageCode,String projectCode, String folderCode, String fileCode,
			String newContent,String commitTitle,String commitContent) throws DAOException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException, CommitExcetion{

		//1_1. get FileDTO
		FileDTO fileDTO=((FileDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("fileDAO")).searchFileCode(fileCode);
		if(fileDTO==null)throw new FileException("Error when searchFile..");
		
		//1_2. FileIO
		String localPath = "C:\\socialPro\\"+fileCode;
		OriginFile oriFile = new OriginFile(fileDTO.getPath());
		oriFile.setContent(newContent);
		if(!oriFile.writeFile(localPath,newContent))return false;
		
		//make diff file
		//insert info into commit
		String commitCode=null;
		if(fileDTO!=null) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			GregorianCalendar cal = new GregorianCalendar();
			df.setTimeZone(cal.getTimeZone());
			commitCode = ((CommitDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("commitDAO")).insertCommit(new CommitDTO(commitTitle, memberCode, df.format(cal.getTime()), fileCode, commitContent, 0,1));
			if(commitCode==null)throw new CommitExcetion("Error when insert Commit");
		}
		System.out.println(commitCode);
		
		//make diff 
		
		// 2_1. cp originFile
		if(!((InsertDiffFileAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("DiffFileActionImpl")).copyFile(fileDTO.getFileContent())){
			throw new RemoteFileException("Error when copy originFile");
		}
		
		//2_2. upload new Content
		/*if(!((RemoteFileInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).insertOriginFileContent(fileDTO.getPath(), fileDTO.getFileContent())){
			throw new RemoteFileException("Error when update originFile");
		}*/
		if(!((RemoteFileInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).insertOriginFileContent(localPath, fileDTO.getFileContent())){
			throw new RemoteFileException("Error when update originFile");
		}
		
		//2_3. make diff File 
		String diffPath=(fileDTO.getFileContent()).replaceAll(fileDTO.getFileCode(), commitCode);
		System.out.println(diffPath);
		if(!((InsertDiffFileAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("DiffFileActionImpl")).insertDiffFile(fileDTO.getFileContent(), diffPath)){
			throw new RemoteFileException("Error when insert diff File");
		}
		
		//remove copied tmpFile
		if(!((DeleteDiffFileAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("DiffFileActionImpl")).removeCpFile(fileDTO.getFileContent())){
			throw new RemoteFileException("Error when remove file");
		}
		
		//filechangeDate
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		GregorianCalendar cal = new GregorianCalendar();
		df.setTimeZone(cal.getTimeZone());
		((FileDAO)this.getDAO()).updateFileADtae(fileCode, df.format(cal.getTime()));
		return true;
	}
	
	
	private boolean updateSharedFileContent(String memberCode, String storageCode,String projectCode, String folderCode, String fileCode,
			String newContent,String commitTitle,String commitContent) throws DAOException, FileNotFoundException, SftpException, IOException, JSchException, FileException, CommitExcetion, RemoteFileException{
		//1. update localFile
		
		//1_1. get FileDTO
		FileDTO fileDTO=((FileDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("fileDAO")).searchFileCode(fileCode);
		if(fileDTO==null)throw new FileException("Error when searchFile..");
		
		//1_2. FileIO
		String localPath = "C:\\socialPro\\"+fileCode;
		OriginFile oriFile = new OriginFile(fileDTO.getPath());
		oriFile.setContent(newContent);
		if(!oriFile.writeFile(localPath,newContent))return false;
		
		//make diff file
		//insert info into commit
		String commitCode=null;
		if(fileDTO!=null) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			GregorianCalendar cal = new GregorianCalendar();
			df.setTimeZone(cal.getTimeZone());
			commitCode = ((CommitDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("commitDAO")).insertCommit(new CommitDTO(commitTitle, memberCode, df.format(cal.getTime()), fileCode, commitContent, 0,1));
			if(commitCode==null)throw new CommitExcetion("Error when insert Commit");
		}
		System.out.println(commitCode);
		
		//make diff 
		
		// 2_1. cp originFile
		if(!((InsertDiffFileAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("DiffFileActionImpl")).copyFile(fileDTO.getFileContent())){
			throw new RemoteFileException("Error when copy originFile");
		}
		
		//2_2. upload new Content
		synchronized (oriFile) {
		//make remoteFile  save FileContent(ftp)
			System.out.println("sync..RemotFile");
			if(!((RemoteFileInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).insertOriginFileContent(localPath,fileDTO.getFileContent())) throw new RemoteFileException("Error insert Remote File Content");
		}			
		if(oriFile.exists())oriFile.delete();
		
		//2_3. make diff File 
		String diffPath=(fileDTO.getFileContent()).replaceAll(fileDTO.getFileCode(), commitCode);
		System.out.println(diffPath);
		if(!((InsertDiffFileAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("DiffFileActionImpl")).insertDiffFile(fileDTO.getFileContent(), diffPath)){
			throw new RemoteFileException("Error when insert diff File");
		}
		
		//remove copied tmpFile
		if(!((DeleteDiffFileAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("DiffFileActionImpl")).removeCpFile(fileDTO.getFileContent())){
			throw new RemoteFileException("Error when remove file");
		}
		
		return true;
	}
	
	@Override
	public CommitInfo updateFileContent(String memberCode, String storageCode,String projectCode, String folderCode, String fileCode,
			String newContent,String commitTitle,String commitContent) throws DAOException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException, CommitExcetion, TeamException {
		// TODO Auto-generated method stub
		System.out.println("\n             FileActionImpl  :: updateFileContent start  872 @");
		//1_1. get FileDTO
		FileDTO fileDTO=((FileDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("fileDAO")).searchFileCode(fileCode);
		if(fileDTO==null)throw new FileException("Error when searchFile..");
		System.out.println("1. end getFileDTO");
		
		//1_2. FileIO
		String localPath = "C:\\socialPro\\"+fileCode;
		OriginFile oriFile = new OriginFile(fileDTO.getPath());
		oriFile.setContent(newContent);
		if(!oriFile.writeFile(localPath,newContent))return null;
		System.out.println("2. end FileIo localPath : "+localPath);
		
		//make diff file
		//insert info into commit
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		GregorianCalendar cal = new GregorianCalendar();
		df.setTimeZone(cal.getTimeZone());
		CommitInfo commitInfo=null;

		if(fileDTO!=null) {
			commitInfo = new CommitInfo(fileCode, "modify", commitContent, df.format(cal.getTime()), memberCode, false, null, commitTitle);
			String commitCode = ((CommitInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("commitActionImpl")).insertCommit(commitInfo);
			if(commitCode==null)throw new CommitExcetion("Error when insert Commit");
			commitInfo.setCode(commitCode);
		}
		System.out.println(commitInfo.getCode());
		System.out.println("3. end insert commitInfo ");
		
		//make diff 
		
		// 2_1. cp originFile
		if(!((InsertDiffFileAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("DiffFileActionImpl")).copyFile(fileDTO.getFileContent())){
			throw new RemoteFileException("Error when copy originFile");
		}
		System.out.println("4. end copy original File content : remotePath(copied one) : "+fileDTO.getFileContent()+"_cp");
		
		//2_2. upload new Content
		/*if(!((RemoteFileInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).insertOriginFileContent(fileDTO.getPath(), fileDTO.getFileContent())){
			throw new RemoteFileException("Error when update originFile");
		}*/
		if(!((RemoteFileInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).insertOriginFileContent(localPath, fileDTO.getFileContent())){
			throw new RemoteFileException("Error when update originFile");
		}
		System.out.println("5. end upload new File content : remotePath : "+fileDTO.getFileContent());
		
		//2_3. make diff File 
		String diffPath=(fileDTO.getFileContent()).replaceAll(fileDTO.getFileCode(), commitInfo.getCode());
		System.out.println(diffPath);
		if(!((InsertDiffFileAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("DiffFileActionImpl")).insertDiffFile(fileDTO.getFileContent(), diffPath)){
			throw new RemoteFileException("Error when insert diff File");
		}
		System.out.println("6. end diff -y commitPath : "+diffPath);
		
		//remove copied cpFile
		if(!((DeleteDiffFileAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("DiffFileActionImpl")).removeCpFile(fileDTO.getFileContent())){
			throw new RemoteFileException("Error when remove file");
		}
		if(oriFile.exists())oriFile.delete();
		System.out.println("7. end remove copied file : "+fileDTO.getFileContent()+"_cp");
		
		//filechangeDate
		((FileDAO)this.getDAO()).updateFileADtae(fileCode, df.format(cal.getTime()));
		System.out.println("end        @\n");
		return commitInfo;
	}

	
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	private OriginFile makeModel(FileDTO dto) throws ParseException, FolderException, DAOException{
		
		System.out.println("  line  957  FolderActionImpl   start makeModel(FolderDTO dto)   "+dto.getFolderCode());
		
		OriginFile oriFile = new OriginFile(dto.getFileCode(), dto.getName(), dto.getPath(), dto.getInsertDate(), dto.getFolderCode());
		oriFile.setAlterDate(dto.getAlterDate());
		oriFile.setSizes(dto.getSizes());
		System.out.println(" return OriginFile makeModel : "+oriFile.getPath());
		System.out.println("FolderActionImpl   end   makeModel(FolderDTO dto)");
		return oriFile;
	}

	private OriginFileList makeModelList(List<FileDTO> dtoList) throws ParseException, FolderException, DAOException{
		
		System.out.println("  line  997  FolderActionImpl   start makeModelList(List<FolderDTO> dtoList)   ");
		
		OriginFileList resList = new OriginFileList();
		for(FileDTO dto : dtoList){
			
			OriginFile oriFile = new OriginFile(dto.getFileCode(), dto.getName(), dto.getPath(), dto.getInsertDate(), dto.getFolderCode());
			oriFile.setAlterDate(dto.getAlterDate());
			oriFile.setSizes(dto.getSizes());
			resList.addOriginFile(oriFile);
		}
		if(resList.getList().isEmpty())System.out.println("resList is empty....");
		else System.out.println("resList size :  "+resList.getList().size());
		System.out.println("FolderActionImpl   end   makeModelList(List<FolderDTO> dtoList)");
		return resList;
	}
	
	private CopiedFile makeModel(FileDTO fileDTO, CopiedFileVDTO copiedFileVDTO) throws ParseException{
		
		System.out.println("  line  1004  FolderActionImpl   start makeModel(FileDTO fileDTO, CopiedFileVDTO copiedFileVDTO)   "+copiedFileVDTO.getCopiedFileCode());
		
		CopiedFile cpFile = new CopiedFile(fileDTO.getFileCode(), fileDTO.getName(), fileDTO.getPath(), fileDTO.getInsertDate(), fileDTO.getFolderCode(),copiedFileVDTO.getOriginFileCode(),copiedFileVDTO.getOriginFolderCode());
		
		cpFile.setAlterDate(fileDTO.getAlterDate());
		System.out.println(" return CopiedFile makeModel : "+cpFile.getPath());
		return cpFile;
	}
	
	private List<File> makeCopiedModelList(List<FileDTO> dtoList) throws ParseException, FolderException, DAOException{
		
		System.out.println("  line  1028  FolderActionImpl   start makeCopiedModelList(List<FolderDTO> dtoList)   ");
		
		List<File> resList = new ArrayList<File>();
		for(FileDTO dto : dtoList){

			CopiedFileVDTO cfVDTO= ((CopiedFileVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedFileVDAO")).searchCopiedFileCode(dto.getFileCode());
			if(cfVDTO==null){
				//insert originFile
				OriginFile oriFile = new OriginFile(dto.getFileCode(), dto.getName(), dto.getPath(), dto.getInsertDate(), dto.getFolderCode());
				oriFile.setAlterDate(dto.getAlterDate());
				oriFile.setSizes(dto.getSizes());
				resList.add(oriFile);
			}else{
				//insert copiedFile
				CopiedFile cpFile = new CopiedFile(dto.getFileCode(), dto.getName(), dto.getPath(), dto.getInsertDate(), dto.getFolderCode(),cfVDTO.getOriginFileCode(),cfVDTO.getOriginFolderCode());
				
				cpFile.setAlterDate(dto.getAlterDate());
				resList.add(cpFile);
			}
		}
		if(resList.isEmpty())System.out.println("resList is empty....");
		else System.out.println("resList size :  "+resList.size());
		System.out.println("FolderActionImpl   end   makeCopiedModelList(List<FolderDTO> dtoList)");
		return resList;
	}
		
}