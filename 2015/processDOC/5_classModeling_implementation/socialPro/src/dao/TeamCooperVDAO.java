package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.TeamCooperVDTO;

import socialProExceptions.DAOException;

public class TeamCooperVDAO extends BaseDAO{

	private static final String SELECT_TEAMCOOPERV_SQL1 = "SELECT DISTINCT * FROM teamcooper_view where TEAMCODE=?";
	private static final String SELECT_TEAMCOOPERV_SQL13 = "SELECT DISTINCT * FROM teamcooper_view where TEAMCODE=? and MEMBERCODE=?";
	private static final String SELECT_TEAMCOOPERV_SQL2 = "SELECT DISTINCT * FROM teamcooper_view where TEAMNAME=?";
	private static final String SELECT_TEAMCOOPERV_SQL3 = "SELECT DISTINCT * FROM teamcooper_view where MEMBERCODE=?";
	private static final String SELECT_TEAMCOOPERV_SQL4 = "SELECT DISTINCT * FROM teamcooper_view where NICKNAME=?";
	private static final String SELECT_TEAMCOOPERV_SQL4_ = "SELECT DISTINCT * FROM teamcooper_view where NICKNAME like ?%";
	private static final String SELECT_TEAMCOOPERV_SQL_4 = "SELECT DISTINCT * FROM teamcooper_view where NICKNAME like %?";
	private static final String SELECT_TEAMCOOPERV_SQL5 = "SELECT DISTINCT * FROM teamcooper_view where EMAIL=? ";
	
