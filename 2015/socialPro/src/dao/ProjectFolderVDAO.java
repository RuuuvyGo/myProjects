package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.FolderDTO;
import dto.ProjectFolderVDTO;
import dto.TagDTO;
import socialProExceptions.DAOException;

public class ProjectFolderVDAO  extends BaseDAO{

	private static final String SELECT_PROJECTFOLDER_SQL0 = "SELECT DISTINCT PROJECTCODE, OWNERCODE, FOLDERCODE, NAME, DESCRIPTION, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(ALTERDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as ALTERDATE, PARENTFOLDERCODE ,FOLDERCONTENT FROM projectfolder_view where ";
	private static final String SELECT_PROJECTFOLDER_SQL1 = "SELECT DISTINCT PROJECTCODE, OWNERCODE, FOLDERCODE, NAME, DESCRIPTION, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(ALTERDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as ALTERDATE, PARENTFOLDERCODE ,FOLDERCONTENT FROM projectfolder_view where projectCode=?";
	private static final String SELECT_PROJECTFOLDER_SQL2 = "SELECT DISTINCT PROJECTCODE, OWNERCODE, FOLDERCODE, NAME, DESCRIPTION, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(ALTERDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as ALTERDATE, PARENTFOLDERCODE ,FOLDERCONTENT FROM projectfolder_view where ownerCode=?";
	private static final String SELECT_PROJECTFOLDER_SQL3 = "SELECT DISTINCT PROJECTCODE, OWNERCODE, FOLDERCODE, NAME, DESCRIPTION, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(ALTERDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as ALTERDATE, PARENTFOLDERCODE ,FOLDERCONTENT FROM projectfolder_view where folderCode=?";
	private static final String SELECT_PROJECTFOLDER_SQL4 = "SELECT DISTINCT PROJECTCODE, OWNERCODE, FOLDERCODE, NAME, DESCRIPTION, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(ALTERDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as ALTERDATE, PARENTFOLDERCODE ,FOLDERCONTENT FROM projectfolder_tb where PARENTFOLDERCODE=?";
	private static final String SELECT_PROJECTFOLDER_SQL5 = "SELECT DISTINCT PROJECTCODE, OWNERCODE, FOLDERCODE, NAME, DESCRIPTION, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(ALTERDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as ALTERDATE, PARENTFOLDERCODE ,FOLDERCONTENT FROM projectfolder_view where NAME=?";
	private static final String SELECT_PROJECTFOLDER_SQL25 = "SELECT DISTINCT PROJECTCODE, OWNERCODE, FOLDERCODE, NAME, DESCRIPTION, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(ALTERDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as ALTERDATE, PARENTFOLDERCODE ,FOLDERCONTENT FROM projectfolder_view where ownerCode=? and NAME=?";
	private static final String SELECT_PROJECTFOLDER_SQL7 = "SELECT DISTINCT PROJECTCODE, OWNERCODE, FOLDERCODE, NAME, DESCRIPTION, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(ALTERDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as ALTERDATE, PARENTFOLDERCODE ,FOLDERCONTENT FROM projectfolder_view where PATH=?";
	private static final String SELECT_PROJECTFOLDER_SQL8 = "SELECT DISTINCT PROJECTCODE, OWNERCODE, FOLDERCODE, NAME, DESCRIPTION, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(ALTERDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as ALTERDATE, PARENTFOLDERCODE ,FOLDERCONTENT FROM projectfolder_view where INSERTDATE=?";
	private static final String SELECT_PROJECTFOLDER_SQL9 = "SELECT DISTINCT PROJECTCODE, OWNERCODE, FOLDERCODE, NAME, DESCRIPTION, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(ALTERDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as ALTERDATE, PARENTFOLDERCODE ,FOLDERCONTENT FROM projectfolder_view where ALTERDATE=?";
	private static final String SELECT_PROJECTFOLDER_SQL10 = "SELECT DISTINCT PROJECTCODE, OWNERCODE, FOLDERCODE, NAME, DESCRIPTION, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(ALTERDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as ALTERDATE, PARENTFOLDERCODE ,FOLDERCONTENT FROM projectfolder_view where FOLDERCONTENT=?";
	
	
	public ProjectFolderVDTO searchProjectCode(String projectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		ProjectFolderVDTO dto = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_PROJECTFOLDER_SQL1);
			pStatement.setString(1,projectCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
			
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectFolderVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<ProjectFolderVDTO> searchProjectCode(List<String> projectCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<ProjectFolderVDTO> dtoList = new ArrayList<ProjectFolderVDTO>();

		int size = projectCodeList.size();
		String sql = SELECT_PROJECTFOLDER_SQL0+" PROJECTCODE in ( ";
				
		for(int i=0;i<size-1;i++){
			sql+="'"+projectCodeList.get(i)+"', ";
		}sql+="'"+projectCodeList.get(size-1)+"' )";
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectFolderVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	
	public List<ProjectFolderVDTO> searchOwneCode(String ownerCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<ProjectFolderVDTO> dtoList = new ArrayList<ProjectFolderVDTO>();
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_PROJECTFOLDER_SQL2);
			pStatement.setString(1,ownerCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectFolderVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<ProjectFolderVDTO> searchProjectName(String projectName) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<ProjectFolderVDTO> dtoList = new ArrayList<ProjectFolderVDTO>();
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_PROJECTFOLDER_SQL5);
			pStatement.setString(1,projectName);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectFolderVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	public ProjectFolderVDTO searchOwneCode(String ownerCode,String projectName) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		ProjectFolderVDTO dto = null;
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_PROJECTFOLDER_SQL25);
			pStatement.setString(1,ownerCode);
			pStatement.setString(2,projectName);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectFolderVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public ProjectFolderVDTO searchFolderCode(String folderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		ProjectFolderVDTO dto = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_PROJECTFOLDER_SQL3);
			pStatement.setString(1,folderCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
			
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectFolderVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public ProjectFolderVDTO searchPFolderCode(String parentFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		ProjectFolderVDTO dto = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_PROJECTFOLDER_SQL4);
			pStatement.setString(1,parentFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
			
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectFolderVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public ProjectFolderVDTO searchPath(String path) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		ProjectFolderVDTO dto = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_PROJECTFOLDER_SQL7);
			pStatement.setString(1,path);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
			
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectFolderVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<ProjectFolderVDTO> searchInsertDate(String insertDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<ProjectFolderVDTO> dtoList = new ArrayList<ProjectFolderVDTO>();
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_PROJECTFOLDER_SQL8);
			pStatement.setString(1,insertDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectFolderVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<ProjectFolderVDTO> searchAlterDate(String alterDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<ProjectFolderVDTO> dtoList = new ArrayList<ProjectFolderVDTO>();
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_PROJECTFOLDER_SQL9);
			pStatement.setString(1,alterDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectFolderVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	
	public ProjectFolderVDTO searchFolderContent(String folderContent) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		ProjectFolderVDTO dto = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_PROJECTFOLDER_SQL10);
			pStatement.setString(1,folderContent);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
			
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectFolderVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	
	private List<ProjectFolderVDTO> makeDTOList(ResultSet rs, List<ProjectFolderVDTO> dtoList) throws SQLException{
		
		while(rs.next())
		{
			ProjectFolderVDTO dto = new ProjectFolderVDTO();
			dto.setProjectCode(rs.getString("projectCode"));
			if(rs.wasNull())return dtoList;
			dto.setFolderCode(rs.getString("folderCode"));
			dto.setOwner(rs.getString("ownerCode"));
			
			dto.setName(rs.getString("NAME"));
			dto.setDescription(rs.getString("description"));
			dto.setPath(rs.getString("PATH"));
			dto.setInsertDate(rs.getString("insertDate"));
			dto.setAlterDate(rs.getString("alterDate"));
			dto.setParentFolderCode(rs.getString("parentFolderCode"));
			dto.setFolderContent(rs.getString("FOLDERCONTENT"));
			dtoList.add(dto);
		}
		return dtoList;
	}
	
	private ProjectFolderVDTO makeDTO(ResultSet rs, ProjectFolderVDTO dto) throws SQLException{
		System.out.println("makeDTO...");
		dto=null;
		while(rs.next())
		{
			dto = new ProjectFolderVDTO();
			dto.setProjectCode(rs.getString("projectCode"));
			if(rs.wasNull())return null;
			dto.setFolderCode(rs.getString("folderCode"));
			dto.setOwner(rs.getString("ownerCode"));
			
			dto.setName(rs.getString("NAME"));
			dto.setDescription(rs.getString("description"));
			dto.setPath(rs.getString("PATH"));
			dto.setInsertDate(rs.getString("insertDate"));
			dto.setAlterDate(rs.getString("alterDate"));
			dto.setParentFolderCode(rs.getString("parentFolderCode"));
			dto.setFolderContent(rs.getString("FOLDERCONTENT"));
		}
		return dto;
	}
}
