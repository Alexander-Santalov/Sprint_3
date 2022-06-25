package ru.yandex.scooter;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderHelper {
    private static final String ORDER_PATH = "/api/v1/orders/";

    @Step("Отправка POST запроса на ручку /api/v1/courier для создания заказа ")
    public static ValidatableResponse create(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Отправка PUT запроса на ручку /api/v1/orders/cancel для отмены заказа ")
    public static void cancelOrder(int trackId) {
        given()
                .header("Content-type", "application/json")
                .body(trackId)
                .when()
                .put(ORDER_PATH + "cancel")
                .then();
    }

    @Step("Отправка GET запроса на ручку /api/v1/orders для получения списка заказов")
    public static ValidatableResponse getOrdersList() {
        return given().
                get(ORDER_PATH)
                .then();
    }
}
