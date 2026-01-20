Feature: Admins can view a list of audit logs

  Scenario: Admins can view Audit Logs
    Given The admin is on the hub page
    When the admin looks at the audit log section
    Then they can see the audit log list within the section

  Scenario Outline: Non-Admins can view Audit Logs
    Given The "<user>" is on the hub page
    When they look for the audit log section
    Then they can't see the audit log list
    Examples:
      |user|
      |tester|
      |developer|