package ru.yandex.scooter;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderHelper {

    @Step("Отправка POST запроса на ручку /api/v1/courier для создания заказа ")
    public static ValidatableResponse create(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then();
    }

    @Step("Отправка PUT запроса на ручку /api/v1/orders/cancel для отмены заказа ")
    public static void cancel(int trackId) {
        given()
                .header("Content-type", "application/json")
                .body(trackId)
                .when()
                .put("/api/v1/orders/cancel")
                .then();
    }

}
