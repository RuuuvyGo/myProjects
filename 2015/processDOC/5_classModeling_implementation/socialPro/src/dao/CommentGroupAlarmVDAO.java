package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import socialProExceptions.DAOException;
import dto.CommentGroupAlarmVDTO;

public class CommentGroupAlarmVDAO extends BaseDAO{


	private static final String SELECT_COMMENTGROUPALARMV_SQL1 = "SELECT DISTINCT COMMENTCODE,COMMENTTARGETCODE,GROUPALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM COMMENTGROUPALARM_VIEW where COMMENTCODE=?";
	private static final String SELECT_COMMENTGROUPALARMV_SQL2 = "SELECT DISTINCT COMMENTCODE,COMMENTTARGETCODE,GROUPALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM COMMENTGROUPALARM_VIEW where COMMENTTARGETCODE=?";
	private static final String SELECT_COMMENTGROUPALARMV_SQL3 = "SELECT DISTINCT COMMENTCODE,COMMENTTARGETCODE,GROUPALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM COMMENTGROUPALARM_VIEW where GROUPALARMCODE=?";
	private static final String SELECT_COMMENTGROUPALARMV_SQL4 = "SELECT DISTINCT COMMENTCODE,COMMENTTARGETCODE,GROUPALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM COMMENTGROUPALARM_VIEW where CHECKDATE=to_timeStamp(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3')";
	

	private static final String SELECT_COMMENTGROUPALARMV_SQL14_0 = "SELECT DISTINCT COMMENTCODE,COMMENTTARGETCODE,GROUPALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM COMMENTGROUPALARM_VIEW where COMMENTCODE=? and CHECKDATE is null";
	private static final String SELECT_COMMENTGROUPALARMV_SQL24_0 = "SELECT DISTINCT COMMENTCODE,COMMENTTARGETCODE,GROUPALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM COMMENTGROUPALARM_VIEW where COMMENTTARGETCODE=? and CHECKDATE is null";
	private static final String SELECT_COMMENTGROUPALARMV_SQL34_0 = "SELECT DISTINCT COMMENTCODE,COMMENTTARGETCODE,GROUPALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM COMMENTGROUPALARM_VIEW where GROUPALARMCODE=? and CHECKDATE is null";
	private static final String SELECT_COMMENTGROUPALARMV_SQL4_0 = "SELECT DISTINCT COMMENTCODE,COMMENTTARGETCODE,GROUPALARMCODE,to_char(CHECKDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as CHECKDATE FROM COMMENTGROUPALARM_VIEW where CHECKDATE is null";
		

	public CommentGroupAlarmVDTO searchCommentCode(String commentCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		CommentGroupAlarmVDTO dto = new CommentGroupAlarmVDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMENTGROUPALARMV_SQL1);
			pStatement.setString(1, commentCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search CommentGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CommentGroupAlarmVDTO> searchCommentTargetCode(String commentTargetCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CommentGroupAlarmVDTO> dtoList = new ArrayList<CommentGroupAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMENTGROUPALARMV_SQL2);
			pStatement.setString(1, commentTargetCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CommentGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CommentGroupAlarmVDTO> searchGroupAlarmCode(String groupAlarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CommentGroupAlarmVDTO> dtoList = new ArrayList<CommentGroupAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMENTGROUPALARMV_SQL3);
			pStatement.setString(1, groupAlarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CommentGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CommentGroupAlarmVDTO> searchCheckDate(String checkDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CommentGroupAlarmVDTO> dtoList = new ArrayList<CommentGroupAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMENTGROUPALARMV_SQL4);
			pStatement.setString(1, checkDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CommentGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	////////////////////
	

	public CommentGroupAlarmVDTO searchUncheckCommentCode(String commentCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		CommentGroupAlarmVDTO dto = new CommentGroupAlarmVDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMENTGROUPALARMV_SQL14_0);
			pStatement.setString(1, commentCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search CommentGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CommentGroupAlarmVDTO> searchUncheckCommentTargetCode(String commentTargetCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CommentGroupAlarmVDTO> dtoList = new ArrayList<CommentGroupAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMENTGROUPALARMV_SQL24_0);
			pStatement.setString(1, commentTargetCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CommentGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CommentGroupAlarmVDTO> searchUncheckGroupAlarmCode(String groupAlarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CommentGroupAlarmVDTO> dtoList = new ArrayList<CommentGroupAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMENTGROUPALARMV_SQL34_0);
			pStatement.setString(1, groupAlarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CommentGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CommentGroupAlarmVDTO> searchUnCheckDate() throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CommentGroupAlarmVDTO> dtoList = new ArrayList<CommentGroupAlarmVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMENTGROUPALARMV_SQL4_0);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CommentGroupAlarmVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	private List<CommentGroupAlarmVDTO> makeDTOList(ResultSet rs, List<CommentGroupAlarmVDTO> dtoList) throws SQLException{
		
		if(dtoList==null){dtoList = new ArrayList<CommentGroupAlarmVDTO>();}
		while(rs.next())
		{
			String alarmCode = rs.getString("COMMENTCODE");
			if(rs.wasNull()){
				return null;
			}
			dtoList.add(new CommentGroupAlarmVDTO(alarmCode, rs.getString("COMMENTTARGETCODE"), rs.getString("GROUPALARMCODE"), rs.getString("SENDDATE")));
		}
		return dtoList;
	}
	
	private CommentGroupAlarmVDTO makeDTO(ResultSet rs, CommentGroupAlarmVDTO dto) throws SQLException{
		
		System.out.println("makeDTO...");
		dto=null;
		while(rs.next())
		{
			String alarmCode = rs.getString("COMMENTCODE");
			if(rs.wasNull()){
				return null;
			}
			dto = new CommentGroupAlarmVDTO(alarmCode, rs.getString("COMMENTTARGETCODE"), rs.getString("GROUPALARMCODE"), rs.getString("SENDDATE"));
		}
		return dto;
	}
}
