package form;

import java.util.List;

public class CommitMsgDetails {

	private List<FileForm> fileList;
	private String msgContent;
	public CommitMsgDetails() {
	}
	public CommitMsgDetails(List<FileForm> fileList, String msgContent) {
		this.fileList = fileList;
		this.msgContent = msgContent;
	}
	public List<FileForm> getFileList() {
		return fileList;
	}
	public void setFileList(List<FileForm> fileList) {
		this.fileList = fileList;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	
}
