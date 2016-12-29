package form;

public class AlarmForm {

	private String code;
	private String title;
	private String content;
	private String sendDate;
	public AlarmForm() {
	}
	public AlarmForm(String code, String title, String content, String sendDate) {
		this.code = code;
		this.title = title;
		this.content = content;
		this.sendDate = sendDate;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	
	
}
