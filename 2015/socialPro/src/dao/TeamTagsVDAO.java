package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.TeamTagsVDTO;

import socialProExceptions.DAOException;

public class TeamTagsVDAO extends BaseDAO{

	private static final String SELECT_TEAMTAGSV_SQL = "SELECT DISTINCT * FROM teamTags_view where ";
	private static final String SELECT_TEAMTAGSV_SQL1 = "SELECT DISTINCT * FROM teamTags_view where TAGCODE=?";
	private static final String SELECT_TEAMTAGSV_SQL2 = "SELECT DISTINCT * FROM teamTags_view where TAGNAME=?";
	private static final String SELECT_TEAMTAGSV_SQL3 = "SELECT DISTINCT * FROM teamTags_view where TEAMCODE=?";
	private static final String SELECT_TEAMTAGSV_SQL4 = "SELECT DISTINCT * FROM teamTags_view where TEAMNAME=?";
	private static final String SELECT_TEAMTAGSV_SQL4_ = "SELECT DISTINCT * FROM teamTags_view where TEAMNAME like ?%";
	private static final String SELECT_TEAMTAGSV_SQL_4 = "SELECT DISTINCT * FROM teamTags_view where TEAMNAME like %?";
	private static final String SELECT_TEAMTAGSV_SQL5 = "SELECT DISTINCT * FROM teamTags_view where DESCRIPTION like %?% ";
	
	
	public List<TeamTagsVDTO> searchTagCodes(List<String> tagCodeList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<TeamTagsVDTO> dtoList = new ArrayList<TeamTagsVDTO>();
		try {
			connection = getConnection();
			
			String sql = SELECT_TEAMTAGSV_SQL;
			for(int i=0;i<tagCodeList.size()-1;i++){
				sql+=" tagCode = ? or";
			}
			sql+=" tagCode=?";
			pStatement = connection.prepareStatement(sql);
			for(int i=0;i<tagCodeList.size();i++){
				pStatement.setString(i,tagCodeList.get(i));
			}
			
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				TeamTagsVDTO dto=new TeamTagsVDTO();
				dto.setTagCode(rs.getString("tagCode"));
				dto.setTagName(rs.getString("tagName"));
				dto.setTeamCode(rs.getString("teamCode"));
				dto.setTeamName(rs.getString("teamName"));
				dto.setDescription(rs.getString("DESCRIPTION"));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search TeamTagVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}
	public List<TeamTagsVDTO> searchTagCode(String tagCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<TeamTagsVDTO> dtoList = new ArrayList<TeamTagsVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_TEAMTAGSV_SQL1);
			pStatement.setString(1,tagCode);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				TeamTagsVDTO dto=new TeamTagsVDTO();
				dto.setTagCode(rs.getString("tagCode"));
				dto.setTagName(rs.getString("tagName"));
				dto.setTeamCode(rs.getString("teamCode"));
				dto.setTeamName(rs.getString("teamName"));
				dto.setDescription(rs.getString("DESCRIPTION"));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search TeamTagVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}
	
