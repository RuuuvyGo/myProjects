package action;

import java.text.ParseException;
import java.util.List;

import model.CommitInfo;
import socialProExceptions.CommitExcetion;
import socialProExceptions.DAOException;

public interface CommitInsertAction {
	public String insertCommit(CommitInfo commitInfo) throws DAOException, CommitExcetion;
	public CommitInfo mergeCommit(String oriCommitCode, String setCode) throws DAOException, CommitExcetion, ParseException;
	public String copyCommit(String memberCode, String cpSetCode, String cal,String oriSetCode,int type) throws DAOException, CommitExcetion;
	public CommitInfo copyCommit(String commitCode, String oriSetCode, String oriOwnerCode,String insertDate, String type) throws DAOException, CommitExcetion, ParseException;
}
