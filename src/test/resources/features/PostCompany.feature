@RuslanFeature @PostCompany
  Feature: Companies Functionalities
    As an API consumer
    I want to create companies using the API
    So that I can manage companies in the system

    Background:
      Given supplySync base URL
      And I provide VALID authorization token



    Scenario: Successfully create a company
      When I send a POST request to create a company with name "R&Z inc.", address "1250 Lily Cache Ln", phone number "555 555 5555", and email "zairov21@gmail.com"
      Then the response status code should be 200
      And the response should contain the name "R&Z inc."
      And the response should contain the address "1250 Lily Cache Ln"
      And the response should contain the phone number "555 555 5555"
      And the response should contain the email "zairov21@gmail.com"
      And the response should contain a id field

    Scenario: Successfully create a company with optional fields
      When I send a POST request to create a company with logo "3197941417111301103421350154357.png",  name "R&Z inc.", address "1250 Lily Cache Ln", phone number "555 555 5555", and email "zairov21@gmail.com"
      Then the response status code should be 200
      And the response should contain the logo "3197941417111301103421350154357.png"
      And the response should contain the name "R&Z inc."
      And the response should contain the address "1250 Lily Cache Ln"
      And the response should contain the phone number "555 555 5555"
      And the response should contain the email "zairov21@gmail.com"
      And the response should contain a id field


    Scenario: Fail to create a company with invalid email
      When I send a POST request to create a company with name "R&Z inc.", address "1250 Lily Cache Ln", phone number "555 555 5555", and wrongEmail "zairov21gmail.com"
      Then the response status code should be 400

    Scenario: Fail to create a company without authentication
      Given I do not have a valid authentication token
      When I send a POST request to create a company with name "R&Z inc.", address "1250 Lily Cache Ln", phone number "555 555 5555", and email "zairov21@gmail.com"
      Then the response status code should be 401


    Scenario: Fail to create a company with missing required fields
      When I send a POST request to create a company with address "1250 Lily Cache Ln", phone number "555 555 5555", and email "zairov21@gmail.com"
      Then the response status code should be 400
