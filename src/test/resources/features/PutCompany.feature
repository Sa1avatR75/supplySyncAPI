@RuslanFeature @PutCompany

Feature: Update Company API
  As an API consumer
  I want to update company details using the API
  So that I can maintain accurate and up-to-date information about companies

  Background:
    Given supplySync base URL
    And I provide VALID authorization token


  Scenario: Successfully update a company's details
    Given a company exists with ID "194"
    And I update the company's name to "R&Z Corp", address to "4209 Chesapeake Dr", phone number to "777 777 7777", and email to "ruslan21@gmai.com"
    When I send a PUT request to update the company with ID "194"
    Then the response status code should be 200
    And the response should contain the updated name "R&Z Corp"
    And the response should contain the updated address "4209 Chesapeake Dr"
    And the response should contain the updated phone number "777 777 7777"
    And the response should contain the updated email "ruslan21@gmai.com"

  Scenario: Fail to update a company with invalid email format
    Given a company exists with ID "194"
    When I send a PUT request to update the company with ID "194"
    And I update the company's email to "zairovgmail.com"
    Then the response status code should be 400

  Scenario: Fail to update a non-existent company
    And I update the company's name to "R&Z Corp", address to "4209 Chesapeake Dr", phone number to "777 777 7777", and email to "ruslan21@gmai.com"
    When I send a PUT request to update the company with ID "9999"
    Then the response status code should be 404

  Scenario: Fail to update a company without authentication
    Given I do not have a valid authentication token
    When I send a PUT request to update the company with ID "178"
    And I update the company's name to "Unauthorized Corp", address to "789 Unauthorized St", phone number to "555-0000", and email to "unauth@example.com"
    Then the response status code should be 401
