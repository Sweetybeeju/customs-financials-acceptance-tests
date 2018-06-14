@qa @acceptance @wip

Feature: Cds finance page
  As a user
  I want to see my financial accounts
  So that i can access these accounts individually

  Background:
    Given the Finance feature is enabled

  Scenario: Check duty deferment accounts on the finance page

    Given I am on the Finance page
    Then I should see my following Duty Deferment Accounts
      | DAN Account number |
      | DAN1234567890      |
      | DAN1234567890      |
      | DAN1234567892      |


