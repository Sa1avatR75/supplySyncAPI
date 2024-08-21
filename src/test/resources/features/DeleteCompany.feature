@RuslanFeature @DeleteCompany

Feature: Delete Company API
  As an API consumer
  I want to delete a company using the API
  So that I can remove companies that are no longer needed from the system

  Background:
    Given supplySync base URL
    And I provide VALID authorization token

  Scenario: Successfully delete a company
    Given a company exists with ID "182"
    When I send a DELETE request to delete the company with ID "182"
    Then the response status code should be 200


  Scenario: Fail to delete a non-existent company
    When I send a DELETE request to delete the company with ID "99999"
    Then the response status code should be 404


  Scenario: Fail to delete a company without authentication
    Given I do not have a valid authentication token
    When I send a DELETE request to delete the company with ID "12345"
    Then the response status code should be 401


