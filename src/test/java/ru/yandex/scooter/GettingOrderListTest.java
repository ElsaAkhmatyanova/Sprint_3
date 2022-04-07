package ru.yandex.scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

public class GettingOrderListTest {

    @Test
    @DisplayName("Success getting a list of orders")
    @Description("Check getting a list of orders is not empty")
    public void getOrderListTest() {
        OrderClient orderClient = new OrderClient();
        ValidatableResponse orderListResponse = orderClient.getOrdersList().statusCode(200);
        orderListResponse.assertThat().body("orders.id", notNullValue());
    }
}
