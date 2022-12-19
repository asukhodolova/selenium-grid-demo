package com.solvd.demo.gui;

import com.solvd.demo.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class GoogleSearchResultsPage extends BasePage {

    private static final String SEARCH_RESULT_LINK_LOCATOR = "//*[@id='search']//a";

    @FindBy(xpath = SEARCH_RESULT_LINK_LOCATOR)
    private List<WebElement> searchResultLinks;

    public GoogleSearchResultsPage(RemoteWebDriver driver) {
        super(driver);
    }

    public String getFirstSearchResult() {
        waitForElementPresent(SEARCH_RESULT_LINK_LOCATOR);
        return searchResultLinks.get(0).getText();
    }
}
