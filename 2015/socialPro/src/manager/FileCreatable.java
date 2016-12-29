package manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import model.CopiedFile;
import model.OriginFile;
import socialProExceptions.CommitExcetion;
import socialProExceptions.DAOException;
import socialProExceptions.FileException;
import socialProExceptions.FolderException;
import socialProExceptions.ProjectException;
import socialProExceptions.RemoteFileException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : FileCreatable.java
//  @ Date : 2015-07-28
//  @ Author : 
//
//




public interface FileCreatable {
	public String createFile(String memberCode, String name, String folderPath) throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException, FolderException;
	public String createFile(String memberCode, String name, String folderPath, String content)throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException, FolderException;
	public String createFileCode(String memberCode, String name, String folderCode)throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException, FolderException;
	public OriginFile createFileCode(String memberCode, String name, String folderCode, String content)throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException, FolderException, ProjectException;
	
	public OriginFile createCommitFileCode(String storageCode, String name, String folderCode, String content)throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException, CommitExcetion, FolderException, ProjectException;
	public Map<String, CopiedFile> createCommitTeamFile(String storageCode,String storageName, String name, String folderPath, String content)throws DAOException, ParseException, FileNotFoundException, SftpException, IOException, JSchException, FileException, RemoteFileException, CommitExcetion, FolderException, ProjectException;
	
	public boolean insertCopiedFile(List<CopiedFile> cfList);
	public boolean insertCopiedFile(CopiedFile cf);
	public boolean insertOriginFile(OriginFile oriFile);
	
}
