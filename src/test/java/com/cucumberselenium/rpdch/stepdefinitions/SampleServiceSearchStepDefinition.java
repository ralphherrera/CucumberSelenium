package com.cucumberselenium.rpdch.stepdefinitions;

import com.cucumberselenium.rpdch.pageobjects.HomePage;
import com.cucumberselenium.rpdch.pageobjects.SearchResultsPage;
import cucumber.api.java8.En;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import static org.hamcrest.core.Is.is;

public class SampleServiceSearchStepDefinition implements En {

    private static final Logger logger = LogManager.getLogger(SampleServiceSearchStepDefinition.class);

    public SampleServiceSearchStepDefinition(ScenarioHooks hooks, HomePage homePage, SearchResultsPage searchResultsPage) {

        When("^I search for '(.*)'$", (String service) -> {
            homePage.clickElementFromList("Service", "FLIGHTS");
        });

        And("^I entered dates from '(.*)' to '(.*)'$", (String departureDate, String returnDate) -> {
            homePage.inputTextToField("From Field", "Ninoy");
            homePage.waitForTextToChange("From Field Auto Complete First Row", "Ninoy");
            homePage.clickElementFromList("From Field Auto Complete List", "Ninoy");

            homePage.clickElement("Round Trip");
            homePage.inputTextToField("Departure Date", departureDate);
            homePage.inputTextToField("Return Date", returnDate);

            homePage.clickElement("Flights Submit Button");
        });

        Then("^I should see relevant search results displayed$", () -> {
            searchResultsPage.setDriver(hooks.getDriverWrapper());
            Assert.assertThat("Verify search results are displayed", searchResultsPage.areFlightsDisplayedInSearchResults(),
                    is(true));
        });
    }
}