package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import utilities.WaitHelper;

import java.util.List;

public class SearchCustomerPage{
    public WebDriver ldriver;
    public WaitHelper waithelper;


    public SearchCustomerPage(WebDriver rdriver) {
        ldriver = rdriver;
        PageFactory.initElements(ldriver, this);
        waithelper = new WaitHelper(ldriver);
    }
    @FindBy(how = How.XPATH, using = "//input[@id='SearchEmail']")
    @CacheLookup
    WebElement txtEmail;

    @FindBy(how = How.XPATH, using = "//input[@id='SearchFirstName']")
    @CacheLookup
    WebElement txtFirstName;

    @FindBy(xpath = "//input[@id='SearchLastName'")
    WebElement txtLastName;

    @FindBy(how = How.XPATH, using = "//button[@id='search-customers']")
    @CacheLookup
    WebElement btnSearch;

    @FindBy(how = How.XPATH, using = "//table[@role='grid']")
    @CacheLookup
    WebElement tblSearchResults;

    @FindBy(how = How.XPATH, using = "//table[@id='customers-grid']")
    WebElement table;

    @FindBy(how = How.XPATH, using = "//table[@id='customers-grid']//tbody/tr")
    List<WebElement> tableRows;

    @FindBy(how = How.XPATH, using = "//table[@id='customers-grid']//tbody/tr/td")
    List<WebElement> tableColumns;
    public void setEmail(String email){
        waithelper.WaitForElement(txtEmail, 10);
        txtEmail.clear();
        txtEmail.sendKeys(email);
    }
    public void setFirstName(String fname) {
        waithelper.WaitForElement(txtEmail, 10);
        txtFirstName.clear();
        txtFirstName.sendKeys(fname);
    }

    public void setLastName(String lname) {
        waithelper.WaitForElement(txtEmail, 10);
        txtLastName.clear();
        txtLastName.sendKeys(lname);
    }
    public void clickSearch() {
        btnSearch.click();

    }

    public int getNoOfRows() {
        return (tableRows.size());
    }

    public int getNoOfColumns() {
        return (tableColumns.size());
    }
    public boolean searchCustomerByEmail(String email) {
        boolean flag = false;

        for (int i = 1; i <= getNoOfRows(); i++) {
            String emailid = table.findElement(By.xpath("//table[@id='customers-grid']/tbody/tr[" + i + "]/td[2]"))
                    .getText();
            System.out.println(emailid);
            if (emailid.equals(email)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
    public boolean searchCustomerByName(String Name) {
        boolean flag = false;

        for (int i = 1; i <= getNoOfRows(); i++) {
            String name = table.findElement(By.xpath("//table[@id='customers-grid']/tbody/tr[" + i + "]/td[3]"))
                    .getText();

            if (Name.equals(name)) {
                flag = true;
                break;
            }

        }

        return flag;

    }
}
