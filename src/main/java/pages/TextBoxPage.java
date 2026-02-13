package pages;

import base.BasePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class TextBoxPage extends BasePage<TextBoxPage> {

    @Override
    protected String endpoint() {
        return "/text-box";
    }

    @Step("Заполнить форму TextBox: имя={name}, email={email}")
    public TextBoxPage fillForm(String name, String email,
                                String currentAddress, String permanentAddress) {

        $("#userName").setValue(name);
        $("#userEmail").setValue(email);
        $("#currentAddress").setValue(currentAddress);
        $("#permanentAddress").setValue(permanentAddress);

        return this;
    }

    @Step("Отправить форму")
    public TextBoxPage submit() {
        $("#submit").scrollTo().click();
        return this;
    }

    @Step("Проверить результат: имя={name}, email={email}")
    public TextBoxPage verifyResult(String name, String email) {
        $("#output").shouldBe(visible);
        $("#name").shouldHave(text(name));
        $("#email").shouldHave(text(email));
        return this;
    }
}
