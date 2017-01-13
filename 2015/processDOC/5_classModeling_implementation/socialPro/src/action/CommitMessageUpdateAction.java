package action;

import socialProExceptions.DAOException;

public interface CommitMessageUpdateAction {

	public String updateAlarmCheckDate(String messageCode, String alarmCode) throws DAOException;
}
