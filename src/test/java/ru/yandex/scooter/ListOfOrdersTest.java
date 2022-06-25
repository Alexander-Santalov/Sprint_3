package ru.yandex.scooter;

import io.qameta.allure.*;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@Epic("Проверка на получение списка заказов")
public class ListOfOrdersTest extends TestBase {

    @Test
    @Story("Получение списка заказов")
    public void getListOrdersTest() {
        ValidatableResponse response = OrderHelper.getOrdersList();
        int actualCode = response.extract().statusCode();
        assertThat(actualCode, is(200));
        response.assertThat().body("orders", hasSize(30));
    }
}
