package action;

import java.util.List;

import socialProExceptions.DAOException;


public interface TagUpdateAction {

	public boolean updateTag(String sourceCode,List<String> newTagNameList)throws DAOException;
}
