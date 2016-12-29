package dto;

public class CopiedFolderCommitAlarmVDTO extends OriginFolderCommitAlarmVDTO{

	private String copiedFolderCode;
	
	public CopiedFolderCommitAlarmVDTO() {
		super();
	}
	public CopiedFolderCommitAlarmVDTO(String messageCode, String alarmCode,
			String commitCode, String senderCode, String receiverCode,
			int type, String originFolderCode,String checkDate,String copiedFolderCode) {
		super(messageCode, alarmCode, commitCode, senderCode, receiverCode, type, originFolderCode, checkDate);
		this.copiedFolderCode  = copiedFolderCode;
	}
	public String getCopiedFolderCode() {
		return copiedFolderCode;
	}
	public void setCopiedFolderCode(String copiedFolderCode) {
		this.copiedFolderCode = copiedFolderCode;
	}
	
}
