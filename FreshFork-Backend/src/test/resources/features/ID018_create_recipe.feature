Feature: Create_Recipe

    As a FreshFork professional user, I would like to create a recipe

    Scenario Outline: Professional attempts to create a recipe (normal flow)

        Given I am an existing user of type Professional
        When I provide a <recipe_name>, <recipe_instructions>, <rating>
        Then a new recipe with a randomnly generated <recipe_id> should be created
        And the recipe should be saved to the database

        Examples:
            | recipe_id | recipe_name | recipe_instructions  | rating |
            | 131423424 | Apple Sauce | Stir the ingredients | 5      |


    Scenario Outline: Professional attempts to create a recipe without rating (alternative flow)
        Given I am an existing user of type Professional
        When I provide a <recipe_name>, <recipe_instructions> and <ingredients>
        Then a new recipe with a randomnly generated <recipe_id> should be created with a default <rating> value of 0
        And the recipe should be saved to the database

        Examples:
            | recipe_id | recipe_name | recipe_instructions  | rating |
            | 131423424 | Apple Sauce | Stir the ingredients | 5      |

    Scenario Outline: : Professional attempts to create a recipe with invalid values (error flow)
        Given I am an existing user of type Professional
        When I provide a <recipe_name>, <recipe_instructions>, <ingredients> and negative <rating>
        Then an "Invalid input for rating" message is issued

        Examples:
            | recipe_id | recipe_name | recipe_instructions  | rating |
            | 131423424 | Apple Sauce | Stir the ingredients | 5      |

    Scenario Outline: : Non-professional attempts to create a recipe (errow flow)

        Given I am an existing user not of type Professional
        When I provide a <recipe_name>, <recipe_instructions> and <ingredients>
        Then an "Unauthorized request" message is issued

        Examples:
            | recipe_id | recipe_name | recipe_instructions  | rating |
            | 131423424 | Apple Sauce | Stir the ingredients | 5      |
