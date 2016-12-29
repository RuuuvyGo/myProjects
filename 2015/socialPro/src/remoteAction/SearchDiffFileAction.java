package remoteAction;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public interface SearchDiffFileAction extends RemoteAction{

	public String searchDiffFile(String remotePath,String remoteDiffPath) throws JSchException, IOException;
	public boolean copyFile(String remoteFromPath,String remoteToPath) throws FileNotFoundException,SftpException, IOException, JSchException;
}
