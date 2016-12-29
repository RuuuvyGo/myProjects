package dao;



import java.sql.Connection;
import socialProExceptions.DAOException;


public interface DAO {
	
	Connection getConnection() throws  DAOException;

}
