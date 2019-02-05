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

    @FindBy(css = "ul[class*='nav-tabs'] li")
    private List<WebElement> btn_ServiceList;

    // Flights
    @FindBy(id = "s2id_autogen12")
    private WebElement txt_FrmCityAirport;

    @FindBy(css = "input[data-type='oneway']")
    private WebElement rdo_OneWay;

    @FindBy(xpath = "(//div[@class='pure-checkbox']//div[contains(@class,'iradio_square-grey')])[2]")
    private WebElement rdo_RoundTrip;

    @FindBy(css = "input[name='departure']")
    private WebElement txt_DepartureDate;

    @FindBy(css = "input[name='arrival']")
    private WebElement txt_ReturnDate;

    @FindBy(css = "div[id='flights'] button[type='submit']")
    private WebElement btn_FlightsSubmit;

    @FindBy(css = "ul[class='select2-results'] li")
    private List<WebElement> llb_FrmCityAirportAutoCompleteList;

    @FindBy(css = "ul[class='select2-results'] span[class='select2-match']")
    private WebElement llb_FrmCityAirportAutoCompleteFirstRow;

    // Page Methods

    @Override
    WebElement returnPageElement(String key) {
        logger.traceEntry("Getting page element [{}]", key);
        Map<String, WebElement> elementMap = new HashMap<>();

        elementMap.put("My Account", ddl_MyAccount);
        elementMap.put("Login", ddl_MyAccount);
        elementMap.put("Sign Up", ddl_MyAccount);
        elementMap.put("From Field", txt_FrmCityAirport);
        elementMap.put("One Way", rdo_OneWay);
        elementMap.put("Round Trip", rdo_RoundTrip);
        elementMap.put("Departure Date", txt_DepartureDate);
        elementMap.put("Return Date", txt_ReturnDate);
        elementMap.put("From Field Auto Complete First Row", llb_FrmCityAirportAutoCompleteFirstRow);
        elementMap.put("Flights Submit Button", btn_FlightsSubmit);

        if (!elementMap.containsKey(key)) {
            logger.warn("No Page Element found with key {}", key);
            Assert.fail();
        }
        return logger.traceExit(elementMap.get(key));
    }

    @Override
    List<WebElement> returnPageElementList(String key) {
        logger.traceEntry("Getting page element [{}]", key);

        Map<String, List<WebElement>> elementListMap = new HashMap<>();

        elementListMap.put("My Account", ddl_MyAccountList);
        elementListMap.put("Service", btn_ServiceList);
        elementListMap.put("From Field Auto Complete List", llb_FrmCityAirportAutoCompleteList);

        if (!elementListMap.containsKey(key)) {
            logger.warn("No Page Element found with key {}", key);
            Assert.fail();
        }
        return logger.traceExit(elementListMap.get(key));
    }

    public void openPage(String url) {
        driverWrapper.navigateToPage(url);
    }
}