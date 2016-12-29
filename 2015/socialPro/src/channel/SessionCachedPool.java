package channel;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.UserInfo;

public class SessionCachedPool {

	protected JSch shell;
	protected String host;
	protected String user;
	protected String pw;
	protected int port;
	
	protected SessionCachedPool() {
		System.out.print("한번이면 된다_");
		this.shell = new JSch();
		this.host = "192.168.109.190";
		this.user = "fileManager";
		this.pw = "1234";
		this.port = 22;
	}
	
	
	protected SessionCachedPool(String host, String user, String pw, int port) {
		this.shell = new JSch();
		this.host = host;
		this.user = user;
		this.pw = pw;
		this.port = port;
	}
	
	 // this class implements jsch UserInfo interface for passing password to the session  
    static class SSHUserInfo implements UserInfo {  
        private String password;  
  
        SSHUserInfo(String password) {  
            this.password = password;  
        }  
  
        public String getPassphrase() {  
            return null;  
        }  
  
        public String getPassword() {  
            return password;  
        }  
  
        public boolean promptPassword(String arg0) {  
            return true;  
        }  
  
        public boolean promptPassphrase(String arg0) {  
            return true;  
        }  
  
        public boolean promptYesNo(String arg0) {  
            return true;  
        }  
  
        public void showMessage(String arg0) {  
            System.out.println(arg0);  
        }  
	}
	
	
}
