package action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import model.CopiedFile;
import model.OriginFile;
import socialProExceptions.CommitExcetion;
import socialProExceptions.DAOException;
import socialProExceptions.FileException;
import socialProExceptions.FolderException;
import socialProExceptions.RemoteFileException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
public interface FileInsertAction {
	
	public OriginFile insertOriginFile(OriginFile file)throws DAOException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException ;
	
	public OriginFile insertOriginCommitFile(OriginFile file,String memberCode)throws DAOException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException, CommitExcetion;
	
	public Map<String,CopiedFile> insertCommitTeamFile(OriginFile fileInfo,String ownerCode)throws DAOException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException, CommitExcetion, FolderException;
	
	public CopiedFile insertCopiedFile(CopiedFile file)throws DAOException, FileException, FileNotFoundException, IOException, RemoteFileException, SftpException, JSchException;
	public CopiedFile insertCopiedFile(CopiedFile file,String remoteOriFile)throws DAOException, FileException, FileNotFoundException, IOException, RemoteFileException, SftpException, JSchException;
	
}
