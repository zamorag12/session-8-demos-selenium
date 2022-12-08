package com.corecodeqa.scenarios;

import com.corecodeqa.dataproviders.LoginData;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import static java.util.concurrent.TimeUnit.SECONDS;

public class SampleTestGrid {
    private WebDriver driver;
    private static final String DEFAULT_BROWSER = "CHROME";
    private static final String BASE_URL_INVENTORY_SYSTEM = "https://thenuxcrew.com/corecode/login.php";
    private static final String USERNAME_SELECTOR_BY_NAME = "user_name";
    private static final String PASSWORD_SELECTOR_BY_NAME = "user_password";

    @BeforeMethod
    public void setup(Method method) throws MalformedURLException {
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
        sauceOptions.setCapability("name", method.getName());
        sauceOptions.setCapability("browserVersion", "latest");

        ChromeOptions options = new ChromeOptions();
        options.setCapability("sauce:options", sauceOptions);
        URL url = new URL("https://ondemand.us-west-1.saucelabs.com/wd/hub");

        driver = new RemoteWebDriver(url, options);
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
