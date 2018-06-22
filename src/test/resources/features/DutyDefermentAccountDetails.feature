@wip @CDSP-2031
  Feature: Duty Deferment Account Details

    Scenario Outline: Navigating to Account Details Page
      Given I have a duty deferment account with DAN '<dan>'
      When I navigate to the duty deferment account details page for DAN '<dan>'
      Then I should see the account summary for DAN '<dan>'
      And the statements display in descending chronological order
      Examples:
        |dan    |
        |1234567|


