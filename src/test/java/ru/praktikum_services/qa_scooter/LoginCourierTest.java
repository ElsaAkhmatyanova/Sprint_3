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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class LoginCourierTest {

    CourierClient courierClient;
    Courier courier;
    int courierId;
    CourierCredentials courierCredentials;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
        courier = Courier.getRandomCourier();
        courierClient.createCourier(courier);
        courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());

    }

    @After
    public void tearDown(){
       courierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Successful courier login")
    @Description("Creating new courier and login with correct credentials")
    @Step("Check the success login courier, statusCode=200")
    public void successLoginCourierTest() {

        ValidatableResponse loginResponse = courierClient.loginCourier(courierCredentials);
        courierId = loginResponse.extract().path("id");
        int statusCode = loginResponse.extract().statusCode();

        assertThat("Status code in courier Login Test is not 200", statusCode, equalTo(200));
        assertThat("Courier ID is not correct", courierId, is(not(0)));
        System.out.println(courierId);
    }

    @Test
    @DisplayName("Failed courier login with incorrect courierLogin")
    @Description("Creating new courier and login with incorrect courierLogin")
    @Step("Check the failed login courier, statusCode=404")
    public void failedLoginCourierIncorrectLoginTest() {

        ValidatableResponse loginResponse = courierClient.loginCourier(courierCredentials);
        courierId = loginResponse.extract().path("id");

        CourierCredentials incorrectLoginCred = new CourierCredentials(courier.getLogin() + "test", courier.getPassword());

        ValidatableResponse failedLoginResponse = courierClient.loginCourier(incorrectLoginCred);
        int statusCode = failedLoginResponse.extract().statusCode();

        assertThat("Status code in courier login with incorrect courierLogin is not 404", statusCode, equalTo(404));
        failedLoginResponse.assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Failed courier login with incorrect courierPassword")
    @Description("Creating new courier and login with incorrect courierPassword")
    @Step("Check the failed login courier, statusCode=404")
    public void failedLoginCourierIncorrectPasswordTest() {

        ValidatableResponse loginResponse = courierClient.loginCourier(courierCredentials);
        courierId = loginResponse.extract().path("id");

        CourierCredentials incorrectPasswordCred = new CourierCredentials(courier.getLogin(), courier.getPassword()+ "123");

        ValidatableResponse failedLoginResponse = courierClient.loginCourier(incorrectPasswordCred);
        int statusCode = failedLoginResponse.extract().statusCode();

        assertThat("Status code in courier login with incorrect courierPassword is not 404", statusCode, equalTo(404));
        failedLoginResponse.assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Failed courier login without courierLogin")
    @Description("Creating new courier and login without courierLogin")
    @Step("Check the failed login courier, statusCode=400")
    public void failedLoginCourierWithoutLoginTest() {

        ValidatableResponse loginResponse = courierClient.loginCourier(courierCredentials);
        courierId = loginResponse.extract().path("id");

        CourierCredentials withoutLoginCred = new CourierCredentials("", courier.getPassword());

        ValidatableResponse failedLoginResponse = courierClient.loginCourier(withoutLoginCred);
        int statusCode = failedLoginResponse.extract().statusCode();

        assertThat("Status code in courier login without courierLogin is not 400", statusCode, equalTo(400));
        failedLoginResponse.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Failed courier login without courierPassword")
    @Description("Creating new courier and login without courierPassword")
    @Step("Check the failed login courier, statusCode=400")
    public void failedLoginCourierWithoutPasswordTest() {

        ValidatableResponse loginResponse = courierClient.loginCourier(courierCredentials);
        courierId = loginResponse.extract().path("id");

        CourierCredentials withoutPasswordCred = new CourierCredentials(courier.getLogin(),"");

        ValidatableResponse failedLoginResponse = courierClient.loginCourier(withoutPasswordCred);
        int statusCode = failedLoginResponse.extract().statusCode();

        assertThat("Status code in courier login without courierPassword is not 400", statusCode, equalTo(400));
        failedLoginResponse.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Failed courier login without courierCred")
    @Description("Creating new courier and login without courierCred")
    @Step("Check the failed login courier, statusCode=400")
    public void failedLoginCourierWithoutCredTest() {

        ValidatableResponse loginResponse = courierClient.loginCourier(courierCredentials);
        courierId = loginResponse.extract().path("id");

        CourierCredentials withoutCred = new CourierCredentials("","");

        ValidatableResponse failedLoginResponse = courierClient.loginCourier(withoutCred);
        int statusCode = failedLoginResponse.extract().statusCode();

        assertThat("Status code in courier login without courierCred is not 400", statusCode, equalTo(400));
        failedLoginResponse.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }



}
