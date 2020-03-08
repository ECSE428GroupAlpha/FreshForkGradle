Feature: Change User Password

As a user with an ongoing membership with freshfork I would like to be able to change my password so I can protect my account. 

Scenario: User changes password(Normal Flow)

	Given I am logged into FreshFork with a valid account
	When I request to change my account password with the correct current password
	Then the system should change the password to the new password
	

Scenario: User attempts to change password with incorrect current password (Error flow)

	Given I am logged into FreshFork with a valid account
	When I request to change my account password with the incorrect current password
	Then the system should return an error message "incorrect current password"

Scenario: User attempts to change password with incorrect email (Error Flow)

	Given I am logged into FreshFork with a valid account
	When I request to change my account password with incorrect email
	Then the system should return an error message "incorrect email"


