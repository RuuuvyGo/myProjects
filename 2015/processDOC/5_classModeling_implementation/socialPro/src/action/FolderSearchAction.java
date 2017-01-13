package action;

import java.io.File;
import java.text.ParseException;
import java.util.List;

import model.CopiedFolder;
import model.CopiedFolderList;
import model.OriginFolder;
import model.OriginFolderList;
import socialProExceptions.DAOException;
import socialProExceptions.FolderException;

public interface FolderSearchAction {
	
	public OriginFolder searchFolderName(String folderName,String parentFolderCode)throws DAOException, ParseException, FolderException;
	
	//doesn't matter folder is copied or origin just search
	public OriginFolder justSearchOriginFolderPath(String folderPath)throws DAOException, ParseException, FolderException;
	public OriginFolder justSearchOriginFolderCode(String folderCode)throws DAOException, ParseException, FolderException;
	public OriginFolder justSearchFolderNameByCode(String folderName, String parentFolderCode) throws DAOException, ParseException, FolderException;
	public OriginFolder justSearchFolderNameByPath(String folderName, String parentFolderPath) throws DAOException, ParseException, FolderException;
	//search Only originFolderList with oriParent
	public OriginFolderList justSearchOriginChFoldersByPFCode(String parentFolderCode)throws DAOException, ParseException, FolderException;
	public OriginFolderList justSearchOriginChFoldersByPFPath(String parentFolderPath)throws DAOException, ParseException, FolderException;
	//search siblings
	public OriginFolderList justSearchSiblingOriginFolderCode(String folderCode)throws DAOException, ParseException, FolderException;
	public OriginFolderList justSearchSiblingOriginFolderPath(String folderPath)throws DAOException, ParseException, FolderException;
	//search All Childs
	public OriginFolderList justSearchAllOriginFoldersByOriPFCode(String parentFolderCode)throws DAOException, ParseException, FolderException;
	public OriginFolderList justSearchAllOriginFoldersByOriPFPath(String parentFolderPath)throws DAOException, ParseException, FolderException;
	
////////////return only originFolder or null
	public OriginFolder searchOnlyOriginFolderPath(String folderPath)throws DAOException, ParseException, FolderException;
	public OriginFolder searchOnlyOriginFolderCode(String folderCode)throws DAOException, ParseException, FolderException;
	public OriginFolderList searchOriginChFoldersByOriPFCode(String oriParentFolderCode)throws DAOException, ParseException, FolderException ;
	public OriginFolderList searchOriginChFoldersByOriPFPath(String oriParentFolderPath)throws DAOException, ParseException, FolderException;
	public OriginFolderList searchSiblingOnlyOriginFolderCode(String oriFolderCode)throws DAOException, ParseException, FolderException;
	public OriginFolderList searchSiblingOnlyOriginFolderPath(String oriFolderPath)throws DAOException, ParseException, FolderException;
	public OriginFolderList searchAllOriginFoldersByOriPFCode(String oriParentFolderCode)throws DAOException, ParseException, FolderException;
	public OriginFolderList searchAllOriginFoldersByOriPFPath(String oriParentFolderPath)throws DAOException, ParseException, FolderException;
	

///////////return only CopiedFolder or null
	public CopiedFolder searchOnlyCopiedFolderPath(String folderPath)throws DAOException, ParseException, FolderException;
	public CopiedFolder searchOnlyCopiedFolderCode(String folderCode)throws DAOException, ParseException, FolderException;
	
	
	//search exactlly
	public File searchFolderByFolderPath(String folderPath) throws DAOException, ParseException, FolderException;
	public File searchFolderByFolderCode(String folderCode) throws DAOException, ParseException, FolderException;
	//childs
	public List<File> searchFolderByPFolderPath(String parentFolderPath) throws DAOException, ParseException, FolderException;
	public List<File> searchFolderByPFolderCode(String parentFolderCode) throws DAOException, ParseException, FolderException;
	//siblings
	public List<File> searchSiblingFolderByPFolderPath(String folderPath) throws DAOException, ParseException, FolderException;
	public List<File> searchSiblingFolderByPFolderCode(String folderCode) throws DAOException, ParseException, FolderException;
	//all children
	public List<File> searchAllFoldersByPFCode(String parentFolderCode)throws DAOException, ParseException, FolderException;
	public List<File> searchAllFoldersByPFPath(String parentFolderPath)throws DAOException, ParseException, FolderException;
	
}
