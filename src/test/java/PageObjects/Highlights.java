package PageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class Highlights {
    private WebDriver driver;
    private static final int HIGHLIGHT_DURATION = 500; // 00.5 segundo

    public Highlights(WebDriver driver) {
        this.driver = driver;
    }

    // Méodo para resaltar un elemento con colores personalizados
    public void highlightElement(WebElement element, String borderColor, String backgroundColor, String screenshotName) throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Guardar estilo original
        String originalStyle = element.getAttribute("style");

       // Estilo resaltado
        String highlightStyle = String.format(
                "border: 6px solid %s !important; " +
                        "background-color: %s !important; " +
                        "box-shadow: 0 0 0 4px white, 0 0 20px 10px %s !important; " +  // Doble sombra
                        "outline: 1px solid black !important; " +  // Para contraste
                        "z-index: 999999 !important; " +
                        "border-radius: 3px !important;",  // Esquinas ligeramente redondeadas
                borderColor, backgroundColor, borderColor
        );

        //Aplicar estilo resaltado
        js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, highlightStyle);

        // Tomar screenshot
        ScreenshotUtils.takeScreenshot(driver, screenshotName);

        // Restaurar estilo original después de un tiempo
        Thread.sleep(HIGHLIGHT_DURATION);
        js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, originalStyle);
    }
}

//Opcion 2:
// Sobrecarga con colores por defecto (rojo y amarillo)
//    public void highlightElement(WebElement element) {
//        highlightElement(element, "red", "#FFFF00");
//    }


