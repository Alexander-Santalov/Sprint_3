package ru.yandex.scooter;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import io.qameta.allure.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Epic("Проверки на логин курьера в системе")
public class LoginCourierTest extends TestBase {


    @Test
    @Story("Успешный логин")
    public void loginCourierTest() {
        Courier courier = new Courier(RandomStringUtils.randomAlphabetic(10), "12345", "Alex");
        CourierHelper.create(courier);
        int courierId = CourierHelper.login(new CourierAuth(courier.getLogin(), courier.getPassword()));
        assertThat(courierId, notNullValue());
        CourierHelper.delete(courierId);
    }

    @Test
    @Story("Запрос с несуществующей парой логин-пароль")
    public void nonexistentLoginCourierTest() {
        String message = CourierHelper.badLogin(new CourierAuth(RandomStringUtils.randomAlphabetic(15),
                        RandomStringUtils.randomAlphabetic(15)),404);
        assertThat(message, equalTo("Учетная запись не найдена"));
    }

    @Test
    @Story("Запрос без логина")
    public void loginCourierTestWithoutLogin() {
        String message = CourierHelper.badLogin(new CourierAuth(null, "12345"), 400);
        assertThat(message, equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Story("Запрос без пароля")
    public void loginCourierTestWithoutPassword() {
        String message = CourierHelper.badLogin(new CourierAuth(RandomStringUtils.randomAlphabetic(10),
                null), 400);
        assertThat(message, equalTo("Недостаточно данных для входа"));
    }
}
