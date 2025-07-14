import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.Map;


public class CreateOrderTest {
    @BeforeEach
    public void setUp() {

        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @ParameterizedTest
    @MethodSource("OrderData#orderDataProvider")
    public void createOrderWithParameters(Map<String, Object> orderData) {
        createOrder(orderData)
                .then()
                .statusCode(201)
                .body("track", notNullValue());
    }

    @Step("Send POST request to /api/v1/orders")
    public static Response createOrder(Map<String, Object> orderData) {
        Response response = given()
                .contentType("application/json")
                .body(orderData)
                .when()
                .post("/api/v1/orders");
        return response;
    }
}
