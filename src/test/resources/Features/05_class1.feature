@lms @class
Feature: Class
  
 Background:
    Given Admin sets Authorization to Bearer Token for class.
    
    
   Scenario Outline: Admin is able to create class with all valid details
    Given Admin creates POST Request for the LMS with request body
    When Admin calls POST request with endpoint from row "<Scenario>"
    Then Admin receive success created status 

    Examples: 
    | Scenario 			    													   |
  	| CreateClassWithValidData											 |
		| CreateClassWithStringBatchID                   |
		|CreateClassWithNonexsistingBatchID              |
	  | CreateClassWithEmptyCLassTopic                 |
	  | CreateClassWithExistingCLassTopic              |
		| CreateClassWithEmptyClassStatus                |
		| CreateClassWithPastClassDate                   |
		| CreateClassWithEmptyClassNo                    |
		| CreateClassWithMandatoryFields                 |
		| CreateClassWithMissingMandatoryFields          |
		|CreateClassWithinvalidclassStatus               |
		|CreateClassWith invalid classNo                 |
	
Scenario: Admin able to retrieve all classes  with valid Endpoint
    Given Admin creates GET Request 
    When Admin sends HTTPS Request with endpoint 
    Then Admin receives success OK Status with response body 
    
Scenario: Admin able to retrieve all classes  with invalid Endpoint
    Given Admin creates GET Request all classes  
    When Admin sends HTTPS Request with invalid endpoint to get all classes 
    Then Admin receives 404 status with error message for all classes
 
 Scenario: Admin able to retrieve all classes without Authorization
    Given Admin creates GET Request all classes without Authorization
    When Admin sends HTTPS Request without Authorization to get all classes 
    Then Admin receives 401 status with error message for all classes without Authorization  
    
Scenario: Admin able to retrieve Class details  with valid BatchId
    Given Admin creates GET Request with Batchid
    When Admin sends HTTPS Request with endpoint and Batchid
    Then Admin receives success OK Status with response body for Batchid 
    
Scenario: Admin able to retrieve Class details  with invalid BatchId
    Given Admin creates GET Request with invalid Batchid
    When Admin sends HTTPS Request with endpoint and invalid Batchid
    Then Admin receives 404 status with error message for invalid Batchid 

Scenario: Admin able to retrieve Class details with invalid Endpoint
    Given Admin creates GET Request for class details by batchid  
    When Admin sends HTTPS Request with invalid endpoint to get class details by batchid 
    Then Admin receives 404 status with error message for class details by batchid
    
Scenario: Admin able to retrieve Class details without Authorization
    Given Admin creates GET Request for class details by batchid without Authorization 
    When Admin sends HTTPS Request without Authorization to get class details by batchid 
    Then Admin receives 401 status with error message for class details by batchid without Authorization
    
Scenario: Admin able to retrieve class recording  with valid Batchid
    Given Admin creates GET Request with Batchid for class recordings
    When Admin sends HTTPS Request with endpoint and Batchid for class recordings
    Then Admin receives success OK Status with response body class recordings with Batchid 
    
Scenario: Admin able to retrieve class recording  with invalid Batchid
    Given Admin creates GET Request with invalid Batchid for class recordings
    When Admin sends HTTPS Request with endpoint and invalid Batchid for class recordings
    Then Admin receives 404 status with error message class recordings with invalid Batchid    

Scenario: Admin able to retrieve Class Recordings with invalid Endpoint
    Given Admin creates GET Request for class Recordings by batchid  
    When Admin sends HTTPS Request with invalid endpoint to get class Recordings by batchid 
    Then Admin receives 404 status with error message for class Recordings by batchid

Scenario: Admin able to retrieve Class Recordings without Authorization
    Given Admin creates GET Request for class Recordings by batchid  without Authorization
    When Admin sends HTTPS Request without Authorization to get class Recordings by batchid 
    Then Admin receives 401 status with error message for class Recordings by batchid without Authorization
       
Scenario: Admin able to retrieve Class details  with valid classId
    Given Admin creates GET Request with classid
    When Admin sends HTTPS Request with endpoint and classid
    Then Admin receives success OK Status with response body for classid
     
Scenario: Admin able to retrieve Class details  with invalid classId
    Given Admin creates GET Request with invalid classid
    When Admin sends HTTPS Request with endpoint and invalid classid
    Then Admin receives 404 status with error message for invalid classid 
    
Scenario: Admin able to retrieve Class details by class id with invalid Endpoint
    Given Admin creates GET Request for class details by classid  
    When Admin sends HTTPS Request with invalid endpoint to get class details by classid 
    Then Admin receives 404 status with error message for class details by classid

Scenario: Admin able to retrieve Class details by class id  without Authorization
    Given Admin creates GET Request for class details by classid   without Authorization
    When Admin sends HTTPS Request  without Authorization to get class details by classid 
    Then Admin receives 401 status with error message for class details by classid  without Authorization

Scenario: Admin  able to retrieve all classes  with valid classtopic
    Given Admin creates GET Request with classtopic
    When Admin sends HTTPS Request with endpoint and classtopic
    Then Admin receives success OK Status with response body for classtopic 

Scenario: Admin  able to retrieve all classes  with invalid classtopic
    Given Admin creates GET Request with invalid classtopic
    When Admin sends HTTPS Request with endpoint and invalid classtopic
    Then Admin receives 404 status with error message for invalid classtopic 
    
Scenario: Admin able to retrieve all Classes  by classtopic with invalid Endpoint
    Given Admin creates GET Request for all classes by classtopic  
    When Admin sends HTTPS Request with invalid endpoint to get all classes by classtopic
    Then Admin receives 404 status with error message for all classes by classtopic
    
Scenario: Admin able to retrieve all Classes  by classtopic without Authorization
    Given Admin creates GET Request for all classes by classtopic without Authorization 
    When Admin sends HTTPS Request without Authorization to get all classes by classtopic
    Then Admin receives 401 status with error message for all classes by classtopic without Authorization