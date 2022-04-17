package ru.yandex.scooter;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterRestClient {

    private static final String COURIER_PATH = "api/v1/courier/";
    private static final String COURIER_LOGIN = "api/v1/courier/login";

    @Step("Login request with Login:{credentials.login} Password:{credentials.password}")
    public ValidatableResponse loginCourier(CourierCredentials courierCredentials) {
        return given()
                .header("Content-type", "application/json")
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_LOGIN)
                .then();
    }

    @Step("Creating courier request with Login:{courier.login} Password:{courier.password} First Name:{courier.firstName}")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Delete courier with id:{courierId}")
    public ValidatableResponse deleteCourier(int courierId) {

        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then();
    }
}
