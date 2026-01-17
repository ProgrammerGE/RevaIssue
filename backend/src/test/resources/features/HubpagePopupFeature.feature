Feature: Popup Functionality on the Hubpage for Admins

  Background: shared starting condition
    Given The admin is on the hub page

  Scenario: Admin can create projects
    When The admin clicks on the create project button
    And  inputs the project details
    And  clicks the submit new project button
    Then A project will be created

  Scenario: Admin can delete projects
    When The admin clicks on the delete project button
    And  clicks the confirm delete button
    Then A project will be delete

    Scenario: Admin can edit projects
      When The admin clicks on the update project button
      And  inputs the updated project details
      And  clicks the submit update project button
      Then A project will be updated

  Scenario Outline: Admin can cancel the popups
    When  The admin clicks on the "<title>" project button
    When  The admin clicks on the cancel button on the "<title>" popup
    Then  The "<title>" popup will disappear
    Examples:
      |title|
      |Delete Project|
      |Create Project|
      |Update Project|
