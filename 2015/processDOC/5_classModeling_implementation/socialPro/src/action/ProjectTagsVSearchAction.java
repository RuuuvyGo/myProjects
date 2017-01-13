package action;

import java.util.List;

import socialProExceptions.DAOException;
import dto.ProjectTagsVDTO;

public interface ProjectTagsVSearchAction {

	public List<ProjectTagsVDTO> searchTagCode(String tagCode) throws DAOException;
	public List<ProjectTagsVDTO> searchTagCode(List<String> tagCode) throws DAOException;
	public List<ProjectTagsVDTO> searchTagName(String tagName) throws DAOException;
	public List<ProjectTagsVDTO> searchTagName(List<String> tagName) throws DAOException;
	public List<ProjectTagsVDTO> searchPCode(String projectCode) throws DAOException;
	public List<ProjectTagsVDTO> searchPName(String projectName) throws DAOException;
}
