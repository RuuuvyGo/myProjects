package form;

public class CommitMessageInfoForm {

	private String messageCode,title,senderCode,senderNickName,receiverCode,receiverNickName,sendDate;

	public CommitMessageInfoForm() {
	}

	public CommitMessageInfoForm(String messageCode, String title,
			String senderCode, String receiverCode, String receiverNickName, String sendDate,String senderNickName) {
		this.messageCode = messageCode;
		this.title = title;
		this.senderCode = senderCode;
		this.receiverCode = receiverCode;
		this.receiverNickName = receiverNickName;
		this.sendDate = sendDate;
		this.senderNickName = senderNickName;
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

	public String getSenderNickName() {
		return senderNickName;
	}

	public void setSenderNickName(String senderNickName) {
		this.senderNickName = senderNickName;
	}

	public String getReceiverNickName() {
		return receiverNickName;
	}

	public void setReceiverNickName(String receiverNickName) {
		this.receiverNickName = receiverNickName;
	}
	
	
}
