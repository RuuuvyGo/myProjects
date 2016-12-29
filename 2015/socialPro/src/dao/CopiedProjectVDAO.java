package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import socialProExceptions.DAOException;
import dto.CopiedProjectVDTO;
import dto.OriginProjectVDTO;

public class CopiedProjectVDAO extends BaseDAO{

	private static final String SELECT_COPIEDPROJECT_SQL1 = "SELECT DISTINCT *  FROM COPIEDPROJECT_VIEW where COPIEDPROJECTCODE=?";
	private static final String SELECT_COPIEDPROJECT_SQL2 = "SELECT DISTINCT *  FROM COPIEDPROJECT_VIEW where COPIEDFOLDERCODE=?";
	private static final String SELECT_COPIEDPROJECT_SQL3 = "SELECT DISTINCT *  FROM COPIEDPROJECT_VIEW where COPIEDPATH=?";
	private static final String SELECT_COPIEDPROJECT_SQL4 = "SELECT DISTINCT *  FROM COPIEDPROJECT_VIEW where COPIEDOWNERCODE=?";
	private static final String SELECT_COPIEDPROJECT_SQL45 = "SELECT DISTINCT *  FROM COPIEDPROJECT_VIEW where COPIEDOWNERCODE=? and ORIGINPROJECTCODE=?";
	private static final String SELECT_COPIEDPROJECT_SQL48 = "SELECT DISTINCT *  FROM COPIEDPROJECT_VIEW where COPIEDOWNERCODE=? and ORIGINOWNERCODE=?";
	private static final String SELECT_COPIEDPROJECT_SQL5 = "SELECT DISTINCT *  FROM COPIEDPROJECT_VIEW where ORIGINPROJECTCODE=?";
	private static final String SELECT_COPIEDPROJECT_SQL54 = "SELECT DISTINCT *  FROM COPIEDPROJECT_VIEW where ORIGINPROJECTCODE=? and COPIEDOWNERCODE=?";
	private static final String SELECT_COPIEDPROJECT_SQL6 = "SELECT DISTINCT *  FROM COPIEDPROJECT_VIEW where ORIGINFOLDERCODE=?";
	private static final String SELECT_COPIEDPROJECT_SQL7 = "SELECT DISTINCT *  FROM COPIEDPROJECT_VIEW where ORIGINPATH=?";
	private static final String SELECT_COPIEDPROJECT_SQL8 = "SELECT DISTINCT *  FROM COPIEDPROJECT_VIEW where ORIGINOWNERCODE=?";
	private static final String SELECT_COPIEDPROJECT_SQL8_ORI = "SELECT DISTINCT  ORIGINPROJECTCODE,ORIGINFOLDERCODE,ORIGINPATH,ORIGINOWNERCODE FROM COPIEDPROJECT_VIEW where ORIGINOWNERCODE=?";
	
	
	public CopiedProjectVDTO searchCopiedProjectCode(String copiedProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		CopiedProjectVDTO dto = new CopiedProjectVDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDPROJECT_SQL1);
			pStatement.setString(1,copiedProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public CopiedProjectVDTO searchCopiedFolderCode(String copiedFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		CopiedProjectVDTO dto = new CopiedProjectVDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDPROJECT_SQL2);
			pStatement.setString(1,copiedFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public CopiedProjectVDTO searchCopiedFolderPath(String copiedFolderPath) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		CopiedProjectVDTO dto = new CopiedProjectVDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDPROJECT_SQL3);
			pStatement.setString(1,copiedFolderPath);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedProjectVDTO> searchCopiedFolderOwner(String copiedFolderOwner) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedProjectVDTO> dtoList = new ArrayList<CopiedProjectVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDPROJECT_SQL4);
			pStatement.setString(1,copiedFolderOwner);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedProjectVDTO> searchCopiedMemFolderOwner(String copiedFolderOwner) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedProjectVDTO> dtoList = new ArrayList<CopiedProjectVDTO>();
		String sql = SELECT_COPIEDPROJECT_SQL4+" and originOwnerCode like 'member%'";
		System.out.println(sql);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,copiedFolderOwner);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	public List<CopiedProjectVDTO> searchCopiedTeamFolderOwner(String copiedFolderOwner) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedProjectVDTO> dtoList = new ArrayList<CopiedProjectVDTO>();
		String sql = SELECT_COPIEDPROJECT_SQL4+" and originOwnerCode like 'team%'";
		System.out.println(sql);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,copiedFolderOwner);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	public CopiedProjectVDTO searchCopiedFolderOwner(String copiedProjectOwner,String orginProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		CopiedProjectVDTO dto=null; 
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDPROJECT_SQL45);
			pStatement.setString(1,copiedProjectOwner);
			pStatement.setString(2,orginProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedProjectVDTO> searchOwnerCode(String oriOwnerCode,String copiedOwnerCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedProjectVDTO> dtoList = new ArrayList<CopiedProjectVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDPROJECT_SQL48);
			pStatement.setString(1,copiedOwnerCode);
			pStatement.setString(2,oriOwnerCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedProjectVDTO> searchOriginProjectCode(String oriProjectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedProjectVDTO> dtoList = new ArrayList<CopiedProjectVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDPROJECT_SQL5);
			pStatement.setString(1,oriProjectCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public CopiedProjectVDTO searchOriginProjectCode(String oriProjectCode,String copiedOwnerCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		CopiedProjectVDTO dto=null; 
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDPROJECT_SQL54);
			pStatement.setString(1,oriProjectCode);
			pStatement.setString(2,copiedOwnerCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	
	public List<CopiedProjectVDTO> searchOriginFolderCode(String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedProjectVDTO> dtoList = new ArrayList<CopiedProjectVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDPROJECT_SQL6);
			pStatement.setString(1,originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedProjectVDTO> searchOriginFolderPath(String originFolderPath) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedProjectVDTO> dtoList = new ArrayList<CopiedProjectVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDPROJECT_SQL7);
			pStatement.setString(1,originFolderPath);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedProjectVDTO> searchOriginFolderOwner(String originOwnerCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedProjectVDTO> dtoList = new ArrayList<CopiedProjectVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDPROJECT_SQL8);
			pStatement.setString(1,originOwnerCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<OriginProjectVDTO> searchOriginOwner(String originOwnerCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<OriginProjectVDTO> dtoList = new ArrayList<OriginProjectVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDPROJECT_SQL8_ORI);
			pStatement.setString(1,originOwnerCode);
			rs = pStatement.executeQuery();
			return this.makeOriDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	private List<OriginProjectVDTO> makeOriDTOList(ResultSet rs, List<OriginProjectVDTO> dtoList) throws SQLException{
		
		while(rs.next())
		{
			String code = rs.getString("ORIGINPROJECTCODE");
			if(rs.wasNull()){
				return dtoList;
			}
			OriginProjectVDTO dto = new OriginProjectVDTO(code, rs.getString("ORIGINFOLDERCODE"), rs.getString("ORIGINPATH"), rs.getString("ORIGINOWNERCODE"));
			dtoList.add(dto);
		}
		return dtoList;
	}
	
	private List<CopiedProjectVDTO> makeDTOList(ResultSet rs, List<CopiedProjectVDTO> dtoList) throws SQLException{
		
		while(rs.next())
		{
			String code = rs.getString("COPIEDPROJECTCODE");
			if(rs.wasNull()){
				return dtoList;
			}
			dtoList.add(new CopiedProjectVDTO(code, rs.getString("COPIEDFOLDERCODE"), rs.getString("COPIEDPATH"), rs.getString("COPIEDOWNERCODE"), rs.getString("ORIGINPROJECTCODE"), rs.getString("ORIGINFOLDERCODE"), rs.getString("ORIGINPATH"), rs.getString("ORIGINOWNERCODE")));
		}
		return dtoList;
	}
	
	private CopiedProjectVDTO makeDTO(ResultSet rs, CopiedProjectVDTO dto) throws SQLException{
		System.out.println(" CopiedProjectDAO makeDTO...");
		dto=null;
		while(rs.next())
		{
			String code = rs.getString("COPIEDPROJECTCODE");
			if(rs.wasNull()){
				return dto;
			}
			dto = new CopiedProjectVDTO(code, rs.getString("COPIEDFOLDERCODE"), rs.getString("COPIEDPATH"), rs.getString("COPIEDOWNERCODE"), rs.getString("ORIGINPROJECTCODE"), rs.getString("ORIGINFOLDERCODE"), rs.getString("ORIGINPATH"), rs.getString("ORIGINOWNERCODE"));
		}
		return dto;
	}
}
