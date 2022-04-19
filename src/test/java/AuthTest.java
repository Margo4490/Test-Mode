import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import data.DataGenerator;
import data.RegistrationInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static data.DataGenerator.*;

public class AuthTest {

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }

    @Test
    void shouldActiveUser() {
        RegistrationInfo user = DataGenerator.Registration.getRegisteredUser("active");
        sendRequest(user);
        $x("//input[@type='text']").val(user.getLogin());
        $x("//input[@type='password']").val(user.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//div[@id='root']").should(visible);

    }

    @Test
    void shouldBlockedUser() {
        RegistrationInfo user = DataGenerator.Registration.getRegisteredUser("blocked");
        sendRequest(user);
        $x("//input[@type='text']").val(user.getLogin());
        $x("//input[@type='password']").val(user.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//div[@class='notification__content']").should(visible);

    }

    @Test
    void shouldIncorrectPassword() {
        RegistrationInfo user = DataGenerator.Registration.getRegisteredUser("blocked");
        sendRequest(user);
        $x("//input[@type='text']").val(user.getLogin());
        $x("//input[@type='password']").val(getRandomPassword());
        $x("//span[@class='button__text']").click();
        $x("//div[@id='root']").should(visible);

    }

    @Test
    void shouldIncorrectLogin() {
        RegistrationInfo user = DataGenerator.Registration.getRegisteredUser("blocked");
        sendRequest(user);
        $x("//input[@type='text']").val(getRandomLogin());
        $x("//input[@type='password']").val(user.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//div[@id='root']").should(visible);
    }

    @Test
    void shouldNotLogin() {
        RegistrationInfo user = DataGenerator.Registration.getRegisteredUser("blocked");
        sendRequest(user);
        //логин не введен
        $x("//input[@type='password']").val(user.getPassword());
        $x("//span[@class='button__text']").click();
        $("[data-test-id='login'].input_invalid .input__sub").should(Condition.visible);
    }

    @Test
    void shouldNotPassword() {
        RegistrationInfo user = DataGenerator.Registration.getRegisteredUser("blocked");
        sendRequest(user);
        $x("//input[@type='text']").val(user.getLogin());
        //пароль не введен
        $x("//span[@class='button__text']").click();
        $("[data-test-id='password'].input_invalid .input__sub").should(Condition.visible);
    }
}
