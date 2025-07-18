import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

public class BaseApi {
    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }
}