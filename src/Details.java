import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Details {
	private String code;
	private String description;
	private DocumentType documentType;
	private List<String> tags;
	private String authorName;
	private String version;
	private StateType stateType;
	private String createDate;
	private String modifiedDate;
	private List<String> procedureComments;

	protected Details(String code, String description,
			DocumentType documentType, List<String> tags, String authorName,
			String version, StateType stateType, String createDate,
			String modifiedDate, List<String> procedureComments) {
		this.code = code;
		this.description = description;
		this.documentType = documentType;
		this.tags = tags;
		this.authorName = authorName;
		this.version = version;
		this.stateType = stateType;
		this.createDate = createDate;
		this.modifiedDate = modifiedDate;
		this.procedureComments = procedureComments;
	}

	public static Details JSONToDetailObject(JSONObject detailObject) {
		String code = (String) ((detailObject.get("code") != null) ? detailObject
				.get("code") : "");
		String description = (String) ((detailObject.get("description") != null) ? detailObject
				.get("description") : "");
		String authorName = (String) ((detailObject.get("authorName") != null) ? detailObject
				.get("authorName") : "");
		String version = (String) ((detailObject.get("version") != null) ? detailObject
				.get("version") : "");
		String createDate = (String) ((detailObject.get("createDate") != null) ? detailObject
				.get("createDate") : "");
		String modifiedDate = (String) (((String) detailObject
				.get("modifiedDate") != null) ? detailObject
				.get("modifiedDate") : "");

		JSONArray tagsArray = new JSONArray();
		tagsArray = (JSONArray) ((detailObject.get("tags") != null) ? detailObject
				.get("tags") : new JSONArray());
		List<String> tags = new ArrayList<String>();
		for (int i = 0; i < tagsArray.size(); i++) {
			tags.add((String) tagsArray.get(i));
		}
		StateType stateType = StateType
				.JSONToStateTypeObject((JSONObject) detailObject
						.get("stateType"));
		DocumentType documentType = DocumentType
				.JSONToDocumentTypeObject((JSONObject) detailObject
						.get("documentType"));

		JSONArray procCommentsArray = new JSONArray();
		procCommentsArray = (JSONArray) ((detailObject.get("procedureComments") != null) ? detailObject
				.get("procedureComments") : "");
		List<String> procedureComments = new ArrayList<String>();
		for (int i = 0; i < procCommentsArray.size(); i++) {
			procedureComments.add((String) procCommentsArray.get(i));
		}

		Details detail = new Details(code, description, documentType, tags,
				authorName, version, stateType, createDate, modifiedDate,
				procedureComments);

		return detail;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public StateType getStateType() {
		return stateType;
	}

	public void setStateType(StateType stateType) {
		this.stateType = stateType;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public List<String> getProcedureComments() {
		return procedureComments;
	}

	public void setProcedureComments(List<String> procedureComments) {
		this.procedureComments = procedureComments;
	}

}
