package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.FolderDTO;

import socialProExceptions.DAOException;

public class FolderDAO extends BaseDAO{

	private static final String FOLDER_SEQ = 
			"select folder_seq.nextval from dual";
	private static final String INSERT_FOLDER_SQL = 
			"insert into folder_tb(FOLDERCODE, NAME, DESCRIPTION, PATH, INSERTDATE, SIZES, PARENTFOLDERCODE, FOLDERCONTENT) values(?,?,?,?,to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff3'),?,?,?)";
	private static final String INSERT_FOLDER_SQL2 = 
			"insert into folder_tb(FOLDERCODE, NAME, DESCRIPTION, PATH, INSERTDATE, SIZES, FOLDERCONTENT) values(?,?,?,?,to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff3'),?.?)";
	
	private static final String UPDATE_FOLDER_SQL241 = "update folder_tb set NAME=? ,path=?"+ "where FOLDERCODE=?";
	private static final String UPDATE_FOLDER_SQL481 = "update folder_tb set path=?,PARENTFOLDERCODE=?"+ "where FOLDERCODE=?";
	private static final String UPDATE_FOLDER_SQL71 = "update folder_tb set SIZES=? where FOLDERCODE=?";
	private static final String UPDATE_FOLDER_SQL61 = "update folder_tb set ALTERDATE=to_char(?,'yyyy-mm-dd hh24:mi:ss.ff3') where FOLDERCODE=?";
	
	private static final String SELECT_FOLDER_SQL1 = "select FOLDERCODE, NAME, DESCRIPTION, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(ALTERDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as ALTERDATE, SIZES, PARENTFOLDERCODE ,FOLDERCONTENT  FROM folder_tb where FOLDERCODE=?";
	private static final String SELECT_FOLDER_SQL12 = "select FOLDERCODE, NAME, DESCRIPTION, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(ALTERDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as ALTERDATE, SIZES, PARENTFOLDERCODE ,FOLDERCONTENT FROM folder_tb where FOLDERCODE=PARENTFOLDERCODE and NAME=?";
	private static final String SELECT_FOLDER_SQL2 = "select FOLDERCODE, NAME, DESCRIPTION, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(ALTERDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as ALTERDATE, SIZES, PARENTFOLDERCODE ,FOLDERCONTENT FROM folder_tb where PARENTFOLDERCODE=?";
	private static final String SELECT_FOLDER_SQL42 = "select FOLDERCODE, NAME, DESCRIPTION, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(ALTERDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as ALTERDATE, SIZES, PARENTFOLDERCODE ,FOLDERCONTENT FROM folder_tb where PARENTFOLDERCODE=? and NAME=? ";
	private static final String SELECT_FOLDER_SQL4 = "select FOLDERCODE, NAME, DESCRIPTION, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(ALTERDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as ALTERDATE, SIZES, PARENTFOLDERCODE ,FOLDERCONTENT FROM folder_tb where PATH=?";
	private static final String SELECT_FOLDER_SQL4_ = "select FOLDERCODE, NAME, DESCRIPTION, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(ALTERDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as ALTERDATE, SIZES, PARENTFOLDERCODE ,FOLDERCONTENT FROM folder_tb where PATH like ";
	private static final String SELECT_FOLDER_SQL5 = "select FOLDERCODE, NAME, DESCRIPTION, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(ALTERDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as ALTERDATE, SIZES, PARENTFOLDERCODE ,FOLDERCONTENT FROM folder_tb where INSERTDATE=?";
	private static final String SELECT_FOLDER_SQL6 = "select FOLDERCODE, NAME, DESCRIPTION, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(ALTERDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as ALTERDATE, SIZES, PARENTFOLDERCODE ,FOLDERCONTENT FROM folder_tb where ALTERDATE=?";
	private static final String SELECT_FOLDER_SQL68 = "select FOLDERCODE, NAME, DESCRIPTION, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(ALTERDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as ALTERDATE, SIZES, PARENTFOLDERCODE ,FOLDERCONTENT FROM folder_tb where PARENTFOLDERCODE=? and ALTERDATE=?";
	private static final String SELECT_FOLDER_SQL7 = "select FOLDERCODE, NAME, DESCRIPTION, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(ALTERDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as ALTERDATE, SIZES, PARENTFOLDERCODE ,FOLDERCONTENT FROM folder_tb where SIZES=?";
	private static final String SELECT_FOLDER_SQL8_1 = "select FOLDERCODE, NAME, DESCRIPTION, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(ALTERDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as ALTERDATE, SIZES, PARENTFOLDERCODE ,FOLDERCONTENT FROM folder_tb where PARENTFOLDERCODE=?";
	private static final String SELECT_FOLDER_SQL8 = "select FOLDERCODE, NAME, DESCRIPTION, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(ALTERDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as ALTERDATE, SIZES, PARENTFOLDERCODE ,FOLDERCONTENT FROM folder_tb where PARENTFOLDERCODE=? and FOLDERCODE!=PARENTFOLDERCODE";
	private static final String SELECT_FOLDER_SQL8_2 = "select FOLDERCODE, NAME, DESCRIPTION, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(ALTERDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as ALTERDATE, SIZES, PARENTFOLDERCODE ,FOLDERCONTENT FROM folder_tb where PARENTFOLDERCODE=? and FOLDERCODE=PARENTFOLDERCODE";
	
