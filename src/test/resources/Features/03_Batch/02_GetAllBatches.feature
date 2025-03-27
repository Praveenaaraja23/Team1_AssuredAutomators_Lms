
@GetBatches
Feature: Get Batch

   Background:
    Given Admin sets Authorization to Bearer Token for GET batch request 

    Scenario: Check if admin able to retrieve all batches  with valid LMS API

    Given Admin creates GET Request for get batches 
    When Admin sends HTTPS Request with endpoint for get batches 
    Then Admin receives 200 OK Status with response body for get batches.
    
    Scenario: Check if admin able to retrieve all batches with invalid Endpoint

    Given Admin creates GET Request with invalid endpoint for get batches 
    When Admin sends HTTPS Request with  invalid endpoint for get batches 
    Then Admin receives 404 status with error message Not Found for get batches       
 
    
     Scenario:  Check if admin able to retrieve all batches without Authorization 

    Given Admin sets no Authorization and creates GET Request for get batches 
    When Admin sends HTTPS Request with No auth, valid endpoint for get batches
    Then Admin receives 401 status with error message Unauthorized for get batches
    
 

     