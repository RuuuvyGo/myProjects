package dto;

public class CommitMessageVDTO {

	private String messageCode,senderCode,receiverCode,title,content,sendDate,commitCode;

	public CommitMessageVDTO() {
	}

	public CommitMessageVDTO(String messageCode, String senderCode,
			String receiverCode, String title, String content, String sendDate,
			String commitCode) {
		this.messageCode = messageCode;
		this.senderCode = senderCode;
		this.receiverCode = receiverCode;
		this.title = title;
		this.content = content;
		this.sendDate = sendDate;
		this.commitCode = commitCode;
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

	public String getCommitCode() {
		return commitCode;
	}

	public void setCommitCode(String commitCode) {
		this.commitCode = commitCode;
	}
	
}