	private static final String DELETE_FOLDER_SQL1 = "DELETE FROM folder_tb WHERE  FOLDERCODE=?";
	private static final String DELETE_FOLDER_SQL4 = "DELETE FROM folder_tb WHERE  PATH=?";
	private static final String DELETE_FOLDER_SQL8 = "DELETE FROM folder_tb WHERE  FOLDERCODE=?";
	//to_timestamp('20021016124324.345','yyyymmddhh24missxff'));
	
	private String makeCode() throws DAOException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement pStatement = null;
		String code=null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(FOLDER_SEQ);
			ResultSet rs = pStatement.executeQuery();
			if(rs.next()){
				code=new String();
				code="folder_"+rs.getString("nextval");
			}
			System.out.println(code);
		
		} catch (SQLException e) {
			throw new DAOException("Error makeCode FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
		return code;
	}
	
	
	public String insertFolder(FolderDTO dto) throws DAOException {
	
		System.out.println("insert Folder start DB "+dto.getPath());
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		String code=this.makeCode();
		System.out.println("insert Folder start DB  "+code);
		System.out.println("insert Folder start DB  "+dto.getInsertDate());
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(INSERT_FOLDER_SQL);
			//FOLDERCODE, NAME, DESCRIPTION, PATH, INSERTDATE, SIZES, PARENTFOLDERCODE
			pStatement.setString(1, code);
			pStatement.setString(2, dto.getName());
			pStatement.setString(3, dto.getDescription());
			pStatement.setString(4, dto.getPath());
			pStatement.setString(5, dto.getInsertDate());
			pStatement.setDouble(6, dto.getSizes());
			if(dto.getParentFolderCode()==null) pStatement.setString(7, code);
			else pStatement.setString(7, dto.getParentFolderCode());
			System.out.println(dto.getFolderContent()+"/"+code);
			pStatement.setString(8, dto.getFolderContent()+"/"+code);
			if(pStatement.executeUpdate()==1)return code;
		} catch (SQLException e) {
			throw new DAOException("Error adding FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
		return null;
	}
	
	public FolderDTO searchFolderCode(String folderCode) throws DAOException{
		
		System.out.println(folderCode);
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		FolderDTO dto = new FolderDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDER_SQL1);
			pStatement.setString(1,folderCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	
	public List<FolderDTO> searchFolderName(String name) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<FolderDTO> dtoList = new ArrayList<FolderDTO>();
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDER_SQL12);
			pStatement.setString(1,name);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderDTO> searchFolderPCode(String parentFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<FolderDTO> dtoList = new ArrayList<FolderDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDER_SQL2);
			pStatement.setString(1,parentFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public FolderDTO searchFolderName(String name,String parentFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		FolderDTO dto = new FolderDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDER_SQL42);
			pStatement.setString(1,parentFolderCode);
			pStatement.setString(2,name);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FolderDTO> searchParentFolderPath(String parentPath) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<FolderDTO> dtoList = new ArrayList<FolderDTO>();
		String sql=SELECT_FOLDER_SQL4_+" '"+parentPath+"\\%'";
		System.out.println("At FolderDAO searchPath : "+sql);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public FolderDTO searchFolderPath(String path) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		FolderDTO dto = null;
		System.out.println("At FolderDAO searchPath : "+path);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDER_SQL4);
			pStatement.setString(1,path);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<FolderDTO> searchFolderIDate(String insertDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<FolderDTO> dtoList = new ArrayList<FolderDTO>();
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDER_SQL5);
			pStatement.setString(1,insertDate);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	
	public List<FolderDTO> searchFolderADate(String alterDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<FolderDTO> dtoList = new ArrayList<FolderDTO>();
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDER_SQL6);
			pStatement.setString(1,alterDate);
			rs = pStatement.executeQuery();
			
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	
	public List<FolderDTO> searchFolderADate(String alterDate,String parendtFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<FolderDTO> dtoList = new ArrayList<FolderDTO>();
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDER_SQL68);
			pStatement.setString(1,parendtFolderCode);
			pStatement.setString(2,alterDate);
			rs = pStatement.executeQuery();
			
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	
	
	public List<FolderDTO> searchFolderSize(double size) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<FolderDTO> dtoList = new ArrayList<FolderDTO>();
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDER_SQL7);
			pStatement.setDouble(1,size);
			rs = pStatement.executeQuery();
			
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<FolderDTO> searchFolderListPCode(String parendtFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<FolderDTO> dtoList = new ArrayList<FolderDTO>();
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDER_SQL8);
			pStatement.setString(1,parendtFolderCode);
			rs = pStatement.executeQuery();
			
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public FolderDTO searchProjectFolder(String parendtFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		FolderDTO dto = null;
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDER_SQL8_2);
			pStatement.setString(1,parendtFolderCode);
			rs = pStatement.executeQuery();
			
			return this.makeDTO(rs, dto);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public List<FolderDTO> searchFolderListPCodes(String parendtFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<FolderDTO> dtoList = new ArrayList<FolderDTO>();
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FOLDER_SQL8_1);
			pStatement.setString(1,parendtFolderCode);
			rs = pStatement.executeQuery();
			
			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public boolean deleteFolderCode(String folderCode)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(DELETE_FOLDER_SQL1);
			pStatement.setString(1, folderCode);
			if(pStatement.executeUpdate()==1)return true;
			return false;
		} catch (SQLException e) {
			throw new DAOException("Error Delete FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	
	public boolean deleteFolderPath(String folderPath)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(DELETE_FOLDER_SQL4);
			pStatement.setString(1, folderPath);
			if(pStatement.executeUpdate()==1)return true;
			return false;
		} catch (SQLException e) {
			throw new DAOException("Error Delete FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	

	public int deleteFolderPCode(String parentFolderCode)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(DELETE_FOLDER_SQL8);
			pStatement.setString(1, parentFolderCode);
			return pStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error Delete FolderDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	public boolean updateFolderName(String code, String newName,String newPath)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(UPDATE_FOLDER_SQL241);
			pStatement.setString(1, newName);
			pStatement.setString(2, newPath);
			pStatement.setString(3, code);
			if(pStatement.executeUpdate()==0)return false;
			return true;
		} catch (SQLException e) {
			throw new DAOException("Error updating Tag. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	

	public boolean updateFolderPCode(String code, String newPath,String newParentCode)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(UPDATE_FOLDER_SQL481);
			pStatement.setString(1, newPath);
			pStatement.setString(2, newParentCode);
			pStatement.setString(3, code);
			if(pStatement.executeUpdate()==0)return false;
			return true;
		} catch (SQLException e) {
			throw new DAOException("Error updating Tag. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	
	public boolean updateFolderADate(String code, String alterDate)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(UPDATE_FOLDER_SQL61);
			pStatement.setString(1, alterDate);
			pStatement.setString(2, code);
			if(pStatement.executeUpdate()==0)return false;
			return true;
		} catch (SQLException e) {
			throw new DAOException("Error updating Tag. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}

	public boolean updateFolderSize(String code, double newSize)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(UPDATE_FOLDER_SQL61);
			pStatement.setDouble(1, newSize);
			pStatement.setString(2, code);
			if(pStatement.executeUpdate()==0)return false;
			return true;
		} catch (SQLException e) {
			throw new DAOException("Error updating Tag. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	private List<FolderDTO> makeDTOList(ResultSet rs, List<FolderDTO> dtoList) throws SQLException{
	
		while(rs.next())
		{
			String folderCode = rs.getString("FolderCode");
			if(rs.wasNull())return dtoList;
			dtoList.add(new FolderDTO(rs.getString("PATH"), folderCode, rs.getString("NAME"), rs.getString("description"), rs.getString("insertDate"), rs.getString("alterDate"), rs.getDouble("sizes"), rs.getString("parentFolderCode"), rs.getString("FOLDERCONTENT")));
		}
		return dtoList;
	}
	
	private FolderDTO makeDTO(ResultSet rs, FolderDTO dto) throws SQLException{
		System.out.println(" FolderDAO  makeDTO...");
		dto=null;
		while(rs.next())
		{
			String folderCode = rs.getString("FolderCode");
			if(rs.wasNull()){
				return dto;
			}
			dto = new FolderDTO(rs.getString("PATH"), folderCode, rs.getString("NAME"), rs.getString("description"), rs.getString("insertDate"), rs.getString("alterDate"), rs.getDouble("sizes"), rs.getString("parentFolderCode"), rs.getString("FOLDERCONTENT"));
		}
		return dto;
	}
}
