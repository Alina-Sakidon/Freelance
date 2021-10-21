package PageObjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;


public class RegisterPage extends BasePage{
    private SelenideElement buttonSingIn = $x("//a[contains(text(),'Sign in')]");
    private SelenideElement inputUsername = $x("//div[@class='mat-form-field-infix ng-tns-c25-1']//input");
    private SelenideElement inputName = $x("//div[@class='mat-form-field-infix ng-tns-c25-2']//input");
    private SelenideElement inputLastName = $x("//div[@class='mat-form-field-infix ng-tns-c25-3']//input");
    private SelenideElement inputPassword = $x("//div[@class='mat-form-field-flex ng-tns-c25-4']//input");
    private SelenideElement inputConfirmPass = $x("//div[@class='mat-form-field-flex ng-tns-c25-5']//input");
    private SelenideElement buttonRegister = $x("//span[contains(text(),\" Register \")]");
    private SelenideElement linkSignIn = $x("//a");

    @Step("setUsername")
    public RegisterPage setUsername(String username){
        setValue(inputUsername,username);
        return this;
    }
    @Step("set name")
    public RegisterPage setName(String name){
        inputName.click();
        setValue(inputName,name);
        return this;
    }
    @Step("set Last Name")
    public RegisterPage setLastName(String lastName){
        setValue(inputLastName,lastName);
        return this;
    }
    @Step("set Password")
    public RegisterPage setPassword(String password){
        setValue(inputPassword,password);
        return this;
    }
    @Step("Confirm Pass")
    public RegisterPage setConfirmPass(String password){
        setValue(inputConfirmPass,password);
        return this;
    }
    @Step("click Register Button")
    public LoginPage clickRegisterButton(){
        buttonRegister.shouldBe(Condition.visible).shouldBe(Condition.enabled).click();
        return new LoginPage();
    }
    @Step("goToLoginPage")
    public LoginPage goToLoginPage(){
        linkSignIn.click();
        return new LoginPage();

    }


}
