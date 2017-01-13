package action;

import java.util.List;

import socialProExceptions.DAOException;

public interface CooperatorInsertAction {

	public boolean insertCooper(String memberCode,String setCode) throws DAOException;
	public boolean insertCooper(List<String> memberCode,String setCode) throws DAOException;
}
