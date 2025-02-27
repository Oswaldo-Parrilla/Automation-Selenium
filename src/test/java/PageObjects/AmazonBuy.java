package PageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.Properties;


public class AmazonBuy extends DriverChrome {

    //static By clickElement = By.xpath("");
    static WebElement elementSearch = driver.findElement(By.xpath("//textarea[@id='APjFqb']"));

    public static WebElement elementLink(String namePage) {
        WebElement linkbutton = driver.findElement(By.xpath("//h3[normalize-space(text())='" + namePage + "']"));
        //h3[contains(text(),'Chedraui — Tu supermercado en línea')]
        return linkbutton;
    }

    public static void getAmazonPage(String namePage) throws InterruptedException {
        elementSearch.sendKeys("Amazon");
        elementSearch.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
//        driver.switchTo().frame(0);
//        WebElement checkCaptcha = driver.findElement(By.xpath("//*[@id=\"recaptcha-anchor\"]"));
//        Thread.sleep(1000);
//        checkCaptcha.click();
//        Thread.sleep(10000);
//        driver.switchTo().defaultContent();
        elementLink(namePage).click();
        Thread.sleep(3000);
//          WebElement acceptCockies = driver.findElement(By.xpath("//a[@id='cookieButtonTrue']"));
//          acceptCockies.click();
//          Thread.sleep(2000);
    }

    //Metodo para leer la contraseña codificarla y desencriptarla
    private static String getDecryptedPassword() {
        Properties prop = new Properties();
        try (InputStream read = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties")) {
            if (read == null) {
                throw new RuntimeException("Archivo config.properties no encontrado en resources");
            }
            prop.load(read);
            String encryptedPassword = prop.getProperty("encrypted_password");
            // Obtener la contraseña encriptada del archivo
            return PasswordEncryptor.decrypt(encryptedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Error al leer la contraseña", e);
        }
    }

    public static void login(String email) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement initLogin = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='nav-line-2 ']")));
        Actions action = new Actions(driver);
        action.moveToElement(initLogin).perform();
        try {
            //Si hay una sesion activa, primero la cierra y luego realiza el logeo completo
            WebElement closeSession = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[normalize-space()='Cerrar sesión']")));
            if (closeSession.isDisplayed()) {
                closeSession.click();
                // Obtener la contraseña desencriptada
                String password = getDecryptedPassword();
                WebElement inputEmail = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='ap_email']")));
                inputEmail.click();
                inputEmail.sendKeys(email);
                WebElement btnContinue = driver.findElement(By.xpath("//input[@id='continue']"));
                btnContinue.click();
                WebElement inputPassword = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='ap_password']")));
                inputPassword.clear();
                inputPassword.sendKeys(password);
                WebElement initSesion = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='signInSubmit']")));
                initSesion.click();
                Thread.sleep(1000);
            }
        } catch (TimeoutException e) {
            System.out.println("No hay sesión activa. Continuando con el proceso de inicio de sesión...");
            // WebElement identify = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='nav-action-inner']")));
            // Obtener la contraseña desencriptada
            String password = getDecryptedPassword();
            WebElement login = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[normalize-space()='Cuenta y Listas']")));
            login.click();
            WebElement inputEmail = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='ap_email']")));
            inputEmail.click();
            inputEmail.sendKeys(email);
            WebElement btnContinue = driver.findElement(By.xpath("//input[@id='continue']"));
            btnContinue.click();
            WebElement inputPassword = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='ap_password']")));
            inputPassword.clear();
            inputPassword.sendKeys(password);
            WebElement initSesion = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='signInSubmit']")));
            initSesion.click();
            Thread.sleep(1000);
        }
    }

    public static void choiseProducts(List<String> products, String filePath) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<String> Attributes = ProductReader.readAltAttributesFromFile(filePath); // Leer los atributos alt desde el archivo de texto
        // Verificar que haya suficientes atributos para los productos
        if (Attributes.size() < products.size()) {
            throw new RuntimeException("No hay suficientes atributos en el archivo para los productos proporcionados.");
        }
        // Iterar sobre los productos y sus atributos
        for (int i = 0; i < products.size(); i++) {
            String product = products.get(i);
            String attribute = Attributes.get(i);
            // Buscar el producto en la plataforma
            WebElement inputSearch = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='twotabsearchtextbox']")));
            inputSearch.clear();
            inputSearch.sendKeys(product);
            inputSearch.sendKeys(Keys.ENTER);
            //Crear el xpath dinamicamente
            String xpathDinamycItem = "//img[@alt='" + attribute + "']";
            try {
                WebElement productElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathDinamycItem)));
                new Actions(driver).scrollToElement(productElement).perform();
                productElement.click();
                WebElement textReference = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='buy-now-button']")));
                if (textReference.isDisplayed()) {
                    WebElement addCar = driver.findElement(By.xpath("//input[@id='add-to-cart-button']"));
                    addCar.click();
                    Thread.sleep(1000);
                    WebElement textasd = driver.findElement(By.xpath("//*[@id=\"attach-warranty-display\"]/div[1]/div[2]/span[1]"));
                    WebElement btnNo = driver.findElement(By.xpath("//*[@id=\"attachSiNoCoverage\"]/span/input"));
                    if (textasd.isDisplayed()) {
                        btnNo.click();
                        Thread.sleep(3000);
                    }
                } else {
                    Thread.sleep(3000);
                    System.out.println("El elemento no fue encontrado en la pgina.");
                }
            } catch (Exception e) {
                System.out.println("El producto con alt '" + xpathDinamycItem + "' no se encontró en la página.");
            }

        }
    }
}


//        // Crea una instancia de Actions
//        Actions actions = new Actions(driver);
//        // Realiza un clic en una posición arbitraria en la página
//        actions.moveByOffset(400, 350).perform();
//        Thread.sleep(1000);
//        // Realiza un clic donde sea
//        actions.click().perform();
//QaAutomationOswa@gmail.com
//QaAutomationOswa7@
