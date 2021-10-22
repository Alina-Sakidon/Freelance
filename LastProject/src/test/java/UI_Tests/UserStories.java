package UI_Tests;

import API.Api;
import API.ApiException;
import API.entity.UserAuth;
import PageObjects.*;
import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class UserStories extends BaseTest {

    Faker faker = new Faker();
    Random random = new Random();
    UserAuth userAuth = new UserAuth();

    final String username = faker.name().username();
    final String password = "!Devart2019";
    final String jobTitle = faker.job().title();
    final String jobDescription = faker.job().position();
    final int jobPrice = random.nextInt(10000);
    final  String jobComment = faker.company().profession();


    @Test(priority = 1)
    public void registerAndLoginUser() {
        LoginPage loginPage = openUrl().goToRegisterPage()
                .setUsername(username)
                .setName(faker.name().name())
                .setLastName(faker.name().lastName())
                .setPassword(password)
                .setConfirmPass(password)
                .clickRegisterButton();
        Assert.assertTrue(loginPage.isLoginPageDisplayed());

    }
    @Test(priority = 2)
    public void loginUser(){
        MainPage mainPage = openUrl().goToLoginPage()
                .enterLogin(username)
                .enterPassword(password)
                .loginUser();
        Assert.assertTrue(mainPage.isImageToProfilePageDisplayed());

    }
    @Test(priority = 3)
    public void changeProfileData() throws InterruptedException, IOException, ApiException {
        ProfilePage profilePage = openUrl().goToLoginPage()
                .enterLogin("AlinaSak")
                .enterPassword("!Devart2019")
                .loginUser()
                .goToProfilePage()
                .uploadFile();
        userAuth.setUsername("AlinaSak");
        userAuth.setPassword("!Devart2019");
        Api.signInUser(userAuth);
        Assert.assertEquals(Api.getImageProfileName(),"viber.jpg");
                profilePage.openEditProfilePage()
                .editNameAndLastName("Alina","Sakidon");
        Assert.assertTrue(profilePage.isProfileChanged("Alina", "Sakidon"));

    }
    @Test(priority = 4)
    public void addJob() throws InterruptedException {
        ProfilePage profilePage = openUrl().goToLoginPage()
                .enterLogin(username)
                .enterPassword(password)
                .loginUser()
                .goToProfilePage()
                .goToAddJobPage()
                .setNewJob(jobTitle,jobDescription,jobPrice)
                .saveNewJob();
        Assert.assertTrue(profilePage.isCreatedJobPresent(jobTitle,jobDescription,jobPrice));
    }
    @Test(priority = 5)
    public void addComment() throws InterruptedException {
        AddCommentPage addCommentPage = openUrl().goToLoginPage()
                .enterLogin(username)
                .enterPassword(password)
                .loginUser()
                .goToAddCommentPage(jobTitle)
                .inputComment(jobComment)
                .liveComment();
        Assert.assertTrue(addCommentPage.isTextOfCommentPresent(jobComment));
    }
    @Test(priority = 6)
    public void viewJobDetails() throws InterruptedException {
        ProfilePage profilePage = openUrl().goToLoginPage()
                .enterLogin(username)
                .enterPassword(password)
                .loginUser()
                .goToProfilePage()
                .viewAllCommentsForUserJobs();
                Assert.assertTrue(profilePage.deleteAnyJob());

    }
}
