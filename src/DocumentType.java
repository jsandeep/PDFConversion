import org.json.simple.JSONObject;

public class DocumentType {
	private String key;
	private String value;

	protected DocumentType(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public static DocumentType JSONToDocumentTypeObject(JSONObject docTypeObject) {
		JSONObject selected = (JSONObject) ((docTypeObject.get("selected") != null) ? docTypeObject
				.get("selected") : "");

		String key = (String) ((selected.get("key") != null) ? selected
				.get("key") : "");
		String value = (String) ((selected.get("value") != null) ? selected
				.get("value") : "");
		DocumentType docType = new DocumentType(key, value);
		return docType;
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
