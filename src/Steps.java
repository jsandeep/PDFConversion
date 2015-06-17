import org.json.simple.JSONObject;

public class Steps {
	private String id;
	private String version;
	private String name;
	private String type;
	private String details;

	protected Steps(String id, String version, String name, String type,
			String details) {
		this.id = id;
		this.version = version;
		this.name = name;
		this.type = type;
		this.details = details;
	}

	public static Steps JSONToStepsObject(JSONObject stepObject) {
		String id = (String) ((stepObject.get("id") != null) ? stepObject
				.get("id") : "");
		String version = (String) ((stepObject.get("version") != null) ? stepObject
				.get("version") : "");
		String name = (String) ((stepObject.get("name") != null) ? stepObject
				.get("name") : "");
		String type = (String) ((stepObject.get("type") != null) ? stepObject
				.get("type") : "");
		String details = (String) ((stepObject.get("details") != null) ? stepObject
				.get("details") : "");

		Steps steps = new Steps(id, version, name, type, details);
		return steps;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
