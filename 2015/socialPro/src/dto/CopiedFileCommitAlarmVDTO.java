package dto;

public class CopiedFileCommitAlarmVDTO extends OriginFileCommitAlarmVDTO{

	private String copiedFileCode;
	public CopiedFileCommitAlarmVDTO() {
		super();
	}
	public CopiedFileCommitAlarmVDTO(String messageCode, String alarmCode,
			String commitCode, String senderCode, String receiverCode,
			int type, String originFileCode, String checkDate,String copiedFileCode) {
		super(messageCode, alarmCode, commitCode, senderCode, receiverCode, type, originFileCode, checkDate);
		this.copiedFileCode = copiedFileCode;
	}
	public String getCopiedFileCode() {
		return copiedFileCode;
	}
	public void setCopiedFileCode(String copiedFileCode) {
		this.copiedFileCode = copiedFileCode;
	}
	
}
