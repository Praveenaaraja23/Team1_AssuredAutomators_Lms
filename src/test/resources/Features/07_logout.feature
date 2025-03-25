
Feature: Logout


  Scenario Outline: Check if Admin able to logout 
    Given Admin creates request with authorization to bearer Token with token 
    When Admin calls Get Https method with <endpoint> 
    Then Admin receives <status>  

    Examples: 
       | endpoint         | status  |
       | valid            | 200 ok and response with "Logout successful" |
       | invalid endpoint | 404 Not found    |

   
   
    Scenario Outline: Check if Admin able to logout 
    Given Admin creates request with no Auth
    When Admin calls Get Https method with <endpoint> 
    Then Admin receives <status>  
       
       Examples: 
       | endpoint         | status  |
       | valid            | 401 Unauthorized |
       