Feature: List Recipes

As a User
I would like to list all recipes
So that I can view recipes from my diet

Scenario: List recipes from diet (Normal Flow)

    Given I am a User logged into the FreshFork system
    And I have registered diets on my account
    When I request to list all recipes
    Then the system will show all recipes from my diet.

Scenario: Unregistered user attempts to list recipes (Error Flow)

    Given I am an unregistered user of FreshFork system
    When I request to list all recipes
    Then the system will randomly select recipes
    And list all random recipes.