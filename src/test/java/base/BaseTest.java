package base;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import utils.ConfigProvider;

public class BaseTest {

    @BeforeAll
    static void setup() {

        Configuration.baseUrl = ConfigProvider.config.uiBaseUrl();
        Configuration.browser = ConfigProvider.config.browser();
        Configuration.headless = ConfigProvider.config.headless();
        Configuration.timeout = ConfigProvider.config.timeoutMs();

        Configuration.screenshots = true;
        Configuration.savePageSource = true;
    }
}
