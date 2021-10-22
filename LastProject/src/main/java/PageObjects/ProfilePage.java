package PageObjects;
import com.codeborne.selenide.*;
import io.qameta.allure.Step;

import java.io.File;
import static com.codeborne.selenide.Selenide.*;

public class ProfilePage extends BasePage {
    private SelenideElement inputToUploadPick = $x("//input[@type='file']");
    private SelenideElement buttonUpload = $x("//span[contains(text(),'Upload')]");
    private SelenideElement buttonEditInfo = $x("//span[contains(text(),'Edit Info')]");
    private ElementsCollection inputsNameAndLasName = $$x("//form//input");
    private SelenideElement buttonUpdate = $x("//span[contains(text(),' Update ')]");
    private SelenideElement headerEditButton = $x("//h3[@class='ng-star-inserted']");
    private SelenideElement editProfileText = $x("//h3[not(@class='ng-star-inserted')]");

    private SelenideElement buttonEditJob = $x("//span[contains(text(),'Add job')]");
    private ElementsCollection allJobs = $$x("//mat-card");
    private ElementsCollection listComments = $$x("//mat-card-subtitle[contains(text(),\"Comments\")]");
    private ElementsCollection listJobTitles = $$x("//mat-card-title");
    private ElementsCollection buttonsDelete = $$x("//mat-icon[contains(text(),'delete')]");

    @Step("uploadFile")
    public ProfilePage uploadFile() {
        inputToUploadPick.sendKeys(new File("D:\\viber.jpg").getAbsolutePath());
        buttonUpload.shouldBe(Condition.visible).click();
        return this;
    }
    @Step("openEditProfilePage")
    public ProfilePage openEditProfilePage() {
        buttonEditInfo.shouldBe(Condition.visible).click();
        return new ProfilePage();
    }
    @Step("editNameAndLastName")
    public ProfilePage editNameAndLastName(String name, String lastName) {
        setValue(inputsNameAndLasName.get(0), name);
        setValue(inputsNameAndLasName.get(1), lastName);
        buttonUpdate.shouldBe(Condition.enabled).click();
        return new ProfilePage();
    }
    @Step("isProfileChanged")
    public boolean isProfileChanged(String name, String lastName) throws InterruptedException {
        refresh();
        headerEditButton.should(Condition.text(name + " " + lastName));
        editProfileText.should(Condition.text(name + " " + lastName));
        return true;
    }
    @Step("goToAddJobPage")
    public AddJobPage goToAddJobPage() {
        buttonEditJob.click();
        return new AddJobPage();
    }

    @Step("isCreatedJobPresent")
    public boolean isCreatedJobPresent(String title, String description, int price) {
        String priceText = Integer.toString(price);
        allJobs.shouldBe(CollectionCondition.sizeGreaterThan(0));
        SelenideElement addedJob = allJobs.get(0);
        String jobText = addedJob.text();
        return jobText.contains(title)&&jobText.contains(description)&&jobText.contains(priceText);
    }
    @Step("viewAllCommentsForUserJobs")
    public ProfilePage viewAllCommentsForUserJobs() throws InterruptedException {
        allJobs.last().scrollTo();
        System.out.println(listJobTitles.texts());
        System.out.println(listComments.texts());
        return new ProfilePage();
    }
    @Step("delete first Job")
    public boolean deleteAnyJob() throws InterruptedException {
        int countOfJobsBefore = listJobTitles.size();
        buttonsDelete.first().click();
        Selenide.switchTo().alert().accept();
        int countOfJobsAfter = listJobTitles.shouldBe(CollectionCondition.sizeLessThan(countOfJobsBefore)).size();
        return countOfJobsBefore>countOfJobsAfter;
    }

}




