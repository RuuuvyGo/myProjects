package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sun.security.util.Length;
import dto.TagDTO;
import dto.TagDetailsDTO;
import dto.TeamDTO;
import socialProExceptions.DAOException;

public class TagDetailsDAO extends BaseDAO{

	private static final String TAGDETAILS_SEQ = 
			"select tagDetails_seq.nextval from dual";
	private static final String INSERT_TAGDETAILS_SQL = 
			"insert into tagDetails_tb(TAGCODE, SOURCECODE) values(?,?)";
	
	private static final String UPDATE_TAGDETAILS_SQL21 = "update tagDetails_tb set SOURCECODE=? "+ "where TAGCODE=?";
	
	private static final String SELECT_TAGDETAILS_SQL1 = "select * FROM tagDetails_tb where TAGCODE=?";
	private static final String SELECT_TAGDETAILS_SQL = "select * FROM tagDetails_tb where ";
	private static final String SELECT_TAGDETAILS_SQL12 = "select * FROM tagDetails_tb where TAGCODE=? and SOURCECODE=?";
	private static final String SELECT_TAGDETAILS_SQL12_ = "select * FROM tagDetails_tb where TAGCODE=? and SOURCECODE like ?%";
	private static final String SELECT_TAGDETAILS_SQL2 = "select * FROM tagDetails_tb where SOURCECODE=?";
	
	private static final String DELETE_TAGDETAILS_SQL1 = "DELETE FROM tagDetails_tb WHERE  TAGCODE=?";
	private static final String DELETE_TAGDETAILS_SQL12 = "DELETE FROM tagDetails_tb WHERE  TAGCODE=? and SOURCECODE=?";
	private static final String DELETE_TAGDETAILS_SQL12_ = "DELETE FROM tagDetails_tb WHERE  TAGCODE=? and SOURCECODE like ?%";
	private static final String DELETE_TAGDETAILS_SQL2 = "DELETE FROM tagDetails_tb WHERE  SOURCECODE=?";
	
