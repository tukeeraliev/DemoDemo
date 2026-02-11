package base;

import static com.codeborne.selenide.WebDriverRunner.url;

public abstract class BasePage<T extends BasePage<T>> {

    public abstract T waitForPageLoaded();

    public boolean isPageLoaded(String endpoint) {
        return url().contains(endpoint);
    }
}

