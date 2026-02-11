package api.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utils.ConfigProvider;

public class ReqresApi {

    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigProvider.config.jsonPlaceholderBaseUrl()) // https://reqres.in
                .setContentType(ContentType.JSON)
                .build();
    }
}
