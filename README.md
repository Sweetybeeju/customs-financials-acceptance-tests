
# Customs Financials Acceptance Tests

This project provides a number of test suites that are intended to prove
[Customs Financials Frontend](https://github.com/hmrc/customs-financials-frontend) and its
interaction with integration points, such as the [Customs Financials API](https://github.com/hmrc/customs-financials-api).

## Running the Tests

There are a number of bash scripts located in the root of the project to help you run
the tests correctly in specific environments (e.g. locally or on jenkins). However,
they are very straightforward and simple. Basically, you just need to specify the
_environment_ and _runner_ class to use. For example:

```
> sm --start CUSTOMS_FINANCIALS_ALL -f
> sbt -Denvironment=local 'test-only uk.gov.hmrc.runner.RunWip'
```

This will execute all the features form the RunWip class on local environment. Whereas;
 
```
> sm --start CUSTOMS_FINANCIALS_ALL -f
> > sbt -Denvironment=qa 'test-only uk.gov.hmrc.runner.RunQa'

```

Will execute all the features form the RunQa class on qa environment.
    