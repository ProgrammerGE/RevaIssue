Feature: A user needs to search for issues
  A user of our application can use the search bar functionality
  to display a list of issues matching their search. Upon clicking on an
  issue from the result, the user will then be redirected to the appropriate
  project page.

  Scenario: A user is searching for an issue with the intent of going to its project page
    Given The user is logged in
    Then  The user can search for an issue
#    And The user types text containing a keyword of their issue
#    And The user clicks on the result
#    Then The user gets redirected to the issues project page