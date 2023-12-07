package stepDefinitions;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import pageObject.AddCustomerPage;
import pageObject.LoginPage;
import pageObject.SearchCustomerPage;
import utilities.ReadPropertiesValue;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class Steps extends BaseClass {
    @Before //Before annotation is from io.cucumber.java
    public void setup() throws IOException {
        logger = LogManager.getLogger(BaseClass.class); // initializing logger to call different log levels
        // Launch browser
        browser ="Firefox";
        //if (ReadPropertiesValue.getBrowser().equals("Chrome")) {
        if (browser.equals("Chrome")) {
            WebDriverManager.chromedriver().setup();
           // System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
           // io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
          //  io.github.bonigarcia.wdm.WebDriverManager.chromiumdriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headed");
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            logger.info("Chrome browser launched successfully");

        } //else if (ReadPropertiesValue.getBrowser().equals("Firefox")) {

        else if (browser.equals("Firefox")) {
//            WebDriverManager.firefoxdriver().setup();
//            FirefoxOptions options = new FirefoxOptions();
//            options.addArguments("--headless");
//            driver = new FirefoxDriver(options);
//            driver.manage().window().maximize();
//            logger.info("Firefox browser launched successfully");
            FirefoxOptions remoteFirefoxOptions = new FirefoxOptions();
            remoteFirefoxOptions.setCapability("acceptInsecureCerts", true);
            try {
                remoteFirefoxOptions.addArguments("--headed");
                driverPool.set(new RemoteWebDriver(new URL("http://10.118.21.131:4444/wd/hub"), remoteFirefoxOptions));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }



        } //else if (ReadPropertiesValue.getBrowser().equals("Edge")) {
        else if (browser.equals("Edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
            driver.manage().window().maximize();
            logger.info("Edge browser launched successfully");
        }


    }
    @Given("User launch chrome browser")
    public void user_launch_chrome_browser() {
        pageLogin =new LoginPage(driverPool.get()); // passing the driver in LoginPage constructor to launch Chrome browser
    }
    @When("User opens URL {string}")
    public void user_opens_url(String url) {
        //logger.info("*********** opening url ************");
        //driver.get(url); // this url as comes direct from feature file
        driverPool.get().navigate().to(url);

    }
    @When("User enters Email as {string} and Password as {string}") //username and password as parameter
    public void user_enters_email_as_and_password_as(String user_name, String password) {
        //logger.info("*********** providing email and password ************");
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
        Thread.sleep(3000);

    }

   /* Find customer by first and last name*/
    @When("Enter customer FirstName")
    public void enterCustomerFirstName() throws InterruptedException {

        logger.info("*********** Searching customer by name ************");
        pageSearchCust = new SearchCustomerPage(driver);
        pageSearchCust.setFirstName("Victoria");
    }

    @And("Enter customer LastName")
    public void enterCustomerLastName() {

        pageSearchCust.setLastName("Terces");
    }

    @Then("User should find Name in the search result table")
    public void userShouldFindNameInTheSearchResultTable() {
        boolean status=pageSearchCust.searchCustomerByName(" Victoria");
        Assert.assertTrue(status);
        logger.info("*********** Provided name found in result table ************");

    }
    @And("close browser")
    public void closeBrowser() {
//        logger.info("*********** Closing Browser ************");
//        driver.close();
        driverPool.get().quit();
    }
}
