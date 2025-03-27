@DeleteBatch
Feature: Delete Batch

   Background:
    Given Admin sets Authorization to Bearer Token for DELETE batch request 

    Scenario: Check if admin able to delete a Batch with valid Batch ID
    
    Given Admin creates a DELETE request with a valid BatchId.
    When Admin sends  HTTPS request to the endpoint for deleting a batch by BatchId
    Then Admin receives a 200 OK status with a message confirming the deletion of the batch by BatchId.
    
   
    Scenario: Check if admin able to delete a Batch with invalid endpoint
    
    Given Admin creates DELETE Request with valid BatchId with invalid endpoint
    When Admin sends HTTPS Request  with invalid endpoint for deleting a batch by BatchId
    Then Admin receives 404 Not Found with Message and boolean success details for the deletion of the batch by BatchId.
    
    Scenario: Check if admin able to delete a Batch with invalid Batch ID
    
    Given Admin creates DELETE Request with invalid BatchId
    When Admin sends HTTPS Request  with invalid BatchId for deleting a batch by BatchId
    Then Admin receives 404 Not Found with Message and boolean success details for the deletion of the batch by BatchId.
    
    Scenario:  Check if admin able to delete a Batch without authorization
    
    Given Admin creates DELETE Request  without authorization for the deletion of the batch by BatchId
    When Admin sends  HTTPS request to the endpoint for deleting a batch by BatchId
    Then Admin receives 401 Unauthorized Status for the deletion of the batch by BatchId.

  #UpdateBatchWithDeletedBatchId
  
  Scenario Outline: Check if admin able to update a Batch with a deleted batchID in the endpoint

    Given Admin creates PUT Request with valid BatchId and Data 
    When Admin sends PUT Request with data from row "<Scenario>" for update batch
    Then the response status should be equal to ExpectedStatus for update batch
    
      Examples:
    | Scenario 			    														   |
  	| UpdateBatchWithDeletedBatchId										 |


   