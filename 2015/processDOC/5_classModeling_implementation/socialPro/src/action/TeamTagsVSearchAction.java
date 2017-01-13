package action;

import java.util.List;

import socialProExceptions.DAOException;
import dto.TeamTagsVDTO;

public interface TeamTagsVSearchAction {

	public List<TeamTagsVDTO> searchTagCode(String tagCode)throws DAOException;
	public List<TeamTagsVDTO> searchTagCode(List<String> tagCode)throws DAOException;
	public List<TeamTagsVDTO> searchTagName(String tagName)throws DAOException;
	public List<TeamTagsVDTO> searchTagName(List<String> tagName)throws DAOException;
	public List<TeamTagsVDTO> searchTeamName(String teamName)throws DAOException;
	public List<TeamTagsVDTO> searchTeamCode(String teamCode)throws DAOException;
	public List<TeamTagsVDTO> searchTeamDes(String description)throws DAOException;
	public List<TeamTagsVDTO> searchTeamNameAll(String teamName)throws DAOException;
	
}
