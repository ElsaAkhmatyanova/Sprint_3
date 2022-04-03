package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class GettingOrderListTest {
    @Test
    @DisplayName("Success getting a list of orders")
    @Description("Check getting a list of orders is not empty")
    @Step("Checking what list of orders in not empty")
    public void getOrderListTest(){
        OrderClient orderClient = new OrderClient();
        ValidatableResponse orderListResponse = orderClient.getOrdersList();
        int statusCode = orderListResponse.extract().statusCode();
        assertThat("List of order not getting, statusCode is not 200", statusCode, equalTo(200));
        orderListResponse.assertThat().body("orders.id", notNullValue());
    }
}
