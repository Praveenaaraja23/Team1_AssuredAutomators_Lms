package utilities;
import org.testng.Assert;

public class ResponseValidator {

	public static void validateStatusCode(int actualStatusCode, int expStatusCode) {
		 Assert.assertEquals(actualStatusCode, expStatusCode, "Status code validation failed!");;
		
	}
	public static void validateContentType(String actualContentType, String expContentType) {
		 Assert.assertEquals(actualContentType, expContentType, "Content type validation failed!");		
	}
}
