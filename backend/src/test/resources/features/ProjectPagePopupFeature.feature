Feature: Popup Functionality on the Project page for Users

  Background: shared starting condition
    Given The user is on the project page

  Scenario: Testers can click on the create issue botton for a popup
    When The tester clicks on the create issue button
    Then A popup will appear for creating an issue for the project

    Scenario: Users can click on the update issue button for a popup
      When Users click the update issue button
      Then A popup will appear for updating the issue details