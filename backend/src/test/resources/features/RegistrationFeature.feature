Feature: Users can register to the app

  Background:
    Given The user is on the registration page

    Scenario Outline: Users can register as a select role
      When The "<user>" enters their information
      And  Selects the "<role>" role
      And  Clicks on the submit button
      Then The user should be registered and sent to login
      Examples:
        |user|role|
        |admin109|ADMIN|
        |tester101|TESTER|
        |developer102|DEVELOPER|

      Scenario: Users can cancel registration
        When The user clicks on the cancel button
        Then The user returns to the login page