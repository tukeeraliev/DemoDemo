package base;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.ConfigProvider;

import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    @BeforeAll
    static void setup() {

        Configuration.baseUrl = ConfigProvider.config.uiBaseUrl();
        Configuration.browser = ConfigProvider.config.browser();
        Configuration.timeout = ConfigProvider.config.timeoutMs();

        // 1) Если передали remoteUrl -> работаем через Selenoid
        String remoteUrl = System.getProperty("remoteUrl", ConfigProvider.config.remoteUrl());

        if (remoteUrl != null && !remoteUrl.isBlank()) {

            Configuration.remote = remoteUrl;

            Configuration.remoteConnectionTimeout = 180000; // 3 минуты
            Configuration.remoteReadTimeout = 180000;       // 3 минуты


            // версия браузера под browsers.json (121.0)
            String browserVersion = System.getProperty("browserVersion", ConfigProvider.config.browserVersion());
            if (browserVersion != null && !browserVersion.isBlank()) {
                Configuration.browserVersion = browserVersion;
            }

            // Selenoid capabilities
            Map<String, Object> selenoidOptions = new HashMap<>();
            selenoidOptions.put("enableVideo", false);

            ChromeOptions options = new ChromeOptions();
            options.setCapability("selenoid:options", selenoidOptions);

            Configuration.browserCapabilities = options;

            // для remote headless НЕ нужен
            Configuration.headless = false;

        } else {
            // 2) Локальный запуск
            Configuration.headless = Boolean.parseBoolean(
                    System.getProperty("headless", String.valueOf(ConfigProvider.config.headless()))
            );

            ChromeOptions options = new ChromeOptions();

            if (Configuration.headless) {
                options.addArguments("--headless=new");
            }

            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-features=VizDisplayCompositor");
            options.addArguments("--window-size=1920,1080");

            Configuration.browserCapabilities = options;
        }

        Configuration.screenshots = true;
        Configuration.savePageSource = true;
    }
}
