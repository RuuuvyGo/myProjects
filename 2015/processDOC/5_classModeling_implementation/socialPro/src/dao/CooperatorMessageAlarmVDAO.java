package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import socialProExceptions.DAOException;
import dto.CooperatorMessageAlarmVDTO;

public class CooperatorMessageAlarmVDAO extends BaseDAO{

	private static final String SELECT_COOPERMESSAGEV_SQL1 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where MESSAGECODE=?";
	private static final String SELECT_COOPERMESSAGEV_SQL123 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where MESSAGECODE=? and (SENDERCODE=? or RECEIVERCODE=?)";
	private static final String SELECT_COOPERMESSAGEV_SQL2 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where SENDERCODE=?";
	private static final String SELECT_COOPERMESSAGEV_SQL2T = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where SENDERCODE=? and SOURCECODE like 'team_%'";
	private static final String SELECT_COOPERMESSAGEV_SQL2P = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where SENDERCODE=? and SOURCECODE like 'project_%'";
	private static final String SELECT_COOPERMESSAGEV_SQL26 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where SENDERCODE=? and SOURCECODE=?";
	private static final String SELECT_COOPERMESSAGEV_SQL23 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where SENDERCODE=? or RECEIVERCODE=?";
	private static final String SELECT_COOPERMESSAGEV_SQL23T = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where SENDERCODE=? or RECEIVERCODE=? SOURCECODE like 'team_%'";
	private static final String SELECT_COOPERMESSAGEV_SQL236 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where (SENDERCODE=? or RECEIVERCODE=?) and SOURCECODE=?";
	private static final String SELECT_COOPERMESSAGEV_SQL3 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where RECEIVERCODE=?";
	private static final String SELECT_COOPERMESSAGEV_SQL3T = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where RECEIVERCODE=? and SOURCECODE like 'team_%'";
	private static final String SELECT_COOPERMESSAGEV_SQL3P = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where RECEIVERCODE=? and SOURCECODE like 'project_%'";
	private static final String SELECT_COOPERMESSAGEV_SQL36 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where RECEIVERCODE=? and SOURCECODE=?";
	private static final String SELECT_COOPERMESSAGEV_SQL4 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where SENDDATE=to_timeStamp(?,'yyyy-mm-dd hh24:mi:ss.ff3')";
	private static final String SELECT_COOPERMESSAGEV_SQL5 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where TYPE=?";
	private static final String SELECT_COOPERMESSAGEV_SQL6 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where SOURCECODE=?";
	private static final String SELECT_COOPERMESSAGEV_SQL7 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where ALARMCODE=?";
	private static final String SELECT_COOPERMESSAGEV_SQL8 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where CHECKDATE=to_timeStamp(?,'yyyy-mm-dd hh24:mi:ss.ff3')";

	private static final String SELECT_COOPERMESSAGEV_SQL18_0 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where MESSAGECODE=? and CHECKDATE is null";
	private static final String SELECT_COOPERMESSAGEV_SQL1238_0 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where MESSAGECODE=? and (SENDERCODE=? or RECEIVERCODE=?) and CHECKDATE is null";
	private static final String SELECT_COOPERMESSAGEV_SQL28_0 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where SENDERCODE=? and CHECKDATE is null";
	private static final String SELECT_COOPERMESSAGEV_SQL2T8_0 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where SENDERCODE=? and SOURCECODE like 'team_%' and CHECKDATE is null";
	private static final String SELECT_COOPERMESSAGEV_SQL2P8_0 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where SENDERCODE=? and SOURCECODE like 'project_%' and CHECKDATE is null";
	private static final String SELECT_COOPERMESSAGEV_SQL268_0 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where SENDERCODE=? and SOURCECODE=? and CHECKDATE is null";
	private static final String SELECT_COOPERMESSAGEV_SQL238_0 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where SENDERCODE=? or RECEIVERCODE=? and CHECKDATE is null";
	private static final String SELECT_COOPERMESSAGEV_SQL23T8_0 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where SENDERCODE=? or RECEIVERCODE=? SOURCECODE like 'team_%' and CHECKDATE is null";
	private static final String SELECT_COOPERMESSAGEV_SQL2368_0 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where (SENDERCODE=? or RECEIVERCODE=?) and SOURCECODE=? and CHECKDATE is null";
	private static final String SELECT_COOPERMESSAGEV_SQL38_0 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where RECEIVERCODE=? and CHECKDATE is null";
	private static final String SELECT_COOPERMESSAGEV_SQL3T8_0 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where RECEIVERCODE=? and SOURCECODE like 'team_%' and CHECKDATE is null";
	private static final String SELECT_COOPERMESSAGEV_SQL3P8_0 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where RECEIVERCODE=? and SOURCECODE like 'project_%' and CHECKDATE is null";
	private static final String SELECT_COOPERMESSAGEV_SQL368_0 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where RECEIVERCODE=? and SOURCECODE=? and CHECKDATE is null";
	private static final String SELECT_COOPERMESSAGEV_SQL48_0 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where SENDDATE=to_timeStamp(?,'yyyy-mm-dd hh24:mi:ss.ff3')  and CHECKDATE is null";
	private static final String SELECT_COOPERMESSAGEV_SQL58_0 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where TYPE=? and CHECKDATE is null";
	private static final String SELECT_COOPERMESSAGEV_SQL68_0 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where SOURCECODE=? and CHECKDATE is null";
	private static final String SELECT_COOPERMESSAGEV_SQL78_0 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where ALARMCODE=? and CHECKDATE is null";
	private static final String SELECT_COOPERMESSAGEV_SQL8_0 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where CHECKDATE is null";
	
