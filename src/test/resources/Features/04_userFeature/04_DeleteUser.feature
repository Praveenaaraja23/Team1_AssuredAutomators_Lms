@user
Feature: User

  Background:
    Given Admin sets Authorization to Bearer Token for delete user. 


  Scenario Outline: Check if user able to delete a user with valid User Id 
    Given Admin creates Delete Request with valid user id in request body 
    When Admin sends Delete Request with valid endpoint 
    Then Admin receives 200 OK  Status with delete Id response body.   

Scenario Outline: Check if user able to delete a user with valid User Id 
    Given Admin creates Delete Request with invalid user id in request body 
    When Admin sends Delete Request with valid endpoint with invalid user id
    Then Admin receives 400 Not Found Status with response body.   
