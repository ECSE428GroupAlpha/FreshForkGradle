Feature: Add a User

As a person with an email
I would like to become a user of FreshFork
So that I can use the Freshwork application

Scenario outline: Different types of users (Normal Flow)

 Given I have email <email>
  And I have first name <fname>
  And I have last name <lname>
  And I have username <uname>
  And I have password <pword>
  And I have account type <acc_type>
 When I have email <email> requests to create a new account
 Then a new user is created with user id <user_id>


| email          | fname | lname   | uname   | password | acc_type | user_id |
| sam@smith.com  | Sam   | Smith   | ssmith  | gsyn123  | Chef     | SS0001  |
| fred@flin.com  | Fred  | Flin    | fflin   | sylo!24  | Trainer  | FF0001  | 
| Tom@riddle.com | Tom   | Riddle  | triddle | vold%32  | client   | TR0001  |

Scenario outline: Existing user attempts to become a user (Error Flow)

 Given dwight@schrute.com is a registered email of an existing user of FreshFork
 When dwight@schrute.com requests to create a new account
 Then a "Already registered" message is issued
