
Feature: User Login

  Scenario Outline: Login with different credentials
    Given the user is on the login page
    When the user enters username "<username>" and password "<password>"
    And the user clicks the login button
    Then the login result should be "<expectedResult>"

    Examples:
      | username | password              | expectedResult |
      | tomsmith | SuperSecretPassword!  | success        |
      | tomsmith | WrongPassword123      | failure        |
