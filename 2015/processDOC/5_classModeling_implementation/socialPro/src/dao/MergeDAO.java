package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import socialProExceptions.DAOException;
import dto.AlarmDTO;
import dto.MergeDTO;

public class MergeDAO extends BaseDAO{
	
	private static final String INSERT_MERGE_SQL = 
			"insert into MERGE_tb(COMMITCODE,APPLIEDCOMMITCODE) values(?,?)";
	
	//private static final String UPDATE_MERGE_SQL21 = "update MERGE_tb set checkDate=to_timeStamp(?,'yyyy-mm-dd hh24:mi:ss.ff3') where MERGECODE=?";
	
	private static final String SELECT_MERGE_SQL1 = "select * FROM MERGE_tb where COMMITCODE=?";
	private static final String SELECT_MERGE_SQL2 = "select * FROM MERGE_tb where APPLIEDCOMMITCODE=?";
	private static final String SELECT_MERGE_SQL3 = "select * FROM MERGE_tb where COMMITCODE=? and APPLIEDCOMMITCODE=?";
	
	
	/*private static final String DELETE_MERGE_SQL1 = "DELETE FROM MERGE_tb WHERE  MERGECODE=?";
	private static final String DELETE_MERGE_SQL46 = "DELETE FROM MERGE_tb WHERE  SOURCECODE=? and sendDate < to_timeStamp(?, 'YYYY-MM-DD HH24:MI:SS.ff3')";
	private static final String DELETE_MERGE_SQL6 = "DELETE FROM MERGE_tb WHERE  SOURCECODE=?";
*/

///////////////////////////////////////  insert   ////////////////////////////////////////////////////////	
	
	public boolean insertMerge(MergeDTO dto) throws DAOException {
	
		//ALARMCODE,SENDERCODE,RECEIVERCODE,TYPE, TITLE, CONTENT, SENDDATE, SOURCECODE
		
		System.out.println("insert merge start");
		Connection connection = null;
		PreparedStatement pStatement = null;
		String commitCode = dto.getCommitCode();
		List<String> appliedCommitCodeList = dto.getAppliedCommitCodeList();
		try {
			connection = getConnection();
			int size = appliedCommitCodeList.size();
			if(size==1){
				pStatement = connection.prepareStatement(INSERT_MERGE_SQL);
				pStatement.setString(1, dto.getCommitCode());
				pStatement.setString(2, appliedCommitCodeList.get(0));
			}else{
				String sql = "Insert All ";
				for(int i=0;i<size;i++){
					sql+="into MERGE_tb(COMMITCODE,APPLIEDCOMMITCODE) values ('"+commitCode+"','"+appliedCommitCodeList.get(i)+"') ";
				}sql+="select * from dual";
				System.out.println(sql);
				pStatement = connection.prepareStatement(sql);
			}
			
			if(pStatement.executeUpdate()>=1)return true;
		} catch (SQLException e) {
			throw new DAOException("Error adding MergeDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
		return false;
	}

///////////////////////////////////////  search   ////////////////////////////////////////////////////////
	
	public MergeDTO searchCommitCode(String commitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		MergeDTO dto = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_MERGE_SQL1);
			pStatement.setString(1,commitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search MergeDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}

	public MergeDTO searchAppliedCommitCode(String appliedCommitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		MergeDTO dto = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_MERGE_SQL2);
			pStatement.setString(1,appliedCommitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search MergeDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}


	public MergeDTO searchCommitCode(String commitCode,String appliedCommitCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		MergeDTO dto = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_MERGE_SQL3);
			pStatement.setString(1,commitCode);
			pStatement.setString(2,appliedCommitCode);
			rs = pStatement.executeQuery();
			return this.makeDTO(rs, dto);
		} catch (SQLException e) {
			throw new DAOException("Error search MergeDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
	}
	private MergeDTO makeDTO(ResultSet rs, MergeDTO dto) throws SQLException{
		
		System.out.println("makeDTO...");
		dto=null;

		String commitCode = rs.getString("COMMITCODE");
		if(rs.wasNull()){
			return dto;
		}
		dto = new MergeDTO();
		dto.setCommitCode(commitCode);
		dto.getAppliedCommitCodeList().add(rs.getString("APPLIEDCOMMITCODE"));
		String apcCode = null;
		while(rs.next())
		{
			apcCode = rs.getString("APPLIEDCOMMITCODE");
			if(apcCode!=null)dto.getAppliedCommitCodeList().add(rs.getString("APPLIEDCOMMITCODE"));
		}
		return dto;
	}
}
