 package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import socialProExceptions.DAOException;
import dto.FileCommitGroupAlarmVDTO;

public class FileCommitGroupAlarmVDAO extends BaseDAO{

	private static final String SELECT_FILECOMMITGROUPALARMV_SQL1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where MESSAGECODE=?";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL2 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where GROUPALARMCODE=?";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL3 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where COMMITCODE=?";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL4 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where SENDERCODE=?";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL45 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where SENDERCODE=? and ORIGINPROJECTCODE=?";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL46O = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where SENDERCODE=? or RECEIVERCODE=?";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL456O = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where (SENDERCODE=? or RECEIVERCODE=?) and  ORIGINPROJECTCODE=?";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL46A = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where SENDERCODE=? and RECEIVERCODE=?";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL456A = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where SENDERCODE=? and RECEIVERCODE=? and  ORIGINPROJECTCODE=?";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL48 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where SENDERCODE=? and ORIGINFILECODE=?";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL5 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where ORIGINPROJECTCODE=?";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL6 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where RECEIVERCODE=?";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL65 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where RECEIVERCODE=? and ORIGINPROJECTCODE=?";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL68 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where RECEIVERCODE=? and ORIGINFILECODE=?";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL7 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where TYPE=?";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL8 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where ORIGINFILECODE=?";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL9 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where CHECKDATE=to_timeStamp(?,'yyyy-mm-dd hh24:mi:ss.ff3')";	
	

	private static final String SELECT_FILECOMMITGROUPALARMV_SQL19_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where MESSAGECODE=? and CHECKDATE is null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL29_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where GROUPALARMCODE=? and CHECKDATE is null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL39_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where COMMITCODE=? and CHECKDATE is null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL49_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where SENDERCODE=? and CHECKDATE is null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL459_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where SENDERCODE=? and ORIGINPROJECTCODE=? and CHECKDATE is null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL46O9_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where (SENDERCODE=? or RECEIVERCODE=?) and CHECKDATE is null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL456O9_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where (SENDERCODE=? or RECEIVERCODE=?) and  ORIGINPROJECTCODE=? and CHECKDATE is null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL46A9_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where SENDERCODE=? and RECEIVERCODE=? and CHECKDATE is null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL456A9_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where SENDERCODE=? and RECEIVERCODE=? and  ORIGINPROJECTCODE=? and CHECKDATE is null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL489_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where SENDERCODE=? and ORIGINFILECODE=? and CHECKDATE is null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL59_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where ORIGINPROJECTCODE=? and CHECKDATE is null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL69_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where RECEIVERCODE=? and CHECKDATE is null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL659_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where RECEIVERCODE=? and ORIGINPROJECTCODE=? and CHECKDATE is null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL689_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where RECEIVERCODE=? and ORIGINFILECODE=? and CHECKDATE is null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL79_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where TYPE=? and CHECKDATE is null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL89_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where ORIGINFILECODE=? and CHECKDATE is null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL9_0 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where CHECKDATE is null";	
	

	private static final String SELECT_FILECOMMITGROUPALARMV_SQL19_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where MESSAGECODE=? and CHECKDATE is not null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL29_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where GROUPALARMCODE=? and CHECKDATE is not null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL39_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where COMMITCODE=? and CHECKDATE is not null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL49_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where SENDERCODE=? and CHECKDATE is not null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL459_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where SENDERCODE=? and ORIGINPROJECTCODE=? and CHECKDATE is not null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL46O9_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where (SENDERCODE=? or RECEIVERCODE=?) and CHECKDATE is not null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL456O9_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where (SENDERCODE=? or RECEIVERCODE=?) and  ORIGINPROJECTCODE=? and CHECKDATE is not null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL46A9_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where SENDERCODE=? and RECEIVERCODE=? and CHECKDATE is not null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL456A9_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where SENDERCODE=? and RECEIVERCODE=? and  ORIGINPROJECTCODE=? and CHECKDATE is not null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL489_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where SENDERCODE=? and ORIGINFILECODE=? and CHECKDATE is not null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL59_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where ORIGINPROJECTCODE=? and CHECKDATE is not null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL69_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where RECEIVERCODE=? and CHECKDATE is not null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL659_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where RECEIVERCODE=? and ORIGINPROJECTCODE=? and CHECKDATE is not null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL689_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where RECEIVERCODE=? and ORIGINFILECODE=? and CHECKDATE is not null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL79_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where TYPE=? and CHECKDATE is not null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL89_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where ORIGINFILECODE=? and CHECKDATE is not null";
	private static final String SELECT_FILECOMMITGROUPALARMV_SQL9_1 = "SELECT DISTINCT MESSAGECODE,GROUPALARMCODE,COMMITCODE,SENDERCODE,ORIGINPROJECTCODE,RECEIVERCODE,TYPE,ORIGINFILECODE, to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM FILECOMMITGROUPALARM_VIEW where CHECKDATE is not null";	
	

