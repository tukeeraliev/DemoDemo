package api.specs;

import io.qameta.allure.restassured.AllureRestAssured;

public class AllureFilter {
    public static AllureRestAssured filter() {
        return new AllureRestAssured();
    }
}
