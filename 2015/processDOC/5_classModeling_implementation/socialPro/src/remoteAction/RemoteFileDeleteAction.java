package remoteAction;

import com.jcraft.jsch.SftpException;

public interface RemoteFileDeleteAction extends RemoteAction {

	public boolean deleteFileConent(String remotePath) throws SftpException;
	
}
