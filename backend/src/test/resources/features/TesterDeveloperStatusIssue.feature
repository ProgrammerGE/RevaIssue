Feature: Issue Management for status changes

  Scenario Outline: Change Issue Status
    Given a "<user>" selects a project with issues
    When a user selects an issue
    And  "<user>" sets status to "<status>"
    Then the issue status is now "<status>"
    Examples:
      |user|status|
      |Developer|In Progress|
      |Developer|Resolved|
      |Tester|Close|
      |Tester|Open|