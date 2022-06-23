package ru.yandex.scooter;

import io.restassured.RestAssured;
import org.junit.Before;

public class TestBase {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }
}
