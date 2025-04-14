package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/amazon.feature",
        glue = {"StepDefs"},
        plugin = {
                //"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "json:build/allure-results/cucumber.json",
                "junit:build/allure-results/junit.xml",
                "pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber.json"
        },
        monochrome = true
)
public class Runner {
    // no necesita código dentro
}

