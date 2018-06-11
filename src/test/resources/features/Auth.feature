@acceptance @qa
Feature: Authenticating users

  Scenario: user accessing secured page
    Given I am not signed in
    When I attempt to navigate to the Dashboard page
    Then I am redirected to the Sign In page
    When I sign in as valid user
    Then I am redirected to the Dashboard page
