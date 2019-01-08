package com.cucumberselenium.rpdch.stepdefinitions;

import com.cucumberselenium.rpdch.utilities.FileMgmtUtil;
import cucumber.api.java8.En;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class SampleAccountManagementStepDefinition implements En {

    private static final Logger logger = LogManager.getLogger(SampleAccountManagementStepDefinition.class);

    public SampleAccountManagementStepDefinition() {

        Given("^I access the phptravels site$", () -> {
            logger.info("GIVEN");
        });

        And("^I navigate to the login page$", () -> {
            logger.warn("AND");
        });

        When("^I login using my account credentials$", () -> {
            logger.error("WHEN");
        });

        Then("^I should be navigated to the accounts page$", () -> {
            logger.fatal("THEN");
        });

    }

}
