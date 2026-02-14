package base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.ConfigProvider;

import java.util.HashMap;
import java.util.Map;

public class BaseUiTest {

    @BeforeEach
    protected void setupUi(TestInfo testInfo) {

        // ✅ Один источник Allure-логирования для UI:
        // Делает скрин + html page source. Этого достаточно.
        // (Это заменяет твой кастомный AllureUiListener и двойные скрины.)
        SelenideLogger.removeListener("allure");
        SelenideLogger.addListener("allure",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true)
        );

        // ✅ baseUrl должен быть ТОЛЬКО домен:
        // https://demoqa.com  (а endpoint добавится в страницах: /login)
        Configuration.baseUrl = ConfigProvider.config.uiBaseUrl();

        // ✅ demoqa часто флейковый — 8 секунд мало
        // Если у тебя в конфиге меньше — ставь минимум 20000
        Configuration.timeout = Math.max(ConfigProvider.config.timeoutMs(), 20_000);

        // ✅ выбор браузера: -Dbrowser=... или из конфига
        Configuration.browser = System.getProperty("browser", ConfigProvider.config.browser());

        // ❌ НЕ включаем второй раз скриншоты и pageSource:
        // Они уже включены в AllureSelenide listener выше.
        // Configuration.screenshots = true;
        // Configuration.savePageSource = true;

        // ✅ remoteUrl: если задан — работаем через Selenoid/Grid
        String remoteUrl = System.getProperty("remoteUrl", System.getenv("remoteUrl"));

        // ======================
        // REMOTE (Selenoid/Grid)
        // ======================
        if (remoteUrl != null && !remoteUrl.isBlank()) {
            Configuration.remote = remoteUrl;

            // Длинные таймауты для remote — нормально
            Configuration.remoteConnectionTimeout = 180_000;
            Configuration.remoteReadTimeout = 180_000;

            // Версия браузера (если передаёшь)
            String browserVersion = System.getProperty("browserVersion");
            if (browserVersion != null && !browserVersion.isBlank()) {
                Configuration.browserVersion = browserVersion;
            }

            // Настройки Selenoid (видео/VNC)
            Map<String, Object> selenoidOptions = new HashMap<>();
            selenoidOptions.put("enableVideo", true);
            selenoidOptions.put("enableVNC", true);

            // Имя видео = имя теста + timestamp
            String testName = testInfo.getTestClass().get().getSimpleName()
                    + "-" + testInfo.getTestMethod().get().getName();
            selenoidOptions.put("videoName", testName + "-" + System.currentTimeMillis() + ".mp4");

            ChromeOptions options = new ChromeOptions();

            // ✅ Минимально-стабильные флаги
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");

            // ✅ Чистим окружение: расширения иногда ломают/вмешиваются
            options.addArguments("--disable-extensions");
            options.addArguments("--no-first-run");
            options.addArguments("--no-default-browser-check");

            options.setCapability("browserName", "chrome");
            options.setCapability("selenoid:options", selenoidOptions);

            Configuration.browserCapabilities = options;

            // remote обычно не headless (чтобы VNC работал)
            Configuration.headless = false;
            return;
        }

        // ==========
        // LOCAL
        // ==========
        boolean isCi = System.getenv("CI") != null;

        // ✅ headless: CI=true или -Dheadless=true или конфиг
        Configuration.headless = isCi || Boolean.parseBoolean(
                System.getProperty("headless", String.valueOf(ConfigProvider.config.headless()))
        );

        ChromeOptions options = new ChromeOptions();

        // ✅ Минимально-стабильные флаги (без “опасных”)
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");

        // ✅ Чистим Chrome окружение (часто лечит “TITLE=Error” из-за вмешательств)
        options.addArguments("--disable-extensions");
        options.addArguments("--incognito");
        options.addArguments("--no-first-run");
        options.addArguments("--no-default-browser-check");

        if (Configuration.headless) {
            options.addArguments("--headless=new");
        }

        // ❌ УБРАНО, потому что может крашить/флейкать на новых Chrome:
        // --disable-features=VizDisplayCompositor
        //
        // ❌ УБРАНО, потому что обычно не нужно и иногда ломает:
        // --remote-allow-origins=*

        Configuration.browserCapabilities = options;
    }

    @AfterEach
    protected void tearDownUi() {
        // ✅ снимаем listener и закрываем драйвер после каждого UI теста
        SelenideLogger.removeListener("allure");
        Selenide.closeWebDriver();
    }
}
