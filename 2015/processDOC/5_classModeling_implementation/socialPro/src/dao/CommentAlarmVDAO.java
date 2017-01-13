package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import socialProExceptions.DAOException;
import dto.CommentAlarmVDTO;

public class CommentAlarmVDAO extends BaseDAO{


	private static final String SELECT_COMMENTALARMV_SQL1 = "select DISTINCT COMMENTCODE,COMMENTTARGETCODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM COMMENTALARM_VIEW where COMMENTCODE=?";
	private static final String SELECT_COMMENTALARMV_SQL2 = "select DISTINCT COMMENTCODE,COMMENTTARGETCODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM COMMENTALARM_VIEW where COMMENTTARGETCODE=?";
	private static final String SELECT_COMMENTALARMV_SQL3 = "select DISTINCT COMMENTCODE,COMMENTTARGETCODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM COMMENTALARM_VIEW where ALARMCODE=?";
	private static final String SELECT_COMMENTALARMV_SQL4 = "select DISTINCT COMMENTCODE,COMMENTTARGETCODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM COMMENTALARM_VIEW where CHECKDATE=to_timeStamp(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3')";
	

	private static final String SELECT_COMMENTALARMV_SQL14_0 = "select DISTINCT COMMENTCODE,COMMENTTARGETCODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM COMMENTALARM_VIEW where COMMENTCODE=? and CHECKDATE is null";
	private static final String SELECT_COMMENTALARMV_SQL24_0 = "select DISTINCT COMMENTCODE,COMMENTTARGETCODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM COMMENTALARM_VIEW where COMMENTTARGETCODE=? and CHECKDATE is null";
	private static final String SELECT_COMMENTALARMV_SQL34_0 = "select DISTINCT COMMENTCODE,COMMENTTARGETCODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM COMMENTALARM_VIEW where ALARMCODE=? and CHECKDATE is null";
	private static final String SELECT_COMMENTALARMV_SQL4_0 = "select DISTINCT COMMENTCODE,COMMENTTARGETCODE,ALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM COMMENTALARM_VIEW where CHECKDATE is null";
		

	public CommentAlarmVDTO searchCommentCode(String commentCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		CommentAlarmVDTO dto = new CommentAlarmVDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMENTALARMV_SQL1);
			pStatement.setString(1, commentCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search CommentAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CommentAlarmVDTO> searchCommentTargetCode(String commentTargetCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CommentAlarmVDTO> dtoList = new ArrayList<CommentAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMENTALARMV_SQL2);
			pStatement.setString(1, commentTargetCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CommentAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CommentAlarmVDTO> searchGroupAlarmCode(String groupAlarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CommentAlarmVDTO> dtoList = new ArrayList<CommentAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMENTALARMV_SQL3);
			pStatement.setString(1, groupAlarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CommentAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CommentAlarmVDTO> searchCheckDate(String checkDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CommentAlarmVDTO> dtoList = new ArrayList<CommentAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMENTALARMV_SQL4);
			pStatement.setString(1, checkDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CommentAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	////////////////////
	

	public CommentAlarmVDTO searchUncheckCommentCode(String commentCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		CommentAlarmVDTO dto = new CommentAlarmVDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMENTALARMV_SQL14_0);
			pStatement.setString(1, commentCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search CommentAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CommentAlarmVDTO> searchUncheckCommentTargetCode(String commentTargetCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CommentAlarmVDTO> dtoList = new ArrayList<CommentAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMENTALARMV_SQL24_0);
			pStatement.setString(1, commentTargetCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CommentAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CommentAlarmVDTO> searchUncheckGroupAlarmCode(String groupAlarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CommentAlarmVDTO> dtoList = new ArrayList<CommentAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMENTALARMV_SQL34_0);
			pStatement.setString(1, groupAlarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CommentAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CommentAlarmVDTO> searchUnCheckDate() throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CommentAlarmVDTO> dtoList = new ArrayList<CommentAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMENTALARMV_SQL4_0);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CommentAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	
	private List<CommentAlarmVDTO> makeDTOList(ResultSet rs, List<CommentAlarmVDTO> dtoList) throws SQLException{
		
		if(dtoList==null){dtoList = new ArrayList<CommentAlarmVDTO>();}
		while(rs.next())
		{
			String alarmCode = rs.getString("COMMENTCODE");
			if(rs.wasNull()){
				return null;
			}
			dtoList.add(new CommentAlarmVDTO(alarmCode, rs.getString("COMMENTTARGETCODE"), rs.getString("ALARMCODE"), rs.getString("SENDDATE")));
		}
		return dtoList;
	}
	
	private CommentAlarmVDTO makeDTO(ResultSet rs, CommentAlarmVDTO dto) throws SQLException{
		
		System.out.println("makeDTO...");
		dto=null;
		while(rs.next())
		{
			String alarmCode = rs.getString("COMMENTCODE");
			if(rs.wasNull()){
				return null;
			}
			dto = new CommentAlarmVDTO(alarmCode, rs.getString("COMMENTTARGETCODE"), rs.getString("ALARMCODE"), rs.getString("SENDDATE"));
		}
		return dto;
	}
}
