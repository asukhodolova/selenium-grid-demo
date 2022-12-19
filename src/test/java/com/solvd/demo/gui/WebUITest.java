package com.solvd.demo.gui;

import com.solvd.demo.BaseTest;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.testng.Assert.assertTrue;

public class WebUITest extends BaseTest {

    private static final String SEARCH_VALUE = "Zebrunner";

    @BeforeClass
    @Parameters({"browser"})
    public void setUp(String browser) {
        LOGGER.info("---SET UP for browser=" + browser + "---");
        AbstractDriverOptions options;
        switch (browser) {
            case "chrome":
                options = new ChromeOptions();
                break;
            case "safari":
                options = new SafariOptions();
                options.setCapability(CapabilityType.PLATFORM_NAME, "MAC");
                break;
            case "firefox":
                options = new FirefoxOptions();
                break;
            default:
                throw new RuntimeException("Unrecognized browser: " + browser);
        }
        try {
            options.setCapability("se:recordVideo", "true");
            options.setCapability("se:timeZone", "US/Pacific");
            options.setCapability("se:screenResolution", "1920x1080");
            driver = new RemoteWebDriver(new URL(SELENIUM_URL), options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while creating a session");
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_IMPLICIT_WAIT));
        driver.manage().window().maximize();
    }

    @Test
    public void testGoogleSearch() {
        LOGGER.info("---TEST is started---");
        String firstResult = new GoogleSearchPage(driver).open().performSearch(SEARCH_VALUE).getFirstSearchResult();
        assertTrue(firstResult.contains(SEARCH_VALUE), "Search result does not include " + SEARCH_VALUE);
        LOGGER.info("---TEST is finished---");
    }
}
