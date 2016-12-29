package remoteAction;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;


public interface InsertDiffFileAction extends RemoteAction{

	public boolean insertDiffFile(String originPath, String diffFilePath) throws IOException, JSchException;
	public boolean insertDiffFile(String originPath, String compareFilePath,String diffFilePath) throws IOException, JSchException;
	public boolean copyFile(String remotePath) throws FileNotFoundException,SftpException, IOException, JSchException;
	public boolean copyFileToTmp(String originPath,String fileCode) throws FileNotFoundException,SftpException, IOException, JSchException ;
	public boolean copyTmpFileToPath(String originPath,String fileCode) throws FileNotFoundException,SftpException, IOException, JSchException ;
	public boolean patchFile(String pathPath,String oriPath) throws IOException, JSchException;
}
