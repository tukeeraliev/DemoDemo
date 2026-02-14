package tests.ui;

import base.BaseTest;
import base.BaseUiTest;
import common.listeners.AllureUiListener;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.TextBoxPage;

@ExtendWith(AllureUiListener.class)
public class TextBoxSmokeTest extends BaseUiTest {

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
