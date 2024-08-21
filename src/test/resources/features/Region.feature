Feature: Region API functionalities

@AbduvohidFeature
  Scenario: Successfully create a new region
    Given base url for SupplySync backend
    When I provide valid authorization token
    And I provide valid id and region as a request body
    And I hit POST endpoint