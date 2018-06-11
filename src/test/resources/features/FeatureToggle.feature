@qa @acceptance
Feature: Testing feature disable endpoint

  Background:
    Given the duty deferment feature is disabled

  Scenario:
    Given I am on the Duty deferment page
    Then page not found is returned
