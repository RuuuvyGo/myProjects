package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import socialProExceptions.DAOException;
import dto.OriginFolderCommitAlarmVDTO;

public class OriginFolderCommitAlarmVDAO extends BaseDAO{

	//messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where MESSAGECODE=?";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL2 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where ALARMCODE=?";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL3 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where COMMITCODE=?";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL4 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where SENDERCODE=?";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL45 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where SENDERCODE=? or RECEIVERCODE=?";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL45A = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where SENDERCODE=? and RECEIVERCODE=?";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL48 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where SENDERCODE=? and ORIGINFOLDERCODE=?";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL5 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where RECEIVERCODE=?";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL58 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where RECEIVERCODE=? and ORIGINFOLDERCODE=?";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL6 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where TYPE=?";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL8 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where ORIGINFOLDERCODE=?";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL9 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where CHECKDATE=to_timeStamp(?,'yyyy-mm-dd hh24:mi:ss.ff3')";	
	

	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL19_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where MESSAGECODE=? and CHECKDATE is null";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL29_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where ALARMCODE=? and CHECKDATE is null";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL39_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where COMMITCODE=? and CHECKDATE is null";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL49_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where SENDERCODE=? and CHECKDATE is null";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL459_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where (SENDERCODE=? or RECEIVERCODE=?) and CHECKDATE is null";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL45A9_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where SENDERCODE=? and RECEIVERCODE=? and CHECKDATE is null";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL489_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where SENDERCODE=? and ORIGINFOLDERCODE=? and CHECKDATE is null";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL59_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where RECEIVERCODE=? and CHECKDATE is null";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL589_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where RECEIVERCODE=? and ORIGINFOLDERCODE=? and CHECKDATE is null";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL89_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where ORIGINFOLDERCODE=? and CHECKDATE is null";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL9_0 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where CHECKDATE is null";

	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL19_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where MESSAGECODE=? and CHECKDATE is not null";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL29_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where ALARMCODE=? and CHECKDATE is not null";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL39_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where COMMITCODE=? and CHECKDATE is not null";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL49_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where SENDERCODE=? and CHECKDATE is not null";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL459_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where (SENDERCODE=? or RECEIVERCODE=?) and CHECKDATE is not null";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL45A9_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where SENDERCODE=? and RECEIVERCODE=? and CHECKDATE is not null";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL489_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where SENDERCODE=? and ORIGINFOLDERCODE=? and CHECKDATE is not null";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL59_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where RECEIVERCODE=? and CHECKDATE is not null";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL589_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where RECEIVERCODE=? and ORIGINFOLDERCODE=? and CHECKDATE is not null";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL69_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where TYPE=? and CHECKDATE is not null";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL89_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where ORIGINFOLDERCODE=? and CHECKDATE is not null";
	private static final String SELECT_ORIFOLDERCOMMITALARMV_SQL9_1 = "SELECT DISTINCT messageCode, alarmCode, commitCode, senderCode, receiverCode ,type, originFolderCode, to_char(checkDate,'yyyy-mm-dd hh24:mi:ss.ff3') as checkDate FROM OriginFOLDERCOMMITALARM_VIEW where CHECKDATE is not null";
	
	
	public List<OriginFolderCommitAlarmVDTO> searchMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL1);
			pStatement.setString(1,messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginFolderCommitAlarmVDTO> searchGroupAlarmCode(String groupAlarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL2);
			pStatement.setString(1, groupAlarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public OriginFolderCommitAlarmVDTO searchCommitCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		OriginFolderCommitAlarmVDTO dto = new OriginFolderCommitAlarmVDTO();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL3);
			pStatement.setString(1,commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFolderCommitAlarmVDTO> searchSenderCode(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL4);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFolderCommitAlarmVDTO> searchSendRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL45);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginFolderCommitAlarmVDTO> searchSendAndRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL45A);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
