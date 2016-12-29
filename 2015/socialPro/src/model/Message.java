package model;

import java.util.GregorianCalendar;

public class Message {

	private String messageCode,title,content,senderCode,receiverCode;
	private GregorianCalendar sendDate;
	public Message() {
	}
	public Message(String senderCode,
			String receiverCode, GregorianCalendar sendDate) {
		this.senderCode = senderCode;
		this.receiverCode = receiverCode;
		this.sendDate = sendDate;
	}
	public Message(String title, String content, String senderCode,
			String receiverCode, GregorianCalendar sendDate) {
		this.title = title;
		this.content = content;
		this.senderCode = senderCode;
		this.receiverCode = receiverCode;
		this.sendDate = sendDate;
	}
	public Message(String messageCode,String senderCode, String receiverCode, GregorianCalendar sendDate) {
		this.messageCode = messageCode;
		this.senderCode = senderCode;
		this.receiverCode = receiverCode;
		this.sendDate = sendDate;
	}
	public Message(String messageCode, String title, String content,
			String senderCode, String receiverCode, GregorianCalendar sendDate) {
		this.messageCode = messageCode;
		this.title = title;
		this.content = content;
		this.senderCode = senderCode;
		this.receiverCode = receiverCode;
		this.sendDate = sendDate;
	}
	public String getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSenderCode() {
		return senderCode;
	}
	public void setSenderCode(String senderCode) {
		this.senderCode = senderCode;
	}
	public String getReceiverCode() {
		return receiverCode;
	}
	public void setReceiverCode(String receiverCode) {
		this.receiverCode = receiverCode;
	}
	public GregorianCalendar getSendDate() {
		return sendDate;
	}
	public void setSendDate(GregorianCalendar sendDate) {
		this.sendDate = sendDate;
	}
	
}
