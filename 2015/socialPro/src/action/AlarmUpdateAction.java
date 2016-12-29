package action;

import model.Alarm;
import socialProExceptions.DAOException;

public interface AlarmUpdateAction {

	public boolean updateCheckDate(String alarmCode) throws DAOException;
	
	public Alarm updateCommitMsgCheckDate(String commitMsgCode) throws DAOException;
}
