package com.solvd.demo.gui;

import com.solvd.demo.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

public class GoogleSearchPage extends BasePage {

    private static final String URL = "https://www.google.com/";
    private static final String COOKIES_DIALOG_LOCATOR = "//*[@role='dialog']";
    private static final String SEARCH_INPUT_LOCATOR = "//input[@type='text']";

    @FindBy(xpath = SEARCH_INPUT_LOCATOR)
    private WebElement searchInput;

    @FindBy(xpath = "//button[.='Accept all' or .='Zaakceptuj wszystko']")
    private WebElement acceptAllCookiesButton;

    public GoogleSearchPage(RemoteWebDriver driver) {
        super(driver);
    }

    public GoogleSearchPage open() {
        driver.get(URL);
        if (isElementPresent(COOKIES_DIALOG_LOCATOR)) {
            acceptAllCookiesButton.click();
        }
        return new GoogleSearchPage(driver);
    }

    public GoogleSearchResultsPage performSearch(String searchValue) {
        wait(3);
        searchInput.sendKeys(searchValue);
        searchInput.sendKeys(Keys.ENTER);
        return new GoogleSearchResultsPage(driver);
    }
}
