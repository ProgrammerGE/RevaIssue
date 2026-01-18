Feature: Login to the application
  A user will need to login with valid credentials
  before they could access the application.

  Scenario Outline: Admin with a registered account successfully logs in to the Admin portal
    Given   A user exists with username "<username>" and password "<password>"
    And     The user is on the login page
    When    They enter a username "<username>"
    And     They enter a password "<password>"
    And     They click the login button
    Then    They are directed to the hubpage

  Examples:
  | username      | password    |
  | june@mail.com | 062025      |
  | juan@mail.com | cjdifjs4432 |
  | catguy        | 1           |

  Scenario Outline: Admin with a registered account invalid login
    Given   A user exists with username "<username>" and password "<password>"
    And     The user is on the login page
    When    They enter a username "<username>"
    And     They enter a password "<password>"
    Then    The login button is disabled because of invalid credentials

  Examples:
  | username      | password    |
  |               | 062025      |
  | juan@mail.com |             |
  |               |             |