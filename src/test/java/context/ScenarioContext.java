package context;

import java.util.HashMap;
import java.util.Map;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ScenarioContext {

    private Map<String, Object> scenarioContext = new HashMap<>();
    private Response response;
    private Map<String, String> rowData;
    private final Map<String, Object> context = new HashMap<>();
    
    public void setRequest(RequestSpecification request) {
        context.put("request", request);
    }

    public RequestSpecification getRequest() {
        return (RequestSpecification) context.get("request");
    }
    public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public Map<String, String> getRowData() {
		return rowData;
	}

	public void setRowData(Map<String, String> rowData) {
		this.rowData = rowData;
	}

	public void setContext(String key, Object value) {
        scenarioContext.put(key, value);
    }

    public Object getContext(String key) {
        return scenarioContext.get(key);
    }

    public boolean contains(String key) {
        return scenarioContext.containsKey(key);
    }
}
