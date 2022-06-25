package ru.yandex.scooter;

import io.qameta.allure.*;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;

import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

@Epic("Проверки на создание курьера")
public class CreatedCourierTest extends TestBase {
    private int courierId;

    @After
    public void tearDown() {
        if (courierId > 0) {
            CourierHelper.delete(courierId);
        }
    }

    @Test
    @Story("Успешное создание учетной записи")
    public void createNewCourierTest() {
        Courier courier = new Courier(RandomStringUtils.randomAlphabetic(30), "12345", "Alex");
        ValidatableResponse response = CourierHelper.createCourier(courier);
        int actualCode = response.extract().statusCode();
        assertThat(actualCode, is(SC_CREATED));
        boolean isCreatedCourier = response.extract().path("ok");
        assertTrue(isCreatedCourier);
        ValidatableResponse responseLogin = CourierHelper.login(new CourierAuth(courier.getLogin(), courier.getPassword()));
        int courierId = responseLogin.extract().path("id");
    }

    @Test
    @Story("Создание учетной записи с повторяющимся логином")
    public void createRepeatCourierTest() {
        Courier courier = new Courier("Santalov." + new Random().nextInt(100),
                "12345", "Alex");
        CourierHelper.createCourier(courier);
        ValidatableResponse response = CourierHelper.createCourier(courier);
        int actualCode = response.extract().statusCode();
        assertThat(actualCode, is(SC_CONFLICT));
        String message = response.extract().path("message");
        assertThat(message, equalTo("Этот логин уже используется"));
        ValidatableResponse responseLogin = CourierHelper.login(new CourierAuth(courier.getLogin(), courier.getPassword()));
        int courierId = responseLogin.extract().path("id");
    }

    @Test
    @Story("Создание учетной записи без пароля")
    public void createNewCourierWithoutPasswordTest() {
        Courier courier = new Courier(RandomStringUtils.randomAlphabetic(10), null, "Alex");
        ValidatableResponse response = CourierHelper.createCourier(courier);
        int actualCode = response.extract().statusCode();
        assertThat(actualCode, is(SC_BAD_REQUEST));
        String message = response.extract().path("message");
        assertThat(message, equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Story("Создание учетной записи без логина")
    public void createNewCourierWithoutLoginTest() {
        Courier courier = new Courier(null, "12345", "Alex");
        ValidatableResponse response = CourierHelper.createCourier(courier);
        int actualCode = response.extract().statusCode();
        assertThat(actualCode, is(SC_BAD_REQUEST));
        String message = response.extract().path("message");
        assertThat(message, equalTo("Недостаточно данных для создания учетной записи"));
    }
}
