package context;

import java.util.HashMap;
import java.util.Map;

public class TestContext {

    private Map<String, Object> scenarioContext = new HashMap<>();

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
