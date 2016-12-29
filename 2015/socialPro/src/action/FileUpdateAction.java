package action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

import model.CommitInfo;
import model.OriginFile;
import socialProExceptions.CommitExcetion;
import socialProExceptions.DAOException;
import socialProExceptions.FileException;
import socialProExceptions.FolderException;
import socialProExceptions.RemoteFileException;
import socialProExceptions.TeamException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public interface FileUpdateAction {

	public boolean updateFileContent(String memberCode, String storageCode,String projectCode,String folderCode,String fileCode, String newContent) throws DAOException, FileNotFoundException, SftpException, IOException, JSchException, FileException, LoginException, RemoteFileException, CommitExcetion;
	public CommitInfo updateFileContent(String memberCode, String storageCode,String projectCode,String folderCode,String fileCode, String newContent,String commitTitle,String commitContent) throws DAOException, FileNotFoundException, SftpException, IOException, JSchException, FileException, LoginException, RemoteFileException, TeamException, CommitExcetion;
	
	public CommitInfo mergeFileContent(String memberCode, String fileCode, String newContent, String commitFileCode,String commiter, String commitCode, List<String> commitCodeList) throws DAOException, FileNotFoundException, SftpException, IOException, JSchException, FileException, LoginException, RemoteFileException , TeamException, CommitExcetion, ParseException;
	public Map<OriginFile,CommitInfo> mergeNewFileContent(String memberCode, String folderPath, String newContent, String commitFileCode,String commiter, String commitCode, List<String> commitCodeList) throws DAOException, FileNotFoundException, SftpException, IOException, JSchException, FileException, LoginException, RemoteFileException , TeamException, CommitExcetion, ParseException, FolderException;
}
