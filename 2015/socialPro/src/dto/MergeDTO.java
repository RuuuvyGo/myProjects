package dto;

import java.util.ArrayList;
import java.util.List;

public class MergeDTO {

	private String commitCode;
	private List<String> appliedCommitCodeList;
	public MergeDTO() {
		this.appliedCommitCodeList = new ArrayList<String>();
	}
	public MergeDTO(String commitCode, List<String> appliedCommitCodeList) {
		this.commitCode = commitCode;
		this.appliedCommitCodeList = appliedCommitCodeList;
	}
	public String getCommitCode() {
		return commitCode;
	}
	public void setCommitCode(String commitCode) {
		this.commitCode = commitCode;
	}
	public List<String> getAppliedCommitCodeList() {
		return appliedCommitCodeList;
	}
	public void setAppliedCommitCodeList(List<String> appliedCommitCodeList) {
		this.appliedCommitCodeList = appliedCommitCodeList;
	}
	
	
}
