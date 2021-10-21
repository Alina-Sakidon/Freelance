package PageObjects;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class AddCommentPage extends BasePage{
    private SelenideElement inputCommentText = $x("//textarea");
    private SelenideElement buttonLiveComment = $x("//span[contains(text(),' Leave comment ')]");
    private ElementsCollection jobInfo = $$x("//mat-card");

    @Step("inputComment")
    public AddCommentPage inputComment(String comment){
        setValue(inputCommentText,comment);
        return this;
    }
    @Step("liveComment")
    public AddCommentPage liveComment(){
        int sizeBeforeAdd = jobInfo.size();
        buttonLiveComment.shouldBe(Condition.visible).click();
        jobInfo.shouldBe(CollectionCondition.size(sizeBeforeAdd++));
        return new AddCommentPage();
    }
    @Step("isTextOfCommentPresent")
    public boolean isTextOfCommentPresent(String commentText){
        jobInfo.find(Condition.text(commentText)).shouldBe(Condition.visible);
       return true;
    }
}
