package tests.api;

import api.specs.JsonPlaceholderSpecs;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@Tag("Regression")
public class JsonPlaceholderNegativeTest {

    @Test
    void shouldReturn404ForNonExistingPost() {
        given()
                .spec(JsonPlaceholderSpecs.jsonPlaceholderSpec())
                .when()
                .get("/posts/999999")
                .then()
                .statusCode(404);
    }
}
