package api.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utils.ConfigProvider;
import static io.restassured.filter.log.LogDetail.ALL;

public class ReqresSpecs {

    public static RequestSpecification reqresRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigProvider.config.reqresBaseUrl())
                .setContentType(ContentType.JSON)
                .log(ALL)
                .addFilter(AllureFilter.filter())
                .build();
    }
}

