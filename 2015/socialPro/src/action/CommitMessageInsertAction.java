package action;

import java.util.List;
import java.util.Map;

import model.Alarm;
import model.CommitMessage;
import model.GroupAlarm;
import socialProExceptions.DAOException;

public interface CommitMessageInsertAction {

	public Map<Alarm,CommitMessage> insertTeamCommitMessageSendToOwner(CommitMessage commitMsg) throws DAOException;
	public CommitMessage insertSharedProjectCommitMessageSendToOwner(CommitMessage commitMsg) throws DAOException;
	public CommitMessage insertTeamCommitMsgFromOwner(CommitMessage commitMsg) throws DAOException;
	public Map<CommitMessage, List<GroupAlarm>> insertSharedProCommitMsgFromOwner(CommitMessage commitMsg,List<String> cooperList) throws DAOException;
}
