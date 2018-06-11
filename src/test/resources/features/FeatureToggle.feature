@qa @acceptance @wip
Feature: Testing feature toggle
# The feature must be enabled before any of the tests are run
  Scenario: Disable Duty deferment using feature toggle
    Given the duty deferment feature is disabled
    When I am on the Duty deferment page
    Then page not found is returned

  Scenario: Enable DUty deferment using feature toggle
    Given the duty deferment feature is enabled
    When I am on the Duty deferment page
    Then the page title should be "Duty Deferment Statements"




