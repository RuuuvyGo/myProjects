package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.CopiedInfoDTO;
import dto.FileDTO;

import socialProExceptions.DAOException;

public class CopiedInfoDAO extends BaseDAO{

	private static final String INSERT_COPIEDINFO_SQL = "insert into COPIEDINFO_TB (COPIEDINFOCODE, ORIGININFOCODE) values (?,?)";
	
	private static final String UPDATE_COPIEDINFO_SQL1 = "update COPIEDINFO_TB set COPIEDINFOCODE=? where COPIEDINFOCODE=?";
	private static final String UPDATE_COPIEDINFO_SQL2 = "update COPIEDINFO_TB set ORIGININFOCODE=? where ORIGININFOCODE=?";
	
	
	private static final String SELECT_COPIEDINFO_SQL1 = "select * FROM COPIEDINFO_TB where COPIEDINFOCODE=?";
	private static final String SELECT_COPIEDINFO_SQL2 = "select * FROM COPIEDINFO_TB where ORIGININFOCODE=?";
	
	private static final String DELETE_COPIEDINFO_SQL1 = "DELETE FROM COPIEDINFO_TB WHERE  COPIEDINFOCODE=?";
	private static final String DELETE_COPIEDINFO_SQL2 = "DELETE FROM COPIEDINFO_TB WHERE  ORIGININFOCODE=?";
	
	public int insertCopiedInfo(CopiedInfoDTO dto) throws DAOException {
		
		System.out.println("insert copiedinfo start");
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(INSERT_COPIEDINFO_SQL);
			
			pStatement.setString(1, dto.getCopiedInfoCode());
			pStatement.setString(2, dto.getOriginInfoCode());
			
			return pStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error adding CopiedInfoDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	public CopiedInfoDTO searchCopiedCode(String copiedCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		CopiedInfoDTO dto = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDINFO_SQL1);
			pStatement.setString(1,copiedCode);
			ResultSet rs = pStatement.executeQuery();
			
			while(rs.next()){
				dto=new CopiedInfoDTO();
				dto.setCopiedInfoCode(rs.getString("COPIEDINFOCODE"));
				if(rs.wasNull()){return null;}
				dto.setOriginInfoCode(rs.getString("ORIGININFOCODE"));
			}
			return dto;
			
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedInfoDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	
	public List<CopiedInfoDTO> searchOriginCode(String originCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedInfoDTO> dtoList = new ArrayList<CopiedInfoDTO>();
		ResultSet rs=null;
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDINFO_SQL2);
			pStatement.setString(1,originCode);
			rs = pStatement.executeQuery();

			while(rs.next()){
				CopiedInfoDTO dto=new CopiedInfoDTO();
				dto.setCopiedInfoCode(rs.getString("COPIEDINFOCODE"));
				if(rs.wasNull()){return null;}
				dto.setOriginInfoCode(rs.getString("ORIGININFOCODE"));
				
				dtoList.add(dto);
			}
			return dtoList;
			
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedInfoDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public boolean deleteCopiedCode(String copiedCode)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(DELETE_COPIEDINFO_SQL1);
			pStatement.setString(1, copiedCode);
			if(pStatement.executeUpdate()==1)return true;
			return false;
		} catch (SQLException e) {
			throw new DAOException("Error Delete CopiedInfoDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	public int deleteOriginCode(String originCode)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(DELETE_COPIEDINFO_SQL2);
			pStatement.setString(1, originCode);
			return pStatement.executeUpdate();
			
		} catch (SQLException e) {
			throw new DAOException("Error Delete CopiedInfoDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	public boolean updateCopiedCode(String oldCopiedCode,String newCopiedCode)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(UPDATE_COPIEDINFO_SQL1);
			pStatement.setString(1, newCopiedCode);
			pStatement.setString(2, oldCopiedCode);
			if(pStatement.executeUpdate()==0)return false;
			return true;
		} catch (SQLException e) {
			throw new DAOException("Error updating CopiedInfoDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	public int updateOriginCode(String oldOriginCode,String newOriginCode)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(UPDATE_COPIEDINFO_SQL2);
			pStatement.setString(1, newOriginCode);
			pStatement.setString(2, oldOriginCode);
			return  pStatement.executeUpdate();
			
		} catch (SQLException e) {
			throw new DAOException("Error updating CopiedInfoDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
}
