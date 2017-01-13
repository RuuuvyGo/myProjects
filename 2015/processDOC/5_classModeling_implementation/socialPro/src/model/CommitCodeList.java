package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommitCodeList {

	private Map<String,List<String>> notMergeCodeMap,mergeCodeMap;
	private Map<String,String> latestCodePerFolder;
	
	public CommitCodeList() {
		this.notMergeCodeMap = new HashMap<String, List<String>>();
		this.mergeCodeMap = new HashMap<String, List<String>>();
		this.latestCodePerFolder = new HashMap<String, String>();
	}

	public CommitCodeList(Map<String,List<String>> notMergeCodeMap, Map<String,List<String>> mergeCodeMap) {
		this.notMergeCodeMap = notMergeCodeMap;
		this.mergeCodeMap = mergeCodeMap;
		this.latestCodePerFolder = new HashMap<String, String>();
	}
	
	public Map<String,List<String>> getNotMergeCodeMap() {
		return notMergeCodeMap;
	}

	public void setNotMergeCodeMap(Map<String,List<String>> notMergeCodeMap) {
		this.notMergeCodeMap = notMergeCodeMap;
	}

	public Map<String,List<String>> getMergeCodeMap() {
		return mergeCodeMap;
	}

	public void setMergeCodeMap(Map<String,List<String>> mergeCodeMap) {
		this.mergeCodeMap = mergeCodeMap;
	}
	
	public void addMergeCode(String mergeCode,String objCode){
		System.out.println("add code :   addMergeCode()");
		if(this.mergeCodeMap.containsKey(objCode)){
			if(this.mergeCodeMap.get(objCode).contains(mergeCode))return;
			else this.mergeCodeMap.get(objCode).add(mergeCode);
		}else {
			List<String> val = new ArrayList<String>();
			val.add(mergeCode);
			this.mergeCodeMap.put(objCode, val);
		}
	}

	public void addNotMergeCode(String notMergeCode,String objCode){
		System.out.println("add code :   addNotMergeCode()");
		if(this.notMergeCodeMap.containsKey(objCode)){
			if(this.notMergeCodeMap.get(objCode).contains(notMergeCode))return;
			else this.notMergeCodeMap.get(objCode).add(notMergeCode);
		}else {
			List<String> val = new ArrayList<String>();
			val.add(notMergeCode);
			this.notMergeCodeMap.put(objCode, val);
		}
	}
	public void mergeCode(String notMergeCode,String objCode){
		
		if(this.notMergeCodeMap.containsKey(objCode)){
			if(this.notMergeCodeMap.get(objCode).contains(notMergeCode)){
				this.notMergeCodeMap.get(objCode).remove(objCode);
			}
		}
		if(this.mergeCodeMap.containsKey(objCode)){
			if(this.mergeCodeMap.get(objCode).contains(notMergeCode))return;
			else this.mergeCodeMap.get(objCode).add(notMergeCode);
		}else {
			List<String> val = new ArrayList<String>();
			val.add(notMergeCode);
			this.mergeCodeMap.put(objCode, val);
		}
	}
	public void mergeCodeMap(Map<String,List<String>> notMergeeMap){
		if(notMergeeMap==null)return;
		if(notMergeeMap.isEmpty())return;
		
		for(String objCode : notMergeeMap.keySet()){
			List<String> mergeCodeList = notMergeeMap.get(objCode);
			if(!mergeCodeList.isEmpty()){
				if(this.notMergeCodeMap.containsKey(objCode)){
					if(this.notMergeCodeMap.get(objCode).containsAll(mergeCodeList))this.notMergeCodeMap.get(objCode).removeAll(mergeCodeList);
				}
				if(this.mergeCodeMap.containsKey(objCode)){
					this.mergeCodeMap.get(objCode).addAll(mergeCodeList);
				}else{
					this.mergeCodeMap.put(objCode, mergeCodeList);
				}
			}
		}
	}
	
	public Map<String, String> getLatestCodePerFolder() {
		return latestCodePerFolder;
	}

	public void setLatestCodePerFolder(Map<String, String> latestCodePerFolder) {
		this.latestCodePerFolder = latestCodePerFolder;
	}
	
	public void changeLatestCodePerFolder(String folderCode, String commitCode){
		
		if(this.latestCodePerFolder.containsKey(folderCode)){
			this.latestCodePerFolder.remove(folderCode);
		}
		this.latestCodePerFolder.put(folderCode, commitCode);
	}

	public String getLatestCodePerFolder(String folderCode){
		
		if(this.latestCodePerFolder.containsKey(folderCode)){
			this.latestCodePerFolder.get(folderCode);
		}
		return null;
	}
}
