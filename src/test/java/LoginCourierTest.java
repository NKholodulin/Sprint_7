import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.*;

public class LoginCourierTest extends ApiSteps {
    CourierData createCourierData = new CourierData("holodTest", "1234", "holod");
    private boolean shouldDeleteCourier = false;
    private int courierId;

    @BeforeEach
    public void setUp() {
        super.setUp();
        createCourier(createCourierData).then().statusCode(SC_CREATED);
    }

    @Test
    @DisplayName("Check response and status code of /api/v1/courier/login") // имя теста
    @Description("Basic test for /api/v1/courier/login endpoint")
    void loginCourierCheckResponseAndStatusCode() {
        shouldDeleteCourier = true; // включаем удаление после теста
        loginCourier(createCourierData)
                .then().assertThat().body("id", notNullValue()).statusCode(SC_OK);
    }

    @Test
    @DisplayName("Request without firstName of /api/v1/courier/login") // имя теста
    @Description("Basic test for /api/v1/courier/login endpoint")
    void loginCourierCheckWithoutFirstName() {
        CourierData createCourierWithoutFirstNameData = CourierData.withLoginAndPassword("holodTest","1234");
        shouldDeleteCourier = true; // включаем удаление после теста
        loginCourier(createCourierWithoutFirstNameData)
                .then().assertThat().body("id", notNullValue()).and().statusCode(SC_OK);
    }

    @Test
    @DisplayName("Request without login of /api/v1/courier/login") // имя теста
    @Description("Negative test for /api/v1/courier/login endpoint")
    void loginCourierCheckWithoutLogin() {
        CourierData createCourierWithoutLoginData = CourierData.withPasswordAndFirstName("1234", "holod");
        shouldDeleteCourier = true; // включаем удаление после теста
        loginCourier(createCourierWithoutLoginData)
                .then().statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Request without password of /api/v1/courier/login") // имя теста
    @Description("Negative test for /api/v1/courier/login endpoint")
    void loginCourierCheckWithoutPassword() {
        CourierData createCourierWithoutPasswordData = CourierData.withLoginAndFirstName("holodTest","holod");
        shouldDeleteCourier = true; // включаем удаление после теста
        loginCourier(createCourierWithoutPasswordData)
                .then().statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Request with wrong login of /api/v1/courier/login") // имя теста
    @Description("Negative test for /api/v1/courier/login endpoint")
    void loginCourierWithWrongLogin() {
        CourierData createCourierWithWrongLoginData = new CourierData("holodTestWrong", "1234", "holod");
        shouldDeleteCourier = true; // включаем удаление после теста
        loginCourier(createCourierWithWrongLoginData)
                .then().assertThat().body("code", equalTo(SC_NOT_FOUND)).body("message", equalTo("Учетная запись не найдена")).and().statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Request with wrong password of /api/v1/courier/login") // имя теста
    @Description("Negative test for /api/v1/courier/login endpoint")
    void loginCourierWithWrongPassword() {
        CourierData createCourierWithIdenticalLoginData = new CourierData("holodTest", "12341234", "holod1234");
        shouldDeleteCourier = true; // включаем удаление после теста
        loginCourier(createCourierWithIdenticalLoginData)
                .then().assertThat().body("code", equalTo(SC_NOT_FOUND)).body("message", equalTo("Учетная запись не найдена")).and().statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Request with non-existent courier of /api/v1/courier/login") // имя теста
    @Description("Negative test for /api/v1/courier/login endpoint")
    void loginCourierWithNonExistentCourier() {
        CourierData NonExistentCourierData = new CourierData("nonnonnononnon", "nonnonnononnon", "nonnonnononnon");
        shouldDeleteCourier = true; // включаем удаление после теста
        loginCourier(NonExistentCourierData)
                .then().assertThat().body("code", equalTo(SC_NOT_FOUND)).body("message", equalTo("Учетная запись не найдена")).and().statusCode(SC_NOT_FOUND);
    }

    @AfterEach
    void tearDown() {
        if (shouldDeleteCourier) {
            courierId = loginCourier(createCourierData)
                    .then().extract().body().path("id");
            DeleteCourierTest.deleteCourier(courierId)
                    .then().statusCode(SC_OK);
        }
    }
}
