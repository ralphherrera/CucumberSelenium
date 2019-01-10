package com.cucumberselenium.rpdch.stepdefinitions;

import com.cucumberselenium.rpdch.constants.CommonConstants;
import com.cucumberselenium.rpdch.utilities.FileMgmtUtil;
import cucumber.api.Scenario;
import cucumber.api.java8.En;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class ScenarioHooks implements En {

    private static final Logger logger = LogManager.getLogger(ScenarioHooks.class);

    private static WebDriver driver;

    public ScenarioHooks() {

        Before((Scenario scenario) -> {
            logger.info("Starting scenario: {}", scenario.getName());
            try {
                System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver.exe");
                driver = new ChromeDriver();
                driver.manage().deleteAllCookies();
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(FileMgmtUtil.getNumberValue(CommonConstants.DEFAULT_TIMEOUT), TimeUnit.SECONDS);
            } catch (WebDriverException wde) {
                Assert.fail("Failed to initialize Web Driver");
            } catch (Exception e) {
                Assert.fail("Something went wrong!");
            }
        });

        After((Scenario scenario) -> {
            logger.info("Ending scenario: {}", scenario.getName());
            try {
                if (driver != null) {
                    driver.quit();
                    driver = null;
                    logger.info("Successfully closed WebDriver Instance");
                }
            } catch (WebDriverException wde) {
                logger.error("Failed to close instance of Web Driver");
            } catch (Exception e) {
                logger.error("No Web Driver instance to close");
            }
        });
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
