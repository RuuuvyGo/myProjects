package action;

import socialProExceptions.DAOException;

public interface TagDropAction {

	public boolean dropTagCode(String tagCode)throws DAOException;
	public boolean dropTagName(String tagName)throws DAOException;
	public int dropTagCode(String tagCode,String front)throws DAOException;
	public int dropTagName(String tagName,String front)throws DAOException;
	public boolean dropTagCodesC(String tagCode,String sourceCode)throws DAOException;
	public boolean dropTagNamesC(String tagName,String sourceCode)throws DAOException;
	public int dropTagSCode(String sourceCode)throws DAOException;
}
