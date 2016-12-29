package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import socialProExceptions.DAOException;
import dto.CopiedFolderCommitAlarmVDTO;

public class CopiedFolderCommitAlarmVDAO extends BaseDAO{

	//messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where MESSAGECODE=?";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL2 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where ALARMCODE=?";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL3 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where COMMITCODE=?";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL4 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where SENDERCODE=?";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL45 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where SENDERCODE=? or RECEIVERCODE=?";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL45A = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where SENDERCODE=? and RECEIVERCODE=?";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL47 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where SENDERCODE=? and COPIEDFOLDERCODE=?";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL48 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where SENDERCODE=? and ORIGINFOLDERCODE=?";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL5 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where RECEIVERCODE=?";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL57 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where RECEIVERCODE=? and COPIEDFOLDERCODE=?";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL58 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where RECEIVERCODE=? and ORIGINFOLDERCODE=?";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL6 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where TYPE=?";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL7 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where COPIEDFOLDERCODE=?";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL8 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where ORIGINFOLDERCODE=?";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL9 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where CHECKDATE=to_timeStamp(?,'yyyy-mm-dd hh24:mi:ss.ff3')";	
	

	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL19_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where MESSAGECODE=? and CHECKDATE is null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL29_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where ALARMCODE=? and CHECKDATE is null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL39_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where COMMITCODE=? and CHECKDATE is null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL49_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where SENDERCODE=? and CHECKDATE is null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL459_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where (SENDERCODE=? or RECEIVERCODE=?) and CHECKDATE is null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL45A9_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where SENDERCODE=? and RECEIVERCODE=? and CHECKDATE is null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL479_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where SENDERCODE=? and COPIEDFOLDERCODE=? and CHECKDATE is null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL489_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where SENDERCODE=? and ORIGINFOLDERCODE=? and CHECKDATE is null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL59_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where RECEIVERCODE=? and CHECKDATE is null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL579_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where RECEIVERCODE=? and COPIEDFOLDERCODE=? and CHECKDATE is null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL589_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where RECEIVERCODE=? and ORIGINFOLDERCODE=? and CHECKDATE is null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL69_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where TYPE=? and CHECKDATE is null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL79_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where COPIEDFOLDERCODE=? and CHECKDATE is null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL89_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where ORIGINFOLDERCODE=? and CHECKDATE is null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL9_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where CHECKDATE is null";

	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL19_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where MESSAGECODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL29_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where ALARMCODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL39_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where COMMITCODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL49_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where SENDERCODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL459_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where (SENDERCODE=? or RECEIVERCODE=?) and CHECKDATE is not null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL45A9_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where SENDERCODE=? and RECEIVERCODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL479_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where SENDERCODE=? and COPIEDFOLDERCODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL489_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where SENDERCODE=? and ORIGINFOLDERCODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL59_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where RECEIVERCODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL579_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where RECEIVERCODE=? and COPIEDFOLDERCODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL589_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where RECEIVERCODE=? and ORIGINFOLDERCODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL69_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where TYPE=? and CHECKDATE is not null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL79_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where COPIEDFOLDERCODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL89_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where ORIGINFOLDERCODE=? and CHECKDATE is not null";
	private static final String SELECT_CPFOLDERCOMMITALARMV_SQL9_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, copiedFolderCode,originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM CopiedFOLDERCOMMITALARM_VIEW where CHECKDATE is not null";
	
	
	public List<CopiedFolderCommitAlarmVDTO> searchMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL1);
			pStatement.setString(1,messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFolderCommitAlarmVDTO> searchGroupAlarmCode(String groupAlarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL2);
			pStatement.setString(1, groupAlarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public CopiedFolderCommitAlarmVDTO searchCommitCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		CopiedFolderCommitAlarmVDTO dto = new CopiedFolderCommitAlarmVDTO();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL3);
			pStatement.setString(1,commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitAlarmVDTO> searchSenderCode(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL4);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitAlarmVDTO> searchSendRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL45);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFolderCommitAlarmVDTO> searchSendAndRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL45A);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitAlarmVDTO> searchSenderCodeCp(String senderCode,String copiedFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL47);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,copiedFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitAlarmVDTO> searchSenderCodeCp(String senderCode,List<String> copiedFolderList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		String sql = SELECT_CPFOLDERCOMMITALARMV_SQL4+" and COPIEDFOLDERCODE in ( ";
		
		for(int i=0;i<copiedFolderList.size()-1;i++){
			sql+="'"+copiedFolderList.get(i)+"', ";
		}sql+="'"+copiedFolderList.get(copiedFolderList.size()-1)+"' )";
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CopiedFolderCommitAlarmVDTO> searchSenderCodeOr(String senderCode,List<String> oriFolderList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		String sql = SELECT_CPFOLDERCOMMITALARMV_SQL4+" and ORIGINFOLDERCODE in ( ";
		
		for(int i=0;i<oriFolderList.size()-1;i++){
			sql+="'"+oriFolderList.get(i)+"', ";
		}sql+="'"+oriFolderList.get(oriFolderList.size()-1)+"' )";
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFolderCommitAlarmVDTO> searchSenderCodeOr(String senderCode,String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL48);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitAlarmVDTO> searchReceiverCode(String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL5);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitAlarmVDTO> searchReceiverCodeCp(String receiverCode,List<String> copiedFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		String sql = SELECT_CPFOLDERCOMMITALARMV_SQL5+" and COPIEDFOLDERCODE in ( ";
		
		for(int i=0;i<copiedFolderCode.size()-1;i++){
			sql+="'"+copiedFolderCode.get(i)+"', ";
		}sql+="'"+copiedFolderCode.get(copiedFolderCode.size()-1)+"' )";
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CopiedFolderCommitAlarmVDTO> searchReceiverCodeOr(String receiverCode,List<String> oriFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		String sql = SELECT_CPFOLDERCOMMITALARMV_SQL5+" and COPIEDFOLDERCODE in ( ";
		
		for(int i=0;i<oriFolderCode.size()-1;i++){
			sql+="'"+oriFolderCode.get(i)+"', ";
		}sql+="'"+oriFolderCode.get(oriFolderCode.size()-1)+"' )";
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitAlarmVDTO> searchReceiverCodeCp(String receiverCode,String copiedFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL57);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, copiedFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFolderCommitAlarmVDTO> searchReceiverCodeOr(String receiverCode,String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL58);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitAlarmVDTO> searchSendDate(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL6);
			pStatement.setInt(1, type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitAlarmVDTO> searchCopiedFolderCode(String copiedFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL7);
			pStatement.setString(1, copiedFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}	

	public List<CopiedFolderCommitAlarmVDTO> searchOriginFolderCode(String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL8);
			pStatement.setString(1, originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CopiedFolderCommitAlarmVDTO> searchCheckDate(String checkDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL9);
			pStatement.setString(1, checkDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
/////////////////////////////////////////	
	

	public List<CopiedFolderCommitAlarmVDTO> searchUnReadMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL19_0);
			pStatement.setString(1,messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFolderCommitAlarmVDTO> searchUnReadGroupAlarmCode(String groupAlarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL29_0);
			pStatement.setString(1, groupAlarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public CopiedFolderCommitAlarmVDTO searchUnReadCommitCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		CopiedFolderCommitAlarmVDTO dto = new CopiedFolderCommitAlarmVDTO();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL39_0);
			pStatement.setString(1,commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitAlarmVDTO> searchUnReadSenderCode(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL49_0);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitAlarmVDTO> searchUnReadSendRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL459_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitAlarmVDTO> searchUnReadSendAndRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL45A9_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<CopiedFolderCommitAlarmVDTO> searchUnReadSendAndRecCp(String senderCode,String receiverCode,List<String> copiedFolderList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		
		String sql = SELECT_CPFOLDERCOMMITALARMV_SQL45A9_0+" and COPIEDFOLDERCODE in ( ";
		
		for(int i=0;i<copiedFolderList.size()-1;i++){
			sql+="'"+copiedFolderList.get(i)+"', ";
		}sql+="'"+copiedFolderList.get(copiedFolderList.size()-1)+"' )";
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitAlarmVDTO> searchUnReadSendAndRecOr(String senderCode,String receiverCode,List<String> oriFolderList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		
		String sql = SELECT_CPFOLDERCOMMITALARMV_SQL45A9_0+" and ORIGINFOLDERCODE in ( ";
		
		for(int i=0;i<oriFolderList.size()-1;i++){
			sql+="'"+oriFolderList.get(i)+"', ";
		}sql+="'"+oriFolderList.get(oriFolderList.size()-1)+"' )";
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
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFolderCommitAlarmVDTO> searchUnReadSenderCodeCp(String senderCode,String copiedFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL479_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,copiedFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	
	public List<CopiedFolderCommitAlarmVDTO> searchUnReadSenderCodeOr(String senderCode,String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL489_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitAlarmVDTO> searchUnReadSenderCodeCp(String senderCode,List<String> copiedFolderList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		String sql = SELECT_CPFOLDERCOMMITALARMV_SQL49_0+" and COPIEDFOLDERCODE in ( ";
		
		for(int i=0;i<copiedFolderList.size()-1;i++){
			sql+="'"+copiedFolderList.get(i)+"', ";
		}sql+="'"+copiedFolderList.get(copiedFolderList.size()-1)+"' )";
		System.out.println(sql);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CopiedFolderCommitAlarmVDTO> searchUnReadSenderCodeOr(String senderCode,List<String> oriFolderList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		String sql = SELECT_CPFOLDERCOMMITALARMV_SQL49_0+" and ORIGINFOLDERCODE in ( ";
		
		for(int i=0;i<oriFolderList.size()-1;i++){
			sql+="'"+oriFolderList.get(i)+"', ";
		}sql+="'"+oriFolderList.get(oriFolderList.size()-1)+"' )";
		System.out.println(sql);
		System.out.println("senderCode  :  "+senderCode);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CopiedFolderCommitAlarmVDTO> searchUnReadReceiverCode(String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL59_0);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitAlarmVDTO> searchUnReadReceiverCodeCp(String receiverCode,String copiedFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL579_0);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, copiedFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitAlarmVDTO> searchUnReadReceiverCodeCp(String receiverCode,List<String> copiedFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		
		String sql = SELECT_CPFOLDERCOMMITALARMV_SQL59_0+" and COPIEDFOLDERCODE in ( ";
		
		for(int i=0;i<copiedFolderCode.size()-1;i++){
			sql+="'"+copiedFolderCode.get(i)+"', ";
		}sql+="'"+copiedFolderCode.get(copiedFolderCode.size()-1)+"' )";
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CopiedFolderCommitAlarmVDTO> searchUnReadReceiverCodeOr(String receiverCode,List<String> oriFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		
		String sql = SELECT_CPFOLDERCOMMITALARMV_SQL59_0+" and ORIGINFolderCode in ( ";
		
		for(int i=0;i<oriFolderCode.size()-1;i++){
			sql+="'"+oriFolderCode.get(i)+"', ";
		}sql+="'"+oriFolderCode.get(oriFolderCode.size()-1)+"' )";
		System.out.println(sql);
		System.out.println("reCeiver : "+receiverCode);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFolderCommitAlarmVDTO> searchUnReadReceiverCodeOr(String receiverCode,String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL589_0);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<CopiedFolderCommitAlarmVDTO> searchUnReadSendDate(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL69_0);
			pStatement.setInt(1, type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<CopiedFolderCommitAlarmVDTO> searchUnReadCopiedFolderCode(String copiedFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL79_0);
			pStatement.setString(1, copiedFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}	

	public List<CopiedFolderCommitAlarmVDTO> searchUnReadOriginFolderCode(String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL89_0);
			pStatement.setString(1, originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFolderCommitAlarmVDTO> searchUnReadOriginFolderCode(List<String> originFolderCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		
		int size = originFolderCodeList.size();
		String sql = SELECT_CPFOLDERCOMMITALARMV_SQL9_0+" and ORIGINFOLDERCODE in ( ";
		
		for(int i=0;i<size-1;i++){
			sql+="'"+originFolderCodeList.get(i)+"', ";
		}sql+="'"+originFolderCodeList.get(size-1)+"' )";
		
		System.out.println(sql);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFolderCommitAlarmVDTO> searchUnReadCheckDate(String checkDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL9_0);
			pStatement.setString(1, checkDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
/////////////////////////////////////////////////////
	
	

	public List<CopiedFolderCommitAlarmVDTO> searchReadMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL19_1);
			pStatement.setString(1,messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFolderCommitAlarmVDTO> searchReadGroupAlarmCode(String groupAlarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL29_1);
			pStatement.setString(1, groupAlarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public CopiedFolderCommitAlarmVDTO searchReadCommitCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		CopiedFolderCommitAlarmVDTO dto = new CopiedFolderCommitAlarmVDTO();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL39_1);
			pStatement.setString(1,commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitAlarmVDTO> searchReadSenderCode(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL49_1);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CopiedFolderCommitAlarmVDTO> searchReadSenderCodeCp(String senderCode,List<String> copiedFolderList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		String sql = SELECT_CPFOLDERCOMMITALARMV_SQL49_1+" and COPIEDFOLDERCODE in ( ";
		
		for(int i=0;i<copiedFolderList.size()-1;i++){
			sql+="'"+copiedFolderList.get(i)+"', ";
		}sql+="'"+copiedFolderList.get(copiedFolderList.size()-1)+"' )";
		System.out.println(sql);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CopiedFolderCommitAlarmVDTO> searchReadSenderCodeOr(String senderCode,List<String> oriFolderList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		String sql = SELECT_CPFOLDERCOMMITALARMV_SQL49_1+" and ORIGINFOLDERCODE in ( ";
		
		for(int i=0;i<oriFolderList.size()-1;i++){
			sql+="'"+oriFolderList.get(i)+"', ";
		}sql+="'"+oriFolderList.get(oriFolderList.size()-1)+"' )";
		System.out.println(sql);
		System.out.println("senderCode  ::  "+senderCode);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CopiedFolderCommitAlarmVDTO> searchReadSendRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL459_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitAlarmVDTO> searchReadSendAndRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL45A9_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<CopiedFolderCommitAlarmVDTO> searchReadSendAndRecCp(String senderCode,String receiverCode,List<String> copiedFolderList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		
		String sql = SELECT_CPFOLDERCOMMITALARMV_SQL45A9_1+" and COPIEDFOLDERCODE in ( ";
		
		for(int i=0;i<copiedFolderList.size()-1;i++){
			sql+="'"+copiedFolderList.get(i)+"', ";
		}sql+="'"+copiedFolderList.get(copiedFolderList.size()-1)+"' )";
		System.out.println(sql);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitAlarmVDTO> searchReadSendAndRecOr(String senderCode,String receiverCode,List<String> oriFolderList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		
		String sql = SELECT_CPFOLDERCOMMITALARMV_SQL45A9_1+" and ORIGINFOLDERCODE in ( ";
		
		for(int i=0;i<oriFolderList.size()-1;i++){
			sql+="'"+oriFolderList.get(i)+"', ";
		}sql+="'"+oriFolderList.get(oriFolderList.size()-1)+"' )";
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
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFolderCommitAlarmVDTO> searchReadSenderCodeCp(String senderCode,String copiedFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL479_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,copiedFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFolderCommitAlarmVDTO> searchReadSenderCodeOr(String senderCode,String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL489_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitAlarmVDTO> searchReadReceiverCode(String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL59_1);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitAlarmVDTO> searchReadReceiverCodeCp(String receiverCode,List<String> copiedFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		
		String sql = SELECT_CPFOLDERCOMMITALARMV_SQL59_1+" and COPIEDFOLDERCODE in ( ";
		
		for(int i=0;i<copiedFolderCode.size()-1;i++){
			sql+="'"+copiedFolderCode.get(i)+"', ";
		}sql+="'"+copiedFolderCode.get(copiedFolderCode.size()-1)+"' )";
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitAlarmVDTO> searchReadReceiverCodeOr(String receiverCode,List<String> oriFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		
		String sql = SELECT_CPFOLDERCOMMITALARMV_SQL59_1+" and OriginFOLDERCODE in ( ";
		
		for(int i=0;i<oriFolderCode.size()-1;i++){
			sql+="'"+oriFolderCode.get(i)+"', ";
		}sql+="'"+oriFolderCode.get(oriFolderCode.size()-1)+"' )";
		System.out.println(sql);
		System.out.println("receiverCode : "+receiverCode);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	public List<CopiedFolderCommitAlarmVDTO> searchReadReceiverCodeCp(String receiverCode,String copiedFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL579_1);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, copiedFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFolderCommitAlarmVDTO> searchReadReceiverCodeOr(String receiverCode,String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL589_1);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<CopiedFolderCommitAlarmVDTO> searchReadSendDate(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL69_1);
			pStatement.setInt(1, type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<CopiedFolderCommitAlarmVDTO> searchReadCopiedFolderCode(String copiedFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL79_1);
			pStatement.setString(1, copiedFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}	

	public List<CopiedFolderCommitAlarmVDTO> searchReadOriginFolderCode(String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL89_1);
			pStatement.setString(1, originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CopiedFolderCommitAlarmVDTO> searchReadCheckDate(String checkDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_CPFOLDERCOMMITALARMV_SQL9_1);
			pStatement.setString(1, checkDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitAlarmVDTO> searchReadOriginFolderCode(List<String> originFolderCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitAlarmVDTO> dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		
		int size = originFolderCodeList.size();
		String sql = SELECT_CPFOLDERCOMMITALARMV_SQL9_1+" and ORIGINFOLDERCODE in ( ";
		
		for(int i=0;i<size-1;i++){
			sql+="'"+originFolderCodeList.get(i)+"', ";
		}sql+="'"+originFolderCodeList.get(size-1)+"' )";
		
		System.out.println(sql);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	

	
	private List<CopiedFolderCommitAlarmVDTO> makeDTOList(ResultSet rs, List<CopiedFolderCommitAlarmVDTO> dtoList) throws SQLException{
		
		if(dtoList==null){dtoList = new ArrayList<CopiedFolderCommitAlarmVDTO>();}
		while(rs.next())
		{
			String alarmCode = rs.getString("MESSAGECODE");
			if(rs.wasNull()){
				return dtoList;
			}
			dtoList.add(new CopiedFolderCommitAlarmVDTO(alarmCode, rs.getString("ALARMCODE"),rs.getString("COMMITCODE"),rs.getString("SENDERCODE"), rs.getString("receiverCode"), rs.getInt("TYPE"),rs.getString("ORIGINFOLDERCODE"), rs.getString("checkDate"),rs.getString("COPIEDFOLDERCODE")));
		}
		return dtoList;
	}
	
	private CopiedFolderCommitAlarmVDTO makeDTO(ResultSet rs, CopiedFolderCommitAlarmVDTO dto) throws SQLException{
		
		System.out.println("makeDTO...");
		dto=null;
		while(rs.next())
		{
			String alarmCode = rs.getString("MESSAGECODE");
			if(rs.wasNull()){
				return dto;
			}
			dto = new CopiedFolderCommitAlarmVDTO(alarmCode, rs.getString("ALARMCODE"),rs.getString("COMMITCODE"),rs.getString("SENDERCODE"), rs.getString("receiverCode"), rs.getInt("TYPE"),rs.getString("ORIGINFOLDERCODE"), rs.getString("checkDate"),rs.getString("COPIEDFOLDERCODE"));
		}
		return dto;
	}
}
