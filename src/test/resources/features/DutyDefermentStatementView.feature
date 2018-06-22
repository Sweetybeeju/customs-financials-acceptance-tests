@wip
Feature: Duty Deferment Statement View Page

  Background:
    Given the Duty Deferment feature is enabled
    And I navigate to the Duty Deferment Statement View page
    And I am redirected to the Sign In page
    And I sign in as a registered user

  # TODO separate scenario for not-signed-in redirect

  Scenario: Page title and period narrative
    Given I have at least one Statement available to view
    When I navigate to the Duty Deferment Statement View page
#    Then the page title should be X (check window title & h1 element)
    # TODO explicitly state expected period narrative in feature file
    Then I am able to see the period in which the Statement was issued

  Scenario: The total liabilities for the period in which the statement was issued
    Given I have at least one Statement available to view
    When I navigate to the Duty Deferment Statement View page
    Then I am able to see the total liabilities for the period in which the Statement was issued
      | Duty Due | VAT Due  | Excise Due |
      | £2000.00 | £3000.00 | £1000.00   |

  Scenario: The total liabilities for the month in which the statement was issued
    Given I have at least one Statement available to view
    When I navigate to the Duty Deferment Statement View page
    Then I am able to see the total liabilities for the month in which the Statement was issued
      | Duty Due | VAT Due  | Excise Due |
      | £6000.00 | £9001.00 | £3333.00   |

  # TODO alter stub data so that transactions have different dates and are returned in wrong order
  Scenario:The list of transactions, ordered by accounting date ASCENDING
    Given I have at least one Statement available to view
    When I navigate to the Duty Deferment Statement View page
    Then I can see the following transactions
      | Accounting Date | Movement Reference Number | Agent's EORI   | Agent's Reference | Duty Due    | VAT Due     | Excise Due  |
      | 2018-02-20	    | 	    12345678ABC         |  EORI123456    |     AGENT98765    | 	 £1234.56  | 	£123.45  |  £12.34     |
      | 2018-02-20	    | 	    3456789DEF          |  EORI234567    |     AGENT007      |£1020304.56  |  £10203.45  |  £102.34    |
