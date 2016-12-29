package factory;

import channel.SFTPSessionCachedPool;
import channel.SHELLSessionCachedPool;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSchException;

public class ChannelFactory implements Factory{

	private static ChannelFactory CHANNELFACTORY_INSTANCE=null;
	
	static{
		CHANNELFACTORY_INSTANCE = new ChannelFactory();
	}
	
	public static ChannelFactory getCHANNELFACTORY_INSTANCE(){
		if(CHANNELFACTORY_INSTANCE==null){
			CHANNELFACTORY_INSTANCE = new ChannelFactory();
		}
		return CHANNELFACTORY_INSTANCE;
	}
	
	public Object create(String name) {
		
			try {
				if(name.equals("shell")){
				return this.createChannelShell();}
				
				else if(name.equals("sftp")){
					return this.createChannelSftp();
				}else return null;
			} catch (JSchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		
	}
	
	private ChannelShell createChannelShell() throws JSchException{
		return SHELLSessionCachedPool.getINSTANCE().getChannel();
	}
	
	private ChannelSftp createChannelSftp() throws JSchException{
		System.out.println("ChannelFactory..");
		return SFTPSessionCachedPool.getINSTANCE().getChannel();
	}
	
}
