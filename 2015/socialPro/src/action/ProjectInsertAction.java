package action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import model.CopiedProject;
import model.CopiedProjectList;
import model.FileNode;
import model.OriginProject;
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


public interface ProjectInsertAction {

	//public OriginProject insertOriginProject(String memberCode,String nickName, String name, String description, String path,  List<String> tagList)throws DAOException, SftpException, JSchException, FolderException, ProjectException;
	public OriginProject insertOriginProject(String memberCode,String nickName, String name, String description, String path,  List<String> tagList)throws DAOException, SftpException, JSchException, FolderException, ProjectException, TagException;
	
	public String insertCopiedProject(String memberCode,String nickName, String name, String description, String path,  List<String> tagList,String originProjecCode)throws DAOException;
	//public List<File> insertCopiedTeamProject(String teamCode, String teamName,String projectName, String proDes,String path,List<String> tagList) throws DAOException, SftpException, JSchException, FolderException, ProjectException, CommitExcetion, TagException;
	public List<File> insertCopiedTeamProject(String teamCode, String teamName,String projectName, String proDes,String path,List<String> tagList) throws DAOException, SftpException, JSchException, FolderException, ProjectException, TagException;
	
	//public CopiedProject insertCopiedProject(String oriOwnerCode,String oriProjectCode,String oriFolderCode,String memberCode, String nickName,String teamCode, String teamName, String projectName, String path)throws DAOException, SftpException, JSchException, FolderException, ProjectException, CommitExcetion;
	public CopiedProject insertCopiedProject(String oriOwnerCode,String oriProjectCode,String oriFolderCode,String memberCode, String nickName,String teamCode, String teamName, String projectName, String path)throws DAOException, SftpException, JSchException, FolderException, ProjectException;
	
	public Map<String,CopiedProject> insertCopiedProjectList(List<FileNode> oriProjectList,String memberCode, String nickName,String teamCode, String teamName)throws DAOException, SftpException, JSchException, FolderException, ProjectException, FileNotFoundException, ParseException, IOException, FileException, RemoteFileException, CommitExcetion;
	public CopiedProject insertCopiedProject(FileNode oriProject,String memberCode, String nickName,String ownerCode, String ownerName)throws DAOException, SftpException, JSchException, FolderException, ProjectException, FileNotFoundException, ParseException, IOException, FileException, RemoteFileException, CommitExcetion;
	List<File> insertCopiedTeamProject(Team teamInfo, String projectName,String proDes, String path, List<String> tagList)throws DAOException, SftpException, JSchException, FolderException,ProjectException, CommitExcetion, TagException;
	
}
