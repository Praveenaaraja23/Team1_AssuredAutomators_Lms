package models;

import java.util.Map;

public class programpojo {

	    private String programName;
	    private String programDescription;
	    private String programStatus;
	    
	    // Getters and Setters
	    public String getProgramName() {
	        return programName;
	    }

	    public void setProgramName(String programName) {
	        this.programName = programName;
	    }

	    public String getProgramDescription() {
	        return programDescription;
	    }

	    public void setProgramDescription(String programDescription) {
	        this.programDescription = programDescription;
	    }

	    public String getProgramStatus() {
	        return programStatus;
	    }

	    public void setProgramStatus(String programStatus) {
	        this.programStatus = programStatus;
	    }
	    
	    public String getJson() {
	        return 
	               "{\"programDescription\": \"" + programDescription + "\", " +
	               "\"programName\": \"" + programName + "\", " +
	               "\"programStatus\": \"" + programStatus + "\"}";
	    }
	    
	}


