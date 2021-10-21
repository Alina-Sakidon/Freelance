package PageObjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;


public class HomePage extends BasePage{
    private SelenideElement loginButton = $x("//a[contains(text(),'Log in')]");
    private SelenideElement createAccountButton = $x("//a[contains(text(),'Create account')]");

    @Step("goToRegisterPage")
    public RegisterPage goToRegisterPage(){
        createAccountButton.shouldBe(Condition.visible).click();
        return new RegisterPage();
    }
    @Step("goToLoginPage")
    public LoginPage goToLoginPage(){
        loginButton.shouldBe(Condition.visible).click();
        return new LoginPage();
    }


}
