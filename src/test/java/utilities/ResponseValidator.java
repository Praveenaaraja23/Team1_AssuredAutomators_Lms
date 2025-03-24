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
	
//	public static void validateData(Response response, String jsonPath, Object expectedValue) {
//        Object actualValue = response.jsonPath().get(jsonPath);
//        Assert.assertEquals(actualValue, expectedValue, "Data validation failed for JSON path '" + jsonPath + "'");
//    }
	
	
//	public static void validateSchema(Response response, String schemaFilePath) {
//        try {
//            JSONObject jsonSchema = new JSONObject(new JSONTokener(
//                ResponseValidator.class.getClassLoader().getResourceAsStream(schemaFilePath)
//            ));
//            Schema schema = SchemaLoader.load(jsonSchema);
//            JSONObject responseJson = new JSONObject(response.getBody().asString());
//            schema.validate(responseJson);
//        } catch (ValidationException e) {
//            Assert.fail("Schema validation failed: " + e.getMessage());
//        }
//    }
}
