import io.qameta.allure.Description;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.apache.http.HttpStatus.*;

public class AcceptOrderTest extends ApiSteps {
    CourierData createCourierData = new CourierData("holodTest", "1234", "holod");
    private boolean shouldDeleteCourier = false;
    private int courierId;
    private int orderId;
    private int trackId;
    private Map<String, Object> singleOrderData = OrderData.orderDataProvider().findFirst().orElseThrow();
    @BeforeEach
    public void setUp() {
        super.setUp();
        createCourier(createCourierData).then().statusCode(SC_CREATED);
        courierId = loginCourier(createCourierData).then().extract().body().path("id");
        trackId = createOrder(singleOrderData).then().extract().body().path("track");
        orderId = getOrderByTrack(trackId).then().extract().body().path("order.id");
    }

    @Test
    @DisplayName("Check status code of /api/v1/orders/accept/{orderId}?courierId={courierId}") // имя теста
    @Description("Basic test for /api/v1/orders/accept/{orderId}?courierId={courierId} endpoint")
    void acceptOrderCheckStatusCode() {
        shouldDeleteCourier = true; // включаем удаление после теста
        acceptOrder(orderId, courierId)
                .then().statusCode(SC_OK);
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
                .then().assertThat().body("code", equalTo(SC_BAD_REQUEST)).body("message", equalTo("Недостаточно данных для поиска")).and().statusCode(SC_BAD_REQUEST);
    }
    @Test
    @DisplayName("Check without orderId of /api/v1/orders/accept/?courierId={courierId}") // имя теста
    @Description("Basic test for /api/v1/orders/accept/?courierId={courierId} endpoint")
    void acceptOrderCheckWithoutOrderId() {
        shouldDeleteCourier = true; // включаем удаление после теста
        acceptOrderWithoutOrderId(courierId)
                .then().assertThat().body("code", equalTo(SC_BAD_REQUEST)).body("message", equalTo("Недостаточно данных для поиска")).and().statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Check with incorrect courierId of /api/v1/orders/accept/{orderId}?courierId=123") // имя теста
    @Description("Basic test for /api/v1/orders/accept/{orderId}?courierId=123 endpoint")
    void acceptOrderCheckWithIncorrectCourierId() {
        shouldDeleteCourier = true; // включаем удаление после теста
        acceptOrderWithIncorrectCourierId(orderId)
                .then().assertThat().body("code", equalTo(SC_NOT_FOUND)).body("message", equalTo("Курьера с таким id не существует")).and().statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Check with incorrect orderId of /api/v1/orders/accept/123?courierId={courierId}") // имя теста
    @Description("Basic test for /api/v1/orders/accept/123?courierId={courierId} endpoint")
    void acceptOrderCheckWithIncorrectOrderId() {
        shouldDeleteCourier = true; // включаем удаление после теста
        acceptOrderWithIncorrectOrderId(courierId)
                .then().assertThat().body("code", equalTo(SC_NOT_FOUND)).body("message", equalTo("Заказа с таким id не существует")).and().statusCode(SC_NOT_FOUND);
    }

    @AfterEach
    void tearDown() {
        if (shouldDeleteCourier) {
            DeleteCourierTest.deleteCourier(courierId)
                    .then().statusCode(SC_OK);
        }
    }
}