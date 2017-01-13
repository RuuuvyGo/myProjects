package action;

import java.util.List;

import model.CommitMessage;
import socialProExceptions.DAOException;

public interface CommitUpdateAction {

	
	public boolean sendMergeCommitMsg(CommitMessage commitMsg) throws DAOException;
	public boolean sendMergeCommitList(List<String> commitCodeList) throws DAOException;
	
	public boolean sendMergeCommit(String commitCode) throws DAOException;
}
