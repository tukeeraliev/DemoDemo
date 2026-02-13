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
        Configuration.timeout = ConfigProvider.config.timeoutMs();

        // browser: сначала -Dbrowser=..., иначе app.properties
        Configuration.browser = System.getProperty("browser", ConfigProvider.config.browser());

        // 1) Если передали remoteUrl -> работаем через Selenoid
        String remoteUrl = System.getProperty("remoteUrl");

        if (remoteUrl != null && !remoteUrl.isBlank()) {

            Configuration.remote = remoteUrl;

            Configuration.remoteConnectionTimeout = 180_000; // 3 минуты
            Configuration.remoteReadTimeout = 180_000;       // 3 минуты

            // browserVersion: ТОЛЬКО из system property (чтобы app.properties не ломал CI)
            String browserVersion = System.getProperty("browserVersion");
            if (browserVersion != null && !browserVersion.isBlank()) {
                Configuration.browserVersion = browserVersion;
            }

            // Selenoid capabilities
            Map<String, Object> selenoidOptions = new HashMap<>();
            selenoidOptions.put("enableVideo", false);
            // если надо будет VNC:
            // selenoidOptions.put("enableVNC", true);

            ChromeOptions options = new ChromeOptions();
            options.setCapability("selenoid:options", selenoidOptions);
            Configuration.browserCapabilities = options;

            // для remote headless не нужен
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
}
