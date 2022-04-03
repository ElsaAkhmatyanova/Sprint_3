package ru.praktikum_services.qa_scooter;

import org.junit.Before;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class CreatingCourierTest {

    CourierClient courierClient;
    Courier courier;
    private int courierId;

    @Before
    @Step("Creating a courier Client")
    public void setUp(){
        courierClient = new CourierClient();
        courier = Courier.getRandomCourier();
    }

    @After
    @Step("Deleting courier after test")
    public void tearDown(){
        if(courierId!=0){
            courierClient.deleteCourier(courierId);}
    }

    @Test
    @DisplayName("Creating new courier")
    @Description("Creating new courier with correct credentials")
    @Step("Check the positive creating courier, statusCode=201 and body=true")
    public void successCreatingCourierTest() {

        ValidatableResponse createResponse = courierClient.createCourier(courier);
        int statusCode = createResponse.extract().statusCode();

        assertThat("Courier cannot registered", statusCode, equalTo(201));
        createResponse.assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Creating identical couriers")
    @Description("Checking Response when trying to create identical couriers")
    @Step("Checking the response (status code and body) for compliance with the documentation when trying to create identical couriers")
    public void creatingIdenticalCourierTest() {

        courierClient.createCourier(courier);
        ValidatableResponse createResponse = courierClient.createCourier(courier);
        int statusCode = createResponse.extract().statusCode();

        assertThat("Wrong status code, identical courier cannot be create", statusCode, equalTo(409));
        createResponse.assertThat().body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Creating a courier with an existing login")
    @Description("Creating a courier with an existing login")
    @Step("Checking the response (status code and body) for compliance with the documentation")
    public void creatingExistingLoginCourierTest() {
        courierClient.createCourier(courier);
        String firstCourierLogin = courier.getLogin();

        Courier secondCourier = Courier.getRandomCourier();
        secondCourier.setLogin(firstCourierLogin);
        ValidatableResponse createResponse = courierClient.createCourier(secondCourier);
        int statusCode = createResponse.extract().statusCode();

        assertThat("Wrong status code, courier with identical login cannot be create", statusCode, equalTo(409));
        createResponse.assertThat().body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Creating a courier without login")
    @Description("Creating a courier without login")
    @Step("Checking the response (status code and body) for compliance with the documentation")
    public void creatingCourierWithoutLoginTest() {
        courier.setLogin("");

        ValidatableResponse createResponse = courierClient.createCourier(courier);
        int statusCode = createResponse.extract().statusCode();

        assertThat("Wrong status code, courier without login cannot be create", statusCode, equalTo(400));
        createResponse.assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Creating a courier without password")
    @Description("Creating a courier without password")
    @Step("Checking the response (status code and body) for compliance with the documentation")
    public void creatingCourierWithoutPasswordTest() {
        courier.setPassword("");

        ValidatableResponse createResponse = courierClient.createCourier(courier);
        int statusCode = createResponse.extract().statusCode();

        assertThat("Wrong status code, courier without password cannot be create", statusCode, equalTo(400));
        createResponse.assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

}
