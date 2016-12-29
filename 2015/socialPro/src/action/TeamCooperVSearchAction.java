package action;

import java.util.List;

import socialProExceptions.DAOException;
import dto.TeamCooperVDTO;

public interface TeamCooperVSearchAction {

	public List<TeamCooperVDTO> searchTeamCode(String teamCode)throws DAOException;
	public List<TeamCooperVDTO> searchTeamCode(String teamCode,String memberCode)throws DAOException;
	public List<TeamCooperVDTO> searchTeamName(String teamName) throws DAOException;
	public List<TeamCooperVDTO> searchMCode(String memberCode) throws DAOException;
	public List<TeamCooperVDTO> searchNickName(String nickName) throws DAOException;
	public List<TeamCooperVDTO> searchEmail(String email) throws DAOException;
}
