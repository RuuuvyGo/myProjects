package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.TagDTO;

import socialProExceptions.DAOException;

public class TagDAO extends BaseDAO{
	
	private static final String TAG_SEQ = 
			"select tag_seq.nextval from dual";
	private static final String INSERT_TAG_SQL = 
			"insert into tag_tb(TAGCODE, TAGNAME) values(?,?)";
	
	private static final String UPDATE_TAG_SQL21 = "update tag_tb set TAGNAME=? "+ "where TAGCODE=?";
	
	private static final String SELECT_TAG_SQL0 = "select * FROM tag_tb where";
	private static final String SELECT_TAG_SQL1 = "select * FROM tag_tb where TAGCODE=?";
	private static final String SELECT_TAG_SQL2 = "select * FROM tag_tb where TAGNAME=?";
	
	private static final String DELETE_TAG_SQL1 = "DELETE FROM tag_tb WHERE  TAGCODE=?";
	private static final String DELETE_TAG_SQL2 = "DELETE FROM tag_tb WHERE  TAGNAME=?";
	
	private String makeCode() throws DAOException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement pStatement = null;
		String code=null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(TAG_SEQ);
			ResultSet rs = pStatement.executeQuery();
			if(rs.next()){
				code=new String();
				code="tag_"+rs.getString("nextval");
			}
			System.out.println(code);
		
		} catch (SQLException e) {
			throw new DAOException("Error makeCode TagDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
		return code;
	}
	
	
	public String insertTag(TagDTO dto) throws DAOException {
	
		System.out.println("insertTeam start");
		Connection connection = null;
		PreparedStatement pStatement = null;
		String code=this.makeCode();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(INSERT_TAG_SQL);
			pStatement.setString(1, code);
			pStatement.setString(2, dto.getTagName());
			if(pStatement.executeUpdate()==1)return code;
		} catch (SQLException e) {
			throw new DAOException("Error adding TagDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
		return null;
	}
	
	public List<String> insertTag(List<TagDTO> dtoList) throws DAOException {
		
		System.out.println("insert Cooper start");
		Connection connection = null;
		PreparedStatement pStatement = null;

		try {
			connection = getConnection();
			List<String> tagCodes = new ArrayList<String>();
			String sql = "insert all ";
			for(int i=0;i<dtoList.size();i++){
				tagCodes.add(makeCode());
				sql+="into tag_tb (tagCode,tagName) values (?,?) ";
			}sql+="select * from dual";
			pStatement = connection.prepareStatement(sql);
			
			for(int i=0;i<dtoList.size();i++){
				pStatement.setString(i*2+1, tagCodes.get(i));
				pStatement.setString(i*2+2, dtoList.get(i).getTagName());
			}
			System.out.println(pStatement.executeUpdate());
			
			return tagCodes;
		} catch (SQLException e) {
			throw new DAOException("Error adding Cooper. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	public TagDTO searchTagName(String tagName) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		TagDTO dto = new TagDTO();
		ResultSet rs=null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_TAG_SQL2);
			pStatement.setString(1,tagName);
			 rs = pStatement.executeQuery();
			while(rs.next())
			{
				
				dto.setTagCode(rs.getString("TAGCODE"));
				dto.setTagName(rs.getString("TAGNAME"));
			}
			if(dto.getTagCode()==null)return null;
			return dto;
		} catch (SQLException e) {
			throw new DAOException("Error search TagDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<TagDTO> searchTagNameList(List<String> tagNameList) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<TagDTO> dtoList = new ArrayList<TagDTO>();
		ResultSet rs=null;
		
		int size = tagNameList.size();
		String sql = SELECT_TAG_SQL0+" TAGNAME in ( ";
				
		for(int i=0;i<size-1;i++){
			sql+="'"+tagNameList.get(i)+"', ";
		}sql+="'"+tagNameList.get(size-1)+"' )";
		System.out.println("Thisis sql !! :  "+sql);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			rs = pStatement.executeQuery();
			return this.makeList(dtoList, rs);
		} catch (SQLException e) {
			throw new DAOException("Error search TagDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public TagDTO searchTagCode(String tagCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		TagDTO dto = new TagDTO();
		ResultSet rs=null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_TAG_SQL1);
			pStatement.setString(1,tagCode);
			 rs = pStatement.executeQuery();
			while(rs.next())
			{
				
				dto.setTagCode(rs.getString("TAGCODE"));
				dto.setTagName(rs.getString("TAGNAME"));
			}
			if(dto.getTagCode()==null)return null;
			return dto;
		} catch (SQLException e) {
			throw new DAOException("Error search TagDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public boolean updateTag(String code, String newTagName)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(UPDATE_TAG_SQL21);
			pStatement.setString(1, newTagName);
			pStatement.setString(2, code);
			if(pStatement.executeUpdate()==0)return false;
			return true;
		} catch (SQLException e) {
			throw new DAOException("Error updating Tag. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	public boolean deleteTagCode(String tagCode)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(DELETE_TAG_SQL1);
			pStatement.setString(1, tagCode);
			if(pStatement.executeUpdate()==1)return true;
			return false;
		} catch (SQLException e) {
			throw new DAOException("Error Delete TagDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	public boolean deleteTagName(String tagName)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(DELETE_TAG_SQL2);
			pStatement.setString(1, tagName);
			if(pStatement.executeUpdate()==1)return true;
			return false;
		} catch (SQLException e) {
			throw new DAOException("Error Delete TagDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	private List<TagDTO> makeList(List<TagDTO> dtoList, ResultSet rs) throws SQLException{
		
		while(rs.next()){
			String tagCode = rs.getString("TAGCODE");
			if(rs.wasNull())return dtoList;
			dtoList.add(new TagDTO(tagCode, rs.getString("TAGNAME")));
		}
		return dtoList;
	}
}
