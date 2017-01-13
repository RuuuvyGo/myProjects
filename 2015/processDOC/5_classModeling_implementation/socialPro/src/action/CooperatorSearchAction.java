package action;

import java.util.List;

import socialProExceptions.DAOException;

public interface CooperatorSearchAction {

	public List<String> searchCooper(String setCode) throws DAOException;//return memberList
	public List<String> searchSCode(String memberCode) throws DAOException;//return codeList
	public List<String> searchSCode(String memberCode,String front) throws DAOException;//return codeList
}
