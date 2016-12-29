package remoteAction;

import java.util.List;

import com.jcraft.jsch.SftpException;

public interface RemoteFolderSearchAction extends RemoteAction{

	public List<String> searchChild(String remotePath,String remotePPath) throws SftpException;
}