	public boolean insertTagDtl(TagDetailsDTO dto) throws DAOException {
		
		System.out.println("insertTeam start");
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(INSERT_TAGDETAILS_SQL);
			pStatement.setString(1, dto.getTagCode());
			pStatement.setString(2, dto.getSourceCode());
			if(pStatement.executeUpdate()==1)return true;
			
		} catch (SQLException e) {
			throw new DAOException("Error adding TagDetails. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
		return false;
	}
	
	public int insertTagDtl(List<TagDetailsDTO> dtoList) throws DAOException {
		
		System.out.println("insert TgD start");
		System.out.println(dtoList.size());
		Connection connection = null;
		PreparedStatement pStatement = null;

		try {
			connection = getConnection();
			List<String> tagCodes = new ArrayList<String>();
			String sql = "insert all ";
			for(int i=0;i<dtoList.size();i++){
				sql+="into tagDetails_tb (TAGCODE,SOURCECODE) values (?,?) ";
			}sql+="select * from dual";
			System.out.println(sql);
			pStatement = connection.prepareStatement(sql);
			for(int i=0;i<dtoList.size();i++){
				pStatement.setString(i*2+1, dtoList.get(i).getTagCode());
				pStatement.setString(i*2+2, dtoList.get(i).getSourceCode());
			}
			return pStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error adding TagDetailsDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	public List<TagDetailsDTO> searchTagDetails(List<String> tagCodeList,String front)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<TagDetailsDTO> dtoList=new ArrayList<TagDetailsDTO>();
		
		int size = tagCodeList.size();
		String sql = SELECT_TAGDETAILS_SQL+" TAGCODE in ( ";

		for(int i=0;i<size-1;i++){
			sql+="'"+tagCodeList.get(i)+"', ";
		}sql+="'"+tagCodeList.get(size-1)+"' ) and sourceCode like ?";
		
		
		try {
			connection = getConnection();
			System.out.println(sql);
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,front+"%");
			ResultSet rs = pStatement.executeQuery();
			return this.makeList(dtoList, rs);
		} catch (SQLException e) {
			throw new DAOException("Error search TagDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	
	public List<TagDetailsDTO> searchTagDetails(String tagCode)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<TagDetailsDTO> dtoList=new ArrayList<TagDetailsDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_TAGDETAILS_SQL1);
			pStatement.setString(1,tagCode);
			ResultSet rs = pStatement.executeQuery();
			while(rs.next())
			{
				TagDetailsDTO dto = new TagDetailsDTO();
				dto.setTagCode(rs.getString("tagCode"));
				dto.setSourceCode(rs.getString("SOURCECODE"));
				
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search TagDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	public TagDetailsDTO searchTagDetailsCodes(String tagCode,String sourceCode)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		TagDetailsDTO dto = new TagDetailsDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_TAGDETAILS_SQL12);
			pStatement.setString(1,tagCode);
			pStatement.setString(2,tagCode);
			ResultSet rs = pStatement.executeQuery();
			while(rs.next())
			{
				dto.setTagCode(rs.getString("tagCode"));
				dto.setSourceCode(rs.getString("SOURCECODE"));
			}
			if(dto.getTagCode()==null)return null;
			return dto;
		} catch (SQLException e) {
			throw new DAOException("Error search TagDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	public List<TagDetailsDTO> searchTagDetails(String tagCode,String front)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<TagDetailsDTO> dtoList=new ArrayList<TagDetailsDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_TAGDETAILS_SQL12_);
			pStatement.setString(1,tagCode);
			pStatement.setString(2,front);
			ResultSet rs = pStatement.executeQuery();
			while(rs.next())
			{
				TagDetailsDTO dto = new TagDetailsDTO();
				dto.setTagCode(rs.getString("tagCode"));
				dto.setSourceCode(rs.getString("SOURCECODE"));
				
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search TagDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	public List<TagDetailsDTO> searchTagDetailsSCode(String sourceCode)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<TagDetailsDTO> dtoList=new ArrayList<TagDetailsDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_TAGDETAILS_SQL2);
			pStatement.setString(1,sourceCode);
			ResultSet rs = pStatement.executeQuery();
			while(rs.next())
			{
				TagDetailsDTO dto = new TagDetailsDTO();
				dto.setTagCode(rs.getString("tagCode"));
				dto.setSourceCode(rs.getString("SOURCECODE"));
				
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search TagDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	public int deleteTagDetailsTCode(String tagCode)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(DELETE_TAGDETAILS_SQL1);
			pStatement.setString(1, tagCode);
			return pStatement.executeUpdate();
			
		} catch (SQLException e) {
			throw new DAOException("Error Delete TagDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	public boolean deleteTagDetailsCodes(String tagCode,String sourceCode)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(DELETE_TAGDETAILS_SQL12);
			pStatement.setString(1, tagCode);
			pStatement.setString(2, sourceCode);
			if(pStatement.executeUpdate()!=1)return false;
			return true;
			
		} catch (SQLException e) {
			throw new DAOException("Error Delete TagDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	public boolean deleteTagDetailsCodes(String tagCode,List<String> sourceCodeList)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			String sql = "DELETE FROM tagDetails_tb WHERE tagCode=? and (";
			for(int i=0;i<sourceCodeList.size()-1;i++){
				sql+="sourceCode=? or ";
			}sql+="sourceCode=?";
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, tagCode);
			for(int i=2;i<=sourceCodeList.size()+1;i++){
				pStatement.setString(i, sourceCodeList.get(i-2));
			}
			
			if(pStatement.executeUpdate()<=0)return false;
			return true;
			
		} catch (SQLException e) {
			throw new DAOException("Error Delete TagDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	public int deleteTagDetailsTCode(String tagCode,String front)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(DELETE_TAGDETAILS_SQL12_);
			pStatement.setString(1, tagCode);
			pStatement.setString(2, front);
			return pStatement.executeUpdate();
			
		} catch (SQLException e) {
			throw new DAOException("Error Delete TagDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	public int deleteTagDetailsSCode(String sourceCode)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(DELETE_TAGDETAILS_SQL2);
			pStatement.setString(1, sourceCode);
			return pStatement.executeUpdate();
			
		} catch (SQLException e) {
			throw new DAOException("Error Delete TagDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}

	private List<TagDetailsDTO> makeList(List<TagDetailsDTO> dtoList, ResultSet rs) throws SQLException{
		
		while(rs.next()){
			String tagCode = rs.getString("TAGCODE");
			if(rs.wasNull())return dtoList;
			dtoList.add(new TagDetailsDTO(tagCode, rs.getString("SOURCECODE")));
		}
		return dtoList;
	}
}
