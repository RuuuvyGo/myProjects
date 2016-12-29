package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import socialProExceptions.DAOException;
import dto.AlarmDTO;
import dto.CopiedFileCommitVDTO;

public class CopiedFileCommitVDAO extends BaseDAO{

	//commitCode, copiedFileCode, copiedOwnerCode, originFileCode, originOwnerCode, title, description,  to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, content, merge,TYPE
	private static final String SELECT_COPIEDFILECOMMIT_SQL1 = "SELECT DISTINCT commitCode, copiedFileCode, copiedOwnerCode, originFileCode, originOwnerCode, title, description,  to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, content, merge,TYPE FROM COPIEDFILECOMMIT_VIEW where COMMITCODE=?";
	private static final String SELECT_COPIEDFILECOMMIT_SQL2 = "SELECT DISTINCT commitCode, copiedFileCode, copiedOwnerCode, originFileCode, originOwnerCode, title, description,  to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, content, merge,TYPE FROM COPIEDFILECOMMIT_VIEW where TYPE=?";
	private static final String SELECT_COPIEDFILECOMMIT_SQL3 = "SELECT DISTINCT commitCode, copiedFileCode, copiedOwnerCode, originFileCode, originOwnerCode, title, description,  to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, content, merge,TYPE FROM COPIEDFILECOMMIT_VIEW where COPIEDFILECODE=?";
	private static final String SELECT_COPIEDFILECOMMIT_SQL4 = "SELECT DISTINCT commitCode, copiedFileCode, copiedOwnerCode, originFileCode, originOwnerCode, title, description,  to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, content, merge,TYPE FROM COPIEDFILECOMMIT_VIEW where ORIGINFILECODE=?";
	
	public CopiedFileCommitVDTO searchCommitCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		CopiedFileCommitVDTO dto = new CopiedFileCommitVDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDFILECOMMIT_SQL1);
			pStatement.setString(1,commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<CopiedFileCommitVDTO> searchType(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitVDTO> dtoList = new ArrayList<CopiedFileCommitVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDFILECOMMIT_SQL2);
			pStatement.setInt(1,type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitVDTO> searchCopiedFileCode(String copiedFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitVDTO> dtoList = new ArrayList<CopiedFileCommitVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDFILECOMMIT_SQL3);
			pStatement.setString(1, copiedFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<CopiedFileCommitVDTO> searchOriginFileCode(String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<CopiedFileCommitVDTO> dtoList = new ArrayList<CopiedFileCommitVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_COPIEDFILECOMMIT_SQL4);
			pStatement.setString(1, originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search CopiedFileCommitVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	

	private List<CopiedFileCommitVDTO> makeDTOList(ResultSet rs, List<CopiedFileCommitVDTO> dtoList) throws SQLException{
		
		if(dtoList==null){dtoList = new ArrayList<CopiedFileCommitVDTO>();}
		while(rs.next())
		{
			String commitCode = rs.getString("COMMITCODE");
			if(rs.wasNull()){
				return null;
			}
			//commitCode, copiedFileCode, copiedOwnerCode, originFileCode, originOwnerCode, title, description,  to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, content, merge,TYPE
			dtoList.add(new CopiedFileCommitVDTO(commitCode, rs.getString("originFileCode"), rs.getString("originOwnerCode"), rs.getString("title"), rs.getString("description"), rs.getString("INSERTDATE"), rs.getString("content"), rs.getInt("merge"), rs.getInt("TYPE"), rs.getString("copiedFileCode"), rs.getString("copiedOwnerCode")));
		}
		return dtoList;
	}
	
	private CopiedFileCommitVDTO makeDTO(ResultSet rs, CopiedFileCommitVDTO dto) throws SQLException{
		
		System.out.println("makeDTO...");
		dto=null;
		while(rs.next())
		{
			String commitCode = rs.getString("COMMITCODE");
			if(rs.wasNull()){
				return null;
			}
			dto = new CopiedFileCommitVDTO(commitCode, rs.getString("originFileCode"), rs.getString("originOwnerCode"), rs.getString("title"), rs.getString("description"), rs.getString("INSERTDATE"), rs.getString("content"), rs.getInt("merge"), rs.getInt("TYPE"), rs.getString("copiedFileCode"), rs.getString("copiedOwnerCode"));
		}
		return dto;
	}
}
