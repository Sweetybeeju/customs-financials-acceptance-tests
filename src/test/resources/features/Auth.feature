@wip @acceptance

Feature: Authenticating users

Scenario: User accesing dashboard page through Auth
Given I am on the Auth login stub page
When I enter the valid user details on the auth page
And click submit
Then I should be on the Dashboard page

