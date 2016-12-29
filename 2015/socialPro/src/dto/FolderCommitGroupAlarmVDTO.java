package dto;

public class FolderCommitGroupAlarmVDTO {

	private String messageCode,groupAlarmCode,commitCode,originProjectCode,senderCode,receiverCode;
	private int type;
	private String originFolderCode,checkDate;
	public FolderCommitGroupAlarmVDTO() {
	}
	
	public FolderCommitGroupAlarmVDTO(String messageCode,
			String groupAlarmCode, String commitCode, String originProjectCode,
			String senderCode, String receiverCode, int type,
			String originFolderCode, String checkDate) {
		this.messageCode = messageCode;
		this.groupAlarmCode = groupAlarmCode;
		this.commitCode = commitCode;
		this.originProjectCode = originProjectCode;
		this.senderCode = senderCode;
		this.receiverCode = receiverCode;
		this.type = type;
		this.originFolderCode = originFolderCode;
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
	public String getOriginFolderCode() {
		return originFolderCode;
	}
	public void setOriginFolderCode(String originFolderCode) {
		this.originFolderCode = originFolderCode;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getOriginProjectCode() {
		return originProjectCode;
	}

	public void setOriginProjectCode(String originProjectCode) {
		this.originProjectCode = originProjectCode;
	}
	
	
}
