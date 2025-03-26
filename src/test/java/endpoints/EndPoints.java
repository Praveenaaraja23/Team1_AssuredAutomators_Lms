package endpoints;

public enum EndPoints {
	//Login end points
	USER_SIGN_IN("/login"),
	
	//Batch end points
	CREATE_BATCH("/batches"),
	GET_ALL_BATCHES("/batches"),
    GET_BATCH_BY_ID("/batches/batchId/{batchId}"),
    GET_BATCH_BY_BATCHNAME("/batches/batchName/{batchName}"),
    GET_BATCH_BY_PROGRAMID("/batches/program/{programId}"),
    UPDATE_BATCH_BY_ID("/batches/{batchId}"),
    DELETE_BATCH_ID("/batches/{id}"),
	
	//user endpoint
	CREATE_USER("/users/roleStatus"),
    
	//Class Module part 2 Endpoints
	 Get_AllClass_byStaffID("/classesByStaff/{staffId}"),
	    Get_AllClass_byStaffID_Invalid("/classesByStaff/staffId/http"),
	    Get_ALlRecordings("/classrecordings"),
	     Get_ALlRecordings_Invalid("/classrecordings/staff"),
	   Get_AllRecordings_byClassID("/classRecordings/{classId}"),
	  Get_AllRecordings_byClassID_Invalid("/classRecordings/cl"),
	    Put_Class("/updateClass/{classId}"),
	    Put_Class_Invalid("/updateClass/lassId"),
	     Put_classREcording("/updateClassrecording/{classId}"),
	  Put_classREcordingInvalidEnd ("/updateClassrecording/classId"),
	    Delete_classByID("/deleteByClass/{classId}"),
	     Delete_classByID_Invalid("/deleteByClass/classId");
	    //***class part 2 code END
    private final String endpoint;

    EndPoints(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }
}