package manager;

import socialProExceptions.DAOException;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : MemberDropable.java
//  @ Date : 2015-07-28
//  @ Author : 
//
//




public interface MemberDropable {
	public boolean dropMember(String nickName,String pw)throws DAOException;
	public boolean dropMember(String code) throws DAOException;
}
