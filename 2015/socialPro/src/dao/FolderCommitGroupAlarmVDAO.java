 package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import socialProExceptions.DAOException;
import dto.FolderCommitGroupAlarmVDTO;

public class FolderCommitGroupAlarmVDAO extends BaseDAO{

	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL1 = "SELECT DISTINCT MESSAVECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where MESSAGECODE=?";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL2 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where GROUPALARMCODE=?";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL3 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where COMMITCODE=?";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL4 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where SENDERCODE=?";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL49 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where SENDERCODE=? and ORIGINPROJECTCODE=?";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL45O = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where SENDERCODE=? or RECEIVERCODE=?";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL459O = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where (SENDERCODE=? or RECEIVERCODE=?) and ORIGINPROJECTCODE=?";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL45A = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where SENDERCODE=? and RECEIVERCODE=?";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL459A = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where SENDERCODE=? and RECEIVERCODE=? and ORIGINPROJECTCODE=?";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL47 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where SENDERCODE=? and ORIGINFOLDERCODE=?";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL5 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where RECEIVERCODE=?";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL59 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where RECEIVERCODE=? and ORIGINPROJECTCODE=?";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL57 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where RECEIVERCODE=? and ORIGINFOLDERCODE=?";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL6 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where TYPE=?";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL7 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where ORIGINFOLDERCODE=?";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL8 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where CHECKDATE=to_timeStamp(?,'yyyy-mm-dd hh24:mi:ss.ff3')";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL9 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where ORIGINPROJECTCODE=?";
	

	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL18_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where MESSAGECODE=? and CHECKDATE is null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL28_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where GROUPALARMCODE=? and CHECKDATE is null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL38_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where COMMITCODE=? and CHECKDATE is null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL48_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where SENDERCODE=? and CHECKDATE is null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL498_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where SENDERCODE=? and ORIGINPROJECTCODE=? and CHECKDATE is null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL45O8_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where SENDERCODE=? or RECEIVERCODE=? and CHECKDATE is null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL459O8_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where (SENDERCODE=? or RECEIVERCODE=?) and ORIGINPROJECTCODE=? and CHECKDATE is null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL45A8_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where SENDERCODE=? and RECEIVERCODE=? and CHECKDATE is null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL459A8_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where SENDERCODE=? and RECEIVERCODE=? and ORIGINPROJECTCODE=? and CHECKDATE is null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL478_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where SENDERCODE=? and ORIGINFOLDERCODE=? and CHECKDATE is null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL58_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where RECEIVERCODE=? and CHECKDATE is null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL578_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where RECEIVERCODE=? and ORIGINFOLDERCODE=? and CHECKDATE is null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL598_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where RECEIVERCODE=? and ORIGINPROJECTCODE=? and CHECKDATE is null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL68_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where TYPE=? and CHECKDATE is null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL78_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where ORIGINFOLDERCODE=? and CHECKDATE is null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL8_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where CHECKDATE is null";	
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL98_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where ORIGINPROJECTCODE=? and CHECKDATE is null";

	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL18_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where MESSAGECODE=? and CHECKDATE is not null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL28_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where GROUPALARMCODE=? and CHECKDATE is not null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL38_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where COMMITCODE=? and CHECKDATE is not null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL48_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where SENDERCODE=? and CHECKDATE is not null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL498_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where SENDERCODE=? and ORIGINPROJECTCODE=? and CHECKDATE is not null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL45O8_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where SENDERCODE=? or RECEIVERCODE=? and CHECKDATE is not null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL459O8_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where (SENDERCODE=? or RECEIVERCODE=?) and ORIGINPROJECTCODE=? and CHECKDATE is not null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL45A8_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where SENDERCODE=? and RECEIVERCODE=? and CHECKDATE is not null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL459A8_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where SENDERCODE=? and RECEIVERCODE=? and ORIGINPROJECTCODE=? and CHECKDATE is not null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL478_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where SENDERCODE=? and ORIGINFOLDERCODE=? and CHECKDATE is not null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL58_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where RECEIVERCODE=? and CHECKDATE is not null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL578_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where RECEIVERCODE=? and ORIGINFOLDERCODE=? and CHECKDATE is not null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL598_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where RECEIVERCODE=? and ORIGINPROJECTCODE=? and CHECKDATE is not null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL68_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where TYPE=? and CHECKDATE is not null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL78_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where ORIGINFOLDERCODE=? and CHECKDATE is not null";
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL8_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where CHECKDATE is not null";	
	private static final String SELECT_FOLDERCOMMITGROUPALARMV_SQL98_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFOLDERCODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FOLDERCOMMITGROUPALARM_VIEW where ORIGINPROJECTCODE=? and CHECKDATE is not null";
	
	
	public List<FolderCommitGroupAlarmVDTO> searchMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL1);
			pStatement.setString(1,messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<FolderCommitGroupAlarmVDTO> searchGroupAlarmCode(String groupAlarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL2);
			pStatement.setString(1, groupAlarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public FolderCommitGroupAlarmVDTO searchCommitCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		FolderCommitGroupAlarmVDTO dto = new FolderCommitGroupAlarmVDTO();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL3);
			pStatement.setString(1,commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchSenderCode(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL4);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchSendOrRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL45O);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchSendOrRec(String senderCode,String receiverCode,String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL459O);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchSendAnRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL45A);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchSendAnRec(String senderCode,String receiverCode,String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL459A);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchSenderCodePro(String senderCode,String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL49);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<FolderCommitGroupAlarmVDTO> searchSenderCodeFolder(String senderCode,String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL47);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchReceiverCode(String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL5);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<FolderCommitGroupAlarmVDTO> searchReceiverCodeFolder(String receiverCode,String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL57);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchReceiverCodePro(String receiverCode,String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL59);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchSendDate(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL6);
			pStatement.setInt(1, type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchOriginFileCode(String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL7);
			pStatement.setString(1, originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}	

	public List<FolderCommitGroupAlarmVDTO> searchCheckDate(String checkDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL8);
			pStatement.setString(1, checkDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<FolderCommitGroupAlarmVDTO> searchOriginProjectCode(String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL9);
			pStatement.setString(1, originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
/////////////////////////////////////////	
	

	public List<FolderCommitGroupAlarmVDTO> searchUnReadMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL18_0);
			pStatement.setString(1,messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<FolderCommitGroupAlarmVDTO> searchUnReadGroupAlarmCode(String groupAlarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL28_0);
			pStatement.setString(1, groupAlarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public FolderCommitGroupAlarmVDTO searchUnReadCommitCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		FolderCommitGroupAlarmVDTO dto = new FolderCommitGroupAlarmVDTO();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL38_0);
			pStatement.setString(1,commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchUnReadSenderCode(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL48_0);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchUnReadSendOrRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL45O8_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchUnReadSendOrRec(String senderCode,String receiverCode,String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL459O8_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchUnReadSendAnRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL45A8_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchUnReadSendAnRec(String senderCode,String receiverCode,String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;	
		System.out.println(SELECT_FOLDERCOMMITGROUPALARMV_SQL459A8_0);
		System.out.println("senderCode : "+senderCode+"   receiverCode : "+receiverCode+"   oriProCode : "+originProjectCode);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL459A8_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchUnReadSenderCodeFolder(String senderCode,String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL478_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchUnReadSenderCodePro(String senderCode,String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL498_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchUnReadReceiverCode(String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL58_0);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<FolderCommitGroupAlarmVDTO> searchUnReadReceiverCodeFolder(String receiverCode,String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL578_0);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchUnReadReceiverCodePro(String receiverCode,String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL598_0);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchUnReadSendDate(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL68_0);
			pStatement.setInt(1, type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchUnReadOriginFileCode(String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL78_0);
			pStatement.setString(1, originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}	

	public List<FolderCommitGroupAlarmVDTO> searchUnReadCheckDate() throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL8_0);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchUnReadOriginProject(String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL98_0);
			pStatement.setString(1, originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<FolderCommitGroupAlarmVDTO> searchReadMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL18_1);
			pStatement.setString(1,messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<FolderCommitGroupAlarmVDTO> searchReadGroupAlarmCode(String groupAlarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL28_1);
			pStatement.setString(1, groupAlarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public FolderCommitGroupAlarmVDTO searchReadCommitCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		FolderCommitGroupAlarmVDTO dto = new FolderCommitGroupAlarmVDTO();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL38_1);
			pStatement.setString(1,commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchReadSenderCode(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL48_1);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchReadSendOrRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL45O8_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchReadSendOrRec(String senderCode,String receiverCode,String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL459O8_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchReadSendAnRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL45A8_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchReadSendAnRec(String senderCode,String receiverCode,String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;	
		System.out.println(SELECT_FOLDERCOMMITGROUPALARMV_SQL459A8_1);
		System.out.println("senderCode : "+senderCode+"  receiverCode : "+receiverCode+"  oriProCode : "+originProjectCode);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL459A8_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	public List<FolderCommitGroupAlarmVDTO> searchReadSenderCodeFolder(String senderCode,String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL478_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchReadSenderCodePro(String senderCode,String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL498_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchReadReceiverCode(String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL58_1);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<FolderCommitGroupAlarmVDTO> searchReadReceiverCodeFolder(String receiverCode,String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL578_1);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchReadReceiverCodePro(String receiverCode,String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL598_1);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchReadSendDate(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL68_1);
			pStatement.setInt(1, type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchReadOriginFileCode(String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL78_1);
			pStatement.setString(1, originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}	

	public List<FolderCommitGroupAlarmVDTO> searchReadCheckDate() throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL8_1);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderCommitGroupAlarmVDTO> searchReadOriginProject(String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FolderCommitGroupAlarmVDTO> dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDERCOMMITGROUPALARMV_SQL98_1);
			pStatement.setString(1, originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	
	private List<FolderCommitGroupAlarmVDTO> makeDTOList(ResultSet rs, List<FolderCommitGroupAlarmVDTO> dtoList) throws SQLException{
		
		if(dtoList==null){dtoList = new ArrayList<FolderCommitGroupAlarmVDTO>();}
		while(rs.next())
		{
			String alarmCode = rs.getString("MESSAGECODE");
			if(rs.wasNull()){
				return null;
			}
			dtoList.add(new FolderCommitGroupAlarmVDTO(alarmCode, rs.getString("GROUPALARMCODE"),rs.getString("COMMITCODE"),rs.getString("SENDERCODE"),rs.getString("ORIGINPROJECTCODE"), rs.getString("receiverCode"), rs.getInt("TYPE"),rs.getString("ORIGINFOLDERCODE"), rs.getString("checkDate")));
		}
		return dtoList;
	}
	
	private FolderCommitGroupAlarmVDTO makeDTO(ResultSet rs, FolderCommitGroupAlarmVDTO dto) throws SQLException{
		
		System.out.println("makeDTO...");
		dto=null;
		while(rs.next())
		{
			String alarmCode = rs.getString("MESSAGECODE");
			if(rs.wasNull()){
				return null;
			}
			dto = new FolderCommitGroupAlarmVDTO(alarmCode, rs.getString("GROUPALARMCODE"),rs.getString("COMMITCODE"),rs.getString("SENDERCODE"),rs.getString("ORIGINPROJECTCODE"), rs.getString("receiverCode"), rs.getInt("TYPE"),rs.getString("ORIGINFOLDERCODE"), rs.getString("checkDate"));
		}
		return dto;
	}
	
}
