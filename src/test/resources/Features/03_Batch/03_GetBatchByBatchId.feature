@GetBatchByBatchId
Feature: Get batch by batch Id

 Background:
     Given Admin sets Authorization to Bearer Token for GET batch request 

    Scenario: Check if admin able to retrieve a batch with valid BATCH ID
    Given Admin creates GET Request with valid Batch ID
    When  Admin sends HTTPS Request with endpoint to get batch details
    Then Admin receives 200 OK Status with response body for batch.        

   



