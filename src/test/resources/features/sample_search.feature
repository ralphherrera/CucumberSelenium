Feature: Display relevant items in the search results page
  As a User
  I should be able to search for a service
  So that I can view related items in the search results page

  Scenario: Search for Service
    Given I access the phptravels site
    When I search for 'flights'
    And I entered dates from 'dd/mm/yyyy' to 'dd/mm/yyyy'
    Then I should see relevant search results