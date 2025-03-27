@lms @UpdateBatchByBatchId
Feature: UpdateBatchByBatchId

  Background:
    Given Admin sets Authorization to Bearer Token for update batch. 


  Scenario Outline:Check if admin able to update a Batch with valid batchID and mandatory fields in request body

    Given Admin creates PUT Request with valid BatchId and Data 
    When Admin sends PUT Request with data from row "<Scenario>" for update batch
    Then the response status should be equal to ExpectedStatus for update batch


  Examples:
    | Scenario 			    														   |
  	| UpdateBatchIdWithAllFields											 |
		| UpdateBatchIdWithMandatoryFields        				 |
		| UpdateBatchIdWithMIssingMandatoryFields          |
		| UpdateBatchWithInvalidData                  		 |
		
		
		
		
		Scenario Outline: Check if admin able to update a Batch with valid batchID and mandatory fields in request body without authorization

    Given Admin sets Authorization to No Auth for updating batch by batchId 
    When Admin sends PUT Request with data from row "<Scenario>" for update batch
    Then the response status should be equal to ExpectedStatus for update batch
    
      Examples:
    | Scenario 			    														   |
  	| UpdateBatchWithNoAuth											       |
  	
  	Scenario Outline: Check if admin able to update a Batch with invalid batchID and mandatory fields in request body

    Given Admin creates PUT Request with invalid BatchId and Data 
    When Admin sends PUT Request with data from row "<Scenario>" for update batch
    Then Admin receives 404 Not Found Status with message and boolean success details for update batch
    
     Examples:
    | Scenario 			    														   |
  	| UpdateBatchWithInvalidBatchId										 |
    
    Scenario Outline: Check if admin able to update a Batch with a valid batchID and deleted programID field  in the request body with other mandatory fields

    Given Admin creates PUT Request with valid BatchId and Data 
    When Admin sends PUT Request with data from row "<Scenario>" for update batch
    Then Admin receives 400 Bad Request Status with message and boolean success details for updating batch
    
     Examples:
    | Scenario 			    														   |
  	| UpdateBatchWithDeletedProgramID										 |
    
     