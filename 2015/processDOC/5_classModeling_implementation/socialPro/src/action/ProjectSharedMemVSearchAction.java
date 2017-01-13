package action;

import java.util.List;

import socialProExceptions.DAOException;
import dto.ProjectSharedMemVDTO;

public interface ProjectSharedMemVSearchAction {
 
	public List<ProjectSharedMemVDTO> searchProjectCode(String projectCode)throws DAOException;
	public List<ProjectSharedMemVDTO> searchProjectName(String projectName)throws DAOException;
	public List<ProjectSharedMemVDTO> searchMemberCode(String memberCode)throws DAOException;
	public List<ProjectSharedMemVDTO> searchNickName(String nickName)throws DAOException;
	public List<ProjectSharedMemVDTO> searchEmail(String email)throws DAOException;
}
