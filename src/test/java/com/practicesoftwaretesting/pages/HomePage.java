package com.practicesoftwaretesting.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HomePage {

    private final WebDriver driver;
    private final Actions move;

    // SIDE BAR
    @FindBy(css = ".form-select")
    private WebElement sortSelect;
    @FindBy(css = ".ngx-slider-pointer-min")
    private WebElement priceRangeSliderMinPointer;
    @FindBy(css = ".ngx-slider-model-value")
    private WebElement priceRangeMinValue;
    @FindBy(css = ".ngx-slider-pointer-max")
    private WebElement priceRangeSliderMaxPointer;
    @FindBy(css = ".ngx-slider-model-high")
    private WebElement priceRangeMaxValue;
    @FindBy(id = "search-query")
    private WebElement searchField;
    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;
    private By categoryFilterCheckboxLabel = By.cssSelector(".checkbox label");
    private By firstCategoryPanel = By.cssSelector("fieldset > div:first-of-type");
    // PRODUCTS
    private By productsListContainer = By.cssSelector("div .container");
    private By productsListLocator = By.cssSelector(".col-md-9");
    private By product = By.cssSelector(".card");
    private By productName = By.cssSelector(".card-title");
    private By productPrice = By.cssSelector("[data-test='product-price']");
    private By searchCaption = By.cssSelector("[data-test='search-caption']");
    private By paginationList = By.cssSelector(".pagination");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.move = new Actions(driver);
        PageFactory.initElements(driver, this);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(firstCategoryPanel));
    }

    public void selectSortOptionByIndex(int index) {
        new Select(sortSelect).selectByIndex(index);
    }

    public void changeMinPrice() {
        move.clickAndHold(priceRangeSliderMinPointer).moveByOffset(70, 0).release().perform();
    }

    public int getMinPriceFromPriceRange() {
        String price = priceRangeMinValue.getText();
        return Integer.parseInt(price);
    }

    public void changeMaxPrice() {
        move.clickAndHold(priceRangeSliderMaxPointer).moveByOffset(-100, 0).release().perform();
    }

    public int getMaxPriceFromPriceRange() {
        String price = priceRangeMaxValue.getText();
        return Integer.parseInt(price);
    }

    public void enterProductInSearchField(String productName) {
        searchField.sendKeys(productName);
    }

    public void clickSearchProductButton() {
        searchButton.click();
    }

    public void selectCategoryCheckboxFilterByLabel(String labelText) {
        List<WebElement> labels = driver.findElements(categoryFilterCheckboxLabel);
        for (WebElement label : labels) {
            if (label.getText().trim().equalsIgnoreCase(labelText.trim())) {
                WebElement checkbox = label.findElement(By.tagName("input"));
                checkbox.click();
                if (checkbox.isSelected()) {
                    return;
                } else {
                    System.out.println("Checkbox was not selected");
                }
            }
        }
        System.out.println("Label not found" + labelText);
    }

    public void waitForAllProductsToLoad() {
        List<WebElement> products = getProductsList();
        for (WebElement product : products) {
            waitForProductImageToLoad(product);
        }
    }

    public List<WebElement> getProductsList() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement productsList = wait.until(ExpectedConditions.visibilityOfElementLocated(productsListLocator));
        return productsList.findElements(product);
    }

    public void waitForProductImageToLoad(WebElement product) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement img = wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(product, By.tagName("img")));
        String src = img.getAttribute("src");
        wait.until(driver -> src != null && !src.isEmpty());
    }

    public ProductPage goToProduct(WebElement product) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(product));
        product.click();
        return new ProductPage(driver);
    }

    public void waitForTableNamesToReload(List<String> oldListNames) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> {
            try {
                List<String> newListNames = getProductsNamesFromTheList();
                return !newListNames.equals(oldListNames);
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });
    }

    public void waitForSortingToComplete() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.attributeContains(productsListContainer, "data-test", "sorting_completed"));
    }

    public void waitForSearchingProductToComplete() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.attributeContains(productsListContainer, "data-test", "search_completed"));
    }

    public void waitForFilteringToComplete() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.attributeContains(productsListContainer, "data-test", "filter_completed"));
    }

    public List<String> getProductsNamesFromTheList() {
        return getProductsList().stream().map(this::getProductName).toList();
    }

    public List<Double> getProductsPricesFromTheList() {
        return getProductsList().stream().map(this::getProductPrice).toList();
    }

    public String getProductName(WebElement product) {
        return product.findElement(productName).getText();
    }

    public List<String> getAllProductsNames() {
        if (isPaginationDisplayed()) {
            List<WebElement> totalPages = getPagesButtons();
            List<String> allNames = new ArrayList<>();
            for (int i = 1; i <= totalPages.size()-1; i++) {
                WebElement initialProduct = getProductsList().getFirst();
                totalPages.get(i).click();
                new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.stalenessOf(initialProduct));
                allNames.addAll(getProductsNamesFromTheList());
            }
            return allNames;
        } else {
            return getProductsNamesFromTheList();
        }
    }

    public double getProductPrice(WebElement product) {
        String priceStr = product.findElement(productPrice).getText();
        // Delete currency symbol
        String priceNum = priceStr.substring(1);
        return Double.parseDouble(priceNum);
    }

    public List<Double> getAllProductsPrices() {
        if (isPaginationDisplayed()) {
            List<WebElement> totalPages = getPagesButtons();
            List<Double> allPrices = new ArrayList<>();
            for (int i = 1; i <= totalPages.size()-1; i++) {
                WebElement initialProduct = getProductsList().getFirst();
                totalPages.get(i).click();
                new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.stalenessOf(initialProduct));
                allPrices.addAll(getProductsPricesFromTheList());
            }
            return allPrices;
        } else {
            return getProductsPricesFromTheList();
        }
    }

    public String getSearchCaptionText() {
        return new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(searchCaption)).getText();
    }

    private boolean isPaginationDisplayed() {
        try {
            WebElement pagination = driver.findElement(paginationList);
            return pagination.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private List<WebElement> getPagesButtons() {
        org.openqa.selenium.WebElement pagination = driver.findElement(paginationList);
        List<org.openqa.selenium.WebElement> list = pagination.findElements(By.cssSelector("li a"));
        // Deleting previous and next page buttons
        list.removeFirst();
        list.removeLast();

        return list;
    }

}
