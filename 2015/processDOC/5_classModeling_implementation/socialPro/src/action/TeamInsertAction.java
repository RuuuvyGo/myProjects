package action;

import java.util.List;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import socialProExceptions.DAOException;

public interface TeamInsertAction {
	
	public String insertTeam(String teamName, String manager,List<String> tagList) throws DAOException;
	public String insertTeam(String teamName, String description ,String manager, List<String> tagList) throws DAOException, SftpException, JSchException;

}
