package dto;

public class CooperatorMessageAlarmVDTO {

	private String messageCode,senderCode,receiverCode,title,content,sendDate;
	private int type;
	private String sourceCode,alarmCode,checkDate;
	public CooperatorMessageAlarmVDTO() {
	}
	
	public CooperatorMessageAlarmVDTO(String messageCode, String senderCode,
			String receiverCode, String title, String content, String sendDate,
			int type, String sourceCode, String alarmCode, String checkDate) {
		this.messageCode = messageCode;
		this.senderCode = senderCode;
		this.receiverCode = receiverCode;
		this.title = title;
		this.content = content;
		this.sendDate = sendDate;
		this.type = type;
		this.sourceCode = sourceCode;
		this.alarmCode = alarmCode;
		this.checkDate = checkDate;
	}

	public String getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
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
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getAlarmCode() {
		return alarmCode;
	}

	public void setAlarmCode(String alarmCode) {
		this.alarmCode = alarmCode;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
		
}
