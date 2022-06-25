package ru.yandex.scooter;

import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class CourierHelper {
    private static final String COURIER_PATH = "/api/v1/courier/";

    @Step("Отправка POST запроса на ручку /api/v1/courier для создания курьера")
    public static boolean createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ok");
    }

    @Step("Отправка POST запроса на ручку /api/v1/courier/login для логина курьера в систему")
    public static int login(CourierAuth auth) {
        return given()
                .header("Content-type", "application/json")
                .body(auth)
                .when()
                .post(COURIER_PATH + "login")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
    }

    @Step("Отправка DELETE запроса на ручку /api/v1/courier/:id для удаления курьера")
    public static void delete(int courierId) {
        given()
                .header("Content-type", "application/json")
                .when()
                .delete(COURIER_PATH + courierId)
                .then();
    }

    @Step("Отправка POST запроса на ручку /api/v1/courier для создания курьера ")
    public static String badCreate(Courier courier, int code) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .assertThat()
                .statusCode(code)
                .extract()
                .path("message");
    }

    @Step("Отправка POST запроса на ручку /api/v1/courier/login для логина курьера в систему")
    public static String badLogin(CourierAuth auth, int code) {
        return given()
                .header("Content-type", "application/json")
                .body(auth)
                .when()
                .post(COURIER_PATH + "login")
                .then()
                .assertThat()
                .statusCode(code)
                .extract()
                .path("message");
    }

}

