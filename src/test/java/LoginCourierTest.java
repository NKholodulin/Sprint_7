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
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    File jsonCreateCourier = new File("src/test/resources/createCourier.json");
    private boolean shouldDeleteCourier = false;
    private int courierId;

    @BeforeEach
    public void setUp() {

        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        CreateCourierTest.createCourier(jsonCreateCourier).then().statusCode(201);
    }

    @Test
    @DisplayName("Check status code of /api/v1/courier/login") // имя теста
    @Description("Basic test for /api/v1/courier/login endpoint")
    void loginCourierStatusCode() {
        shouldDeleteCourier = true; // включаем удаление после теста
        loginCourier(jsonCreateCourier)
                .then().statusCode(200);
    }

    @Test
    @DisplayName("Check response of /api/v1/courier/login") // имя теста
    @Description("Basic test for /api/v1/courier/login endpoint")
    void loginCourierCheckResponse() {
        shouldDeleteCourier = true; // включаем удаление после теста
        loginCourier(jsonCreateCourier)
                .then().assertThat().body("id", notNullValue());
    }
    @Test
    @DisplayName("Request without firstName of /api/v1/courier/login") // имя теста
    @Description("Basic test for /api/v1/courier/login endpoint")
    void loginCourierCheckWithoutFirstName() {
        File jsonCreateCourierWithoutFirstName = new File("src/test/resources/createCourierWithoutFirstName.json");
        shouldDeleteCourier = true; // включаем удаление после теста
        loginCourier(jsonCreateCourierWithoutFirstName)
                .then().assertThat().body("id", notNullValue()).and().statusCode(200);
    }

    @Test
    @DisplayName("Request without login of /api/v1/courier/login") // имя теста
    @Description("Negative test for /api/v1/courier/login endpoint")
    void loginCourierCheckWithoutLogin() {
        File jsonCreateCourierWithoutLogin = new File("src/test/resources/createCourierWithoutLogin.json");
        shouldDeleteCourier = true; // включаем удаление после теста
        loginCourier(jsonCreateCourierWithoutLogin)
                .then().statusCode(400);
    }

    @Test
    @DisplayName("Request without password of /api/v1/courier/login") // имя теста
    @Description("Negative test for /api/v1/courier/login endpoint")
    void loginCourierCheckWithoutPassword() {
        File jsonCreateCourierWithoutPassword = new File("src/test/resources/createCourierWithoutPassword.json");
        shouldDeleteCourier = true; // включаем удаление после теста
        loginCourier(jsonCreateCourierWithoutPassword)
                .then().statusCode(400);
    }

    @Test
    @DisplayName("Request with wrong login of /api/v1/courier/login") // имя теста
    @Description("Negative test for /api/v1/courier/login endpoint")
    void loginCourierWithWrongLogin() {
        File jsonLoginCourierWithWrongLogin = new File("src/test/resources/loginCourierWithWrongLogin.json");
        shouldDeleteCourier = true; // включаем удаление после теста
        loginCourier(jsonLoginCourierWithWrongLogin)
                .then().assertThat().body("code", equalTo(404)).body("message", equalTo("Учетная запись не найдена")).and().statusCode(404);
    }

    @Test
    @DisplayName("Request with wrong password of /api/v1/courier/login") // имя теста
    @Description("Negative test for /api/v1/courier/login endpoint")
    void loginCourierWithWrongPassword() {
        File jsonCreateCourierWithIdenticalLogin = new File("src/test/resources/createCourierWithIdenticalLogin.json");
        shouldDeleteCourier = true; // включаем удаление после теста
        loginCourier(jsonCreateCourierWithIdenticalLogin)
                .then().assertThat().body("code", equalTo(404)).body("message", equalTo("Учетная запись не найдена")).and().statusCode(404);
    }

    @Test
    @DisplayName("Request with non-existent courier of /api/v1/courier/login") // имя теста
    @Description("Negative test for /api/v1/courier/login endpoint")
    void loginCourierWithNonExistentCourier() {
        File jsonNonExistentCourier = new File("src/test/resources/nonExistentCourier.json");
        shouldDeleteCourier = true; // включаем удаление после теста
        loginCourier(jsonNonExistentCourier)
                .then().assertThat().body("code", equalTo(404)).body("message", equalTo("Учетная запись не найдена")).and().statusCode(404);
    }

    @AfterEach
    void tearDown() {
        if (shouldDeleteCourier) {
            courierId = loginCourier(jsonCreateCourier)
                    .then().extract().body().path("id");
            DeleteCourierTest.deleteCourier(courierId)
                    .then().statusCode(200);
        }
    }

    @Step("Send POST request to /api/v1/courier/login")
    public static Response loginCourier(File json) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login");
        return response;
    }
}
