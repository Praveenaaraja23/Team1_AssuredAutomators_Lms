@user
Feature: User

  Background:
    Given Admin sets Authorization to Bearer Token for update user. 


  Scenario: Admin able to update User with valid endpoint and request body
    Given Admin sends PUT Request with valid user data in request body 
    When Admin sends Put HTTPS Request with user data from row "<Scenario>"
    Then the user api response status should be equals to Expected Status

  Examples:
    |  Scenario 			    		             |
  	| UpdateUserWithValidData	             |
  	| UpdateUserwithValidData              |
    | UpdateUserWithMissingMandatoryFields |
    | UpdateUserWithEmptyPhone Number      |
    | UpdateUserWithEmptyFirstName         |
    | UpdateUserWithEmptyLast Name         |
    | UpdateUserWithEmptyTimeZone          |
    | UpdateUserWithEmptyVisaStatus        |


Scenario: Admin able to update User with invalid User ID with request body
    Given User sends PUT Request with in invalid userId and  the LMS API endpoint
    When User sends PUT HTTPS Request and request Body with valid mandatory fields
    Then User receives 404 Not Found Status with message




  Scenario: Admin able to update User role status by valid User ID with request body
    Given User sends PUT Request with valid userId and valid endpoint 
    When User sends PUT HTTPS Request and  request Body with valid mandatory fields
    Then User receives 200 OK Status with message and boolean success details for user role status
    
  Scenario: Admin able to update User role status by invalid User ID with request body
    Given User sends PUT Request with in invalid userId and  the LMS API endpoint
    When User sends PUT HTTPS Request and  request Body with valid mandatory fields
    Then User receives 404 Not Found Status with message User role status
    
  Scenario: Admin able to update User role status by valid User ID  with missing fields
    Given User sends PUT Request with in valid userId and  the LMS API endpoint
    When User sends PUT HTTPS Request and  request Body with missing fields
    Then User receives 400 Bad Request Status with message for user role status
   
   
   
   
    
    Scenario: Admin able to update user to Assign User to Program / Batch by valid User ID with request body
    Given User sends PUT Request with valid userId and valid endpoint 
    When User sends PUT HTTPS Request and  request Body with valid mandatory fields
    Then User receives 200 OK Status with message and boolean success details for user role status
    
  Scenario: Admin able to update user to Assign User to Program / Batch by invalid User ID with request body
    Given User sends PUT Request with in invalid userId and  the LMS API endpoint
    When User sends PUT HTTPS Request and  request Body with valid mandatory fields
    Then User receives 404 Not Found Status with message 
    
  Scenario: Admin able to update user to Assign User to Program / Batch by valid User ID and request body with missing fields
    Given User sends PUT Request with in valid userId and  the LMS API endpoint
    When User sends PUT HTTPS Request and  request Body with missing fields into Assign User to Program / Batch 
    Then User receives 400 Bad Request Status with message for Assign User to Program / Batch
  
  