package form;

public class MessageForm {

	private String messageCode,senderCode,sendDate,title,storageName,projectName,contents;

	public MessageForm() {
	}

	public MessageForm(String messageCode, String senderCode, String sendDate,
			String title, String storageName, String projectName,
			String contents) {
		this.messageCode = messageCode;
		this.senderCode = senderCode;
		this.sendDate = sendDate;
		this.title = title;
		this.storageName = storageName;
		this.projectName = projectName;
		this.contents = contents;
	}

	public MessageForm(String messageCode, String senderCode, String sendDate,
			String title, String storageName, 
			String contents) {
		this.messageCode = messageCode;
		this.senderCode = senderCode;
		this.sendDate = sendDate;
		this.title = title;
		this.storageName = storageName;
		this.contents = contents;
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

	public String getStorageName() {
		return storageName;
	}

	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	
}
