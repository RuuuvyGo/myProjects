package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import socialProExceptions.DAOException;
import dto.AlarmDTO;
import dto.OriginFileCommitVDTO;

public class OriginFileCommitVDAO extends BaseDAO{

	private static final String SELECT_ORIGINFILECOMMITV_SQL1 = "SELECT DISTINCT commitCode, title,	ownerCode, description, to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, fileCode, content, merge,TYPE FROM ORIGINFILECOMMIT_VIEW where COMMITCODE=?";
	private static final String SELECT_ORIGINFILECOMMITV_SQL2 = "SELECT DISTINCT commitCode, title,	ownerCode, description, to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, fileCode, content, merge,TYPE FROM ORIGINFILECOMMIT_VIEW where TYPE=?";
	private static final String SELECT_ORIGINFILECOMMITV_SQL3 = "SELECT DISTINCT commitCode, title,	ownerCode, description, to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, fileCode, content, merge,TYPE FROM ORIGINFILECOMMIT_VIEW where ORIGINFILECODE=?";
	
	
	
	public OriginFileCommitVDTO searchCommitCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		OriginFileCommitVDTO dto = new OriginFileCommitVDTO();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIGINFILECOMMITV_SQL1);
			pStatement.setString(1,commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	

	public List<OriginFileCommitVDTO> searchType(int type) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitVDTO> dtoList = new ArrayList<OriginFileCommitVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIGINFILECOMMITV_SQL2);
			pStatement.setInt(1, type);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public List<OriginFileCommitVDTO> searchOriginFileCode(String originFileCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		List<OriginFileCommitVDTO> dtoList = new ArrayList<OriginFileCommitVDTO>();
		ResultSet rs=null;		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_ORIGINFILECOMMITV_SQL3);
			pStatement.setString(1, originFileCode);
			rs = pStatement.executeQuery();
			return this.makeDTOList(rs, dtoList);
		} catch (SQLException e) {
			throw new DAOException("Error search OriginFileCommitVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	
	

	private List<OriginFileCommitVDTO> makeDTOList(ResultSet rs, List<OriginFileCommitVDTO> dtoList) throws SQLException{
		
		if(dtoList==null){dtoList = new ArrayList<OriginFileCommitVDTO>();}
		while(rs.next())
		{
			String commitCode = rs.getString("COMMITCODE");
			if(rs.wasNull()){
				return dtoList;
			}
			dtoList.add(new OriginFileCommitVDTO(commitCode, rs.getString("originFileCode"), rs.getString("originOwnerCode"), rs.getString("title"), rs.getString("description"), rs.getString("INSERTDATE"), rs.getString("content"), rs.getInt("merge"), rs.getInt("TYPE")));
		}
		return dtoList;
	}
	
	private OriginFileCommitVDTO makeDTO(ResultSet rs, OriginFileCommitVDTO dto) throws SQLException{
		
		System.out.println("makeDTO...");
		dto=null;
		while(rs.next())
		{
			String commitCode = rs.getString("COMMITCODE");
			if(rs.wasNull()){
				return dto;
			}
			dto = new OriginFileCommitVDTO(commitCode, rs.getString("originFileCode"), rs.getString("originOwnerCode"), rs.getString("title"), rs.getString("description"), rs.getString("INSERTDATE"), rs.getString("content"), rs.getInt("merge"), rs.getInt("TYPE"));
		}
		return dto;
	}
}
