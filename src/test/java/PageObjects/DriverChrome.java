package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Arrays;
import java.util.Random;


public class DriverChrome {
    static WebDriver driver;

    public static void openBrowser() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Chrome Driver Selenium\\chromedriver_137.0.7151.69.exe");
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-blink-features");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.6834.111 Safari/537.36");
        options.setExperimentalOption("excludeSwitches", Arrays.asList("disable-popup-blocking"));
        options.addArguments("--disable-geolocation"); // Deshabilita la geolocalización
        options.addArguments("--disable-popup-blocking"); // Deshabilita ventanas emergentes
        options.addArguments("--disable-notifications"); // Desactiva notificaciones
        options.addArguments("--disable-web-security");
        options.addArguments("--disable-extensions");
        options.addArguments("--user-data-dir=C:\\Users\\uchih\\chrome_profile");
        options.addArguments("start-maximized");

        driver = new ChromeDriver(options);
        driver.get("https://www.google.com");
    }


}