package action;

import socialProExceptions.DAOException;

public interface MemberUpdateAction {

	public boolean modifyMemberNickName(String code, String newNickName, String newPw) throws DAOException;
	public boolean modifyMemberNickName(String code, String newNickName) throws DAOException;
}
