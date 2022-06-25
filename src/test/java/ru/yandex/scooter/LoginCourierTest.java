package ru.yandex.scooter;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import io.qameta.allure.*;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Epic("Проверки на логин курьера в системе")
public class LoginCourierTest extends TestBase {


    @Test
    @Story("Успешный логин")
    public void loginCourierTest() {
        Courier courier = new Courier(RandomStringUtils.randomAlphabetic(10), "12345", "Alex");
        CourierHelper.createCourier(courier);
        ValidatableResponse response = CourierHelper.login(new CourierAuth(courier.getLogin(), courier.getPassword()));
        int actualCode = response.extract().statusCode();
        assertThat(actualCode, is(SC_OK));
        int courierId = response.extract().path("id");
        assertThat(courierId, notNullValue());
        CourierHelper.delete(courierId);
    }

    @Test
    @Story("Запрос с несуществующей парой логин-пароль")
    public void nonexistentLoginCourierTest() {
        ValidatableResponse response = CourierHelper.login(new CourierAuth(RandomStringUtils.randomAlphabetic(15),
                RandomStringUtils.randomAlphabetic(15)));
        int actualCode = response.extract().statusCode();
        assertThat(actualCode, is(SC_NOT_FOUND));
        String message = response.extract().path("message");
        assertThat(message, equalTo("Учетная запись не найдена"));
    }

    @Test
    @Story("Запрос без логина")
    public void loginCourierTestWithoutLogin() {
        ValidatableResponse response = CourierHelper.login(new CourierAuth(null, "12345"));
        int actualCode = response.extract().statusCode();
        assertThat(actualCode, is(SC_BAD_REQUEST));
        String message = response.extract().path("message");
        assertThat(message, equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Story("Запрос без пароля")
    public void loginCourierTestWithoutPassword() {
        ValidatableResponse response = CourierHelper.login(new CourierAuth(RandomStringUtils.randomAlphabetic(10),
                null));
        int actualCode = response.extract().statusCode();
        assertThat(actualCode, is(SC_BAD_REQUEST));
        String message = response.extract().path("message");
        assertThat(message, equalTo("Недостаточно данных для входа"));
    }
}
