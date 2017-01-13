package action;

import java.util.List;

import socialProExceptions.DAOException;
import model.GroupAlarm;

public interface GroupAlarmInsertAction {

	public GroupAlarm insertGroupAlarm(String targetCode, List<String> groupMemberCodeList) throws DAOException;
}
