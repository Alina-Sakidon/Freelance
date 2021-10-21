package PageObjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class AddJobPage extends BasePage {
    private ElementsCollection inputsEditJob = $$x("//form//input");
    private SelenideElement descriptionJobTextarea = $x("//textarea");
    private SelenideElement buttonCreateJob = $x("//span[contains(text(),' Create job ')]");

    @Step("setNewJob")
    public AddJobPage setNewJob(String jobTitle, String description, int jobPrice) {
        setValue(inputsEditJob.get(0), jobTitle);
        setValue(descriptionJobTextarea, description);
        setValue(inputsEditJob.get(1), Integer.toString(jobPrice));
        return this;
    }
    @Step("saveNewJob")
    public ProfilePage saveNewJob() {
        buttonCreateJob.shouldBe(Condition.enabled).click();
        return new ProfilePage();
    }
}


