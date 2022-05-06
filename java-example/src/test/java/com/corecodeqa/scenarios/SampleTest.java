package com.corecodeqa.scenarios;

import com.corecodeqa.dataproviders.LoginData;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static java.util.concurrent.TimeUnit.SECONDS;

public class SampleTest {
    private WebDriver driver;
    private static final String DEFAULT_BROWSER = "CHROME";
    private static final String BASE_URL_INVENTORY_SYSTEM = "https://corecode-qa-bootcamp-site.herokuapp.com/login.php";
    private static final String USERNAME_SELECTOR_BY_NAME = "user_name";
    private static final String PASSWORD_SELECTOR_BY_NAME = "user_password";

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        // GET ENV VARIABLE FOR BROWSER
        String browserSelected = System.getenv("BROWSER");

        if ((browserSelected == null)) {
            browserSelected = DEFAULT_BROWSER;
        }

        // CHOOSE BROWSER DRIVER
        switch (browserSelected) {
            case "CHROME":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "FIREFOX":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                driver.manage().window().maximize();
                break;
            case "H-CHROME":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(chromeOptions);
                break;
            case "H-FIREFOX":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setHeadless(true);
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(firefoxOptions);
                break;
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }

        // FULL SCREEN MODE
        driver.manage().window().maximize();
    }

    @Test(groups = {"basic"})
    public void Seleniumtest1() {
        System.out.println("In test 1");
        driver.get("http://google.com");
        final String expectedPageTitle = "Google";
        Assert.assertTrue(driver.getTitle().contains(expectedPageTitle), "Test Failed");
    }

    @Test(dataProvider = "data-provider-login", dataProviderClass = LoginData.class, groups = {"intermediate"})
    public void Seleniumtest2_LoginCases_InventorySystem(final String username,
                                                         final String password) throws InterruptedException {
        System.out.println("In test 2");
        System.out.println("UserName : " + username);
        System.out.println("Password : " + password);
        driver.get(BASE_URL_INVENTORY_SYSTEM);
        // Clear email field
        driver.findElement(By.name(USERNAME_SELECTOR_BY_NAME)).clear();
        // Enter username
        driver.findElement(By.name(USERNAME_SELECTOR_BY_NAME)).sendKeys(username);
        // Custom sleep
        SECONDS.sleep(2);
        // Clear password field
        driver.findElement(By.name(PASSWORD_SELECTOR_BY_NAME)).clear();
        // Enter password
        driver.findElement(By.name(PASSWORD_SELECTOR_BY_NAME)).sendKeys(password);
        // Click submit
        driver.findElement(By.name(PASSWORD_SELECTOR_BY_NAME)).sendKeys(Keys.RETURN);
        // Custom sleep
        SECONDS.sleep(2);
        // For cleaning browser purposes
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }
}
