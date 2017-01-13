package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.FileDTO;

import socialProExceptions.DAOException;

public class FileDAO extends BaseDAO{
	
	private static final String FILE_SEQ = 
			"select FILE_seq.nextval from dual";
	private static final String INSERT_FILE_SQL = 
			"insert into FILE_tb (FileCODE, FOLDERCODE,NAME, PATH, INSERTDATE, SIZES, FILECONTENT) values (?,?,?,?,to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff3'),?,?)";
	
	private static final String UPDATE_FILE_SQL241 = "update FILE_tb set FOLDERCODE=? ,path=?"+ "where FileCODE=?";
	private static final String UPDATE_FILE_SQL341 = "update FILE_tb set name=?,path=? where FileCODE=?";
	private static final String UPDATE_FILE_SQL61 = "update FILE_tb set ALTERDATE=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff3') where FileCODE=?";
	private static final String UPDATE_FILE_SQL71 = "update FILE_tb set sizes=?"+ "where FileCODE=?";
	
	private static final String SELECT_FILE_SQL1 = "select FileCODE, FOLDERCODE,NAME, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(alterDate,'yyyy-mm-dd hh24:mi:ss.ff3') as alterDate, SIZES, FILECONTENT FROM FILE_tb where FileCODE=?";
	private static final String SELECT_FILE_SQL2 = "select FileCODE, FOLDERCODE,NAME, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(alterDate,'yyyy-mm-dd hh24:mi:ss.ff3') as alterDate, SIZES, FILECONTENT FROM FILE_tb where FOLDERCODE=? ";
	private static final String SELECT_FILE_SQL23 = "select FileCODE, FOLDERCODE,NAME, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(alterDate,'yyyy-mm-dd hh24:mi:ss.ff3') as alterDate, SIZES, FILECONTENT FROM FILE_tb where FOLDERCODE=? and NAME=?";
	private static final String SELECT_FILE_SQL4 = "select FileCODE, FOLDERCODE,NAME, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(alterDate,'yyyy-mm-dd hh24:mi:ss.ff3') as alterDate, SIZES, FILECONTENT FROM FILE_tb where PATH=?";
	private static final String SELECT_FILE_SQL4_ = "select FileCODE, FOLDERCODE,NAME, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(alterDate,'yyyy-mm-dd hh24:mi:ss.ff3') as alterDate, SIZES, FILECONTENT FROM FILE_tb where PATH like ";
	private static final String SELECT_FILE_SQL5 = "select FileCODE, FOLDERCODE,NAME, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(alterDate,'yyyy-mm-dd hh24:mi:ss.ff3') as alterDate, SIZES, FILECONTENT FROM FILE_tb where INSERTDATE=to_char(?,'yyyy-mm-dd hh24:mi:ss.ff3')";
	private static final String SELECT_FILE_SQL6 = "select FileCODE, FOLDERCODE,NAME, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(alterDate,'yyyy-mm-dd hh24:mi:ss.ff3') as alterDate, SIZES, FILECONTENT FROM FILE_tb where ALTERDATE=to_char(?,'yyyy-mm-dd hh24:mi:ss.ff3')";
	private static final String SELECT_FILE_SQL26 = "select FileCODE, FOLDERCODE,NAME, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(alterDate,'yyyy-mm-dd hh24:mi:ss.ff3') as alterDate, SIZES, FILECONTENT FROM FILE_tb where FOLDERCODE=? and ALTERDATE<to_char(?,'yyyy-mm-dd hh24:mi:ss.ff3')";
	private static final String SELECT_FILE_SQL7 = "select FileCODE, FOLDERCODE,NAME, PATH, to_char(insertdate,'yyyy-mm-dd hh24:mi:ss.ff3') as insertdate, to_char(alterDate,'yyyy-mm-dd hh24:mi:ss.ff3') as alterDate, SIZES, FILECONTENT FROM FILE_tb where SIZES=?";
	
