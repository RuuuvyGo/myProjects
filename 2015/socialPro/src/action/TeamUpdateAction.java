package action;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import socialProExceptions.DAOException;
import socialProExceptions.FolderException;
import socialProExceptions.ProjectException;

public interface TeamUpdateAction {
	
	public boolean updateTeamName(String teamCode, String newName) throws DAOException;
	public boolean updateTeamDes(String teamCode, String newDescription) throws DAOException;
	public boolean updateTeamManager(String teamCode, String newManager) throws DAOException;
	public boolean updateTeamCooperators(String teamCode, List<String> newCooperators) throws DAOException;
	public boolean addTeamCooperators(String teamCode, List<String> cooperatorList) throws DAOException, SftpException, JSchException;
	public boolean addTeamCooperator(String teamCode, String cooperatorCode) throws DAOException, SftpException, JSchException, ParseException, FolderException, ProjectException, IOException;
	public boolean updateTeamTags(String teamCode, List<String> newTags) throws DAOException;

}
