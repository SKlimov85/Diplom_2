package sitle.nomoreparties.stellarburgers.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class UserClient extends sitle.nomoreparties.stellarburgers.Client {

    protected final String ROOT = "/auth";

    @Step("Отправить запрос на создание пользователя")
    public ValidatableResponse create(UserCreate userCreate) {
        return spec()
                .body(userCreate)
                .when()
                .post(ROOT + "/register")
                .then().log().all();
    }

    @Step("Отправить запрос на удаление пользователя")
    public ValidatableResponse delete(String token) {
        return spec()
                .header("Authorization", token)
                .when()
                .delete(ROOT + "/user")
                .then().log().all();
    }

    @Step("Отправить запрос на вход под логином курьера")
    public ValidatableResponse login(UserLogin userLogin) {
        return spec()
                .body(userLogin)
                .when()
                .post(ROOT + "/login")
                .then().log().all();
    }

    @Step("Отправить запрос на изменение данных пользователя")
    public ValidatableResponse update(String token, UserCreate userCreate) {
        return spec()
                .header("Authorization", token)
                .body(userCreate)
                .when()
                .patch(ROOT + "/user")
                .then().log().all();
    }
}
