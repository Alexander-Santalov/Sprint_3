package ru.yandex.scooter;

import io.qameta.allure.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;


import java.util.Random;

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
        boolean isCreatedCourier = CourierHelper.createCourier(courier);
        courierId = CourierHelper.login(new CourierAuth(courier.getLogin(), courier.getPassword()));
        assertTrue(isCreatedCourier);
    }

    @Test
    @Story("Создание учетной записи с повторяющимся логином")
    public void createRepeatCourierTest() {
        Courier courier = new Courier("Santalov." + new Random().nextInt(100),
                "12345", "Alex");
        CourierHelper.createCourier(courier);
        courierId = CourierHelper.login(new CourierAuth(courier.getLogin(), courier.getPassword()));
        String message = CourierHelper.badCreate(courier, 409);
        assertThat(message, equalTo("Этот логин уже используется"));
    }

    @Test
    @Story("Создание учетной записи без пароля")
    public void createNewCourierWithoutPasswordTest() {
        Courier courier = new Courier(RandomStringUtils.randomAlphabetic(10), null, "Alex");
        String message = CourierHelper.badCreate(courier, 400);
        assertThat(message, equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Story("Создание учетной записи без логина")
    public void createNewCourierWithoutLoginTest() {
        Courier courier = new Courier(null, "12345", "Alex");
        String message = CourierHelper.badCreate(courier, 400);
        assertThat(message, equalTo("Недостаточно данных для создания учетной записи"));
    }
}
