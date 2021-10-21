package PageObjects;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.jetbrains.annotations.NotNull;

public class BasePage {
    @Step("setValue")
    protected void setValue(@NotNull SelenideElement element, String value){
        element.clear();
        element.click();
        element.sendKeys(value);
    }
}
