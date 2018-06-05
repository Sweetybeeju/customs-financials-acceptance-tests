@qa
Feature: Duty Deferment Statements

  Scenario Outline: File can be downloaded
    Given I am on the Duty deferment page
    When I select the month '<month>' statement to download
    Then I am able to access the pdf file that was downloaded
    Examples:
      | month |
      | April |
      | June  |

  Scenario Outline: Link text should be the same as the filename
    Given I am on the Duty deferment page
    When I select the month '<month>' statement to download
    Then the link text for period '<month>' should be the same as the filename
    Examples:
      | month |
      | April |
      | May   |

  Scenario Outline: Understand the size of each PDF
    Given I am on the Duty deferment page
    When I select the month '<month>' statement to download
    Then I am able to understand the size of the PDF for '<month>'
    Examples:
      | month |
      | May |
