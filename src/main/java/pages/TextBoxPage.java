package pages;

import base.BasePage;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class TextBoxPage extends BasePage<TextBoxPage> {

    @Override
    protected String endpoint() {
        return "/text-box";
    }

    public TextBoxPage fillForm(String name, String email,
                                String currentAddress, String permanentAddress) {

        $("#userName").setValue(name);
        $("#userEmail").setValue(email);
        $("#currentAddress").setValue(currentAddress);
        $("#permanentAddress").setValue(permanentAddress);

        return this;
    }

    public TextBoxPage submit() {
        $("#submit").scrollTo().click();
        return this;
    }

    public TextBoxPage verifyResult(String name, String email) {
        $("#output").shouldBe(visible);
        $("#name").shouldHave(text(name));
        $("#email").shouldHave(text(email));
        return this;
    }
}
