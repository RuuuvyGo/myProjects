package dto;

public class CommentGroupAlarmVDTO {

	private String commnetCode,commentTargetCode,groupAlarmCode,checkDate;

	public CommentGroupAlarmVDTO() {
	}

	public CommentGroupAlarmVDTO(String commnetCode, String commentTargetCode,
			String groupAlarmCode, String checkDate) {
		this.commnetCode = commnetCode;
		this.commentTargetCode = commentTargetCode;
		this.groupAlarmCode = groupAlarmCode;
		this.checkDate = checkDate;
	}

	public String getCommnetCode() {
		return commnetCode;
	}

	public void setCommnetCode(String commnetCode) {
		this.commnetCode = commnetCode;
	}

	public String getCommentTargetCode() {
		return commentTargetCode;
	}

	public void setCommentTargetCode(String commentTargetCode) {
		this.commentTargetCode = commentTargetCode;
	}

	public String getGroupAlarmCode() {
		return groupAlarmCode;
	}

	public void setGroupAlarmCode(String groupAlarmCode) {
		this.groupAlarmCode = groupAlarmCode;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
		
}
