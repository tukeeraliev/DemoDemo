package common.listeners;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class AllureUiListener implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        try {

            if (!WebDriverRunner.hasWebDriverStarted()) {
                return;
            }

            Allure.addAttachment(
                    "Page Source",
                    "text/html",
                    new ByteArrayInputStream(WebDriverRunner.source().getBytes(StandardCharsets.UTF_8)),
                    "html"
            );

            Allure.addAttachment(
                    "URL",
                    "text/plain",
                    new ByteArrayInputStream(WebDriverRunner.url().getBytes(StandardCharsets.UTF_8)),
                    "txt"
            );

        } catch (Exception ignored) {
        }
    }
}
