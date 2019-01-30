package com.cucumberselenium.rpdch.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    // Page Elements
    @FindBy(css = "input[name*= 'username']")
    private WebElement txt_Email;

    @FindBy(css = "input[name*= 'password']")
    private WebElement txt_Password;

    @FindBy(xpath = "//button[text()= 'Login']")
    private WebElement btn_Login;

    // Page Methods

    @Override
    WebElement returnPageElement(String key) {
        logger.info("Getting page element [{}]", key);
        Map<String, WebElement> elementMap = new HashMap<>();

        elementMap.put("Email Field", txt_Email);
        elementMap.put("Password Field", txt_Password);
        elementMap.put("Login Button", btn_Login);

        if (!elementMap.containsKey(key)) {
            logger.warn("No Page Element found with key {}", key);
            Assert.fail();
        }
        return elementMap.get(key);

    }

    @Override
    List<WebElement> returnPageElementList(String key) {
        return null;
    }


}