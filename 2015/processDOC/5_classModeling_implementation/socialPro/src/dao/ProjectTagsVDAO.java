package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ProjectTagsVDTO;

import socialProExceptions.DAOException;
public class ProjectTagsVDAO extends BaseDAO{

	private static final String SELECT_PROJECTTAGSV_SQL = "SELECT DISTINCT * FROM projectTags_view where ";
	private static final String SELECT_PROJECTTAGSV_SQL1 = "SELECT DISTINCT * FROM projectTags_view where TAGCODE=?";
	private static final String SELECT_PROJECTTAGSV_SQL2 = "SELECT DISTINCT * FROM projectTags_view where TAGNAME=?";
	private static final String SELECT_PROJECTTAGSV_SQL3 = "SELECT DISTINCT * FROM projectTags_view where projectCode=?";
	private static final String SELECT_PROJECTTAGSV_SQL4 = "SELECT DISTINCT * FROM projectTags_view where PROJECTNAME=?";
	private static final String SELECT_PROJECTTAGSV_SQL4_ = "SELECT DISTINCT * FROM projectTags_view where PROJECTNAME like ?%";
	private static final String SELECT_PROJECTTAGSV_SQL_4 = "SELECT DISTINCT * FROM projectTags_view where PROJECTNAME like %?";
	
