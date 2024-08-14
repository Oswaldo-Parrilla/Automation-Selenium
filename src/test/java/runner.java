import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

public class runner {
    @RunWith(Cucumber.class)
    @CucumberOptions(glue = {"StepDefs"},
            plugin = "preaty", //reporte
            features = {"Feature"})

            public class runnerConstructor{

    }
}
