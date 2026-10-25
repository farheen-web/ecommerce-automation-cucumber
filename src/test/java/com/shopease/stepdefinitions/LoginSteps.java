
package com.shopease.stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.shopease.pages.LoginPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class LoginSteps {

    private LoginPage loginPage;

    @Given("the user is on the login page")
    public void userIsOnLoginPage() {
        loginPage = new LoginPage();
        loginPage.openLoginPage();

        System.out.println("Opened the login page");
    }

    @When("the user enters username {string} and password {string}")
    public void enterCredentials(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);

        System.out.println("Entered credentials for: " + username);
    }

    @And("the user clicks the login button")
    public void clickLoginButton() {
        loginPage.clickLoginButton();

        System.out.println("Clicked the login button");
    }


    @Then("the login result should be {string}")
    public void verifyLoginResult(String expectedResult) {

        if (expectedResult.equalsIgnoreCase("success")) {

            assertTrue(
                loginPage.isLoginSuccessful(),
                "Expected successful login, but verification failed"
            );

        } else if (expectedResult.equalsIgnoreCase("failure")) {

            assertTrue(
                loginPage.isInvalidPasswordErrorDisplayed(),
                "Expected invalid-password error, but verification failed"
            );

        } else {
            fail("Unsupported expected result: " + expectedResult);
        }

        System.out.println(
            "Login result verified: " + expectedResult
        );
    }

}
