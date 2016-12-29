package action;

import java.util.List;

import dao.DAO;
import socialProExceptions.DAOException;
import dao.TeamTagsVDAO;
import dto.TeamTagsVDTO;
import factory.DAOFactory;

public class TeamTagsVActionImpl extends BaseAction implements TeamTagsVSearchAction{

	
	public TeamTagsVActionImpl() {
		// TODO Auto-generated constructor stub
		this.setDAO((TeamTagsVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamTagsVDAO"));
	}

	@Override
	public void setDAO(DAO dao) {
		// TODO Auto-generated method stub
		super.dao=(TeamTagsVDAO)dao;
	}

	@Override
	public List<TeamTagsVDTO> searchTagCode(String tagCode) throws DAOException {
		// TODO Auto-generated method stub
		return ((TeamTagsVDAO)super.getDAO()).searchTagCode(tagCode);
	}

	@Override
	public List<TeamTagsVDTO> searchTagCode(List<String> tagCode)
			throws DAOException {
		// TODO Auto-generated method stub
		return ((TeamTagsVDAO)super.getDAO()).searchTagCodes(tagCode);
	}

	@Override
	public List<TeamTagsVDTO> searchTagName(String tagName) throws DAOException {
		// TODO Auto-generated method stub
		return ((TeamTagsVDAO)super.getDAO()).searchTagName(tagName);
	}

	@Override
	public List<TeamTagsVDTO> searchTagName(List<String> tagName)
			throws DAOException {
		// TODO Auto-generated method stub
		return ((TeamTagsVDAO)super.getDAO()).searchTagNames(tagName);
	}

	@Override
	public List<TeamTagsVDTO> searchTeamName(String teamName)
			throws DAOException {
		// TODO Auto-generated method stub
		return ((TeamTagsVDAO)super.getDAO()).searchTeamName(teamName);
	}

	@Override
	public List<TeamTagsVDTO> searchTeamCode(String teamCode)
			throws DAOException {
		// TODO Auto-generated method stub
		return ((TeamTagsVDAO)super.getDAO()).searchTeamCode(teamCode);
	}

	@Override
	public List<TeamTagsVDTO> searchTeamDes(String description)
			throws DAOException {
		// TODO Auto-generated method stub
		return ((TeamTagsVDAO)super.getDAO()).searchDes(description);
	}

	@Override
	public List<TeamTagsVDTO> searchTeamNameAll(String teamName)
			throws DAOException {
		// TODO Auto-generated method stub
		return ((TeamTagsVDAO)super.getDAO()).searchTeamNameAll(teamName);
	}
	
}
