Feature: Simple addition test

  Background:
    Given the resource files prefix '/features/calc/data/'

  Scenario: I want to add 2 numbers
    When I call 'GET' on '/calc/add/1/1' without body
    Then the response code should be 200
    And the response body should be like in the file 'addResult.txt'

  @ignore
  Scenario: I want adding 2 numbers to fail
    When I call 'GET' on '/calc/add/1/2' without body
    Then the response code should be 200
    And the response body should be like in the file 'addResult.txt'