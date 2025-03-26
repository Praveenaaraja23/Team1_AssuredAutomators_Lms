@lms @batch
Feature: Create Batch API

  Background:
    Given Admin sets Authorization to Bearer Token. 


  Scenario Outline: Admin able to create a Batch with valid endpoint and request body
    Given Admin creates POST Request  with valid data in request body 
    When Admin sends HTTPS Request with data from row "<Scenario>"
    Then the response status should be equal to ExpectedStatus


  Examples:
    | Scenario 			    														 |
  	| CreateBatchWithValidData											 |
#		| CreateBatchWithMissingAdditionalFields         |
#		| CreateBatchWithValidRequestBody                |
#		| CreateBatchWithEmptyProgramId                  |
#		| CreateBatchWithEmptyBatchStatus                |
#		| CreateBatchWithEmptyNoOfClasses                |
#		| CreateBatchWithEmptyBatchName                  |
#		| CreateBatchWithValidEndpointNonexistingBody    |	
#		| CreateBatchWithMissingMandatoryFields          |
#		| CreateBatchWithInactiveProgramId               |
#		| CreateBatchWithInvalidData                     |
		
		
#	Scenario Outline: Check if admin able to create a Batch with valid endpoint and request body without authorization.
    #Given Admin sets Authorization to No  Auth, creates POST Request  with valid data in request body
    #When Admin sends HTTPS Request with data from row "<Scenario>"
    #Then the response status should be equal to ExpectedStatus	
    #
    #Examples:
    #| Scenario 			    														 |
  #	| CreateBatchWithValidDataAndNoAuth							 |
    #
  #Scenario Outline: Check if admin able to create a batch with invalid endpoint
    #Given Admin creates POST Request  with valid data in request body 
    #When Admin sends HTTPS Request with data from row "<Scenario>"
    #Then the response status should be equal to ExpectedStatus	
    #
    #Examples:
    #| Scenario 			    														 |
  #	| CreateBatchWithInvalidEndpoint                 |
    #
   #
    
       
    
 
  







    