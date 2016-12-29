package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import socialProExceptions.DAOException;
import dto.CommitMessageVDTO;

public class CommitMessageVDAO extends BaseDAO{


	private static final String SELECT_COMMITMESSAGEV_SQL1 = "select MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,COMMITCODE from COMMITMESSAGE_VIEW where MESSAGECODE=?";
	private static final String SELECT_COMMITMESSAGEV_SQL123 = "select MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,COMMITCODE from COMMITMESSAGE_VIEW where MESSAGECODE=? and (SENDERCODE=? or RECEIVERCODE=?)";
	private static final String SELECT_COMMITMESSAGEV_SQL2 = "select MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,COMMITCODE from COMMITMESSAGE_VIEW where SENDERCODE=?";
	private static final String SELECT_COMMITMESSAGEV_SQL25 = "select MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,COMMITCODE from COMMITMESSAGE_VIEW where SENDERCODE=? and COMMITCODE=?";
	private static final String SELECT_COMMITMESSAGEV_SQL23 = "select MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,COMMITCODE from COMMITMESSAGE_VIEW where SENDERCODE=? or RECEIVERCODE=?";
	private static final String SELECT_COMMITMESSAGEV_SQL235 = "select MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,COMMITCODE from COMMITMESSAGE_VIEW where (SENDERCODE=? or RECEIVERCODE=?) and COMMITCODE=?";
	private static final String SELECT_COMMITMESSAGEV_SQL3 = "select MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,COMMITCODE from COMMITMESSAGE_VIEW where RECEIVERCODE=?";
	private static final String SELECT_COMMITMESSAGEV_SQL35 = "select MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,COMMITCODE from COMMITMESSAGE_VIEW where RECEIVERCODE=? and COMMITCODE=?";
	private static final String SELECT_COMMITMESSAGEV_SQL4 = "select MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,COMMITCODE from COMMITMESSAGE_VIEW where SENDDATE=to_timeStamp(?,'yyyy-mm-dd hh24:mi:ss.ff3')";
	private static final String SELECT_COMMITMESSAGEV_SQL5 = "select MESSAGECODE,SENDERCODE,RECEIVERCODE,TITLE,CONTENT,to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,COMMITCODE from COMMITMESSAGE_VIEW where COMMITCODE=?";
	
	
	public List<CommitMessageVDTO> searchMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CommitMessageVDTO> dtoList = new ArrayList<CommitMessageVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMITMESSAGEV_SQL1);
			pStatement.setString(1, messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CommitMessageVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CommitMessageVDTO> searchMessageCode(String messageCode,String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CommitMessageVDTO> dtoList = new ArrayList<CommitMessageVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMITMESSAGEV_SQL123);
			pStatement.setString(1, messageCode);
			pStatement.setString(2, senderCode);
			pStatement.setString(3, receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CommitMessageVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CommitMessageVDTO> searchSenderCode(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CommitMessageVDTO> dtoList = new ArrayList<CommitMessageVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMITMESSAGEV_SQL2);
			pStatement.setString(1, senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CommitMessageVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CommitMessageVDTO> searchSenderCode(String senderCode,String sourceCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CommitMessageVDTO> dtoList = new ArrayList<CommitMessageVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMITMESSAGEV_SQL25);
			pStatement.setString(1, senderCode);
			pStatement.setString(2, sourceCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CommitMessageVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CommitMessageVDTO> searchSendRec(String senderCode,String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CommitMessageVDTO> dtoList = new ArrayList<CommitMessageVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMITMESSAGEV_SQL23);
			pStatement.setString(1, senderCode);
			pStatement.setString(2, receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CommitMessageVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CommitMessageVDTO> searchSendRec(String senderCode,String receiverCode,String sourceCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CommitMessageVDTO> dtoList = new ArrayList<CommitMessageVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMITMESSAGEV_SQL235);
			pStatement.setString(1, senderCode);
			pStatement.setString(2, receiverCode);
			pStatement.setString(3, sourceCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CommitMessageVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	public List<CommitMessageVDTO> searchReceiverCode(String receiverCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CommitMessageVDTO> dtoList = new ArrayList<CommitMessageVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMITMESSAGEV_SQL3);
			pStatement.setString(1, receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CommitMessageVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CommitMessageVDTO> searchReceiverCode(String receiverCode,String sourceCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CommitMessageVDTO> dtoList = new ArrayList<CommitMessageVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMITMESSAGEV_SQL35);
			pStatement.setString(1, receiverCode);
			pStatement.setString(2, sourceCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CommitMessageVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	public List<CommitMessageVDTO> searchSendDate(String sendDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CommitMessageVDTO> dtoList = new ArrayList<CommitMessageVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMITMESSAGEV_SQL4);
			pStatement.setString(1, sendDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CommitMessageVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CommitMessageVDTO> searchCommitCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CommitMessageVDTO> dtoList = new ArrayList<CommitMessageVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COMMITMESSAGEV_SQL5);
			pStatement.setString(1, commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CommitMessageVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}



	private List<CommitMessageVDTO> makeDTOList(ResultSet rs, List<CommitMessageVDTO> dtoList) throws SQLException{
		
		if(dtoList==null){dtoList = new ArrayList<CommitMessageVDTO>();}
		while(rs.next())
		{
			String alarmCode = rs.getString("MESSAGECODE");
			if(rs.wasNull()){
				return null;
			}
			dtoList.add(new CommitMessageVDTO(alarmCode, rs.getString("SENDERCODE"), rs.getString("RECEIVERCODE"), rs.getString("TITLE"), rs.getString("CONTENT"), rs.getString("SENDDATE"), rs.getString("COMMITCODE")));
		}
		return dtoList;
	}
	
	private CommitMessageVDTO makeDTO(ResultSet rs, CommitMessageVDTO dto) throws SQLException{
		
		System.out.println("makeDTO...");
		dto=null;
		while(rs.next())
		{
			String alarmCode = rs.getString("MESSAGECODE");
			if(rs.wasNull()){
				return null;
			}
			dto = new CommitMessageVDTO(alarmCode, rs.getString("SENDERCODE"), rs.getString("RECEIVERCODE"), rs.getString("TITLE"), rs.getString("CONTENT"), rs.getString("SENDDATE"), rs.getString("COMMITCODE"));
		}
		return dto;
	}
	
}
