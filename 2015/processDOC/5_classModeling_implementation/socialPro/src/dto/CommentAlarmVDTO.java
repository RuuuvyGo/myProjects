package dto;

public class CommentAlarmVDTO {

	private String commentCode,commentTarget,alarmCode,checkDate;

	public CommentAlarmVDTO() {
	}

	public CommentAlarmVDTO(String commentCode, String commentTarget,
			String alarmCode, String checkDate) {
		this.commentCode = commentCode;
		this.commentTarget = commentTarget;
		this.alarmCode = alarmCode;
		this.checkDate = checkDate;
	}

	public String getCommentCode() {
		return commentCode;
	}

	public void setCommentCode(String commentCode) {
		this.commentCode = commentCode;
	}

	public String getCommentTarget() {
		return commentTarget;
	}

	public void setCommentTarget(String commentTarget) {
		this.commentTarget = commentTarget;
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
