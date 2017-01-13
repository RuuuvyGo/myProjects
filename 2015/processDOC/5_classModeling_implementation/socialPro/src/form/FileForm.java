package form;

public class FileForm {

	private String commitCode,fileName,fileContent,commitDate,fileDiffContent,fileCommitTitle,fileCommitContent;

	public FileForm() {
	}

	public FileForm(String commitCode,String fileName, String fileContent, String commitDate,
			String fileDiffContent,String fileCommitTitle,String fileCommitContent) {
		this.commitCode = commitCode;
		this.fileName = fileName;
		this.fileContent = fileContent;
		this.commitDate = commitDate;
		this.fileDiffContent = fileDiffContent;
		this.fileCommitTitle = fileCommitTitle;
		this.fileCommitContent = fileCommitContent;
	}

	public String getCommitCode() {
		return commitCode;
	}

	public void setCommitCode(String commitCode) {
		this.commitCode = commitCode;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public String getCommitDate() {
		return commitDate;
	}

	public void setCommitDate(String commitDate) {
		this.commitDate = commitDate;
	}

	public String getFileDiffContent() {
		return fileDiffContent;
	}

	public void setFileDiffContent(String fileDiffContent) {
		this.fileDiffContent = fileDiffContent;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileCommitTitle() {
		return fileCommitTitle;
	}

	public void setFileCommitTitle(String fileCommitTitle) {
		this.fileCommitTitle = fileCommitTitle;
	}

	public String getFileCommitContent() {
		return fileCommitContent;
	}

	public void setFileCommitContent(String fileCommitContent) {
		this.fileCommitContent = fileCommitContent;
	}
}
