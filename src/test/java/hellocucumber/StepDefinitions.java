package hellocucumber;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StepDefinitions {
    private int a, b;
    private BigDecimal result;
    private String operation;

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
        this.operation = "add";
        result = new BigDecimal(StepUtils.callApiPost("calculate/add", StepUtils.buildRequestBody(a, b)));
    }

    @When("I perform a subtraction with these numbers")
    public void iPerformASubtractionWithTheseNumbers() throws IOException {
        this.operation = "subtract";
        result = new BigDecimal(StepUtils.callApiPost("calculate/subtract", StepUtils.buildRequestBody(a, b)));
    }

    @When("I perform a multiplication with these numbers")
    public void iPerformAMultiplicationWithTheseNumbers() throws IOException {
        this.operation = "multiply";
        result = new BigDecimal(StepUtils.callApiPost("calculate/multiply", StepUtils.buildRequestBody(a, b)));
    }

    @When("I perform a division with these numbers")
    public void iPerformADivisionWithTheseNumbers() throws IOException {
        this.operation = "divide";
        result = new BigDecimal(StepUtils.callApiPost("calculate/divide", StepUtils.buildRequestBody(a, b)));
    }

    @ParameterType("\\d*\\.?\\d*")
    public BigDecimal BigDecimal(String bigdecimal) {
        return new BigDecimal(bigdecimal);
    }

    @Given("there is no history data available")
    public void thereIsNoHistoryDataAvailable() throws IOException {
        StepUtils.callApiPost("history/clear", "");
    }

    @And("history data contains the calculation")
    public void historyDataContainsTheCalculation() throws IOException {
        // [{"firstValue":3.0,"secondValue":2.0,"result":1.5,"operation":"divide"}]
        String history = StepUtils.callApiGet("history");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(history);
        JsonNode next = jsonNode.elements().next();
        assertEquals(0, new BigDecimal(next.get("firstValue").asText()).compareTo(new BigDecimal(a)));
        assertEquals(0, new BigDecimal(next.get("secondValue").asText()).compareTo(new BigDecimal(b)));
        assertEquals(0, new BigDecimal(next.get("result").asText()).compareTo(result));
        assertEquals(operation, next.get("operation").asText());
    }
}
