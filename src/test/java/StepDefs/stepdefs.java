package StepDefs;

import PageObjects.DriverChrome;
import PageObjects.AmazonBuy;
import io.cucumber.java.en.*;

import java.util.List;

public class stepdefs {
    @Given("I enter the Amazon page")
    public void IentertheAmazonPage(){
    }
    @When("I open browser google")
    public void Iopenbrowsergoogle() throws InterruptedException {
        DriverChrome.openBrowser();
    }
    @And("I search {string}")
    public void IsearchAmazon(String namePage) throws InterruptedException {
        AmazonBuy.getAmazonPage(namePage);
    }
    @And("I log in to the portal")
    public void iLogInToThePortal() throws InterruptedException {
        String emailSoriana = "QaAutomationOswa@gmail.com";
        AmazonBuy.login(emailSoriana);
    }
    @And("I search the following products in the soriana page:")
    public void iSearchTheFollowingProductsInTheSorianaPage(List<String> products) throws InterruptedException {
        String filePath = "C:/Users/uchih/OneDrive/Documentos/Oswaldo/Automatizacion/productos.txt";
        AmazonBuy.choiseProducts(products, filePath);
    }


}
