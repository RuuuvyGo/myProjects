package manager;

import java.io.File;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import model.CopiedProject;
import model.CopiedProjectList;
import model.OriginProject;
import model.OriginProjectList;
import socialProExceptions.CommitExcetion;
import socialProExceptions.DAOException;
import socialProExceptions.FolderException;
import socialProExceptions.MemberException;
import socialProExceptions.ProjectException;
import socialProExceptions.TeamException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import form.ProjectSearchedForm;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : ProjectSearchable.java
//  @ Date : 2015-07-28
//  @ Author : 
//
//




public interface ProjectSearchable {
	
	//search exactlly
	public List<File> searchPersonalProjectByOwnerCode(String ownerCode) throws DAOException, ParseException, FolderException;
	public Map<String,List<File>> searchPersonalProjectByOwnerCodeMap(String ownerCode) throws DAOException, ParseException, FolderException;
	public Map<String,List<File>> searchPersonalProjectByOwnerCode(List<String> ownerCodeList) throws DAOException, ParseException, FolderException;
	public File searchProjectByProjectCode(String projectCode) throws DAOException, ParseException, FolderException;
	public List<File> searchProjectByProjectCode(List<String> projectCodeList) throws DAOException, ParseException, FolderException;
	public File searchProjectByProjectPath(String projectPath) throws DAOException, ParseException, FolderException;
	public List<File> searchProjectByProjectPath(List<String> projectPathList) throws DAOException, ParseException, FolderException;
	
	
	//just search originProject doesn't matter what is real
	public OriginProject justSearchProjectCode(String projectCode) throws DAOException, ParseException, FolderException;
	public OriginProject justSearchProjectPath(String projectPath) throws DAOException, ParseException, FolderException;
	public OriginProject justSearchProjectFolderCode(String folderCode) throws DAOException, ParseException, FolderException;
	
	
	//return only real OriginProject
	public OriginProjectList searchOnlyOriginProjectByOwnerCode(String ownerCode) throws DAOException, ParseException, FolderException;
	public Map<String,OriginProjectList> searchOnlyOriginProjectByOwnerCode(List<String> ownerCodeList) throws DAOException, ParseException, FolderException;
	public OriginProject searchOnlyOriginProjectByProjectCode(String projectCode) throws DAOException, ParseException, FolderException;
	public OriginProjectList searchOnlyOriginProjectByProjectCode(List<String> projectCodeList) throws DAOException, ParseException, FolderException;
	public OriginProject searchOnlyOriginProjectByProjectPath(String projectPath) throws DAOException, ParseException, FolderException;
	public OriginProjectList searchOnlyOriginProjectByProjectPath(List<String> projectPathList) throws DAOException, ParseException, FolderException;
	public OriginProject searchOnlyOriginProjectByFolderCode(String folderCode) throws DAOException, ParseException, FolderException;
	public OriginProjectList searchOnlyOriginProjectByFolderCode(List<String> folderCodeList) throws DAOException, ParseException, FolderException;
	public OriginProjectList searchOnlyOriginProjectByTagNameList(List<String> tagNameList) throws DAOException, ParseException, FolderException;
	
	
	//return only real CopiedProject
	public CopiedProjectList searchOnlyPersonalCpProjectByOwnerCode(String ownerCode) throws DAOException, ParseException, FolderException, ProjectException;
	public Map<String,CopiedProjectList> searchOnlyPersonalCpProjectByOwnerCode(List<String> ownerCodeList) throws DAOException, ParseException, FolderException, ProjectException;
	public CopiedProjectList searchOnlyTeamCpProjectByOwnerCode(String teamCode, String cooper) throws DAOException, ParseException, FolderException, ProjectException;
	public Map<String,CopiedProjectList> searchOnlyTeamCpProjectByOwnerCode(String teamCode, List<String> cooperList) throws DAOException, ParseException, FolderException, ProjectException;
	public Map<String,Map<String,CopiedProjectList>> searchOnlyTeamCpProjectByOwnerCode(Map<String,List<String>> teamCooperMap) throws DAOException, ParseException, FolderException, ProjectException;
	public CopiedProject searchOnlyCpProjectByOriProCode(String oriProCode, String cooper) throws DAOException, ParseException, FolderException, ProjectException;
	public Map<String,CopiedProject> searchOnlyCpProjectByOriProCode(String oriProCode) throws DAOException, ParseException, FolderException, ProjectException;
	public CopiedProject searchOnlyCpProjectByOriProPath(String oriProPath, String cooper) throws DAOException, ParseException, FolderException, ProjectException;
	public Map<String,CopiedProject> searchOnlyCpProjectByOriProPath(String oriProPath) throws DAOException, ParseException, FolderException, ProjectException;
	public CopiedProject searchOnlyCpProjectByProjectPath(String projectPath) throws DAOException, ParseException, FolderException, ProjectException;
	public CopiedProjectList searchOnlyCpProjectByProjectPath(List<String> projectPathList) throws DAOException, ParseException, FolderException, ProjectException;
	public CopiedProject searchOnlyCpProjectByProjectCode(String projectCode) throws DAOException, ParseException, FolderException, ProjectException;
	public CopiedProjectList searchOnlyCpProjectByProjectCode(List<String> projectCodeList) throws DAOException, ParseException, FolderException, ProjectException;
	
	
	//public OriginProjectList searchProject(List<String> tagList, String name, String description, String path);
	public boolean checkProjectName(String memberCode, String projectName) throws DAOException, ParseException, FolderException;
	
	
	public CopiedProjectList searchASelectCopiedProject(List<String> pathList) throws DAOException, ParseException, FolderException, ProjectException;
	public OriginProjectList searchASelectOriginProject(List<String> pathList) throws DAOException, ParseException, FolderException, ProjectException;

	public CopiedProject searchASelectCopiedProject(String path) throws DAOException, ParseException, FolderException, ProjectException;
	public OriginProject searchASelectOriginProject(String path) throws DAOException, ParseException, FolderException, ProjectException;

	public Map<String,OriginProjectList> searchSharedOriProjectMCode(String memberCode) throws DAOException, ParseException, FolderException;
	public Map<String,List<ProjectSearchedForm>> searchProjectTagList(List<String> tagList) throws DAOException, ParseException, FolderException, ProjectException, MemberException, TeamException;
}