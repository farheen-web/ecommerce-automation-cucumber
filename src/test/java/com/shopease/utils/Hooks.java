
package com.shopease.utils;

import io.cucumber.java.Before;
import io.cucumber.java.After;

public class Hooks {

    @Before
    public void setUp() {
        System.out.println("=== CUCUMBER BEFORE HOOK STARTED ===");

        DriverManager.createDriver();
        DriverManager.getDriver().manage().window().maximize();

        System.out.println("=== CHROME STARTED SUCCESSFULLY ===");
    }

    @After
    public void tearDown() {
        System.out.println("=== CUCUMBER AFTER HOOK STARTED ===");

        DriverManager.quitDriver();

        System.out.println("=== CHROME CLOSED ===");
    }
}
