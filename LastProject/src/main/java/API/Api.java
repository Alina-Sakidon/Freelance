package API;

import API.entity.Comment;
import API.entity.Job;
import API.entity.UserAuth;
import com.google.gson.Gson;
import okhttp3.*;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Api {
    private static RequestBody requestBody;
    private static Request request;
    private static OkHttpClient client;
    private static Response response;
    private static Call call;
    private static String baseUrl = "https://freelance.lsrv.in.ua/api";
    private static String token;
    private UserAuth userAuth;
    private static ArrayList<Job> listOfJobs;

    public static void checkStatusCode() throws ApiException {
        int codeStatus = response.code();
        if (codeStatus !=200){
            throw new ApiException(codeStatus,"Status code not 200, code is " + codeStatus);
        }
    }
    public static UserAuth signUpUser(UserAuth userAuth) throws IOException, ApiException {
        Gson gson = new Gson();
        Map myMap = new TreeMap();
        myMap.put("username", userAuth.getUsername());
        myMap.put("password", userAuth.getPassword());
        myMap.put("confirmPassword",userAuth.getConfirmPassword());

        String jsonObject = gson.toJson(myMap);
        System.out.println("Create user with data " + myMap);

        requestBody = RequestBody.create(jsonObject.getBytes(StandardCharsets.UTF_8));

        request = new Request.Builder()
                .header("Content-Type", "Application/json")
                .post(requestBody)
                .url(baseUrl + "/auth/signup")
                .build();

        client = new OkHttpClient();
        call = client.newCall(request);
        response = call.execute();
        checkStatusCode();
        UserAuth newUser = gson.fromJson(response.body().string(), UserAuth.class);
        return newUser;
    }
    public static String signInUser(UserAuth userAuth) throws IOException, ApiException {
        Gson gson = new Gson();
        Map<String, String> myMap = new HashMap<String, String>();
        myMap.put("username", userAuth.getUsername());
        myMap.put("password", userAuth.getPassword());
        String jsonObject = gson.toJson(myMap);
        System.out.println("To post " + myMap);

        requestBody = RequestBody.create(jsonObject.getBytes(StandardCharsets.UTF_8));

        request = new Request.Builder()
                .header("Content-Type", "Application/json")
                .post(requestBody)
                .url(baseUrl + "/auth/signin")
                .build();

        client = new OkHttpClient();
        call = client.newCall(request);
        response = call.execute();
        var responseText = response.body().string();
        checkStatusCode();
        JSONObject jsonObject1 = new JSONObject(responseText);
        token = jsonObject1.get("token").toString();
        return token;
    }

    public static UserAuth getUser(UserAuth userAuth) throws IOException, ApiException {
        Gson gson = new Gson();
        request = new Request.Builder()
                .header("Authorization",token)
                .header("Content-Type", "Application/json")
                .get()
                .url(baseUrl + "/user/")
                .build();

        client = new OkHttpClient();
        call = client.newCall(request);
        response = call.execute();
        checkStatusCode();
        UserAuth newUserAuth = gson.fromJson(response.body().string(),UserAuth.class);
        String jsonObject = gson.toJson(newUserAuth);
        System.out.println("get user info " + jsonObject);
        userAuth.setId(newUserAuth.getId());
        return newUserAuth;

    }


    public static UserAuth updateUserById (UserAuth user) throws IOException, ApiException {
        Gson gson = new Gson();
        Map<String, Object> myMap = new HashMap<String, Object>();
        myMap.put("id",user.getId());
        myMap.put("name","Alina");
        myMap.put("username",user.getUsername());
        myMap.put("lastname","Sakidon");
        String jsonObject = gson.toJson(myMap);
        System.out.println("update user by id " + myMap);
        requestBody = RequestBody.create(jsonObject.getBytes(StandardCharsets.UTF_8));

        request = new Request.Builder()
                .header("Authorization",token)
                .header("Content-Type", "Application/json")
                .post(requestBody)
                .url(baseUrl + "/user/update")
                .build();

        client = new OkHttpClient();
        call = client.newCall(request);
        response = call.execute();
        checkStatusCode();
        UserAuth newUser = gson.fromJson(response.body().string(),UserAuth.class);
        user.setName(newUser.getName());
        user.setLastname(newUser.getLastname());
        return newUser;
    }
    public static UserAuth getUserById(UserAuth userAuth) throws IOException, ApiException {
        Gson gson = new Gson();
        request = new Request.Builder()
                .header("Authorization",token)
                .header("Content-Type", "Application/json")
                .get()
                .url(baseUrl + "/user/"+userAuth.getId())
                .build();

        client = new OkHttpClient();
        call = client.newCall(request);
        response = call.execute();
        checkStatusCode();
        UserAuth newUserAuth = gson.fromJson(response.body().string(),UserAuth.class);
        String jsonObject = gson.toJson(newUserAuth);
        System.out.println("get user by id " +userAuth.getId()+ jsonObject);
        userAuth.setId(newUserAuth.getId());
        return newUserAuth;
    }


//job-controller
    public static Job createJob (Job job) throws IOException, ApiException {
        Gson gson = new Gson();
        String jsonObject = gson.toJson(job);
        System.out.println("create job " + jsonObject);
        requestBody = RequestBody.create(jsonObject.getBytes(StandardCharsets.UTF_8));

        request = new Request.Builder()
                .header("Authorization",token)
                .header("Content-Type", "Application/json")
                .post(requestBody)
                .url(baseUrl + "/job/create")
                .build();

        client = new OkHttpClient();
        call = client.newCall(request);
        response = call.execute();
        checkStatusCode();
        Job newJob = gson.fromJson(response.body().string(),Job.class);
        job.setId(newJob.getId());
        return newJob;
    }

    public static Job getJobById(int idJob) throws IOException, ApiException {
        Gson gson = new Gson();
        request = new Request.Builder()
                .header("Authorization",token)
                .header("Content-Type", "Application/json")
                .get()
                .url(baseUrl + "/job/"+idJob)
                .build();

        client = new OkHttpClient();
        call = client.newCall(request);
        response = call.execute();
        checkStatusCode();
        Job job1 = gson.fromJson(response.body().string(),Job.class);
        String jsonObject = gson.toJson(job1);
        System.out.println("get user info " + jsonObject);
        return job1;
    }


    public static Job[] getAllUserJobs() throws IOException, ApiException {
        Gson gson = new Gson();
        request = new Request.Builder()
                .header("Authorization",token)
                .header("Content-Type", "Application/json")
                .get()
                .url(baseUrl + "/job/user/jobs")
                .build();

        client = new OkHttpClient();
        call = client.newCall(request);
        response = call.execute();
        checkStatusCode();
        Job[] userJobs = gson.fromJson(response.body().string(),Job[].class);

        System.out.println("get all user jobs ");
        Arrays.stream(userJobs).forEach(j -> System.out.println(j));
        return userJobs;
    }

    public static Job[] getAllJobs() throws IOException, ApiException {
        Gson gson = new Gson();
        request = new Request.Builder()
                .header("Authorization", token)
                .header("Content-Type", "Application/json")
                .get()
                .url(baseUrl + "/job/all")
                .build();

        client = new OkHttpClient();
        call = client.newCall(request);
        response = call.execute();
        checkStatusCode();
        Job[] allJobs= gson.fromJson(response.body().string(), Job[].class);
        return allJobs;
    }
    public static boolean  deleteJobById(int idJob) throws ApiException, IOException {
        Gson gson = new Gson();
        requestBody = RequestBody.create(null, new byte[0]);
        request = new Request.Builder()
                .header("Authorization",token)
                .post(requestBody)
                .header("Content-Type", "Application/json")
                .url(baseUrl + "/job/delete/"+idJob)
                .build();

        client = new OkHttpClient();
        call = client.newCall(request);
        response = call.execute();
        checkStatusCode();
        JSONObject jsonObject1 = new JSONObject(response.body().string());
        return jsonObject1.get("message").toString().equals("Job is deleted");
    }

    //image-controller

    public static void postImage() throws ApiException, IOException {
        Gson gson = new Gson();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "image.png",
                        RequestBody.create(MediaType.parse("png"), new File("D://screen and video//image.png")))
                .build();

        request = new Request.Builder()
                .header("Content-Type", "Application/json")
                .header("Authorization",token)
                .post(requestBody)
                .url(baseUrl + "/image/upload")
                .build();

        client = new OkHttpClient();
        call = client.newCall(request);
        response = call.execute();
        checkStatusCode();

    }
    public static boolean getImageByUserId(int userId) throws IOException, ApiException {
        Gson gson = new Gson();
        request = new Request.Builder()
                .header("Authorization", token)
                .header("Content-Type", "Application/json")
                .get()
                .url(baseUrl + "/image/"+userId)
                .build();

        client = new OkHttpClient();
        call = client.newCall(request);
        response = call.execute();
        checkStatusCode();
        JSONObject jsonObject = new JSONObject(response.body().string());
        return jsonObject.get("name").toString().equals("image.png");
    }
    public static String getImageProfileName() throws IOException, ApiException {
        Gson gson = new Gson();
        request = new Request.Builder()
                .header("Authorization", token)
                .header("Content-Type", "Application/json")
                .get()
                .url(baseUrl + "/image/profile")
                .build();

        client = new OkHttpClient();
        call = client.newCall(request);
        response = call.execute();
        checkStatusCode();
        JSONObject jsonObject = new JSONObject(response.body().string());
        return jsonObject.get("name").toString();
    }

    //comment-controller

    public static Comment createJobComment(Comment comment, int jobId) throws IOException, ApiException {
        Gson gson = new Gson();
        String jsonObject = gson.toJson(comment);
        System.out.println("create comment " + jsonObject);
        requestBody = RequestBody.create(jsonObject.getBytes(StandardCharsets.UTF_8));

        request = new Request.Builder()
                .header("Authorization",token)
                .header("Content-Type", "Application/json")
                .post(requestBody)
                .url(baseUrl + "/comment/"+jobId+"/create")
                .build();

        client = new OkHttpClient();
        call = client.newCall(request);
        response = call.execute();
        checkStatusCode();
        Comment createdComment= gson.fromJson(response.body().string(),Comment.class);
        comment.setMessage(createdComment.getMessage());
        comment.setCommentDate(createdComment.getCommentDate());
        return createdComment;
    }
    public static Comment[] getAllJobComments(int idJob) throws IOException, ApiException {
        Gson gson = new Gson();
        request = new Request.Builder()
                .header("Authorization",token)
                .header("Content-Type", "Application/json")
                .get()
                .url(baseUrl + "/comment/"+idJob+"/all")
                .build();

        client = new OkHttpClient();
        call = client.newCall(request);
        response = call.execute();
        checkStatusCode();
        Comment[] jobComments = gson.fromJson(response.body().string(),Comment[].class);

        //Arrays.stream(jobComments).forEach(c -> c.getMessage().contains(comment.getMessage()));
        return jobComments;
    }

}
