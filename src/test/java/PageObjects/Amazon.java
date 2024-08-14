package PageObjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//Teclado
import org.openqa.selenium.Keys;

public class Amazon extends DriverChrome{

    static WebElement elementSearch = driver.findElement(By.xpath("//textarea[@id='APjFqb']"));
    //static By clickElement = By.xpath("");
    //static  WebElement inputSearch = driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
    public static WebElement elementLink(String namePage) {
        WebElement linkbutton = driver.findElement(By.xpath("//span[normalize-space()='" + namePage + "']"));
        return linkbutton;
        //span[normalize-space()='Compra Amazon | Sitio oficial de Amazon.com.mx']
    }

    public static void getAmazon(String namePage) throws InterruptedException {
        elementSearch.sendKeys("Amazon");
        elementSearch.sendKeys(Keys.ENTER);
        Thread.sleep(3000);
        elementLink(namePage).click();
    }

    public static void choiseComponentsPC(String motherboard) throws InterruptedException {

        //inputSearch.sendKeys(motherboard);
        //inputSearch.sendKeys(Keys.ENTER);
        Thread.sleep(4000);
        driver.quit();
    }

}