	public List<FileCommitGroupAlarmVDTO> searchMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL1);
			pStatement.setString(1,messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<FileCommitGroupAlarmVDTO> searchGroupAlarmCode(String groupAlarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL2);
			pStatement.setString(1, groupAlarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public FileCommitGroupAlarmVDTO searchCommitCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		FileCommitGroupAlarmVDTO dto = new FileCommitGroupAlarmVDTO();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL3);
			pStatement.setString(1,commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FileCommitGroupAlarmVDTO> searchSenderCode(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL4);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<FileCommitGroupAlarmVDTO> searchSenderCodePro(String senderCode,String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL45);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FileCommitGroupAlarmVDTO> searchSendOrRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL46O);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<FileCommitGroupAlarmVDTO> searchSendOrRec(String senderCode,String receiverCode,String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL456O);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<FileCommitGroupAlarmVDTO> searchSendAnRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL46A);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<FileCommitGroupAlarmVDTO> searchSendAnRec(String senderCode,String receiverCode,String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL456A);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FileCommitGroupAlarmVDTO> searchSenderCodeFile(String senderCode,String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL48);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FileCommitGroupAlarmVDTO> searchOriginProjectCode(String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL5);
			pStatement.setString(1,originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FileCommitGroupAlarmVDTO> searchReceiverCode(String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL6);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<FileCommitGroupAlarmVDTO> searchReceiverCodePro(String receiverCode,String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL65);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<FileCommitGroupAlarmVDTO> searchReceiverCodeFile(String receiverCode,String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL68);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<FileCommitGroupAlarmVDTO> searchSendDate(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL7);
			pStatement.setInt(1, type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FileCommitGroupAlarmVDTO> searchOriginFileCode(String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL8);
			pStatement.setString(1, originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}	

	public List<FileCommitGroupAlarmVDTO> searchCheckDate(String checkDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL9);
			pStatement.setString(1, checkDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
/////////////////////////////////////////	
	

	public List<FileCommitGroupAlarmVDTO> searchUnReadMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL19_0);
			pStatement.setString(1,messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<FileCommitGroupAlarmVDTO> searchUnReadGroupAlarmCode(String groupAlarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL29_0);
			pStatement.setString(1, groupAlarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public FileCommitGroupAlarmVDTO searchUnReadCommitCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		FileCommitGroupAlarmVDTO dto = new FileCommitGroupAlarmVDTO();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL39_0);
			pStatement.setString(1,commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FileCommitGroupAlarmVDTO> searchUnReadSenderCode(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL49_0);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<FileCommitGroupAlarmVDTO> searchUnReadSenderCodePro(String senderCode,String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL459_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FileCommitGroupAlarmVDTO> searchUnReadSendOrRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL46O9_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<FileCommitGroupAlarmVDTO> searchUnReadSendOrRec(String senderCode,String receiverCode,String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL456O9_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FileCommitGroupAlarmVDTO> searchUnReadSendAnRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL46A9_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<FileCommitGroupAlarmVDTO> searchUnReadSendAnRec(String senderCode,String receiverCode,String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;	
		System.out.println(SELECT_FILECOMMITGROUPALARMV_SQL456A9_0);
		System.out.println("senderCode : "+senderCode+"   receiverCode : "+receiverCode+"   oriProCode : "+originProjectCode);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL456A9_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FileCommitGroupAlarmVDTO> searchUnReadSenderCodeFile(String senderCode,String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL489_0);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FileCommitGroupAlarmVDTO> searchUnReadOriginProjectCode(String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL59_0);
			pStatement.setString(1,originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FileCommitGroupAlarmVDTO> searchUnReadReceiverCode(String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL69_0);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<FileCommitGroupAlarmVDTO> searchUnReadReceiverCodePro(String receiverCode,String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL659_0);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<FileCommitGroupAlarmVDTO> searchUnReadReceiverCodeFile(String receiverCode,String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL689_0);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<FileCommitGroupAlarmVDTO> searchUnReadSendDate(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL79_0);
			pStatement.setInt(1, type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FileCommitGroupAlarmVDTO> searchUnReadOriginFileCode(String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL89_0);
			pStatement.setString(1, originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}	

	public List<FileCommitGroupAlarmVDTO> searchUnReadCheckDate(String checkDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL9_0);
			pStatement.setString(1, checkDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

///////////////////////////	/////////////////////
	

	public List<FileCommitGroupAlarmVDTO> searchReadMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL19_1);
			pStatement.setString(1,messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<FileCommitGroupAlarmVDTO> searchReadGroupAlarmCode(String groupAlarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL29_1);
			pStatement.setString(1, groupAlarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public FileCommitGroupAlarmVDTO searchReadCommitCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		FileCommitGroupAlarmVDTO dto = new FileCommitGroupAlarmVDTO();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL39_1);
			pStatement.setString(1,commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FileCommitGroupAlarmVDTO> searchReadSenderCode(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL49_1);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<FileCommitGroupAlarmVDTO> searchReadSenderCodePro(String senderCode,String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL459_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FileCommitGroupAlarmVDTO> searchReadSendOrRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL46O9_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<FileCommitGroupAlarmVDTO> searchReadSendOrRec(String senderCode,String receiverCode,String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL456O9_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FileCommitGroupAlarmVDTO> searchReadSendAnRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL46A9_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<FileCommitGroupAlarmVDTO> searchReadSendAnRec(String senderCode,String receiverCode,String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;	
		System.out.println(SELECT_FILECOMMITGROUPALARMV_SQL456A9_1);
		System.out.println("senderCode : "+senderCode+"  receiverCode : "+receiverCode+"  oriProCode : "+originProjectCode);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL456A9_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,receiverCode);
			pStatement.setString(3,originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FileCommitGroupAlarmVDTO> searchReadSenderCodeFile(String senderCode,String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL489_1);
			pStatement.setString(1,senderCode);
			pStatement.setString(2,originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FileCommitGroupAlarmVDTO> searchReadOriginProjectCode(String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL59_1);
			pStatement.setString(1,originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FileCommitGroupAlarmVDTO> searchReadReceiverCode(String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL69_1);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<FileCommitGroupAlarmVDTO> searchReadReceiverCodePro(String receiverCode,String originProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL659_1);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, originProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<FileCommitGroupAlarmVDTO> searchReadReceiverCodeFile(String receiverCode,String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL689_1);
			pStatement.setString(1,receiverCode);
			pStatement.setString(2, originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public List<FileCommitGroupAlarmVDTO> searchReadSendDate(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL79_1);
			pStatement.setInt(1, type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FileCommitGroupAlarmVDTO> searchReadOriginFileCode(String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL89_1);
			pStatement.setString(1, originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}	
	
	public List<FileCommitGroupAlarmVDTO> searchReadCheckDate(String checkDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileCommitGroupAlarmVDTO> dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILECOMMITGROUPALARMV_SQL9_1);
			pStatement.setString(1, checkDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FileCommitGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	
	
////////////////////////////////////////////////////
	
	
	private List<FileCommitGroupAlarmVDTO> makeDTOList(ResultSet rs, List<FileCommitGroupAlarmVDTO> dtoList) throws SQLException{
		
		if(dtoList==null){dtoList = new ArrayList<FileCommitGroupAlarmVDTO>();}
		while(rs.next())
		{
			String alarmCode = rs.getString("MESSAGECODE");
			if(rs.wasNull()){
				return null;
			}
			dtoList.add(new FileCommitGroupAlarmVDTO(alarmCode, rs.getString("GROUPALARMCODE"),rs.getString("COMMITCODE"),rs.getString("SENDERCODE"),rs.getString("ORIGINPROJECTCODE"), rs.getString("receiverCode"), rs.getInt("TYPE"),rs.getString("ORIGINFILECODE"), rs.getString("checkDate")));
		}
		return dtoList;
	}
	
	private FileCommitGroupAlarmVDTO makeDTO(ResultSet rs, FileCommitGroupAlarmVDTO dto) throws SQLException{
		
		System.out.println("makeDTO...");
		dto=null;
		while(rs.next())
		{
			String alarmCode = rs.getString("MESSAGECODE");
			if(rs.wasNull()){
				return null;
			}
			dto = new FileCommitGroupAlarmVDTO(alarmCode, rs.getString("GROUPALARMCODE"),rs.getString("COMMITCODE"),rs.getString("SENDERCODE"),rs.getString("ORIGINPROJECTCODE"), rs.getString("receiverCode"), rs.getInt("TYPE"),rs.getString("ORIGINFILECODE"), rs.getString("checkDate"));
		}
		return dto;
	}
	
}
