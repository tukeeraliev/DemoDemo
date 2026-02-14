package common.listeners;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Screenshots.takeScreenShotAsFile;

public class AllureUiListener implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        try {

            // Screenshot
            var screenshot = takeScreenShotAsFile();
            if (screenshot != null && screenshot.exists()) {
                Allure.addAttachment(
                        "Screenshot",
                        "image/png",
                        new FileInputStream(screenshot),
                        "png"
                );
            }

            // Page Source
            String source = Selenide.webdriver().driver().source();
            Allure.addAttachment(
                    "Page Source",
                    "text/html",
                    new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8)),
                    "html"
            );

            // Current URL
            String url = Selenide.webdriver().driver().url();
            Allure.addAttachment(
                    "URL",
                    "text/plain",
                    new ByteArrayInputStream(url.getBytes(StandardCharsets.UTF_8)),
                    "txt"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
