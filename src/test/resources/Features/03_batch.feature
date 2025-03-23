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
		| CreateBatchWithMissingAdditionalFields         |
		| CreateBatchWithValidRequestBody                |
		| CreateBatchWithEmptyProgramId                  |
		| CreateBatchWithEmptyBatchStatus                |
		| CreateBatchWithEmptyNoOfClasses                |
		| CreateBatchWithEmptyBatchName                  |
		| CreateBatchWithValidEndpointNonexistingBody    |
		| CreateBatchWithInvalidEndpoint                 |
		| CreateBatchWithMissingMandatoryFields          |
		| CreateBatchWithInactiveProgramId               |
		| CreateBatchWithInvalidData                     |