package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import socialProExceptions.DAOException;
import dto.CopiedFileVDTO;

public class CopiedFileVDAO extends BaseDAO{

	private static final String SELECT_COPIEDFILE_SQL1 = "SELECT DISTINCT *  FROM COPIEDFILE_VIEW where COPIEDFILECODE=?";
	private static final String SELECT_COPIEDFILE_SQL2 = "SELECT DISTINCT *  FROM COPIEDFILE_VIEW where COPIEDPATH=?";
	private static final String SELECT_COPIEDFILE_SQL2_ = "SELECT DISTINCT *  FROM COPIEDFILE_VIEW where COPIEDPATH like ";
	private static final String SELECT_COPIEDFILE_SQL3 = "SELECT DISTINCT *  FROM COPIEDFILE_VIEW where COPIEDFOLDERCODE=?";
	private static final String SELECT_COPIEDFILE_SQL4 = "SELECT DISTINCT *  FROM COPIEDFILE_VIEW where COPIEDOWNERCODE=?";
	private static final String SELECT_COPIEDFILE_SQL5 = "SELECT DISTINCT *  FROM COPIEDFILE_VIEW where ORIGINFILECODE=?";
	private static final String SELECT_COPIEDFILE_SQL6 = "SELECT DISTINCT *  FROM COPIEDFILE_VIEW where ORIGINPATH=?";
	private static final String SELECT_COPIEDFILE_SQL7 = "SELECT DISTINCT *  FROM COPIEDFILE_VIEW where ORIGINFOLDERCODE=?";
	private static final String SELECT_COPIEDFILE_SQL8 = "SELECT DISTINCT *  FROM COPIEDFILE_VIEW where ORIGINOWNERCODE=?";
	
	
	
	public CopiedFileVDTO searchCopiedFileCode(String copiedFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		CopiedFileVDTO dto = new CopiedFileVDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDFILE_SQL1);
			pStatement.setString(1,copiedFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
			
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileVDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public CopiedFileVDTO searchCopiedFilePath(String copiedFilePath) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		CopiedFileVDTO dto = new CopiedFileVDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDFILE_SQL2);
			pStatement.setString(1,copiedFilePath);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
			
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileVDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileVDTO> searchAllCopiedFileByPath(String copiedFilePath) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFileVDTO> dtoList = new ArrayList<CopiedFileVDTO>();
		String sql = SELECT_COPIEDFILE_SQL2_+" '"+copiedFilePath+"\\%'";
		System.out.println(sql);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileVDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileVDTO> searchCopiedFolderCode(String copiedFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFileVDTO> dtoList = new ArrayList<CopiedFileVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDFILE_SQL3);
			pStatement.setString(1,copiedFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileVDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileVDTO> searchCopiedOwnerCode(String copiedOwnerCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFileVDTO> dtoList = new ArrayList<CopiedFileVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDFILE_SQL4);
			pStatement.setString(1,copiedOwnerCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileVDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileVDTO> searchOriginFileCode(String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFileVDTO> dtoList = new ArrayList<CopiedFileVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDFILE_SQL5);
			pStatement.setString(1,originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileVDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileVDTO> searchOriginFilePath(String originFilePath) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFileVDTO> dtoList = new ArrayList<CopiedFileVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDFILE_SQL6);
			pStatement.setString(1,originFilePath);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileVDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CopiedFileVDTO> searchOriginFolderCode(String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFileVDTO> dtoList = new ArrayList<CopiedFileVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDFILE_SQL7);
			pStatement.setString(1,originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileVDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFileVDTO> searchOriginOwnerCode(String originOwnerCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFileVDTO> dtoList = new ArrayList<CopiedFileVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDFILE_SQL8);
			pStatement.setString(1,originOwnerCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileVDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	private List<CopiedFileVDTO> makeDTOList(ResultSet rs, List<CopiedFileVDTO> dtoList) throws SQLException{
		
		while(rs.next())
		{
			CopiedFileVDTO dto = new CopiedFileVDTO();
			dto = new CopiedFileVDTO();
			dto.setCopiedFileCode(rs.getString("COPIEDFILECODE"));
			if(rs.wasNull()){
				return null;
			}
			dto.setCopiedPath(rs.getString("COPIEDPATH"));
			dto.setCopiedFolderCode(rs.getString("COPIEDFOLDERCODE"));
			dto.setCopiedOwnerCode(rs.getString("COPIEDOWNERCODE"));
			dto.setOriginFileCode(rs.getString("ORIGINFILECODE"));
			dto.setOriginPath(rs.getString("ORIGINPATH"));
			dto.setOriginFolderCode(rs.getString("ORIGINFOLDERCODE"));
			dto.setOriginOwnerCode(rs.getString("ORIGINOWNERCODE"));
			dtoList.add(dto);
		}
		return dtoList;
	}
	
	private CopiedFileVDTO makeDTO(ResultSet rs, CopiedFileVDTO dto) throws SQLException{

		dto=null;
		while(rs.next())
		{
			dto = new CopiedFileVDTO();
			dto.setCopiedFileCode(rs.getString("COPIEDFILECODE"));
			if(rs.wasNull()){
				return null;
			}
			dto.setCopiedPath(rs.getString("COPIEDPATH"));
			dto.setCopiedFolderCode(rs.getString("COPIEDFOLDERCODE"));
			dto.setCopiedOwnerCode(rs.getString("COPIEDOWNERCODE"));
			dto.setOriginFileCode(rs.getString("ORIGINFILECODE"));
			dto.setOriginPath(rs.getString("ORIGINPATH"));
			dto.setOriginFolderCode(rs.getString("ORIGINFOLDERCODE"));
			dto.setOriginOwnerCode(rs.getString("ORIGINOWNERCODE"));
		}
		return dto;
	}
}
