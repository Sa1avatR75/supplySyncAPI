@SalavatFeature @PutBranch
Feature: Branches functionality
  Background:
    Given sypplySync base URL

    Scenario: Successfully BLOCK branch by Id
      When I provide VALID authorization token for branches
      And I hit GET endpoint "/api/v1/branches/" with id 123
      Then I should receive a 200 OK status code
      And status of branch should be false
      And I hit PUT branch endpoint "/api/v1/branches/block/" with id 123
      Then I should receive a 200 OK status code
      And the response should contains the block  is true

  Scenario: Successfully UNBLOCK branch by Id
    When I provide VALID authorization token for branches
    And I hit GET endpoint "/api/v1/branches/" with id 123
    Then I should receive a 200 OK status code
    And status of branch should be true
    And I hit PUT branch endpoint "/api/v1/branches/unblock/" with id 123
    Then I should receive a 200 OK status code
    And the response should contains the block false



