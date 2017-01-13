package action;

import java.io.File;
import java.text.ParseException;
import java.util.List;

import model.CopiedProject;
import model.CopiedProjectList;
import model.OriginProject;
import model.OriginProjectList;
import socialProExceptions.DAOException;
import socialProExceptions.FolderException;
import socialProExceptions.ProjectException;

public interface ProjectSearchAction {

	//just search originProject doesn't matter is that real originProject
	public OriginProject justSearchOriProjectName(String name,String owner)throws DAOException, ParseException, FolderException;
	public OriginProjectList justSearchOriProjectName(String name)throws DAOException, ParseException;
	public OriginProject justSearchOriProjectCode(String projectCode)throws DAOException, ParseException, FolderException;
	public OriginProject justSearchOriProjectPath(String projectPath)throws DAOException, ParseException, FolderException;
	public OriginProjectList justSearchOriginProjectTag(String projectTag)throws DAOException, ParseException, FolderException;
	public OriginProjectList justSearchOriginProjectTags(List<String> projectTagList)throws DAOException, ParseException, FolderException;
	public OriginProject justSearchOriginProjectFCode(String folderCode)throws DAOException, ParseException, FolderException;
	public OriginProject checkProModifiable(String ownerCode,String projectCode)throws DAOException;
	
	//search Only OriginProject if not return null
	//public OriginProject searchOriginProjectPath(String projectPath)throws DAOException, ParseException, FolderException;
	//public OriginProject searchOriginProjectCode(String projectCode)throws DAOException, ParseException, FolderException;
	//public OriginProjectList searchProjectTag(String projectTag)throws DAOException, ParseException, FolderException;
	//public OriginProjectList searchProjectTags(List<String> projectTagList)throws DAOException, ParseException, FolderException;
	//public OriginProjectList searchProjectTagList(List<String> tagList)throws DAOException, ParseException, FolderException, ProjectException;
	//public OriginProjectList searchProjectOwner(String ownerCode)throws DAOException, ParseException, FolderException;
	//public OriginProjectList searchOriginProjectOwner(String ownerCode)throws DAOException, ParseException, FolderException;
	//public OriginProjectList searchSharedOriProjectOwner(String ownerCode)throws DAOException, ParseException, FolderException;
	//public OriginProject searchProjectFCode(String folderCode)throws DAOException, ParseException, FolderException;
	public OriginProject searchOnlyOriginProjectPath(String oriProjectPath)throws DAOException, ParseException, FolderException;
	public OriginProject searchOnlyOriginProjectCode(String oriProjectCode)throws DAOException, ParseException, FolderException;
	public OriginProjectList searchOnlyOriginProjectTag(String projectTag)throws DAOException, ParseException, FolderException;
	public OriginProjectList searchOnlyOriginProjectTags(List<String> projectTagList)throws DAOException, ParseException, FolderException;
	public OriginProjectList searchOnlyOriginProjectOwner(String ownerCode)throws DAOException, ParseException, FolderException;
	public OriginProject searchOnlyOriginProjectOwner(String ownerCode, String projectName)throws DAOException, ParseException, FolderException;
	public OriginProjectList searchOnlySharedOriProjectOwner(String ownerCode)throws DAOException, ParseException, FolderException;
	public OriginProject searchOnlyOriginProjectFCode(String oriFolderCode)throws DAOException, ParseException, FolderException;
	
	//search Only CopiedProject if not return null
	//public CopiedProject searchCopiedProjectCode(String copiedProjectCode)throws DAOException, ParseException, FolderException, ProjectException;
	//public CopiedProject searchCopiedProjectPath(String copiedProjectPath)throws DAOException, ParseException, FolderException, ProjectException;
	//public CopiedProject searchCopiedProjectCode(String oriProjectCode,String memberCode)throws DAOException, ParseException;
	//public CopiedProjectList searchCopiedProject(String oriOwnerCode,String memberCode)throws DAOException, ParseException, FolderException, ProjectException;
	//public CopiedProjectList searchCopiedSharedProject(String memberCode)throws DAOException, ParseException, FolderException, ProjectException;
	//public CopiedProjectList searchCopiedTeamProject(String memberCode)throws DAOException, ParseException, FolderException, ProjectException;
	public CopiedProject searchOnlyCpProByCpProCode(String copiedProjectCode)throws DAOException, ParseException;
	public CopiedProject searchOnlyCpProByCpProPath(String copiedProjectPath)throws DAOException, ParseException;
	public CopiedProjectList searchOnlyCpProByCpOwnerCode(String cpOwnerCode)throws DAOException, ParseException, FolderException, ProjectException;
	public CopiedProjectList searchOnlyCpSharedProByCpOwnerCode(String cpOwnerCode)throws DAOException, ParseException, FolderException, ProjectException;
	public CopiedProjectList searchOnlyCpTeamProByCpOwnerCode(String cpOwnerCode)throws DAOException, ParseException, FolderException, ProjectException;
	public CopiedProject searchOnlyCpProByCpOwnerCodeProName(String cpOwnerCode, String projectName)throws DAOException, ParseException, FolderException, ProjectException;
	public CopiedProject searchOnlyCpProByCpOwnerCodeOriProCode(String cpOwnerCode,String oriProjectCode)throws DAOException, ParseException;
	public CopiedProjectList searchOnlyCpProByOriProCode(String oriProjectCode)throws DAOException, ParseException;
	public CopiedProjectList searchOnlyCpProByOriOwnerCodeCpOwner(String oriOwnerCode,String cpOwnerCode)throws DAOException, ParseException, FolderException, ProjectException;
	public CopiedProjectList searchOnlyCpProByOriOwner(String oriOwnerCode)throws DAOException, ParseException, FolderException, ProjectException;
	public OriginProject searchOnlyCpProByCpFCode(String cpFolderCode)throws DAOException, ParseException, FolderException;
	
	
	//public OriginProject searchProjectFolderBypCode(String projectCode)throws DAOException, ParseException, FolderException;
	//search
	public File searchProjectPath(String projectPath)throws DAOException, ParseException, FolderException;
	public File searchProjectCode(String projectCode)throws DAOException, ParseException, FolderException;
	public List<File> searchProjectOwner(String ownerCode)throws DAOException, ParseException, FolderException;
	public List<File> searchPersonalProjectOwner(String ownerCode)throws DAOException, ParseException, FolderException;
	public File searchProjectFCode(String folderCode)throws DAOException, ParseException, FolderException;
}
