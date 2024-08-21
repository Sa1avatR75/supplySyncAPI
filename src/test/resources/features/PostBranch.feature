@SalavatFeature @PostBranch
Feature: Branches functionality
  Background:
    Given sypplySync base URL



  Scenario:  Successfully create a Branch
    When I provide VALID authorization token for branches
    And I provide request body name "branc3h11", email "emai2l@gmail.com", address "address 123", phone number "3023907831", regionId 1, companyId 1
    And I hit POST branch endpoint "/api/v1/branches"
    Then I should receive a 200 OK status code
    And the response should contains the name "branc3h11"
    And the response should contains the email "emai2l@gmail.com"
    And the response should contains the address "address 123"
    And the response should contains the phone number "3023907831"


  Scenario: Fail to create a Branch without authentication
    When I provide INVALID authorization token for branches
    And I provide request body name "branc3h11", email "emai2l@gmail.com", address "address 123", phone number "3023907831", regionId 1, companyId 1
    And I hit POST branch endpoint "/api/v1/branches"
    Then I should receive a 401 UNAUTHORIZED status code
