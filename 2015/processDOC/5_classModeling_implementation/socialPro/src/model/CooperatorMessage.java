package model;

import java.util.GregorianCalendar;

public class CooperatorMessage extends Message{

	private String groupCode;//projectcode or teamCode
	private int type;
	
	public CooperatorMessage() {
	}
	public CooperatorMessage(String title, String content, String senderCode,
			String receiverCode, GregorianCalendar sendDate,String groupCode, int type) {
		super(title, content, senderCode, receiverCode, sendDate);
		this.groupCode = groupCode;
		this.type = type;
	}
	public CooperatorMessage(String messageCode, String title, String content,
			String senderCode, String receiverCode, GregorianCalendar sendDate,String groupCode, int type) {
		super(messageCode, title, content, senderCode, receiverCode, sendDate);
		this.groupCode = groupCode;
		this.type = type;
	}public CooperatorMessage(String senderCode,String receiverCode, GregorianCalendar sendDate,String groupCode, int type) {
		super(senderCode, receiverCode, sendDate);
		this.groupCode = groupCode;
		this.type = type;
	}
	public CooperatorMessage(String messageCode, String senderCode, String receiverCode, GregorianCalendar sendDate,String groupCode, int type) {
		super(messageCode,senderCode, receiverCode, sendDate);
		this.groupCode = groupCode;
		this.type = type;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
