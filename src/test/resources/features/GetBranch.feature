@SalavatFeature @GetBranch
Feature: Branches functionality

  Background:
    Given sypplySync base URL


    Scenario: verify id of all Branches are more than 0
      When I provide VALID authorization token for branches
      And I hit GET endpoint "/api/v1/branches"
      Then I should receive a 200 OK status code
      And verify id of all branches is not NULL

    Scenario: Successfully get all  Branches
    When I provide VALID authorization token for branches
    And I hit GET endpoint "/api/v1/branches"
    Then I should receive a 200 OK status code


    Scenario: Fail to get all Branches without authentication
      When I provide INVALID authorization token for branches
      And I hit GET endpoint "/api/v1/branches"
      Then I should receive a 401 UNAUTHORIZED status code

    Scenario: Successfully get all Branches is not locked
      When I provide VALID authorization token for branches
      And I hit GET endpoint "/api/v1/branches/notBlock"
      Then I should receive a 200 OK status code
      And verify each branch is not blocked



#    @GET
#    Scenario: Successfully get count of branches each company
#      When I provide VALID authorization token for branches
#      When I hit a GET endpoint "/api/v1/company"
#      Then I should receive a 200 OK status code
#      And get id of each company
#      Then I git GET endpoint "/api/v1/branches/count/branches"
#      Then I should receive a 200 OK status code
#      And verify each company has branches









