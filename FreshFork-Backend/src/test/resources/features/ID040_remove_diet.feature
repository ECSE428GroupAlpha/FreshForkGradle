Feature: Remove a Diet

  Scenario: Pro user removes a diet(Normal Flow)
  
  	Given a pro user is logged on to the freshFork system
  	When the user requests to remove a diet 
  	Then the system will remove the diet from the system
  	
  Scenario: Non-pro user attempts to remove a diet (Error Flow)
  	
  	Given a non-pro user is logged onto the freshFork system
  	When the user requests to remove a diet 
  	Then the system will return an remove diet error message "User not Pro"
  	
  Scenario: User attempts to delete a diet that does not exist
  	
  	Given a pro user is logged on to the freshFork system
		When the user requests to remove a diet from the system that does not exist
		Then the system will return an remove diet error message "diet does not exist"
		

