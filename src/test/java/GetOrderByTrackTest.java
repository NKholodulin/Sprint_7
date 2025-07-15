import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetOrderByTrackTest {

    @Step("Send GET request to /api/v1/orders/track?t={trackId}")
    public static Response getOrderByTrack(int trackId) {
        Response response = given()
                .queryParam("t", trackId)
                .when()
                .get("/api/v1/orders/track");
        return response;
    }
}
