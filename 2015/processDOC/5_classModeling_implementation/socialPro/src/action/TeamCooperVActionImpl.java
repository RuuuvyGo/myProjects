package action;

import java.util.List;

import dao.DAO;
import socialProExceptions.DAOException;
import dao.TeamCooperVDAO;
import dto.TeamCooperVDTO;
import factory.DAOFactory;

public class TeamCooperVActionImpl extends BaseAction implements TeamCooperVSearchAction{

	
	public TeamCooperVActionImpl() {
		// TODO Auto-generated constructor stub
		this.setDAO((TeamCooperVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamCooperVDAO"));
	}

	@Override
	public void setDAO(DAO dao) {
		// TODO Auto-generated method stub
		super.dao=(TeamCooperVDAO)dao;
	}

	@Override
	public List<TeamCooperVDTO> searchTeamCode(String teamCode)
			throws DAOException {
		// TODO Auto-generated method stub
		return ((TeamCooperVDAO)super.getDAO()).searchTeamCode(teamCode);
	}

	@Override
	public List<TeamCooperVDTO> searchTeamCode(String teamCode,
			String memberCode) throws DAOException {
		// TODO Auto-generated method stub
		return ((TeamCooperVDAO)super.getDAO()).searchTeamCode(teamCode, memberCode);
	}

	@Override
	public List<TeamCooperVDTO> searchTeamName(String teamName)
			throws DAOException {
		// TODO Auto-generated method stub
		return ((TeamCooperVDAO)super.getDAO()).searchTeamName(teamName);
	}

	@Override
	public List<TeamCooperVDTO> searchMCode(String memberCode)
			throws DAOException {
		// TODO Auto-generated method stub
		return ((TeamCooperVDAO)super.getDAO()).searchMCode(memberCode);
	}

	@Override
	public List<TeamCooperVDTO> searchNickName(String nickName)
			throws DAOException {
		// TODO Auto-generated method stub
		return ((TeamCooperVDAO)super.getDAO()).searchMNickName(nickName);
	}

	@Override
	public List<TeamCooperVDTO> searchEmail(String email) throws DAOException {
		// TODO Auto-generated method stub
		return ((TeamCooperVDAO)super.getDAO()).searchEamil(email);
	}

	
}
