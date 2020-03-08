Feature: Login

  As a user of FreshFork
  I would like to login into the FreshFork system
  So that I can use the FreshFork system

  Scenario: User logs into the freshfork system(Normal Flow)
  
    Given I am a user of the FreshFork system
    When I request to login to the FreshFork System with a valid email and password
    Then the system should login me in

  Scenario: User attempts to login to the FreshFork system with invalid email(Error Flow)
  
    Given I am a user of the FreshFork system
    When I request to login to the FreshFork System with an invalid email and correct password
    Then the system should return a login error message "Account with given email does not exist."
 
  Scenario: User attempts to login to the FreshFork system with invalid email (Error Flow)
  
    Given I am a user of the FreshFork system
    When I request to login to the FreshFork System with an valid email and incorrect password
    Then the system should return a login error message "Incorrect password."   
  

