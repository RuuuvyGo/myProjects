package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import socialProExceptions.DAOException;
import dto.CopiedFolderDTO;

public class CopiedFolderVDAO extends BaseDAO{

	private static final String SELECT_COPIEDFOLDER_SQL1 = "SELECT DISTINCT *  FROM COPIEDFOLDER_VIEW where COPIEDFOLDERCODE=?";
	private static final String SELECT_COPIEDFOLDER_SQL2 = "SELECT DISTINCT *  FROM COPIEDFOLDER_VIEW where COPIEDPATH=?";
	private static final String SELECT_COPIEDFOLDER_SQL2_ = "SELECT DISTINCT *  FROM COPIEDFOLDER_VIEW where COPIEDPATH like ";
	private static final String SELECT_COPIEDFOLDER_SQL3 = "SELECT DISTINCT *  FROM COPIEDFOLDER_VIEW where CPARENTFOLDERCODE=?";
	private static final String SELECT_COPIEDFOLDER_SQL4 = "SELECT DISTINCT *  FROM COPIEDFOLDER_VIEW where COPIEDOWNERCODE=?";
	
	private static final String SELECT_COPIEDFOLDER_SQL5 = "SELECT DISTINCT *  FROM COPIEDFOLDER_VIEW where ORIGINFOLDERCODE=?";
	private static final String SELECT_COPIEDFOLDER_SQL6 = "SELECT DISTINCT *  FROM COPIEDFOLDER_VIEW where ORIGINPATH=?";
	private static final String SELECT_COPIEDFOLDER_SQL7 = "SELECT DISTINCT *  FROM COPIEDFOLDER_VIEW where OPARENTFOLDERCODE=?";
	private static final String SELECT_COPIEDFOLDER_SQL8 = "SELECT DISTINCT *  FROM COPIEDFOLDER_VIEW where ORIGINOWNERCODE=?";
	
	
	public CopiedFolderDTO searchCopiedFolderCode(String copiedFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		CopiedFolderDTO dto = new CopiedFolderDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDFOLDER_SQL1);
			pStatement.setString(1,copiedFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public CopiedFolderDTO searchCopiedFolderPath(String copiedFolderPath) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		CopiedFolderDTO dto = new CopiedFolderDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDFOLDER_SQL2);
			pStatement.setString(1,copiedFolderPath);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderDTO> searchParentCopiedFolderPath(String copiedFolderPath) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFolderDTO> dtoList = new ArrayList<CopiedFolderDTO>();
		String sql =SELECT_COPIEDFOLDER_SQL2_ +" '"+copiedFolderPath+"\\%'";
		System.out.println(sql);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			rs = pStatement.executeQuery();
			if(rs==null)System.out.println("rs is null");
			else System.out.println("rs is NOT    null");
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderVDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFolderDTO> searchCPFolderCode(String cFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFolderDTO> dtoList = new ArrayList<CopiedFolderDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDFOLDER_SQL3);
			pStatement.setString(1,cFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFolderDTO> searchCopiedOwnerCode(String copiedOwnerCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFolderDTO> dtoList = new ArrayList<CopiedFolderDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDFOLDER_SQL4);
			pStatement.setString(1,copiedOwnerCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFolderDTO> searchOriginFolderCode(String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFolderDTO> dtoList = new ArrayList<CopiedFolderDTO>();
		System.out.println(SELECT_COPIEDFOLDER_SQL5);
		System.out.println(originFolderCode);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDFOLDER_SQL5);
			pStatement.setString(1,originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<CopiedFolderDTO> searchOriginFolderPath(String originFolderPath) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFolderDTO> dtoList = new ArrayList<CopiedFolderDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDFOLDER_SQL6);
			pStatement.setString(1,originFolderPath);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
		
	public List<CopiedFolderDTO> searchOParentFolderCode(String oParentFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFolderDTO> dtoList = new ArrayList<CopiedFolderDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDFOLDER_SQL7);
			pStatement.setString(1,oParentFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	
	public List<CopiedFolderDTO> searchOriginOwnerCode(String originOwnerCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<CopiedFolderDTO> dtoList = new ArrayList<CopiedFolderDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDFOLDER_SQL8);
			pStatement.setString(1,originOwnerCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	private List<CopiedFolderDTO> makeDTOList(ResultSet rs, List<CopiedFolderDTO> dtoList) throws SQLException{
		int i=0;
		while(rs.next())
		{
			i++;
			System.out.println("index  "+i);
			CopiedFolderDTO dto = new CopiedFolderDTO();
			String code = rs.getString("COPIEDFOLDERCODE");
			if(code==null){
				System.out.println("code : null.......");
				if(rs.wasNull()){
					System.out.println("makeDTOList  null....mod  :::::   "+dtoList.size());
					return dtoList;
				}else System.out.println("not yet...not null yet");
			}
			else System.out.println("code  ::: "+code);
			dto.setCopiedFolderCode(code);
			dto.setCopiedFolderPath(rs.getString("COPIEDPATH"));
			dto.setcParentFolderCode(rs.getString("CPARENTFOLDERCODE"));
			dto.setCopiedOwnerCode(rs.getString("COPIEDOWNERCODE"));
			dto.setOriginFolderCode(rs.getString("ORIGINFOLDERCODE"));
			dto.setOriginFolderPath(rs.getString("ORIGINPATH"));
			dto.setoParentFolderCode(rs.getString("OPARENTFOLDERCODE"));
			dto.setOriginOwnerCode(rs.getString("ORIGINOWNERCODE"));
			dtoList.add(dto);
		}
		System.out.println("makeDTOList    :::::   "+dtoList.size());
		return dtoList;
	}
	
	private CopiedFolderDTO makeDTO(ResultSet rs, CopiedFolderDTO dto) throws SQLException{
		System.out.println("makeDTO...");
		dto=null;
		while(rs.next())
		{
			dto = new CopiedFolderDTO();
			dto.setCopiedFolderCode(rs.getString("COPIEDFOLDERCODE"));
			if(rs.wasNull()){
				return null;
			}
			dto.setCopiedFolderPath(rs.getString("COPIEDPATH"));
			dto.setcParentFolderCode(rs.getString("CPARENTFOLDERCODE"));
			dto.setCopiedOwnerCode(rs.getString("COPIEDOWNERCODE"));
			dto.setOriginFolderCode(rs.getString("ORIGINFOLDERCODE"));
			dto.setOriginFolderPath(rs.getString("ORIGINPATH"));
			dto.setoParentFolderCode(rs.getString("OPARENTFOLDERCODE"));
			dto.setOriginOwnerCode(rs.getString("ORIGINOWNERCODE"));			
		}
		return dto;
	}
	
}
