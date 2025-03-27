package utilities;

import java.io.File;

import org.testng.Assert;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class ResponseValidator {

	public static void validateStatusCode(int actualStatusCode, int expStatusCode) {
		Assert.assertEquals(actualStatusCode, expStatusCode, "Status code validation failed!");
	}

	public static void validateContentType(String actualContentType, String expContentType) {
		Assert.assertEquals(actualContentType, expContentType, "Content type validation failed!");
	}

	public static void validateDataType(Response response, String jsonPath, Class<?> expectedType) {
		Object value = response.jsonPath().get(jsonPath);
		Assert.assertNotNull(value, "Value at JSON path '" + jsonPath + "' is null!");
		Assert.assertTrue(expectedType.isInstance(value), "Data type validation failed for JSON path '" + jsonPath
				+ "'. Expected: " + expectedType.getSimpleName() + ", Actual: " + value.getClass().getSimpleName());
	}

	public static void validateData(String actualValue, String expectedValue) {
		if (actualValue == null && expectedValue == null) {
			return;
		}
		Assert.assertEquals(actualValue, expectedValue, "Data validation failed!");
		;
	}

	// validate JSON schema
	public static void validateJsonSchema(Response response, String schemaFileName) {
		try {
			// Path to schema file
			File schemaFile = new File("src/test/resources/Schema/" + schemaFileName);

			// Validate response against the schema
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
			System.out.println("Schema validation successful for: " + schemaFile);
		} catch (Exception e) {
			Assert.fail("Schema validation failed: " + e.getMessage());
		}

	}
}