/*
	public List<OriginFolderCommitAlarmVDTO> searchSenderCodeCp(String senderCode,List<String> copiedFolderList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		String sql = SELECT_ORIFOLDERCOMMITALARMV_SQL4+" and COPIEDFOLDERCODE in ( ";
		
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
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	*/

	public List<OriginFolderCommitAlarmVDTO> searchSenderCodeOr(String senderCode,List<String> oriFolderList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		String sql = SELECT_ORIFOLDERCOMMITALARMV_SQL4+" and ORIGINFOLDERCODE in ( ";
		
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
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginFolderCommitAlarmVDTO> searchSenderCodeOr(String senderCode,String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL48);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFolderCommitAlarmVDTO> searchReceiverCode(String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL5);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	/*public List<OriginFolderCommitAlarmVDTO> searchReceiverCodeCp(String receiverCode,List<String> copiedFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		String sql = SELECT_ORIFOLDERCOMMITALARMV_SQL5+" and COPIEDFOLDERCODE in ( ";
		
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
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}*/
	
/*
	public List<OriginFolderCommitAlarmVDTO> searchReceiverCodeOr(String receiverCode,List<String> oriFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		String sql = SELECT_ORIFOLDERCOMMITALARMV_SQL5+" and COPIEDFOLDERCODE in ( ";
		
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
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}*/

	public List<OriginFolderCommitAlarmVDTO> searchReceiverCodeOr(String receiverCode,String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL58);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFolderCommitAlarmVDTO> searchSendDate(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL6);
			pStatement.setInt(1, type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFolderCommitAlarmVDTO> searchOriginFolderCode(String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL8);
			pStatement.setString(1, originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<OriginFolderCommitAlarmVDTO> searchCheckDate(String checkDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL9);
			pStatement.setString(1, checkDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
/////////////////////////////////////////	
	

	public List<OriginFolderCommitAlarmVDTO> searchUnReadMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL19_0);
			pStatement.setString(1,messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginFolderCommitAlarmVDTO> searchUnReadGroupAlarmCode(String groupAlarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL29_0);
			pStatement.setString(1, groupAlarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public OriginFolderCommitAlarmVDTO searchUnReadCommitCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		OriginFolderCommitAlarmVDTO dto = new OriginFolderCommitAlarmVDTO();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL39_0);
			pStatement.setString(1,commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFolderCommitAlarmVDTO> searchUnReadSenderCode(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL49_0);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFolderCommitAlarmVDTO> searchUnReadSendRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL459_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFolderCommitAlarmVDTO> searchUnReadSendAndRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL45A9_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

/*
	public List<OriginFolderCommitAlarmVDTO> searchUnReadSendAndRecCp(String senderCode,String receiverCode,List<String> copiedFolderList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		
		String sql = SELECT_ORIFOLDERCOMMITALARMV_SQL45A9_0+" and COPIEDFOLDERCODE in ( ";
		
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
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}*/

	public List<OriginFolderCommitAlarmVDTO> searchUnReadSendAndRecOr(String senderCode,String receiverCode,List<String> oriFolderList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		
		String sql = SELECT_ORIFOLDERCOMMITALARMV_SQL45A9_0+" and ORIGINFOLDERCODE in ( ";
		
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
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginFolderCommitAlarmVDTO> searchUnReadSenderCodeOr(String senderCode,String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL489_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	/*public List<OriginFolderCommitAlarmVDTO> searchUnReadSenderCodeCp(String senderCode,List<String> copiedFolderList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		String sql = SELECT_ORIFOLDERCOMMITALARMV_SQL49_0+" and COPIEDFOLDERCODE in ( ";
		
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
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	*/

	public List<OriginFolderCommitAlarmVDTO> searchUnReadSenderCodeOr(String senderCode,List<String> oriFolderList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		String sql = SELECT_ORIFOLDERCOMMITALARMV_SQL49_0+" and ORIGINFOLDERCODE in ( ";
		
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
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<OriginFolderCommitAlarmVDTO> searchUnReadReceiverCode(String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL59_0);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
/*
	public List<OriginFolderCommitAlarmVDTO> searchUnReadReceiverCodeCp(String receiverCode,List<String> copiedFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		
		String sql = SELECT_ORIFOLDERCOMMITALARMV_SQL59_0+" and COPIEDFOLDERCODE in ( ";
		
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
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}*/
	

	public List<OriginFolderCommitAlarmVDTO> searchUnReadReceiverCodeOr(String receiverCode,List<String> oriFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		
		String sql = SELECT_ORIFOLDERCOMMITALARMV_SQL59_0+" and ORIGINFolderCode in ( ";
		
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
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginFolderCommitAlarmVDTO> searchUnReadReceiverCodeOr(String receiverCode,String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL589_0);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFolderCommitAlarmVDTO> searchUnReadOriginFolderCode(String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL89_0);
			pStatement.setString(1, originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginFolderCommitAlarmVDTO> searchUnReadOriginFolderCode(List<String> originFolderCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		
		int size = originFolderCodeList.size();
		String sql = SELECT_ORIFOLDERCOMMITALARMV_SQL9_0+" and ORIGINFOLDERCODE in ( ";
		
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
	
	public List<OriginFolderCommitAlarmVDTO> searchUnReadCheckDate(String checkDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL9_0);
			pStatement.setString(1, checkDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
/////////////////////////////////////////////////////
	
	

	public List<OriginFolderCommitAlarmVDTO> searchReadMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL19_1);
			pStatement.setString(1,messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginFolderCommitAlarmVDTO> searchReadGroupAlarmCode(String groupAlarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL29_1);
			pStatement.setString(1, groupAlarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public OriginFolderCommitAlarmVDTO searchReadCommitCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		OriginFolderCommitAlarmVDTO dto = new OriginFolderCommitAlarmVDTO();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL39_1);
			pStatement.setString(1,commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFolderCommitAlarmVDTO> searchReadSenderCode(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL49_1);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
/*
	public List<OriginFolderCommitAlarmVDTO> searchReadSenderCodeCp(String senderCode,List<String> copiedFolderList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		String sql = SELECT_ORIFOLDERCOMMITALARMV_SQL49_1+" and COPIEDFOLDERCODE in ( ";
		
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
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	*/

	public List<OriginFolderCommitAlarmVDTO> searchReadSenderCodeOr(String senderCode,List<String> oriFolderList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		String sql = SELECT_ORIFOLDERCOMMITALARMV_SQL49_1+" and ORIGINFOLDERCODE in ( ";
		
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
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<OriginFolderCommitAlarmVDTO> searchReadSendRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL459_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFolderCommitAlarmVDTO> searchReadSendAndRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL45A9_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

/*
	public List<OriginFolderCommitAlarmVDTO> searchReadSendAndRecCp(String senderCode,String receiverCode,List<String> copiedFolderList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		
		String sql = SELECT_ORIFOLDERCOMMITALARMV_SQL45A9_1+" and COPIEDFOLDERCODE in ( ";
		
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
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}*/

	public List<OriginFolderCommitAlarmVDTO> searchReadSendAndRecOr(String senderCode,String receiverCode,List<String> oriFolderList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		
		String sql = SELECT_ORIFOLDERCOMMITALARMV_SQL45A9_1+" and ORIGINFOLDERCODE in ( ";
		
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
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginFolderCommitAlarmVDTO> searchReadSenderCodeOr(String senderCode,String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL489_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFolderCommitAlarmVDTO> searchReadReceiverCode(String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL59_1);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
/*
	public List<OriginFolderCommitAlarmVDTO> searchReadReceiverCodeCp(String receiverCode,List<String> copiedFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		
		String sql = SELECT_ORIFOLDERCOMMITALARMV_SQL59_1+" and COPIEDFOLDERCODE in ( ";
		
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
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}*/

	public List<OriginFolderCommitAlarmVDTO> searchReadReceiverCodeOr(String receiverCode,List<String> oriFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;
		
		String sql = SELECT_ORIFOLDERCOMMITALARMV_SQL59_1+" and OriginFOLDERCODE in ( ";
		
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
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginFolderCommitAlarmVDTO> searchReadReceiverCodeOr(String receiverCode,String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL589_1);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<OriginFolderCommitAlarmVDTO> searchReadSendDate(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL69_1);
			pStatement.setInt(1, type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFolderCommitAlarmVDTO> searchReadOriginFolderCode(String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL89_1);
			pStatement.setString(1, originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<OriginFolderCommitAlarmVDTO> searchReadCheckDate(String checkDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIFOLDERCOMMITALARMV_SQL9_1);
			pStatement.setString(1, checkDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFolderCommitAlarmVDTO> searchReadOriginFolderCode(List<String> originFolderCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitAlarmVDTO> dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();
		ResultSet rs=null;		
		
		int size = originFolderCodeList.size();
		String sql = SELECT_ORIFOLDERCOMMITALARMV_SQL9_1+" and ORIGINFOLDERCODE in ( ";
		
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
	
	

	
	private List<OriginFolderCommitAlarmVDTO> makeDTOList(ResultSet rs, List<OriginFolderCommitAlarmVDTO> dtoList) throws SQLException{
		
		if(dtoList==null){dtoList = new ArrayList<OriginFolderCommitAlarmVDTO>();}
		while(rs.next())
		{
			String alarmCode = rs.getString("MESSAGECODE");
			if(rs.wasNull()){
				return dtoList;
			}
			dtoList.add(new OriginFolderCommitAlarmVDTO(alarmCode, rs.getString("ALARMCODE"),rs.getString("COMMITCODE"),rs.getString("SENDERCODE"), rs.getString("receiverCode"), rs.getInt("TYPE"),rs.getString("ORIGINFOLDERCODE"), rs.getString("checkDate")));
		}
		return dtoList;
	}
	
	private OriginFolderCommitAlarmVDTO makeDTO(ResultSet rs, OriginFolderCommitAlarmVDTO dto) throws SQLException{
		
		System.out.println("makeDTO...");
		dto=null;
		while(rs.next())
		{
			String alarmCode = rs.getString("MESSAGECODE");
			if(rs.wasNull()){
				return dto;
			}
			dto = new OriginFolderCommitAlarmVDTO(alarmCode, rs.getString("ALARMCODE"),rs.getString("COMMITCODE"),rs.getString("SENDERCODE"), rs.getString("receiverCode"), rs.getInt("TYPE"),rs.getString("ORIGINFOLDERCODE"), rs.getString("checkDate"));
		}
		return dto;
	}
}
