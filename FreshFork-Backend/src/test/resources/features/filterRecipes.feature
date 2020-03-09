Feature: Filter recipes
	As a user of the FreshFork system
  I would like to filter recipes 
  So that I can easily find what recipe I am interested in
  

  Scenario: Filter recipes by diet (Normal Flow)
  
    Given I am a user of the freshfork system
    And there are diets in the freshfork system
    And there are recipes in the fresFork system
    When I choose to filter recipes by diet
    Then The system return a list of filtered recipes
  
  Scenario: User attempts to filter by diet that does not exist(Error Flow)
  	
		Given I am a user of the freshfork system
		But there are no diets
    When I choose to filter recipes by diet
    Then The system returns a filter recipe error message "Diet does not exist."
    
  Scenario: Non user tries to filter diet(Error Flow)
  	
  	Given I am not a user of the freshfork system
  	When I choose to filter recipes by diet
  	Then The system returns a filter recipe error message "User not found"
