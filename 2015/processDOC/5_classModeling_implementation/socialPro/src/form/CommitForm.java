package form;

public class CommitForm {

	private String commitCode,title,insertDate,commiter,commiterNickName,commitContent;

	public CommitForm() {
	}

	public CommitForm(String commitCode, String title, String insertDate,String commiter,String commiterNickName,String commitContent) {
		this.commitCode = commitCode;
		this.title = title;
		this.insertDate = insertDate;
		this.commiter = commiter;
		this.commiterNickName = commiterNickName;
		this.commitContent = commitContent;
	}

	public String getCommitCode() {
		return commitCode;
	}

	public void setCommitCode(String commitCode) {
		this.commitCode = commitCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}

	public String getCommiter() {
		return commiter;
	}

	public void setCommiter(String commiter) {
		this.commiter = commiter;
	}

	public String getCommiterNickName() {
		return commiterNickName;
	}

	public void setCommiterNickName(String commiterNickName) {
		this.commiterNickName = commiterNickName;
	}

	public String getCommitContent() {
		return commitContent;
	}

	public void setCommitContent(String commitContent) {
		this.commitContent = commitContent;
	}
	
}
