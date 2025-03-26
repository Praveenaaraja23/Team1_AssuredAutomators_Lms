@user
Feature: User

  Background:
    Given Admin sets Authorization to Bearer Token for update user. 


  Scenario: Admin able to update User with valid endpoint and request body
    Given Admin update PUT Request with valid user data in request body 
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


  Scenario: Admin able to update User role status by User ID with endpoint and request body
    Given User update PUT Request with valid userId and  the LMS API endpoint
    When User sends PUT HTTPS Request and  request Body with valid mandatory fields
    Then User receives 200 OK Status with message and boolean success details
    
  