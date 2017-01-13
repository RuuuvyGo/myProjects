package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import socialProExceptions.DAOException;
import dto.CooperatorMessageDTO;

public class CooperatorMessageDAO extends BaseDAO{

	private static final String INSERT_COOPERALARM_SQL = "insert into COOPERATORMESSAGE_TB (MESSAGECODE,TYPE,SOURCECODE) values(?,?,?)";
	
	private static final String UPDATE_COOPERALARM_SQL1 = "update COOPERATORMESSAGE_TB set checkDate=to_timeStamp(?,'yyyy-mm-dd hh24:mi:ss.ff3') where ALARMCODE=?";
	
	private static final String SELECT_COOPERALARM_SQL1 = "select MESSAGECODE,TYPE,SOURCECODE from COOPERATORMESSAGE_TB where ALARMCODE=?";
	private static final String SELECT_COOPERALARM_SQL2 = "select MESSAGECODE,TYPE,SOURCECODE from COOPERATORMESSAGE_TB where type=?";
	private static final String SELECT_COOPERALARM_SQL3 = "select MESSAGECODE,TYPE,SOURCECODE from COOPERATORMESSAGE_TB where sourceCode=?";
		
	private static final String DELETE_COOPERALARM_SQL1 = "DELETE FROM COOPERATORMESSAGE_TB WHERE  ALARMCODE=?";
	
	
	
	public boolean insertCooperatorMessage(CooperatorMessageDTO dto) throws DAOException {
		
		System.out.println("insert  CooperatorAlarmDTO start");
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(INSERT_COOPERALARM_SQL);
			pStatement.setString(1, dto.getMessageCode());
			pStatement.setInt(2, dto.getType());
			pStatement.setString(3, dto.getSourceCode());
			if(pStatement.executeUpdate()==1)return true;
		} catch (SQLException e) {
			throw new DAOException("Error adding CooperatorMessageDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
		return false;
	}
	
/////////////////////////////////////////   search   ///////////////////////////////////////////////////////////////////////
	

	public CooperatorMessageDTO searchMessageCode(String messageCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		CooperatorMessageDTO dto = new CooperatorMessageDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERALARM_SQL1);
			pStatement.setString(1,messageCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CooperatorMessageDTO> searchType(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageDTO> dtoList = new ArrayList<CooperatorMessageDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERALARM_SQL2);
			pStatement.setInt(1, type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CooperatorMessageDTO> searchSourceCode(String sourceCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CooperatorMessageDTO> dtoList = new ArrayList<CooperatorMessageDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPERALARM_SQL3);
			pStatement.setString(1,sourceCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CooperatorMessageDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	
///////////////////////////////////////  update   ////////////////////////////////////////////////////////	



///////////////////////////////////////// end ////////////////////////////////////////////////////	
	

	private List<CooperatorMessageDTO> makeDTOList(ResultSet rs, List<CooperatorMessageDTO> dtoList) throws SQLException{
		
		if(dtoList==null){dtoList = new ArrayList<CooperatorMessageDTO>();}
		while(rs.next())
		{
			String alarmCode = rs.getString("ALARMCODE");
			if(rs.wasNull()){
				return null;
			}
			dtoList.add(new CooperatorMessageDTO(alarmCode, rs.getInt("TYPE"), rs.getString("SOURCECODE")));
		}
		return dtoList;
	}
	
	private CooperatorMessageDTO makeDTO(ResultSet rs, CooperatorMessageDTO dto) throws SQLException{
		
		System.out.println("makeDTO...");
		dto=null;
		while(rs.next())
		{
			String alarmCode = rs.getString("ALARMCODE");
			if(rs.wasNull()){
				return null;
			}
			dto = new CooperatorMessageDTO(alarmCode, rs.getInt("TYPE"), rs.getString("SOURCECODE"));
		}
		return dto;
	}
}
