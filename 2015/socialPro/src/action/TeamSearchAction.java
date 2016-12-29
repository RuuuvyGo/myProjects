package action;

import java.util.List;

import model.Team;
import model.TeamList;
import socialProExceptions.DAOException;
import socialProExceptions.TeamException;

public interface TeamSearchAction {

	public TeamList searchMemberCode(String memberCode)throws DAOException;
	public Team searchTeamCode(String teamCode)throws DAOException, TeamException;
	public TeamList searchManagerCode(String managerCode)throws DAOException;
	public TeamList searchCooper(String cooperCode)throws DAOException;
	public TeamList searchCooper(List<String> cooperCode)throws DAOException;
	public Team searchTeamName(String teamName)throws DAOException;
	public TeamList searchTeamDes(String description)throws DAOException;
	public TeamList searchTags(String tags)throws DAOException;
	public TeamList searchTags(List<String> tags)throws DAOException;
	public TeamList searchTags(List<String> tags,String name)throws DAOException;
	public TeamList searchTags(List<String> tags,String name,String description)throws DAOException;
	
}
