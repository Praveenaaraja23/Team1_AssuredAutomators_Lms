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
	
	//user end points
	CREATE_USER("/users/roleStatus"),
	
		CREATING_USER_WITH_ROLE("/users/roleStatus"),
		GET_ALL_USERS("/users"),
		GET_ALL_ACTIVE_USERS("users/activeUsers"),
		GET_EMAILS_OF_ALL_USERS_WITH_ACTIVE_STATUS("/fetch-emails"),
		GET_ALL_ROLES("/roles"),
		GET_USER_INFORMATION_BY_USERID("/users/{userId}"),
		GET_USER_INFORMATION_BY_STAFFID("/classesByStaff/{staffId}"),
		GET_ALL_USERS_WITH_ROLES("/users/roles"),
		GETS_COUNT_OF_ACTIVE_AND_INACTIVE_USERS("/users/byStatus"),
		GETS_USER_BY_PROGRAM_BATCHES("/users/programBatch/{batchId}"),
	    GETS_USERS_FOR_PROGRAM("/users/programs/{programId}"),
	    GETS_USERS_BY_ROLEID("/users/roles/{roleId}"),
	    GETS_USERS_BY_ROLEID_V2("/v2/users"),
	    UPDATE_USER("/users/{userId}"),
		UPDATE_USER_ROLEID("/users/roleId/{userID}"),
	    ASSIGN_UPDATE_USER_ROLE_PROGRAM_BATCH_STATUS("/users/roleProgramBatchStatus/{userId}"),
	    UPDATE_USER_LOGIN_STATUS("/users/userLogin/{userId}"),
	    DELETE_USER("/users/{userID}"),

		
    
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
	     Delete_classByID_Invalid("/deleteByClass/classId"),
	   
	
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
        GET_CLASSES_BY_INVALIDBATCHID("/classesbyBatch/{invalidbatchId}"),
		
   
	//Program endpoints
	SAVE_PROGRAM("/saveprogram"),
	GET_ALL_PROGRAMS("/allPrograms"),
	GET_PROGRAM_BY_ID("/programs/{programId}"),
	GET_All_Programs_with_Users("/allProgramsWithUsers "),
	Update_Program_by_ProgramName("/program/{programName}"),
	Update_Program_by_ProgramId("/putprogram/{programId}"),
	Delete_Program_By_ProgramID("/deletebyprogid/{programId}"),
	Delete_Program_By_ProgramName("/deletebyprogname/{programName}"),
	
	//logout end points
			LOGOUT("/logoutlms");
	
    
    private String endpoint;
	private static final String baseUrl = "https://lms-hackthon-feb25-803334c87fbe.herokuapp.com/lms";

    EndPoints(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }
    public String getFullEndpoint() {
        return baseUrl + this.endpoint;  
    }
}




