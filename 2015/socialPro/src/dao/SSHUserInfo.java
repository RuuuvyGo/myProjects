package dao;

import com.jcraft.jsch.UserInfo;

public class SSHUserInfo implements UserInfo {  
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
