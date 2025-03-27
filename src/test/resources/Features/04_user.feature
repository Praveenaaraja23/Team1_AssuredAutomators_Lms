@lms @User
Feature: Create User API

  Background:
    Given Admin sets Authorization to Bearer Token to create user. 


  Scenario Outline: Admin able to create a User with valid endpoint and request body
    Given Admin creates POST Request  with valid data in request body for create user
    When Admin sends HTTPS Request with data from row "<Scenario>" for create user
    Then the response status should be equal to ExpectedStatus for create user


  Examples:
    | Scenario 			    														 |
  	| CreateUserWithValidData											   |