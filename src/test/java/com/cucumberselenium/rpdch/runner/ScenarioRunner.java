package com.cucumberselenium.rpdch.runner;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        tags = {"@sampleSearch, @sampleLogin"},
        features = {"src/test/resources/features"},
        glue = {"com.cucumberselenium.rpdch.stepdefinitions"}, // package of stepdefinition classes
//        dryRun = true, // checks that every Step mentioned in the Feature File have corresponding code written in Step Definition file or not
        monochrome = true, // console output for the Cucumber test are much more readable
        plugin = {
                "pretty",
                "html:target/testresults/html", // test results as html
                "json:target/testresults/cucumber.json", // test results as json
                "junit:target/testresults/cucumber.xml" // test results as xml
        }
)
public class ScenarioRunner {
}
