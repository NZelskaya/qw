package pl.b2b.net.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static pl.b2b.net.GlobalDefinitions.*;

public class DriverSetup {
    public static final String WEBDRIVER_RESOURCES = "c:\\tf\\tmp\\webdriver-resources\\";
    private static WebDriver driver;

    public static WebDriver createDriver(String browser) {
        try {
            if (driver == null) {
                BrowserEnum browserEnum = BrowserEnum.valueOf(browser);

                System.setProperty(browserEnum.getSystemProperty(), WEBDRIVER_RESOURCES + browserEnum.getExecutableFile());
                switch (browserEnum) {
                    case CHROME:
                        System.out.println("Launching Google Chrome ...");
                        driver = new ChromeDriver();
                        System.out.println("Browser opened");
                        break;

                    case FF:
                        System.out.println("Launching FireFox ...");
                        driver = new FirefoxDriver();
                        System.out.println("Browser opened");
                        break;

                    default:
                        System.out.println("Invalid browser getSelectorType specified.");
                        throw new IllegalArgumentException("NO BROWSER DEFINED");
                }

                driver.manage().window().maximize();
                driver.manage().deleteAllCookies();
                driver.manage().timeouts().implicitlyWait(DRIVER_IMPLICIT_TIMEOUT, TimeUnit.SECONDS);
                driver.manage().timeouts().pageLoadTimeout(DRIVER_EXPLICIT_TIMEOUT, TimeUnit.SECONDS);
            }

            return driver;
        } catch (Throwable e) {
            if (driver != null) {
                DriverSetup.close(driver);
            }
            throw e;
        }
    }

    public static void close(WebDriver driverInstance) {
        System.out.println("Closing browser ...");
        driverInstance.quit();
        driver = null;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void setDriver(String browser) {
        System.out.println("Driver was set to = " + browser);

        createDriver(browser);
    }
}