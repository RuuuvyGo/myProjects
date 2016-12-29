package remoteAction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public interface RemoteFileSearchAction extends RemoteAction{

	public File searchFile(String remotePath,String remoteRootPath,String localRootPath) throws SftpException, FileNotFoundException, IOException, JSchException;
	public File searchDiffFile(String remoteDiffPath,String diffCode) throws SftpException, FileNotFoundException, IOException, JSchException;
	public Map<String,InputStream> searchFileByFolder(String remoteFolderPath) throws SftpException;
	public File searchFile(String remotePath, String localPath) throws SftpException,IOException, JSchException;
	public String searchFileContent(String remotePath) throws SftpException,IOException, JSchException;
}
