package remoteAction;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public interface RemoteFolderInsertAction extends RemoteAction{

	public boolean insertOriginFolder(String remotePath) throws SftpException, JSchException;
}
