
package com.shopease.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {

    private static final int WAIT_TIMEOUT =
            Integer.parseInt(ConfigReader.getProperty("explicitWait"));

    public static WebElement waitForVisibility(By locator) {

        WebDriver driver = DriverManager.getDriver();

        WebDriverWait wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(WAIT_TIMEOUT)
        );

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    public static WebElement waitForClickable(By locator) {

        WebDriver driver = DriverManager.getDriver();

        WebDriverWait wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(WAIT_TIMEOUT)
        );

        return wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );
    }
}
