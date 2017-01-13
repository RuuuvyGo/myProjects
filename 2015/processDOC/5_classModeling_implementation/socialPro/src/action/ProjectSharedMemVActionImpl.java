package action;

import java.util.List;

import dao.DAO;
import socialProExceptions.DAOException;
import dao.ProjectSharedMemVDAO;
import dto.ProjectSharedMemVDTO;
import factory.DAOFactory;

public class ProjectSharedMemVActionImpl extends BaseAction implements ProjectSharedMemVSearchAction{

	public ProjectSharedMemVActionImpl() {
		// TODO Auto-generated constructor stub
		this.setDAO((ProjectSharedMemVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("projectSharedMemVDAO"));
	}
	@Override
	public void setDAO(DAO dao) {
		// TODO Auto-generated method stub
		this.dao = (ProjectSharedMemVDAO)dao;
	}

	@Override
	public List<ProjectSharedMemVDTO> searchProjectCode(String projectCode)
			throws DAOException {
		// TODO Auto-generated method stub
		return ((ProjectSharedMemVDAO)this.getDAO()).searchProjectCode(projectCode);
	}

	@Override
	public List<ProjectSharedMemVDTO> searchProjectName(String projectName)
			throws DAOException {
		// TODO Auto-generated method stub
		return ((ProjectSharedMemVDAO)this.getDAO()).searchProjectName(projectName);
	}

	@Override
	public List<ProjectSharedMemVDTO> searchMemberCode(String memberCode)
			throws DAOException {
		// TODO Auto-generated method stub
		return ((ProjectSharedMemVDAO)this.getDAO()).searchMemberCode(memberCode);
	}

	@Override
	public List<ProjectSharedMemVDTO> searchNickName(String nickName)
			throws DAOException {
		// TODO Auto-generated method stub
		return ((ProjectSharedMemVDAO)this.getDAO()).searchNickName(nickName);
	}

	@Override
	public List<ProjectSharedMemVDTO> searchEmail(String email)
			throws DAOException {
		// TODO Auto-generated method stub
		return ((ProjectSharedMemVDAO)this.getDAO()).searchEmail(email);
	}

}
