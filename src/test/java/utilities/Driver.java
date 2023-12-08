package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class Driver {
    private Driver(){

    }
    private static InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>();
    public static WebDriver get(){
        //Ist im Thread noch kein WebDriver vorhanden -> in den Pool hinzufügen
        if (driverPool.get() == null) {
            //Browser wird entweder im Terminal mitgegeben oder aus der configurations.properties Datei ausgelesen
            String browser = System.getProperty("browser") != null ? browser = System.getProperty("browser") : ConfigurationReader.get("browser");
            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.setCapability("acceptInsecureCerts", true); // Ignoriere SSL-Zertifikatsfehler
                    driverPool.set(new ChromeDriver(chromeOptions));
                    break;
                case "chrome-headless":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions headlessChromeOptions = new ChromeOptions();
                    headlessChromeOptions.setHeadless(true);
                    headlessChromeOptions.setCapability("acceptInsecureCerts", true); // Ignoriere SSL-Zertifikatsfehler
                    driverPool.set(new ChromeDriver(headlessChromeOptions));
                    break;
                case "remote_chrome":
                    ChromeOptions remoteChromeOptions = new ChromeOptions();
                    remoteChromeOptions.setCapability("acceptInsecureCerts", true);
                    try {
                        driverPool.set(new RemoteWebDriver(new URL("http://10.118.21.131:4444/wd/hub"), remoteChromeOptions));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.setCapability("acceptInsecureCerts", true); // Ignoriere SSL-Zertifikatsfehler
                    driverPool.set(new FirefoxDriver(firefoxOptions));
                    break;
                case "firefox-headless":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions headlessFirefoxOptions = new FirefoxOptions();
                    headlessFirefoxOptions.setHeadless(true);
                    headlessFirefoxOptions.setCapability("acceptInsecureCerts", true); // Ignoriere SSL-Zertifikatsfehler
                    driverPool.set(new FirefoxDriver(headlessFirefoxOptions));
                    break;
                case "remote_firefox":
                    FirefoxOptions remoteFirefoxOptions = new FirefoxOptions();
                    remoteFirefoxOptions.setCapability("acceptInsecureCerts", true);
                    try {
                        driverPool.set(new RemoteWebDriver(new URL("http://10.118.21.131:4444/wd/hub"), remoteFirefoxOptions));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;

                case "edge":
                    if (!System.getProperty("os.name").toLowerCase().contains("windows"))
                        throw new WebDriverException("Your OS doesn't support Edge");
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.setCapability("acceptInsecureCerts", true); // Ignoriere SSL-Zertifikatsfehler
                    driverPool.set(new EdgeDriver(edgeOptions));
                    break;



            }
        }
        return driverPool.get();
    }
    public static void closeDriver() {
        driverPool.get().quit();
        driverPool.remove();
    }
}
