package remoteAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import factory.ChannelFactory;

public class RemoteFolderActionImpl extends BaseRemoteAction  implements RemoteFolderInsertAction,RemoteFolderSearchAction,RemoteFolderUpdateAction,RemoteFolderDeleteAction{

	
	
	
	public RemoteFolderActionImpl() throws JSchException {
		this.channel=(ChannelSftp)ChannelFactory.getCHANNELFACTORY_INSTANCE().create("sftp");
	}

	@Override
	public  synchronized void setChannel(Channel channel) {
		// TODO Auto-generated method stub
		
		this.channel  = (ChannelSftp)channel; 	
	}
		
	@Override
	public synchronized boolean deleteFolder(String remotePath) throws SftpException {
		// TODO Auto-generated method stub
		
		((ChannelSftp) this.channel).rmdir(remotePath);
		return true;
	}

	@Override
	public synchronized boolean updateFolder(String remotePath, String newRemotePath) throws SftpException {
		// TODO Auto-generated method stub
		
		((ChannelSftp) this.channel).rename(remotePath, newRemotePath);
		return true;
	}

	
	@Override
	public synchronized List<String> searchChild(String remotePath,String remotePPath) throws SftpException {
		// TODO Auto-generated method stub
		List<String> resList = new ArrayList<String>();
		
		Vector<ChannelSftp.LsEntry> filelist = ((ChannelSftp) this.channel).ls(remotePath);
        for (ChannelSftp.LsEntry file : filelist) {
            if (file.getAttrs().isDir()) {
            	
            	resList.add(remotePPath+"/"+file.getFilename());   
            }
        }
		
		return resList;
	}

	@Override
	public synchronized boolean insertOriginFolder(String remotePath) throws SftpException, JSchException {
		//make folder
		// TODO Auto-generated method stub
	
		System.out.println("remotePath : "+remotePath);
		System.out.println("THis is ImsertOriginFolder....");
		ChannelSftp cSftp=(ChannelSftp) this.channel;
		if(!cSftp.isConnected()){
			System.out.println("THis is not Connected...");
			cSftp.connect();
		}
		
		cSftp.mkdir(remotePath);
		System.out.println("End Remote.. RemoteFolderActionImpl");
		cSftp.disconnect();
        return true;
	}

	
}
