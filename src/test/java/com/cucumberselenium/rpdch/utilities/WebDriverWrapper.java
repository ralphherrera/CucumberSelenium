package com.cucumberselenium.rpdch.utilities;

import com.cucumberselenium.rpdch.constants.CommonConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
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
        logger.traceEntry("Opening site: [{}]", url);
        try {
            driver.get(url);
            waitForPageToLoad();
        } catch (Exception e) {
            logger.error("Something went wrong ", e.getMessage());
            Assert.fail();
        }
        logger.traceExit();
    }

    /***
     * Checks the Page Element in the DOM if it is present
     * @param element
     * @return True if element is present | False if element is not displayed in the DOM
     */
    public boolean isElementPresent(WebElement element) {
        logger.traceEntry();

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
            logger.error("Cannot find element [{}]", element.toString());
            Assert.fail();
        } catch (Exception e) {
            logger.error("Something went wrong finding element [{}]", e.toString());
//            Assert.fail();
        } finally {
            driver.manage().timeouts().implicitlyWait(FileMgmtUtil.getNumberValue(CommonConstants.DEFAULT_TIMEOUT), TimeUnit.SECONDS);
        }
        return logger.traceExit(isElementDisplayed);
    }

    /***
     * Checks the Page Element in the DOM if it is present
     * @param elements
     * @return True if elements are present | False if element are not displayed in the DOM
     */
    public boolean areElementsPresent(List<WebElement> elements) {
        logger.traceEntry();

        List<WebElement> targets;
        boolean areElementsDisplayed = false;
        try {
            driver.manage().timeouts().implicitlyWait(ZERO_TIMEOUT, TimeUnit.SECONDS);
            WebDriverWait driverWait = new WebDriverWait(driver, FileMgmtUtil.getNumberValue(CommonConstants.EXPLICIT_TIMEOUT));
            targets = driverWait.until(ExpectedConditions.visibilityOfAllElements(elements));

            logger.debug("Element list size [{}]", elements.size());
            if (targets.size() != 0) {
                areElementsDisplayed = true;
            }

        } catch (NoSuchElementException nsee) {
            logger.error("Cannot find elements [{}]", elements.toString());
            Assert.fail();
        } catch (Exception e) {
            logger.error("Something went wrong finding element [{}]", e.toString());
//            Assert.fail();
        } finally {
            driver.manage().timeouts().implicitlyWait(FileMgmtUtil.getNumberValue(CommonConstants.DEFAULT_TIMEOUT), TimeUnit.SECONDS);
        }
        return logger.traceExit(areElementsDisplayed);
    }

    /***
     * Clicks the specified element if present and clickable
     * @param element
     */
    public void clickElement(WebElement element) {
        logger.traceEntry();
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
            logger.error("Unable to click element! [{}]", e.getMessage());
        } finally {
            driver.manage().timeouts().implicitlyWait(FileMgmtUtil.getNumberValue(CommonConstants.DEFAULT_TIMEOUT), TimeUnit.SECONDS);
        }
        logger.traceExit();
    }

    public void clickElementFromList(List<WebElement> elements, String key) {
        logger.traceEntry("Clicking [{}] from list", key);
        try {
            if (areElementsPresent(elements)) {
                for (WebElement element : elements) {
                    logger.debug("ELEMENT TEXT [{}] -- KEY [{}]", element.getText(), key);
                    if (element.getText().toLowerCase().contains(key.toLowerCase())) {
                        clickElement(element);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Unable to click element! [{}]", e.getMessage());
        }
        logger.traceExit();
    }

    /***
     *
     * @param element
     */
    public void jsClickElement(WebElement element) {
        logger.traceEntry("Clicking element [{}]", element.toString());
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
        logger.traceExit();
    }

    /***
     *
     * @param element
     * @param value
     */
    public void enterTextToField(WebElement element, String value) {
        logger.traceEntry();
        try {
            if (isElementPresent(element) && element.isEnabled()) {
                scrollIntoView(element);
                logger.info("Entering text [{}] to field [{}]", value, element.toString());
                element.clear();
                element.sendKeys(value);
            }
        } catch (Exception e) {
            logger.error("Unable to input text to element! [{}]", e.getMessage());
        }
        logger.traceExit();
    }

    /**
     * WebDriver Utility that scrolls page into element identified as WebElement.
     *
     * @param element
     */
    public void scrollIntoView(WebElement element) {
        logger.traceEntry();
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].scrollIntoView();", element);
        } catch (Exception e) {
            logger.error("Something went wrong [{}]", e.getMessage());
        }
        logger.traceExit();
    }

    /***
     *
     */
    public void waitForPageToLoad() {
        logger.traceEntry();
        try {
            if (!isPageLoaded()) {
                WebDriverWait driverWait = new WebDriverWait(driver, FileMgmtUtil.getNumberValue("default.wait.for.page"));
                driverWait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
            }
        } catch (Exception e) {
            logger.error("Page did not load {}", e.getMessage());
            Assert.fail();
        }
        logger.traceExit();
    }

    /***
     *
     * @return
     */
    public boolean isPageLoaded() {
        logger.traceEntry();
        return logger.traceExit(((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    public boolean waitForHtmlAttributeToChange(WebElement element, String attribute, String value) {
        logger.traceEntry();
        boolean didAttributeChanged = false;
        try {
            if (isElementPresent(element)) {
                driver.manage().timeouts().implicitlyWait(ZERO_TIMEOUT, TimeUnit.SECONDS);
                WebDriverWait driverWait = new WebDriverWait(driver, FileMgmtUtil.getNumberValue(CommonConstants.EXPLICIT_TIMEOUT));
                logger.info("Current Attribute 1 [{}] with Value of [{}]", attribute, element.getAttribute(attribute));
                didAttributeChanged = driverWait.until(ExpectedConditions.attributeToBe(element, attribute, value));
                logger.info("Getting Attribute 2 [{}] with Value of [{}]", attribute, element.getAttribute(attribute));
            }
        } catch (WebDriverException wde) {
            logger.error("Something went wrong [{}]", wde.toString());
            Assert.fail();
        } catch (Exception e) {
            logger.error("Something went wrong [{}]", e.toString());
//            Assert.fail();
        } finally {
            driver.manage().timeouts().implicitlyWait(FileMgmtUtil.getNumberValue(CommonConstants.DEFAULT_TIMEOUT), TimeUnit.SECONDS);
        }
        return logger.traceExit(didAttributeChanged);
    }

    public void waitForTextToChange(WebElement element, String text) {
        logger.traceEntry();

        try {
            driver.manage().timeouts().implicitlyWait(ZERO_TIMEOUT, TimeUnit.SECONDS);
            WebDriverWait driverWait = new WebDriverWait(driver, FileMgmtUtil.getNumberValue(CommonConstants.EXPLICIT_TIMEOUT));

            logger.debug("ELEMENT TEXT TEXT [{}]", element.getText());

            driverWait.until(ExpectedConditions.textToBePresentInElement(element, text));

        } catch (Exception e) {
            logger.error("Something went wrong [{}]", e.toString());
        } finally {
            driver.manage().timeouts().implicitlyWait(FileMgmtUtil.getNumberValue(CommonConstants.DEFAULT_TIMEOUT), TimeUnit.SECONDS);
        }

        logger.traceExit();
    }
}