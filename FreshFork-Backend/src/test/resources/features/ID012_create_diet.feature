Feature: Create Diet 
  As a pro user of FreshFork System 
  I would like to create a diet 
  So that I can filter recipes by diet

  Scenario: Pro user creates a new diet (Normal Flow)
  	
  	Given That i am logged in as a pro user 
  	When I try to create a new diet
  	Then the system will create a new diet

  Scenario: Pro user attempts to new diet with same name as existing diet (Error Flow)
  	
  	Given That i am logged in as a pro user
  	When I try to create a new diet with the same name as an existing diet
  	Then a create diet "Diet with given name has already been created." error message is issued
  	
  Scenario: Non-pro user attempts to new diet (Error Flow)
  	
  	Given That i am logged in as a non-pro user
  	When I try to create a new diet
  	Then a create diet "User is not professional" error message is issued