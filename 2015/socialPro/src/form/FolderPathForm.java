package form;

public class FolderPathForm {

	private String type;
	private String id;
	private String name;
	private String path;
	
	public FolderPathForm() {
	}
	public FolderPathForm(String type, String id, String name) {
		this.type = type;
		this.id = id;
		this.name = name;
	}
	
	public FolderPathForm(String type, String id, String name, String path) {
		this.type = type;
		this.id = id;
		this.name = name;
		this.path = path;
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
