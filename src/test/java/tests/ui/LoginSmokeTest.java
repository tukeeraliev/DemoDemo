package tests.ui;

import base.BaseTest;
import base.BaseUiTest;
import com.github.javafaker.Faker;
import common.listeners.AllureUiListener;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.LoginPage;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AllureUiListener.class)
public class LoginSmokeTest extends BaseUiTest {

    Faker faker = new Faker();

    @Test
    void shouldShowErrorForInvalidCredentials() {

        LoginPage page = new LoginPage()
                .openPage()
                .shouldBeOpened()
                .setUserName(faker.name().username())
                .setPassword(faker.internet().password())
                .clickLogin();

        assertThat(page.isOpened()).isTrue();

    }
}
