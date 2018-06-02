
Feature: Cds Landing Page

Scenario: Check CDS landing page title

Given i am on the Cds landing page
Then the page title should be "hello outside world from customs-financials-frontend !"



@qa

Scenario: File can be downloaded cds

  Given i am on the Duty deferment page
  When i select the following statement to download
  |May|
  Then i am able to access the pdf file that was downlaoded



