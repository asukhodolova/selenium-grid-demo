package com.solvd.demo.mobile;

import com.solvd.demo.BasePage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;
import java.util.stream.Collectors;

public class CatalogPage extends BasePage {

    @AndroidFindBy(xpath = "//*[contains(@resource-id,'title')]")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeOther[`name == 'ProductItem'`]/XCUIElementTypeStaticText[`NOT(name CONTAINS '$')`]")
    private List<WebElement> productNameLabels;

    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup")
    @iOSXCUITFindBy(iOSNsPredicate = "name == 'ProductItem'")
    private List<WebElement> productItems;

    public CatalogPage(RemoteWebDriver driver) {
        super(driver);
    }

    public List<String> getAllProductNames() {
        return productNameLabels.stream().map(p -> p.getText()).collect(Collectors.toList());
    }

    public ProductDetailsPage openProductByName(String productName) {
        int productIndex = -1;
        for (int i = 0; i < productNameLabels.size(); i++) {
            if (productNameLabels.get(i).getText().equals(productName)) {
                productIndex = i;
                break;
            }
        }
        if (productIndex != -1) {
            productItems.get(productIndex).click();
            return new ProductDetailsPage(driver);
        }
        throw new RuntimeException("Product " + productName + " not found");
    }
}
