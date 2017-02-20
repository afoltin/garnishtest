Feature: Create user

  In order to be able to authenticate and use the API
  As an API client
  I want to create an user

  Background:
    Given the resource files prefix '/features/auth/create_user/data/'


  Scenario: Create one user
    Given a clean database
    And   I am not logged-in
    And   http mocks from 'create-user-googleGeocode.http-mocks.json'
    When  I call 'POST' on '/users' with JSON from 'create-user-request.http.json'
    Then  the response code should be 200
    And   the response body should be like in the JSON file 'create-user-expected.http.json'
    And   the database state should be like in 'create-user-expected.db.xml'
