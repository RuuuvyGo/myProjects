package action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import model.CommitInfo;
import model.CopiedFolder;
import model.CopiedFolderList;
import model.FileNode;
import model.OriginFolder;
import socialProExceptions.CommitExcetion;
import socialProExceptions.DAOException;
import socialProExceptions.FileException;
import socialProExceptions.FolderException;
import socialProExceptions.RemoteFileException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public interface FolderInsertAction {

	public OriginFolder insertOriginFolder(OriginFolder folder,String ownerCode)throws DAOException, SftpException, JSchException, FolderException;
	public OriginFolder insertOriginProjectFolder(OriginFolder folder,String ownerCode)throws DAOException, SftpException, JSchException, FolderException;
	
	//public OriginFolder insertOriginFolder(OriginFolder folder,String ownerCode)throws DAOException, SftpException, JSchException, FolderException;
	//public Map<String,CopiedFolder> insertTeamFolder(OriginFolder folder,String ownerCode)throws DAOException, SftpException, JSchException, FolderException, CommitExcetion;
	
	public Map<String,CopiedFolder> insertTeamFolder(OriginFolder folder,String ownerCode)throws DAOException, SftpException, JSchException, FolderException;
	public String insertOriTeamFolder(OriginFolder folder,String teamCode)throws DAOException, SftpException, JSchException, FolderException;
	public CopiedFolder insertCopiedProjectFolder(CopiedFolder folder,String originOwnerCode,String oriFolderCode,String memberCode)throws DAOException, SftpException, JSchException, FolderException, CommitExcetion;
	//public CopiedFolder insertCopiedFolder(CopiedFolder folder,String originOwnerCode,String oriFolderCode,String memberCode)throws DAOException, SftpException, JSchException, FolderException, CommitExcetion;
	
	public CopiedFolder insertCopiedFolder(CopiedFolder folder,String originOwnerCode,String oriFolderCode,String memberCode)throws DAOException, SftpException, JSchException, FolderException;
	public boolean insertCopiedFolders(FileNode folder,String originOwnerCode,String memberCode)throws DAOException, SftpException, JSchException, FolderException, FileNotFoundException, ParseException, IOException, FileException, RemoteFileException, CommitExcetion;
	public String insertCopiedFolder(CopiedFolder folder)throws DAOException;
	
	//when merge
	public Map<OriginFolder,CommitInfo> copyFolder(String oriFolderCode, String commitCode, String folderParentPath, String memberCode) throws DAOException, ParseException, FolderException, CommitExcetion, SftpException, JSchException;
}
