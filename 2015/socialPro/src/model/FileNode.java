package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FileNode extends MyOriginFile{

	private static final long serialVersionUID = 4082786430661452758L;
	private FileNode parentFileNode;
	private TreeMap<String, FileNode> fileTreeMap; 
	
	
	public FileNode(MyOriginFile pFile,MyOriginFile file) {
		super(file.getCode(),file.getName(),file.getPath(),file.getType());
		if(file.getType().equals("project")){
			super.setChildFolderList(file.getChildFolderList());
			//System.out.println("constructor FileNode             :   "+file.getChildFileList().get(0));
			super.setChildFileList(file.getChildFileList());
			this.parentFileNode=null;
			this.fileTreeMap = new TreeMap<String,FileNode>();
			
		}else if(file.getType().equals("folder")){
			System.out.println("!!!!   THis is fodler");
			super.setChildFolderList(file.getChildFolderList());
			super.setChildFileList(file.getChildFileList());
			this.parentFileNode = (FileNode)pFile;
			this.fileTreeMap = new TreeMap<String,FileNode>();
			if(super.getChildFileList().isEmpty())System.out.println("11 chFIle is empty list");
			if(super.getChildFolderList().isEmpty())System.out.println("11 chFolder is empty list");
			
		}else if(file.getType().equals("file")){
			System.out.println("THis is file");
			this.parentFileNode = (FileNode)pFile;
			super.setChildFolderList(null);
			super.setChildFileList(null);
			this.fileTreeMap = null;
		}
	}
	

	public void initFileNode(Map<String,MyOriginFile> fileMap) throws IOException{

		if(this.fileTreeMap==null){return;}
		
		List<String> childCodeList=this.getChildFolderList();
		
		if(!childCodeList.isEmpty()){
			int childCnt = childCodeList.size();
			
			System.out.println(childCnt);
			for(int i=0;i<childCnt;i++){
				MyOriginFile file=fileMap.get(childCodeList.get(i));	
				if(file!=null){		
					System.out.println("Make  "+file.getPath()+"    ;////////////    "+file.getCode());
					FileNode node = new FileNode(this,file);
					node.initFileNode(fileMap);
					this.fileTreeMap.put(file.getCode(), node);
				}
			}
		}
		
		List<String> childFileCodeList = this.getChildFileList();
		if(!childFileCodeList.isEmpty()){
			System.out.println(this.getCode()+"    THis has childFile.....");
			int childCnt = childFileCodeList.size();
			for(int i=0;i<childCnt;i++){
				MyOriginFile file=fileMap.get(childFileCodeList.get(i));	
				if(file!=null){	
					System.out.println("i'm path  : "+this.getPath()+file.getPath()+"    ;;////////////    "+file.getCode());
					FileNode node = new FileNode(this,file);
					node.initFileNode(fileMap);
					this.fileTreeMap.put(file.getCode(), node);
				}
			}
		}else System.out.println("i'm path  : "+this.getPath()+"  no  childFile.....");
	}
	
	
	public String makeTreeView(String res)
	{
		if(this.fileTreeMap == null)
		{
			res+="\n"+this.getType()+" : "+this.getCode();
			return res;
		}
		else if(this.fileTreeMap.isEmpty()){
			res+="\n"+this.getType()+" : "+this.getCode();
			return res;
		}
		else{
			System.out.println(this.getCode());
			List<String> childs=this.getChildFolderList();

			int cnt = childs.size();
			System.out.println(cnt);
			for(int i=0;i<cnt;i++){
				res+=(this.fileTreeMap.get(childs.get(i))).makeTreeView("");
			}
			
			res+="\n"+this.getType()+" : "+this.getCode();
		}
		return res;
	}

	public File getParentFileNode() {
		return parentFileNode;
	}

	public void setParentFileNode(FileNode parentFileNode) {
		this.parentFileNode = parentFileNode;
	}

	public TreeMap<String, FileNode> getFileTreeMap() {
		return fileTreeMap;
	}

	public void setFileTreeMap(TreeMap<String, FileNode> fileTreeMap) {
		this.fileTreeMap = fileTreeMap;
	}
	
	public boolean createChildFile(MyOriginFile file){
		
		if(this.fileTreeMap.containsKey(file.getCode()))return false;
		FileNode node = new FileNode(this, file);
		//node.createNewFile();
		this.getChildFileList().add(node.getCode());
		this.fileTreeMap.put(file.getCode(), node);
		
		return true;
	}
	
	public boolean createChildFolder(MyOriginFile file){
		
		if(this.fileTreeMap.containsKey(file.getCode()))return false;
		System.out.println("I'm....."+this.getPath()+"   "+this.getCode());
		FileNode node = new FileNode(this, file);
		//node.mkdir();
		this.getChildFolderList().add(node.getCode());
		this.fileTreeMap.put(file.getCode(), node);
		return true;
	}
	
	/*public boolean createChild(MyOriginFile file){
		
		if(file.isDirectory())return createChildFolder(file);
		else return createChildFile(file);
	}*/
	
	public boolean delete(){
		
		if(this.isDirectory()){
			List<String> childs = this.getChildFolderList();
			if(!childs.isEmpty()){
				int cnt = childs.size();
				for(int i=0;i<cnt;i++){
					(this.fileTreeMap.get(childs.get(i))).delete();
					return true;
				}
			}else{
				if(this.exists())super.delete();
				return true;
			}
		}else if(this.isFile()){
			if(this.exists())super.delete();
			return false;
		}
		return false;
	} 
	
	public FileNode getParentFile(){
		return this.parentFileNode;
	}
	
	public TreeMap<String,FileNode> getChilds(){
		return this.fileTreeMap;
	}
	public FileNode getChild(String childCode){
		return this.fileTreeMap.get(childCode);
	}
	
}
