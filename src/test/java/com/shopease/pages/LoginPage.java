
package com.shopease.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shopease.utils.ConfigReader;
import com.shopease.utils.DriverManager;
import com.shopease.utils.WaitUtils;

public class LoginPage {

    private final WebDriver driver;

    // Locators
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton =
            By.cssSelector("button[type='submit']");
    private final By flashMessage = By.id("flash");

    // Constructor
    public LoginPage() {
        this.driver = DriverManager.getDriver();
    }

    // Navigate to login page
    public void openLoginPage() {
        driver.get(ConfigReader.getProperty("baseUrl"));
    }

    // Enter username
    public void enterUsername(String username) {
        WaitUtils.waitForVisibility(usernameField).sendKeys(username);
    }

    // Enter password
    public void enterPassword(String password) {
        WaitUtils.waitForVisibility(passwordField).sendKeys(password);
    }

    // Click login
    public void clickLoginButton() {
        WaitUtils.waitForClickable(loginButton).click();
    }

    // Read the flash message
    public String getFlashMessage() {
        return WaitUtils.waitForVisibility(flashMessage).getText();
    }

    // Read the current browser URL
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // Wait until the secure page is loaded
    public void waitForSecurePage() {
        new org.openqa.selenium.support.ui.WebDriverWait(
                driver,
                java.time.Duration.ofSeconds(
                        Integer.parseInt(
                                ConfigReader.getProperty("explicitWait")
                        )
                )
        ).until(
                org.openqa.selenium.support.ui.ExpectedConditions
                        .urlContains("/secure")
        );
    }
    

    // Verify that login was successful
    public boolean isLoginSuccessful() {
        waitForSecurePage();

        return getCurrentUrl().contains("/secure")
                && getFlashMessage()
                    .contains("You logged into a secure area!");
    }

    // Verify that login failed with the expected password error
    public boolean isInvalidPasswordErrorDisplayed() {
        return getFlashMessage()
                .contains("Your password is invalid!");
    }

    
}
