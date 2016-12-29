package dto;

public class FileCommitVDTO extends CommitDTO{

	private String fileCode;


	public FileCommitVDTO(int type) {
		super(type);
	}
	public FileCommitVDTO(String title, String memberCode,String insertDate, String setCode, String content, int merge) {
		super(title, memberCode, insertDate, setCode, content, merge);
		this.fileCode = setCode;
	}
	public FileCommitVDTO(String commitCode, String title, String memberCode,String insertDate, String setCode, String content, int merge) {
		super(commitCode, title, memberCode, insertDate, setCode, content, merge);
		this.fileCode = setCode;
	}
	
	public FileCommitVDTO(String title, String memberCode,String insertDate, String setCode, String content, int merge,int type) {
		super(title, memberCode, insertDate, setCode, content, merge, type);
		this.fileCode = setCode;
	}
	
	public FileCommitVDTO(String commitCode, String title, String memberCode,String insertDate, String setCode, String content, int merge,int type) {
		super(commitCode, title, memberCode, insertDate, setCode, content, merge, type);
		this.fileCode = setCode;
	}
	public String getFileCode() {
		return fileCode;
	}
	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
		this.setSetCode(fileCode);
	}
}
