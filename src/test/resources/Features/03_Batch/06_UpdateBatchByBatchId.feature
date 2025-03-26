@lms @UpdateBatchByBatchId
Feature: UpdateBatchByBatchId

  Background:
    Given Admin sets Authorization to Bearer Token for update batch. 


  Scenario Outline:Check if admin able to update a Batch with valid batchID and mandatory fields in request body

    Given Admin creates PUT Request with valid BatchId and Data 
    When Admin sends PUT Request with data from row "<Scenario>"
    Then the response status should be equal to ExpectedStatus for update batch


  Examples:
    | Scenario 			    														   |
  	| UpdateBatchIdWithAllFields											 |
		| UpdateBatchIdWithMandatoryFields        				 |
		| UpdateBatchIdWithMIssingMandatoryFields          |
		| UpdateBatchWithInvalidData                  		 |
		
		
		
		
		