package tests.ui;

import base.BaseTest;
import base.BaseUiTest;
import common.listeners.AllureUiListener;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.LoginPage;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("Regression")
@ExtendWith(AllureUiListener.class)
public class LoginRegressionTest extends BaseUiTest {

    @Test
    void shouldStayOnLoginPageWhenSubmittingEmptyCredentials() {

        LoginPage page = new LoginPage();

        page.openPage()
                .goToLoginFromBooks()
                .shouldBeOpened()
                .clickLogin()
                .verifyStillOnLoginPage();

        assertThat(page.isOpened()).isTrue();
    }

    @Test
    void shouldStayOnLoginPageWhenOnlyUsernameProvided() {

        LoginPage page = new LoginPage();

        page.openPage()
                .setUserName("wrong_user")
                .clickLogin()
                .verifyStillOnLoginPage();

        assertThat(page.isOpened()).isTrue();
    }

    @Test
    void shouldStayOnLoginPageWhenOnlyPasswordProvided() {

        LoginPage page = new LoginPage();

        page.openPage()
                .setPassword("wrong_pass")
                .clickLogin()
                .verifyStillOnLoginPage();

        assertThat(page.isOpened()).isTrue();
    }
}
