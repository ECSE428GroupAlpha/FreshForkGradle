Feature: Add a User

AS a person with an email
I would like to become a user of FreshFork
So that I can use the Freshwork application

Scenario outline: Different types of users (Normal Flow)

 Given email <email>
  And first name <fname>
  And last name <lname>
  And username <uname>
  And password <pword>
  And account type <acc_type>
 When email <email> requests to create a new account
 Then a new user is created with user id <user_id>


| email          | fname | lname   | uname   | password | acc_type | user_id |
| sam@smith.com  | Sam   | Smith   | ssmith  | gsyn123  | Chef     | SS0001  |
| fred@flin.com  | Fred  | Flin    | fflin   | sylo!24  | Trainer  | FF0001  | 
| Tom@riddle.com | Tom   | Riddle  | triddle | vold%32  | client   | TR0001  |

Scenario outline: Existing user attempts to become a user (Error Flow)

 Given dwight@schrute.com is a registered email of an existing user of FreshFork
 When dwight@schrute.com requests to create a new account
 Then a "Already registered" message is issued
