package manager;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Alarm;
import model.CommitMessage;
import model.CooperatorMessage;
import model.GroupAlarm;
import model.Team;
import model.TeamList;
import socialProExceptions.DAOException;
import socialProExceptions.FolderException;
import socialProExceptions.ProjectException;
import socialProExceptions.TeamException;
import action.AlarmUpdateAction;
import action.CommitMessageSearchAction;
import action.CooperatorMessageSearchAction;
import factory.ActionFactory;
import factory.DAOFactory;

public class AlarmDBManager {

	private static AlarmDBManager INSTANCE;
	
	static{INSTANCE = new AlarmDBManager();}
	private AlarmDBManager(){}
	public static AlarmDBManager getINSTANCE() {
		return INSTANCE;
	}
	
	public Alarm updateCommitMsgAlarmCheckDate(String commitMessageCode) throws DAOException{
		
		return ((AlarmUpdateAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("alarmActionImpl")).updateCommitMsgCheckDate(commitMessageCode);
	}
}
