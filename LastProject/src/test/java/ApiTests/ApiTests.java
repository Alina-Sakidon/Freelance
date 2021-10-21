package ApiTests;


import API.Api;
import API.ApiException;
import API.entity.Comment;
import API.entity.Job;
import API.entity.UserAuth;
import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

public class ApiTests {
    private UserAuth userAuth;
    Faker faker = new Faker();
    final String username = faker.name().username();

    @BeforeMethod
    public void setup()  {
        userAuth = new UserAuth();
        userAuth.setUsername(username);
        userAuth.setPassword("Alina777!");
        userAuth.setConfirmPassword("Alina777!");
    }

    //auth-controller
    @Test(priority = 1)
    public void signUp() throws IOException, ApiException {
        Api.signUpUser(userAuth);
        Assert.assertEquals(userAuth.getUsername(),username);
    }
    @Test(priority = 2)
    public void signIn() throws IOException, ApiException {
        Api.signInUser(userAuth);
    }

    // user-controller
    @Test(priority = 3)
    public void userControllerTest() throws IOException, ApiException {
        Api.signInUser(userAuth);
        Api.getUser(userAuth);
        Api.updateUserById(userAuth);
        Api.getUserById(userAuth);
    }

    //job-controller
    @Test(priority =4 )
    public void jobControllerTests() throws IOException, ApiException {
        Api.signInUser(userAuth);
        Job job = new Job();
        job.setId(11);
        job.setTitle("API4");
        job.setDescription("create Api tests via idea3");
        job.setPrice(1200);
        job.setUser(userAuth.getName()+" "+ userAuth.getLastname());
        job.setNoOfComments(0);
        Api.createJob(job);

        Api.getJobById(job.getId());
        Job[] listUserJobs = Api.getAllUserJobs();
        Assert.assertTrue(Arrays.stream(listUserJobs).anyMatch( j -> j.getId() == job.getId()));

        Job[] allJobs = Api.getAllJobs();
        Assert.assertTrue(allJobs.length>0);

        Assert.assertTrue(Api.deleteJobById(job.getId()));

    }
    //image-controller
    @Test(priority = 5)
    public void imageControllerTests() throws IOException, ApiException {
        Api.signInUser(userAuth);
        Api.postImage();
        Api.getUser(userAuth);
       Assert.assertTrue(Api.getImageByUserId(userAuth.getId()));
       Assert.assertTrue(Api.getImageProfileName().equals("image.png"));
    }

    //comment-controller
    @Test(priority = 6)
    public void  commentControllerTests() throws IOException, ApiException {
        Api.signInUser(userAuth);
        Job job = new Job();
        job.setId(0);
        job.setTitle("create job for comment2");
        job.setDescription("create Api tests via idea3");
        job.setPrice(200);
        job.setUser(userAuth.getName()+" "+ userAuth.getLastname());
        job.setNoOfComments(0);
        Api.createJob(job);
        Comment comment = new Comment();
        comment.setId(0);
        comment.setMessage("Comment for this job - i'm looking for a good one2");
        comment.setUsername(userAuth.getUsername());
        comment.setCommentDate(LocalDateTime.now().toString());
        Api.createJobComment(comment,job.getId());
        Assert.assertTrue(comment.getMessage().contains("Comment for this job - i'm looking for a good one"));
        Comment[] listJobComments = Api.getAllJobComments(job.getId());
        Assert.assertTrue(Arrays.stream(listJobComments).anyMatch( c -> c.getMessage().equals(comment.getMessage())));
    }


}
