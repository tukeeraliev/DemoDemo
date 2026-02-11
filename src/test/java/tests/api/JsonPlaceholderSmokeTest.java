package tests.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import utils.ConfigProvider;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Tag("Smoke")
public class JsonPlaceholderSmokeTest {

    @Test
    void shouldGetPostsList() {
        RestAssured.baseURI = ConfigProvider.config.jsonPlaceholderBaseUrl();

        given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }
}
