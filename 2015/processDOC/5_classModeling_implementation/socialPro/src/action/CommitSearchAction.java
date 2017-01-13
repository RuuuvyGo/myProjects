package action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import model.CommitInfo;
import socialProExceptions.DAOException;
import socialProExceptions.FileException;
import socialProExceptions.RemoteFileException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public interface CommitSearchAction {

	public List<CommitInfo> searchCommitCodeAll(String commitCode) throws DAOException, FileException, FileNotFoundException, SftpException, IOException, JSchException, RemoteFileException, ParseException;
	public List<CommitInfo> searchCommitCode(String commitCode) throws DAOException, FileException, FileNotFoundException, SftpException, IOException, JSchException, RemoteFileException, ParseException;
	public CommitInfo searchOnlyCommitCode(String commitCode) throws DAOException, FileException, FileNotFoundException, SftpException, IOException, JSchException, RemoteFileException, ParseException; 
	public List<CommitInfo> searchCommitContent(List<CommitInfo> commitInfoList) throws DAOException, FileException, FileNotFoundException, SftpException, IOException, JSchException, RemoteFileException, ParseException;
	public List<CommitInfo> searchCommitCodesAll(String commitCode) throws DAOException, FileException, FileNotFoundException, SftpException, IOException, JSchException, RemoteFileException, ParseException;
	public Map<String,CommitInfo> searchCommitCodeList(List<String> commitCodeList) throws DAOException, FileException, FileNotFoundException, SftpException, IOException, JSchException, RemoteFileException, ParseException;
	
	public Map<String,List<CommitInfo>> searchProjectCommitList(Map<String,Map<String,List<String>>> projectChildren) throws DAOException, FileException, FileNotFoundException, SftpException, IOException, JSchException, RemoteFileException, ParseException;
	public List<CommitInfo> searchMemberProjectCommitList(String memberCode,Map<String,List<String>> projectChildren) throws FileNotFoundException, ParseException, DAOException, FileException, SftpException, IOException, JSchException, RemoteFileException;
	public Map<String,List<CommitInfo>> searchTeamProjectCommitList(Map<String,Map<String,List<String>>> teamProjectChildren) throws DAOException, FileException, FileNotFoundException, SftpException, IOException, JSchException, RemoteFileException, ParseException;
	
	//for file Content
	public List<CommitInfo> searchCommitInfoContentDetails(String fileCode, List<CommitInfo> commitInfoList) throws FileNotFoundException, ParseException, DAOException, FileException, SftpException, IOException, JSchException, RemoteFileException;
	public List<CommitInfo> searchCommitInfoByDate(String objCode,String date) throws DAOException, FileNotFoundException, ParseException, FileException, SftpException, IOException, JSchException, RemoteFileException;
	public List<CommitInfo> searchCommitInfoContentByDate(String objCode,String date) throws DAOException, FileNotFoundException, ParseException, FileException, SftpException, IOException, JSchException, RemoteFileException;
}
