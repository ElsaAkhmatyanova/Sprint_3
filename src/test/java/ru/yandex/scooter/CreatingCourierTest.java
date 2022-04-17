package ru.yandex.scooter;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.*;


public class CreatingCourierTest {

    CourierClient courierClient;
    Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandomCourier();
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            courierClient.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Creating new courier")
    @Description("Creating new courier with correct credentials and check the positive creating courier, statusCode=201 and body=true")
    public void successCreatingCourierTest() {
        ValidatableResponse createResponse = courierClient.createCourier(courier).statusCode(201);
        createResponse.assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Creating identical couriers")
    @Description("Checking Response (status code and body) when trying to create identical couriers")
    public void creatingIdenticalCourierTest() {
        courierClient.createCourier(courier);
        ValidatableResponse createResponse = courierClient.createCourier(courier).statusCode(409);
        createResponse.assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Creating a courier with an existing login")
    @Description("Creating a courier with an existing login and checking the response (status code and body)")
    public void creatingExistingLoginCourierTest() {
        courierClient.createCourier(courier);
        String firstCourierLogin = courier.getLogin();
        Courier secondCourier = Courier.getRandomCourier();
        secondCourier.setLogin(firstCourierLogin);
        ValidatableResponse createResponse = courierClient.createCourier(secondCourier).statusCode(409);
        createResponse.assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Creating a courier without login")
    @Description("Creating a courier without login and checking the response (status code and body)")
    public void creatingCourierWithoutLoginTest() {
        courier.setLogin("");
        ValidatableResponse createResponse = courierClient.createCourier(courier).statusCode(400);
        createResponse.assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Creating a courier without password")
    @Description("Creating a courier without password and checking the response (status code and body)")
    public void creatingCourierWithoutPasswordTest() {
        courier.setPassword("");
        ValidatableResponse createResponse = courierClient.createCourier(courier).statusCode(400);
        createResponse.assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

}
