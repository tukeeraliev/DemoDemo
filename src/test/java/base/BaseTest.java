package base;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.ConfigProvider;

public class BaseTest {

    @BeforeAll
    static void setup() {

        Configuration.baseUrl = ConfigProvider.config.uiBaseUrl();
        Configuration.browser = ConfigProvider.config.browser();
        Configuration.timeout = ConfigProvider.config.timeoutMs();

        Configuration.headless =
                Boolean.parseBoolean(
                        System.getProperty(
                                "headless",
                                String.valueOf(ConfigProvider.config.headless())
                        )
                );

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--headless=new");   // важно для новых версий Chrome
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        Configuration.browserCapabilities = options;


        Configuration.screenshots = true;
        Configuration.savePageSource = true;
    }
}
