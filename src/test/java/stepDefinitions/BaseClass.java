package stepDefinitions;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import pageObject.AddCustomerPage;
import pageObject.LoginPage;
import pageObject.SearchCustomerPage;
import java.util.concurrent.atomic.AtomicReference;


public class BaseClass {
    public WebDriver driver= null; // declare driver globally
    public LoginPage pageLogin;  // declare loginPage object instance
    public AddCustomerPage pageAddCustomer; // declare AddCustomer object instance
    public SearchCustomerPage pageSearchCust;  // declare SearchCustomerpage object instance
    public Logger logger; // logger variable declaration




    // creating random string for unique email id
    public static String randomString(){
        AtomicReference<String> generatedString = new AtomicReference<>(RandomStringUtils.randomAlphabetic(5));
        return (generatedString.get());
    }


}
