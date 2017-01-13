package action;

import socialProExceptions.DAOException;

public interface CooperatorDropAction {
	
	public boolean dropCooper(String setCode,String memberCode) throws DAOException;
	public int dropCooperCode(String memberCode) throws DAOException;
	public int dropCooperSCode(String setCode) throws DAOException;
}
