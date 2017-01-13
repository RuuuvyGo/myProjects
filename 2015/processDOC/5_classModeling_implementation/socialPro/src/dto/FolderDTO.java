package dto;

import java.io.File;

public class FolderDTO {

	
	private String folderCode;
	private String name;
	private String description;
	private String path;
	private String insertDate;
	private String alterDate;
	private double sizes;
	private String parentFolderCode;
	private String folderContent;
	
	public FolderDTO() {
	}
	public FolderDTO(String path, String name,
			String insertDate, double sizes) {
		this.name = name;
		this.path = path;
		this.insertDate = insertDate;
		this.sizes = sizes;
	}
	public FolderDTO(String path) {

		this.path = path;
	}
	public FolderDTO( String path, String folderCode, String name,
			String description, String insertDate,
			String alterDate, double sizes, String parentFolderCode) {
		this.folderCode = folderCode;
		this.name = name;
		this.description = description;
		this.path = path;
		this.insertDate = insertDate;
		this.alterDate = alterDate;
		this.sizes = sizes;
		this.parentFolderCode = parentFolderCode;
	}
	
	public FolderDTO( String name,String insertDate, double sizes,String folderContent) {
		this.name = name;
		this.path = path;
		this.insertDate = insertDate;
		this.sizes = sizes;
		this.folderContent = folderContent;
	}
	public FolderDTO(String path,String folderCotent) {

		this.path = path;
		this.folderContent = folderContent;
	}
	public FolderDTO(String path,String folderCode, String name,
			String description, String insertDate,double sizes, String parentFolderCode,String folderContent) {
		this.folderCode = folderCode;
		this.name = name;
		this.description = description;
		this.path = path;
		this.insertDate = insertDate;
		this.sizes = sizes;
		this.parentFolderCode = parentFolderCode;
		this.folderContent = folderContent;
	}
	public FolderDTO(String path,String folderCode, String name,
			String description, String insertDate,
			String alterDate, double sizes, String parentFolderCode,String folderContent) {
		this.folderCode = folderCode;
		this.name = name;
		this.description = description;
		this.path = path;
		this.insertDate = insertDate;
		this.alterDate = alterDate;
		this.sizes = sizes;
		this.parentFolderCode = parentFolderCode;
		this.folderContent = folderContent;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getParentFolderCode() {
		return parentFolderCode;
	}
	public void setParentFolderCode(String parentFolderCode) {
		this.parentFolderCode = parentFolderCode;
	}
	public String getFolderContent() {
		return folderContent;
	}
	public void setFolderContent(String folderContent) {
		this.folderContent = folderContent;
	}
	
	
}

