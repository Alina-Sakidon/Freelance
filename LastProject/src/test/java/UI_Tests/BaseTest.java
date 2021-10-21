package UI_Tests;

import PageObjects.HomePage;
import com.codeborne.selenide.Configuration;
import io.qameta.allure.Attachment;
import lombok.SneakyThrows;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.nio.file.Files;

import static com.codeborne.selenide.Selenide.*;

public class BaseTest {
    protected WebDriver driver;

   @BeforeMethod
    public void beforeMethod(){
       closeWebDriver();
       Configuration.startMaximized = true;
    }

    public HomePage openUrl() {
        open("https://freelance.lsrv.in.ua");
        return new HomePage();
    }
    @SneakyThrows
    @AfterMethod
    public void takeScreenshot(ITestResult result) {
        if (!result.isSuccess()){
            File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            saveScreenshot(Files.readAllBytes(screenshot.toPath()));
        }
    }
    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshot(byte[] screenShot) {
        return screenShot;
    }
}
