package action;

import socialProExceptions.DAOException;
import model.Alarm;

public interface AlarmInsertAction {

	public Alarm insertAlarm(String targetCode) throws DAOException;
}
