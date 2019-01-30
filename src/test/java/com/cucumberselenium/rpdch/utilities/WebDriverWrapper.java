package com.cucumberselenium.rpdch.utilities;

import com.cucumberselenium.rpdch.constants.CommonConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class WebDriverWrapper {

    private static final Logger logger = LogManager.getLogger(WebDriverWrapper.class);
    private static final int ZERO_TIMEOUT = 0;

    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWrapper(WebDriver driver) {
        this.driver = driver;
    }

    /***
     *
     * @param url
     */
    public void navigateToPage(String url) {
        logger.info("Opening site: [{}]", url);
        try {
            driver.get(url);
            waitForPageToLoad();
        } catch (Exception e) {
            logger.error("Something went wrong ", e.getMessage());
            Assert.fail();
        }
    }

    /***
     * Checks the Page Element in the DOM if it is present
     * @param element
     * @return True if element is present | False if element is not displayed in the DOM
     */
    public boolean isElementPresent(WebElement element) {
        WebElement target;
        boolean isElementDisplayed = false;
        try {
            driver.manage().timeouts().implicitlyWait(ZERO_TIMEOUT, TimeUnit.SECONDS);
            WebDriverWait driverWait = new WebDriverWait(driver, FileMgmtUtil.getNumberValue(CommonConstants.EXPLICIT_TIMEOUT));
            target = driverWait.until(ExpectedConditions.visibilityOf(element));
            if (target.isDisplayed()) {
                scrollIntoView(element);
                isElementDisplayed = true;
            }
        } catch (NoSuchElementException nsee) {
            logger.error("Cannot find element {}", element.toString());
            Assert.fail();
        } catch (Exception e) {
            logger.error("Something went wrong finding element {}", element.toString());
            Assert.fail();
        } finally {
            driver.manage().timeouts().implicitlyWait(ZERO_TIMEOUT, TimeUnit.SECONDS);
        }
        return isElementDisplayed;
    }

    /***
     * Clicks the specified element if present and clickable
     * @param element
     */
    public void clickElement(WebElement element) {
        try {
            if (isElementPresent(element) && element.isEnabled()) {
                logger.info("Clicking element...");
                scrollIntoView(element);
                element.click();
            } else {
                driver.manage().timeouts().implicitlyWait(ZERO_TIMEOUT, TimeUnit.SECONDS);
                WebDriverWait driverWait = new WebDriverWait(driver, FileMgmtUtil.getNumberValue(CommonConstants.EXPLICIT_TIMEOUT));
                driverWait.until(ExpectedConditions.elementToBeClickable(element));
                logger.info("Clicking element...");
                scrollIntoView(element);
                element.click();
            }
        } catch (Exception e) {
            logger.error("Unable to click element! {}", e.getMessage());
        }
    }

    public void clickElementFromList(List<WebElement> elements, String key) {
        try {
            for (WebElement element : elements) {
                if (key.contains(element.getText())) {
                    clickElement(element);
                }
            }
        } catch (Exception e) {
            logger.error("Unable to click element! {}", e.getMessage());
        }
    }

    /***
     *
     * @param element
     */
    public void jsClickElement(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    /***
     *
     * @param element
     * @param value
     */
    public void enterTextToField(WebElement element, String value) {

        try {
            if (isElementPresent(element) && element.isEnabled()) {
                scrollIntoView(element);
                logger.info("Entering text [{}] to field [{}]", value, element.toString());
                element.clear();
                element.sendKeys(value);
            }
        } catch (Exception e) {
            logger.error("Unable to input text to element! {}", e.getMessage());
        }
    }

    /**
     * WebDriver Utility that scrolls page into element identified as WebElement.
     *
     * @param element
     */
    public void scrollIntoView(WebElement element) {
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].scrollIntoView();", element);
        } catch (Exception e) {
            logger.error("Something went wrong [{}]", e.getMessage());
        }
    }

    /***
     *
     */
    public void waitForPageToLoad() {
        try {
            if (!isPageLoaded()) {
                WebDriverWait driverWait = new WebDriverWait(driver, FileMgmtUtil.getNumberValue("default.wait.for.page"));
                driverWait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
            }
        } catch (Exception e) {
            logger.error("Page did not load {}", e.getMessage());
            Assert.fail();
        }
    }

    /***
     *
     * @return
     */
    public boolean isPageLoaded() {
        return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
    }
}