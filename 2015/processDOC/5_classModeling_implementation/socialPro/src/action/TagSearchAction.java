package action;

import java.util.List;
import java.util.Map;

import socialProExceptions.DAOException;

public interface TagSearchAction {

	public Map<String,List<String>> searchTagsExist(List<String> tagName)throws DAOException; 
	
	public Map<String,List<String>> searchTag(String tagName)throws DAOException;
	public Map<String,List<String>> searchTag(List<String> tagName)throws DAOException;
	public List<String> searchTag(String tagName,String key)throws DAOException;
	public List<String> searchTag(List<String> tagName,String key)throws DAOException;
	public List<String> searchTagSCode(String sourceCode)throws DAOException;
	
	public Map<String,List<String>> searchTagCode(String tagCode)throws DAOException;
	public Map<String,List<String>> searchTagCode(List<String> tagCode)throws DAOException;
	public List<String> searchTagCode(String tagCode,String key)throws DAOException;
	public List<String> searchTagCode(List<String> tagCode,String key)throws DAOException;
}
