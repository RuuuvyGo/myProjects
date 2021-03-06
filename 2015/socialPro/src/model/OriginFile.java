package model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import com.sun.java_cup.internal.runtime.Scanner;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : OriginFile.java
//  @ Date : 2015-07-28
//  @ Author : 
//
//




public class OriginFile extends File {
	
	
	private String code;
	private String name;
	private String path;
	private String makeDate;
	private String alterDate;
	private String folderCode;
	private String folderPath;
	private String content;
	private double sizes;
	
	
	public OriginFile(File file){
		super(file.getPath());
		String fileName = file.getName();
		String path = file.getPath();
		if(fileName!=null)this.name = fileName;
		if(path!=null) this.path = path;
	}
	public OriginFile(String path) {
		super(path);
		this.path = path;
		this.sizes = 0;
	}
	public OriginFile(String name, String path, String makeDate,String folderCode) {
		super(path);
		this.name = name;
		this.path = path;
		this.makeDate = makeDate;
		this.folderCode = folderCode;
		this.sizes = 0;
	}
	public OriginFile(String name, String path, String makeDate,
			String folderCode, String content,int sizes) {
		super(path);
		this.name = name;
		this.path = path;
		this.makeDate = makeDate;
		this.folderCode = folderCode;
		this.content = content;
		this.sizes = sizes;
	}
	
	public OriginFile(String code,String name, String path, String makeDate,String folderCode) {
		super(path);
		this.code = code;
		this.name = name;
		this.path = path;
		this.makeDate = makeDate;
		this.folderCode = folderCode;
		this.sizes = 0;
	}
	public OriginFile(String code,String name, String path, String makeDate,
			String folderCode, String content,int sizes) {
		super(path);
		this.code = code;
		this.name = name;
		this.path = path;
		this.makeDate = makeDate;
		this.folderCode = folderCode;
		this.content = content;
		this.sizes = sizes;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(String makeDate) {
		this.makeDate = makeDate;
	}
	public String getAlterDate() {
		return alterDate;
	}
	public void setAlterDate(String alterDate) {
		this.alterDate = alterDate;
	}
	public String getFolderCode() {
		return folderCode;
	}
	public void setFolderCode(String folderCode) {
		this.folderCode = folderCode;
	}
	public String getFolderPath() {
		
		if(this.folderPath==null){
			System.out.println(this.path+"   "+this.name);
			int leng = this.path.length();
			int nameLeng = this.name.length();
			this.folderPath=this.path.substring(0, leng-nameLeng-1);
			System.out.println(this.folderPath);
			
		}else System.out.println(this.folderPath);
		return folderPath;
	}
	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public double getSizes() {
		return sizes;
	}
	public void setSizes(double sizes) {
		this.sizes = sizes;
	}
	public synchronized boolean writeFile(String localPath,String content) throws IOException,FileNotFoundException{
		
		FileOutputStream fos = null;
		FileDescriptor fd = null;
		this.content = content;
		try {
			
			fos = new FileOutputStream(localPath);
			fd = fos.getFD();
			
			fos.write(content.getBytes());
			fos.flush();
			//fd.sync();
			
			//((RemoteFileInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFileActionImpl")).insertOriginFileContent(file.getPath(),remotePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally{
			if(fos!=null){fos.close();}
		}
		
		
		return true;
	}
	
	public synchronized boolean writeFile() throws IOException,FileNotFoundException{
		return this.writeFile(this.path, this.content);
	}
	
	public synchronized boolean readFile(String localPath){
		
		BufferedReader f=null;
		FileReader fr=null;
		//if(this.content==null)this.content=new String();
		try {
			fr=new FileReader(localPath);
			f = new BufferedReader(fr);
			String str=null;
			if((str=f.readLine())==null){
				this.content=null;
			}else{
				this.content=str+"\n";
				while((str=f.readLine())!=null){
					this.content+=str+"\n";
				}
			}
			
			fr.close();
			f.close();
			return true;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				fr.close();
				f.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return false;
	}
	public synchronized boolean readFile(){return this.readFile(this.path);}
}
