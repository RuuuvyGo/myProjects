package dao;



import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



public class DataSourceCache {

	
	private static DataSourceCache instance=null;
	private DataSource dataSource;
	
	static{
		instance=new DataSourceCache();
	}
	
	private DataSourceCache(){
		dataSourceLookup();
	}

	/*private void start() {
		dataSourceLookup();
		if(dataSource == null) {
			ComboPooledDataSource cpds = new ComboPooledDataSource();
			try {
				cpds.setDriverClass("oracle.jdbc.driver.OracleDriver");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			cpds.setJdbcUrl("jdbc:oracle:thin:@127.0.0.1:1521:XE");
			cpds.setUser("jgdb");
			cpds.setPassword("1234");
			
			cpds.setMinPoolSize(5);
			cpds.setAcquireIncrement(5);
			cpds.setMaxPoolSize(20);
			dataSource = cpds;
		}
	}*/
/*		
	public void shutDown() {
		try {
			DataSources.destroy(dataSource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	private void dataSourceLookup(){
		Context context = null;
		try {
			context = new InitialContext();
			Context envContext = (Context)context.lookup("java:comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/myoracle");
		} catch(NamingException e) {
			System.out.println(e.getMessage());
		}
	}
	public static DataSourceCache getInstance(){
		if(instance==null){
			instance=new DataSourceCache();
		}
		return instance;
	}
	public DataSource getDataSource(){
		return dataSource;
	}
}
