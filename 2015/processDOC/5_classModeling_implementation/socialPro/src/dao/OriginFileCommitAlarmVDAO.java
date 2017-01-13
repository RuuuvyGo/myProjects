package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import socialProExceptions.DAOException;
import dto.OriginFileCommitAlarmVDTO;
public class OriginFileCommitAlarmVDAO extends BaseDAO{

	private static final String SELECT_ORIFILECOMMITALARMV_SQL1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where MESSAGECODE=?";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL2 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where ALARMCODE=?";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL3 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where COMMITCODE=?";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL4 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where SENDERCODE=?";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL45O = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where SENDERCODE=? or RECEIVERCODE=?";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL45A = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where SENDERCODE=? and RECEIVERCODE=?";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL45O8 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where SENDERCODE=? or RECEIVERCODE=? and ORIGINFILECODE=?";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL45A8 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where SENDERCODE=? and RECEIVERCODE=? and ORIGINFILECODE=?";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL48 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where SENDERCODE=? and ORIGINFILECODE=?";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL5 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where RECEIVERCODE=?";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL58 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where RECEIVERCODE=? and ORIGINFILECODE=?";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL6 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where TYPE=?";	
	private static final String SELECT_ORIFILECOMMITALARMV_SQL8 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where ORIGINFILECODE=?";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL9 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where CHECKDATE=to_timeStamp(?,'yyyy-mm-dd hh24:mi:ss.ff3')";	
	

	private static final String SELECT_ORIFILECOMMITALARMV_SQL19_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where MESSAGECODE=? and CHECKDATE is null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL29_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where ALARMCODE=? and CHECKDATE is null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL39_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where COMMITCODE=? and CHECKDATE is null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL49_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where SENDERCODE=? and CHECKDATE is null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL45O9_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where (SENDERCODE=? or RECEIVERCODE=?) and CHECKDATE is null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL45A9_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where SENDERCODE=? and RECEIVERCODE=? and CHECKDATE is null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL45O89_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where SENDERCODE=? or RECEIVERCODE=? and ORIGINFILECODE=? and CHECKDATE is null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL45A89_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where SENDERCODE=? and RECEIVERCODE=? and ORIGINFILECODE=? and CHECKDATE is null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL489_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where SENDERCODE=? and ORIGINFILECODE=? and CHECKDATE is null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL59_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where RECEIVERCODE=? and CHECKDATE is null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL589_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where RECEIVERCODE=? and ORIGINFILECODE=? and CHECKDATE is null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL69_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where TYPE=? and CHECKDATE is null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL89_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where ORIGINFILECODE=? and CHECKDATE is null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL9_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where CHECKDATE is null";
	

