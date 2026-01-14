Feature: Popup Functionality on the Hubpage for Admins

  Background: shared starting condition
    Given The admin is on the hub page

  Scenario: Admin can click the delete project button for a popup
    When  The admin clicks on the delete project button
    Then  A popup will appear for deleting a project

  Scenario: Admin can click the create project button for a popup
    When  The admin clicks on the create project button
    Then  A popup will appear to create a new project

  Scenario: Admin can click the edit project button for a popup
    When  The admin clicks on the edit project button
    Then  A popup will appear to edit the selected project

  Scenario Outline: Admin can cancel the popups
    When  The admin clicks on the cancel button on the "<title>" popup
    Then  The "<title>" popup will disappear
    Examples:
      |title|
      |Delete Project|
      |Create Project|
      |Update Project|

    Scenario: Admin can create projects
      When The admin clicks on the create project button
      And  inputs the project details
      And  clicks the submit button
      Then A project will be created