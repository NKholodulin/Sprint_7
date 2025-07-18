import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.*;

public class GetOrderByTrackTest extends ApiSteps {
    private int trackId;
    private Map<String, Object> singleOrderData = OrderData.orderDataProvider().findFirst().orElseThrow();

    @BeforeEach
    public void setUp() {
        super.setUp();
        trackId = createOrder(singleOrderData).then().extract().body().path("track");
    }

    @Test
    @DisplayName("Check status code of /api/v1/orders/track?t={trackId}") // имя теста
    @Description("Basic test for /api/v1/orders/track?t={trackId} endpoint")
    void getOrderByTrackCheckStatusCode() {
        getOrderByTrack(trackId)
                .then().statusCode(SC_OK);
    }

    @Test
    @DisplayName("Check response of /api/v1/orders/track?t={trackId}") // имя теста
    @Description("Basic test for /api/v1/orders/track?t={trackId} endpoint")
    void getOrderByTrackCheckResponse() {
        getOrderByTrack(trackId)
                .then().body("order", notNullValue()).body("order.track", notNullValue()).body("order.id", notNullValue());
    }

    @Test
    @DisplayName("Check without trackId of /api/v1/orders/track") // имя теста
    @Description("Negative test for /api/v1/orders/track endpoint")
    void getOrderByTrackCheckWithoutTrackId() {
        getOrderByTrackWithoutTrackId()
                .then().assertThat().body("code", equalTo(SC_BAD_REQUEST)).body("message", equalTo("Недостаточно данных для поиска")).and().statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Check without trackId of /api/v1/orders/track") // имя теста
    @Description("Negative test for /api/v1/orders/track endpoint")
    void getOrderByTrackCheckWithIncorrectTrackId() {
        getOrderByTrack(123)
                .then().assertThat().body("code", equalTo(SC_NOT_FOUND)).body("message", equalTo("Заказ не найден")).and().statusCode(SC_NOT_FOUND);
    }
}
