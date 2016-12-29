package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import socialProExceptions.DAOException;
import dto.CommitMessageDTO;
import dto.CommitMessageDTO;

public class CommitMessageDAO extends BaseDAO{

	private static final String INSERT_COMMITALARM_SQL = "insert into COMMITMESSAGE_TB (MESSAGECODE,COMMITCODE) values(?,?)";
	
	private static final String UPDATE_COMMITALARM_SQL1 = "update COMMITALARM_TB set checkDate=to_timeStamp(?,'yyyy-mm-dd hh24:mi:ss.ff3') where MESSAGECODE=?";
	
	private static final String SELECT_COMMITALARM_SQL1 = "select MESSAGECODE,COMMITCODE FROM COMMITALARM_TB where MESSAGECODE=?";
	private static final String SELECT_COMMITALARM_SQL2 = "select MESSAGECODE,COMMITCODE FROM COMMITALARM_TB	where COMMITCODE=?";
	
	
	private static final String DELETE_COMMITALARM_SQL1 = "DELETE FROM COMMITMESSAGE_TB WHERE  MESSAGECODE=?";
	
///////////////////////////////////////  insert   ////////////////////////////////////////////////////////	
	
	public boolean insertCommitMessage(CommitMessageDTO dto) throws DAOException {
		
		System.out.println("insert Commit start");
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(INSERT_COMMITALARM_SQL);
			pStatement.setString(1, dto.getMessageCode());
			pStatement.setString(2, dto.getCommitCode());
			if(pStatement.executeUpdate()==1)return true;
		} catch (SQLException e) {
			throw new DAOException("Error adding CommitMessageDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
		return false;
	}

///////////////////////////////////////  search   ////////////////////////////////////////////////////////
	
	
	
	public List<CommitMessageDTO> searchMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CommitMessageDTO> dtoList = new ArrayList<CommitMessageDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMITALARM_SQL1);
			pStatement.setString(1,messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CommitMessageDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CommitMessageDTO> searchNewAlarmCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CommitMessageDTO> dtoList = new ArrayList<CommitMessageDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMITALARM_SQL2);
			pStatement.setString(1,commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CommitMessageDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	
///////////////////////////////////////  update   ////////////////////////////////////////////////////////	
	
	public int updateChecks(String alarmCode,String checkDate)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(UPDATE_COMMITALARM_SQL1);
			pStatement.setString(1, checkDate);
			pStatement.setString(2, alarmCode);
			return pStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error updating CommitMessageDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	
	
///////////////////////////////////////// end ////////////////////////////////////////////////////	

	private List<CommitMessageDTO> makeDTOList(ResultSet rs, List<CommitMessageDTO> dtoList) throws SQLException{
		
		if(dtoList==null){dtoList = new ArrayList<CommitMessageDTO>();}
		while(rs.next())
		{
			String alarmCode = rs.getString("MESSAGECODE");
			if(rs.wasNull()){
				return null;
			}
			dtoList.add(new CommitMessageDTO(alarmCode, rs.getString("COMMITCODE")));
		}
		return dtoList;
	}
	
	private CommitMessageDTO makeDTO(ResultSet rs, CommitMessageDTO dto) throws SQLException{
		
		System.out.println("makeDTO...");
		dto=null;
		while(rs.next())
		{
			String alarmCode = rs.getString("MESSAGECODE");
			if(rs.wasNull()){
				return null;
			}
			dto = new CommitMessageDTO(alarmCode, rs.getString("COMMITCODE"));
		}
		return dto;
	}
}
