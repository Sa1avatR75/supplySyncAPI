@Salavat
Feature: Branches functionality

  Background:
    Given sypplySync base URL

    Scenario: verify id of all users are more than 0
      When I provide VALID authorization token
      And I hit GET endpoint "/api/v1/branches"
      Then I should receive a 200 OK status code
      And verify id of all branches is not NULL

    Scenario: verify branch can be created
      When I provide VALID authorization token
      And I provide request body name "name", email "email", address "address", phone number "phone number", regionId 9, companyId 3
      Then I should receive a 200 OK status code
      And the response should contains the name "name"
      And the response should contains the email "name"
      And the response should contains the address "name"
      And the response should contains the phone number "name"
      And the response should contains the regionId 9
      And the response should contains the companyId 3





