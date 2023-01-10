package sitle.nomoreparties.stellarburgers.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sitle.nomoreparties.stellarburgers.user.UserAssertion;
import sitle.nomoreparties.stellarburgers.user.UserClient;
import sitle.nomoreparties.stellarburgers.user.UserCreate;
import sitle.nomoreparties.stellarburgers.user.UserGenerator;

public class OrderListUserTest {
    private final UserGenerator userGenerator = new UserGenerator();
    private final UserClient userClient = new UserClient();
    private final UserAssertion userAssertion = new UserAssertion();
    private final OrderClient orderClient = new OrderClient();
    UserCreate uniqueUser = userGenerator.randomDataCourier();
    OrderAssertion orderAssertion = new OrderAssertion();
    private String accessToken;


    @Before
    public void userCreate() {
        ValidatableResponse create = userClient.create(uniqueUser);
        accessToken = userAssertion.assertCreationSusses(create);
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            ValidatableResponse response = userClient.delete(accessToken);
            userAssertion.assertDeleteSusses(response);
        }
    }

    @DisplayName("Провека получения списка заказов зарегистрированного пользователя")
    @Test
    public void checkGetListOrderInLoginUser() {
        ValidatableResponse response = orderClient.getOrdersListUser(accessToken);
        orderAssertion.getOrderListInLoginUser(response);
    }
    @DisplayName("Провека невозможности получения списка заказов не зарегистрированным пользователем")
    @Test
    public void checkGetListOrderNoLoginUser() {
        ValidatableResponse response = orderClient.getOrdersListUser("1");
        orderAssertion.getOrderListNoLoginUser(response);
    }
}
