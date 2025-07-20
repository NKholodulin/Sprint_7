package Api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import Data.CourierData;

public class CourierApi {
    static final String CREATE_COURIER_API = "/api/v1/courier";
    static final String LOGIN_COURIER_API = "/api/v1/courier/login";
    static final String DELETE_COURIER_API = "/api/v1/courier/{courierId}";
    // метод для шага "Создать курьера":
    @Step("Send POST request to /api/v1/courier")
    public static Response createCourier(CourierData createCourierData) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(createCourierData)
                .when()
                .post(CREATE_COURIER_API);
        return response;
    }

    @Step("Send POST request to /api/v1/courier/login")
    public static Response loginCourier(CourierData createCourierData) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(createCourierData)
                .when()
                .post(LOGIN_COURIER_API);
        return response;
    }

    @Step("Send DELETE request to /api/v1/courier/{courierId}")
    public static Response deleteCourier(int courierId) {
        Response response = given()
                .delete(DELETE_COURIER_API, courierId);
        return response;
    }

    @Step("Send DELETE request to /api/v1/courier/courierId")
    public static Response deleteCourierWithoutId() {
        Response response = given()
                .delete(CREATE_COURIER_API);
        return response;
    }
}
