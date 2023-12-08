package testRunner;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(

        /*If you want to run all the feature files, just provide the name of Folder in path where feature files exist*/
        features = ".//src/test/Resources/Features",
        glue = "stepDefinitions", // Name of the package where Stepdefinition class exists
        /*you can run only specific scenario with the tag name as below */
        //tags = "@system_test",
        plugin = {"pretty", "html:test-output/report.html"} // html report will be in test-output folder generated
)

public class TestRunner {
}
