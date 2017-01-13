package remoteAction;

import java.io.IOException;

import com.jcraft.jsch.SftpException;

public interface RemoteFileUpdateAction extends RemoteAction{

	public boolean updateFileContent(String localPath,String remotePath) throws SftpException, IOException;
	
}
