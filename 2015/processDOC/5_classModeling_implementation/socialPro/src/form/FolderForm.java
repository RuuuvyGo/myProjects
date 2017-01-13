package form;

public class FolderForm {

	private String type;
	private String id;
	private String name;
	private String makeDate;
	private String alterDate;
	private String description;
	
	public FolderForm() {
	}
	public FolderForm(String type, String id, String name) {
		this.type = type;
		this.id = id;
		this.name = name;
	}
	
	
	public FolderForm(String type, String id, String name, String makeDate,
			String alterDate,String description) {
		this.type = type;
		this.id = id;
		this.name = name;
		this.makeDate = makeDate;
		this.alterDate = alterDate;
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(String makeDate) {
		this.makeDate = makeDate;
	}
	public String getAlterDate() {
		return alterDate;
	}
	public void setAlterDate(String alterDate) {
		this.alterDate = alterDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
