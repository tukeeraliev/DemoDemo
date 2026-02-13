package base;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.open;

public abstract class BasePage<T extends BasePage<T>> {

    protected abstract String endpoint();

    @Step("Open page")
    @SuppressWarnings("unchecked")
    public T openPage() {
        open(endpoint());
        return (T) this;
    }


    @Step("Проверить что страница {this.endpoint} открыта")
    public boolean isOpened() {
        return Selenide.webdriver().driver()
                .url()
                .contains(endpoint());
    }
}
