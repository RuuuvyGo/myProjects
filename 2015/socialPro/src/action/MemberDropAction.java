package action;

import socialProExceptions.DAOException;

public interface MemberDropAction{

	public boolean deleteMember(String code) throws DAOException;
	public boolean deleteMember(String email,String pw) throws DAOException;
}
