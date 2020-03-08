Feature: Remove a recipe

  As a Pro user of FreshFork I would like to remove my own recipes

  Scenario: Remove a recipe which I added (Normal Flow)
  
    Given I am a pro user 
    And I have registered recipes on my account 
    When I request removal of a recipe "Apple Sauce" from my account
    Then recipe "Apple Sauce" will be removed

  Scenario: Pro User attempts to remove recipe that is not on their account (Error Flow)
  
    Given I am a pro user 
    When I request to delete a recipe that is not mine 
    Then a remove recipe error "User is not creator" message is issued
  
  
  Scenario:  Non-Pro User attempts to remove recipe(Error FLow)
  	Given I am a non-pro user 
    When I request to delete a recipe 
    Then a remove recipe error "User not pro" message is issued
    
  Scenario: User attempts to remove recipe that does not exist (Error Flow)
  	Given I am a pro user 
  	When I request to delete a recipe that does not exist
  	Then a remove recipe error "Recipe not found" message is issued
    

