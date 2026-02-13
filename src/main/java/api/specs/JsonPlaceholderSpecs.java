package api.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utils.ConfigProvider;
import static io.restassured.filter.log.LogDetail.ALL;

public class JsonPlaceholderSpecs {

    public static RequestSpecification jsonPlaceholderSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigProvider.config.jsonPlaceholderBaseUrl())
                .setContentType(ContentType.JSON)
                .log(ALL)
                .addFilter(AllureFilter.filter())
                .build();
    }
}
