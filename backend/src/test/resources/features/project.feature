Feature: Project Management

  Scenario: Add users to a project
    Given   I am an admin
    When I open up a project
    And  I add  users to the project
    Then I can see who is on the project
