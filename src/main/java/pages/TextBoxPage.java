package pages;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class TextBoxPage {

    public void openPage() {
        open("/text-box");
    }

    public void fillForm(String name, String email, String currentAddress, String permanentAddress) {
        $("#userName").setValue(name);
        $("#userEmail").setValue(email);
        $("#currentAddress").setValue(currentAddress);
        $("#permanentAddress").setValue(permanentAddress);
    }

    public void submit() {
        $("#submit").scrollTo().click();
    }

    public void verifyResult(String name, String email) {
        $("#output").shouldBe(visible);
        $("#name").shouldHave(text(name));
        $("#email").shouldHave(text(email));
    }
}
