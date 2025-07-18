import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.Map;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AcceptOrderTest {
    CourierData createCourierData = new CourierData("holodTest", "1234", "holod");
    private boolean shouldDeleteCourier = false;
    private int courierId;
    private int orderId;
    private int trackId;
    private Map<String, Object> singleOrderData = OrderData.orderDataProvider().findFirst().orElseThrow();
    @BeforeEach
    public void setUp() {

        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        CreateCourierTest.createCourier(createCourierData).then().statusCode(201);
        courierId = LoginCourierTest.loginCourier(createCourierData).then().extract().body().path("id");
        trackId = CreateOrderTest.createOrder(singleOrderData).then().extract().body().path("track");
        orderId = GetOrderByTrackTest.getOrderByTrack(trackId).then().extract().body().path("order.id");
    }

    @Test
    @DisplayName("Check status code of /api/v1/orders/accept/{orderId}?courierId={courierId}") // имя теста
    @Description("Basic test for /api/v1/orders/accept/{orderId}?courierId={courierId} endpoint")
    void acceptOrderCheckStatusCode() {
        shouldDeleteCourier = true; // включаем удаление после теста
        acceptOrder(orderId, courierId)
                .then().statusCode(200);
    }

    @Test
    @DisplayName("Check response of /api/v1/orders/accept/{orderId}?courierId={courierId}") // имя теста
    @Description("Basic test for /api/v1/orders/accept/{orderId}?courierId={courierId} endpoint")
    void acceptOrderCheckResponse() {
        shouldDeleteCourier = true; // включаем удаление после теста
        acceptOrder(orderId, courierId)
                .then().assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Check without courierId of /api/v1/orders/accept/{orderId}") // имя теста
    @Description("Basic test for /api/v1/orders/accept/{orderId} endpoint")
    void acceptOrderCheckWithoutCourierId() {
        shouldDeleteCourier = true; // включаем удаление после теста
        acceptOrderWithoutCourierId(orderId)
                .then().assertThat().body("code", equalTo(400)).body("message", equalTo("Недостаточно данных для поиска")).and().statusCode(400);
    }
    @Test
    @DisplayName("Check without orderId of /api/v1/orders/accept/?courierId={courierId}") // имя теста
    @Description("Basic test for /api/v1/orders/accept/?courierId={courierId} endpoint")
    void acceptOrderCheckWithoutOrderId() {
        shouldDeleteCourier = true; // включаем удаление после теста
        acceptOrderWithoutOrderId(courierId)
                .then().assertThat().body("code", equalTo(400)).body("message", equalTo("Недостаточно данных для поиска")).and().statusCode(400);
    }

    @Test
    @DisplayName("Check with incorrect courierId of /api/v1/orders/accept/{orderId}?courierId=123") // имя теста
    @Description("Basic test for /api/v1/orders/accept/{orderId}?courierId=123 endpoint")
    void acceptOrderCheckWithIncorrectCourierId() {
        shouldDeleteCourier = true; // включаем удаление после теста
        acceptOrderWithIncorrectCourierId(orderId)
                .then().assertThat().body("code", equalTo(404)).body("message", equalTo("Курьера с таким id не существует")).and().statusCode(404);
    }

    @Test
    @DisplayName("Check with incorrect orderId of /api/v1/orders/accept/123?courierId={courierId}") // имя теста
    @Description("Basic test for /api/v1/orders/accept/123?courierId={courierId} endpoint")
    void acceptOrderCheckWithIncorrectOrderId() {
        shouldDeleteCourier = true; // включаем удаление после теста
        acceptOrderWithIncorrectOrderId(courierId)
                .then().assertThat().body("code", equalTo(404)).body("message", equalTo("Заказа с таким id не существует")).and().statusCode(404);
    }

    @AfterEach
    void tearDown() {
        if (shouldDeleteCourier) {
            DeleteCourierTest.deleteCourier(courierId)
                    .then().statusCode(200);
        }
    }

    @Step("Send PUT request to /api/v1/orders/accept/{orderId}?courierId={courierId}")
    public static Response acceptOrder(int orderId, int courierId) {
        Response response = given()
                .queryParam("courierId",courierId)
                .pathParam("orderId", orderId)
                .when()
                .put("/api/v1/orders/accept/{orderId}");
        return response;
    }

    @Step("Send PUT request without courierId to /api/v1/orders/accept/{orderId}")
    public static Response acceptOrderWithoutCourierId(int orderId) {
        Response response = given()
                .pathParam("orderId", orderId)
                .when()
                .put("/api/v1/orders/accept/{orderId}");
        return response;
    }

    @Step("Send PUT request without orderId to /api/v1/orders/accept/?courierId={courierId}")
    public static Response acceptOrderWithoutOrderId(int courierId) {
        Response response = given()
                .queryParam("courierId", courierId)
                .when()
                .put("/api/v1/orders/accept/");
        return response;
    }

    @Step("Send PUT request with incorrect courierId to /api/v1/orders/accept/{orderId}?courierId=123")
    public static Response acceptOrderWithIncorrectCourierId(int orderId) {
        Response response = given()
                .queryParam("courierId",123)
                .pathParam("orderId", orderId)
                .when()
                .put("/api/v1/orders/accept/{orderId}");
        return response;
    }

    @Step("Send PUT request with incorrect orderId to /api/v1/orders/accept/123?courierId={courierId}")
    public static Response acceptOrderWithIncorrectOrderId(int courierId) {
        Response response = given()
                .queryParam("courierId",courierId)
                .pathParam("orderId", 123)
                .when()
                .put("/api/v1/orders/accept/{orderId}");
        return response;
    }
}