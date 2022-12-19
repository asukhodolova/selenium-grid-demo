package com.solvd.demo.mobile;

import com.solvd.demo.BasePage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ProductDetailsPage extends BasePage {

    @AndroidFindBy(xpath = "//*[contains(@resource-id,'product')]")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeScrollView/**/XCUIElementTypeStaticText")
    private WebElement nameLabel;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'add product to cart')]")
    @iOSXCUITFindBy(iOSNsPredicate = "name == 'Add To Cart'")
    private WebElement addToCartButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id,'cart')]")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeOther[-1]/XCUIElementTypeStaticText")
    private WebElement cartAmountLabel;

    public ProductDetailsPage(RemoteWebDriver driver) {
        super(driver);
    }

    public boolean isProductOpened(String productName) {
        return nameLabel.getText().equals(productName);
    }

    public ProductDetailsPage clickAddToCartButton() {
        addToCartButton.click();
        return this;
    }

    public int getProductsAmountInCart() {
        return Integer.valueOf(cartAmountLabel.getAttribute("name"));
    }
}
