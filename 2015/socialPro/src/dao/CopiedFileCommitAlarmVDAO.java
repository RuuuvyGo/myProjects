package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import socialProExceptions.DAOException;
import dto.CopiedFileCommitAlarmVDTO;
public class CopiedFileCommitAlarmVDAO extends BaseDAO{

	//messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFileCode, checkDate

	private static final String SELECT_CPFILECOMMITALARMV_SQL1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where MESSAGECODE=?";
	private static final String SELECT_CPFILECOMMITALARMV_SQL2 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where ALARMCODE=?";
	private static final String SELECT_CPFILECOMMITALARMV_SQL3 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where COMMITCODE=?";
	private static final String SELECT_CPFILECOMMITALARMV_SQL4 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=?";
	private static final String SELECT_CPFILECOMMITALARMV_SQL45O = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=? or RECEIVERCODE=?";
	private static final String SELECT_CPFILECOMMITALARMV_SQL45A = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=? and RECEIVERCODE=?";
	private static final String SELECT_CPFILECOMMITALARMV_SQL45O7 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=? or RECEIVERCODE=? and COPIEDFILECODE=?";
	private static final String SELECT_CPFILECOMMITALARMV_SQL45A7 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=? and RECEIVERCODE=? and COPIEDFILECODE=?";
	private static final String SELECT_CPFILECOMMITALARMV_SQL45O8 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=? or RECEIVERCODE=? and ORIGINFILECODE=?";
	private static final String SELECT_CPFILECOMMITALARMV_SQL45A8 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=? and RECEIVERCODE=? and ORIGINFILECODE=?";
	private static final String SELECT_CPFILECOMMITALARMV_SQL47 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=? and COPIEDFILECODE=?";
	private static final String SELECT_CPFILECOMMITALARMV_SQL48 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=? and ORIGINFILECODE=?";
	private static final String SELECT_CPFILECOMMITALARMV_SQL5 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where RECEIVERCODE=?";
	private static final String SELECT_CPFILECOMMITALARMV_SQL57 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where RECEIVERCODE=? and COPIEDFILECODE=?";
	private static final String SELECT_CPFILECOMMITALARMV_SQL58 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where RECEIVERCODE=? and ORIGINFILECODE=?";
	private static final String SELECT_CPFILECOMMITALARMV_SQL6 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where TYPE=?";
	private static final String SELECT_CPFILECOMMITALARMV_SQL7 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where COPIEDFILECODE=?";
	private static final String SELECT_CPFILECOMMITALARMV_SQL8 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where ORIGINFILECODE=?";
	private static final String SELECT_CPFILECOMMITALARMV_SQL9 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where CHECKDATE=to_timeStamp(?,'yyyy-mm-dd hh24:mi:ss.ff3')";	
	

	private static final String SELECT_CPFILECOMMITALARMV_SQL19_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where MESSAGECODE=? and CHECKDATE is null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL29_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where ALARMCODE=? and CHECKDATE is null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL39_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where COMMITCODE=? and CHECKDATE is null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL49_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=? and CHECKDATE is null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL45O9_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where (SENDERCODE=? or RECEIVERCODE=?) and CHECKDATE is null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL45A9_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=? and RECEIVERCODE=? and CHECKDATE is null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL45O79_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=? or RECEIVERCODE=? and COPIEDFILECODE=? and CHECKDATE is null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL45A79_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=? and RECEIVERCODE=? and COPIEDFILECODE=? and CHECKDATE is null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL45O89_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=? or RECEIVERCODE=? and ORIGINFILECODE=? and CHECKDATE is null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL45A89_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=? and RECEIVERCODE=? and ORIGINFILECODE=? and CHECKDATE is null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL479_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=? and COPIEDFILECODE=? and CHECKDATE is null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL489_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=? and ORIGINFILECODE=? and CHECKDATE is null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL59_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where RECEIVERCODE=? and CHECKDATE is null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL579_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where RECEIVERCODE=? and COPIEDFILECODE=? and CHECKDATE is null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL589_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where RECEIVERCODE=? and ORIGINFILECODE=? and CHECKDATE is null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL69_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where TYPE=? and CHECKDATE is null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL79_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where COPIEDFILECODE=? and CHECKDATE is null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL89_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where ORIGINFILECODE=? and CHECKDATE is null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL9_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where CHECKDATE is null";
	

