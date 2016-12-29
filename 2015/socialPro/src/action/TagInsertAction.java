package action;

import java.util.List;

import socialProExceptions.DAOException;

public interface TagInsertAction {
	
	public boolean insertTag(String tagName, String sourceCode)throws DAOException;
	public int insertTag(List<String> tagNameList, String sourceCode)throws DAOException;
	
	public int insertTagDetails(List<String> tagCodeList, String sourceCode)throws DAOException;

}
