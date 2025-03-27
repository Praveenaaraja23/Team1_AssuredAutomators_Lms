@lms @login
Feature: User Login

  Background: 
   Scenario: Check if Admin able to generate token with valid credential
   Given Admin creates request with valid credentials
   When Admin calls Post Https method  with valid endpoint
   Then Admin receives 200 created with auto generated token
         
   Scenario: Check if Admin able to generate token with valid inendpoint and valid credentialss
   Given: Admin creates request with valid credentials
   When Admin calls Post Https method  with invalid endpoint for auto generated token
   Then Admin receives 401 Unauthorized in response body for auto generated token
   
   Scenario: Check if Admin able to generate token with valid endpoint and invalid credentialss
   Given: Admin creates request with invalid credentials for auto generated token
   When Admin calls Post Https method  with valid endpoint 
   Then Admin receives 400 Not Found Status in response body for auto generated token