	public List<TeamTagsVDTO> searchTagNames(List<String> tagNameList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<TeamTagsVDTO> dtoList = new ArrayList<TeamTagsVDTO>();
		try {
			connection = getConnection();
			
			String sql = SELECT_TEAMTAGSV_SQL;
			for(int i=0;i<tagNameList.size()-1;i++){
				sql+=" tagName = ? or";
			}
			sql+=" tagName=?";
			pStatement = connection.prepareStatement(sql);
			for(int i=0;i<tagNameList.size();i++){
				pStatement.setString(i,tagNameList.get(i));
			}
			
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				TeamTagsVDTO dto=new TeamTagsVDTO();
				dto.setTagCode(rs.getString("tagCode"));
				dto.setTagName(rs.getString("tagName"));
				dto.setTeamCode(rs.getString("teamCode"));
				dto.setTeamName(rs.getString("teamName"));
				dto.setDescription(rs.getString("DESCRIPTION"));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search TeamTagVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}
	
	public List<TeamTagsVDTO> searchTagName(String tagName) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<TeamTagsVDTO> dtoList = new ArrayList<TeamTagsVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_TEAMTAGSV_SQL2);
			pStatement.setString(1,tagName);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				TeamTagsVDTO dto=new TeamTagsVDTO();
				dto.setTagCode(rs.getString("tagCode"));
				dto.setTagName(rs.getString("tagName"));
				dto.setTeamCode(rs.getString("teamCode"));
				dto.setTeamName(rs.getString("teamName"));
				dto.setDescription(rs.getString("DESCRIPTION"));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search TeamTagVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}
	
	public List<TeamTagsVDTO> searchTeamCode(String teamCode) throws DAOException{
		System.out.println("teamTagsVDAO searchTeamCode  "+teamCode);
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<TeamTagsVDTO> dtoList = new ArrayList<TeamTagsVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_TEAMTAGSV_SQL3);
			pStatement.setString(1,teamCode);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				TeamTagsVDTO dto=new TeamTagsVDTO();
				dto.setTagCode(rs.getString("tagCode"));
				dto.setTagName(rs.getString("tagName"));
				dto.setTeamCode(rs.getString("teamCode"));
				dto.setTeamName(rs.getString("teamName"));
				dto.setDescription(rs.getString("DESCRIPTION"));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search TeamTagVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}
	
	public List<TeamTagsVDTO> searchTeamName(String teamName) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<TeamTagsVDTO> dtoList = new ArrayList<TeamTagsVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_TEAMTAGSV_SQL4);
			pStatement.setString(1,teamName);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				TeamTagsVDTO dto=new TeamTagsVDTO();
				dto.setTagCode(rs.getString("tagCode"));
				dto.setTagName(rs.getString("tagName"));
				dto.setTeamCode(rs.getString("teamCode"));
				dto.setTeamName(rs.getString("teamName"));
				dto.setDescription(rs.getString("DESCRIPTION"));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search TeamTagVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}
	
	public List<TeamTagsVDTO> searchTeamNameAll(String teamName) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<TeamTagsVDTO> dtoList = new ArrayList<TeamTagsVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_TEAMTAGSV_SQL4);
			pStatement.setString(1,teamName);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				TeamTagsVDTO dto=new TeamTagsVDTO();
				dto.setTagCode(rs.getString("tagCode"));
				dto.setTagName(rs.getString("tagName"));
				dto.setTeamCode(rs.getString("teamCode"));
				dto.setTeamName(rs.getString("teamName"));
				dto.setDescription(rs.getString("DESCRIPTION"));
				dtoList.add(dto);
			}
			
			pStatement = connection.prepareStatement(SELECT_TEAMTAGSV_SQL_4);
			pStatement.setString(1,teamName);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				TeamTagsVDTO dto=new TeamTagsVDTO();
				dto.setTagCode(rs.getString("tagCode"));
				dto.setTagName(rs.getString("tagName"));
				dto.setTeamCode(rs.getString("teamCode"));
				dto.setTeamName(rs.getString("teamName"));
				dto.setDescription(rs.getString("DESCRIPTION"));
				dtoList.add(dto);
			}
			
			pStatement = connection.prepareStatement(SELECT_TEAMTAGSV_SQL4_);
			pStatement.setString(1,teamName);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				TeamTagsVDTO dto=new TeamTagsVDTO();
				dto.setTagCode(rs.getString("tagCode"));
				dto.setTagName(rs.getString("tagName"));
				dto.setTeamCode(rs.getString("teamCode"));
				dto.setTeamName(rs.getString("teamName"));
				dto.setDescription(rs.getString("DESCRIPTION"));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search TeamTagVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}
	
	public List<TeamTagsVDTO> searchDes(String description) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<TeamTagsVDTO> dtoList = new ArrayList<TeamTagsVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_TEAMTAGSV_SQL5);
			pStatement.setString(1,description);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				TeamTagsVDTO dto=new TeamTagsVDTO();
				dto.setTagCode(rs.getString("tagCode"));
				dto.setTagName(rs.getString("tagName"));
				dto.setTeamCode(rs.getString("teamCode"));
				dto.setTeamName(rs.getString("teamName"));
				dto.setDescription(rs.getString("DESCRIPTION"));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search TeamTagVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}
	
	
}
