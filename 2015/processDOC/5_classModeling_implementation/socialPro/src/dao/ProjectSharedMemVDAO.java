package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ProjectSharedMemVDTO;

import socialProExceptions.DAOException;

public class ProjectSharedMemVDAO extends BaseDAO{

	private static final String SELECT_PROJECTSHAREDMEM_SQL = "SELECT DISTINCT * FROM ProjectSharedMem_view where ";
	private static final String SELECT_PROJECTSHAREDMEM_SQL1 = "SELECT DISTINCT * FROM ProjectSharedMem_view where PROJECTCODE=?";
	private static final String SELECT_PROJECTSHAREDMEM_SQL2 = "SELECT DISTINCT * FROM ProjectSharedMem_view where PROJECTNAME=?";
	private static final String SELECT_PROJECTSHAREDMEM_SQL3 = "SELECT DISTINCT * FROM ProjectSharedMem_view where MEMBERCODE=?";
	private static final String SELECT_PROJECTSHAREDMEM_SQL4 = "SELECT DISTINCT * FROM ProjectSharedMem_view where NICKNAME=?";
	private static final String SELECT_PROJECTSHAREDMEM_SQL5 = "SELECT DISTINCT * FROM ProjectSharedMem_view where EMAIL=?";

	
	public List<ProjectSharedMemVDTO> searchProjectCode(String projectCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<ProjectSharedMemVDTO> dtoList = new ArrayList<ProjectSharedMemVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_PROJECTSHAREDMEM_SQL1);
			pStatement.setString(1,projectCode);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				ProjectSharedMemVDTO dto=new ProjectSharedMemVDTO();
				dto.setProjectCode(rs.getString("projectCode"));
				dto.setProjectName(rs.getString("projectName"));
				dto.setMemberCode(rs.getString("memberCode"));
				dto.setNickName(rs.getString("nickName"));
				dto.setEmail(rs.getString("email"));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectSharedMemVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}

	public List<ProjectSharedMemVDTO> searchProjectName(String projectName) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<ProjectSharedMemVDTO> dtoList = new ArrayList<ProjectSharedMemVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_PROJECTSHAREDMEM_SQL2);
			pStatement.setString(1,projectName);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				ProjectSharedMemVDTO dto=new ProjectSharedMemVDTO();
				dto.setProjectCode(rs.getString("projectCode"));
				dto.setProjectName(rs.getString("projectName"));
				dto.setMemberCode(rs.getString("memberCode"));
				dto.setNickName(rs.getString("nickName"));
				dto.setEmail(rs.getString("email"));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectSharedMemVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}
	

	public List<ProjectSharedMemVDTO> searchMemberCode(String memberCode) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<ProjectSharedMemVDTO> dtoList = new ArrayList<ProjectSharedMemVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_PROJECTSHAREDMEM_SQL3);
			pStatement.setString(1,memberCode);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				ProjectSharedMemVDTO dto=new ProjectSharedMemVDTO();
				dto.setProjectCode(rs.getString("projectCode"));
				dto.setProjectName(rs.getString("projectName"));
				dto.setMemberCode(rs.getString("memberCode"));
				dto.setNickName(rs.getString("nickName"));
				dto.setEmail(rs.getString("email"));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectSharedMemVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}
	

	public List<ProjectSharedMemVDTO> searchEmail(String email) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<ProjectSharedMemVDTO> dtoList = new ArrayList<ProjectSharedMemVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_PROJECTSHAREDMEM_SQL5);
			pStatement.setString(1,email);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				ProjectSharedMemVDTO dto=new ProjectSharedMemVDTO();
				dto.setProjectCode(rs.getString("projectCode"));
				dto.setProjectName(rs.getString("projectName"));
				dto.setMemberCode(rs.getString("memberCode"));
				dto.setNickName(rs.getString("nickName"));
				dto.setEmail(rs.getString("email"));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectSharedMemVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}
	

	public List<ProjectSharedMemVDTO> searchNickName(String nickName) throws DAOException{
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet rs=null;
		List<ProjectSharedMemVDTO> dtoList = new ArrayList<ProjectSharedMemVDTO>();
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(SELECT_PROJECTSHAREDMEM_SQL4);
			pStatement.setString(1,nickName);
			rs = pStatement.executeQuery();
			while(rs.next())
			{
				ProjectSharedMemVDTO dto=new ProjectSharedMemVDTO();
				dto.setProjectCode(rs.getString("projectCode"));
				dto.setProjectName(rs.getString("projectName"));
				dto.setMemberCode(rs.getString("memberCode"));
				dto.setNickName(rs.getString("nickName"));
				dto.setEmail(rs.getString("email"));
				dtoList.add(dto);
			}
			return dtoList;
		} catch (SQLException e) {
			throw new DAOException("Error search ProjectSharedMemVDTO. "+ e.getMessage());
		} finally {
			closeDBObjects(rs, pStatement, connection);
		}
		
	}
	
}
