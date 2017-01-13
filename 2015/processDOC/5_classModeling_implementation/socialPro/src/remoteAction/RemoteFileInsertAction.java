package remoteAction;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public interface RemoteFileInsertAction extends RemoteAction{

	public boolean insertOriginFileContent(String localPath, String remotePath) throws FileNotFoundException, SftpException, IOException, JSchException;
	//public boolean mvFile(String remotePath,String fileName) throws FileNotFoundException, SftpException, IOException, JSchException;
	public boolean insertCopiedFileContent(String remotOriPath, String remotePath) throws FileNotFoundException, IOException, JSchException;

}
