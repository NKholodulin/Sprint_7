import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DeleteCourierTest {
    File jsonCreateCourier = new File("src/test/resources/createCourier.json");
    private boolean shouldDeleteCourier = false;
    private int courierId;

    @BeforeEach
    public void setUp() {

        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        CreateCourierTest.createCourier(jsonCreateCourier).then().statusCode(201);
        courierId = LoginCourierTest.loginCourier(jsonCreateCourier).then().extract().body().path("id");
    }

    @Test
    @DisplayName("Check status code of /api/v1/courier/{courierId}") // имя теста
    @Description("Basic test for /api/v1/courier/{courierId} endpoint")
    void deleteCourierCheckStatusCode() {
        shouldDeleteCourier = false; // выключаем удаление после теста
        deleteCourier(courierId)
                .then().statusCode(200);
    }

    @Test
    @DisplayName("Check response of /api/v1/courier/{courierId}") // имя теста
    @Description("Basic test for /api/v1/courier/{courierId} endpoint")
    void deleteCourierCheckResponse() {
        shouldDeleteCourier = false; // выключаем удаление после теста
        deleteCourier(courierId)
                .then().assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Check double delete of /api/v1/courier/{courierId}") // имя теста
    @Description("Negative test for /api/v1/courier/{courierId} endpoint")
    void deleteCourierDouble() {
        shouldDeleteCourier = false; // выключаем удаление после теста
        deleteCourier(courierId)
                .then().statusCode(200);
        deleteCourier(courierId)
                .then().assertThat().body("code", equalTo(404)).body("message", equalTo("Курьера с таким id нет.")).and().statusCode(404);
    }

    @Test
    @DisplayName("Check delete non-existing courier of /api/v1/courier/{courierId}") // имя теста
    @Description("Negative test for /api/v1/courier/{courierId} endpoint")
    void deleteNonExistingCourier() {
        shouldDeleteCourier = true; // включаем удаление после теста
        deleteCourier(404)
                .then().assertThat().body("code", equalTo(404)).body("message", equalTo("Курьера с таким id нет.")).and().statusCode(404);
    }

    @Test
    @DisplayName("Check delete courier without id of /api/v1/courier/{courierId}") // имя теста
    @Description("Negative test for /api/v1/courier/{courierId} endpoint")
    void deleteCourierWithoutIdTest() {
        shouldDeleteCourier = true; // включаем удаление после теста
        deleteCourierWithoutId()
                .then().assertThat().body("code", equalTo(404)).body("message", equalTo("Not Found.")).and().statusCode(404);
    }

    @AfterEach
    void tearDown() {
        if (shouldDeleteCourier) {
            DeleteCourierTest.deleteCourier(courierId)
                    .then().statusCode(200);
        }
    }

    @Step("Send DELETE request to /api/v1/courier/{courierId}")
    public static Response deleteCourier(int courierId) {
        Response response = given()
                .delete("/api/v1/courier/{courierId}", courierId);
        return response;
    }
    @Step("Send DELETE request to /api/v1/courier/courierId")
    public static Response deleteCourierWithoutId() {
        Response response = given()
                .delete("/api/v1/courier/");
        return response;
    }
}
