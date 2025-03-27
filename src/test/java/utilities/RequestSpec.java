package utilities;

import endpoints.EndPoints;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpec {
	private static final RequestSpecification req_Base= new RequestSpecBuilder()
			.setBaseUri(ConfigReader.getBaseUrl()) 	
			.setContentType(ContentType.JSON).build();
	
	private static final RequestSpecification GetRequest_Base= new RequestSpecBuilder()
			.setBaseUri(ConfigReader.getBaseUrl()) 	
			.build();
	
	public static RequestSpecification Get_AllClass_byStaffID(String staffId ) {
		return new RequestSpecBuilder().addRequestSpecification(req_Base)
				.setBasePath(EndPoints.Get_AllClass_byStaffID.getEndpoint())
				.addPathParam("staffId",staffId)				
				.build();
	}
	public static RequestSpecification Get_AllClass_byStaffID_InvalidENDpoint(String staffId ) {
		return new RequestSpecBuilder().addRequestSpecification(req_Base)
				.setBasePath(EndPoints.Get_AllClass_byStaffID_Invalid.getEndpoint())				
				.build();
	}
	public static RequestSpecification Get_ALlRecordings() {
		return new RequestSpecBuilder().addRequestSpecification(req_Base)
				.setBasePath(EndPoints.Get_ALlRecordings.getEndpoint()).build();
	}
	
	public static RequestSpecification Get_ALlRecordings_Invalid() {
		return new RequestSpecBuilder().addRequestSpecification(req_Base)
				.setBasePath(EndPoints.Get_ALlRecordings_Invalid.getEndpoint()).build();
	}
	public static RequestSpecification Get_AllRecordings_byClassID(String csId) {
		return new RequestSpecBuilder().addRequestSpecification(req_Base)
				.setBasePath(EndPoints.Get_AllRecordings_byClassID.getEndpoint())
				.addPathParam("classId", csId)
				.build();
	}
	public static RequestSpecification Get_AllRecordings_byClassID_InvalidEndpoint() {
		return new RequestSpecBuilder().addRequestSpecification(req_Base)
				.setBasePath(EndPoints.Get_AllRecordings_byClassID_Invalid.getEndpoint())
				.build();
	}
	public static RequestSpecification Put_Class(String csId){
		return new RequestSpecBuilder().addRequestSpecification(req_Base)
				.setBasePath(EndPoints.Put_Class.getEndpoint())
				.addPathParam("classId", csId)
				.build();
	}
	public static RequestSpecification Put_Class_InvalidEndpoint(){
		return new RequestSpecBuilder().addRequestSpecification(req_Base)
				.setBasePath(EndPoints.Put_Class_Invalid.getEndpoint())
				.build();
	}
	public static RequestSpecification  Put_classREcording(String csId){
		return new RequestSpecBuilder().addRequestSpecification(req_Base)
				.setBasePath(EndPoints.Put_classREcording.getEndpoint())
				.addPathParam("classId", csId)
				.build();
	}
	public static RequestSpecification Put_classREcordingInvalidEnd(){
		return new RequestSpecBuilder().addRequestSpecification(req_Base)
				.setBasePath(EndPoints.Put_classREcordingInvalidEnd.getEndpoint())
				.build();
	}
	
	public static RequestSpecification Delete_classByID(String csId){
		return new RequestSpecBuilder().addRequestSpecification(req_Base)
				.setBasePath(EndPoints.Delete_classByID.getEndpoint())
				.addPathParam("classId", csId)
				.build();
	}
	public static RequestSpecification Delete_classByID_Invalid(String csId){
		return new RequestSpecBuilder().addRequestSpecification(req_Base)
				.setBasePath(EndPoints.Delete_classByID_Invalid.getEndpoint()).build();
	}
	public static RequestSpecification Delete_Program_By_ProgramID(int programId2){
		return new RequestSpecBuilder().addRequestSpecification(req_Base)
				.setBasePath(EndPoints.Delete_Program_By_ProgramID.getEndpoint())
				.addPathParam("programId", programId2)
				.build();
	}
	public static RequestSpecification Delete_Batch_By_Batchid(int batchid2){
		return new RequestSpecBuilder().addRequestSpecification(req_Base)
				.setBasePath(EndPoints.DELETE_BATCH_ID.getEndpoint())
				.addPathParam("id", batchid2)
				.build();
	}
	
}
