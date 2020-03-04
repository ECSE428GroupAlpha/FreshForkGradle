Feature: Add a User

  As a person with an email
  I would like to become a user of FreshFork
  So that I can use the Freshwork application

  Scenario Outline: Different types of users (Normal Flow)

    Given email <email>
    And first name <fname>
    And last name <lname>
    And username <uname>
    And password <pword>
    And account type <acc_type>
    When email <email> requests to create a new account
    Then a new user is created

    Examples:
      | email          | fname | lname  | uname   | password | acc_type |
      | sam@smith.com  | Sam   | Smith  | ssmith  | gsyn123  | Chef     |
      | fred@flin.com  | Fred  | Flin   | fflin   | sylo!24  | Trainer  |
      | Tom@riddle.com | Tom   | Riddle | triddle | vold%32  | client   |

  Scenario: Existing user attempts to become a user (Error Flow)

    Given dwight@schrute.com is a registered email of an existing user of FreshFork
    When dwight@schrute.com requests to create a new account
    Then a "Email is already taken." message is issued
