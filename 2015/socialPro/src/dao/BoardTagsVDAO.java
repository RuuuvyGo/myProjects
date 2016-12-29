package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.BoardTagsVDTO;
import dto.ProjectTagsVDTO;

import socialProExceptions.DAOException;

public class BoardTagsVDAO extends BaseDAO{

	private static final String SELECT_BOARDTAGSV_SQL = "select DISTINCT * FROM boardTags_view where ";
	private static final String SELECT_BOARDTAGSV_SQL1 = "select DISTINCT * FROM boardTags_view where TAGCODE=?";
	private static final String SELECT_BOARDTAGSV_SQL2 = "select DISTINCT * FROM boardTags_view where TAGNAME=?";
	private static final String SELECT_BOARDTAGSV_SQL3 = "select DISTINCT * FROM boardTags_view where BOARDCODE=?";
	private static final String SELECT_BOARDTAGSV_SQL4 = "select DISTINCT * FROM boardTags_view where BOARDTITLE=?";
	private static final String SELECT_BOARDTAGSV_SQL4_ = "select DISTINCT * FROM boardTags_view where BOARDTITLE like ?%";
	private static final String SELECT_BOARDTAGSV_SQL_4 = "select DISTINCT * FROM boardTags_view where BOARDTITLE like %?";
	
	public List<BoardTagsVDTO> searchTagCode(String tagCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<BoardTagsVDTO> dtoList = new ArrayList<BoardTagsVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_BOARDTAGSV_SQL1);
			pStatement.setString(1,tagCode);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				BoardTagsVDTO dto=new BoardTagsVDTO();
				dto.setTagCode(rs.getString("tagCode"));
				dto.setTagName(rs.getString("tagName"));
				dto.setBoardCode(rs.getString("boardCode"));
				dto.setBoardTitle(rs.getString("boardTitle"));
				dto.setInsertDate(rs.getString("insertDate"));
				dto.setRecommend(rs.getInt("RECOMMEND"));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectTagVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}
	
	public List<BoardTagsVDTO> searchTagName(String tagName) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<BoardTagsVDTO> dtoList = new ArrayList<BoardTagsVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_BOARDTAGSV_SQL2);
			pStatement.setString(1,tagName);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				BoardTagsVDTO dto=new BoardTagsVDTO();
				dto.setTagCode(rs.getString("tagCode"));
				dto.setTagName(rs.getString("tagName"));
				dto.setBoardCode(rs.getString("boardCode"));
				dto.setBoardTitle(rs.getString("boardTitle"));
				dto.setInsertDate(rs.getString("insertDate"));
				dto.setRecommend(rs.getInt("RECOMMEND"));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectTagVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}
	
	public List<BoardTagsVDTO> searchBCode(String boardCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<BoardTagsVDTO> dtoList = new ArrayList<BoardTagsVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_BOARDTAGSV_SQL3);
			pStatement.setString(1,boardCode);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				BoardTagsVDTO dto=new BoardTagsVDTO();
				dto.setTagCode(rs.getString("tagCode"));
				dto.setTagName(rs.getString("tagName"));
				dto.setBoardCode(rs.getString("boardCode"));
				dto.setBoardTitle(rs.getString("boardTitle"));
				dto.setInsertDate(rs.getString("insertDate"));
				dto.setRecommend(rs.getInt("RECOMMEND"));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectTagVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}
	
	public List<BoardTagsVDTO> searchBTitle(String boardTitle) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<BoardTagsVDTO> dtoList = new ArrayList<BoardTagsVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_BOARDTAGSV_SQL4);
			pStatement.setString(1,boardTitle);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				BoardTagsVDTO dto=new BoardTagsVDTO();
				dto.setTagCode(rs.getString("tagCode"));
				dto.setTagName(rs.getString("tagName"));
				dto.setBoardCode(rs.getString("boardCode"));
				dto.setBoardTitle(rs.getString("boardTitle"));
				dto.setInsertDate(rs.getString("insertDate"));
				dto.setRecommend(rs.getInt("RECOMMEND"));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectTagVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}
	
	public List<BoardTagsVDTO> searchBTitleAll(String boardTitle) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<BoardTagsVDTO> dtoList = new ArrayList<BoardTagsVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_BOARDTAGSV_SQL4);
			pStatement.setString(1,boardTitle);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				BoardTagsVDTO dto=new BoardTagsVDTO();
				dto.setTagCode(rs.getString("tagCode"));
				dto.setTagName(rs.getString("tagName"));
				dto.setBoardCode(rs.getString("boardCode"));
				dto.setBoardTitle(rs.getString("boardTitle"));
				dto.setInsertDate(rs.getString("insertDate"));
				dto.setRecommend(rs.getInt("RECOMMEND"));
				dtoList.add(dto);
			}
			pStatement = connection.prepareStatement(SELECT_BOARDTAGSV_SQL_4);
			pStatement.setString(1,boardTitle);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				BoardTagsVDTO dto=new BoardTagsVDTO();
				dto.setTagCode(rs.getString("tagCode"));
				dto.setTagName(rs.getString("tagName"));
				dto.setBoardCode(rs.getString("boardCode"));
				dto.setBoardTitle(rs.getString("boardTitle"));
				dto.setInsertDate(rs.getString("insertDate"));
				dto.setRecommend(rs.getInt("RECOMMEND"));
				dtoList.add(dto);
			}
			pStatement = connection.prepareStatement(SELECT_BOARDTAGSV_SQL4_);
			pStatement.setString(1,boardTitle);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				BoardTagsVDTO dto=new BoardTagsVDTO();
				dto.setTagCode(rs.getString("tagCode"));
				dto.setTagName(rs.getString("tagName"));
				dto.setBoardCode(rs.getString("boardCode"));
				dto.setBoardTitle(rs.getString("boardTitle"));
				dto.setInsertDate(rs.getString("insertDate"));
				dto.setRecommend(rs.getInt("RECOMMEND"));
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
