@user
Feature: User

  Background:
    Given Admin sets Authorization to Bearer Token for create user. 


  Scenario Outline: Admin able to create a User with valid endpoint and request body
    Given Admin creates POST Request with valid user data in request body 
    When Admin sends HTTPS Request with user data from row "<Scenario>"
    Then the user api response status should be equal to ExpectedStatus


  Examples:
    |  Scenario 			    		     |
  	| CreateUserWithValidData	     |
  	| CreateUserWithEmptyRoleStatus|
    | CreateUserWithEmptyFirstName |
    | CreateUserWithEmptyLast Name |
    | CreateUserWithEmptyTimeZone  |
    | CreateUserWithEmptyVisaStatus|
