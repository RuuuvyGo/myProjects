package action;

import socialProExceptions.DAOException;

public interface TeamDropAction {
	
	public boolean dropTeamCode(String code) throws DAOException;

}
