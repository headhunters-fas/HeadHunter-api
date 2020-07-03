#end to end testing step definitions
  Feature: post user

    Scenario Outline: as a user I want to register into the application
      Given I can register
      And I send a post to create an account with username <username>, password <password> and confirmPassword <confirmPassword>
      Then I should be able to login into the application

      Examples:
      | username| password| confirmPassword|
      | admin@gmail.com| testing321| testing321|