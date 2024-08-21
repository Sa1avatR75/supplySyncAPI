Feature: Get Companies API
  As an API consumer
  I want to retrieve companies using the API
  So that I can view the list of companies or specific company details

  Background:
    Given supplySync base URL
    And I provide VALID authorization token


  Scenario: Successfully retrieve all companies
    When I send a GET request to retrieve all companies
    Then the response status code should be 200
    And the response should contain a list of companies
    And each company in the list should have a "company_id", "name", "address", "phone_number", and "email"
