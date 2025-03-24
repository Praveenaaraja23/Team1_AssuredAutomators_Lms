@lms @login
Feature: User Login

  Background: Admin sets No Auth
  
  Scenario Outline: Check if Admin able to generate token
    Given Admin creates request with valid credentials
    When Admin calls Post Https method  with valid endpoint with data from row "<Scenario>"
    Then Admin receives 200 created with auto generated token
    
    Examples:
    | Scenario 			    														 |
  	| ValidCredentials								               |