	private static final String SELECT_COOPERMESSAGEV_SQL18_1 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where MESSAGECODE=? and CHECKDATE is not null";
	private static final String SELECT_COOPERMESSAGEV_SQL1238_1 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where MESSAGECODE=? and (SENDERCODE=? or RECEIVERCODE=?) and CHECKDATE is not null";
	private static final String SELECT_COOPERMESSAGEV_SQL28_1 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where SENDERCODE=? and CHECKDATE is not null";
	private static final String SELECT_COOPERMESSAGEV_SQL2T8_1 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where SENDERCODE=? and SOURCECODE like 'team_%' and CHECKDATE is not null";
	private static final String SELECT_COOPERMESSAGEV_SQL2P8_1 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where SENDERCODE=? and SOURCECODE like 'project_%' and CHECKDATE is not null";
	private static final String SELECT_COOPERMESSAGEV_SQL268_1 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where SENDERCODE=? and SOURCECODE=? and CHECKDATE is not null";
	private static final String SELECT_COOPERMESSAGEV_SQL238_1 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where SENDERCODE=? or RECEIVERCODE=? and CHECKDATE is not null";
	private static final String SELECT_COOPERMESSAGEV_SQL23T8_1 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where SENDERCODE=? or RECEIVERCODE=? SOURCECODE like 'team_%' and CHECKDATE is not null";
	private static final String SELECT_COOPERMESSAGEV_SQL2368_1 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where (SENDERCODE=? or RECEIVERCODE=?) and SOURCECODE=? and CHECKDATE is not null";
	private static final String SELECT_COOPERMESSAGEV_SQL38_1 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where RECEIVERCODE=? and CHECKDATE is not null";
	private static final String SELECT_COOPERMESSAGEV_SQL3T8_1 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where RECEIVERCODE=? and SOURCECODE like 'team_%' and CHECKDATE is not null";
	private static final String SELECT_COOPERMESSAGEV_SQL3P8_1 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where RECEIVERCODE=? and SOURCECODE like 'project_%' and CHECKDATE is not null";
	private static final String SELECT_COOPERMESSAGEV_SQL368_1 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where RECEIVERCODE=? and SOURCECODE=? and CHECKDATE is not null";
	private static final String SELECT_COOPERMESSAGEV_SQL48_1 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where SENDDATE=to_timeStamp(?,'yyyy-mm-dd hh24:mi:ss.ff3')  and CHECKDATE is not null";
	private static final String SELECT_COOPERMESSAGEV_SQL58_1 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where TYPE=? and CHECKDATE is not null";
	private static final String SELECT_COOPERMESSAGEV_SQL68_1 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where SOURCECODE=? and CHECKDATE is not null";
	private static final String SELECT_COOPERMESSAGEV_SQL78_1 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where ALARMCODE=? and CHECKDATE is not null";
	private static final String SELECT_COOPERMESSAGEV_SQL8_1 = "SELECT DISTINCT MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TYPE,SOURCECODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE from COOPERATORMESSAGEALARM_VIEW where CHECKDATE is not null";
	

