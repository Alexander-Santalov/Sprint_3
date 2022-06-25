package ru.yandex.scooter;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierHelper {
    private static final String COURIER_PATH = "/api/v1/courier/";

    @Step("Отправка POST запроса на ручку /api/v1/courier для создания курьера")
    public static ValidatableResponse createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Отправка POST запроса на ручку /api/v1/courier/login для логина курьера в систему")
    public static ValidatableResponse login(CourierAuth auth) {
        return given()
                .header("Content-type", "application/json")
                .body(auth)
                .when()
                .post(COURIER_PATH + "login")
                .then();
    }

    @Step("Отправка DELETE запроса на ручку /api/v1/courier/:id для удаления курьера")
    public static void delete(int courierId) {
        given()
                .header("Content-type", "application/json")
                .when()
                .delete(COURIER_PATH + courierId)
                .then();
    }
}

