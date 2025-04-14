package PageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;


public class AmazonBuy extends DriverChrome {

    //static By clickElement = By.xpath("");
    static WebElement elementSearch = driver.findElement(By.xpath("//textarea[@id='APjFqb']"));

    public static WebElement elementLink(String namePage) {
        WebElement linkbutton = driver.findElement(By.xpath("//h3[normalize-space(text())='" + namePage + "']"));
        return linkbutton;
    }

    public static void getAmazonPage(String namePage) throws InterruptedException {
        Highlights highlight = new Highlights(driver);
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
        //highlight.highlightAfterClick(elementLink(namePage));
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

    public static void login(String email) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Highlights highlight = new Highlights(driver);
        WebElement initLogin = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='nav-line-2 ']")));
        Actions action = new Actions(driver);
        highlight.highlightElement(initLogin, "red", "transparent", "init_login_highlighted");
        action.moveToElement(initLogin).perform();

        try {
            //Si hay una sesion activa, primero la cierra y luego realiza el logeo completo
            WebElement closeSession = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[normalize-space()='Cerrar sesión']")));
            if (closeSession.isDisplayed()) {
                highlight.highlightElement(closeSession, "red", "transparent", "close_session");
                closeSession.click();

                // Obtener la contraseña desencriptada
                String password = getDecryptedPassword();
                WebElement inputEmail = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='ap_email']")));
                inputEmail.click();
                inputEmail.sendKeys(email);
                highlight.highlightElement(inputEmail, "red", "transparent", "Input_Email");
                WebElement btnContinue = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='continue']")));
                btnContinue.click();
                WebElement inputPassword = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='ap_password']")));
                inputPassword.clear();
                inputPassword.sendKeys(password);
                highlight.highlightElement(inputPassword, "red", "transparent", "Input Password");
                WebElement initSesion = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='signInSubmit']")));
                initSesion.click();
                Thread.sleep(1000);
                CloseRFC.deleteRFC();
            }
        } catch (TimeoutException e) {
            System.out.println("No hay sesión activa. Continuando con el proceso de inicio de sesión...");

            // Obtener la contraseña desencriptada
            String password = getDecryptedPassword();
            WebElement login = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[normalize-space()='Cuenta y Listas']")));
            login.click();
            WebElement inputEmail = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='ap_email']")));
            inputEmail.click();
            inputEmail.sendKeys(email);
            highlight.highlightElement(inputEmail, "red", "transparent", "Input_Email");
            WebElement btnContinue = driver.findElement(By.xpath("//input[@id='continue']"));
            btnContinue.click();
            WebElement inputPassword = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='ap_password']")));
            inputPassword.clear();
            inputPassword.sendKeys(password);
            highlight.highlightElement(inputPassword, "red", "transparent", "Password");
            WebElement initSesion = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='signInSubmit']")));
            initSesion.click();
            CloseRFC.deleteRFC();
            Thread.sleep(1000);
            //Manejo de excepciones al cambiar direccion
            System.err.println("Error en el bloque 'Cambiar dirección': " + e.getMessage());
            System.err.println("Clase: " + e.getClass().getName());
            System.err.println("Método: buyProducts");
            // Buscar la línea exacta en el stack trace
            for (StackTraceElement element : e.getStackTrace()) {
                if (element.getClassName().equals(AmazonBuy.class.getName())) {
                    System.err.println("Línea exacta: " + element.getLineNumber());
                    break;
                }
            }
            e.printStackTrace();
        }
    }

    public static void choiseProducts(List<String> products, String filePath) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Highlights highlight = new Highlights(driver);

        // Leer los atributos alt desde el archivo de texto
        List<String> Attributes = ProductReader.readAltAttributesFromFile(filePath);

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
            WebElement highSearch = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='twotabsearchtextbox' and @name='field-keywords' and @role='searchbox']")));
            inputSearch.clear();
            inputSearch.sendKeys(product);
            highlight.highlightElement(highSearch, "red", "transparent", "Products");
            inputSearch.sendKeys(Keys.ENTER);

            //Crear el xpath dinamicamente
            String xpathDinamycItem = "//img[@alt='" + attribute + "']";
            try {
                WebElement productElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDinamycItem)));
                new Actions(driver).scrollToElement(productElement).perform();
                productElement.click();
                WebElement textReference = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='buy-now-button']")));
                if (textReference.isDisplayed()) {
                    WebElement addCar = driver.findElement(By.xpath("//input[@type='button' and @value='Agregar al Carrito']"));
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

    public static void buyProducts(String excelFilePath) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        Highlights highlight = new Highlights(driver);
        try {
            // Hacer clic en el carrito
            WebElement buy = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='nav-cart-text-container']")));
            buy.click();
            // Hacer clic en el botón de compra
            WebElement btnBuy = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='proceedToRetailCheckout']")));
            btnBuy.click();

            // Verificar si el botón "Cambiar" está presente
            try {
                Thread.sleep(3000);
                //WebElement btnAddres = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@data-testid='Address_selectShipToThisAddress']")));
                WebElement btnCambiar = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[normalize-space()='Cambiar'])[1]")));
                //WebElement btnChangeAddress = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h3[@class='a-color-state' and contains(., 'Agregar RFC o CURP para despacho de aduana')]")));
                // Hacer clic en "Agregar una nueva dirección de entrega"
                if (btnCambiar.isDisplayed()) {
                    btnCambiar.click();
                    WebElement btnAddres = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@data-testid=\"bottom-continue-button\" and @type=\"submit\" and contains(@class, \"a-button-input\")]")));
                    btnAddres.click();
                    WebElement cbxOmitir = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Omitir por ahora']")));
                    cbxOmitir.click();
                    WebElement cbxCurp = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Agregar un nuevo RFC o CURP']")));
                    cbxCurp.click();
                    WebElement dropDaownCurp = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[@class='a-dropdown-prompt'])[1]")));
                    highlight.highlightElement(dropDaownCurp, "red", "transparent", "Dropdown");
                    dropDaownCurp.click();
                    WebElement optionRFC = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[normalize-space()='RFC'])[1]")));
                    highlight.highlightElement(optionRFC, "red", "transparent", "OptionRFC");
                    optionRFC.click();
                    WebElement inputRFC = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='sif_kyc_xborder_ID_NUMBER']")));
                    inputRFC.sendKeys("PACO940318292");
                    highlight.highlightElement(inputRFC, "red", "transparent", "InputRFC");
                    WebElement inputIdentifier = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='sif_kyc_xborder_SAVE_ID_AS']")));
                    inputIdentifier.clear();
                    inputIdentifier.sendKeys("Testing");
                    highlight.highlightElement(inputIdentifier, "red", "transparent", "inputIdentifier");
                    inputRFC.click();
                    WebElement btnContinue = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@aria-labelledby='kyc-xborder-continue-button-announce'])[1]")));
                    btnContinue.click();
                }
            } catch (Exception e) {
                //Manejo de excepciones al cambiar direccion
                System.err.println("Error en el bloque 'Cambiar dirección': " + e.getMessage());
                System.err.println("Clase: " + e.getClass().getName());
                System.err.println("Método: buyProducts");
                // Buscar la línea exacta en el stack trace
                for (StackTraceElement element : e.getStackTrace()) {
                    if (element.getClassName().equals(AmazonBuy.class.getName())) {
                        System.err.println("Línea exacta: " + element.getLineNumber());
                        break;
                    }
                }
                e.printStackTrace();

                WebElement btnAddres = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[normalize-space()='Agregar una nueva dirección de entrega']")));
                btnAddres.click();

                // Leer datos del Excel
                Map<String, String> addressData = ExcelReader.readAddressFromExcel(excelFilePath);

                // Rellenar el formulario de dirección
                WebElement inputName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='address-ui-widgets-enterAddressFullName']")));
                WebElement inputAddress = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='address-ui-widgets-enterAddressLine1']")));
                WebElement inputCP = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='address-ui-widgets-enterAddressPostalCode']")));
                WebElement btnValidateCP = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='address-ui-widgets-enterAddressPostalCode-submit']")));
                WebElement inputPhoneNumber = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='address-ui-widgets-enterAddressPhoneNumber']")));
                WebElement inputInstructions = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='address-ui-widgets-addr-details-gate-code']")));
                WebElement btnDirection = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@id='checkout-primary-continue-button-id']//input[@type='submit']")));
                inputName.clear();
                inputName.sendKeys(addressData.get("Nombre"));
                inputAddress.sendKeys(addressData.get("Calle Numero"));
                inputCP.sendKeys(addressData.get("CP"));
                btnValidateCP.click();
                inputPhoneNumber.sendKeys(addressData.get("Num Telefono"));
                inputInstructions.sendKeys(addressData.get("Instrucciones"));

                // Hacer clic en el botón de confirmación de dirección
                Actions actions = new Actions(driver);
                actions.moveToElement(btnDirection).perform();
                btnDirection.click();
            }

            Thread.sleep(2000); // Espera adicional para verificar el resultado
        } catch (Exception e) {
            // Manejo de excepciones generales
            System.err.println("Error general en el método buyProducts: " + e.getMessage());
            System.err.println("Clase: " + e.getClass().getName());
            System.err.println("Método: buyProducts");
            // Buscar la línea exacta en el stack trace
            for (StackTraceElement element : e.getStackTrace()) {
                if (element.getClassName().equals(AmazonBuy.class.getName())) {
                    System.err.println("Línea exacta: " + element.getLineNumber());
                    break;
                }
            }
            e.printStackTrace(); // Imprimir el stack trace completo
        }
    }

    public static void paymnet(String excelFilePath) throws InterruptedException {
        try {
            Highlights highlight = new Highlights(driver);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            // 2. Crear objeto Actions
            Actions actions = new Actions(driver);
            WebElement addCreditCard = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[normalize-space()='Agregar una tarjeta de crédito o débito'])[1]")));
            addCreditCard.click();
            // Leer datos del Excel
            Map<String, String> paymentData = ExcelReader.readPaymentFromExcel(excelFilePath);
            // Validar que los datos no sean nulos
            String cardNumber = Objects.requireNonNull(paymentData.get("Number Card"), "El número de tarjeta no puede ser nulo");
            String nameOnCard = Objects.requireNonNull(paymentData.get("Name Target"), "El nombre en la tarjeta no puede ser nulo");
            String cvv = Objects.requireNonNull(paymentData.get("CVV"), "El CVV no puede ser nulo");
            String monthInit = Objects.requireNonNull(paymentData.get("Month Init"), "El mes no puede ser nulo");
            String ageInit = Objects.requireNonNull(paymentData.get("Age Init"), "El año no puede ser nulo");

            WebElement iframePayment = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[contains(@name, 'ApxSecureIframe')]")));
            driver.switchTo().frame(iframePayment);
            WebElement inputNumberCard = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[contains(@id, 'pp-') and contains(@id, '-17')]")));
            inputNumberCard.sendKeys(cardNumber);
            highlight.highlightElement(inputNumberCard, "red", "transparent", "inputNumberCard");
            WebElement inputNameCard = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='ppw-accountHolderName']")));
            inputNameCard.sendKeys(nameOnCard);
            highlight.highlightElement(inputNameCard, "red", "transparent", "inputNameCard");
            WebElement month = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[@role='button'])[1]")));
            month.click();
            // Convertir el mes String a número de interacciones en interger(ej: "06" -> 6 iteraciones)
            int monthInteractions = Integer.parseInt(monthInit.trim()); // Elimina espacios y convierte a int
            String monthInteractionsStr = String.valueOf(monthInteractions);
            // Simular flechas del teclado
            for (int i = 1; i < monthInteractions; i++) {
                actions.sendKeys(Keys.ARROW_DOWN).perform();
                Thread.sleep(200); // Pequeña pausa entre interacciones
            }
            actions.sendKeys(Keys.ENTER).perform();
            highlight.highlightElement(month, "red", "transparent", "monthDrop");

            WebElement age = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[@role='button'])[2]")));
            age.click();
            int ageInteractions = Integer.parseInt(ageInit.trim()); // Elimina espacios y convierte a int
            // Simular flechas del teclado
            for (int i = 1; i < ageInteractions; i++) {
                actions.sendKeys(Keys.ARROW_DOWN).perform();
                Thread.sleep(200); // Pequeña pausa entre interacciones
            }
            actions.sendKeys(Keys.ENTER).perform();
            highlight.highlightElement(age, "red", "transparent", "ageDrop");

            WebElement inputCvv = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@autocomplete='off' and @maxlength='4' and @type='password']")));
            inputCvv.sendKeys(cvv);
            highlight.highlightElement(inputCvv, "red", "transparent", "inputCvv");
            WebElement btnAdd = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@name='ppw-widgetEvent:AddCreditCardEvent'])[1]")));
            //btnAdd.click();
            driver.close();

        } catch (Exception e) {
            System.err.println("Error general en el método buyProducts: " + e.getMessage());
            System.err.println("Clase: " + e.getClass().getName());
            System.err.println("Método: Payment");
            //Buscar la linea exactta en el stack trance
            for (StackTraceElement element : e.getStackTrace()) {
                if (element.getClassName().equals(AmazonBuy.class.getName())) {
                    System.err.println("Linea exacta: " + element.getLineNumber());
                    break;
                }
            }
        }
    }
}


//QaAutomationOswa@gmail.com
//QaAutomationOswa7@
