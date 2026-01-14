Feature: Popup Functionality on the Project page for Users

  Background: shared starting condition
    Given The user is on the project page

  Scenario: Testers can create issue botton for a project
    When The tester clicks on the create issue button
    And  inputs the issue details
    And  clicks the submit new issue button
    Then A issue will be created

    Scenario: Users can update issue details
      When Users click the update issue button
      And  inputs the updated issue details
      And  clicks the submit update issue button
      Then the issue details will be updated