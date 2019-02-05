package com.cucumberselenium.rpdch.stepdefinitions;

import com.cucumberselenium.rpdch.pageobjects.AccountsPage;
import com.cucumberselenium.rpdch.pageobjects.HomePage;
import com.cucumberselenium.rpdch.pageobjects.LoginPage;
import com.cucumberselenium.rpdch.utilities.FileMgmtUtil;
import cucumber.api.java8.En;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import static org.hamcrest.core.Is.is;

public class SampleAccountManagementStepDefinition implements En {

    private static final Logger logger = LogManager.getLogger(SampleAccountManagementStepDefinition.class);

    public SampleAccountManagementStepDefinition(ScenarioHooks hooks, HomePage homePage, LoginPage loginPage,
                                                 AccountsPage accountsPage) {

        Given("^I access the phptravels site$", () -> {
            homePage.setDriver(hooks.getDriverWrapper());
            homePage.openPage(FileMgmtUtil.getPropertyValue("website.url"));
        });

        And("^I navigate to the login page$", () -> {
            homePage.clickElement("My Account");
            homePage.clickElementFromList("My Account", "Login");
        });

        When("^I login using my account credentials$", () -> {
            loginPage.setDriver(hooks.getDriverWrapper());
            loginPage.inputTextToField("Email Field", "user@phptravels.com");
            loginPage.inputTextToField("Password Field", "a");
            loginPage.clickElement("Login Button");
        });

        Then("^I should be navigated to the accounts page$", () -> {
            accountsPage.setDriver(hooks.getDriverWrapper());
            Assert.assertThat("Verify if user is navigated to the accounts page", accountsPage.isElementDisplayed("Name Header"),
                    is(true));
        });

    }

}