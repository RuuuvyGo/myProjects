package model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : CopiedFolderList.java
//  @ Date : 2015-07-28
//  @ Author : 
//
//




public class CopiedFolderList {
	private List<CopiedFolder> list;
	
	public CopiedFolderList() {
		this.list = new ArrayList<CopiedFolder>();
	}
	public CopiedFolderList(String path) {
		this.list = new ArrayList<CopiedFolder>();
		this.list.add(new CopiedFolder(path));
	}
	
	public boolean deleteCopiedFolderByOriCode(String originCode) {
	
		CopiedFolderList folderList=this.searchCopiedFolderOriCode(originCode);
		if(folderList.getList().isEmpty())return false;
		for(CopiedFolder folder:folderList.getList()){
			if(!this.list.remove(folder))return false;
		}
		return true;
	}
	
	public boolean deleteCopiedFolderByOriPCode(String originParentCode) {
	
		CopiedFolderList folderList=this.searchCopiedFolderOriPCode(originParentCode);
		if(folderList.getList().isEmpty())return false;
		for(CopiedFolder folder:folderList.getList()){
			if(!this.list.remove(folder))return false;
		}
		return true;
	}
	
	public boolean deleteCopiedFoldersByName(String name) {
	
		CopiedFolderList folderList=this.searchCopiedFolderName(name);
		if(folderList.getList().isEmpty())return false;
		for(CopiedFolder folder:folderList.getList()){
			if(!this.list.remove(folder))return false;
		}
		return true;
	}
	
	public boolean deleteCopiedFoldersByPath(String path) {
	
		CopiedFolder folder = this.searchCopiedFolderPath(path);
		if(folder==null)return false;
		return this.list.remove(folder);
	}
	
	public boolean deleteCopiedFoldersByCode(String code) {
	
		CopiedFolder folder = this.searchCopiedFolderCode(code);
		if(folder==null)return false;
		return this.list.remove(folder);
	}
	
	public boolean deleteCopiedFoldersByPCode(String parentFolder) {
	
		CopiedFolderList folderList=this.searchCopiedFolderPFolder(parentFolder);
		if(folderList.getList().isEmpty())return false;
		for(CopiedFolder folder:folderList.getList()){
			if(!this.list.remove(folder))return false;
		}
		return true;
	}
	
	public boolean modifyCopiedFolderByCodeName(String code, String newName) {
	
		CopiedFolder folder = this.searchCopiedFolderCode(code);
		if(folder==null)return false;
		folder.setName(newName);
		StringTokenizer tokens = new StringTokenizer(folder.getPath(),"\\");
		int cnt = tokens.countTokens();
		String newPath=new String();
		for(int i=0;i<cnt-1;i++){
			newPath+=tokens.nextToken()+"\\";
		}
		folder.setPath(newPath+"\\"+newName);
		return true;
	}
	
	public boolean modifyCopiedFolderByCodeDes(String code, String newDescription) {
	
		CopiedFolder folder = this.searchCopiedFolderCode(code);
		if(folder==null)return false;
		folder.setDescription(newDescription);
		return true;
	}
	
	public boolean modifyCopiedFolderByCodePath(String code, String newParentPath, String newParentFolder) {
	
		CopiedFolder folder = this.searchCopiedFolderCode(code);
		if(folder==null)return false;
		folder.setParentFolder(newParentFolder);
		folder.setPath(newParentPath+"\\"+folder.getName());
		return true;
	}
	
	public boolean modifyCopiedFolderByCodePath(String code, String newPath) {
	
		CopiedFolder folder = this.searchCopiedFolderCode(code);
		if(folder==null)return false;
		folder.setPath(newPath);
		return true;
	}
	
	public boolean modifyCopiedFolderByCodeAlter(String code, String newAlterDate) {
	
		CopiedFolder folder = this.searchCopiedFolderCode(code);
		if(folder==null)return false;
		folder.setAlterDate(newAlterDate);
		return true;
	}
	
	public boolean modifyCopiedFolderByCodeSize(String code, double newSize) {
	
		CopiedFolder folder = this.searchCopiedFolderCode(code);
		if(folder==null)return false;
		folder.setSize(newSize);
		return true;
	}
	
	public boolean modifyCopiedFolderByCodeChFolders(String code, List<String> newChildFolderList) {
	
		CopiedFolder folder = this.searchCopiedFolderCode(code);
		if(folder==null)return false;
		folder.setChildFolderList(newChildFolderList);
		return true;
	}
	
	public boolean modifyCopiedFolderByPFolderPath(String parentFolder, String newParentPath) {
	
		CopiedFolderList folderList = this.searchCopiedFolderPFolder(parentFolder);
		if(folderList.getList().isEmpty())return false;
		for(CopiedFolder folder:folderList.getList()){
			folder.setPath(newParentPath+"\\"+folder.getName());
		}
		return true;
	}
	
	public boolean modifyCopiedFolderByPFolderName(String parentFolder, String newName) {
	
		CopiedFolderList folderList = this.searchCopiedFolderPFolder(parentFolder);
		if(folderList.getList().isEmpty())return false;
		int cnt=0;
		StringTokenizer tokens=null;
		String newPath=new String();
		for(CopiedFolder folder:folderList.getList()){
			tokens = new StringTokenizer(folder.getPath(),"\\");
			cnt = tokens.countTokens();
			for(int i=0;i<cnt-1;i++){
				newPath+=tokens.nextToken()+"\\";
			}
			folder.setPath(newPath+"\\"+newName);
			folder.setName(newName);
		}
		return true;
	}
	
