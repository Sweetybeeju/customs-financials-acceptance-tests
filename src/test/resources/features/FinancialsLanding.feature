@wip
Feature: Financials Landing Page
  As a user
  I want to see my financial accounts
  So that i can access these accounts individually

  Background:
    Given I try to navigate to the Financials Landing page
    When I am redirected to the Sign In page
    Then I sign in as valid user

  Scenario Outline:
    Given I am on the Financials Landing page
    Then I can see the Financials Landing page
    Then I should see the duty deferment account with DAN '<dan>' listed in the accounts
    Examples:
      |dan        |
      |09476355377|
