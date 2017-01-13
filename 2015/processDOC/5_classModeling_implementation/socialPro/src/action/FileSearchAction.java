package action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import model.CommitInfo;
import model.CopiedFile;
import model.OriginFile;
import model.OriginFileList;
import socialProExceptions.CommitExcetion;
import socialProExceptions.DAOException;
import socialProExceptions.FileException;
import socialProExceptions.FolderException;
import socialProExceptions.RemoteFileException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public interface FileSearchAction {

	//return only originFile even that is copiedFile
	public OriginFile searchFileName(String folderName ,String fileName) throws DAOException, ParseException;		

	//return only originFile or null
	public OriginFile searchOnlyOriginFileCode(String fileCode)throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FolderException;
	public OriginFile searchOnlyOriginFilePath(String filePath) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FolderException;		
	public OriginFile searchOnlyOriginFilePathContent(String filePath) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FolderException;		
	
	//return only CopiedFile or Null
	public CopiedFile searchOnlyCpFileCode(String fileCode) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException;
	public CopiedFile searchOnlyCpFilePath(String filePath) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException;
	public CopiedFile searchOnlyCpFileContent(String filePath) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException;
	
	//return only originFileList or empty list
	public OriginFileList searchAllFilesByOnlyOriginFCode(String folderCode)throws DAOException, ParseException, FolderException;
	public OriginFileList searchAllFilesByOnlyOriginFPath(String folderPath)throws DAOException, ParseException, FolderException;
	//return only CopiedFileList even cpFolder has originFIle (if folder has originFile then error)
	public List<File> searchAllCpFilesByFCode(String cpFolderCode)throws DAOException, ParseException, FolderException;
	public List<File> searchAllCpFilesByFPath(String cpFolderPath)throws DAOException, ParseException, FolderException;
	
	//return FileList for copiedFolder
	public OriginFileList searchOnlyOriginFileByFolderPath(String oriFolderPath)throws DAOException, ParseException, FolderException;
	public OriginFileList searchOnlyOriginFileByFolderCode(String oriFolderCode)throws DAOException, ParseException, FolderException;	
	//return FileList for copiedFolder
	public List<File> searchOnlyCpFileByFolderPath(String cpFolderPath)throws DAOException, ParseException, FolderException;
	public List<File> searchOnlyCpFileByFolderCode(String cpFolderCode)throws DAOException, ParseException, FolderException;
	
	//doesn't matter is copiedFolder or originFolder
	public OriginFileList justSearchAllFilesByFCode(String folderCode)throws DAOException, ParseException, FolderException;
	public OriginFileList justSearchAllFilesByFPath(String folderPath)throws DAOException, ParseException, FolderException;
	public OriginFileList justSearchFileByFCode(String folderCode)throws DAOException, ParseException, FolderException;
	public OriginFile justSearchFileByFCode(String folderCode,String name)throws DAOException, ParseException, FolderException;
	public OriginFileList justSearchFileByFPath(String folderPath)throws DAOException, ParseException, FolderException;
	//doesn't matter is copiedFolder or originFolder
	public OriginFile justSearchOriginFileCode(String fileCode)throws DAOException, ParseException, FolderException, JSchException, IOException, SftpException;
	public OriginFile justSearchOriginFilePath(String filePath)throws DAOException, ParseException, FolderException,JSchException, IOException, SftpException;	
	
	//just for search diff content
	public String searchDiffFile(String fileCode) throws FileException, DAOException, CommitExcetion, JSchException, IOException, RemoteFileException, SftpException;
	
	///////////////////////////////////////////////////////////////////
	
	public File searchFilePath(String filePath)throws DAOException, ParseException, FolderException;
	public File searchFileCode(String fileCode)throws DAOException, ParseException, FolderException;
	public File searchFileContentPath(String filePath) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FolderException;
	public File searchFileContentCode(String fileCode) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FolderException;
	public List<File> searchSiblingFileByPath(String filePath)throws DAOException, ParseException, FolderException;
	public List<File> searchSiblingFileByCode(String fileCode)throws DAOException, ParseException, FolderException;
	public List<File> searchFileByFolderPath(String folderPath)throws DAOException, ParseException, FolderException;
	public List<File> searchFileByFolderCode(String folderCode)throws DAOException, ParseException, FolderException;
	public List<File> searchAllFileByFolderPath(String folderPath)throws DAOException, ParseException, FolderException;
	public List<File> searchAllFileByFolderCode(String folderCode)throws DAOException, ParseException, FolderException;

	public boolean checkFileDiff(String oriFileCode, List<CommitInfo> commitInfoList) throws FileNotFoundException, DAOException, FileException, SftpException, IOException, JSchException, RemoteFileException, ParseException;
}
