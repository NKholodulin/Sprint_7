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

public class CreateCourierTest {
    File jsonCreateCourier = new File("src/test/resources/createCourier.json");
    private boolean shouldDeleteCourier = false;
    private int courierId;

    @BeforeEach
    public void setUp() {

        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check status code of /api/v1/courier") // имя теста
    @Description("Basic test for /api/v1/courier endpoint")
    void createCourierStatusCode() {
        shouldDeleteCourier = true; // включаем удаление после теста
        createCourier(jsonCreateCourier)
                .then().statusCode(201);
    }

    @Test
    @DisplayName("Check response of /api/v1/courier") // имя теста
    @Description("Basic test for /api/v1/courier endpoint")
    void createCourierCheckResponse() {
        shouldDeleteCourier = true; // включаем удаление после теста
        createCourier(jsonCreateCourier)
                .then().assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("[Negative] create two identical couriers of /api/v1/courier") // имя теста
    @Description("Negative test for /api/v1/courier endpoint")
    void createIdenticalCourier() {
        shouldDeleteCourier = true; // включаем удаление после теста
        createCourier(jsonCreateCourier)
                .then().statusCode(201);

        createCourier(jsonCreateCourier)
                .then().assertThat().body("code", equalTo(409)).body("message", equalTo("Этот логин уже используется. Попробуйте другой.")).and().statusCode(409);
    }

    @Test
    @DisplayName("[Negative] create two identical couriers of /api/v1/courier") // имя теста
    @Description("Negative test for /api/v1/courier endpoint")
    void createCourierWithIdenticalLogin() {
        shouldDeleteCourier = true; // включаем удаление после теста
        File jsonCreateCourierWithIdenticalLogin = new File("src/test/resources/createCourierWithIdenticalLogin.json");
        createCourier(jsonCreateCourier)
                .then().statusCode(201);

        createCourier(jsonCreateCourierWithIdenticalLogin)
                .then().assertThat().body("code", equalTo(409)).body("message", equalTo("Этот логин уже используется. Попробуйте другой.")).and().statusCode(409);
    }

    @Test
    @DisplayName("Create courier without firstName /api/v1/courier") // имя теста
    @Description("Basic test for /api/v1/courier endpoint")
    void createCourierWithoutFirstName() {
        shouldDeleteCourier = true; // включаем удаление после теста
        File jsonCreateCourierWithoutFirstName = new File("src/test/resources/createCourierWithoutFirstName.json");
        createCourier(jsonCreateCourierWithoutFirstName)
                .then().statusCode(201);
    }

    @Test
    @DisplayName("Create courier without login /api/v1/courier") // имя теста
    @Description("Negative test for /api/v1/courier endpoint")
    void createCourierWithoutLogin() {
        File jsonCreateCourierWithoutLogin = new File("src/test/resources/createCourierWithoutLogin.json");
        createCourier(jsonCreateCourierWithoutLogin)
                .then().assertThat().body("code", equalTo(400)).body("message", equalTo("Недостаточно данных для создания учетной записи")).and().statusCode(400);
    }

    @Test
    @DisplayName("Create courier without password /api/v1/courier") // имя теста
    @Description("Negative test for /api/v1/courier endpoint")
    void createCourierWithoutPassword() {
        File jsonCreateCourierWithoutPassword = new File("src/test/resources/createCourierWithoutPassword.json");
        createCourier(jsonCreateCourierWithoutPassword)
                .then().assertThat().body("code", equalTo(400)).body("message", equalTo("Недостаточно данных для создания учетной записи")).and().statusCode(400);
    }

    @AfterEach
    void tearDown() {
        if (shouldDeleteCourier) {
            courierId = loginCourier(jsonCreateCourier)
                    .then().extract().body().path("id");
            deleteCourier(courierId)
                    .then().statusCode(200);
        }
    }

    // метод для шага "Создать курьера":
    @Step("Send POST request to /api/v1/courier")
    public Response createCourier(File json) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier");
        return response;
    }

    @Step("Send POST request to /api/v1/courier/login")
    public Response loginCourier(File json) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login");
        return response;
    }

    @Step("Send DELETE request to /api/v1/courier/{courierId}")
    public Response deleteCourier(int courierId) {
        Response response = given()
                .delete("/api/v1/courier/{courierId}", courierId);
        return response;
    }
}