	public List<TeamCooperVDTO> searchTeamCode(String teamCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<TeamCooperVDTO> dtoList=new ArrayList<TeamCooperVDTO>();
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_TEAMCOOPERV_SQL1);
			pStatement.setString(1,teamCode);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				TeamCooperVDTO dto=new TeamCooperVDTO();
				dto.setTeamCode(rs.getString("TEAMCODE"));
				dto.setTeamName(rs.getString("TEAMNAME"));
				dto.setMemberCode(rs.getString("MEMBERCODE"));
				dto.setNickName(rs.getString("NICKNAME"));
				dto.setEmail(rs.getString("EMAIL"));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search TeamCooperVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}
	
	public List<TeamCooperVDTO> searchTeamCode(String teamCode, String memberCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<TeamCooperVDTO> dtoList = new ArrayList<TeamCooperVDTO>();
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_TEAMCOOPERV_SQL13);
			pStatement.setString(1,teamCode);
			pStatement.setString(2,memberCode);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				TeamCooperVDTO dto=new TeamCooperVDTO();
				dto.setTeamCode(rs.getString("TEAMCODE"));
				dto.setTeamName(rs.getString("TEAMNAME"));
				dto.setMemberCode(rs.getString("MEMBERCODE"));
				dto.setNickName(rs.getString("NICKNAME"));
				dto.setEmail(rs.getString("EMAIL"));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search TeamCooperVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}
	
	public List<TeamCooperVDTO> searchTeamName(String teamName) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<TeamCooperVDTO> dtoList=new ArrayList<TeamCooperVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_TEAMCOOPERV_SQL2);
			pStatement.setString(1,teamName);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				TeamCooperVDTO dto=new TeamCooperVDTO();
				dto.setTeamCode(rs.getString("TEAMCODE"));
				dto.setTeamName(rs.getString("TEAMNAME"));
				dto.setMemberCode(rs.getString("MEMBERCODE"));
				dto.setNickName(rs.getString("NICKNAME"));
				dto.setEmail(rs.getString("EMAIL"));
				dtoList.add(dto);
			}
			return dtoList;
			
		} catch (SQLException e) {
			throw new DAOException("Error search TeamCooperVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}
	
	public List<TeamCooperVDTO> searchMCode(String memberCode) throws DAOException{
		System.out.println("Cooper DB");
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<TeamCooperVDTO> dtoList=new ArrayList<TeamCooperVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_TEAMCOOPERV_SQL3);
			pStatement.setString(1,memberCode);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				TeamCooperVDTO dto=new TeamCooperVDTO();
				dto.setTeamCode(rs.getString("TEAMCODE"));
				dto.setTeamName(rs.getString("TEAMNAME"));
				dto.setMemberCode(rs.getString("MEMBERCODE"));
				dto.setNickName(rs.getString("NICKNAME"));
				dto.setEmail(rs.getString("EMAIL"));
				dtoList.add(dto);
			}
			return dtoList;
			
		} catch (SQLException e) {
			throw new DAOException("Error search TeamCooperVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}
	
	
	public List<TeamCooperVDTO> searchMNickName(String nickName) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<TeamCooperVDTO> dtoList=new ArrayList<TeamCooperVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_TEAMCOOPERV_SQL4);
			pStatement.setString(1,nickName);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				TeamCooperVDTO dto=new TeamCooperVDTO();
				dto.setTeamCode(rs.getString("TEAMCODE"));
				dto.setTeamName(rs.getString("TEAMNAME"));
				dto.setMemberCode(rs.getString("MEMBERCODE"));
				dto.setNickName(rs.getString("NICKNAME"));
				dto.setEmail(rs.getString("EMAIL"));
				dtoList.add(dto);
			}
			return dtoList;
			
		} catch (SQLException e) {
			throw new DAOException("Error search TeamCooperVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}
	
	public List<TeamCooperVDTO> searchMNickNameAll(String nickName) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<TeamCooperVDTO> dtoList=new ArrayList<TeamCooperVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_TEAMCOOPERV_SQL4);
			pStatement.setString(1,nickName);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				TeamCooperVDTO dto=new TeamCooperVDTO();
				dto.setTeamCode(rs.getString("TEAMCODE"));
				dto.setTeamName(rs.getString("TEAMNAME"));
				dto.setMemberCode(rs.getString("MEMBERCODE"));
				dto.setNickName(rs.getString("NICKNAME"));
				dto.setEmail(rs.getString("EMAIL"));
				dtoList.add(dto);
			}
			
			pStatement = connection.prepareStatement(SELECT_TEAMCOOPERV_SQL_4);
			pStatement.setString(1,nickName);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				TeamCooperVDTO dto=new TeamCooperVDTO();
				dto.setTeamCode(rs.getString("TEAMCODE"));
				dto.setTeamName(rs.getString("TEAMNAME"));
				dto.setMemberCode(rs.getString("MEMBERCODE"));
				dto.setNickName(rs.getString("NICKNAME"));
				dto.setEmail(rs.getString("EMAIL"));
				dtoList.add(dto);
			}
			
			pStatement = connection.prepareStatement(SELECT_TEAMCOOPERV_SQL4_);
			pStatement.setString(1,nickName);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				TeamCooperVDTO dto=new TeamCooperVDTO();
				dto.setTeamCode(rs.getString("TEAMCODE"));
				dto.setTeamName(rs.getString("TEAMNAME"));
				dto.setMemberCode(rs.getString("MEMBERCODE"));
				dto.setNickName(rs.getString("NICKNAME"));
				dto.setEmail(rs.getString("EMAIL"));
				dtoList.add(dto);
			}
			
			
			return dtoList;
			
		} catch (SQLException e) {
			throw new DAOException("Error search TeamCooperVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}
	
	public List<TeamCooperVDTO> searchEamil(String email)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<TeamCooperVDTO> dtoList=new ArrayList<TeamCooperVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_TEAMCOOPERV_SQL5);
			pStatement.setString(1,email);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				TeamCooperVDTO dto=new TeamCooperVDTO();
				dto.setTeamCode(rs.getString("TEAMCODE"));
				dto.setTeamName(rs.getString("TEAMNAME"));
				dto.setMemberCode(rs.getString("MEMBERCODE"));
				dto.setNickName(rs.getString("NICKNAME"));
				dto.setEmail(rs.getString("EMAIL"));
				dtoList.add(dto);
			}
			return dtoList;
			
		} catch (SQLException e) {
			throw new DAOException("Error search TeamCooperVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
}
