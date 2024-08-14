package StepDefs;

import PageObjects.DriverChrome;
import PageObjects.Amazon;
import io.cucumber.java.en.*;

public class stepdefs {
    @Given("I enter the amazon Mexico page")
    public void iNeedBuyThingsInAmazonPage(){
    }
    @When("I open browser google")
    public void Iopenbrowsergoogle() throws InterruptedException {
        DriverChrome.openBrowser();
    }
    @And("I search {string}")
    public void Isearchamazonpage(String namePage) throws InterruptedException {
        Amazon.getAmazon(namePage);
    }
    @And("I search {string} in the amazon search engine")
    public void iSelectPcGameComponents(String motherboard) throws InterruptedException {
        Amazon.choiseComponentsPC(motherboard);
    }
}
