package channel;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SHELLSessionCachedPool extends SessionCachedPool{

	private static SHELLSessionCachedPool INSTANCE = null;
	private MyCircularQueue sshSessions;
	
	static{
		try {
			INSTANCE = new SHELLSessionCachedPool();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private SHELLSessionCachedPool() throws JSchException {
		super();
		System.out.println("SFTPSessionCachedPool  con");
		this.sshSessions = new MyCircularQueue();
		
		for(int i=0;i<10;i++){
			Session s = super.shell.getSession(super.user, super.host, super.port);
			s.setUserInfo(new SSHUserInfo(super.pw));  
			s.connect(); 
			this.sshSessions.put(s);
			/*ChannelShell shell1=(ChannelShell)s.openChannel("shell");
			this.sshSessions.put(shell1);*/
		}
	}
	
	private SHELLSessionCachedPool(int p) throws JSchException {
		super();
		this.sshSessions = new MyCircularQueue(p);
		
		for(int i=0;i<p-1;i++){
			Session s = super.shell.getSession(super.user, super.host, super.port);
			s.setUserInfo(new SSHUserInfo(super.pw));  
			s.connect(); 
			
			this.sshSessions.put(s);
		}
	}
	
	
	private SHELLSessionCachedPool(String host, String user, String pw, int port) throws JSchException {
		super();
		this.sshSessions = new MyCircularQueue();
		
		for(int i=0;i<10;i++){
			Session s = super.shell.getSession(user, host, port);
			s.setUserInfo(new SSHUserInfo(pw));  
			s.connect(); 
			
			this.sshSessions.put(s);
		}
	}
	
	private SHELLSessionCachedPool(String host, String user, String pw, int port,int p) throws JSchException {
		super();
		this.sshSessions = new MyCircularQueue(p);
		
		for(int i=0;i<p-1;i++){
			Session s = super.shell.getSession(user, host, port);
			s.setUserInfo(new SSHUserInfo(pw));  
			s.connect(); 
			
			this.sshSessions.put(s);
		}
	}

	public static SHELLSessionCachedPool getINSTANCE() throws JSchException {
		if(INSTANCE==null){
			INSTANCE = new SHELLSessionCachedPool();
		}
		return INSTANCE;
	}

	
	public static SHELLSessionCachedPool getINSTANCE(String host, String user, String pw, int port,int p) throws JSchException {
		if(INSTANCE==null){
			INSTANCE = new SHELLSessionCachedPool( host,  user,  pw,  port, p);
		}
		return INSTANCE;
	}
	
	/*public synchronized ChannelShell getChannel(){
		ChannelShell res = (ChannelShell)this.sshSessions.get();
		if(res==null){
			
			ChannelShell shell1=(ChannelShell)s.openChannel("shell");
			
			this.sshSessions.put(shell1);
		}
		return res;
	} */
	
	public synchronized ChannelShell getChannel() throws JSchException{
		Session s = (Session)this.sshSessions.get();
		this.sshSessions.put(s);
		
		ChannelShell shell1=(ChannelShell)s.openChannel("shell");
		
		return shell1;
	} 
	
	
	
}