	private static final String SELECT_CPFILECOMMITALARMV_SQL19_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where MESSAGECODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL29_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where ALARMCODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL39_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where COMMITCODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL49_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL45O9_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where (SENDERCODE=? or RECEIVERCODE=?) and CHECKDATE is not null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL45A9_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=? and RECEIVERCODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL45O79_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=? or RECEIVERCODE=? and COPIEDFILECODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL45A79_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=? and RECEIVERCODE=? and COPIEDFILECODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL45O89_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=? or RECEIVERCODE=? and ORIGINFILECODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL45A89_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=? and RECEIVERCODE=? and ORIGINFILECODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL479_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=? and COPIEDFILECODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL489_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where SENDERCODE=? and ORIGINFILECODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL59_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where RECEIVERCODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL579_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where RECEIVERCODE=? and COPIEDFILECODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL589_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where RECEIVERCODE=? and ORIGINFILECODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL69_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where TYPE=? and CHECKDATE is not null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL79_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where COPIEDFILECODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL89_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where ORIGINFILECODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFILECOMMITALARMV_SQL9_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFileCode,originFileCode, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM CopiedFILECOMMITALARM_VIEW where CHECKDATE is not null";
	
	
	public List<CopiedFileCommitAlarmVDTO> searchMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL1);
			pStatement.setString(1,messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileCommitAlarmVDTO> searchGroupAlarmCode(String groupAlarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL2);
			pStatement.setString(1, groupAlarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public CopiedFileCommitAlarmVDTO searchCommitCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		CopiedFileCommitAlarmVDTO dto = new CopiedFileCommitAlarmVDTO();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL3);
			pStatement.setString(1,commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchSenderCode(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL4);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchSendOrRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL45O);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileCommitAlarmVDTO> searchSendAndRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL45A);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CopiedFileCommitAlarmVDTO> searchSendOrRecOr(String senderCode,String receiverCode,String oriFile) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL45O8);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,oriFile);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileCommitAlarmVDTO> searchSendAndRecOr(String senderCode,String receiverCode,String oriFile) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;	
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL45A8);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,oriFile);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchSendOrRecCp(String senderCode,String receiverCode,String copiedFile) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL45O7);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,copiedFile);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileCommitAlarmVDTO> searchSendAndRecCp(String senderCode,String receiverCode,String copiedFile) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;	
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL45A7);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,copiedFile);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<CopiedFileCommitAlarmVDTO> searchSendOrRecOr(String senderCode,String receiverCode,List<String> oriFileList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;
		int size = oriFileList.size();
		String sql = SELECT_CPFILECOMMITALARMV_SQL45O+" and ORIGINFILECODE in ( ";
		
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
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileCommitAlarmVDTO> searchSendAndRecOr(String senderCode,String receiverCode,List<String> oriFileList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;	
		int size = oriFileList.size();
		String sql = SELECT_CPFILECOMMITALARMV_SQL45A+" and ORIGINFILECODE in ( ";
		
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
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchSendOrRecCp(String senderCode,String receiverCode,List<String> copiedFileList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;

		String sql = SELECT_CPFILECOMMITALARMV_SQL45O+" and COPIEDFILECODE in ( ";
		
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
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileCommitAlarmVDTO> searchSendAndRecCp(String senderCode,String receiverCode,List<String> copiedFileList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;	
		
		String sql = SELECT_CPFILECOMMITALARMV_SQL45A+" and COPIEDFILECODE in ( ";
		
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
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchSenderCodeCp(String senderCode,String copiedFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL47);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,copiedFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileCommitAlarmVDTO> searchSenderCodeOr(String senderCode,String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL48);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchReceiverCode(String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL5);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchReceiverCodeCp(String receiverCode,String copiedFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL57);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, copiedFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileCommitAlarmVDTO> searchReceiverCodeOr(String receiverCode,String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL58);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchSendDate(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL6);
			pStatement.setInt(1, type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchCopiedFileCode(String copiedFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL7);
			pStatement.setString(1, copiedFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}	

	public List<CopiedFileCommitAlarmVDTO> searchOriginFileCode(String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL8);
			pStatement.setString(1, originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CopiedFileCommitAlarmVDTO> searchCheckDate(String checkDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL9);
			pStatement.setString(1, checkDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
/////////////////////////////////////////	
	

	public List<CopiedFileCommitAlarmVDTO> searchUnReadMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL19_0);
			pStatement.setString(1,messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileCommitAlarmVDTO> searchUnReadGroupAlarmCode(String groupAlarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL29_0);
			pStatement.setString(1, groupAlarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public CopiedFileCommitAlarmVDTO searchUnReadCommitCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		CopiedFileCommitAlarmVDTO dto = new CopiedFileCommitAlarmVDTO();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL39_0);
			pStatement.setString(1,commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchUnReadSenderCode(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL49_0);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchUnReadSendOrRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL45O9_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchUnReadSendAndRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL45A9_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchUnReadSendOrRecOr(String senderCode,String receiverCode,String oriFile) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL45O89_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,oriFile);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileCommitAlarmVDTO> searchUnReadSendAndRecOr(String senderCode,String receiverCode,String oriFile) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;	
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL45A89_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,oriFile);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchUnReadSendOrRecCp(String senderCode,String receiverCode,String copiedFile) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL45O79_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,copiedFile);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileCommitAlarmVDTO> searchUnReadSendAndRecCp(String senderCode,String receiverCode,String copiedFile) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;	
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL45A79_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,copiedFile);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<CopiedFileCommitAlarmVDTO> searchUnReadSendOrRecOr(String senderCode,String receiverCode,List<String> oriFileList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;
		int size = oriFileList.size();
		String sql = SELECT_CPFILECOMMITALARMV_SQL45O9_0+" and ORIGINFILECODE in ( ";
		
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
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileCommitAlarmVDTO> searchUnReadSendAndRecOr(String senderCode,String receiverCode,List<String> oriFileList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;	
		int size = oriFileList.size();
		String sql = SELECT_CPFILECOMMITALARMV_SQL45A9_0+" and ORIGINFILECODE in ( ";
		
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
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchUnReadSendOrRecCp(String senderCode,String receiverCode,List<String> copiedFileList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;

		String sql = SELECT_CPFILECOMMITALARMV_SQL45O9_0+" and COPIEDFILECODE in ( ";
		
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
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileCommitAlarmVDTO> searchUnReadSendAndRecCp(String senderCode,String receiverCode,List<String> copiedFileList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;	
		
		String sql = SELECT_CPFILECOMMITALARMV_SQL45A9_0+" and COPIEDFILECODE in ( ";
		
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
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<CopiedFileCommitAlarmVDTO> searchUnReadSenderCodeCp(String senderCode,String copiedFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL479_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,copiedFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileCommitAlarmVDTO> searchUnReadSenderCodeCp(String senderCode,List<String> copiedFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		
		String sql = SELECT_CPFILECOMMITALARMV_SQL479_0+" ( ";
		
		for(int i=0;i<copiedFileCode.size()-1;i++){
			sql+="COPIEDFILECODE=? OR";
		}sql+="COPIEDFILECODE=? ) and CHECKDATE is null";
		System.out.println(sql);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			for(int i=1;i<=copiedFileCode.size();i++){
				pStatement.setString(i,copiedFileCode.get(i-1));
			}
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileCommitAlarmVDTO> searchUnReadSenderCodeOr(String senderCode,String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL489_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchUnReadSenderCodeOr(String senderCode,List<String> originFileCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;	

		String sql = SELECT_CPFILECOMMITALARMV_SQL59_0+" and originFileCode in ( ";
		
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
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	public List<CopiedFileCommitAlarmVDTO> searchUnReadReceiverCode(String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL59_0);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchUnReadReceiverCodeCp(String receiverCode,String copiedFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL579_0);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, copiedFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchUnReadReceiverCodeCp(String receiverCode,List<String> copiedFileCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;

		String sql = SELECT_CPFILECOMMITALARMV_SQL59_0+" and copiedFileCode in ( ";
		
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
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	public List<CopiedFileCommitAlarmVDTO> searchUnReadReceiverCodeOr(String receiverCode,String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL589_0);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchUnReadReceiverCodeOr(String receiverCode,List<String> originFileCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;

		String sql = SELECT_CPFILECOMMITALARMV_SQL59_0+" and originFileCode in ( ";
		
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
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<CopiedFileCommitAlarmVDTO> searchUnReadSendDate(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL69_0);
			pStatement.setInt(1, type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<CopiedFileCommitAlarmVDTO> searchUnReadCopiedFileCode(String copiedFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL79_0);
			pStatement.setString(1, copiedFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}	

	public List<CopiedFileCommitAlarmVDTO> searchUnReadOriginFileCode(String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL89_0);
			pStatement.setString(1, originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileCommitAlarmVDTO> searchUnReadOriginFileCode(List<String> originFileCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		
		int size = originFileCodeList.size();
		String sql = SELECT_CPFILECOMMITALARMV_SQL9_0+" and ORIGINFILECODE in ( ";
		
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
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CopiedFileCommitAlarmVDTO> searchUnReadCheckDate(String checkDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL9_0);
			pStatement.setString(1, checkDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

////////////////////////////////////////////////
	

	public List<CopiedFileCommitAlarmVDTO> searchReadMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL19_1);
			pStatement.setString(1,messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileCommitAlarmVDTO> searchReadGroupAlarmCode(String groupAlarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL29_1);
			pStatement.setString(1, groupAlarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public CopiedFileCommitAlarmVDTO searchReadCommitCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		CopiedFileCommitAlarmVDTO dto = new CopiedFileCommitAlarmVDTO();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL39_1);
			pStatement.setString(1,commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchReadSenderCode(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL49_1);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchReadSendOrRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL45O9_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchReadSendAndRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL45A9_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchReadSendOrRecOr(String senderCode,String receiverCode,String oriFile) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL45O89_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,oriFile);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileCommitAlarmVDTO> searchReadSendAndRecOr(String senderCode,String receiverCode,String oriFile) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;	
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL45A89_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,oriFile);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchReadSendOrRecCp(String senderCode,String receiverCode,String copiedFile) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL45O79_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,copiedFile);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileCommitAlarmVDTO> searchReadSendAndRecCp(String senderCode,String receiverCode,String copiedFile) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;	
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL45A79_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,copiedFile);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<CopiedFileCommitAlarmVDTO> searchReadSendOrRecOr(String senderCode,String receiverCode,List<String> oriFileList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;
		int size = oriFileList.size();
		String sql = SELECT_CPFILECOMMITALARMV_SQL45O9_1+" and ORIGINFILECODE in ( ";
		
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
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileCommitAlarmVDTO> searchReadSendAndRecOr(String senderCode,String receiverCode,List<String> oriFileList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;	
		int size = oriFileList.size();
		String sql = SELECT_CPFILECOMMITALARMV_SQL45A9_1+" and ORIGINFILECODE in ( ";
		
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
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchReadSendOrRecCp(String senderCode,String receiverCode,List<String> copiedFileList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;

		String sql = SELECT_CPFILECOMMITALARMV_SQL45O9_1+" and COPIEDFILECODE in ( ";
		
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
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileCommitAlarmVDTO> searchReadSendAndRecCp(String senderCode,String receiverCode,List<String> copiedFileList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;	
		
		String sql = SELECT_CPFILECOMMITALARMV_SQL45A9_1+" and COPIEDFILECODE in ( ";
		
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
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchReadSenderCodeCp(String senderCode,String copiedFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL479_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,copiedFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileCommitAlarmVDTO> searchReadSenderCodeCp(String senderCode,List<String> copiedFileCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		
		String sql = SELECT_CPFILECOMMITALARMV_SQL59_1+" and copiedFileCode in ( ";

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
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileCommitAlarmVDTO> searchReadSenderCodeOr(String senderCode,String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL489_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchReadSenderCodeOr(String senderCode,List<String> oriFileCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		

		String sql = SELECT_CPFILECOMMITALARMV_SQL59_1+" and originFileCode in ( ";

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
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	public List<CopiedFileCommitAlarmVDTO> searchReadReceiverCode(String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL59_1);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchReadReceiverCodeCp(String receiverCode,String copiedFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL579_1);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, copiedFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitAlarmVDTO> searchReadReceiverCodeCp(String receiverCode,List<String> copiedFileCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;

		String sql = SELECT_CPFILECOMMITALARMV_SQL59_1+" and CopiedFileCode in ( ";
		
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
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileCommitAlarmVDTO> searchReadReceiverCodeOr(String receiverCode,String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL589_1);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<CopiedFileCommitAlarmVDTO> searchReadReceiverCodeOr(String receiverCode,List<String> originFileCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;

		String sql = SELECT_CPFILECOMMITALARMV_SQL59_1+" and originFileCode in ( ";
		
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
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<CopiedFileCommitAlarmVDTO> searchReadSendDate(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL69_1);
			pStatement.setInt(1, type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<CopiedFileCommitAlarmVDTO> searchReadCopiedFileCode(String copiedFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL79_1);
			pStatement.setString(1, copiedFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}	

	public List<CopiedFileCommitAlarmVDTO> searchReadOriginFileCode(String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL89_1);
			pStatement.setString(1, originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CopiedFileCommitAlarmVDTO> searchReadCheckDate(String checkDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFILECOMMITALARMV_SQL9_1);
			pStatement.setString(1, checkDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CopiedFileCommitAlarmVDTO> searchReadOriginFileCode(List<String> originFileCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitAlarmVDTO> dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();
		ResultSet rs=null;		
		
		int size = originFileCodeList.size();
		String sql = SELECT_CPFILECOMMITALARMV_SQL9_1+" and ORIGINFILECODE in ( ";
		
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
			throw new DAOException("Error search CopiedFileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	
	private List<CopiedFileCommitAlarmVDTO> makeDTOList(ResultSet rs, List<CopiedFileCommitAlarmVDTO> dtoList) throws SQLException{
		
		if(dtoList==null){dtoList = new ArrayList<CopiedFileCommitAlarmVDTO>();}
		while(rs.next())
		{
			String messageCode = rs.getString("MESSAGECODE");
			if(rs.wasNull()){
				return null;
			}
			dtoList.add(new CopiedFileCommitAlarmVDTO(messageCode, rs.getString("alarmCode"), rs.getString("commitCode"), rs.getString("senderCode"), rs.getString("receiverCode"), rs.getInt("type"), rs.getString("originFileCode"), rs.getString("checkDate"), rs.getString("copiedFileCOde")));
		}
		return dtoList;
	}
	
	private CopiedFileCommitAlarmVDTO makeDTO(ResultSet rs, CopiedFileCommitAlarmVDTO dto) throws SQLException{
		
		String messageCode = rs.getString("MESSAGECODE");
		dto=null;
		while(rs.next())
		{
			String alarmCode = rs.getString("MESSAGECODE");
			if(rs.wasNull()){
				return null;
			}
			dto = new CopiedFileCommitAlarmVDTO(alarmCode, rs.getString("ALARMCODE"),rs.getString("COMMITCODE"),rs.getString("SENDERCODE"), rs.getString("receiverCode"), rs.getInt("TYPE"),rs.getString("originFileCode"), rs.getString("checkDate"),rs.getString("COPIEDFILECODE"));
		}
		return dto;
	}
	
}
