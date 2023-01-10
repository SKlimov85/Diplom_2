package sitle.nomoreparties.stellarburgers.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;

public class UserAssertion {

    @Step("Получение успешного ответа на запрос по созданию пользователя")
    public String assertCreationSusses(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(200)
                .body("success", is(true))
                .extract().path("accessToken");
    }

    @Step("Получение успешного ответа на запрос по удалению пользователя")
    public void assertDeleteSusses(ValidatableResponse response) {
        response.assertThat()
                .statusCode(202)
                .body("message", equalTo("User successfully removed"));
    }

    @Step("Получение ответа с ошибкой на запрос по созданию пользователя с существующим e-mail")
    public void assertCreationDoubleUserFailed(ValidatableResponse response) {
        response.assertThat()
                .statusCode(403)
                .body("message", equalTo("User already exists"));
    }

    @Step("Получение ответа с ошибкой на запрос по созданию пользователя c незаполненным обязательным полем")
    public void assertCreationUserNoRequiredField(ValidatableResponse response) {
        response.assertThat()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Step("Получение успешного ответа на запрос логин курьера в системе")
    public void loginInSusses(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("accessToken", notNullValue());
    }

    @Step("Получение ответа с ошибкой при указании неверного логина или пароля")
    public void loginInFailed(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }

    @Step("Получение успешного ответа на запрос изменения данных зарегестрированным пользователем")
    public void updateDateUserInSusses(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }
    @Step("Получение успешного ответа на запрос изменения данных зарегестрированным пользователем")
    public void updateDateUserNotInFailed(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }
}