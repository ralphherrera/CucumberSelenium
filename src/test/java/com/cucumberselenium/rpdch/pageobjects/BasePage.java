package com.cucumberselenium.rpdch.pageobjects;

import com.cucumberselenium.rpdch.utilities.WebDriverWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

abstract class BasePage {

    private static final Logger logger = LogManager.getLogger(BasePage.class);

    protected WebDriver driver;
    protected WebDriverWrapper driverWrapper;

    public BasePage() {

    }

    public void setDriver(WebDriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;
        this.driver = driverWrapper.getDriver();
        PageFactory.initElements(driver, this);
    }

    abstract WebElement returnPageElement(String key);
    abstract List<WebElement> returnPageElementList(String key);

    public void clickElement(String element) {
        driverWrapper.clickElement(returnPageElement(element));
    }

    public void jsClickElement(String element) {
        driverWrapper.jsClickElement(returnPageElement(element));
    }

    public void clickElementFromList(String elements, String key) {
        driverWrapper.clickElementFromList(returnPageElementList(elements), key);
    }

    public void inputTextToField(String element, String input) {
        driverWrapper.enterTextToField(returnPageElement(element), input);
    }

    public boolean isElementDisplayed(String element) {
        return driverWrapper.isElementPresent(returnPageElement(element));
    }
}
