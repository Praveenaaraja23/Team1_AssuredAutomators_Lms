package endpoints;

public enum EndPoints {
	
	//Batch end points
	CREATE_BATCH("/batches"),
	GET_ALL_BATCHES("/batches"),
    GET_BATCH_BY_ID("/batches/batchId/{batchId}"),
    GET_BATCH_BY_BATCHNAME("/batches/batchName/{batchName}"),
    GET_BATCH_BY_PROGRAMID("/batches/program/{programId}"),
    UPDATE_BATCH_BY_ID("/batches/{batchId}"),
    DELETE_BATCH_ID("/batches/{id}"),
    
	
	//User end points
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
    DELETE_USER("/users/{userID}");
  
	
    private final String endpoint;

    EndPoints(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }
}