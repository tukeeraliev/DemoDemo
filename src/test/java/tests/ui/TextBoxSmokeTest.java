package tests.ui;

import base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.TextBoxPage;

@Tag("Smoke")
public class TextBoxSmokeTest extends BaseTest {

    TextBoxPage page = new TextBoxPage();

    @Test
    void shouldSubmitTextBoxForm() {

        page.openPage();

        page.fillForm(
                "Tugolbai",
                "tukeeraliev@gmail.com",
                "Bishkek",
                "Karakol"
        );

        page.submit();

        page.verifyResult("Tugolbai", "tukeeraliev@gmail.com");
    }
}
