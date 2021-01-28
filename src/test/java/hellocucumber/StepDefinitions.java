package hellocucumber;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.fail;

public class StepDefinitions {
    private int a, b;
    private BigDecimal result;

    @Given("I have the numbers {int} and {int}")
    public void iHaveTheNumbersAnd(int arg0, int arg1) throws IOException {
        a = arg0;
        b = arg1;
    }

    @Then("I receive the value {BigDecimal}")
    public void iReceiveTheValue(BigDecimal arg0) {
        int i = arg0.compareTo(result);
        if (i != 0) {
            fail("Resulting value " + result + " does not match expected value " + arg0);
        }
    }

    @When("I perform an addition with these numbers")
    public void iPerformAnAdditionWithTheseNumbers() throws IOException {

        result = new BigDecimal(StepUtils.callApi("add", StepUtils.buildRequestBody(a, b)));
    }

    @When("I perform a subtraction with these numbers")
    public void iPerformASubtractionWithTheseNumbers() throws IOException {
        result = new BigDecimal(StepUtils.callApi("subtract", StepUtils.buildRequestBody(a, b)));
    }

    @When("I perform a multiplication with these numbers")
    public void iPerformAMultiplicationWithTheseNumbers() throws IOException {
        result = new BigDecimal(StepUtils.callApi("multiply", StepUtils.buildRequestBody(a, b)));
    }

    @When("I perform a division with these numbers")
    public void iPerformADivisionWithTheseNumbers() throws IOException {
        result = new BigDecimal(StepUtils.callApi("divide", StepUtils.buildRequestBody(a, b)));
    }

    @ParameterType("\\d*\\.?\\d*")
    public BigDecimal BigDecimal(String bigdecimal) {
        return new BigDecimal(bigdecimal);
    }
}
