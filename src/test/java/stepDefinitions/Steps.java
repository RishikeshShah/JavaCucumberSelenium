package stepDefinitions;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import pageObject.AddCustomerPage;
import pageObject.LoginPage;
import pageObject.SearchCustomerPage;
import utilities.Driver;
import java.io.IOException;



public class Steps extends BaseClass {
    @Before //Before annotation is from io.cucumber.java
    public void setup() throws IOException {
        driver = Driver.get();  // initializing driver
        logger= LogManager.getLogger(BaseClass.class);  //initializing logger
    }

    @Given("User launch browser")
    public void userLaunchBrowser() {
        logger.info("*********** launching browser ************");
        pageLogin =new LoginPage(driver); // passing the driver in LoginPage constructor to launch Chrome browser
        driver.manage().window().maximize();
    }

    @When("User opens URL {string}")
    public void user_opens_url(String url) {
        logger.info("*********** opening url ************");
        driver.get(url); // this url as comes direct from feature file

    }
    @When("User enters Email as {string} and Password as {string}") //username and password as parameter
    public void user_enters_email_as_and_password_as(String user_name, String password) {
        logger.info("*********** providing email and password ************");
        pageLogin.setUserName(user_name); // user_name=admin@yourstore.com is comming from feature file
        pageLogin.setPassword(password); // password=admin@yourstore.com is comming from feature file
    }
    @When("Click on Login")
    public void click_on_login() {
        logger.info("*********** Clicking on login button ************");
        pageLogin.clickLogin();
    }
    @When("Page title should be {string}")
    public void page_title_should_be(String title) throws InterruptedException {
        Thread.sleep(3000);
        // if user name or password is not correct the login will fail
        if (driver.getPageSource().contains("Login was unsuccessful.")){
            logger.error("*********** Login failed. Provide correct email and password ************");
            driver.close();
            Assert.fail();
        }
        else{
            logger.info("*********** Login Successful ************");
            // verifying the title
            Assert.assertEquals(title, driver.getTitle());
        }
   }

    @When("User click on Logout link")
    public void user_click_on_logout_link() throws InterruptedException {
        logger.info("*********** Click logout ************");
        pageLogin.clickLogout();
     Thread.sleep(3000);
    }

    /* Customer feature steps*/

    @Then("User can view Dashboad")
    public void userCanViewDashboad() {
        pageAddCustomer = new AddCustomerPage(driver);
        Assert.assertEquals("Dashboard / nopCommerce administration",pageAddCustomer.getPageTitle());
    }

    @When("User click on customers Menu")
    public void userClickOnCustomersMenu() throws InterruptedException {
        Thread.sleep(2000);
        pageAddCustomer.clickOnCustomersMenu();
    }

    @And("click on customers Menu Item")
    public void clickOnCustomersMenuItem() throws InterruptedException {
        Thread.sleep(2000);
        pageAddCustomer.clickOnCustomersMenuItem();
    }

    @And("click on Add new button")
    public void clickOnAddNewButton() {
        pageAddCustomer.clickOnAddnew();
    }
    @Then("User can view Add new customer page")
    public void user_can_view_add_new_customer_page() {
        Assert.assertEquals("Add a new customer / nopCommerce administration", pageAddCustomer.getPageTitle());
    }

    @When("User enter customer info")
    public void userEnterCustomerInfo() throws InterruptedException {
        logger.info("*********** entering new customer info ************");
        String email = randomString() + "@gmail.com";
        pageAddCustomer.setEmail(email);
        pageAddCustomer.setPassword("test123");
        // Registered - default
        // The customer cannot be in both 'Guests' and 'Registered' customer roles
        // Add the customer to 'Guests' or 'Registered' customer role
        pageAddCustomer.setCustomerRoles("Guest");
        Thread.sleep(3000);

        pageAddCustomer.setManagerOfVendor("Vendor 2");
        pageAddCustomer.setGender("Male");
        pageAddCustomer.setFirstName("Tester");
        pageAddCustomer.setLastName("Automation");
        pageAddCustomer.setDob("7/05/1985"); // Format: D/MM/YYY
        pageAddCustomer.setCompanyName("busyQA");
        pageAddCustomer.setAdminContent("This is for testing.........");
    }

    @And("click on Save button")
    public void clickOnSaveButton() {
        logger.info("*********** Saving customer data ************");
        pageAddCustomer.clickOnSave();
    }
    @Then("User can view confirmation message {string}")
    public void userCanViewConfirmationMessage(String msg) {
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText()
                .contains("The new customer has been added successfully"));
    }


    /* Search customer by EmailID steps implementation*/
    @When("Enter customer EMail")
    public void enterCustomerEMail() {
        logger.info("*********** Searching Customer by Email ************");
        pageSearchCust = new SearchCustomerPage(driver);
        pageSearchCust.setEmail("victoria_victoria@nopCommerce.com");
    }

    @When("Click on search button")
    public void clickOnSearchButton() {
        pageSearchCust.clickSearch();

    }

    @Then("User should find Email in the search result table")
    public void userShouldFindEmailInTheSearchResultTable() throws InterruptedException {
        boolean status = pageSearchCust.searchCustomerByEmail("victoria_victoria@nopCommerce.com");
        Assert.assertTrue(status);
        Thread.sleep(4000);

    }

   /* Find customer by first and last name*/
    @When("Enter customer FirstName")
    public void enterCustomerFirstName() throws InterruptedException {
        pageSearchCust = new SearchCustomerPage(driver);
        pageSearchCust.setFirstName("Victoria");
        logger.info("*********** Entering first name ************");
    }

    @And("Enter customer LastName")
    public void enterCustomerLastName() {

        pageSearchCust.setLastName("Terces");
        logger.info("*********** Entering last name ************");

    }

    @Then("User should find Name in the search result table")
    public void userShouldFindNameInTheSearchResultTable() {
        boolean status=pageSearchCust.searchCustomerByName(" Victoria");
        Assert.assertTrue(status);
        logger.info("*********** Provided name found in result table ************");

    }
    @And("close browser")
    public void closeBrowser() {
          Driver.closeDriver();
        logger.info("*********** closing all the browser ************");
    }

}
