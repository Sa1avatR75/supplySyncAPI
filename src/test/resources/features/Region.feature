Feature: Region API functionalities

@AbduvohidFeature
  Scenario: Successfully create a new region
    Given base url for SupplySync backend
    When I provide valid authorization token
    And I provide valid id and region as a request body
    And I hit POST endpoint "/api/v1/regions"
    Then I verify the status code is 200

  @AbduvohidFeature
  Scenario: Successfully update region
    Given base url for SupplySync backend
    When I provide valid authorization token
    And I provide valid id and region as a request body
    And I hit POST endpoint "/api/v1/regions"
    And I provide id 12 and updated "Nukus"
    And I hit PUT endpoint "/api/v1/regions"
    Then I verify the status code is 201


  @AbduvohidFeature
  Scenario: Check if regions list contain new created region
    Given base url for SupplySync backend
    When I provide valid authorization token
    And I provide valid id and region as a request body
    And I hit POST endpoint "/api/v1/regions" to create region
    Then I verify the status code is 200
    Then I hit GET endpoint "/api/v1/regions" to get list of regions
    Then I verify the status code is 200
    Then I verify that regions list contains new created region

  @AbduvohidFeature
  Scenario: Successfully delete region
    Given base url for SupplySync backend
    When I provide valid authorization token
    And I provide valid id and region as a request body
    And I hit POST endpoint "/api/v1/regions"
    Then I verify the status code is 200
    And I get id of created region
    And I hit DELETE endpoint "/api/v1/regions/" with valid id
    Then I verify the status code is 200
    Then I verify message contains "successfully deleted"









