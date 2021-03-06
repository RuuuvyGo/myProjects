package manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.security.auth.login.LoginException;

import model.CommitInfo;
import model.CopiedFile;
import model.OriginFile;
import model.OriginFileList;
import model.OriginFolder;
import model.OriginProject;
import socialProExceptions.CommitExcetion;
import socialProExceptions.DAOException;
import socialProExceptions.FileException;
import socialProExceptions.FolderException;
import socialProExceptions.ProjectException;
import socialProExceptions.RemoteFileException;
import socialProExceptions.TeamException;
import action.FileInsertAction;
import action.FileSearchAction;
import action.FileUpdateAction;
import action.FolderSearchAction;
import action.ProjectSearchAction;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import factory.ActionFactory;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : FileDBManager.java
//  @ Date : 2015-07-28
//  @ Author : 
//
//




public class FileDBManager {
	
	private static FileDBManager INSTANCE;
	
	static{
		INSTANCE = new FileDBManager();
	}
	
	private FileDBManager(){
		
	}
	
	public static FileDBManager getINSTANCE() {
		if(INSTANCE==null)INSTANCE=new FileDBManager();
		return INSTANCE;
	}

	public OriginFile insertOriginFileByFCode(String memberCode, String name, String folderCode ) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException, FolderException{
		
		OriginFolder oriFolder=((FolderSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("folderActionImpl")).justSearchOriginFolderCode(folderCode);
		if(oriFolder==null)return null;
		String path = oriFolder.getPath()+"\\"+name;
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		GregorianCalendar cal = new GregorianCalendar();
		df.setTimeZone(cal.getTimeZone());

		return ((FileInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).insertOriginFile(new OriginFile(name, path, df.format(cal.getTime()), folderCode));
	}
	
	public OriginFile insertOriginFileByFCode(String memberCode, String name, String folderCode ,String content) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException, FolderException, ProjectException{
		
		System.out.println("insertOriginFileByFCode");
		
		String path=null;
		Map<String,String> resMap=makeCodePath(folderCode,name);
		if(resMap==null){return null;	}
		else{folderCode=resMap.get("code");path=resMap.get("path");}
		
		System.out.println(path);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		GregorianCalendar cal = new GregorianCalendar();
		df.setTimeZone(cal.getTimeZone());

		return ((FileInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).insertOriginFile(new OriginFile(name, path, df.format(cal.getTime()), folderCode, content,0));
	
	}
	
	
	private Map<String,String> makeCodePath(String code,String name) throws DAOException, ParseException, FolderException, ProjectException{
		
		System.out.println("FielDBManager   makeCodePath : line  97 start "+code+"    "+name);
		
		Map<String,String> resMap=null; 
		StringTokenizer token = new StringTokenizer(code,"_");
		OriginFolder oriFolder=null;
		OriginProject oriProject=null;
		if(token.nextToken().equals("project")){
			oriProject = ((ProjectSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("projectActionImpl")).justSearchOriProjectCode(code);
			resMap=new HashMap<String, String>();
			resMap.put("code", oriProject.getParentFolder());
			resMap.put("path", oriProject.getPath()+"\\"+name);
		}else{
			oriFolder =((FolderSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("folderActionImpl")).justSearchOriginFolderCode(code);
			resMap=new HashMap<String, String>();
			resMap.put("code", oriFolder.getCode());
			resMap.put("path", oriFolder.getPath()+"\\"+name);
		}
		return resMap;
	}

	public OriginFile insertOriginFile(String memberCode, String name, String folderPath ) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException, FolderException{
	
		String path = folderPath+"\\"+name;
		OriginFolder oriFolder=((FolderSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("folderActionImpl")).justSearchOriginFolderPath(folderPath);
		if(oriFolder==null)return null;
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		GregorianCalendar cal = new GregorianCalendar();
		df.setTimeZone(cal.getTimeZone());

		return ((FileInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).insertOriginFile(new OriginFile(name, path, df.format(cal.getTime()), oriFolder.getCode()));
	}
	
	public OriginFile insertOriginFile(String memberCode, String name, String folderPath ,String content) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException, FolderException{
		
		String path = folderPath+"\\"+name;
		OriginFolder oriFolder=((FolderSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("folderActionImpl")).justSearchOriginFolderPath(folderPath);
		if(oriFolder==null)return null;
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		GregorianCalendar cal = new GregorianCalendar();
		df.setTimeZone(cal.getTimeZone());

		return ((FileInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).insertOriginFile(new OriginFile(name, path, df.format(cal.getTime()), oriFolder.getCode(), content,0));
	}
	
	public OriginFile insertCommitFile(String ownerCode, String name, String folderCode,String content)
			throws DAOException, ParseException, FileNotFoundException,SftpException, IOException, JSchException, FileException,RemoteFileException, CommitExcetion, FolderException, ProjectException {
		
		System.out.println("createCommitFile (DBM)\n"+ownerCode);
		String path=null;
		Map<String,String> resMap=makeCodePath(folderCode,name);
		if(resMap==null){return null;	}
		else{folderCode=resMap.get("code");path=resMap.get("path");}
		
		System.out.println("path : "+path);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		GregorianCalendar cal = new GregorianCalendar();
		df.setTimeZone(cal.getTimeZone());

		return ((FileInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).insertOriginCommitFile(new OriginFile(name, path, df.format(cal.getTime()), folderCode, content,0),ownerCode);
	}
	
	public Map<String,CopiedFile> insertCommitTeamFile(OriginFolder folderInfo,String ownerCode, String fileName,String content)
			throws DAOException, ParseException, FileNotFoundException,SftpException, IOException, JSchException, FileException,RemoteFileException, CommitExcetion, FolderException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		GregorianCalendar cal = new GregorianCalendar();
		df.setTimeZone(cal.getTimeZone());
		
		OriginFile fileInfo = new OriginFile(fileName, folderInfo.getPath()+"\\"+fileName, df.format(cal.getTime()), folderInfo.getCode(), content,0);
		System.out.println("oriFolderCode : "+folderInfo.getCode() +"  __ "+fileInfo.getFolderCode()+"   filePath  : "+fileInfo.getPath());
		return ((FileInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).insertCommitTeamFile(fileInfo,ownerCode);
	}		

///////////////////////////////////////////////////    search	
///////////////////just	
	public String searchDiffFile(String fileCode) throws FileException, DAOException, CommitExcetion, JSchException, IOException, RemoteFileException, SftpException{
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchDiffFile(fileCode);
	}
	
	public OriginFile justSearchOriginFileCode(String fileCode) throws DAOException, ParseException, FolderException, JSchException, IOException, SftpException{
		
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).justSearchOriginFileCode(fileCode);
	}
	public OriginFile justSearchOriginFilePath(String filePath) throws DAOException, ParseException, FolderException, JSchException, IOException, SftpException{
		
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).justSearchOriginFilePath(filePath);
	}
	
	
/////////////////searcg exactlly	
	public File searchFileCode(String fileCode) throws DAOException, ParseException, FolderException{
		
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchFileCode(fileCode);
	}
	public File searchFilePath(String filePath) throws DAOException, ParseException, FolderException{
		
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchFilePath(filePath);
	}
	public File searchFileContentPath(String filePath) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FolderException{
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchFileContentPath(filePath);
	}
	public File searchFileContentCode(String fileCode) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FolderException{
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchFileContentCode(fileCode);
	}
	public List<File> searchSiblingFileByPath(String filePath)throws DAOException, ParseException, FolderException{
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchSiblingFileByPath(filePath);
	}
	public List<File> searchSiblingFileByCode(String fileCode)throws DAOException, ParseException, FolderException{
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchSiblingFileByCode(fileCode);
	}
	public List<File> searchFileByFolderPath(String folderPath)throws DAOException, ParseException, FolderException{
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchFileByFolderPath(folderPath);
	}
	public List<File> searchFileByFolderCode(String folderCode)throws DAOException, ParseException, FolderException{
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchFileByFolderCode(folderCode);
	}
	public List<File> searchAllFileByFolderPath(String folderPath)throws DAOException, ParseException, FolderException{
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchAllFileByFolderPath(folderPath);
	}
	public List<File> searchAllFileByFolderCode(String folderCode)throws DAOException, ParseException, FolderException{
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchAllFileByFolderCode(folderCode);
	}

	
	
	public OriginFileList searchAllFilesByOnlyOriginFCode(String oriFolderCode) throws DAOException, ParseException, FolderException{
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchAllFilesByOnlyOriginFCode(oriFolderCode);
	}
	public OriginFileList searchAllFilesByOnlyOriginFPath(String oriFolderPath) throws DAOException, ParseException, FolderException{
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchAllFilesByOnlyOriginFPath(oriFolderPath);
	}
	public OriginFile searchOnlyOriginFileCode(String fileCode)throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FolderException{
		
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchOnlyOriginFileCode(fileCode);
	}
	public OriginFile searchOnlyOriginFilePath(String filePath) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FolderException{
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchOnlyOriginFilePath(filePath);
	}		
	public OriginFile searchOnlyOriginFilePathContent(String filePath) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FolderException{
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchOnlyOriginFilePathContent(filePath);
	}		
	public OriginFileList searchOnlyOriginFileByFolderPath(String oriFolderPath)throws DAOException, ParseException, FolderException{
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchOnlyOriginFileByFolderPath(oriFolderPath);
	}
	public OriginFileList searchOnlyOriginFileByFolderCode(String oriFolderCode)throws DAOException, ParseException, FolderException{
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchOnlyOriginFileByFolderCode(oriFolderCode);
	}
	
	
	//return only CopiedFile or Null
	public CopiedFile searchOnlyCpFileCode(String fileCode) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException{
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchOnlyCpFileCode(fileCode);
	}
	public CopiedFile searchOnlyCpFilePath(String filePath) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException{
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchOnlyCpFilePath(filePath);
	}
	public CopiedFile searchOnlyCpFileContent(String filePath) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException{
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchOnlyCpFileContent(filePath);
	}
		
	//return FileList for copiedFolder
	public List<File> searchOnlyCpFileByFolderPath(String cpFolderPath)throws DAOException, ParseException, FolderException{
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchOnlyCpFileByFolderPath(cpFolderPath);
	}
	public List<File> searchOnlyCpFileByFolderCode(String cpFolderCode)throws DAOException, ParseException, FolderException{
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchOnlyCpFileByFolderCode(cpFolderCode);
	}
	public List<File> searchFileAllByCpFolderCode(String cpFolderCode) throws DAOException, ParseException, FolderException{
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchAllCpFilesByFCode(cpFolderCode);
	}
	public List<File> searchFileAllByCpFolderPath(String cpFolderPath) throws DAOException, ParseException, FolderException{
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).searchAllCpFilesByFPath(cpFolderPath);
	}
	
	////check
	public boolean checkOriginFilePath(String filePath) throws DAOException, ParseException, FolderException, JSchException, IOException, SftpException{
		OriginFile file = this.justSearchOriginFilePath(filePath);
		if(file!=null)return true;
		return false;
	}
	public boolean checkOriginFileName(String folderCode,String name) throws DAOException, ParseException, FolderException{
		OriginFile file = ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).justSearchFileByFCode(folderCode, name);
		if(file!=null)return true;
		return false;
	}
	public boolean checkFileDiff(String oriFileCode, List<CommitInfo> commitInfoList) throws FileNotFoundException, DAOException, FileException, SftpException, IOException, JSchException, RemoteFileException, ParseException{
		System.out.println("\n                FileDBManager        checkFileDiff    line 292");
		
		return ((FileSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).checkFileDiff(oriFileCode, commitInfoList);
	}
	
/////////////////////////////////////////////////////////  update  	
	
	
	public boolean updateFileContent(String ownerCode, String storageCode,String projectCode,String folderCode,String fileCode, String newContent) throws FileNotFoundException, LoginException, DAOException, SftpException, IOException, JSchException, FileException, RemoteFileException, CommitExcetion{
		
		return ((FileUpdateAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).updateFileContent(ownerCode, storageCode, projectCode, folderCode, fileCode, newContent);
	}
	public CommitInfo mergeFileContent(String ownerCode, String fileCode, String newContent, String commitFileCode, String commiter, String commitCode,List<String> commitCodeList) throws FileNotFoundException, LoginException, DAOException, SftpException, IOException, JSchException, FileException, RemoteFileException, CommitExcetion, TeamException, ParseException{
		
		return ((FileUpdateAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).mergeFileContent(ownerCode, fileCode, newContent, commitFileCode, commiter, commitCode, commitCodeList);
	}
	public Map<OriginFile, CommitInfo> mergeNewFileContent(String ownerCode, String folderPath, String newContent, String commitFileCode, String commiter, String commitCode, List<String> commitCodeList) throws FileNotFoundException, LoginException, DAOException, SftpException, IOException, JSchException, FileException, RemoteFileException, CommitExcetion, TeamException, ParseException, FolderException{
		
		return ((FileUpdateAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).mergeNewFileContent(ownerCode, folderPath, newContent, commitFileCode, commiter, commitCode, commitCodeList);
	}
	public CommitInfo updateFileContent(String ownerCode, String storageCode,String projectCode,String folderCode,String fileCode, String newContent,String commitTitle, String commitContent) throws FileNotFoundException, LoginException, DAOException, SftpException, IOException, JSchException, FileException, RemoteFileException, CommitExcetion, TeamException{
		
		return ((FileUpdateAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("fileActionImpl")).updateFileContent(ownerCode, storageCode, projectCode, folderCode, fileCode, newContent,commitTitle,commitContent);
	}
	
}
