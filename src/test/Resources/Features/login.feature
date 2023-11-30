Feature: Login


  @system_test  # with this tag we can run only this scenario from TestRunner class
  Scenario: Successful Login with valid credentials
    Given User launch chrome browser
    When User opens URL "https://admin-demo.nopcommerce.com/login"
    And  User enters Email as "admin@yourstore.com" and Password as "admin"
    And Click on Login
    And Page title should be "Dashboard / nopCommerce administration"
    When User click on Logout link
    And Page title should be "Your store. Login"
    Then close browser

  @regression # with this tag only Login Data Driven scenario can be triggered
  Scenario Outline: Login Data Driven
    Given User launch chrome browser
    When User opens URL "https://admin-demo.nopcommerce.com/login"
    And  User enters Email as "<email>" and Password as "<password>"
    And Click on Login
    And Page title should be "Dashboard / nopCommerce administration"
    When User click on Logout link
    And Page title should be "Your store. Login"
    Then close browser
    Examples:
      | email                 | password |
      | admin@yourstore.com   | admin    |
      | adminFalse@syours.com | admin    |
      | admin@yourstore.com   | wrong    |

