@SalavatFeature @DeleteBranch
Feature: Branches functionality
  Background:
    Given sypplySync base URL



  Scenario: Successfully delete Branch
    When I provide VALID authorization token for branches
    And I hit DELETE endpoint "/api/v1/branches/" with Branch id 10
    Then I should receive a 200 OK status code