package PageObjects;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;

import static com.codeborne.selenide.Selenide.*;

public class MainPage extends BasePage{
    private SelenideElement imageProfilePage = $x("//button[@mattooltip='Profile']");
    private SelenideElement body = $x("//body");
    private SelenideElement profileButton = $x("//div[@class='mat-menu-content ng-tns-c16-0']//button[1]");
    private ElementsCollection jobsCollection = $$x("//mat-card");

    public ProfilePage goToProfilePage() throws InterruptedException {
        body.hover();
        imageProfilePage.shouldBe(Condition.enabled).click();
        profileButton.hover().click();
        return new ProfilePage();
    }
    @Step("isImageToProfilePageDisplayed")
    public boolean isImageToProfilePageDisplayed(){
        imageProfilePage.shouldBe(Condition.visible);
        return imageProfilePage.isDisplayed();
    }
    @Step("go To Add Comment Page")
    public AddCommentPage goToAddCommentPage(String jobTitle) throws InterruptedException {
        jobsCollection.shouldBe(CollectionCondition.sizeGreaterThan(0));
        SelenideElement job =  jobsCollection.stream().filter(j -> j.text().contains(jobTitle)).findFirst().get();

        Assert.assertNotNull(job);
        job.find(By.tagName("button")).shouldBe(Condition.enabled).click();
        return new AddCommentPage();
    }
}

