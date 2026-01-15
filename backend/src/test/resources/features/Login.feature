Feature: Login to the application
  A user will need to login with valid credentials
  before they could access the application.


  Scenario: Admin with a registered account successfully logs in to the Admin portal
    Given   The admin is on the login page
    When    They enter a valid username
    And     They enter a valid password
    And     They click the login button
    Then    They are directed to the hubpage