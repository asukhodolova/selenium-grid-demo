package com.solvd.demo.mobile;

import com.solvd.demo.BaseTest;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;
import java.time.Duration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class MobileTest extends BaseTest {

    @BeforeClass
    @Parameters({"platformName"})
    public void setUp(String platformName) {
        LOGGER.info("---SET UP for platform=" + platformName + "---");
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "/src/test/resources/apps");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        try {
            switch (platformName) {
                case "ios":
                    File iosApp = new File(appDir.getCanonicalPath(), "SauceLabs-Demo-App.Simulator.zip");
                    capabilities.setCapability("app", iosApp.getAbsolutePath());
                    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 14");
                    capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
                    capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "16.0");
                    capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
                    driver = new IOSDriver(new URL(SELENIUM_URL), capabilities);
                    break;
                case "android":
                    File androidApp = new File(appDir.getCanonicalPath(), "mda-1.0.13-15.apk");
                    capabilities.setCapability("app", androidApp.getAbsolutePath());
                    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_3a_API_33_arm64-v8a");
                    capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
                    capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
                    capabilities.setCapability("uiautomator2ServerInstallTimeout", "40000");
                    driver = new AndroidDriver(new URL(SELENIUM_URL), capabilities);
                    break;
                default:
                    throw new RuntimeException("Unrecognized platform name: " + platformName);
            }
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_IMPLICIT_WAIT));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while creating a session", e);
        }
    }

    @Test
    public void testAddToCartAndVerifyCounter() {
        String productToOpen = new CatalogPage(driver).getAllProductNames().get(0);
        ProductDetailsPage productDetailsPage = new CatalogPage(driver).openProductByName(productToOpen);
        assertTrue(productDetailsPage.isProductOpened(productToOpen), "Product " + productToOpen + " is not opened");

        productDetailsPage.clickAddToCartButton();
        assertEquals(productDetailsPage.getProductsAmountInCart(), 1, "Incorrect amount of products in the cart");
    }
}
