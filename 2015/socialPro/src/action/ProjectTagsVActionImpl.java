package action;

import java.util.List;

import dao.DAO;
import socialProExceptions.DAOException;
import dao.ProjectTagsVDAO;
import dto.ProjectTagsVDTO;
import factory.DAOFactory;

public class ProjectTagsVActionImpl extends BaseAction implements ProjectTagsVSearchAction{

	public ProjectTagsVActionImpl() {
		// TODO Auto-generated constructor stub
		this.setDAO((ProjectTagsVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectTagsVDAO"));
	}

	@Override
	public void setDAO(DAO dao) {
		// TODO Auto-generated method stub
		super.dao=(ProjectTagsVDAO)dao;
	}

	@Override
	public List<ProjectTagsVDTO> searchTagCode(String tagCode)
			throws DAOException {
		// TODO Auto-generated method stub
		
		return ((ProjectTagsVDAO)super.getDAO()).searchTagCode(tagCode);
	}
	
	@Override
	public List<ProjectTagsVDTO> searchTagCode(List<String> tagCode)
			throws DAOException {
		// TODO Auto-generated method stub
		
		return ((ProjectTagsVDAO)super.getDAO()).searchTagCodes(tagCode);
	}

	@Override
	public List<ProjectTagsVDTO> searchTagName(String tagName)
			throws DAOException {
		// TODO Auto-generated method stub
		return ((ProjectTagsVDAO)super.getDAO()).searchTagName(tagName);
	}
	
	@Override
	public List<ProjectTagsVDTO> searchTagName(List<String> tagName)
			throws DAOException {
		// TODO Auto-generated method stub
		return ((ProjectTagsVDAO)super.getDAO()).searchTagNames(tagName);
	}

	@Override
	public List<ProjectTagsVDTO> searchPCode(String projectCode)
			throws DAOException {
		// TODO Auto-generated method stub
		return ((ProjectTagsVDAO)super.getDAO()).searchPCode(projectCode);
	}

	@Override
	public List<ProjectTagsVDTO> searchPName(String projectName)
			throws DAOException {
		// TODO Auto-generated method stub
		return ((ProjectTagsVDAO)super.getDAO()).searchPName(projectName);
	}
	
}
