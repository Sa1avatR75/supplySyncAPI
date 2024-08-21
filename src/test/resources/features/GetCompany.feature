@RuslanFeature @GetCompany

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
    And each company emails contains "@"

  Scenario: Successfully retrieve a specific company by ID
    When I send a GET request to retrieve a company with ID "177"
    Then the response status code should be 200
    And the response should contain the company with ID "177"
    And the response should contain the name "R&Z inc."
    And the response should contain the address "1250 Lily Cache Ln"
    And the response should contain the phone number "555 555 5555"
    And the response should contain the email "zairov21@gmail.com"


  Scenario: Fail to retrieve a company with a non-existent ID
    When I send a GET request to retrieve a company with ID "99999"
    Then the response status code should be 404



  Scenario: Fail to retrieve companies without authentication
    Given I do not have a valid authentication token
    When I send a GET request to retrieve all companies
    Then the response status code should be 401
