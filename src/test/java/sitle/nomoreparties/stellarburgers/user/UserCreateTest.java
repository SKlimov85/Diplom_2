package sitle.nomoreparties.stellarburgers.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class UserCreateTest {

    private final UserGenerator userGenerator = new UserGenerator();
    private final UserClient userClient = new UserClient();
    private final UserAssertion userAssertion = new UserAssertion();
    UserCreate uniqueUser = userGenerator.randomDataCourier();
    private String accessToken;

    @After
    public void deleteUser() {
        if (accessToken != null) {
            ValidatableResponse response = userClient.delete(accessToken);
            userAssertion.assertDeleteSusses(response);
        }
    }

    @Test
    @DisplayName("Проверка успешного создания пользователя")
    public void checkUserCreateSusses() {
        ValidatableResponse create = userClient.create(uniqueUser);
        accessToken = userAssertion.assertCreationSusses(create);
    }

    @Test
    @DisplayName("Проверка невозможности создания дубля пользователя")
    public void checkDoubleCourier() {
        ValidatableResponse create = userClient.create(uniqueUser);
        accessToken = userAssertion.assertCreationSusses(create);

        ValidatableResponse create2 = userClient.create(uniqueUser);
        userAssertion.assertCreationDoubleUserFailed(create2);
    }

    @Test
    @DisplayName("Проверка невозможности создания пользователя без e-mail")
    public void checkCreateUserNoEmail() {
        uniqueUser.setEmail(null);
        ValidatableResponse create = userClient.create(uniqueUser);
        userAssertion.assertCreationUserNoRequiredField(create);
    }
    @Test
    @DisplayName("Проверка невозможности создания пользователя без пароля")
    public void checkCreateUserNoPassword() {
        uniqueUser.setPassword(null);
        ValidatableResponse create = userClient.create(uniqueUser);
        userAssertion.assertCreationUserNoRequiredField(create);
    }
    @Test
    @DisplayName("Проверка невозможности создания пользователя без имени")
    public void checkCreateCourierNoName() {
        uniqueUser.setName(null);
        ValidatableResponse create = userClient.create(uniqueUser);
        userAssertion.assertCreationUserNoRequiredField(create);
    }
}