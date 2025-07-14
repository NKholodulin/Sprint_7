import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

public class ListOrdersTest {
    @BeforeEach
    public void setUp() {

        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check status code of /api/v1/orders") // имя теста
    @Description("Basic test for /api/v1/orders endpoint")
    void listOrdersStatusCode() {
        listOrders()
                .then().statusCode(200);
    }

    @Test
    @DisplayName("Check body of /api/v1/orders") // имя теста
    @Description("Basic test for /api/v1/orders endpoint")
    void listOrdersResponse() {
        listOrders()
                .then()
                .body("orders", notNullValue())
                .body("orders", instanceOf(List.class));
    }

    // метод для шага "Создать курьера":
    @Step("Send GET request to /api/v1/orders")
    public static Response listOrders() {
        Response response = given()
                .when()
                .get("/api/v1/orders");
        return response;
    }
}
