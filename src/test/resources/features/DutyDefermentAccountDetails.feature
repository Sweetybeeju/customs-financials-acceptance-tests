@acceptance @CDSP-2031
  Feature: Duty Deferment Account Details
    Background:
      Given I am signed in as a registered user

    Scenario Outline: Navigating to Account Details Page
      Given I have a duty deferment account with DAN '<dan>'
      When I navigate to the duty deferment account details page for DAN '<dan>'
      Then I should see the account summary for DAN '<dan>'
      Examples:
        |dan    |
        |1234567|
