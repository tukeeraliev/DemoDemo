package api.client;

import api.models.UserResponse;
import api.specs.ReqresSpecs;

import static io.restassured.RestAssured.given;

public class ReqresClient {

    public UserResponse getUsers(int page) {
        return given()
                .spec(ReqresSpecs.reqresRequestSpec())
                .queryParam("page", page)
                .when()
                .get("/api/users")
                .then()
                .statusCode(200)
                .extract()
                .as(UserResponse.class);
    }
}
