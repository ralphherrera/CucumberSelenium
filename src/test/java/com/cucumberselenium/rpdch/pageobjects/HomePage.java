package com.cucumberselenium.rpdch.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.Map;

public class HomePage {

    private static final Logger logger = LogManager.getFormatterLogger(HomePage.class);

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Page Elements
    @FindBy(css = "nav[class*=\"navbar\"] li[id= \"li_myaccount\"]")
    private WebElement ddl_MyAccount;

    @FindBy(css = "//nav[contains(@class, 'navbar')]//a[text()=' Login']")
    private WebElement llb_Login;

    @FindBy(css = "//nav[contains(@class, 'navbar')]//a[text()='  Sign Up']")
    private WebElement llb_SignUp;


    // Page Methods

    public WebElement returnPageElement(String key) {
        logger.info("Getting Page Element {}", key);

        Map<String, WebElement> elementMap = new HashMap<>();

        elementMap.put("My Account", ddl_MyAccount);
        elementMap.put("Login", ddl_MyAccount);
        elementMap.put("Sign Up", ddl_MyAccount);

        if (!elementMap.containsKey(key)) {
            logger.warn("No Page Element found with key {}", key);
            Assert.fail();
        }
        return elementMap.get(key);
    }
}
