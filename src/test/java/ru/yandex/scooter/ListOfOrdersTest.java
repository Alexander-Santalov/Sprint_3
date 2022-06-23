package ru.yandex.scooter;

import io.qameta.allure.*;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@Epic("Проверка на получение списка заказов")
public class ListOfOrdersTest extends TestBase {

    @Test
    @Story("Получение списка заказов")
    @Step("Отправка GET запроса на ручку /api/v1/orders для получения списка заказов")
    public void getListOrdersTest() {
        given().get("/api/v1/orders")
                .then().statusCode(200)
                .and()
                .assertThat().body("orders", hasSize(30));
    }
}
