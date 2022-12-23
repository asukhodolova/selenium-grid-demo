package com.solvd.demo.gui;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    private static final Integer DEFAULT_WAIT = 10;

    protected RemoteWebDriver driver;

    public BasePage(RemoteWebDriver driver) {
        this.driver = driver;
        if (driver instanceof IOSDriver || driver instanceof AndroidDriver) {
            PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        } else {
            PageFactory.initElements(driver, this);
        }
    }

    protected boolean isElementPresent(String xpathLocator) {
        return driver.findElements(By.xpath(xpathLocator)).size() > 0;
    }

    protected void waitForPageLoaded() {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    protected void waitForElementPresent(String xpathLocator) {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathLocator)));
    }
}
