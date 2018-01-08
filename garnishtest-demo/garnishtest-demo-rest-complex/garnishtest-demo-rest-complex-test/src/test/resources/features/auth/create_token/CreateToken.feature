Feature: Create token

  Background:
    Given the resource files prefix '/features/auth/create_token/data/'


  Scenario: Get user
    Given the database state from 'create-token-initial.sql'
    And   I am logged in as 'test_user'
    When  I call 'GET' on '/users/current' without body
    Then  the response code should be 200
    And   the response body should be like in the JSON file 'create-token-expected.http.json'
