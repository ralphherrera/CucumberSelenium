package com.cucumberselenium.rpdch.runner;


import com.vimalselvam.cucumber.listener.ExtentProperties;
import com.vimalselvam.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.io.File;

@RunWith(Cucumber.class)
@CucumberOptions(
        tags = {"@sampleLogin,@sampleSearch"},
        features = {"src/test/resources/features"},
        glue = {"com.cucumberselenium.rpdch.stepdefinitions"}, // package of stepdefinition classes
//        dryRun = true, // checks that every Step mentioned in the Feature File have corresponding code written in Step Definition file or not
        monochrome = true, // console output for the Cucumber test are much more readable
        plugin = {
//                "pretty",
                "html:target/testresults/html", // test results as html
                "json:target/testresults/cucumber.json", // test results as json
                "junit:target/testresults/cucumber.xml", // test results as xml
                "com.vimalselvam.cucumber.listener.ExtentCucumberFormatter:" // extent reporter
        }
)
public class ScenarioRunner {

    @BeforeClass
    public static void setup() {
        ExtentProperties extentProperties = ExtentProperties.INSTANCE;
        extentProperties.setReportPath("reports/myreport.html");
//        extentProperties.setKlovServerUrl("http://localhost");

        // specify project

        // ! you must specify a project, other a "Default project will be used"
        extentProperties.setKlovProjectName("Cucumber Selenium");
        // you must specify a reportName otherwise a default timestamp will be used

        extentProperties.setKlovReportName("");
        // Mongo DB Configuration
        extentProperties.setMongodbHost("localhost");
        extentProperties.setMongodbPort(27017);
        extentProperties.setMongodbDatabase("klov");

        // If mongo Db is running in Authentication mode provide username and password
//        extentProperties.setMongodbUsername("username");
//        extentProperties.setMongodbPassword("password");
    }

    @AfterClass
    public static void writeExtentReport() {
        Reporter.loadXMLConfig(new File("src/test/resources/extent-config.xml"));
    }

}
