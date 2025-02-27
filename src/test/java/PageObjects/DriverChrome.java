package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.Arrays;
import java.util.Random;


public class DriverChrome {
    static WebDriver driver;

    public static void openBrowser() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver","C:\\Chrome Driver Selenium\\chromedriver_133.0.6943.126.exe");
        ChromeOptions options = new ChromeOptions();
        // Bypass Cloudflare checks
        //options.addArguments("--proxy-server=http://200.106.165.81:999");
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

        // Obtiene acceso al DevTools
//        DevTools devTools = ((ChromeDriver) driver).getDevTools();
//        devTools.createSession();

        // Habilita las herramientas de red y borra caché y cookies
//        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
//        devTools.send(Network.clearBrowserCache());
//        devTools.send(Network.clearBrowserCookies());

        driver.get("https://www.google.com");
    }

    private static void randomPause() throws InterruptedException {
        Random random = new Random();
        int delay = 2000 + random.nextInt(3000); // Entre 2 y 5 segundos
        Thread.sleep(delay);
    }
}