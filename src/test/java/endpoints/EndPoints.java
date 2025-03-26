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
	
	//Class end points
		CREATE_CLASS("/CreateClassSchedule"),
		GET_All_CLASSES("/allClasses"),
		GET_CLASSDETAILS_BY_ID("/class/{classId}"),
		GET_CLASSRECORDINGS_BY_BATCHID("/batchRecordings/{batchId}"),
		GET_CLASSES_BY_CLASSTOPIC("/classes/{classTopic}"),
	    GET_CLASSES_BY_BATCHID("/classesbyBatch/{batchId}"),
	    GET_CLASSDETAILS_BY_INVALIDID("/class/{invalidclassId}"),
	    GET_CLASSRECORDINGS_BY_INVALIDBATCHID("/batchRecordings/{invalidbatchId}"),
	    GET_CLASSES_BY_INVALIDCLASSTOPIC("/classes/{invalidclassTopic}"),
        GET_CLASSES_BY_INVALIDBATCHID("/classesbyBatch/{invalidbatchId}");
		
    private final String endpoint;

    EndPoints(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }
}