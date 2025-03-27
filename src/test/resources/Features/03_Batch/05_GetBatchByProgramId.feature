@GetBatchByProgramId
Feature: Get batch by program Id

 Background:
     Given Admin sets Authorization to Bearer Token for GET batch request 

    Scenario: Check if admin able to retrieve a batch with valid Program ID
    Given Admin creates GET Request with valid Program Id
    When  Admin sends HTTPS Request with valid Program Id for GET batch by program id 
    Then Admin receives 200 OK Status with batch response body for corresponding program.       
    
   

    