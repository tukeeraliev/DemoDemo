package tests.ui;

import base.BaseTest;
import base.BaseUiTest;
import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import common.listeners.AllureUiListener;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.LoginPage;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AllureUiListener.class)
public class LoginExtendedTest extends BaseUiTest {

    Faker faker = new Faker();

    @Test
    void shouldShowErrorWhenInvalidCredentialsProvided() {

        LoginPage page = new LoginPage();

        page
                .openPage()
                .setUserName(faker.name().username())
                .setPassword(faker.internet().password())
                .clickLogin()
                .verifyStillOnLoginPage();

        assertThat(page.isOpened()).isTrue();
    }

    @Test
    void shouldStayOnLoginPageWhenUsernameIsVeryLong() {

        LoginPage page = new LoginPage();

        page
                .openPage()
                .shouldBeOpened()
                .setUserName("u".repeat(200))
                .setPassword(faker.internet().password())
                .clickLogin()
                .verifyStillOnLoginPage();

        assertThat(page.isOpened()).isTrue();
    }

    @Test
    void shouldStayOnLoginPageWhenPasswordIsVeryLong() {

        LoginPage page = new LoginPage();

        page
                .openPage()
                .setUserName(faker.name().username())
                .setPassword("p".repeat(200))
                .clickLogin()
                .verifyStillOnLoginPage();

        assertThat(page.isOpened()).isTrue();
    }

    @Test
    void shouldStayOnLoginPageWhenUsernameContainsSpecialCharacters() {

        LoginPage page = new LoginPage()
                .openPage()
                .setUserName("!@#$%^&*")
                .setPassword(faker.internet().password())
                .clickLogin()
                .verifyStillOnLoginPage();

        assertThat(page.isOpened()).isTrue();
    }

    @Test
    void shouldStayOnLoginPageWhenPasswordContainsSpecialCharacters() {

        LoginPage page = new LoginPage()
                .openPage()
                .setUserName(faker.name().username())
                .setPassword("!@#$%^&*")
                .clickLogin()
                .verifyStillOnLoginPage();

        assertThat(page.isOpened()).isTrue();
    }

    @Test
    void shouldStayOnLoginPageWhenUsernameContainsUnicode() {

        LoginPage page = new LoginPage()
                .openPage()
                .setUserName("тест")
                .setPassword(faker.internet().password())
                .clickLogin()
                .verifyStillOnLoginPage();

        assertThat(page.isOpened()).isTrue();
    }

    @Test
    void shouldStayOnLoginPageWhenSqlInjectionAttemptUsed() {

        LoginPage page = new LoginPage()
                .openPage()
                .setUserName("' OR 1=1 --")
                .setPassword(faker.internet().password())
                .clickLogin()
                .verifyStillOnLoginPage();

        assertThat(page.isOpened()).isTrue();
    }

    @Test
    void shouldStayOnLoginPageWhenXssPayloadUsed() {

        LoginPage page = new LoginPage()
                .openPage()
                .setUserName("<script>alert(1)</script>")
                .setPassword(faker.internet().password())
                .clickLogin()
                .verifyStillOnLoginPage();

        assertThat(page.isOpened()).isTrue();
    }

    @Test
    void shouldShowErrorAfterMultipleLoginAttempts() {

        LoginPage page = new LoginPage().openPage();

        page
                .setUserName(faker.name().username())
                .setPassword(faker.internet().password())
                .clickLogin();

        page
                .setUserName(faker.name().username())
                .setPassword(faker.internet().password())
                .clickLogin()
                .verifyStillOnLoginPage();

        assertThat(page.isOpened()).isTrue();
    }

    @Test
    void shouldStayOnLoginPageAfterRefreshWithEnteredData() {

        LoginPage page = new LoginPage().openPage();

        page
                .setUserName(faker.name().username())
                .setPassword(faker.internet().password());

        Selenide.refresh();

        page
                .verifyStillOnLoginPage();

        assertThat(page.isOpened()).isTrue();
    }

    @Test
    void shouldStayOnLoginPageAfterRefreshWithErrorShown() {

        LoginPage page = new LoginPage().openPage();

        page
                .setUserName(faker.name().username())
                .setPassword(faker.internet().password())
                .clickLogin();

        Selenide.refresh();

        page
                .verifyStillOnLoginPage();

        assertThat(page.isOpened()).isTrue();
    }

    @Test
    void shouldStayOnLoginPageWhenLoginClickedMultipleTimes() {

        LoginPage page = new LoginPage().openPage();

        page
                .setUserName(faker.name().username())
                .setPassword(faker.internet().password())
                .clickLogin()
                .clickLogin()
                .clickLogin()
                .verifyStillOnLoginPage();

        assertThat(page.isOpened()).isTrue();
    }

    @Test
    void shouldStayOnLoginPageWhenUsernameContainsOnlySpaces() {

        LoginPage page = new LoginPage().openPage();

        page
                .setUserName("  ")
                .setPassword(faker.internet().password())
                .clickLogin()
                .verifyStillOnLoginPage();

        assertThat(page.isOpened()).isTrue();
    }

    @Test
    void shouldStayOnLoginPageWhenPasswordContainsOnlySpaces() {

        LoginPage page = new LoginPage().openPage();

        page
                .setUserName(faker.name().username())
                .setPassword("   ")
                .clickLogin()
                .verifyStillOnLoginPage();

        assertThat(page.isOpened()).isTrue();
    }

    @Test
    void shouldSubmitFormWhenEnterKeyPressed() {

        LoginPage page = new LoginPage().openPage();

        page
                .setUserName(faker.name().username())
                .setPassword(faker.internet().password());

        Selenide.$("#password").pressEnter();

        page
                .verifyStillOnLoginPage();

        assertThat(page.isOpened()).isTrue();
    }

    @Test
    void shouldStayOnLoginPageWhenPageReopenedAfterError() {

        LoginPage page = new LoginPage().openPage();

        page
                .setUserName(faker.name().username())
                .setPassword(faker.internet().password())
                .clickLogin();

        page
                .openPage()
                .verifyStillOnLoginPage();

        assertThat(page.isOpened()).isTrue();
    }
}
