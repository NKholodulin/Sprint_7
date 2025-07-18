import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.apache.http.HttpStatus.*;

public class DeleteCourierTest extends ApiSteps {
    CourierData createCourierData = new CourierData("holodTest", "1234", "holod");
    private boolean shouldDeleteCourier = false;
    private int courierId;

    @BeforeEach
    public void setUp() {
        super.setUp();
        createCourier(createCourierData).then().statusCode(SC_CREATED);
        courierId = LoginCourierTest.loginCourier(createCourierData).then().extract().body().path("id");
    }

    @Test
    @DisplayName("Check response and status code of /api/v1/courier/{courierId}") // имя теста
    @Description("Basic test for /api/v1/courier/{courierId} endpoint")
    void deleteCourierCheckResponseAndStatusCode() {
        shouldDeleteCourier = false; // выключаем удаление после теста
        deleteCourier(courierId)
                .then().assertThat().body("ok", equalTo(true)).statusCode(SC_OK);
    }

    @Test
    @DisplayName("Check double delete of /api/v1/courier/{courierId}") // имя теста
    @Description("Negative test for /api/v1/courier/{courierId} endpoint")
    void deleteCourierDouble() {
        shouldDeleteCourier = false; // выключаем удаление после теста
        deleteCourier(courierId)
                .then().statusCode(SC_OK);
        deleteCourier(courierId)
                .then().assertThat().body("code", equalTo(SC_NOT_FOUND)).body("message", equalTo("Курьера с таким id нет.")).and().statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Check delete non-existing courier of /api/v1/courier/{courierId}") // имя теста
    @Description("Negative test for /api/v1/courier/{courierId} endpoint")
    void deleteNonExistingCourier() {
        shouldDeleteCourier = true; // включаем удаление после теста
        deleteCourier(404)
                .then().assertThat().body("code", equalTo(SC_NOT_FOUND)).body("message", equalTo("Курьера с таким id нет.")).and().statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Check delete courier without id of /api/v1/courier/{courierId}") // имя теста
    @Description("Negative test for /api/v1/courier/{courierId} endpoint")
    void deleteCourierWithoutIdTest() {
        shouldDeleteCourier = true; // включаем удаление после теста
        deleteCourierWithoutId()
                .then().assertThat().body("code", equalTo(SC_NOT_FOUND)).body("message", equalTo("Not Found.")).and().statusCode(SC_NOT_FOUND);
    }

    @AfterEach
    void tearDown() {
        if (shouldDeleteCourier) {
            deleteCourier(courierId)
                    .then().statusCode(SC_OK);
        }
    }
}
