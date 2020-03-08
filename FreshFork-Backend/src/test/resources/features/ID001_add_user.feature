Feature: Add a User

  As a person with an email
  I would like to become a user of FreshFork
  So that I can use the Freshwork application

  Scenario: Different types of users (Normal Flow)
		
		Given the user submits the name,email, password and pro atrribute 
		When user request to create a new account on the system 
		Then the system will create a new account 
		
  Scenario: Existing user attempts to become a user (Error Flow)

    Given dwight@schrute.com is a registered email of an existing user of FreshFork
    When dwight@schrute.com requests to create a new account
    Then a "Email is already taken." message is issued
