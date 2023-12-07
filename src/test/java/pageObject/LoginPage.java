package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage{
    public WebDriver driver;
    ThreadLocal<RemoteWebDriver> driverPool = new ThreadLocal<>();

    public LoginPage(WebDriver rdriver){
        driver=rdriver;
        PageFactory.initElements(rdriver,this);
    }
    @FindBy(id = "Email")
    WebElement txtEmail;
    @FindBy(id = "Password")
    WebElement txtPassword;
    @FindBy(xpath = "//button[@type='submit']")
    WebElement btnLogin;
    @FindBy(xpath = "//a[.='Logout']")
    WebElement linkLogout;
    public void setUserName(String uName){
        txtEmail.clear();
        txtEmail.sendKeys(uName);
    }
    public void setPassword(String pwd){
        txtPassword.clear();
        txtPassword.sendKeys(pwd);
    }
    public void clickLogin(){
        btnLogin.click();
    }
    public void clickLogout(){
        linkLogout.click();
    }


}
