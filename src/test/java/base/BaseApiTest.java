package base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import utils.ConfigProvider;

/**
 * BaseApiTest — база ТОЛЬКО для API тестов.
 *
 * Зачем отдельный класс:
 * 1) Чтобы API тесты НЕ тянули UI конфиг (Selenide/ChromeOptions/Browser)
 * 2) Чтобы CI/Docker прогон API был стабильным и быстрым
 *
 * Важно:
 * - Здесь НЕТ Selenide
 * - Здесь НЕТ ChromeOptions
 * - Здесь НЕТ SelenideLogger
 */
public class BaseApiTest {

    @BeforeEach
    protected void setupApi() {

        // =========================
        // 1) BASE URL API
        // =========================
        // Должен быть отдельный параметр в твоём конфиге (например apiBaseUrl)
        // Пример: https://demoqa.com (если API там) или https://petstore.swagger.io и т.п.
        //
        // Если у тебя пока нет apiBaseUrl в ConfigProvider — добавь.
        RestAssured.baseURI = ConfigProvider.config.apiBaseUrl();

        // =========================
        // 2) TIMEOUTS
        // =========================
        // Полезно для CI, чтобы тесты не зависали бесконечно
        // Если у тебя уже есть timeoutMs, можно использовать его.
        int timeoutMs = (int) Math.max(ConfigProvider.config.timeoutMs(), 10_000);

        RestAssured.config = RestAssured.config()
                .httpClient(io.restassured.config.HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", timeoutMs)
                        .setParam("http.socket.timeout", timeoutMs)
                        .setParam("http.connection-manager.timeout", (long) timeoutMs)
                );

        // =========================
        // 3) LOGGING + ALLURE
        // =========================
        // AllureRestAssured цепляется как filter и будет добавлять шаги/запросы/ответы в Allure.
        // Это НЕ влияет на UI и не конфликтует с твоим AllureSelenide.
        //
        // Чтобы не накапливать фильтры в процессе запуска множества тестов,
        // сначала очищаем filters.
        RestAssured.filters().clear();
        RestAssured.filters(new AllureRestAssured());

        // =========================
        // 4) DEFAULTS (опционально)
        // =========================
        // Если у тебя API всегда JSON — можно включить так:
        // RestAssured.requestSpecification = new RequestSpecBuilder()
        //        .setContentType(ContentType.JSON)
        //        .setAccept(ContentType.JSON)
        //        .build();
    }
}
