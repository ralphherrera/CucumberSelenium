package com.cucumberselenium.rpdch.utilities;

import com.cucumberselenium.rpdch.constants.CommonConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class WebDriverWrapper {

    private static final Logger logger = LogManager.getLogger(LogManager.class);
    private static final int ZERO_TIMEOUT = 0;

    private WebDriver driver;

    public WebDriverWrapper(WebDriver driver) {
        this.driver = driver;
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
            if (isElementPresent(element)) {
                if (element.isEnabled()) {
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
            }
        } catch (Exception e) {
            logger.error("Unable to click element! {}", e.getMessage());
        }
    }

    /***
     *
     * @param element
     * @param value
     */
    public void enterTextToField(WebElement element, String value) {

    }

    /**
     * WebDriver Utility that scrolls page into element identified as WebElement.
     * @param element
     */
    public void scrollIntoView(WebElement element) {
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].scrollIntoView();", element);
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
