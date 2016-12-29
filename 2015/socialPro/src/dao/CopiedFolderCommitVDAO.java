package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import socialProExceptions.DAOException;
import dto.AlarmDTO;
import dto.CopiedFolderCommitVDTO;

public class CopiedFolderCommitVDAO extends BaseDAO{

	//commitCode, copiedFolderCode, copiedOwnerCode, originFolderCode, originOwnerCode, title, description, to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, content, merge,TYPE
	private static final String SELECT_COPIEDFOLDERCOMMIT_SQL1 = "SELECT DISTINCT commitCode, copiedFolderCode, copiedOwnerCode, originFolderCode, originOwnerCode, title, description, to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, content, merge,TYPE FROM COPIEDFOLDERCOMMIT_VIEW where COMMITCODE=?";
	private static final String SELECT_COPIEDFOLDERCOMMIT_SQL2 = "SELECT DISTINCT commitCode, copiedFolderCode, copiedOwnerCode, originFolderCode, originOwnerCode, title, description, to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, content, merge,TYPE FROM COPIEDFOLDERCOMMIT_VIEW where TYPE=?";
	private static final String SELECT_COPIEDFOLDERCOMMIT_SQL3 = "SELECT DISTINCT commitCode, copiedFolderCode, copiedOwnerCode, originFolderCode, originOwnerCode, title, description, to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, content, merge,TYPE FROM COPIEDFOLDERCOMMIT_VIEW where COPIEDFOLDERCODE=?";
	private static final String SELECT_COPIEDFOLDERCOMMIT_SQL4 = "SELECT DISTINCT commitCode, copiedFolderCode, copiedOwnerCode, originFolderCode, originOwnerCode, title, description, to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, content, merge,TYPE FROM COPIEDFOLDERCOMMIT_VIEW where ORIGINFOLDERCODE=?";
	
	public CopiedFolderCommitVDTO searchCommitCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		CopiedFolderCommitVDTO dto = new CopiedFolderCommitVDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDFOLDERCOMMIT_SQL1);
			pStatement.setString(1,commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CopiedFolderCommitVDTO> searchType(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitVDTO> dtoList = new ArrayList<CopiedFolderCommitVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDFOLDERCOMMIT_SQL2);
			pStatement.setInt(1,type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitVDTO> searchCopiedFolderCode(String copiedFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitVDTO> dtoList = new ArrayList<CopiedFolderCommitVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDFOLDERCOMMIT_SQL3);
			pStatement.setString(1, copiedFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFolderCommitVDTO> searchOriginFolderCode(String originFolderCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFolderCommitVDTO> dtoList = new ArrayList<CopiedFolderCommitVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDFOLDERCOMMIT_SQL4);
			pStatement.setString(1, originFolderCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFolderCommitVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	

	private List<CopiedFolderCommitVDTO> makeDTOList(ResultSet rs, List<CopiedFolderCommitVDTO> dtoList) throws SQLException{
		
		if(dtoList==null){dtoList = new ArrayList<CopiedFolderCommitVDTO>();}
		while(rs.next())
		{
			String commitCode = rs.getString("COMMITCODE");
			if(rs.wasNull()){
				return dtoList;
			}
			dtoList.add(new CopiedFolderCommitVDTO(commitCode, rs.getString("originFolderCode"), rs.getString("originOwnerCode"), rs.getString("title"), rs.getString("description"), rs.getString("INSERTDATE"), rs.getString("content"), rs.getInt("merge"), rs.getInt("TYPE"), rs.getString("copiedFolderCode"), rs.getString("copiedOwnerCode")));
		}
		return dtoList;
	}
	
	private CopiedFolderCommitVDTO makeDTO(ResultSet rs, CopiedFolderCommitVDTO dto) throws SQLException{
		
		System.out.println("makeDTO...");
		dto=null;
		while(rs.next())
		{
			String commitCode = rs.getString("COMMITCODE");
			if(rs.wasNull()){
				return dto;
			}
			dto = new CopiedFolderCommitVDTO(commitCode, rs.getString("originFolderCode"), rs.getString("originOwnerCode"), rs.getString("title"), rs.getString("description"), rs.getString("INSERTDATE"), rs.getString("content"), rs.getInt("merge"), rs.getInt("TYPE"), rs.getString("copiedFolderCode"), rs.getString("copiedOwnerCode"));
		}
		return dto;
	}
}