	private static final String SELECT_ORIFILECOMMITALARMV_SQL19_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where MESSAGECODE=? and CHECKDATE is not null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL29_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where ALARMCODE=? and CHECKDATE is not null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL39_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where COMMITCODE=? and CHECKDATE is not null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL49_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where SENDERCODE=? and CHECKDATE is not null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL45O9_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where (SENDERCODE=? or RECEIVERCODE=?) and CHECKDATE is not null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL45A9_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where SENDERCODE=? and RECEIVERCODE=? and CHECKDATE is not null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL45O89_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where SENDERCODE=? or RECEIVERCODE=? and ORIGINFILECODE=? and CHECKDATE is not null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL45A89_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where SENDERCODE=? and RECEIVERCODE=? and ORIGINFILECODE=? and CHECKDATE is not null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL489_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where SENDERCODE=? and ORIGINFILECODE=? and CHECKDATE is not null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL59_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where RECEIVERCODE=? and CHECKDATE is not null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL589_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where RECEIVERCODE=? and ORIGINFILECODE=? and CHECKDATE is not null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL69_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where TYPE=? and CHECKDATE is not null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL89_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where ORIGINFILECODE=? and CHECKDATE is not null";
	private static final String SELECT_ORIFILECOMMITALARMV_SQL9_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate From OriginFileCommitAlarm_view where CHECKDATE is not null";
	
	
	public List<OriginFileCommitAlarmVDTO> searchMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL1);
			pStatement.setString(1,messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginFileCommitAlarmVDTO> searchGroupAlarmCode(String groupAlarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL2);
			pStatement.setString(1, groupAlarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public OriginFileCommitAlarmVDTO searchCommitCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		OriginFileCommitAlarmVDTO dto = new OriginFileCommitAlarmVDTO();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL3);
			pStatement.setString(1,commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFileCommitAlarmVDTO> searchSenderCode(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL4);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFileCommitAlarmVDTO> searchSendOrRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL45O);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginFileCommitAlarmVDTO> searchSendAndRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL45A);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<OriginFileCommitAlarmVDTO> searchSendOrRecOr(String senderCode,String receiverCode,String oriFile) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL45O8);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,oriFile);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginFileCommitAlarmVDTO> searchSendAndRecOr(String senderCode,String receiverCode,String oriFile) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;	
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL45A8);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,oriFile);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFileCommitAlarmVDTO> searchSendOrRecOr(String senderCode,String receiverCode,List<String> oriFileList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;
		int size = oriFileList.size();
		String sql = SELECT_ORIFILECOMMITALARMV_SQL45O+" and ORIGINFILECODE in ( ";
		
		for(int i=0;i<size-1;i++){
			sql+="'"+oriFileList.get(i)+"', ";
		}sql+="'"+oriFileList.get(size-1)+"' )";
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginFileCommitAlarmVDTO> searchSendAndRecOr(String senderCode,String receiverCode,List<String> oriFileList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;	
		int size = oriFileList.size();
		String sql = SELECT_ORIFILECOMMITALARMV_SQL45A+" and ORIGINFILECODE in ( ";
		
		for(int i=0;i<size-1;i++){
			sql+="'"+oriFileList.get(i)+"', ";
		}sql+="'"+oriFileList.get(size-1)+"' )";
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
/*
	public List<OriginFileCommitAlarmVDTO> searchSendOrRecCp(String senderCode,String receiverCode,List<String> copiedFileList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;

		String sql = SELECT_ORIFILECOMMITALARMV_SQL45O+" and COPIEDFILECODE in ( ";
		
		for(int i=0;i<copiedFileList.size()-1;i++){
			sql+="'"+copiedFileList.get(i)+"', ";
		}sql+="'"+copiedFileList.get(copiedFileList.size()-1)+"' )";
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}*/
	
	/*public List<OriginFileCommitAlarmVDTO> searchSendAndRecCp(String senderCode,String receiverCode,List<String> copiedFileList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;	
		
		String sql = SELECT_ORIFILECOMMITALARMV_SQL45A+" and COPIEDFILECODE in ( ";
		
		for(int i=0;i<copiedFileList.size()-1;i++){
			sql+="'"+copiedFileList.get(i)+"', ";
		}sql+="'"+copiedFileList.get(copiedFileList.size()-1)+"' )";
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}*/

	public List<OriginFileCommitAlarmVDTO> searchSenderCodeOr(String senderCode,String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL48);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFileCommitAlarmVDTO> searchReceiverCode(String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL5);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFileCommitAlarmVDTO> searchReceiverCodeOr(String receiverCode,String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL58);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFileCommitAlarmVDTO> searchSendDate(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL6);
			pStatement.setInt(1, type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginFileCommitAlarmVDTO> searchOriginFileCode(String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL8);
			pStatement.setString(1, originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<OriginFileCommitAlarmVDTO> searchCheckDate(String checkDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL9);
			pStatement.setString(1, checkDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
/////////////////////////////////////////	
	

	public List<OriginFileCommitAlarmVDTO> searchUnReadMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL19_0);
			pStatement.setString(1,messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginFileCommitAlarmVDTO> searchUnReadGroupAlarmCode(String groupAlarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL29_0);
			pStatement.setString(1, groupAlarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public OriginFileCommitAlarmVDTO searchUnReadCommitCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		OriginFileCommitAlarmVDTO dto = new OriginFileCommitAlarmVDTO();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL39_0);
			pStatement.setString(1,commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFileCommitAlarmVDTO> searchUnReadSenderCode(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL49_0);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFileCommitAlarmVDTO> searchUnReadSendOrRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL45O9_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFileCommitAlarmVDTO> searchUnReadSendAndRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL45A9_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFileCommitAlarmVDTO> searchUnReadSendOrRecOr(String senderCode,String receiverCode,String oriFile) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL45O89_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,oriFile);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginFileCommitAlarmVDTO> searchUnReadSendAndRecOr(String senderCode,String receiverCode,String oriFile) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;	
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL45A89_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,oriFile);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginFileCommitAlarmVDTO> searchUnReadSendOrRecOr(String senderCode,String receiverCode,List<String> oriFileList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;
		int size = oriFileList.size();
		String sql = SELECT_ORIFILECOMMITALARMV_SQL45O9_0+" and ORIGINFILECODE in ( ";
		
		for(int i=0;i<size-1;i++){
			sql+="'"+oriFileList.get(i)+"', ";
		}sql+="'"+oriFileList.get(size-1)+"' )";
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginFileCommitAlarmVDTO> searchUnReadSendAndRecOr(String senderCode,String receiverCode,List<String> oriFileList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;	
		int size = oriFileList.size();
		String sql = SELECT_ORIFILECOMMITALARMV_SQL45A9_0+" and ORIGINFILECODE in ( ";
		
		for(int i=0;i<size-1;i++){
			sql+="'"+oriFileList.get(i)+"', ";
		}sql+="'"+oriFileList.get(size-1)+"' )";
		System.out.println(sql);
		System.out.println("senderCode :  "+senderCode+"    receiverCode  : "+receiverCode);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	/*public List<OriginFileCommitAlarmVDTO> searchUnReadSendOrRecCp(String senderCode,String receiverCode,List<String> copiedFileList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;

		String sql = SELECT_ORIFILECOMMITALARMV_SQL45O9_0+" and COPIEDFILECODE in ( ";
		
		for(int i=0;i<copiedFileList.size()-1;i++){
			sql+="'"+copiedFileList.get(i)+"', ";
		}sql+="'"+copiedFileList.get(copiedFileList.size()-1)+"' )";
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}*/
	
	public List<OriginFileCommitAlarmVDTO> searchUnReadSendAndRecCp(String senderCode,String receiverCode,List<String> copiedFileList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;	
		
		String sql = SELECT_ORIFILECOMMITALARMV_SQL45A9_0+" and COPIEDFILECODE in ( ";
		
		for(int i=0;i<copiedFileList.size()-1;i++){
			sql+="'"+copiedFileList.get(i)+"', ";
		}sql+="'"+copiedFileList.get(copiedFileList.size()-1)+"' )";
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginFileCommitAlarmVDTO> searchUnReadSenderCodeOr(String senderCode,String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL489_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFileCommitAlarmVDTO> searchUnReadSenderCodeOr(String senderCode,List<String> originFileCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;	

		String sql = SELECT_ORIFILECOMMITALARMV_SQL59_0+" and originFileCode in ( ";
		
		for(int i=0;i<originFileCodeList.size()-1;i++){
			sql+="'"+originFileCodeList.get(i)+"', ";
		}sql+="'"+originFileCodeList.get(originFileCodeList.size()-1)+"' )";
		System.out.println(sql);
		System.out.println("senderCode  :  "+senderCode);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	public List<OriginFileCommitAlarmVDTO> searchUnReadReceiverCode(String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL59_0);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	/*public List<OriginFileCommitAlarmVDTO> searchUnReadReceiverCodeCp(String receiverCode,List<String> copiedFileCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;

		String sql = SELECT_ORIFILECOMMITALARMV_SQL59_0+" and copiedFileCode in ( ";
		
		for(int i=0;i<copiedFileCodeList.size()-1;i++){
			sql+="'"+copiedFileCodeList.get(i)+"', ";
		}sql+="'"+copiedFileCodeList.get(copiedFileCodeList.size()-1)+"' )";
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}*/
	public List<OriginFileCommitAlarmVDTO> searchUnReadReceiverCodeOr(String receiverCode,String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL589_0);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFileCommitAlarmVDTO> searchUnReadReceiverCodeOr(String receiverCode,List<String> originFileCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;

		String sql = SELECT_ORIFILECOMMITALARMV_SQL59_0+" and originFileCode in ( ";
		
		for(int i=0;i<originFileCodeList.size()-1;i++){
			sql+="'"+originFileCodeList.get(i)+"', ";
		}sql+="'"+originFileCodeList.get(originFileCodeList.size()-1)+"' )";
		System.out.println(sql);
		System.out.println("reCeiver : "+receiverCode);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<OriginFileCommitAlarmVDTO> searchUnReadSendDate(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL69_0);
			pStatement.setInt(1, type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFileCommitAlarmVDTO> searchUnReadOriginFileCode(String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL89_0);
			pStatement.setString(1, originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginFileCommitAlarmVDTO> searchUnReadOriginFileCode(List<String> originFileCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		
		int size = originFileCodeList.size();
		String sql = SELECT_ORIFILECOMMITALARMV_SQL9_0+" and ORIGINFILECODE in ( ";
		
		for(int i=0;i<size-1;i++){
			sql+="'"+originFileCodeList.get(i)+"', ";
		}sql+="'"+originFileCodeList.get(size-1)+"' )";
		
		System.out.println(sql);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<OriginFileCommitAlarmVDTO> searchUnReadCheckDate(String checkDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL9_0);
			pStatement.setString(1, checkDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

////////////////////////////////////////////////
	

	public List<OriginFileCommitAlarmVDTO> searchReadMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL19_1);
			pStatement.setString(1,messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginFileCommitAlarmVDTO> searchReadGroupAlarmCode(String groupAlarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL29_1);
			pStatement.setString(1, groupAlarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public OriginFileCommitAlarmVDTO searchReadCommitCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		OriginFileCommitAlarmVDTO dto = new OriginFileCommitAlarmVDTO();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL39_1);
			pStatement.setString(1,commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFileCommitAlarmVDTO> searchReadSenderCode(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL49_1);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFileCommitAlarmVDTO> searchReadSendOrRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL45O9_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFileCommitAlarmVDTO> searchReadSendAndRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL45A9_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFileCommitAlarmVDTO> searchReadSendOrRecOr(String senderCode,String receiverCode,String oriFile) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL45O89_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,oriFile);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginFileCommitAlarmVDTO> searchReadSendAndRecOr(String senderCode,String receiverCode,String oriFile) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;	
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL45A89_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,oriFile);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFileCommitAlarmVDTO> searchReadSendOrRecOr(String senderCode,String receiverCode,List<String> oriFileList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;
		int size = oriFileList.size();
		String sql = SELECT_ORIFILECOMMITALARMV_SQL45O9_1+" and ORIGINFILECODE in ( ";
		
		for(int i=0;i<size-1;i++){
			sql+="'"+oriFileList.get(i)+"', ";
		}sql+="'"+oriFileList.get(size-1)+"' )";
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginFileCommitAlarmVDTO> searchReadSendAndRecOr(String senderCode,String receiverCode,List<String> oriFileList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;	
		int size = oriFileList.size();
		String sql = SELECT_ORIFILECOMMITALARMV_SQL45A9_1+" and ORIGINFILECODE in ( ";
		
		for(int i=0;i<size-1;i++){
			sql+="'"+oriFileList.get(i)+"', ";
		}sql+="'"+oriFileList.get(size-1)+"' )";
		System.out.println(sql);
		System.out.println("senderCode : "+senderCode+"  receiverCode  : "+receiverCode);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
/*
	public List<OriginFileCommitAlarmVDTO> searchReadSendOrRecCp(String senderCode,String receiverCode,List<String> copiedFileList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;

		String sql = SELECT_ORIFILECOMMITALARMV_SQL45O9_1+" and COPIEDFILECODE in ( ";
		
		for(int i=0;i<copiedFileList.size()-1;i++){
			sql+="'"+copiedFileList.get(i)+"', ";
		}sql+="'"+copiedFileList.get(copiedFileList.size()-1)+"' )";
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}*/
	
	/*public List<OriginFileCommitAlarmVDTO> searchReadSendAndRecCp(String senderCode,String receiverCode,List<String> copiedFileList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;	
		
		String sql = SELECT_ORIFILECOMMITALARMV_SQL45A9_1+" and COPIEDFILECODE in ( ";
		
		for(int i=0;i<copiedFileList.size()-1;i++){
			sql+="'"+copiedFileList.get(i)+"', ";
		}sql+="'"+copiedFileList.get(copiedFileList.size()-1)+"' )";
		System.out.println(sql);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}*/
/*
	public List<OriginFileCommitAlarmVDTO> searchReadSenderCodeCp(String senderCode,List<String> copiedFileCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		
		String sql = SELECT_ORIFILECOMMITALARMV_SQL59_1+" and copiedFileCode in ( ";

		for(int i=0;i<copiedFileCodeList.size()-1;i++){
			sql+="'"+copiedFileCodeList.get(i)+"', ";
		}sql+="'"+copiedFileCodeList.get(copiedFileCodeList.size()-1)+"' )";
		System.out.println(sql);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}*/
	
	public List<OriginFileCommitAlarmVDTO> searchReadSenderCodeOr(String senderCode,String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL489_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFileCommitAlarmVDTO> searchReadSenderCodeOr(String senderCode,List<String> oriFileCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		

		String sql = SELECT_ORIFILECOMMITALARMV_SQL59_1+" and originFileCode in ( ";

		for(int i=0;i<oriFileCodeList.size()-1;i++){
			sql+="'"+oriFileCodeList.get(i)+"', ";
		}sql+="'"+oriFileCodeList.get(oriFileCodeList.size()-1)+"' )";
		System.out.println(sql);
		System.out.println("senderCode   :  "+senderCode);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	public List<OriginFileCommitAlarmVDTO> searchReadReceiverCode(String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL59_1);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	/*public List<OriginFileCommitAlarmVDTO> searchReadReceiverCodeCp(String receiverCode,List<String> copiedFileCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;

		String sql = SELECT_ORIFILECOMMITALARMV_SQL59_1+" and CopiedFileCode in ( ";
		
		for(int i=0;i<copiedFileCodeList.size()-1;i++){
			sql+="'"+copiedFileCodeList.get(i)+"', ";
		}sql+="'"+copiedFileCodeList.get(copiedFileCodeList.size()-1)+"' )";
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}*/
	
	public List<OriginFileCommitAlarmVDTO> searchReadReceiverCodeOr(String receiverCode,String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL589_1);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<OriginFileCommitAlarmVDTO> searchReadReceiverCodeOr(String receiverCode,List<String> originFileCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;

		String sql = SELECT_ORIFILECOMMITALARMV_SQL59_1+" and originFileCode in ( ";
		
		for(int i=0;i<originFileCodeList.size()-1;i++){
			sql+="'"+originFileCodeList.get(i)+"', ";
		}sql+="'"+originFileCodeList.get(originFileCodeList.size()-1)+"' )";
		System.out.println(sql);
		System.out.println("receiverCode  : "+receiverCode);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<OriginFileCommitAlarmVDTO> searchReadSendDate(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL69_1);
			pStatement.setInt(1, type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFileCommitAlarmVDTO> searchReadOriginFileCode(String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL89_1);
			pStatement.setString(1, originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<OriginFileCommitAlarmVDTO> searchReadCheckDate(String checkDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFILECOMMITALARMV_SQL9_1);
			pStatement.setString(1, checkDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<OriginFileCommitAlarmVDTO> searchReadOriginFileCode(List<String> originFileCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitAlarmVDTO> dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		
		int size = originFileCodeList.size();
		String sql = SELECT_ORIFILECOMMITALARMV_SQL9_1+" and ORIGINFILECODE in ( ";
		
		for(int i=0;i<size-1;i++){
			sql+="'"+originFileCodeList.get(i)+"', ";
		}sql+="'"+originFileCodeList.get(size-1)+"' )";
		
		System.out.println(sql);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	
	private List<OriginFileCommitAlarmVDTO> makeDTOList(ResultSet rs, List<OriginFileCommitAlarmVDTO> dtoList) throws SQLException{
		
		if(dtoList==null){dtoList = new ArrayList<OriginFileCommitAlarmVDTO>();}
		while(rs.next())
		{
			String messageCode = rs.getString("MESSAGECODE");
			if(rs.wasNull()){
				return dtoList;
			}
			dtoList.add(new OriginFileCommitAlarmVDTO(messageCode, rs.getString("alarmCode"), rs.getString("commitCode"), rs.getString("senderCode"), rs.getString("receiverCode"), rs.getInt("type"), rs.getString("originFileCode"), rs.getString("checkDate")));
		}
		return dtoList;
	}
	
	private OriginFileCommitAlarmVDTO makeDTO(ResultSet rs, OriginFileCommitAlarmVDTO dto) throws SQLException{
		
		System.out.println("makeDTO...");
		dto=null;
		while(rs.next())
		{
			String messageCode = rs.getString("MESSAGECODE");
			if(rs.wasNull()){
				return dto;
			}
			dto = new OriginFileCommitAlarmVDTO(messageCode, rs.getString("alarmCode"), rs.getString("commitCode"), rs.getString("senderCode"), rs.getString("receiverCode"), rs.getInt("type"), rs.getString("originFileCode"), rs.getString("checkDate"));
		}
		return dto;
	}
	
}
