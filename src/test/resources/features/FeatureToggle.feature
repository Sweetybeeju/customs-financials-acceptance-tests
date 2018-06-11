@qa @acceptance
Feature: Testing disable feature toggle

  Scenario: Disable Duty deferment using feature toggle
    Given the duty deferment feature is disabled
    When I am on the Duty deferment page
    Then page not found is returned




