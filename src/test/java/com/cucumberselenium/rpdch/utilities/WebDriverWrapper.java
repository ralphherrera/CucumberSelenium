package com.cucumberselenium.rpdch.utilities;

import com.cucumberselenium.rpdch.constants.CommonConstants;
import com.vimalselvam.cucumber.listener.Reporter;
import cucumber.api.Scenario;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WebDriverWrapper {

    private static final Logger logger = LogManager.getLogger(WebDriverWrapper.class);
    private static final int ZERO_TIMEOUT = 0;

    private WebDriver driver;
    private Scenario scenario;

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWrapper(WebDriver driver, Scenario scenario) {
        this.driver = driver;
        this.scenario = scenario;
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
            takeScreenshot();
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
                isElementDisplayed = true;
            }
        } catch (NoSuchElementException nsee) {
            logger.error("Cannot find element [{}]", element.toString());
            Assert.fail();
        } catch (Exception e) {
            logger.error("Something went wrong finding element [{}]", e.toString());
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
                embedScreenshotWithHighlight(element);
                element.click();
            } else {
                driver.manage().timeouts().implicitlyWait(ZERO_TIMEOUT, TimeUnit.SECONDS);
                WebDriverWait driverWait = new WebDriverWait(driver, FileMgmtUtil.getNumberValue(CommonConstants.EXPLICIT_TIMEOUT));
                driverWait.until(ExpectedConditions.elementToBeClickable(element));
                logger.info("Clicking element...");
                embedScreenshotWithHighlight(element);
                element.click();
            }
        } catch (Exception e) {
            logger.error("Unable to click element! [{}]", e.getMessage());
        } finally {
            driver.manage().timeouts().implicitlyWait(FileMgmtUtil.getNumberValue(CommonConstants.DEFAULT_TIMEOUT), TimeUnit.SECONDS);
        }
        logger.traceExit();
    }

    /***
     *
     * @param elements
     * @param key
     */
    public void clickElementFromList(List<WebElement> elements, String key) {
        logger.traceEntry("Clicking [{}] from list", key);
        try {
            if (areElementsPresent(elements)) {
                for (WebElement element : elements) {
                    logger.debug("ELEMENT TEXT [{}] -- KEY [{}]", element.getText(), key);
                    if (element.getText().toLowerCase().contains(key.toLowerCase())) {
                        embedScreenshotWithHighlight(element);
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
        embedScreenshotWithHighlight(element);
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
                logger.info("Entering text [{}] to field [{}]", value, element.toString());
                element.clear();
                element.sendKeys(value);
                embedScreenshotWithHighlight(element);
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

    /***
     *
     * @param element
     * @param attribute
     * @param value
     * @return
     */
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
        } finally {
            driver.manage().timeouts().implicitlyWait(FileMgmtUtil.getNumberValue(CommonConstants.DEFAULT_TIMEOUT), TimeUnit.SECONDS);
        }
        return logger.traceExit(didAttributeChanged);
    }

    /***
     *
     * @param element
     * @param text
     */
    public void waitForTextToChange(WebElement element, String text) {
        logger.traceEntry();
        try {
            driver.manage().timeouts().implicitlyWait(ZERO_TIMEOUT, TimeUnit.SECONDS);
            WebDriverWait driverWait = new WebDriverWait(driver, FileMgmtUtil.getNumberValue(CommonConstants.EXPLICIT_TIMEOUT));
            driverWait.until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (Exception e) {
            logger.error("Something went wrong [{}]", e.toString());
        } finally {
            driver.manage().timeouts().implicitlyWait(FileMgmtUtil.getNumberValue(CommonConstants.DEFAULT_TIMEOUT), TimeUnit.SECONDS);
        }
        logger.traceExit();
    }

    /**
     * Highlight Element
     *
     * @param webElement
     */
    private void highlightElement(WebElement webElement) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].style.border='2px solid red'", webElement);
        } catch (Exception e) {
            logger.error("Something went wrong {}", e);
        }
    }

    /**
     * Remove highlight element
     *
     * @param webElement
     */
    private void removeHighlightedElement(WebElement webElement) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].style.border='none'", webElement);
        } catch (Exception e) {
            logger.error("Something went wrong {}", e);
        }
    }

    /***
     * Embed screenshot with highlight to the HTML report
     * @return String
     */
    public void embedScreenshotWithHighlight(WebElement element) {
        logger.traceEntry();
        if (element != null) {
            highlightElement(element);
            takeScreenshot();
            removeHighlightedElement(element);
        } else {
            takeScreenshot();
        }
        logger.traceExit("Generating Screenshot");
    }

    /***
     * Takes a screen shot of the current window
     */
    public void takeScreenshot() {
        logger.traceEntry();
        try {
            final String outPath = System.getProperty("user.dir") + "\\target\\screenshots\\" + scenario.getId().split(";")[0] +
                    "\\" + scenario.getName().replaceAll("\\s", "_") + "\\" + scenario.getName().replaceAll("\\s", "_") +
                    "_" + CommonUtil.getTimeStamp() + ".png";

            // Convert WebDriver to TakeScreenshot
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;

            // Create image file
            File srcFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

            // Create new file object and move/rename the srcFile to the outPath destination
            FileUtils.copyFile(srcFile, new File(outPath).getAbsoluteFile());

            // Add screenshot to HTML report file
            Reporter.addScreenCaptureFromPath(outPath);

        } catch (Exception e) {
            logger.error("Something went wrong {}", e.getMessage());
        }

        logger.traceExit();
    }
}