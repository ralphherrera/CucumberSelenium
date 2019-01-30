package com.cucumberselenium.rpdch.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class BrowserManager {

    private static final Logger logger = LogManager.getLogger(BrowserManager.class);

    public BrowserManager() {

    }

    public WebDriver getBrowserWebDriver(String key) {
        WebDriver driver = null;
        logger.info("Opening browser [{}]", key);
        switch (key) {
            case "Chrome":
                driver = setUpChromeDriver();
                break;
            case "Firefox":
                driver = setUpGeckoDriver();
                break;
            case "IE":
                driver = setUpIEDriver();
                break;
            default:
                Assert.fail("Invalid browser name in config.properties: " + key);
        }
        return driver;
    }

    private ChromeDriver setUpChromeDriver() {
        System.setProperty("webdriver.chrome.driver", FileMgmtUtil.getPropertyValue("local.chrome.driver.path"));
        return new ChromeDriver();
    }

    private FirefoxDriver setUpGeckoDriver() {
        System.setProperty("webdriver.gecko.driver", FileMgmtUtil.getPropertyValue("local.firefox.driver.path"));
        return new FirefoxDriver();
    }

    private InternetExplorerDriver setUpIEDriver() {
        System.setProperty("webdriver.ie.driver", FileMgmtUtil.getPropertyValue("local.ie.driver.path"));
        return new InternetExplorerDriver();
    }

}