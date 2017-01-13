package remoteAction;

import com.jcraft.jsch.Channel;

public abstract class BaseRemoteAction implements RemoteAction{

	protected Channel channel;
	
	@Override
	public abstract void setChannel(Channel channel);
	
	protected Channel getChannel(){
		
		return channel;
	}

}
