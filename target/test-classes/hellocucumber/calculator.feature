Feature: Simple Calculations
  Show all calculator functions work well
  Background:
    Given there is no history data available

  Scenario: Addition
    Given I have the numbers 3 and 2
    When I perform an addition with these numbers
    Then I receive the value 5
    And history data contains the calculation

  Scenario: Subtraction
    Given I have the numbers 3 and 2
    When I perform a subtraction with these numbers
    Then I receive the value 1
    And history data contains the calculation

  Scenario: Multiplication
    Given I have the numbers 3 and 2
    When I perform a multiplication with these numbers
    Then I receive the value 6
    And history data contains the calculation

  Scenario: Division
    Given I have the numbers 3 and 2
    When I perform a division with these numbers
    Then I receive the value 1.5
    And history data contains the calculation