
# Customs Financials Acceptance Tests

This project provides a number of test suites that are intended to prove
[Customs Financials Frontend](https://github.com/hmrc/customs-financials-frontend) and its
interaction with integration points, such as the [Customs Financials API](https://github.com/hmrc/customs-financials-api).

## Test Suites

There are currently 2 distinct test suites defined: "Acceptance" and "End-to-end".
A package-based convention is utilised to determine which suite a spec belongs to. All
"acceptance" tests should be placed in the `uk.gov.hmrc.acceptance` package (or sub-packages) and all 
"end-to-end" tests should be placed in the `uk.gov.hmrc.endtoend` package (or sub-packages).

### Acceptance Tests

Acceptance tests are intended to prove application behaviour in terms of business
features and requirements. External integration points (such as HoDs) should be
stubbed. The aim is to provide confidence to the business stakeholder that required
functionality is present and working.

### End-to-End Tests

End-to-end tests are intended to prove a small number of key journeys within a
representative deployment environment (e.g. QA) against real integration points.
The aim, therefore, is to provide confidence to the development team that deployment
will work as expected.

## Test Profiles

Both acceptance and end-to-end test suites support the concept of a "profile" which
aims to encapsulate variable execution properties, such as the user's browser type
and the endpoint of the application under test. These properties are defined in 
`uk.gov.hmrc.drivers.Config`.

If you want or need to change test execution properties for any purpose, new profiles
can be defined via the `src/test/resources/application.conf` config file.

## Running the Tests

There are a number of bash scripts located in the root of the project to help you run
the tests correctly in specific environments (e.g. locally or on jenkins). However,
they are very straightforward and simple. Basically, you just need to specify the
_suite_ to run and the _profile_ to use. For example:

```
> sm --start CUSTOMS_FINANCIALS_FRONTEND -f
> sbt -Dtest.profile=local acceptance:test
```

This will execute the "acceptance" test suite using the "local" profile. Whereas:

```
> sm --start CUSTOMS_FINANCIALS_FRONTEND -f
> sbt -Dtest.profile=local-mac endtoend:test
```

Will run the "end-to-end" test suite using the "local-mac" profile.
    