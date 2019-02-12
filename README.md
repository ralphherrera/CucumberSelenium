### BDD Cucumber-Selenium Automated Feature Tests

------------
###### *using Selenium WebDriver, Cucumber and JUnit*
##### Prerequisites
- Java 1.8 or better
- Maven 3.1 or better

##### Setup
- Read the sample.feature file for instructions on how to run a basic web and api test
```gherkin
Feature: Sample
  As a BDDCucumberJVM user
  I want an example of a good feature
  so that I can write good features of my own 

  Scenario: Simple Acceptance Test
    Given I setup the BDDCucumberJVM project
    When I configure the run-config.properties with my execution details
    And I run the tests using "mvn clean install -Dtags=~@sample"
    Then the "websearch" feature should run and pass
    """
    NOTE:
    ===============
    This Sample feature has not been implemented.
    It's here to help users get started.
    """
```

##### Command-Line Options
\-Dapp.name=MyAppName
\-Dtags=@MyTag1,@MyTag2,@MyTag3,~@ExcludeMyTag4
##### Example:
```sh
mvn clean install -Dapp.name=CucumberSelenium -Dtags=@sampleFeature,@sampleLogin,~@sampleServiceSearch
```
##### Supported Browsers
- Google Chrome
- Internet Explorer
- Mozilla Firefox

##### Report Screenshots (Extent reports and Klov)
- ###### Extent Reports
 - ######Passed Run
 
[![Passed Run Dashboard](https://imgur.com/MukYgZd.png "Passed Run Dashboard")](https://imgur.com/MukYgZd "Passed Run Dashboard")
[![Sample Run Screenshot](https://imgur.com/YDFiuVj.png "Sample Run Screenshot")](https://imgur.com/YDFiuVj.png "Sample Run Screenshot")

  - ######Failed Run
  
[![Failed Run Dashboard](https://imgur.com/CO05vS1.png "Failed Run Dashboard")](https://imgur.com/CO05vS1 "Failed Run Dashboard")
[![Failed Run Screenshot](https://imgur.com/HX7HF43.png "Failed Run Screenshot")](https://imgur.com/HX7HF43 "Failed Run Screenshot")

- ###### Klov Extent Report

 - ###### Dashboard
 
 
 [![Klov Dashboard](https://imgur.com/HQOmqsR.png "Klov Dashboard")](https://imgur.com/HQOmqsR "Klov Dashboard")

 - ###### Builds
 
 
 [![Klov Builds](https://imgur.com/aKqw8tx.png "Klov Builds")](https://imgur.com/aKqw8tx "Klov Builds")

 - ###### Builds
 
 
 [![Klov Feature Run](https://imgur.com/sLGEEff.png "Klov Feature Run")](https://imgur.com/sLGEEff "Klov Feature Run")
 
##### References
- [Cucumber](https://docs.cucumber.io/ "Cucumber") - An open-source tool for executable specifications (Java implementation)
- [Cucumber Documentation](https://docs.cucumber.io/guides/ "Cucumber Documentation") - General reference for all Cucumber implementations
- [Selenium](http://www.seleniumhq.org/ "Selenium") - An open-source browser automation tool
- [Selenium Documentation](http://www.seleniumhq.org/docs/ "Selenium Documentation") - General reference for Selenium
- [JUnit](http://junit.org/junit4/ "JUnit") - A simple, open-source framework to write repeatable unit tests
- [Cucumber Extent Reporter](https://github.com/email2vimalraj/CucumberExtentReporter "Cucumber Extent Reporter") - Generates HTML report for Cucumber Tests using ExtentReports plugin
- [Klov](http://extentreports.com/docs/klov/ "Klov") - Report Server for the Extent Framework
- [Cucumber-JVM-Parallel-Plugin](https://github.com/temyers/cucumber-jvm-parallel-plugin "Cucumber-JVM-Parallel-Plugin") - This plugin automatically generates a Cucumber JUnit or TestNG runner for each scenario/feature file found in the project