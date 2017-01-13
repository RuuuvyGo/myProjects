package action;

import model.Member;
import model.MemberList;
import socialProExceptions.DAOException;

public interface MemberSearchAction {

	public Member searchMemberCode(String code) throws DAOException;
	public Member searchMemberNickName(String nickName) throws DAOException;
	public Member searchMemberEmail(String email) throws DAOException;
	public Member searchMemberLoginInfo(String email,String pw) throws DAOException;
	public MemberList searchMemberSchool(String school) throws DAOException;
	public MemberList searchMemberSchool(String school,int entranceYear) throws DAOException;
	public MemberList searchMemberEntranceYear(int entranceYear) throws DAOException;
}
