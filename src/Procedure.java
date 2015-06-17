import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Procedure {
	private String id;
	private String name;
	private Details details;
	private List<Steps> steps;

	protected Procedure(String id, String name, Details detail,
			List<Steps> steps) {
		this.id = id;
		this.name = name;
		this.details = detail;
		this.steps = steps;
	}

	public static Procedure JSONToProcedureObject(JSONObject procedureObject) {
		String id = (String) procedureObject.get("id");
		String name = (String) procedureObject.get("name");
		Details details = Details
				.JSONToDetailObject((JSONObject) procedureObject.get("details"));

		JSONArray stepsArray = new JSONArray();
		stepsArray = (JSONArray) procedureObject.get("steps");
		List<Steps> steps = new ArrayList<Steps>();

		for (int i = 0; i < stepsArray.size(); i++) {
			JSONObject stepsObject = (JSONObject) stepsArray.get(i);
			steps.add(Steps.JSONToStepsObject(stepsObject));
		}

		Procedure procedure = new Procedure(id, name, details, steps);

		return procedure;
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

	public Details getDetails() {
		return details;
	}

	public void setDetails(Details details) {
		this.details = details;
	}

	public List<Steps> getSteps() {
		return steps;
	}

	public void setSteps(List<Steps> steps) {
		this.steps = steps;
	}

}
