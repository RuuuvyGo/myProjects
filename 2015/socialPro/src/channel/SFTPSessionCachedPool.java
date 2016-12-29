package channel;

import channel.SessionCachedPool.SSHUserInfo;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SFTPSessionCachedPool extends SessionCachedPool{

	private static SFTPSessionCachedPool INSTANCE;
	private MyCircularQueue sftpSessions;
	
	static{
		System.out.println("static  !!!");
		try {
			INSTANCE = new SFTPSessionCachedPool();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private SFTPSessionCachedPool() throws JSchException {
		super();
		System.out.println("SFTPSessionCachedPool  con");
		this.sftpSessions = new MyCircularQueue();
		
		for(int i=0;i<10;i++){
			Session s = super.shell.getSession(super.user, super.host, super.port);
			s.setUserInfo(new SSHUserInfo(super.pw));  
			s.connect(); 
			/*ChannelSftp sftp1=(ChannelSftp)s.openChannel("sftp");
			this.sftpSessions.put(sftp1);*/
			this.sftpSessions.put(s);
			System.out.println("put is done...");
		}
	}
	
	private SFTPSessionCachedPool(int p) throws JSchException {
		super();
		this.sftpSessions = new MyCircularQueue();
		
		for(int i=0;i<p-1;i++){
			Session s = super.shell.getSession(super.user, super.host, super.port);
			s.setUserInfo(new SSHUserInfo(super.pw));  
			s.connect(); 
			ChannelSftp sftp1=(ChannelSftp)s.openChannel("sftp");
			
			
			this.sftpSessions.put(sftp1);
		}
	}
	
	
	private SFTPSessionCachedPool(String host, String user, String pw, int port) throws JSchException {
		super(host, user, pw, port);
		this.sftpSessions = new MyCircularQueue();
		
		for(int i=0;i<10;i++){
			Session s = super.shell.getSession(user, host, port);
			s.setUserInfo(new SSHUserInfo(pw));  
			s.connect(); 
			
			this.sftpSessions.put(s);
		}
	}
	
	private SFTPSessionCachedPool(String host, String user, String pw, int port,int p) throws JSchException {
		super(host, user, pw, port);
		this.sftpSessions = new MyCircularQueue(p);
		
		for(int i=0;i<p-1;i++){
			Session s = super.shell.getSession(user, host, port);
			s.setUserInfo(new SSHUserInfo(pw));  
			s.connect(); 
			
			this.sftpSessions.put(s);
		}
	}

	public static SFTPSessionCachedPool getINSTANCE() throws JSchException {
		if(INSTANCE==null){
			INSTANCE = new SFTPSessionCachedPool();
		}
		return INSTANCE;
	}

	
	public static SFTPSessionCachedPool getINSTANCE(String host, String user, String pw, int port,int p) throws JSchException {
		if(INSTANCE==null){
			INSTANCE = new SFTPSessionCachedPool( host,  user,  pw,  port, p);
		}
		return INSTANCE;
	}
	
	/*public synchronized ChannelSftp getChannel() throws JSchException{
		System.out.println("SFTPSessionCachedPool  !!");
		ChannelSftp c=(ChannelSftp)sftpSessions.get();
		if(c!=null)System.out.println("C is no Null");
		return c;
	}*/
	
	public synchronized ChannelSftp getChannel() throws JSchException{
		System.out.println("SFTPSessionCachedPool  !!");
		Session s=(Session)this.sftpSessions.get();
		this.sftpSessions.put(s);
		ChannelSftp res=(ChannelSftp)s.openChannel("sftp");
		if(res.isClosed())System.out.println("closed....");
		return res;
	}
	
}
