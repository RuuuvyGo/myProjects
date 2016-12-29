package action;

import java.util.List;

import socialProExceptions.DAOException;


public interface CopiedInfoInsertAction {

	public boolean insertCopiedCode(String copiedCode,String originCode) throws DAOException, socialProExceptions.DAOException;
	public int insertOriginCode(String originCode,List<String> copiedCodeList) throws DAOException;
}
