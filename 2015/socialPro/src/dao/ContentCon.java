package dao;

import java.sql.SQLException;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ContentCon implements Conn{

	private Session session=null;   

	
	public ContentCon() {
		this.session=null;
	}
	public ContentCon(Session session) {
		this.session=session;
	}

	@Override
	public  Object getConnection() throws JSchException {
		// TODO Auto-generated method stub
		
		String host = "192.168.142.131"; // host IP  
		String user = "sshUser_DIFF"; // username for SSH connection  
		String password = "1234"; // password for SSH connection  
		int port = 22; // default SSH port
		
		JSch shell = new JSch();  
        // get a new session    
        Session session=null;
        
        try{
			session = shell.getSession(user, host, port);
	
	        // set user password and connect to a channel  
	        session.setUserInfo(new SSHUserInfo(password));  
	        session.connect();
        }catch (JSchException e) {
        	e.printStackTrace();
			throw new JSchException();
		} 
		return session;
	}

	
	public void closeConnection(){
		if(this.session!=null){
			this.session.disconnect();
		}
	}
}

