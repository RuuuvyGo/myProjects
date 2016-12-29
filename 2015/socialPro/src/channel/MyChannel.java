package channel;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.Session;

public class MyChannel {

	private Session session;
	private Channel channel;
	public MyChannel(Session session, Channel channel) {
		this.session = session;
		this.channel = channel;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	public void closeChannel(){
		this.channel.disconnect();
		this.session.disconnect();
	}
}
