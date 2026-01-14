Feature: Issue Management

  Background:
    Given    I open a project with issues

  Scenario: View list of issues
    Then    I will see a list of issues for that project

  Scenario: Update an issue
    When    I select an issue I want to update
    And     I change the values of the issue
    Then    I will see the updated issue details

  Scenario: View history of an issue
    When    I select an issue
    Then    I can see the comments describing the history

  Scenario Outline: Change Issue Status
    When a user selects an issue
    And  "<user>" sets status to "<status>"
    Then the issue status is now "<status>"
    Examples:
      |user|status|
      |Developer|In Progress|
      |Developer|Resolved|
      |Tester|Close|
      |Tester|Open|