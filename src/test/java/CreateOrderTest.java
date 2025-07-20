import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.*;
import static Api.OrderApi.*;

import java.util.Map;

public class CreateOrderTest extends BaseTest {
    @ParameterizedTest
    @MethodSource("Data.OrderData#orderDataProvider")
    public void createOrderWithParameters(Map<String, Object> orderData) {
        createOrder(orderData)
                .then()
                .statusCode(SC_CREATED)
                .body("track", notNullValue());
    }
}