package pages;

import base.BasePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage<LoginPage> {

    @Override
    protected String endpoint() {
        return "/profile";
    }

    @Step("Перейти на Login со страницы Book Store")
    public LoginPage goToLoginFromBooks() {
        // На /books есть кнопка Login с id=login
        $("#login").shouldBe(visible).click();
        return this;
    }

    @Step("Дождаться, что страница логина открыта")
    public LoginPage shouldBeOpened() {
        $("#userName").shouldBe(visible);
        $("#password").shouldBe(visible);
        return this;
    }

    @Step("Ввести username: {username}")
    public LoginPage setUserName(String username) {
        $("#userName").setValue(username);
        return this;
    }

    @Step("Ввести пароль (masked)")
    public LoginPage setPassword(String password) {
        $("#password").setValue(password);
        return this;
    }

    @Step("Нажать кнопку Login")
    public LoginPage clickLogin() {
        $("#login").click();
        return this;
    }

    @Step("Проверить что отображается ошибка логина")
    public LoginPage verifyLoginErrorVisible() {
        $("#name").shouldBe(visible);
        return this;
    }

    @Step("Проверить что остались на странице логина")
    public LoginPage verifyStillOnLoginPage() {
        $("#login").shouldBe(visible);
        return this;
    }

    public boolean isOpened() {
        return $("#userName").is(visible);
    }
}
