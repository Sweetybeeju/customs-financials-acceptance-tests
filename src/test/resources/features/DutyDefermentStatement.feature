@wip @qa
Feature: Duty Deferment Statements

  Scenario: File can be downloaded
    Given I am on the Duty deferment page
    When I select the following statement to download
      | June |
    Then I am able to access the pdf file that was downloaded

  Scenario: Link text should be the same as the filename
    Given I am on the Duty deferment page
    When I select the following statement to download
      | April |
    Then the link text should be the same as the filename


  Scenario: Understand the size of each PDF
    Given I am on the Duty deferment page
    When I select the following statement to download
      | April |
    Then I am able to understand the size of each PDF







