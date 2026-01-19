Feature: A user needs to search for issues
  A user of our application can use the search bar functionality
  to display a list of issues matching their search. Upon clicking on an
  issue from the result, the user will then be redirected to the appropriate
  project page.

  Scenario: A user is searching for an issue with the intent of going to its project page
    Given The user is logged into the hubpage
    When  The user searches for an issue
    And   The user clicks on a result
    Then  The user is sent to the issue's project page