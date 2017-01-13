package manager;

import java.util.List;

import model.Alarm;
import model.GroupAlarm;

public interface AlarmInsertable {

	public void putAlarm(Alarm alarm);
	public void putAlarmList(List<Alarm> alarmList);
	public void putGroupAlarm(GroupAlarm gAlarm);
}