	public CooperatorMessageAlarmVDTO searchMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		CooperatorMessageAlarmVDTO dto  = new CooperatorMessageAlarmVDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL1);
			pStatement.setString(1,messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public CooperatorMessageAlarmVDTO searchMessageCode(String messageCode,String senderCode, String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		CooperatorMessageAlarmVDTO dto = new CooperatorMessageAlarmVDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL123);
			pStatement.setString(1,messageCode);
			pStatement.setString(2,senderCode);
			pStatement.setString(3,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CooperatorMessageAlarmVDTO> searchSenderCode(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL2);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CooperatorMessageAlarmVDTO> searchSenderCodeTeam(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL2T);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CooperatorMessageAlarmVDTO> searchSenderCodePro(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL2P);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CooperatorMessageAlarmVDTO> searchSenderCode(String senderCode,String sourceCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL26);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,sourceCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CooperatorMessageAlarmVDTO> searchSendRec(String senderCode, String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL23);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CooperatorMessageAlarmVDTO> searchSendRecTeam(String senderCode, String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL23T);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CooperatorMessageAlarmVDTO> searchSendRec(String senderCode, String receiverCode,String sourceCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL236);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,sourceCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<CooperatorMessageAlarmVDTO> searchReceiverCode(String reciverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL3);
			pStatement.setString(1,reciverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	public List<CooperatorMessageAlarmVDTO> searchReceiverCodeTeam(String reciverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL3T);
			pStatement.setString(1,reciverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CooperatorMessageAlarmVDTO> searchReceiverCodePro(String reciverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL3P);
			pStatement.setString(1,reciverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CooperatorMessageAlarmVDTO> searchReceiverCode(String reciverCode,String sourceCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL36);
			pStatement.setString(1,reciverCode);
			pStatement.setString(2,sourceCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CooperatorMessageAlarmVDTO> searchSendDate(String sendDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL4);
			pStatement.setString(1,sendDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	public List<CooperatorMessageAlarmVDTO> searchType(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL5);
			pStatement.setInt(1, type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CooperatorMessageAlarmVDTO> searchSourceCode(String sourceCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL6);
			pStatement.setString(1,sourceCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CooperatorMessageAlarmVDTO> searchAlarmCode(String alarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL7);
			pStatement.setString(1,alarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CooperatorMessageAlarmVDTO> searchCheckDate(String checkDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL8);
			pStatement.setString(1,checkDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	
//////////////////////////////
	
	public CooperatorMessageAlarmVDTO searchUnReadMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		CooperatorMessageAlarmVDTO dto  = new CooperatorMessageAlarmVDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL18_0);
			pStatement.setString(1,messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public CooperatorMessageAlarmVDTO searchUnReadMessageCode(String messageCode,String senderCode, String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		CooperatorMessageAlarmVDTO dto = new CooperatorMessageAlarmVDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL1238_0);
			pStatement.setString(1,messageCode);
			pStatement.setString(2,senderCode);
			pStatement.setString(3,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CooperatorMessageAlarmVDTO> searchUnReadSenderCode(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL28_0);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CooperatorMessageAlarmVDTO> searchUnReadSenderCode(String senderCode,List<String> sourceCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		int size = sourceCodeList.size();
		String sql = SELECT_COOPERMESSAGEV_SQL28_0+" and SOURCECODE in ( ";
		
		for(int i=0;i<size-1;i++){
			sql+="'"+sourceCodeList.get(i)+"', ";
		}sql+="'"+sourceCodeList.get(sourceCodeList.size()-1)+"' )";
		
		System.out.println(sql);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CooperatorMessageAlarmVDTO> searchUnReadSenderCodeTeam(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL2T8_0);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CooperatorMessageAlarmVDTO> searchUnReadSenderCodePro(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL2P8_0);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CooperatorMessageAlarmVDTO> searchUnReadSenderCode(String senderCode,String sourceCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL268_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,sourceCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CooperatorMessageAlarmVDTO> searchUnReadSendRec(String senderCode, String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL238_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CooperatorMessageAlarmVDTO> searchUnReadSendRec(String senderCode,String receiverCode,List<String> sourceCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		int size = sourceCodeList.size();
		String sql = SELECT_COOPERMESSAGEV_SQL238_0+"and SOURCECODE in ( ";
		
		for(int i=0;i<size-1;i++){
			sql+="'"+sourceCodeList.get(i)+"', ";
		}sql+="'"+sourceCodeList.get(sourceCodeList.size()-1)+"' )";
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CooperatorMessageAlarmVDTO> searchUnReadSendRecTeam(String senderCode, String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL23T8_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CooperatorMessageAlarmVDTO> searchUnReadSendRec(String senderCode, String receiverCode,String sourceCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL2368_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,sourceCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<CooperatorMessageAlarmVDTO> searchUnReadReceiverCode(String reciverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL38_0);
			pStatement.setString(1,reciverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CooperatorMessageAlarmVDTO> searchUnReadReceiverCodeTeam(String reciverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL3T8_0);
			pStatement.setString(1,reciverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CooperatorMessageAlarmVDTO> searchUnReadReceiverCodePro(String reciverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL3P8_0);
			pStatement.setString(1,reciverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CooperatorMessageAlarmVDTO> searchUnReadReceiverCode(String reciverCode,String sourceCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL368_0);
			pStatement.setString(1,reciverCode);
			pStatement.setString(2,sourceCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CooperatorMessageAlarmVDTO> searchUnReadReceiverCode(String reciverCode,List<String> sourceCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		int size = sourceCodeList.size();
		String sql = SELECT_COOPERMESSAGEV_SQL38_0+"and SOURCECODE in ( ";
		
		for(int i=0;i<size-1;i++){
			sql+="'"+sourceCodeList.get(i)+"', ";
		}sql+="'"+sourceCodeList.get(sourceCodeList.size()-1)+"' )";
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,reciverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	
	public List<CooperatorMessageAlarmVDTO> searchUnReadSendDate(String sendDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL48_0);
			pStatement.setString(1,sendDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	public List<CooperatorMessageAlarmVDTO> searchUnReadType(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL58_0);
			pStatement.setInt(1, type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CooperatorMessageAlarmVDTO> searchUnReadSourceCode(String sourceCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL68_0);
			pStatement.setString(1,sourceCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CooperatorMessageAlarmVDTO> searchUnReadAlarmCode(String alarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL78_0);
			pStatement.setString(1,alarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CooperatorMessageAlarmVDTO> searchUnReadCheckDate(String checkDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL8_0);
			pStatement.setString(1,checkDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
//////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	public CooperatorMessageAlarmVDTO searchReadMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		CooperatorMessageAlarmVDTO dto  = new CooperatorMessageAlarmVDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL18_1);
			pStatement.setString(1,messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public CooperatorMessageAlarmVDTO searchReadMessageCode(String messageCode,String senderCode, String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		CooperatorMessageAlarmVDTO dto = new CooperatorMessageAlarmVDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL1238_1);
			pStatement.setString(1,messageCode);
			pStatement.setString(2,senderCode);
			pStatement.setString(3,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CooperatorMessageAlarmVDTO> searchReadSenderCode(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL28_1);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CooperatorMessageAlarmVDTO> searchReadSenderCode(String senderCode,List<String> sourceCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		int size = sourceCodeList.size();
		String sql = SELECT_COOPERMESSAGEV_SQL28_0+"and SOURCECODE in ( ";
		
		for(int i=0;i<size-1;i++){
			sql+="'"+sourceCodeList.get(i)+"', ";
		}sql+="'"+sourceCodeList.get(sourceCodeList.size()-1)+"' )";
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CooperatorMessageAlarmVDTO> searchReadSenderCodeTeam(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL2T8_1);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	public List<CooperatorMessageAlarmVDTO> searchReadSenderCodePro(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL2P8_1);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CooperatorMessageAlarmVDTO> searchReadSenderCode(String senderCode,String sourceCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL268_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,sourceCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CooperatorMessageAlarmVDTO> searchReadSendRec(String senderCode, String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL238_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CooperatorMessageAlarmVDTO> searchReadSendRec(String senderCode,String receiverCode,List<String> sourceCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		int size = sourceCodeList.size();
		String sql = SELECT_COOPERMESSAGEV_SQL238_1+"and SOURCECODE in ( ";
		
		for(int i=0;i<size-1;i++){
			sql+="'"+sourceCodeList.get(i)+"', ";
		}sql+="'"+sourceCodeList.get(sourceCodeList.size()-1)+"' )";
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	

	public List<CooperatorMessageAlarmVDTO> searchReadSendRecTeam(String senderCode, String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL23T8_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<CooperatorMessageAlarmVDTO> searchReadSendRec(String senderCode, String receiverCode,String sourceCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL2368_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,sourceCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<CooperatorMessageAlarmVDTO> searchReadReceiverCode(String reciverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL38_1);
			pStatement.setString(1,reciverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CooperatorMessageAlarmVDTO> searchReadReceiverCode(String reciverCode,List<String> sourceCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		int size = sourceCodeList.size();
		String sql = SELECT_COOPERMESSAGEV_SQL38_1+"and SOURCECODE in ( ";
		
		for(int i=0;i<size-1;i++){
			sql+="'"+sourceCodeList.get(i)+"', ";
		}sql+="'"+sourceCodeList.get(sourceCodeList.size()-1)+"' )";
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,reciverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<CooperatorMessageAlarmVDTO> searchReadReceiverCodeTeam(String reciverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL3T8_1);
			pStatement.setString(1,reciverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CooperatorMessageAlarmVDTO> searchReadReceiverCodePro(String reciverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL3P8_1);
			pStatement.setString(1,reciverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	
	public List<CooperatorMessageAlarmVDTO> searchReadReceiverCode(String reciverCode,String sourceCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL368_1);
			pStatement.setString(1,reciverCode);
			pStatement.setString(2,sourceCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CooperatorMessageAlarmVDTO> searchReadSendDate(String sendDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL48_1);
			pStatement.setString(1,sendDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	public List<CooperatorMessageAlarmVDTO> searchReadType(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL58_1);
			pStatement.setInt(1, type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CooperatorMessageAlarmVDTO> searchReadSourceCode(String sourceCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL68_1);
			pStatement.setString(1,sourceCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CooperatorMessageAlarmVDTO> searchReadAlarmCode(String alarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL78_1);
			pStatement.setString(1,alarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CooperatorMessageAlarmVDTO> searchReadCheckDate(String checkDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageAlarmVDTO> dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERMESSAGEV_SQL8_1);
			pStatement.setString(1,checkDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	
	private List<CooperatorMessageAlarmVDTO> makeDTOList(ResultSet rs, List<CooperatorMessageAlarmVDTO> dtoList) throws SQLException{
		
		if(dtoList==null){dtoList = new ArrayList<CooperatorMessageAlarmVDTO>();}
		while(rs.next())
		{
			String alarmCode = rs.getString("MESSAGECODE");
			if(rs.wasNull()){
				return null;
			}
			dtoList.add(new CooperatorMessageAlarmVDTO(alarmCode, rs.getString("SENDERCODE"), rs.getString("RECEIVERCODE"), rs.getString("TITLE"), rs.getString("CONTENT"), rs.getString("SENDDATE"), rs.getInt("TYPE"), rs.getString("SOURCECODE"),  rs.getString("ALARMCODE"),  rs.getString("CHECKDATE")));
		}
		return dtoList;
	}
	
	private CooperatorMessageAlarmVDTO makeDTO(ResultSet rs, CooperatorMessageAlarmVDTO dto) throws SQLException{
		
		System.out.println("makeDTO...");
		dto=null;
		while(rs.next())
		{
			String alarmCode = rs.getString("MESSAGECODE");
			if(rs.wasNull()){
				return null;
			}
			dto = new CooperatorMessageAlarmVDTO(alarmCode, rs.getString("SENDERCODE"), rs.getString("RECEIVERCODE"), rs.getString("TITLE"), rs.getString("CONTENT"), rs.getString("SENDDATE"), rs.getInt("TYPE"), rs.getString("SOURCECODE"),  rs.getString("ALARMCODE"),  rs.getString("CHECKDATE"));
		}
		return dto;
	}
}
