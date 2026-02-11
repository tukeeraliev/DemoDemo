package tests.ui;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import pages.LoginPage;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginSmokeTest extends BaseTest {

    @Test
    void shouldShowErrorForInvalidCredentials() {

        LoginPage page = new LoginPage()
                .openPage()
                .setUserName("Tom")
                .setPassword("12345")
                .clickLogin()
                .verifyStillOnLoginPage();

        assertThat(page.isOpened()).isTrue();

    }
}
