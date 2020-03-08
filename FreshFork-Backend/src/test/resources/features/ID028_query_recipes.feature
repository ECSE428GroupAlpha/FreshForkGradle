Feature: Query Recipe
	As a user of the system 
	I would like to search for a recipe on the system
	So that I can find specific recipes

	Scenario: User searches for recipe (Normal Flow)
		
		Given that I am a user of the system
		And there are recipes in the system
		When I request to search for a recipe 
		Then the system returns a list of recipes within the search criteria
		
	Scenario: Non-user attempts to search for recipes(Error Flow)
		
		Given that I am not a user of the system
		When I request to search for a recipe
		Then the system returns a list diet error message "User not found"
	

