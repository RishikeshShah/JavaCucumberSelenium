Feature: Customers
  # common steps for login are written in Background so it runs before executing every scenario
  Background:
    Given User launch browser
    When User opens URL "https://admin-demo.nopcommerce.com/login"
    And  User enters Email as "admin@yourstore.com" and Password as "admin"
    And Click on Login
    Then User can view Dashboad

@sanity
  Scenario: Add new Customer
    When User click on customers Menu
    And click on customers Menu Item
    And click on Add new button
    Then User can view Add new customer page
    When User enter customer info
    And click on Save button
    Then User can view confirmation message "The new customer has been added successfully."
    Then close browser
@sanity
  Scenario: Search Customer by EMailID
    When User click on customers Menu
    And click on customers Menu Item
    And Enter customer EMail
    When Click on search button
    Then User should find Email in the search result table
    And close browser

  @regression
  Scenario: Search Customer by Name
    When User click on customers Menu
    And click on customers Menu Item
    And Enter customer FirstName
    #And Enter customer LastName
    When Click on search button
    #Then User should find Name in the search result table
    And close browser
