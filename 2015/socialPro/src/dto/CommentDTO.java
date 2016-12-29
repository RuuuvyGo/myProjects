package dto;

public class CommentDTO {

	private String commentCode, title, content, targetCode, writerCode, writeDate;

	public CommentDTO() {
	}

	public CommentDTO(String title, String content, String targetCode,
			String writerCode, String writeDate) {
		this.title = title;
		this.content = content;
		this.targetCode = targetCode;
		this.writerCode = writerCode;
		this.writeDate = writeDate;
	}

	public CommentDTO(String commentCode, String title, String content,
			String targetCode, String writerCode, String writeDate) {
		this.commentCode = commentCode;
		this.title = title;
		this.content = content;
		this.targetCode = targetCode;
		this.writerCode = writerCode;
		this.writeDate = writeDate;
	}

	public String getCommentCode() {
		return commentCode;
	}

	public void setCommentCode(String commentCode) {
		this.commentCode = commentCode;
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

	public String getTargetCode() {
		return targetCode;
	}

	public void setTargetCode(String targetCode) {
		this.targetCode = targetCode;
	}

	public String getWriterCode() {
		return writerCode;
	}

	public void setWriterCode(String writerCode) {
		this.writerCode = writerCode;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	
	
}
