import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.*;
import static Api.OrderApi.*;

public class ListOrdersTest extends BaseTest {
    @Test
    @DisplayName("Check body and status code of /api/v1/orders") // имя теста
    @Description("Basic test for /api/v1/orders endpoint")
    void listOrdersResponseAndStatusCode() {
        listOrders()
                .then()
                .body("orders", notNullValue())
                .body("orders", instanceOf(List.class))
                .statusCode(SC_OK);
    }
}