Feature: Project Management

  Background:
    Given   I am an admin
    When I open up a project


  Scenario: Add users to a project
    And  I add  users to the project
    Then I can see who is on the project

  Scenario Outline: filter issues
    When    an issues "<status>" "<priority>" and "<severity>" are entered
    Then    I can see all the filtered issues
    Examples:
    |status     |priority|severity|
    |OPEN       |   1    |   1    |
    |CLOSED     |   1    |   1    |
    |RESOLVED   |   1    |   1    |
    |IN PROGRESS|   1    |   1    |
