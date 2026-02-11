package tests.ui;

import base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.TextBoxPage;

public class TextBoxSmokeTest extends BaseTest {

    @Test
    @Tag("Smoke")
    void shouldSubmitTextBoxForm() {

        new TextBoxPage()
                .openPage()
                .fillForm("John", "john@test.com", "addr1", "addr2")
                .submit()
                .verifyResult("John", "john@test.com");
    }
}
