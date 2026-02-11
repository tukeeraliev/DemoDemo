package base;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import utils.ConfigProvider;

public class BaseTest {

    @BeforeAll
    static void setup() {

        Configuration.baseUrl = ConfigProvider.config.uiBaseUrl();
        Configuration.browser = ConfigProvider.config.browser();
        Configuration.timeout = ConfigProvider.config.timeoutMs();

        Configuration.headless =
                Boolean.parseBoolean(System.getProperty(
                        "headless",
                        String.valueOf(ConfigProvider.config.headless())
                ));

        Configuration.screenshots = true;
        Configuration.savePageSource = true;
    }
}
