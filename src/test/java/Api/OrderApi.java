package Api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class OrderApi {

    static final String CREATE_ORDER_API = "/api/v1/orders";
    static final String TRACK_ORDER_API = "/api/v1/orders/track";
    static final String ACCEPT_ORDER_API = "/api/v1/orders/accept/{orderId}";
    static final String ACCEPT_ORDER_API_WITHOUT_ORDER_ID = "/api/v1/orders/accept/";

    @Step("Send POST request to /api/v1/orders")
    public static Response createOrder(Map<String, Object> orderData) {
        Response response = given()
                .contentType("application/json")
                .body(orderData)
                .when()
                .post(CREATE_ORDER_API);
        return response;
    }

    @Step("Send GET request to /api/v1/orders/track?t={trackId}")
    public static Response getOrderByTrack(int trackId) {
        Response response = given()
                .queryParam("t", trackId)
                .when()
                .get(TRACK_ORDER_API);
        return response;
    }

    @Step("Send GET request to /api/v1/orders/track")
    public static Response getOrderByTrackWithoutTrackId() {
        Response response = given()
                .when()
                .get(TRACK_ORDER_API);
        return response;
    }

    @Step("Send PUT request to /api/v1/orders/accept/{orderId}?courierId={courierId}")
    public static Response acceptOrder(int orderId, int courierId) {
        Response response = given()
                .queryParam("courierId",courierId)
                .pathParam("orderId", orderId)
                .when()
                .put(ACCEPT_ORDER_API);
        return response;
    }

    @Step("Send PUT request without courierId to /api/v1/orders/accept/{orderId}")
    public static Response acceptOrderWithoutCourierId(int orderId) {
        Response response = given()
                .pathParam("orderId", orderId)
                .when()
                .put(ACCEPT_ORDER_API);
        return response;
    }

    @Step("Send PUT request without orderId to /api/v1/orders/accept/?courierId={courierId}")
    public static Response acceptOrderWithoutOrderId(int courierId) {
        Response response = given()
                .queryParam("courierId", courierId)
                .when()
                .put(ACCEPT_ORDER_API_WITHOUT_ORDER_ID);
        return response;
    }

    @Step("Send PUT request with incorrect courierId to /api/v1/orders/accept/{orderId}?courierId=123")
    public static Response acceptOrderWithIncorrectCourierId(int orderId) {
        Response response = given()
                .queryParam("courierId",123)
                .pathParam("orderId", orderId)
                .when()
                .put(ACCEPT_ORDER_API);
        return response;
    }

    @Step("Send PUT request with incorrect orderId to /api/v1/orders/accept/123?courierId={courierId}")
    public static Response acceptOrderWithIncorrectOrderId(int courierId) {
        Response response = given()
                .queryParam("courierId",courierId)
                .pathParam("orderId", 123)
                .when()
                .put(ACCEPT_ORDER_API);
        return response;
    }

    @Step("Send GET request to /api/v1/orders")
    public static Response listOrders() {
        Response response = given()
                .when()
                .get(CREATE_ORDER_API);
        return response;
    }
}
