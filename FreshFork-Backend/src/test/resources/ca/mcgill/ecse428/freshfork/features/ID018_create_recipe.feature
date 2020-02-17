Feature: Create_Recipe

    As a FreshFork professional user, I would like to create a recipe

    Scenario: Professional attempts to create a recipe (normal flow)

        Given I am an existing user of type Professional
        When I provide a <recipe_name>, <recipe_instructions>, <ingredients> and <calories>
        Then a new recipe with a randomnly generated <recipe_id> should be created
        And the recipe should be saved to the database

            | recipe_id | recipe_name | recipe_instructions  | ingredients  | calories |
            | 131423424 | Apple Sauce | Stir the ingredients | Apple, Water | 100      |


    Scenario: Professional attempts to create a recipe without calories (alternative flow)
        Given I am an existing user of type Professional
        When I provide a <recipe_name>, <recipe_instructions> and <ingredients>
        Then a new recipe with a randomnly generated <recipe_id> should be created with a default <calories> value of 0
        And the recipe should be saved to the database

            | recipe_id | recipe_name | recipe_instructions  | ingredients  | calories |
            | 131423424 | Apple Sauce | Stir the ingredients | Apple, Water | 0        |

    Scenario: Professional attempts to create a recipe with invalid values (error flow)
        Given I am an existing user of type Professional
        When I provide a <recipe_name>, <recipe_instructions> and <ingredients>
        And a negative <calories> value
        Then an "Invalid input for calories amount" message is issued

    Scenario: Non-professional attempts to create a recipe (errow flow)

        Given I am an existing user not of type Professional
        When I provide a <recipe_name>, <recipe_instructions> and <ingredients>
        Then an "Unauthorized request" message is issued
