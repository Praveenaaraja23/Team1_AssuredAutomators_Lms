@lms @user
Feature: User

  Background:
    Given Admin sets Authorization to Bearer Token for Get User. 


  Scenario Outline: Check if user able to retrieve all user with valid LMS API
    Given Admin creates GET Request with valid user data in request body 
    When Admin sends GET Request with endpoint 
    Then Admin receives 200 OK Status with response body.   
    
  Scenario Outline: Check if user able to retrieve a user with valid User ID 
    Given User creates GET Request with valid User Id 
    When Admin sends GET Request with valid endpoint to get User details
    Then Admin receives 200 OK Status with response body.

  Scenario Outline: Check if user able to retrieve a user with invalid User ID 
    Given User creates GET Request with invalid User Id 
    When Admin sends GET Request with valid endpoint to get User details
    Then Admin receives 400 Not Found Status with response body.
    
  Scenario Outline: Check if user able to retrieve all staff users with valid LMS API
    Given User creates GET Request for the LMS API All Staff endpoint 
    When Admin sends GET Request with staff endpoint 
    Then Admin receives 200 OK Status with response body. 
    
  Scenario Outline: Check if user able to retrieve all Roles user with valid LMS API
    Given User creates GET Request for the LMS API All Roles endpoint 
    When Admin sends GET Request with all Roles endpoint 
    Then Admin receives 200 OK Status with response body.
  
