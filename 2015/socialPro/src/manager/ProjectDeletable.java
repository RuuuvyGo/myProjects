package manager;

import java.text.ParseException;

import socialProExceptions.DAOException;
import socialProExceptions.ProjectException;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : ProjectDeletable.java
//  @ Date : 2015-07-28
//  @ Author : 
//
//




public interface ProjectDeletable {
	public boolean deleteProjectMemPath(String nickName, String name, String path);
	public boolean deleteProjectMemCode(String memberCode, String storageCode, String projectCode) throws DAOException, ParseException, ProjectException;
	public boolean deleteProjectTeam(String nickName, String name, String path);
	public boolean deleteProjectTeam(String nickName, String code);
}
