package PageObjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class LoginPage extends BasePage{
    private SelenideElement inputLogin = $x("//input[not(@type='password')]");
    private SelenideElement inputPassword = $x("//input[@type='password']");
    private SelenideElement buttonLogin = $x("//button");
    private SelenideElement titleLoginPage = $x("//h2[text()='Login']");

    @Step("enterLogin")
    public LoginPage enterLogin(String login){
        inputLogin.shouldBe(Condition.visible);

        setValue(inputLogin,login);
        return this;
    }
    @Step("enterPassword")
    public LoginPage enterPassword(String password){
        inputPassword.shouldBe(Condition.visible);
        setValue(inputPassword,password);
        return this;
    }
    @Step("loginUser")
    public MainPage loginUser(){
        buttonLogin.click();
        return new MainPage();
    }
    @Step("isLoginPageDisplayed")
    public boolean isLoginPageDisplayed(){
        titleLoginPage.shouldBe(Condition.appear);
        return titleLoginPage.isDisplayed();
    }
}
