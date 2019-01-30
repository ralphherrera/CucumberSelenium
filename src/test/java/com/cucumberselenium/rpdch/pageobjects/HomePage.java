package com.cucumberselenium.rpdch.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePage extends BasePage {

    private static final Logger logger = LogManager.getLogger(HomePage.class);

    // Page Elements
    @FindBy(css = "nav[class*=\"navbar\"] li[id= \"li_myaccount\"]")
    private WebElement ddl_MyAccount;

    @FindBy(xpath = "//nav[contains(@class, 'navbar')]//a[text()=' Login']")
    private WebElement llb_Login;

    @FindBy(xpath = "//nav[contains(@class, 'navbar')]//a[text()='  Sign Up']")
    private WebElement llb_SignUp;

    @FindBy(xpath = "(//li[@id='li_myaccount']//ul[@class='dropdown-menu']//li)[3]")
    private List<WebElement> ddl_MyAccountList;

    // Page Methods

    @Override
    WebElement returnPageElement(String key) {
        logger.info("Getting page element [{}]", key);
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

    @Override
    List<WebElement> returnPageElementList(String key) {
        logger.info("Getting page element [{}]", key);

        Map<String, List<WebElement>> elementListMap = new HashMap<>();

        elementListMap.put("My Account", ddl_MyAccountList);

        if (!elementListMap.containsKey(key)) {
            logger.warn("No Page Element found with key {}", key);
            Assert.fail();
        }
        return elementListMap.get(key);
    }

    public void openPage(String url) {
        driverWrapper.navigateToPage(url);
    }

}