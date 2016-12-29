package dto;

public class FileCommitGroupAlarmVDTO {

	private String messageCode,groupAlarmCode,commitCode,senderCode,originProjectCode,receiverCode;
	private int type;
	private String originFileCode,checkDate;
	public FileCommitGroupAlarmVDTO() {
	}
	public FileCommitGroupAlarmVDTO(String messageCode, String groupAlarmCode,
			String commitCode, String senderCode, String originProjectCode,
			String receiverCode, int type, String originFileCode,
			String checkDate) {
		this.messageCode = messageCode;
		this.groupAlarmCode = groupAlarmCode;
		this.commitCode = commitCode;
		this.senderCode = senderCode;
		this.originProjectCode = originProjectCode;
		this.receiverCode = receiverCode;
		this.type = type;
		this.originFileCode = originFileCode;
		this.checkDate = checkDate;
	}
	public String getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	public String getGroupAlarmCode() {
		return groupAlarmCode;
	}
	public void setGroupAlarmCode(String groupAlarmCode) {
		this.groupAlarmCode = groupAlarmCode;
	}
	public String getCommitCode() {
		return commitCode;
	}
	public void setCommitCode(String commitCode) {
		this.commitCode = commitCode;
	}
	public String getSenderCode() {
		return senderCode;
	}
	public void setSenderCode(String senderCode) {
		this.senderCode = senderCode;
	}
	public String getOriginProjectCode() {
		return originProjectCode;
	}
	public void setOriginProjectCode(String originProjectCode) {
		this.originProjectCode = originProjectCode;
	}
	public String getReceiverCode() {
		return receiverCode;
	}
	public void setReceiverCode(String receiverCode) {
		this.receiverCode = receiverCode;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getOriginFileCode() {
		return originFileCode;
	}
	public void setOriginFileCode(String originFileCode) {
		this.originFileCode = originFileCode;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	
}
