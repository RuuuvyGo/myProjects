package manager;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : FolderDeletable.java
//  @ Date : 2015-07-28
//  @ Author : 
//
//




public interface FolderDeletable {
	public boolean deleteFolderPath(String nickName, String path);
	public boolean deleteFolderCode(String nickName, String code);
	public boolean deleteFolderAll(String nickName, String parentFolderCode);
}
