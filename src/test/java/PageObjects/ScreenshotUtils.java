package PageObjects;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtils {
    public static String takeScreenshot(WebDriver driver, String screenshotName) throws Exception {
        // Crear directorio si no existe
        String directory = "C:\\Evidencias\\";
        new File(directory).mkdirs();

        // Generar nameImage con java.time (formato: yyyyMMdd_HHmmss)
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filePath = directory + screenshotName + "_" + timestamp + ".png";

        // Tomar screenshot y guardar
        File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(imageFile, new File(filePath));

        //Guardar screenshots en ALLURE
        byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment("\uD83D\uDD0D " + screenshotName + " - " + timestamp, "image/png", new ByteArrayInputStream(screenshotBytes), "png");

        return filePath;
    }
}
