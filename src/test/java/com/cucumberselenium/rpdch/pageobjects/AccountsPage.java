package com.cucumberselenium.rpdch.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountsPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(AccountsPage.class);

    // Page Elements
    @FindBy(css = "h3[class*= 'RTL']")
    private WebElement lbl_NameHeader;

    @Override
    WebElement returnPageElement(String key) {
        logger.info("Getting page element [{}]", key);
        Map<String, WebElement> elementMap = new HashMap<>();

        elementMap.put("Name Header", lbl_NameHeader);

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
