package dto;

public class FileDTO {

	private String fileCode;
	private String folderCode;
	private String name;
	private String path;
	private String insertDate;
	private String alterDate;
	private double sizes;
	private String fileContent;
	public FileDTO(String folderCode, String name, String path,
			String insertDate, double sizes) {
		this.folderCode = folderCode;
		this.name = name;
		this.path = path;
		this.insertDate = insertDate;
		this.sizes = sizes;
	}
	public FileDTO(String fileCode, String folderCode, String name,
			String path, String insertDate, String alterDate, double sizes) {
		this.fileCode = fileCode;
		this.folderCode = folderCode;
		this.name = name;
		this.path = path;
		this.insertDate = insertDate;
		this.alterDate = alterDate;
		this.sizes = sizes;
	}
	public FileDTO(String folderCode, String name, String path,String insertDate, double sizes,String fileContent) {
		this.folderCode = folderCode;
		this.name = name;
		this.path = path;
		this.insertDate = insertDate;
		this.sizes = sizes;
		this.fileContent = fileContent;
	}
	public FileDTO(String fileCode, String folderCode, String name,String path, String insertDate, String alterDate, double sizes,String fileContent) {
		this.fileCode = fileCode;
		this.folderCode = folderCode;
		this.name = name;
		this.path = path;
		this.insertDate = insertDate;
		this.alterDate = alterDate;
		this.sizes = sizes;
		this.fileContent = fileContent;
	}
	public FileDTO() {
	}
	public String getFileCode() {
		return fileCode;
	}
	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}
	public String getFolderCode() {
		return folderCode;
	}
	public void setFolderCode(String folderCode) {
		this.folderCode = folderCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}
	public String getAlterDate() {
		return alterDate;
	}
	public void setAlterDate(String alterDate) {
		this.alterDate = alterDate;
	}
	public double getSizes() {
		return sizes;
	}
	public void setSizes(double sizes) {
		this.sizes = sizes;
	}
	public String getFileContent() {
		return fileContent;
	}
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	
	
}
