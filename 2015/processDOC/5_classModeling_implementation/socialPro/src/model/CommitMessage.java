package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommitMessage extends Message{

	private Map<String,List<String>> commitMap;
	
	
	public CommitMessage() {
		this.commitMap = new HashMap<String, List<String>>();
	}
	public CommitMessage(String senderCode,String receiverCode, GregorianCalendar sendDate) {
		super(senderCode, receiverCode, sendDate);
		this.commitMap = new HashMap<String, List<String>>();
	}
	public CommitMessage(String title, String content, String senderCode,String receiverCode, GregorianCalendar sendDate) {
		super(title, content, senderCode, receiverCode, sendDate);
		this.commitMap = new HashMap<String, List<String>>();
	}
	public CommitMessage(String messageCode,String senderCode, String receiverCode, GregorianCalendar sendDate) {
		super(messageCode, senderCode, receiverCode, sendDate);
		this.commitMap = new HashMap<String, List<String>>();
	}
	public CommitMessage(String messageCode, String title, String content,String senderCode, String receiverCode, GregorianCalendar sendDate) {
		super(messageCode, title, content, senderCode, receiverCode, sendDate);
		this.commitMap = new HashMap<String, List<String>>();
	}
	public CommitMessage(String senderCode,String receiverCode, GregorianCalendar sendDate,Map<String,List<String>> commitMap) {
		super(senderCode, receiverCode, sendDate);
		this.commitMap = commitMap;
	}
	public CommitMessage(String title, String content, String senderCode,String receiverCode, GregorianCalendar sendDate,Map<String,List<String>> commitMap) {
		super(title, content, senderCode, receiverCode, sendDate);
		this.commitMap = commitMap;
	}
	public CommitMessage(String messageCode,String senderCode, String receiverCode, GregorianCalendar sendDate,Map<String,List<String>> commitMap) {
		super(messageCode, senderCode, receiverCode, sendDate);
		this.commitMap = commitMap;
	}
	public CommitMessage(String messageCode, String title, String content,String senderCode, String receiverCode, GregorianCalendar sendDate,Map<String,List<String>> commitMap) {
		super(messageCode, title, content, senderCode, receiverCode, sendDate);
		this.commitMap = commitMap;
	}
	public Map<String,List<String>> getCommitMap() {
		return commitMap;
	}
	public void setCommitList(Map<String,List<String>> commitMap) {
		this.commitMap = commitMap;
	}
	public void addCommitCode(String commitCode,String objCode){
		
		if(!this.commitMap.containsKey(objCode)){
			List<String> val = new ArrayList<String>();
			val.add(commitCode);
			this.commitMap.put(objCode, val);
		}else{
			if(!this.commitMap.get(objCode).contains(commitCode))this.commitMap.get(objCode).add(commitCode);
		}
	}
	public void deleteCommitCode(String commitCode,String objCode){
		
		if(this.commitMap.containsKey(objCode)){
			if(this.commitMap.get(objCode).contains(commitCode))this.commitMap.get(objCode).remove(commitCode);
		}
	}
}
