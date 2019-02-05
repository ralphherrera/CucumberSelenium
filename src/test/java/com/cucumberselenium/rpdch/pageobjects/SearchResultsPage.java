package com.cucumberselenium.rpdch.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResultsPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(SearchResultsPage.class);

    // Page Elements
    @FindBy(css = "table[id='load_data'] > tbody > tr")
    private List<WebElement> lvw_FlightsSearchResultsList;

    @FindBy(css = "table[id='load_data'] > tbody > tr:nth-of-type(1)")
    private WebElement tbr_FlightsFirstRowSearchResult;

    @FindBy(xpath = "//ul[@class='pagination']//li//a[contains(text(), '>')]")
    private WebElement btn_SearchResultsNextPage;

    @Override
    WebElement returnPageElement(String key) {
        logger.traceEntry("Getting page element [{}]", key);
        Map<String, WebElement> elementMap = new HashMap<>();

        elementMap.put("Next Page", btn_SearchResultsNextPage);
        elementMap.put("First Row", tbr_FlightsFirstRowSearchResult);

        if (!elementMap.containsKey(key)) {
            logger.warn("No Page Element found with key {}", key);
            Assert.fail();
        }
        return logger.traceExit(elementMap.get(key));
    }

    @Override
    List<WebElement> returnPageElementList(String key) {
        logger.traceEntry("Getting page element [{}]", key);

        Map<String, List<WebElement>> elementListMap = new HashMap<>();

        elementListMap.put("Search Results", lvw_FlightsSearchResultsList);

        if (!elementListMap.containsKey(key)) {
            logger.warn("No Page Element found with key {}", key);
            Assert.fail();
        }
        return logger.traceExit(elementListMap.get(key));
    }

    public boolean areFlightsDisplayedInSearchResults() {
        logger.traceEntry();
        int searchResultCount = 0;
        boolean areFlightsDisplayed = false;
        boolean isPaginationDisplayed = true;
        while (isPaginationDisplayed) {
            if (driverWrapper.areElementsPresent(lvw_FlightsSearchResultsList)) {
                for (WebElement element : lvw_FlightsSearchResultsList) {
                    driverWrapper.scrollIntoView(element);
                    searchResultCount++;
                }
            }
            if (driverWrapper.isElementPresent(btn_SearchResultsNextPage)) {
                driverWrapper.clickElement(btn_SearchResultsNextPage);
            } else {
                areFlightsDisplayed = true;
                isPaginationDisplayed = false;
            }
        }
        logger.info("Total flights displayed [{}]", searchResultCount);
        return logger.traceExit(areFlightsDisplayed);
    }
}