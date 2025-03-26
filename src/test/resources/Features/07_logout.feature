
Feature: Logout

 Background:
    Given Admin sets Authorization to Bearer Token for logout user.  

    Scenario Outline: Check if Admin able to logout with valid endpoint
    When Admin calls Get Https method with valid endpoint for logout
    Then Admin receives 200 OK  Status with logout response body
    
    Scenario Outline: Check if Admin able to logout with invalid endpoint
    When Admin calls Get Https method with invalid endpoint for logout
    Then Admin receives 400 Not Found Status with logout response body    
   
    Scenario Outline: Check if Admin able to logout with No Auth
    Given Admin creates request with no Auth
    When Admin calls Get Https method with valid endpoint
    Then Admin receives 401 Unauthorized Status with logout response body
 