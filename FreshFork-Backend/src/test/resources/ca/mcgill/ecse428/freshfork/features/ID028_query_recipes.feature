Feature: Query Recipe

As a user with ongoing membership on FreshFork
I would like to receive a list of recipes
So that I can choose recipes for my diet plan

Scenario: User With Membership Requests List of Recipes (Normal Flow)

Given user "Edward" is logged into FreshFork
And the following users have are registered on FreshFork: 
| name      | user_id | is_member   | user_name | password |
| Edward    | 1       | TRUE        |edward     | 1        |
| Douglas   | 2       | TRUE        |douglas    | 2        |
| Kim       | 3       | FALSE       |kim        | 3        |
| Anthony   | 4       | FALSE       |anthony    | 4        |
| Sean      | 5       | FALSE       |sean       | 5        |
| Ivraj     | 6       | TRUE        |ivraj      | 6        |
And the following recipes have been uploaded onto FreshFork:
| recipe_name        | recipe_id | tag       |
| Caponata pasta     | 1         | vegan     |
| Vegeterian fajitas | 2         | vegan     |
| Dhansak            | 3         | keto      |
| Gnocchi            | 4         | keto      |


When user "Edward" requests a list of recipes
Then the following list of users is generated:
| recipe_name        | recipe_id | tag       |
| Caponata pasta     | 1         | vegan     |
| Vegeterian fajitas | 2         | vegan     |
| Dhansak            | 3         | keto      |
| Gnocchi            | 4         | keto      |


Scenario: User With Membership Requests List of Recipes By Tag (Alternate Flow)

Given user "Edward" is logged into FreshFork
And the following users are registered on FreshFork: 
| name      | user_id | is_member   | user_name | password |
| Edward    | 1       | TRUE        |edward     | 1        |
| Douglas   | 2       | TRUE        |douglas    | 2        |
| Kim       | 3       | FALSE       |kim        | 3        |
| Anthony   | 4       | FALSE       |anthony    | 4        |
| Sean      | 5       | FALSE       |sean       | 5        |
| Ivraj     | 6       | TRUE        |ivraj      | 6        |
And the following recipes have been uploaded onto FreshFork:
| recipe_name        | recipe_id | tag       |
| Caponata pasta     | 1         | vegan     |
| Vegeterian fajitas | 2         | vegan     |
| Dhansak            | 3         | keto      |
| Gnocchi            | 4         | keto      |

When user "Edward" requests a list of recipes with the tag "vegan"
Then the following list of recipes is generated:
| recipe_name        | recipe_id | tag       |
| Caponata pasta     | 1         | vegan     |
| Vegeterian fajitas | 2         | vegan     |


Scenario: User Without Membership Requests List Of Recipes (Error Flow)

Given user "Anthony" is logged into FreshFork
And the following users are registed on FreshFork: 
| name      | user_id | is_member   | user_name | password |
| Edward    | 1       | TRUE        |edward     | 1        |
| Douglas   | 2       | TRUE        |douglas    | 2        |
| Kim       | 3       | FALSE       |kim        | 3        |
| Anthony   | 4       | FALSE       |anthony    | 4        |
| Sean      | 5       | FALSE       |sean       | 5        |
| Ivraj     | 6       | TRUE        |ivraj      | 6        |
When user "Anthony" requests a list of users
Then a message asking the user to sign up for a membership is generated