	public List<ProjectTagsVDTO> searchTagCode(String tagCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<ProjectTagsVDTO> dtoList = new ArrayList<ProjectTagsVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_PROJECTTAGSV_SQL1);
			pStatement.setString(1,tagCode);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				ProjectTagsVDTO dto=new ProjectTagsVDTO();
				dto.setTagCode(rs.getString("tagCode"));
				dto.setTagName(rs.getString("tagName"));
				dto.setProjectCode(rs.getString("projectCode"));
				dto.setProjectName(rs.getString("projectName"));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectTagVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}
	
	public List<ProjectTagsVDTO> searchTagCodes(List<String> tagCodeList)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<ProjectTagsVDTO> dtoList=new ArrayList<ProjectTagsVDTO>();
		try {
			connection = getConnection();
			
			int leng= tagCodeList.size();
			String sql = SELECT_PROJECTTAGSV_SQL;
			for(int i=0;i<leng;i++){
				sql+="tagCode=? ";
				if(i!=leng-1)sql+="or ";
				
			}
			System.out.println(sql);
			pStatement = connection.prepareStatement(sql);
			
			for(int i=1;i<=leng;i++){
				pStatement.setString(i,tagCodeList.get(i));
			}
			
			ResultSet rs = pStatement.executeQuery();
			while(rs.next())
			{
				ProjectTagsVDTO dto = new ProjectTagsVDTO();
				dto.setTagCode(rs.getString("tagCode"));
				dto.setTagName(rs.getString("tagName"));
				dto.setProjectCode(rs.getString("projectCode"));
				dto.setProjectName(rs.getString("projectName"));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectTagVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	public List<ProjectTagsVDTO> searchTagNames(List<String> tagNameList)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<ProjectTagsVDTO> dtoList=new ArrayList<ProjectTagsVDTO>();
		try {
			connection = getConnection();
			
			int leng= tagNameList.size()-1;
			String sql = SELECT_PROJECTTAGSV_SQL+" tagName in (";
			for(int i=0;i<leng;i++){
				sql+="'"+tagNameList.get(i)+"', ";				
			}sql+="'"+tagNameList.get(leng)+"' )";
			System.out.println(sql);
			pStatement = connection.prepareStatement(sql);
			
			ResultSet rs = pStatement.executeQuery();
			String tagCode;
			while(rs.next())
			{
				ProjectTagsVDTO dto = new ProjectTagsVDTO();
				tagCode = rs.getString("tagCode");
				if(rs.wasNull())return dtoList;
				dto.setTagCode(tagCode);
				dto.setTagName(rs.getString("tagName"));
				dto.setProjectCode(rs.getString("projectCode"));
				dto.setProjectName(rs.getString("projectName"));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectTagVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	public List<ProjectTagsVDTO> searchTagName(String tagName) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<ProjectTagsVDTO> dtoList = new ArrayList<ProjectTagsVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_PROJECTTAGSV_SQL2);
			pStatement.setString(1,tagName);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				ProjectTagsVDTO dto=new ProjectTagsVDTO();
				dto.setTagCode(rs.getString("tagCode"));
				dto.setTagName(rs.getString("tagName"));
				dto.setProjectCode(rs.getString("projectCode"));
				dto.setProjectName(rs.getString("projectName"));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectTagVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}
	
public List<ProjectTagsVDTO> searchPCode(String projectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<ProjectTagsVDTO> dtoList = new ArrayList<ProjectTagsVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_PROJECTTAGSV_SQL3);
			pStatement.setString(1,projectCode);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				ProjectTagsVDTO dto=new ProjectTagsVDTO();
				dto.setTagCode(rs.getString("TAGCODE"));
				dto.setTagName(rs.getString("TAGNAME"));
				dto.setProjectCode(rs.getString("projectCode"));
				dto.setProjectName(rs.getString("PROJECTNAME"));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectTagVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}

public List<ProjectTagsVDTO> searchPName(String projectName) throws DAOException{
	
	Connection connection = null;
	PreparedStatement pStatement = null;
	ResultSet rs=null;
	List<ProjectTagsVDTO> dtoList = new ArrayList<ProjectTagsVDTO>();
	try {
		connection = getConnection();
		pStatement = connection.prepareStatement(SELECT_PROJECTTAGSV_SQL4);
		pStatement.setString(1,projectName);
		rs = pStatement.executeQuery();
		while(rs.next())
		{
			ProjectTagsVDTO dto=new ProjectTagsVDTO();
			dto.setTagCode(rs.getString("tagCode"));
			dto.setTagName(rs.getString("tagName"));
			dto.setProjectCode(rs.getString("projectCode"));
			dto.setProjectName(rs.getString("projectName"));
			dtoList.add(dto);
		}
		return dtoList;
	} catch (SQLException e) {
		throw new DAOException("Error search ProjectTagVDTO. "+ e.getMessage());
	} finally {
		closeDBObjects(rs, pStatement, connection);
	}
	
}

public List<ProjectTagsVDTO> searchPNameAll(String projectName) throws DAOException{
	
	Connection connection = null;
	PreparedStatement pStatement = null;
	ResultSet rs=null;
	List<ProjectTagsVDTO> dtoList = new ArrayList<ProjectTagsVDTO>();
	try {
		connection = getConnection();
		pStatement = connection.prepareStatement(SELECT_PROJECTTAGSV_SQL4);
		pStatement.setString(1,projectName);
		rs = pStatement.executeQuery();
		while(rs.next())
		{
			ProjectTagsVDTO dto=new ProjectTagsVDTO();
			dto.setTagCode(rs.getString("tagCode"));
			dto.setTagName(rs.getString("tagName"));
			dto.setProjectCode(rs.getString("projectCode"));
			dto.setProjectName(rs.getString("projectName"));
			dtoList.add(dto);
		}
		pStatement = connection.prepareStatement(SELECT_PROJECTTAGSV_SQL_4);
		pStatement.setString(1,projectName);
		rs = pStatement.executeQuery();
		while(rs.next())
		{
			ProjectTagsVDTO dto=new ProjectTagsVDTO();
			dto.setTagCode(rs.getString("tagCode"));
			dto.setTagName(rs.getString("tagName"));
			dto.setProjectCode(rs.getString("projectCode"));
			dto.setProjectName(rs.getString("projectName"));
			dtoList.add(dto);
		}
		pStatement = connection.prepareStatement(SELECT_PROJECTTAGSV_SQL4_);
		pStatement.setString(1,projectName);
		rs = pStatement.executeQuery();
		while(rs.next())
		{
			ProjectTagsVDTO dto=new ProjectTagsVDTO();
			dto.setTagCode(rs.getString("tagCode"));
			dto.setTagName(rs.getString("tagName"));
			dto.setProjectCode(rs.getString("projectCode"));
			dto.setProjectName(rs.getString("projectName"));
			dtoList.add(dto);
		}
		return dtoList;
	} catch (SQLException e) {
		throw new DAOException("Error search ProjectTagVDTO. "+ e.getMessage());
	} finally {
		closeDBObjects(rs, pStatement, connection);
	}
	
}
	
}
