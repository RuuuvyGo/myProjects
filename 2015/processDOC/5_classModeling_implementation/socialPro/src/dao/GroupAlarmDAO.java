package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import socialProExceptions.DAOException;
import dto.AlarmDTO;
import dto.GroupAlarmDTO;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : AlarmDAO.java
//  @ Date : 2015-07-28
//  @ Author : 
//
//




public class GroupAlarmDAO extends BaseDAO{
	
	private static final String GROUPALARM_SEQ = 
			"select groupalarm_seq.nextval from dual";
	private static final String INSERT_GROUPALARM_SQL = 
			"insert into GROUPALARM_TB (GROUPALARMCODE,TARGETCODE,MEMBERCODE) values(?,?,?)";
	private static final String INSERT_GROUPALARMList_SQL = 
			" into GROUPALARM_TB (GROUPALARMCODE,TARGETCODE,MEMBERCODE) values(?,?,?)";
	
	private static final String UPDATE_GROUPALARM_SQL41 = "update GROUPALARM_TB set checkDate=to_timeStamp(?,'yyyy-mm-dd hh24:mi:ss.ff3') where GROUPALARMCODE=?";
	
	private static final String SELECT_GROUPALARM_SQL1 = "select GROUPALARMCODE,TARGETCODE,GROUPALARMCODE, to_char(sendDate,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE FROM GROUPALARM_TB where ALARMCODE=?";
	private static final String SELECT_GROUPALARM_SQL2 = "select GROUPALARMCODE,TARGETCODE,GROUPALARMCODE, to_char(sendDate,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE FROM GROUPALARM_TB where TARGETCODE=?";
	private static final String SELECT_GROUPALARM_SQL3 = "select GROUPALARMCODE,TARGETCODE,GROUPALARMCODE, to_char(sendDate,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE FROM GROUPALARM_TB where MEMBERCODE=?";
	private static final String SELECT_GROUPALARM_SQL4 = "select GROUPALARMCODE,TARGETCODE,GROUPALARMCODE, to_char(sendDate,'yyyy-mm-dd hh24:mi:ss.ff3') as SENDDATE FROM GROUPALARM_TB where CHECKDATE=to_timeStamp(?,'yyyy-mm-dd hh24:mi:ss.ff3')";
	
///////////////////////////////////////  insert   ////////////////////////////////////////////////////////	
	
	private String makeCode() throws DAOException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement pStatement = null;
		String code=null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(GROUPALARM_SEQ);
			ResultSet rs = pStatement.executeQuery();
			if(rs.next()){
				code=new String();
				code="groupAlarm_"+rs.getString("nextval");
			}
			System.out.println(code);
		
		} catch (SQLException e) {
			throw new DAOException("Error makeCode GroupAlarmDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
		return code;
	}
	
	
	public String insertGroupAlarm(GroupAlarmDTO dto) throws DAOException {
	
		//ALARMCODE,SENDERCODE,RECEIVERCODE,TYPE, TITLE, CONTENT, SENDDATE, SOURCECODE
		
		System.out.println("insert groupAlarm start");
		Connection connection = null;
		PreparedStatement pStatement = null;
		String code=this.makeCode();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(INSERT_GROUPALARM_SQL);
			pStatement.setString(1, code);
			pStatement.setString(2, dto.getTargetCode()); 
			pStatement.setString(3, dto.getMemberCode()); 
			if(pStatement.executeUpdate()==1)return code;
		} catch (SQLException e) {
			throw new DAOException("Error adding groupAlarmDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
		return null;
	}
	
	public String insertGroupAlarm(List<GroupAlarmDTO> dtoList) throws DAOException {
		
		//ALARMCODE,SENDERCODE,RECEIVERCODE,TYPE, TITLE, CONTENT, SENDDATE, SOURCECODE
		
		System.out.println("insert groupAlarm start");
		Connection connection = null;
		PreparedStatement pStatement = null;
		String code=this.makeCode();
		String sql = "insert all ";
		for(GroupAlarmDTO g : dtoList){
			sql+="into GroupAlarm_tb(GROUPALARMCODE,TARGETCODE,MEMBERCODE) values ('"+code+"','"+g.getTargetCode()+"','"+g.getMemberCode()+"') ";
			g.setGroupAlarmCode(code);
		}sql+="select * from dual";
		System.out.println(sql);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			if(pStatement.executeUpdate()==dtoList.size())return code;
		} catch (SQLException e) {
			throw new DAOException("Error adding groupAlarmDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
		return null;
	}

///////////////////////////////////////  search   ////////////////////////////////////////////////////////
	
	public GroupAlarmDTO searchGroupAlarmCode(String groupAlarmCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		GroupAlarmDTO dto = new GroupAlarmDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_GROUPALARM_SQL1);
			pStatement.setString(1,groupAlarmCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search groupAlarmDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<GroupAlarmDTO> searchTargetCode(String targetCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<GroupAlarmDTO> dtoList = new ArrayList<GroupAlarmDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_GROUPALARM_SQL2);
			pStatement.setString(1,targetCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search groupAlarmDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	
	public List<GroupAlarmDTO> searchMemberCode(String memberCode) throws DAOException{
			
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<GroupAlarmDTO> dtoList = new ArrayList<GroupAlarmDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_GROUPALARM_SQL3);
			pStatement.setString(1,memberCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search groupAlarmDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<GroupAlarmDTO> searchCheckDate(String checkDate) throws DAOException{
			
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<GroupAlarmDTO> dtoList = new ArrayList<GroupAlarmDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_GROUPALARM_SQL4);
			pStatement.setString(1,checkDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search groupAlarmDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

///////////////////////////////////////  delete   ////////////////////////////////////////////////////////	
	
	
///////////////////////////////////////  update   ////////////////////////////////////////////////////////	
	
	public boolean updateChecks(String groupAlarmCode, String checkDate)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(UPDATE_GROUPALARM_SQL41);
			pStatement.setString(1, checkDate);
			pStatement.setString(2, groupAlarmCode);
			if(pStatement.executeUpdate()==0)return false;
			return true;
		} catch (SQLException e) {
			throw new DAOException("Error updating groupAlarm. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	private List<GroupAlarmDTO> makeDTOList(ResultSet rs, List<GroupAlarmDTO> dtoList) throws SQLException{
		
		if(dtoList==null){dtoList = new ArrayList<GroupAlarmDTO>();}
		while(rs.next())
		{
			String alarmCode = rs.getString("GROUPALARMCODE");
			if(rs.wasNull()){
				return null;
			}
			dtoList.add(new GroupAlarmDTO(alarmCode, rs.getString("TARGETCODE"), rs.getString("MEMBERCODE"),rs.getString("CHECKDATE")));
		}
		return dtoList;
	}
	
	private GroupAlarmDTO makeDTO(ResultSet rs, GroupAlarmDTO dto) throws SQLException{
		
		System.out.println("makeDTO...");
		dto=null;
		while(rs.next())
		{
			String alarmCode = rs.getString("GROUPALARMCODE");
			if(rs.wasNull()){
				return null;
			}
			dto = new GroupAlarmDTO(alarmCode, rs.getString("TARGETCODE"), rs.getString("MEMBERCODE"),rs.getString("CHECKDATE"));
		}
		return dto;
	}
}
