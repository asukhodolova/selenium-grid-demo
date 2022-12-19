package com.solvd.demo;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;

import java.lang.invoke.MethodHandles;

public abstract class BaseTest {

    protected static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    protected static final String SELENIUM_URL = "http://localhost:4444";
    protected static final Integer DEFAULT_IMPLICIT_WAIT = 5;

    protected RemoteWebDriver driver;

    @AfterClass
    public void tearDown() {
        LOGGER.info("---TEAR DOWN---");
        if (driver != null) {
            driver.quit();
        }
    }
}
