package tests.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import utils.ConfigProvider;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Tag("Regression")
public class JsonPlaceholderRegressionTest {

    @Test
    void shouldCreatePost() {
        RestAssured.baseURI = ConfigProvider.config.apiBaseUrl();

        String body = """
            {
              "title": "hello",
              "body": "world",
              "userId": 1
            }
            """;

        given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("title", equalTo("hello"))
                .body("userId", equalTo(1));
    }

    @Test
    void shouldUpdatePost() {
        RestAssured.baseURI = ConfigProvider.config.apiBaseUrl();

        String body = """
            {
              "id": 1,
              "title": "updated",
              "body": "text",
              "userId": 1
            }
            """;

        given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("updated"))
                .body("id", equalTo(1));
    }

    @Test
    void shouldDeletePost() {
        RestAssured.baseURI = ConfigProvider.config.apiBaseUrl();

        given()
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(anyOf(is(200), is(204)));
    }
}
