package com.cucumberselenium.rpdch.stepdefinitions;

import com.cucumberselenium.rpdch.constants.CommonConstants;
import com.cucumberselenium.rpdch.utilities.BrowserManager;
import com.cucumberselenium.rpdch.utilities.FileMgmtUtil;
import com.cucumberselenium.rpdch.utilities.WebDriverWrapper;
import cucumber.api.Scenario;
import cucumber.api.java8.En;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.util.concurrent.TimeUnit;

public class ScenarioHooks implements En {

    private static final Logger logger = LogManager.getLogger(ScenarioHooks.class);

    private WebDriverWrapper driverWrapper;
    private BrowserManager browserManager;


    public ScenarioHooks() {

        Before((Scenario scenario) -> {
            logger.info("Starting scenario: {}", scenario.getName());
            try {
                browserManager = new BrowserManager();
                WebDriver driver = browserManager.getBrowserWebDriver(FileMgmtUtil.getPropertyValue("browser"));
                driver.manage().deleteAllCookies();
                Dimension dimension = new Dimension(1920, 1080); // Set browser window size
                driver.manage().window().setSize(dimension);
                driver.manage().timeouts().implicitlyWait(FileMgmtUtil.getNumberValue(CommonConstants.DEFAULT_TIMEOUT), TimeUnit.SECONDS);
                driverWrapper = new WebDriverWrapper(driver);
            } catch (WebDriverException wde) {
                logger.error(wde.getMessage());
                Assert.fail("Failed to initialize Web Driver");
            } catch (Exception e) {
                logger.error(e.getMessage());
                Assert.fail("Something went wrong!");
            }
        });

        After((Scenario scenario) -> {
            logger.info("Ending scenario: {}", scenario.getName());
            try {
                if (driverWrapper.getDriver() != null) {
                    driverWrapper.getDriver().quit();
                    driverWrapper = null;
                    logger.info("Successfully closed WebDriver Instance");
                }
            } catch (WebDriverException wde) {
                logger.error("Failed to close instance of Web Driver");
            } catch (Exception e) {
                logger.error("No Web Driver instance to close");
            }
        });
    }

    public WebDriverWrapper getDriverWrapper() {
        return driverWrapper;
    }

}
