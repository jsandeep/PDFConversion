import org.json.simple.JSONObject;

public class StateType {
	private String key;
	private String value;

	protected StateType(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public static StateType JSONToStateTypeObject(JSONObject stateTypeObject) {

		String key = (String) ((stateTypeObject.get("key") != null) ? stateTypeObject
				.get("key") : "");
		String value = (String) ((stateTypeObject.get("value") != null) ? stateTypeObject
				.get("value") : "");
		StateType stateType = new StateType(key, value);
		return stateType;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
