package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Notice {

	//msgCodes
	private List<String> readNotice;
	private List<String> unreadNotice;
	public Notice() {
		this.readNotice = new ArrayList<String>();
		this.unreadNotice = new ArrayList<String>();
	}
	public Notice(List<String> readNotice, List<String> unreadNotice) {
		this.readNotice = readNotice;
		this.unreadNotice = unreadNotice;
	}
	public List<String> getReadNotice() {
		return readNotice;
	}
	public void setReadNotice(List<String> readNotice) {
		this.readNotice = readNotice;
	}
	public List<String> getUnreadNotice() {
		return unreadNotice;
	}
	public void setUnreadNotice(List<String> unreadNotice) {
		this.unreadNotice = unreadNotice;
	}
	
	public void addUnReadNoticeCode(String msgCode){
		if(!this.unreadNotice.contains(msgCode))this.unreadNotice.add(msgCode);
	}

	public void addReadNoticeCode(String msgCode){
		if(!this.readNotice.contains(msgCode))this.readNotice.add(msgCode);
	}

	public void removeUnReadNoticeCode(String msgCode){
		this.unreadNotice.remove(msgCode);
		this.readNotice.add(msgCode);
	}
	public boolean checkUnReadNoticeCode(String noticeCode){
		if(this.unreadNotice.contains(noticeCode)){
			this.unreadNotice.remove(noticeCode);
			this.readNotice.add(noticeCode);
			return true;
		}
		return false;
	}
	public void checkUnReadNoticeCodeList(List<String> noticeCodeList){
		for(String noticeCode : noticeCodeList){
			if(this.unreadNotice.contains(noticeCode)){
				this.unreadNotice.remove(noticeCode);
				this.readNotice.add(noticeCode);
			}
		}
	}
}
