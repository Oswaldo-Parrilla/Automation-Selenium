package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static PageObjects.DriverChrome.driver;

public class CloseRFC {
    public static void deleteRFC() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement initLogin = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='nav-line-2 ']")));
            Actions action = new Actions(driver);
            action.moveToElement(initLogin).perform();
            WebElement optionMyCount = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Mi cuenta')]")));
            optionMyCount.click();
            WebElement optionManageRFC = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Administrar RFC o CURP guardados']")));
            optionManageRFC.click();
            WebElement noneRFC = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='a-text-normal']")));
            if (!noneRFC.isDisplayed()) {
                WebElement RFC = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[normalize-space()='RFC (Registro Federal de Contribuyentes) o CURP (Clave Unica de Registro de Población): **********292'])[1]")));
                if (RFC.isDisplayed()) {
                    WebElement btnDelete = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[normalize-space()='Eliminar'])[1]")));
                    btnDelete.click();
                    WebElement btnConfirm = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='a-button-text' and contains(text(), 'Sí')]")));
                    try {
                        btnConfirm.click();
                    } catch (Exception e) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnConfirm);
                    }
                    wait.until(ExpectedConditions.invisibilityOf(btnConfirm));
                    WebElement btnLogo = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='nav-logo-sprites']")));
                    btnLogo.click();
                }
            } else {
                System.out.println("No hay RFC registrado. Continuando flujo normal...");
            }
        } catch (Exception e) {
            //Manejo de excepciones al cambiar direccion
            System.err.println("Error en el bloque 'Borrar RFC': " + e.getMessage());
            System.err.println("Clase: " + e.getClass().getName());
            System.err.println("Método: deleteRFC");
            // Buscar la línea exacta en el stack trace
            for (StackTraceElement element : e.getStackTrace()) {
                if (element.getClassName().equals(CloseRFC.class.getName())) {
                    System.err.println("Línea exacta: " + element.getLineNumber());
                    break;
                }
            }
            e.printStackTrace();
        }
    }
}
