package ru.yandex.scooter;

import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@Epic("Проверки на создание заказа")
@RunWith(Parameterized.class)
public class CreateOrderTest extends TestBase {
    private final List<String> color;
    private final int expectedCode;
    private int trackId;

    @After
    public void tearDown() {
        OrderHelper.cancelOrder(trackId);
    }

    public CreateOrderTest(List<String> color, int expectedCode) {
        this.color = color;
        this.expectedCode = expectedCode;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {List.of("BLACK", "GREY"), 201},
                {List.of(), 201},
                {List.of("BLACK"), 201},
                {List.of("GREY"), 201}
        };
    }

    @Test
    @Story("Успешное создание учетной заказа - параметризованный тест")
    public void orderCreateTest() {
        Order order = Order.getRandomOrder(color);
        ValidatableResponse response = OrderHelper.create(order);
        int actualCode = response.extract().statusCode();
        int trackId = response.extract().path("track");
        assertThat(actualCode, is(expectedCode));
        assertThat(trackId, notNullValue());
    }
}
