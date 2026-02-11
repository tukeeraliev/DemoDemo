package base;

import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selenide.open;

public abstract class BasePage<T extends BasePage<T>> {

    protected abstract String endpoint();

    @SuppressWarnings("unchecked")
    public T openPage() {
        open(endpoint());
        return (T) this;
    }


    public boolean isOpened() {
        return Selenide.webdriver().driver()
                .url()
                .contains(endpoint());
    }
}


