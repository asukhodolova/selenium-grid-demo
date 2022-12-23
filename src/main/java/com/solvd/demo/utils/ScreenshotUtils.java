package com.solvd.demo.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

public class ScreenshotUtils {

    private static final String SCREENSHOTS_PATH = "./assets/screenshots/";
    private static final String SCREENSHOT_NAME_PATTERN = SCREENSHOTS_PATH + "%s_screenshot_%d.png";

    public static void takeElementScreenshot(RemoteWebDriver driver, WebElement element) {
        File scrFile = element.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File(String.format(SCREENSHOT_NAME_PATTERN,
                    driver.getCapabilities().getBrowserName(), getTimestamp())));
        } catch (IOException e) {
            throw new RuntimeException("Error while taking an element screenshot", e);
        }
    }

    public static void takeScreenshot(RemoteWebDriver driver) {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File(String.format(SCREENSHOT_NAME_PATTERN,
                    driver.getCapabilities().getBrowserName(), getTimestamp())));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while taking a screenshot", e);
        }
    }

    private static long getTimestamp() {
        return new Timestamp(System.currentTimeMillis()).getTime();
    }
}
