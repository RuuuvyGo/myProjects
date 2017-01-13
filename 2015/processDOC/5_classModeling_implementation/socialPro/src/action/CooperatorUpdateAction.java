package action;

import java.util.List;

import socialProExceptions.DAOException;

public interface CooperatorUpdateAction {

	public boolean updateCooperator(List<String> memberCodes, String setCode) throws DAOException;
}
