package remoteAction;

import com.jcraft.jsch.SftpException;

public interface RemoteFolderUpdateAction extends RemoteAction{

	public boolean updateFolder(String remotePath,String newRemotePath) throws SftpException;
}
