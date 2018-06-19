@wip
Feature: Duty deferment  Statement view Page

  Background:
    Given the Duty deferment feature is enabled
    And I have at least one Statement available to view

  Scenario :The total liabilities the month in which the statement was issued
    When I navigate to the Duty deferment statement view page
    Then Iam able to see the total liabilities the month in which the Statement was issued
    And Iam able to see the period in which the Statement was issued. 


  Scenario Outline:The list of transactions, ordered by accounting date ASCENDING
    When I navigate to Duty deferment statement view page
    Then I can see following data
    Examples:
      | Accounting Date | Movement reference number | Agent's EORI   | Agent's reference | Duty due  | VAT due  | Excise due  |
      | 2018-02-20	    | 	    12345678ABC         |  EORI123456    |     AGENT98765    | 	 1234.56 | 	 123.45 |  	 12.34    |
      | 2018-02-20	    | 	    3456789DEF          |  EORI234567    |     AGENT007      |1020304.56 | 10203.45 |  	102.34    |