	public boolean modifyCopiedFolderByPFolderChFolders(String parentFolder, List<String> newChildFolderList) {
	
		CopiedFolderList folderList = this.searchCopiedFolderPFolder(parentFolder);
		if(folderList.getList().isEmpty())return false;
		for(CopiedFolder folder:folderList.getList()){
			folder.setChildFolderList(newChildFolderList);
		}
		return true;
	}
	
	public boolean modifyCopiedFolderByOriCode(String originCode, String newName, String newParentPath) {
	
		CopiedFolderList folderList = this.searchCopiedFolderOriCode(originCode);
		if(folderList.getList().isEmpty())return false;
		for(CopiedFolder folder:folderList.getList()){
			folder.setPath(newParentPath+"\\"+newName);
		}
		return true;
	}
	
	public boolean modifyCopiedFolderByOriCode(String originCode, String newName) {
	
		CopiedFolderList folderList = this.searchCopiedFolderOriCode(originCode);
		if(folderList.getList().isEmpty())return false;
		int cnt=0;
		StringTokenizer tokens=null;
		String newPath=new String();
		for(CopiedFolder folder:folderList.getList()){
			tokens = new StringTokenizer(folder.getPath(),"\\");
			cnt = tokens.countTokens();
			for(int i=0;i<cnt-1;i++){
				newPath+=tokens.nextToken()+"\\";
			}
			folder.setPath(newPath+"\\"+newName);
			folder.setName(newName);
		}
		return true;
	}
	
	public boolean modifyCopiedFolderByOriPCode(String originParentCode, String newParentPath) {
	
		CopiedFolderList folderList = this.searchCopiedFolderOriPCode(originParentCode);
		if(folderList.getList().isEmpty())return false;
		for(CopiedFolder folder:folderList.getList()){
			folder.setPath(newParentPath+"\\"+folder.getName());
		}
		return true;
	}
	
	public boolean modifyCopiedFolderByOriPCode(String originParentCode, String newParentPath, String newParentFolder) {
	
		CopiedFolderList folderList = this.searchCopiedFolderOriPCode(originParentCode);
		if(folderList.getList().isEmpty())return false;
		for(CopiedFolder folder:folderList.getList()){
			folder.setPath(newParentPath+"\\"+folder.getName());
			folder.setParentFolder(newParentFolder);
		}
		return true;
	}
	
	public CopiedFolder searchCopiedFolderCode(String code) {
	
		for(CopiedFolder folder:this.list){
			
			if(folder.getCode().equals(code))return folder;
		}return null;
	}
	
	public CopiedFolderList searchCopiedFolderName(String name) {
	
		CopiedFolderList folderList = new CopiedFolderList();
		for(CopiedFolder folder:this.list){
			
			if(folder.getName().equals(name))folderList.addCopiedFolder(folder);
		}return folderList;
	}
	
	public void addCopiedFolder(CopiedFolder folder) {
		// TODO Auto-generated method stub
		if(this.list.contains(folder))this.list.remove(folder);
		this.list.add(folder);
	}
	public CopiedFolder searchCopiedFolderName(String name, String parentFolder) {
	
		for(CopiedFolder folder:this.list){
			
			if(folder.getName().equals(name))
				if(folder.getParentFolder().equals(parentFolder))return folder;
		}return null;
	}
	
	public CopiedFolderList searchCopiedFolderDes(String description) {
	
		CopiedFolderList folderList = new CopiedFolderList();
		for(CopiedFolder folder:this.list){
			
			if(folder.getDescription().equals(description))folderList.addCopiedFolder(folder);
		}return folderList;
	}
	
	public CopiedFolder searchCopiedFolderPath(String path) {
	
		for(CopiedFolder folder:this.list){
			
			if(folder.getPath().equals(path))return folder;
		}return null;
	}
	
	public CopiedFolderList searchCopiedFolderPFolder(String parentFolder) {
	
		CopiedFolderList folderList = new CopiedFolderList();
		for(CopiedFolder folder:this.list){
			
			if(folder.getParentFolder().equals(parentFolder))folderList.addCopiedFolder(folder);
		}return folderList;
	}
	
	public CopiedFolderList searchCopiedFolderOriCode(String originCode) {
	
		CopiedFolderList folderList = new CopiedFolderList();
		for(CopiedFolder folder:this.list){
			
			if(folder.getOriginCode().equals(originCode))folderList.addCopiedFolder(folder);
		}return folderList;
	}
	
	public CopiedFolderList searchCopiedFolderOriPCode(String originParentCode) {
	
		CopiedFolderList folderList = new CopiedFolderList();
		for(CopiedFolder folder:this.list){
			
			if(folder.getOriginParentCode().equals(originParentCode))folderList.addCopiedFolder(folder);
		}return folderList;
	}
	public List<CopiedFolder> getList() {
		return list;
	}
	public void setList(List<CopiedFolder> list) {
		this.list = list;
	}
	
}
