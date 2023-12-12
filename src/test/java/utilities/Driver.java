package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
public class Driver {
    private Driver(){

    }
    private static InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>();
    public static WebDriver get(){
        //If no WebDriver is present in the thread -> add it to the pool.
        if (driverPool.get() == null) {
            //If browser value is given in terminal window then set that value in variable browser otherwiese set the value from configurations.properties file
            String browser = System.getProperty("browser") != null ? browser = System.getProperty("browser") : ConfigurationReader.get("browser");
            switch (browser) {
                case "chrome": // chrome option runs the script locally on your system
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.setCapability("acceptInsecureCerts", true); // Ignoriere SSL-Zertifikatsfehler
                    driverPool.set(new ChromeDriver(chromeOptions));
                    break;
                case "chrome-headless": // chrome-headless option runs the script in headless mode locally on your system
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions headlessChromeOptions = new ChromeOptions();
                    headlessChromeOptions.setHeadless(true);
                    headlessChromeOptions.setCapability("acceptInsecureCerts", true); // Ignoriere SSL-Zertifikatsfehler
                    driverPool.set(new ChromeDriver(headlessChromeOptions));
                    break;
                case "remote_chrome": // romote_chrome option runs the script on chrome browser in selenium grid (url below)
                    ChromeOptions remoteChromeOptions = new ChromeOptions();
                    remoteChromeOptions.setCapability("acceptInsecureCerts", true);
                    try {
                        driverPool.set(new RemoteWebDriver(new URL("http://10.118.21.131:4444/wd/hub"), remoteChromeOptions));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "firefox": // firefox option runs script on firefox browser on your local machine
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.setCapability("acceptInsecureCerts", true); // Ignoriere SSL-Zertifikatsfehler
                    driverPool.set(new FirefoxDriver(firefoxOptions));
                    break;
                case "firefox-headless": // firefox option runs script on firefox browser in headless mode on your local machine
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions headlessFirefoxOptions = new FirefoxOptions();
                    headlessFirefoxOptions.setHeadless(true);
                    headlessFirefoxOptions.setCapability("acceptInsecureCerts", true); // Ignoriere SSL-Zertifikatsfehler
                    driverPool.set(new FirefoxDriver(headlessFirefoxOptions));
                    break;
                case "remote_firefox": // firefox option runs script in firefox browser on selenium grid (see url below)
                    FirefoxOptions remoteFirefoxOptions = new FirefoxOptions();
                    remoteFirefoxOptions.setCapability("acceptInsecureCerts", true);
                    try {
                        driverPool.set(new RemoteWebDriver(new URL("http://10.118.21.131:4444/wd/hub"), remoteFirefoxOptions));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
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
