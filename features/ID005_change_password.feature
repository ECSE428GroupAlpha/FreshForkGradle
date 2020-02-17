Feature: Change User Password

As a user with an ongoing membership with freshfork I would like to be able to change my password so I can protect my account. 

Scenario: Change user password to something that does not meet the minimum password requirements

	I should get confirmation that my password was not changed. 

	Given I am logged into FreshFork with a valid account
	When I request to change my account password
	Then the system should prompt me to enter a new password.
	When I enter a password that does not meet the password requirements
	Then the system will display which requirements I did not meet
	And I should get a message indicating that my pasword was not changed.

Scenario: Change user password to something that meets the minimum password requirements

	I should get confirmation that my password was changed.

	Given I am logged into FreshFork with a valid account
	When I request to change my account password
	Then the system should prompt me to enter a new password.
	When I enter a password that meets the password requirements
	Then my password will be changed to the new password
	And I should recieve a message indicating that my password was successfuly changed. 

Scenario: Change user password to current password

	I should get confirmation that my password was not changed.

	Given I am logged into FreshFork with a valid account
	When I request to change my account password
	Then the system should prompt me to enter a new password.
	When I enter the same password as my current password
	Then the system will display an error message
	And I should recieve a message indicating that my password was not changed.


