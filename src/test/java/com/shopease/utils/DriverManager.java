
package com.shopease.utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driver =
            new ThreadLocal<>();

    public static void createDriver() {

        String browser = System.getenv("BROWSER");

        if (browser == null || browser.trim().isEmpty()) {
            browser = ConfigReader.getProperty("browser");
        }

        browser = browser.trim().toLowerCase();

        WebDriver webDriver;

        switch (browser) {

            case "chrome":
                webDriver = new ChromeDriver();
                break;

            case "firefox":
                webDriver = new FirefoxDriver();
                break;

            case "remote":

                String gridUrl = System.getenv("GRID_URL");

                if (gridUrl == null || gridUrl.trim().isEmpty()) {
                    gridUrl = System.getProperty("selenium.grid.url");
                }

                if (gridUrl == null || gridUrl.trim().isEmpty()) {
                    throw new IllegalArgumentException(
                        "Remote browser requires GRID_URL or "
                        + "-Dselenium.grid.url"
                    );
                }

                try {
                    ChromeOptions options = new ChromeOptions();

                    webDriver = new RemoteWebDriver(
                        new URL(gridUrl),
                        options
                    );

                } catch (MalformedURLException e) {
                    throw new IllegalArgumentException(
                        "Invalid Selenium Grid URL: " + gridUrl,
                        e
                    );
                }

                break;

            default:
                throw new IllegalArgumentException(
                    "Unsupported browser: " + browser
                    + ". Supported values: chrome, firefox, remote"
                );
        }

        driver.set(webDriver);
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {

        WebDriver webDriver = driver.get();

        if (webDriver != null) {
            try {
                webDriver.quit();
            } finally {
                driver.remove();
            }
        }
    }
}
