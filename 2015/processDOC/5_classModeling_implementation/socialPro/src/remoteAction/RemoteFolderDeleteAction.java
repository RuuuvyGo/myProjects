package remoteAction;

import com.jcraft.jsch.SftpException;

public interface RemoteFolderDeleteAction extends RemoteAction{

	public boolean deleteFolder(String remotePath) throws SftpException;
}
