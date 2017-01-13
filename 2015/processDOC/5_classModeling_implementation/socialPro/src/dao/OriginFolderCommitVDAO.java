package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import socialProExceptions.DAOException;
import dto.OriginFolderCommitVDTO;

public class OriginFolderCommitVDAO extends BaseDAO{

	//commitCode, folderCode, OwnerCode, title, description, to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, content, merge,TYPE
	private static final String SELECT_ORIGINFOLDERCOMMITV_SQL1 = "SELECT DISTINCT commitCode, folderCode, OwnerCode, title, description, to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, content, merge,TYPE FROM ORIGINFOLDERCOMMIT_VIEW where COMMITCODE=?";
	private static final String SELECT_ORIGINFOLDERCOMMITV_SQL2 = "SELECT DISTINCT commitCode, folderCode, OwnerCode, title, description, to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, content, merge,TYPE FROM ORIGINFOLDERCOMMIT_VIEW where TYPE=?";
	private static final String SELECT_ORIGINFOLDERCOMMITV_SQL3 = "SELECT DISTINCT commitCode, folderCode, OwnerCode, title, description, to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, content, merge,TYPE FROM ORIGINFOLDERCOMMIT_VIEW where ORIGINFOLDERCODE=?";
	
	
	
	public OriginFolderCommitVDTO searchCommitCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		OriginFolderCommitVDTO dto = new OriginFolderCommitVDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIGINFOLDERCOMMITV_SQL1);
			pStatement.setString(1,commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<OriginFolderCommitVDTO> searchType(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitVDTO> dtoList = new ArrayList<OriginFolderCommitVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIGINFOLDERCOMMITV_SQL2);
			pStatement.setInt(1, type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFolderCommitVDTO> searchOriginFolderCode(String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFolderCommitVDTO> dtoList = new ArrayList<OriginFolderCommitVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIGINFOLDERCOMMITV_SQL3);
			pStatement.setString(1, originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFolderCommitVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	

	private List<OriginFolderCommitVDTO> makeDTOList(ResultSet rs, List<OriginFolderCommitVDTO> dtoList) throws SQLException{
		
		if(dtoList==null){dtoList = new ArrayList<OriginFolderCommitVDTO>();}
		while(rs.next())
		{
			String commitCode = rs.getString("ALARMCODE");
			if(rs.wasNull()){
				return dtoList;
			}
			dtoList.add(new OriginFolderCommitVDTO(commitCode, rs.getString("originFolderCode"), rs.getString("originOwnerCode"), rs.getString("title"), rs.getString("description"), rs.getString("INSERTDATE"), rs.getString("content"), rs.getInt("merge"), rs.getInt("TYPE")));
		}
		return dtoList;
	}
	
	private OriginFolderCommitVDTO makeDTO(ResultSet rs, OriginFolderCommitVDTO dto) throws SQLException{
		
		System.out.println("makeDTO...");
		dto=null;
		while(rs.next())
		{
			String commitCode = rs.getString("COMMITCODE");
			if(rs.wasNull()){
				return dto;
			}
			dto = new OriginFolderCommitVDTO(commitCode, rs.getString("originFolderCode"), rs.getString("originOwnerCode"), rs.getString("title"), rs.getString("description"), rs.getString("INSERTDATE"), rs.getString("content"), rs.getInt("merge"), rs.getInt("TYPE"));
		}
		return dto;
	}
}
