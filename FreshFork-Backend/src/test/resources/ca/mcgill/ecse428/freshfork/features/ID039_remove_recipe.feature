Feature: Remove a recipe  

As a user of FreshFork I would like to remove the recipe I uploaded.  

Scenario: Remove a recipe which I added and hasn’t been shared over hundred times.  

I should get a confirmation message the recipe has been removed.  

     Given I am connected to FreshFork with a valid account.  

     When I request a removal of a recipe with:   

    |Title                   |ID|  

    |Sample Recipe           |01| 

     Then the system will verify the user is the creator of the recipe 

     Then the question should appear to verify the removal of the recipe with: 

     |Title                   |ID|  

     |Sample Recipe           |01|

     When I verify 

     Then the recipe will be removed 

     And I will get a message indicating successful removal of: 

     |title|                   

     |Sample Recipe|  

Scenario: Remove a recipe shared over hundred times.  

I should not be able to remove a recipe which has been shared over hundred times. 

     Given I am connected to FreshFork with a valid account. 

     When I request a removal of the recipe with  

     |Number of shares| 

     |101| 

     Then I should receive an error message there’s a restriction to remove the recipe over hundred with: 

     |Number of shares | 

     |101|  

And I should receive the message to email the admin.  

 

Scenario: Remove a recipe which logged in user isn't the creator of the recipe. 

I should not be able to remove a recipe and get reported to the admin for suspicious behavior. 
    Given I am connected to FreshFork with a valid account. 
    When I request a recipe I am not the creator of 
    Then I should receive error message. 