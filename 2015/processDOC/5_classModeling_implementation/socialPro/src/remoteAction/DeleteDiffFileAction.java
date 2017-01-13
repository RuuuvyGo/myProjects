package remoteAction;

import java.io.IOException;

import com.jcraft.jsch.JSchException;

public interface DeleteDiffFileAction extends RemoteAction{

	public boolean removeCpFile(String remoteOriFilePath) throws JSchException, IOException;
	public boolean removeTmpFile(String remoteOriFilePath) throws JSchException, IOException;
	public boolean justRemoveTmpFile(String remoteFileCode) throws JSchException, IOException;
}
