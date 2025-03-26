@GetBatchByBatchName
Feature: Get batch by batch name

 Background:
 Given Admin sets Authorization to Bearer Token for GET batch request
  
 Scenario: Check if admin able to retrieve a batch with valid BATCH NAME
    Given Admin creates GET Request with valid Batch Name
    When Admin sends HTTPS Request with endpoint for get batch by batch name 
    Then Admin receives 200 OK Status with batch response body for corresponding batch name.
    
    Scenario:  Check if admin able to retrieve all batches without Authorization for get batch by batch name

    Given Admin sets no Authorization and creates GET Request for get batch by batch name
    When Admin sends HTTPS Request with No auth, valid endpoint for get batch by batch name
    Then Admin receives 401 status with error message Unauthorized for get batch by batch name.
    
    Scenario: Check if admin able to retrieve all batches with invalid Endpoint for get batch by batch name

    Given Admin creates GET Request with invalid endpoint for get batch by batch name 
    When Admin sends HTTPS Request with  invalid endpoint for get batch by batch name
    Then Admin receives 404 status with error message Not Found for get batch by batch name.
    
   

                                                            
    