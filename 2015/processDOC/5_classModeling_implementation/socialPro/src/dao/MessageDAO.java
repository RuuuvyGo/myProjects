package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import socialProExceptions.DAOException;
import dto.MessageDTO;

public class MessageDAO extends BaseDAO {

	private static final String MESSAGE_SEQ = 
			"select Message_seq.nextval from dual";
	private static final String INSERT_MESSAGE_SQL = 
			"insert into MESSAGE_tb(MESSAGECODE,SENDERCODE,RECEIVERCODE,SENDDATE,TITLE,CONTENT) values(?,?,?,to_timeStamp(?,'yyyy-mm-dd hh24:mi:ss.ff3'),?,?)";
	
	private static final String UPDATE_MESSAGE_SQL21 = "update MESSAGE_tb set checkDate=to_timeStamp(?,'yyyy-mm-dd hh24:mi:ss.ff3') where MESSAGECODE=?";
	
	private static final String SELECT_MESSAGE_SQL1 = "select MESSAGECODE,SENDERCODE,RECEIVERCODE, to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TITLE,CONTENT FROM MESSAGE_tb where MESSAGECODE=?";
	private static final String SELECT_MESSAGE_SQL2 = "select MESSAGECODE,SENDERCODE,RECEIVERCODE, to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TITLE,CONTENT FROM MESSAGE_tb where SENDERCODE=?";
	private static final String SELECT_MESSAGE_SQL3 = "select MESSAGECODE,SENDERCODE,RECEIVERCODE, to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TITLE,CONTENT FROM MESSAGE_tb where RECEIVERCODE=?";
	private static final String SELECT_MESSAGE_SQL4 = "select MESSAGECODE,SENDERCODE,RECEIVERCODE, to_char(SENDDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE,TITLE,CONTENT FROM MESSAGE_tb where CHECKDATE=to_timeStamp(?,'yyyy-mm-dd hh24:mi:ss.ff3')";

	
	private static final String DELETE_MESSAGE_SQL1 = "DELETE FROM MESSAGE_tb WHERE  MESSAGECODE=?";
	private static final String DELETE_MESSAGE_SQL46 = "DELETE FROM MESSAGE_tb WHERE  SOURCECODE=? and sendDate < to_timeStamp(?, 'YYYY-MM-DD HH24:MI:SS.ff3')";
	private static final String DELETE_MESSAGE_SQL6 = "DELETE FROM MESSAGE_tb WHERE  SOURCECODE=?";


///////////////////////////////////////  insert   ////////////////////////////////////////////////////////	
	
	private String makeCode() throws DAOException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement pStatement = null;
		String code=null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(MESSAGE_SEQ);
			ResultSet rs = pStatement.executeQuery();
			if(rs.next()){
				code=new String();
				code="message_"+rs.getString("nextval");
			}
			System.out.println(code);
		
		} catch (SQLException e) {
			throw new DAOException("Error makeCode MessageDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
		return code;
	}
	
	
	public String insertMesage(MessageDTO dto) throws DAOException {
	
		//ALARMCODE,SENDERCODE,RECEIVERCODE,TYPE, TITLE, CONTENT, SENDDATE, SOURCECODE
		
		System.out.println("insertTeam start");
		Connection connection = null;
		PreparedStatement pStatement = null;
		String code=this.makeCode();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(INSERT_MESSAGE_SQL);
			pStatement.setString(1, code);
			pStatement.setString(2, dto.getSenderCode());
			pStatement.setString(3, dto.getReceiverCode());
			pStatement.setString(4, dto.getSendDate());
			pStatement.setString(5, dto.getTitle());
			pStatement.setString(6, dto.getContent());
			if(pStatement.executeUpdate()==1)return code;
		} catch (SQLException e) {
			throw new DAOException("Error adding MessageDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
		return null;
	}
	
/////////////////////////////////////  search   ////////////////////////////////////////////////////////	
	
	public MessageDTO searchMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		MessageDTO dto = new MessageDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_MESSAGE_SQL1);
			pStatement.setString(1,messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search MessageDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<MessageDTO> searchSenderCode(String senderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<MessageDTO> dtoList = new ArrayList<MessageDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_MESSAGE_SQL2);
			pStatement.setString(1,senderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search MessageDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<MessageDTO> searchReceiverCode(String receiverCode) throws DAOException{
			
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<MessageDTO> dtoList = new ArrayList<MessageDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_MESSAGE_SQL3);
			pStatement.setString(1,receiverCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search MessageDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<MessageDTO> searchCheckDate(String checkDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<MessageDTO> dtoList = new ArrayList<MessageDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_MESSAGE_SQL4);
			pStatement.setString(1,checkDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search MessageDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	private List<MessageDTO> makeDTOList(ResultSet rs, List<MessageDTO> dtoList) throws SQLException{
		
		if(dtoList==null){dtoList = new ArrayList<MessageDTO>();}
		while(rs.next())
		{
			String alarmCode = rs.getString("MESSAGECODE");
			if(rs.wasNull()){
				return null;
			}
			dtoList.add(new MessageDTO(alarmCode, rs.getString("SENDERCODE"), rs.getString("RECEIVERCODE"), rs.getString("SENDDATE"), rs.getString("TITLE"), rs.getString("CONTENT")));
		}
		return dtoList;
	}
	
	private MessageDTO makeDTO(ResultSet rs, MessageDTO dto) throws SQLException{
		
		System.out.println("makeDTO...");
		dto=null;
		while(rs.next())
		{
			String alarmCode = rs.getString("MESSAGECODE");
			if(rs.wasNull()){
				return null;
			}
			dto = new MessageDTO(alarmCode, rs.getString("SENDERCODE"), rs.getString("RECEIVERCODE"), rs.getString("SENDDATE"), rs.getString("TITLE"), rs.getString("CONTENT"));
		}
		return dto;
	}
}
