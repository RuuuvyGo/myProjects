package form;

public class ProjectSearchedForm {

	private String name;
	private String id;
	private String projectOwnerCode;
	private String projectOwnerName;
	private String des;
	private String makeDate;
	private int sharedList;
	private String status;
	
	public ProjectSearchedForm() {
	}
	public ProjectSearchedForm(String name, String id, String projectOwnerCode, String projectOwnerName, String des, String makeDate,
			int sharedList, String status) {
		this.name = name;
		this.id = id;
		this.projectOwnerCode = projectOwnerCode;
		this.projectOwnerName = projectOwnerName;
		this.des = des;
		this.makeDate = makeDate;
		this.sharedList = sharedList;
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(String makeDate) {
		this.makeDate = makeDate;
	}
	public int getSharedList() {
		return sharedList;
	}
	public void setSharedList(int sharedList) {
		this.sharedList = sharedList;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProjectOwnerCode() {
		return projectOwnerCode;
	}
	public void setProjectOwnerCode(String projectOwnerCode) {
		this.projectOwnerCode = projectOwnerCode;
	}
	public String getProjectOwnerName() {
		return projectOwnerName;
	}
	public void setProjectOwnerName(String projectOwnerName) {
		this.projectOwnerName = projectOwnerName;
	}
	
}
