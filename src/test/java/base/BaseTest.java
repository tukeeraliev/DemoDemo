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

public class BaseTest {

    @BeforeEach
    void setup(TestInfo testInfo) {

        SelenideLogger.removeListener("allure");
        SelenideLogger.addListener("allure",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true)
        );

        Configuration.baseUrl = ConfigProvider.config.uiBaseUrl();
        Configuration.timeout = ConfigProvider.config.timeoutMs();

        // browser: сначала -Dbrowser=..., иначе app.properties
        Configuration.browser = System.getProperty("browser", ConfigProvider.config.browser());

        // 1) Если передали remoteUrl -> работаем через Selenoid
        String remoteUrl = System.getProperty("remoteUrl", System.getenv("remoteUrl"));

        if (remoteUrl != null && !remoteUrl.isBlank()) {

            Configuration.remote = remoteUrl;

            Configuration.timeout = 10000;

            Configuration.remoteConnectionTimeout = 180_000;
            Configuration.remoteReadTimeout = 180_000;

            String browserVersion = System.getProperty("browserVersion");
            if (browserVersion != null && !browserVersion.isBlank()) {
                Configuration.browserVersion = browserVersion;
            }

            Map<String, Object> selenoidOptions = new HashMap<>();
            selenoidOptions.put("enableVideo", true);
            selenoidOptions.put("enableVNC", true);

            String testName = testInfo.getTestClass().get().getSimpleName()
                    + "-" +
                    testInfo.getTestMethod().get().getName();

            String videoFileName = testName + "-" + System.currentTimeMillis() + ".mp4";
            selenoidOptions.put("videoName", videoFileName);

            ChromeOptions options = new ChromeOptions();

            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-gpu");

            options.setCapability("browserName", "chrome");
            options.setCapability("selenoid:options", selenoidOptions);

            Configuration.browserCapabilities = options;

            Configuration.headless = false;
        } else {
            // CI определяется автоматически
            boolean isCi = System.getenv("CI") != null;

            Configuration.headless = isCi || Boolean.parseBoolean(
                    System.getProperty("headless",
                            String.valueOf(ConfigProvider.config.headless()))
            );

            ChromeOptions options = new ChromeOptions();

            if (Configuration.headless) {
                options.addArguments("--headless=new");
            }

            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-features=VizDisplayCompositor");
            options.addArguments("--window-size=1920,1080");

            Configuration.browserCapabilities = options;
        }

        Configuration.screenshots = true;
        Configuration.savePageSource = true;
    }

    @AfterEach
    void tearDown() {
        SelenideLogger.removeListener("allure");
        Selenide.closeWebDriver();
    }
}
