package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class DriverChrome {
    static WebDriver driver;
    public static void openBrowser() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver","C:\\chrome\\driver\\chromedriver_64.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.get("http://www.google.com");
    }
}
