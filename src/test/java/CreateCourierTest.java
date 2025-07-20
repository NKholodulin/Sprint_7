import Data.CourierData;
import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.apache.http.HttpStatus.*;
import static Api.CourierApi.*;

public class CreateCourierTest extends BaseTest {
    CourierData createCourierData = new CourierData("holodTest", "1234", "holod");
    private boolean shouldDeleteCourier = false;
    private int courierId;

    @Test
    @DisplayName("Check response and status code of /api/v1/courier") // имя теста
    @Description("Basic test for /api/v1/courier endpoint")
    void createCourierCheckResponseAndStatusCode() {
        shouldDeleteCourier = true; // включаем удаление после теста
        createCourier(createCourierData)
                .then().assertThat().body("ok", equalTo(true)).statusCode(SC_CREATED);
    }

    @Test
    @DisplayName("[Negative] create two identical couriers of /api/v1/courier") // имя теста
    @Description("Negative test for /api/v1/courier endpoint")
    void createIdenticalCourier() {
        shouldDeleteCourier = true; // включаем удаление после теста
        createCourier(createCourierData)
                .then().statusCode(SC_CREATED);

        createCourier(createCourierData)
                .then().assertThat().body("code", equalTo(SC_CONFLICT)).body("message", equalTo("Этот логин уже используется. Попробуйте другой.")).and().statusCode(SC_CONFLICT);
    }

    @Test
    @DisplayName("[Negative] create two identical couriers of /api/v1/courier") // имя теста
    @Description("Negative test for /api/v1/courier endpoint")
    void createCourierWithIdenticalLogin() {
        shouldDeleteCourier = true; // включаем удаление после теста
        CourierData createCourierWithIdenticalLoginData = new CourierData("holodTest", "12341234", "holod1234");
        createCourier(createCourierData)
                .then().statusCode(SC_CREATED);

        createCourier(createCourierWithIdenticalLoginData)
                .then().assertThat().body("code", equalTo(SC_CONFLICT)).body("message", equalTo("Этот логин уже используется. Попробуйте другой.")).and().statusCode(SC_CONFLICT);
    }

    @Test
    @DisplayName("Create courier without firstName /api/v1/courier") // имя теста
    @Description("Basic test for /api/v1/courier endpoint")
    void createCourierWithoutFirstName() {
        shouldDeleteCourier = true; // включаем удаление после теста
        CourierData createCourierWithoutFirstNameData = CourierData.withLoginAndPassword("holodTest","1234");
        createCourier(createCourierWithoutFirstNameData)
                .then().statusCode(SC_CREATED);
    }

    @Test
    @DisplayName("Create courier without login /api/v1/courier") // имя теста
    @Description("Negative test for /api/v1/courier endpoint")
    void createCourierWithoutLogin() {
        CourierData createCourierWithoutLoginData = CourierData.withPasswordAndFirstName("1234", "holod");
        createCourier(createCourierWithoutLoginData)
                .then().assertThat().body("code", equalTo(SC_BAD_REQUEST)).body("message", equalTo("Недостаточно данных для создания учетной записи")).and().statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Create courier without password /api/v1/courier") // имя теста
    @Description("Negative test for /api/v1/courier endpoint")
    void createCourierWithoutPassword() {
        CourierData createCourierWithoutPasswordData = CourierData.withLoginAndFirstName("holodTest","holod");
        createCourier(createCourierWithoutPasswordData)
                .then().assertThat().body("code", equalTo(SC_BAD_REQUEST)).body("message", equalTo("Недостаточно данных для создания учетной записи")).and().statusCode(SC_BAD_REQUEST);
    }

    @AfterEach
    void tearDown() {
        if (shouldDeleteCourier) {
            courierId = loginCourier(createCourierData)
                    .then().extract().body().path("id");
            deleteCourier(courierId)
                    .then().statusCode(SC_OK);
        }
    }
}
