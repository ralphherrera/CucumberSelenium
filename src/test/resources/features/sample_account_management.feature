@AccountManagement @sampleTest
Feature: View-Update user account information
  As a User
  I should be able to login my account
  So that I can view or update my personal information

  @sampleLogin
  Scenario: Successful Login
    Given I access the phptravels site
    And I navigate to the login page
    When I login using my account credentials
    Then I should be navigated to the accounts page

  @sampleLogin
  Scenario: Successful Invalid Login
    Given I access the phptravels site
    And I navigate to the login page
    When I login using invalid credentials
    Then I should not be navigated to the accounts page