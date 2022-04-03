package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreatingOrderTest {

    private final List<String> color;
    private final Matcher<Object> expected;

    public CreatingOrderTest(List<String> color, Matcher<Object> expected) {
        this.color = color;
        this.expected = expected;
    }

    @Parameterized.Parameters
    @Step("Test data colors creating for checking order creating")
    public static Object[][] getColorData() {
        return new Object[][]{
                {List.of("BLACK", "GREY"), notNullValue()},
                {List.of("BLACK"), notNullValue()},
                {List.of("GREY"), notNullValue()},
                {null, notNullValue()}
        };
    }

    @Test
    @DisplayName("Success creating order")
    @Description("Check creating order with different list of color.")
    @Step("Checking order creating with different colors")
    public void CreatingOrderTest() {
        OrderClient orderClient = new OrderClient();
        Order order = new Order(color);
        Response createOrderResponse = orderClient.createOrder(order);
        createOrderResponse.then().assertThat().statusCode(201)
                .and()
                .body("track", expected);
    }
}
