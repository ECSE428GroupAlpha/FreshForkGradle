Feature: List diets

  As a user of Freshfork system 
  I would like to list all diets
  So that I can choose diets

  Scenario: User lists all diets
  	Given I am user of the system 
  	When I request to get the list of diets
  	Then the system will return a list of diets

  Scenario: Non user attemps to obtain list of diets
    Given I am not a user of the system 
    When I request to get the list of diets
    Then The system will return a list diet error "Account with given email does not exist."