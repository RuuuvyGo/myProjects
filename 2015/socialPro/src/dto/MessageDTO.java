package dto;

public class MessageDTO {

	private String messageCode,senderCode, receiverCode,sendDate,title,content;

	public MessageDTO(String senderCode, String receiverCode, String sendDate,
			String title, String content) {
		this.senderCode = senderCode;
		this.receiverCode = receiverCode;
		this.sendDate = sendDate;
		this.title = title;
		this.content = content;
	}

	public MessageDTO() {
	}

	public MessageDTO(String messageCode, String senderCode,
			String receiverCode, String sendDate, String title, String content) {
		this.messageCode = messageCode;
		this.senderCode = senderCode;
		this.receiverCode = receiverCode;
		this.sendDate = sendDate;
		this.title = title;
		this.content = content;
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

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
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
	
}
