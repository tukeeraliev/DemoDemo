package pages;

import base.BasePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage extends BasePage<LoginPage> {

    @Override
    protected String endpoint() {
        return "/login";
    }

    public LoginPage openPage() {
        open(endpoint());
        return this;
    }

    public LoginPage setUserName(String username) {
        $("#userName").setValue(username);
        return this;
    }

    public LoginPage setPassword(String password) {
        $("#password").setValue(password);
        return this;
    }

    public LoginPage clickLogin() {
        $("#login").click();
        return this;
    }

    public LoginPage verifyLoginErrorVisible() {
        $("#name").shouldBe(visible);
        return this;
    }

    public LoginPage verifyStillOnLoginPage() {
        $("#login").shouldBe(visible);
        return this;
    }

}