	private static final String DELETE_FILE_SQL1 = "DELETE FROM FILE_tb WHERE  FileCODE=?";
	private static final String DELETE_FILE_SQL2 = "DELETE FROM FILE_tb WHERE  FOLDERCODE=?";
	private static final String DELETE_FILE_SQL4 = "DELETE FROM FILE_tb WHERE  PATH=?";
	

	private String makeCode() throws DAOException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement pStatement = null;
		String code=null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(FILE_SEQ);
			ResultSet rs = pStatement.executeQuery();
			if(rs.next()){
				code=new String();
				code="file_"+rs.getString("nextval");
			}
			System.out.println(code);
		
		} catch (SQLException e) {
			throw new DAOException("Error makeCode FileDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
		return code;
	}
	
	
	public String insertFile(FileDTO dto) throws DAOException {
	
		System.out.println("insertFile start  "+dto.getPath());
		Connection connection = null;
		PreparedStatement pStatement = null;
		String code=this.makeCode();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(INSERT_FILE_SQL);
			pStatement.setString(1, code);
			pStatement.setString(2, dto.getFolderCode());
			pStatement.setString(3, dto.getName());
			pStatement.setString(4, dto.getPath());
			pStatement.setString(5, dto.getInsertDate());
			pStatement.setDouble(6, dto.getSizes());
			pStatement.setString(7, dto.getFileContent()+"/"+code);
			if(pStatement.executeUpdate()==1)return code;
		} catch (SQLException e) {
			throw new DAOException("Error adding FileDAO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
		return null;
	}
	
	public FileDTO searchFileCode(String fileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		FileDTO dto = new FileDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILE_SQL1);
			pStatement.setString(1,fileCode);
			System.out.println(SELECT_FILE_SQL1+" "+fileCode);
			ResultSet rs = pStatement.executeQuery();
			
			return this.makeDTO(rs, dto);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FileDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	
	public List<FileDTO> searchFolderCode(String folderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileDTO> dtoList = new ArrayList<FileDTO>();
		ResultSet rs=null;
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILE_SQL2);
			pStatement.setString(1,folderCode);
			rs = pStatement.executeQuery();

			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FileDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	
	public FileDTO searchFolderCode(String folderCode,String fileName) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		FileDTO dto = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILE_SQL23);
			pStatement.setString(1,folderCode);
			pStatement.setString(2,fileName);
			ResultSet rs = pStatement.executeQuery();

			return this.makeDTO(rs, dto);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FileDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	


	public List<FileDTO> searchFolderCodeADate(String folderCode,String alterDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileDTO> dtoList = new ArrayList<FileDTO>();
		ResultSet rs=null;
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILE_SQL26);
			pStatement.setString(1,folderCode);
			pStatement.setString(2,alterDate);
			rs = pStatement.executeQuery();

			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FileDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public FileDTO searchFilePath(String filePath) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		FileDTO dto = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILE_SQL4);
			pStatement.setString(1,filePath);
			rs = pStatement.executeQuery();

			return this.makeDTO(rs, dto);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FileDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	public List<FileDTO> searchFileByFPath(String folderPath) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<FileDTO> dtoList = new ArrayList<FileDTO>();
		String sql = SELECT_FILE_SQL4_+" '"+folderPath+"\\%'";
		System.out.println(sql);
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			rs = pStatement.executeQuery();

			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FileDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<FileDTO> searchFileIDate(String inserDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileDTO> dtoList = new ArrayList<FileDTO>();
		ResultSet rs=null;
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILE_SQL5);
			pStatement.setString(1,inserDate);
			rs = pStatement.executeQuery();

			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FileDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<FileDTO> searchFileADate(String inserDate) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileDTO> dtoList = new ArrayList<FileDTO>();
		ResultSet rs=null;
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILE_SQL6);
			pStatement.setString(1,inserDate);
			rs = pStatement.executeQuery();

			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FileDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<FileDTO> searchFileSize(double sizes) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<FileDTO> dtoList = new ArrayList<FileDTO>();
		ResultSet rs=null;
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_FILE_SQL5);
			pStatement.setDouble(1,sizes);
			rs = pStatement.executeQuery();

			return this.makeDTOList(rs, dtoList);
			
		} catch (SQLException e) {
			throw new DAOException("Error search FileDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	public int updateFolderCode(String fileCode,String newFolderCode, String newPath)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(UPDATE_FILE_SQL241);
			pStatement.setString(1, newFolderCode);
			pStatement.setString(2, newPath);
			pStatement.setString(3, fileCode);
			return pStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error updating FileDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	

	public boolean updateFileName(String fileCode,String newFileName, String newPath)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(UPDATE_FILE_SQL341);
			pStatement.setString(1, newFileName);
			pStatement.setString(2, newPath);
			pStatement.setString(3, fileCode);
			if(pStatement.executeUpdate()==0)return false;
			return true;
		} catch (SQLException e) {
			throw new DAOException("Error updating FileDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}


	public boolean updateFileADtae(String fileCode,String newAlterDate)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(UPDATE_FILE_SQL61);
			pStatement.setString(1, newAlterDate);
			pStatement.setString(2, fileCode);
			if(pStatement.executeUpdate()==0)return false;
			return true;
		} catch (SQLException e) {
			throw new DAOException("Error updating FileDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}


	public boolean updateFileSize(String fileCode,double newSize)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(UPDATE_FILE_SQL71);
			pStatement.setDouble(1, newSize);
			pStatement.setString(2, fileCode);
			if(pStatement.executeUpdate()==0)return false;
			return true;
		} catch (SQLException e) {
			throw new DAOException("Error updating FileDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	

	public boolean deleteFileCode(String fileCode)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(DELETE_FILE_SQL1);
			pStatement.setString(1, fileCode);
			if(pStatement.executeUpdate()==1)return true;
			return false;
		} catch (SQLException e) {
			throw new DAOException("Error Delete FileDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}

	public int deleteFolderCode(String folderCode)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(DELETE_FILE_SQL2);
			pStatement.setString(1, folderCode);
			return pStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error Delete FileDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	

	public boolean deleteFilePath(String path)throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(DELETE_FILE_SQL4);
			pStatement.setString(1, path);
			if(pStatement.executeUpdate()==1)return true;
			return false;
		} catch (SQLException e) {
			throw new DAOException("Error Delete FileDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
	
	private FileDTO makeDTO(ResultSet rs, FileDTO dto) throws SQLException{
		
		while(rs.next())
		{
			dto = new FileDTO();
			dto.setFileCode(rs.getString("fileCode"));
			if(rs.wasNull())return null;
			dto.setFolderCode(rs.getString("folderCode"));
			dto.setName(rs.getString("name"));
			dto.setPath(rs.getString("path"));
			System.out.println(dto.getPath());
			dto.setInsertDate(rs.getString("insertDate"));
			dto.setAlterDate(rs.getString("alterDate"));
			dto.setSizes(rs.getDouble("sizes"));
			dto.setFileContent(rs.getString("fileContent"));
		}
		return dto;
	}
	
	private List<FileDTO> makeDTOList(ResultSet rs, List<FileDTO> dtoList) throws SQLException{
		
		while(rs.next())
		{
			FileDTO dto = new FileDTO();
			dto.setFileCode(rs.getString("fileCode"));
			if(rs.wasNull())return dtoList;
			dto.setFolderCode(rs.getString("folderCode"));
			dto.setName(rs.getString("name"));
			dto.setPath(rs.getString("path"));
			dto.setInsertDate(rs.getString("insertDate"));
			dto.setAlterDate(rs.getString("alterDate"));
			dto.setSizes(rs.getDouble("sizes"));
			dto.setFileContent(rs.getString("fileContent"));
			dtoList.add(dto);
		}
		return dtoList;
		
	}
}
