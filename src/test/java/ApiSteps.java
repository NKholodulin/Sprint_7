import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiSteps extends BaseApi{
    // метод для шага "Создать курьера":
    @Step("Send POST request to /api/v1/courier")
    public static Response createCourier(CourierData createCourierData) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(createCourierData)
                .when()
                .post("/api/v1/courier");
        return response;
    }

    @Step("Send POST request to /api/v1/courier/login")
    public static Response loginCourier(CourierData createCourierData) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(createCourierData)
                .when()
                .post("/api/v1/courier/login");
        return response;
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
    @Step("Send POST request to /api/v1/orders")
    public static Response createOrder(Map<String, Object> orderData) {
        Response response = given()
                .contentType("application/json")
                .body(orderData)
                .when()
                .post("/api/v1/orders");
        return response;
    }

    @Step("Send GET request to /api/v1/orders/track?t={trackId}")
    public static Response getOrderByTrack(int trackId) {
        Response response = given()
                .queryParam("t", trackId)
                .when()
                .get("/api/v1/orders/track");
        return response;
    }

    @Step("Send GET request to /api/v1/orders/track")
    public static Response getOrderByTrackWithoutTrackId() {
        Response response = given()
                .when()
                .get("/api/v1/orders/track");
        return response;
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

    @Step("Send GET request to /api/v1/orders")
    public static Response listOrders() {
        Response response = given()
                .when()
                .get("/api/v1/orders");
        return response;
    }
}
