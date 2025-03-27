package utilities;
import org.testng.Assert;

import io.restassured.response.Response;

public class ResponseValidator {

	public static void validateStatusCode(int actualStatusCode, int expStatusCode) {
		 Assert.assertEquals(actualStatusCode, expStatusCode, "Status code validation failed!");;
		
	}
	public static void validateContentType(String actualContentType, String expContentType) {
		 Assert.assertEquals(actualContentType, expContentType, "Content type validation failed!");		
	}
	
	public static void validateDataType(Response response, String jsonPath, Class<?> expectedType) {
        Object value = response.jsonPath().get(jsonPath);
        Assert.assertNotNull(value, "Value at JSON path '" + jsonPath + "' is null!");
        Assert.assertTrue(expectedType.isInstance(value),
            "Data type validation failed for JSON path '" + jsonPath + "'. Expected: " + expectedType.getSimpleName() + ", Actual: " + value.getClass().getSimpleName());
    }
	
	public static void validateData(String actualValue, String expectedValue) {
	    if (actualValue == null && expectedValue == null) {
	        return; // Both values are null, so validation passes
	    }
	    Assert.assertEquals(actualValue, expectedValue, "Data validation failed!");;
	}
}
