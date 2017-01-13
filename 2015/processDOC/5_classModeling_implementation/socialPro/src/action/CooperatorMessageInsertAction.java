package action;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import socialProExceptions.DAOException;
import socialProExceptions.MemberException;
import model.Alarm;
import model.CooperatorMessage;

public interface CooperatorMessageInsertAction {

	public Map<Alarm,CooperatorMessage> insertTeamCooperatorInviteMsg(String teamCode,String teamName,String teamManagerCode, List<String> cooperList) throws DAOException, MemberException, ParseException;
	public Map<Alarm,CooperatorMessage> insertProjectCooperatorMsg(String oriProCode,String oriProName,String oriProOwnerCode, String cooperCode, String cooperNick) throws DAOException, MemberException, ParseException;
	public Map<Alarm,CooperatorMessage> insertProjectCooperatorMsgSendToOwner(String oriProCode,String oriProName,String oriProOwnerCode, String cooperCode, String cooperNick) throws DAOException, MemberException, ParseException;
}
