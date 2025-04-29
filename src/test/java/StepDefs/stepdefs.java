package StepDefs;

import PageObjects.DriverChrome;
import PageObjects.AmazonBuy;
import io.cucumber.java.en.*;

import java.util.List;

public class stepdefs {
    @Given("I enter the Amazon page")
    public void IentertheAmazonPage() {
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
    public void iLogInToThePortal() throws Exception {
        String emailSoriana = "QaAutomationOswa@gmail.com";
        AmazonBuy.login(emailSoriana);
    }

    @And("I search the following products in the soriana page:")
    public void iSearchTheFollowingProductsInTheSorianaPage(List<String> products) throws Exception {
        String filePath = "src/test/resources/productos.txt";
        AmazonBuy.choiseProducts(products, filePath);
    }

    @And("I proceed to payment")
    public void iProceedToPayment() {
        String Filepath = "src/test/resources/addressAmazon.xlsx";
        try {
            AmazonBuy.buyProducts(Filepath);
        } catch (InterruptedException e) {
            e.printStackTrace(); // Imprime el error en la consola
            Thread.currentThread().interrupt(); // Restaura el estado de interrupción del hilo
        }
    }
    @And("I add credit card pay")
    public void iAddCreaditCardPay() {
        String Filepath = "src/test/resources/addressAmazon.xlsx";
        try {
            AmazonBuy.paymnet(Filepath);
        } catch (InterruptedException e) {
            e.printStackTrace(); // Imprime el error en la consola
            Thread.currentThread().interrupt(); // Restaura el estado de interrupción del hilo
        }
    }
}
