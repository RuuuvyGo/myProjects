package dto;

public class CommitMessageDTO {

	private String messageCode,commitCode;

	public CommitMessageDTO() {
	}

	public CommitMessageDTO(String messageCode, String commitCode) {
		this.messageCode = messageCode;
		this.commitCode = commitCode;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getCommitCode() {
		return commitCode;
	}

	public void setCommitCode(String commitCode) {
		this.commitCode = commitCode;
	}

	
	
}
