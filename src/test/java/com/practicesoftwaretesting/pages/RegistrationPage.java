package com.practicesoftwaretesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class RegistrationPage {

    public WebDriver driver;
    @FindBy(id = "first_name")
    private WebElement firstNameInput;
    @FindBy(id = "last_name")
    private WebElement lastNameInput;
    @FindBy(id = "dob")
    private WebElement dateOfBirthInput;
    @FindBy(id = "street")
    private WebElement streetInput;
    @FindBy(id = "postal_code")
    private WebElement postalCodeInput;
    @FindBy(id = "city")
    private WebElement cityInput;
    @FindBy(id = "state")
    private WebElement stateInput;
    @FindBy(id = "country")
    private Select countrySelect;
    @FindBy(id = "phone")
    private WebElement phoneNrInput;
    @FindBy(id = "email")
    private WebElement emailInput;
    @FindBy(id = "password")
    private WebElement passwordInput;
    @FindBy(css = "[data-test='register-submit']")
    private WebElement registerButton;
    private By error = By.cssSelector(".alert-danger");
    private By passwordError = By.cssSelector("[data-test='password-error']");
    @FindBy(css = "#passwordHelp ul ul")
    private WebElement passwordHelpList;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlToBe("https://practicesoftwaretesting.com/auth/register"));
        PageFactory.initElements(driver, this);
    }

    public void enterFirstName(String fName) {
        firstNameInput.sendKeys(fName);
    }

    public void enterLastName(String lName) {
        lastNameInput.sendKeys(lName);
    }

    public void enterDOB(String dob) {
        dateOfBirthInput.sendKeys(dob);
    }

    public void enterStreet(String street) {
        streetInput.sendKeys(street);
    }

    public void enterPostalCode(String pCode) {
        postalCodeInput.sendKeys(pCode);
    }

    public void enterCity(String city) {
        cityInput.sendKeys(city);
    }

    public void enterState(String state) {
        stateInput.sendKeys(state);
    }

    public void selectCountryByVisibleText(String country) {
        countrySelect.selectByVisibleText(country);
    }

    public void enterPhoneNumber(String number) {
        phoneNrInput.sendKeys(number);
    }

    public void enterEmail(String email) {
        emailInput.sendKeys(email);
    }

    public void enterPassword(String pass) {
        passwordInput.clear();
        passwordInput.sendKeys(pass);
    }

    public void clickRegisterButton() {
        registerButton.click();
    }

    public List<WebElement> getValidationErrors() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(error));
        return driver.findElements(error);
    }

    public List<String> getValidationErrorMessages(List<WebElement> validationErrors) {
        List<String> validationMessages = new ArrayList<>();
        for (WebElement error : validationErrors) {
            WebElement el = error.findElement(By.tagName("div"));
            validationMessages.add(el.getText());
        }
        return validationMessages;
    }

    public List<String> getPasswordValidationMessages() {
        List<String> messages = new ArrayList<>();
        WebElement passError = driver.findElement(passwordError);
        List<WebElement> errorMessElements = passError.findElements(By.tagName("div"));
        for (WebElement mess : errorMessElements) {
            messages.add(mess.getText());
        }
        return messages;
    }

    public List<WebElement> getPasswordHelpList() {
        return passwordHelpList.findElements(By.tagName("li"));
    }

    public boolean doesPasswordMatchThisRuleByIndex(int index) {
        List<WebElement> list = getPasswordHelpList();
        WebElement rule = list.get(index);
        String classes = rule.getAttribute("class");
        return classes.contains("text-success");
    }

}
