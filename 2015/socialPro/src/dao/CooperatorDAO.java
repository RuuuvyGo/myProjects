package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.CooperatorDTO;
import dto.TeamDTO;

import socialProExceptions.DAOException;

public class CooperatorDAO extends BaseDAO{

	private static final String INSERT_COOPER_SQL = 
			"insert into cooperator_tb(MEMBERCODE, SETCODE) values(?, ?)";
	private static final String INSERT_COOPER_SQLS=
			"insert all ";
	
	/*INSERT ALL
	INTO test_tb(num)
	VALUES (1)
	INTO test_tb(num)
	VALUES (55)
	SELECT * FROM DUAL;*/
	private static final String SELECT_COOPER_SQL1 = "select * FROM cooperator_tb where MEMBERCODE=?";
	private static final String SELECT_COOPER_SQL2 = "select * FROM cooperator_tb where SETCODE=?";
	private static final String SELECT_COOPER_SQL12 = "select * FROM cooperator_tb where MEMBERCODE=? and SETCODE like ?%";
	
	private static final String DELETE_COOPER_SQL1 = "DELETE FROM cooperator_tb WHERE  MEMBERCODE=?";
	private static final String DELETE_COOPER_SQL2 = "DELETE FROM cooperator_tb WHERE  SETCODE=?";
	private static final String DELETE_COOPER_SQL12 = "DELETE FROM cooperator_tb WHERE  MEMBERCODE=? and SETCODE=?";
	
	public boolean insertCooperator(CooperatorDTO dto)throws DAOException{
		
		System.out.println("insert Cooper start");
		Connection connection = null;
		PreparedStatement pStatement = null;

		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(INSERT_COOPER_SQL);
			pStatement.setString(1, dto.getMemberCode());
			pStatement.setString(2, dto.getSetCode());
			
			if(pStatement.executeUpdate()==1)return true;
			return false;
		} catch (SQLException e) {
			throw new DAOException("Error adding Cooper. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}

	}
	
	public boolean insertCooperators(List<String> memberCodes,String setCode)throws DAOException{
		
		System.out.println("insert Cooper start");
		Connection connection = null;
		PreparedStatement pStatement = null;

		try {
			connection = getConnection();
			String sql = INSERT_COOPER_SQLS;
			for(int i=0;i<memberCodes.size();i++){
				sql+="into cooperator_tb (memberCode,setCode) values (?,?) ";
			}sql+="select * from dual";
			pStatement = connection.prepareStatement(sql);
			for(int i=0;i<memberCodes.size();i++){
				pStatement.setString(i*2+1, memberCodes.get(i));
				pStatement.setString(i*2+2, setCode);
			}
			
			if(pStatement.executeUpdate()==memberCodes.size())return true;
			return false;
		} catch (SQLException e) {
			throw new DAOException("Error adding Cooper. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}

	}
	
	public List<CooperatorDTO> searchCooperMCode(String memberCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CooperatorDTO> dtoList=new ArrayList<CooperatorDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPER_SQL1);
			pStatement.setString(1,memberCode);
			ResultSet rs = pStatement.executeQuery();
			while(rs.next())
			{
				CooperatorDTO dto = new CooperatorDTO();
				dto.setMemberCode(rs.getString("MEMBERCODE"));
				dto.setSetCode(rs.getString("SETCODE"));
			}
			
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search Cooper. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	public List<CooperatorDTO> searchCooperSCode(String setCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CooperatorDTO> dtoList=new ArrayList<CooperatorDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPER_SQL2);
			pStatement.setString(1,setCode);
			ResultSet rs = pStatement.executeQuery();
			while(rs.next())
			{
				CooperatorDTO dto = new CooperatorDTO();
				dto.setMemberCode(rs.getString("MEMBERCODE"));
				dto.setSetCode(rs.getString("SETCODE"));
			}
			
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search Cooper. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	public List<CooperatorDTO> searchCooper(String memberCode,String key) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CooperatorDTO> dtoList=new ArrayList<CooperatorDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COOPER_SQL12);
			pStatement.setString(1,memberCode);
			pStatement.setString(2,key);
			ResultSet rs = pStatement.executeQuery();
			while(rs.next())
			{
				CooperatorDTO dto = new CooperatorDTO();
				dto.setMemberCode(rs.getString("MEMBERCODE"));
				dto.setSetCode(rs.getString("SETCODE"));
			}
			
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search Cooper. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	public int dropCooperMCode(String memberCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(DELETE_COOPER_SQL1);
			pStatement.setString(1, memberCode);
			return pStatement.executeUpdate();

			
		} catch (SQLException e) {
			throw new DAOException("Error Delete Cooper. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	public int dropCooperSCode(String setCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(DELETE_COOPER_SQL2);
			pStatement.setString(1, setCode);
			return pStatement.executeUpdate();

			
		} catch (SQLException e) {
			throw new DAOException("Error Delete Cooper. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	public boolean dropCooper(String memberCode,String setCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(DELETE_COOPER_SQL12);
			pStatement.setString(1, memberCode);
			pStatement.setString(2, setCode);
			if(pStatement.executeUpdate()==1)return true;
			return false;
			
		} catch (SQLException e) {
			throw new DAOException("Error Delete Cooper. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
}
