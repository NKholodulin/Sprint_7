import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;

public class ListOrdersTest extends ApiSteps {
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
}
