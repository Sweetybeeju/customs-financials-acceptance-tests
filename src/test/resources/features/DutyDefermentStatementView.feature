@wip
Feature: Duty Deferment Statement View Page

  Background:
    Given the Duty Deferment feature is enabled
    And I have at least one Statement available to view

  Scenario: The total liabilities the month in which the statement was issued
    Given I am not signed in
    When I navigate to the Duty Deferment Statement View page
    And I am redirected to the Sign In page
    And I sign in as a registered user
    Then I am able to see the period in which the Statement was issued
    And I am able to see the total liabilities for the period in which the Statement was issued
    And I am able to see the total liabilities for the month in which the Statement was issued

  Scenario:The list of transactions, ordered by accounting date ASCENDING
    When I navigate to the Duty Deferment Statement View page
    Then I can see the following transactions
      | Accounting Date | Movement Reference Number | Agent's EORI   | Agent's Reference | Duty Due    | VAT Due     | Excise Due  |
      | 2018-02-20	    | 	    12345678ABC         |  EORI123456    |     AGENT98765    | 	 £1234.56  | 	£123.45  |  £12.34     |
      | 2018-02-20	    | 	    3456789DEF          |  EORI234567    |     AGENT007      |£1020304.56  |  £10203.45  |  £102.34    |
