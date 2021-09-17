package com.example.bbb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.riversun.okhttp3.OkHttp3CookieHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpHelper implements Serializable {
    private final String serverUrl = "http://192.168.0.101:8000/";
    //private final String serverUrl = "http://bipboopboard2.herokuapp.com/";

    String sessionid = null;
    static final String[] profileInfo = new String[0];
    private static final HttpHelper instance = new HttpHelper();

    public HttpHelper(){
    }

    public static HttpHelper getHttpHelper(){
        return instance;
    }

    enum typeRequest {
        GET,
        POST
    }

    private RequestBody logRequestBody(String name, String password){
        return new FormBody.Builder()
                .add("username", name)
                .add("password", password)
                .build();
    }

    private RequestBody regRequestBody(String name, String password){
        return new FormBody.Builder()
                .add("username", name)
                .add("password1", password)
                .add("password2", password)
                .build();
    }

    private Request makePostRequest(RequestBody formBody, String subURL){
        return new Request.Builder()
                .url(serverUrl + subURL)
                .post(formBody)
                .build();
    }

    private Request makeGetRequest(String subURL){
        return new Request.Builder()
                .url(serverUrl + subURL)
                .get()
                .build();
    }

    boolean HttpRegistration(String name, String password) {
        RequestBody registerBody = regRequestBody(name, password);
        Response response = makeRequest(typeRequest.POST, registerBody, "register/");

        if (response == null)
            return false;
        if (this.sessionid == null) {
            GetCookie(response);
            HttpLogin(name, password);
        }
        if (response.priorResponse() == null)
            return false;
        else return Objects.requireNonNull(response.priorResponse()).code() == 302;
    }

    boolean HttpLogin(String name, String password) {
        RequestBody loginBody = logRequestBody(name, password);
        Response response = makeRequest(typeRequest.POST, loginBody, "login/");

        if (response == null)
            return false;
        if (this.sessionid == null) {
            GetCookie(response);
            HttpLogin(name, password);
        }
        if (response.priorResponse() == null)
            return false;
        else return Objects.requireNonNull(response.priorResponse()).code() == 302;
    }

    public String[]  HttpGetProfileInfo() throws JSONException, IOException {
        Response response = makeRequest(typeRequest.GET, null, "api/profiles/");

        JSONObject obj = new JSONObject(Objects.requireNonNull(response.body()).string());
        String[] profileInfo = {"", "", "", "", "", ""};
        profileInfo[0] = obj.getString("username");
        profileInfo[1] = obj.getString("first_name");
        profileInfo[2] = obj.getString("last_name");
        profileInfo[3] = obj.getString("bio");
        profileInfo[4] = obj.getString("email");
        profileInfo[5] = obj.getString("telegram_id");

        for (int i = 0; i < profileInfo.length; i++) {
            if (profileInfo[i].equals("null") || profileInfo[i] == null)
                profileInfo[i] = "";
        }
        return profileInfo;
    }

    private RequestBody profileRequestBody(String firstName, String lastName, String bio, String email,String telegramID){
        return new FormBody.Builder()
                .add("first_name", firstName)
                .add("last_name", lastName)
                .add("bio", bio)
                .add("email", email)
                .add("telegram_id", telegramID)
                .build();
    }

    public boolean HttpSetProfileInfo(String firstName, String lastName, String bio, String email,String telegramID) {
        RequestBody profileBody = profileRequestBody(firstName, lastName, bio, email, telegramID);
        Response response = makeRequest(typeRequest.POST, profileBody, "profile/edit");

        if (response == null)
            return false;
        if (this.sessionid == null)
            GetCookie(response);

        if (response.priorResponse() == null)
            return false;
        else return Objects.requireNonNull(response.priorResponse()).code() == 302;
    }

    public boolean HttpSetCookie() {
        RequestBody logoutBody = new FormBody.Builder().build();
        Response response = makeRequest(typeRequest.GET, null,"");

        if (response == null)
            return false;
        if (this.sessionid == null) {
            GetCookie(response);
        }
        if (response.priorResponse() == null)
            return false;
        else return Objects.requireNonNull(response.priorResponse()).code() == 302;
    }



    void test(String name, String password){
        RequestBody registerBody = logRequestBody(name, password);
        //Request regRequest = makePostRequest(registerBody, "register/");

        // GetCookie(registerBody, serverUrl + "login/");
        // String str = HttpGetProfileUser("http://192.168.0.101:8000/media/images/totoro_anime.gif");
    }

    void GetCookie(Response response)
    {
        String cookie;
        if (response.priorResponse() != null)
        {
            cookie = response.priorResponse().header("Set-Cookie");
            int lastIndex = -1;
            String comparedString = "sessionid=";
            assert cookie != null;
            lastIndex = cookie.indexOf(comparedString);
            lastIndex += comparedString.length();

            this.sessionid = "";
            while(cookie.charAt(lastIndex) != ';')
            {
                this.sessionid += cookie.charAt(lastIndex);
                lastIndex ++;
            }
        }
    }


    public void HttpLogout(){
        RequestBody logoutBody = new FormBody.Builder().build();
        Response response = makeRequest(typeRequest.POST, logoutBody, "logout/");
    }

    String HttpGetProfileUser(final String sUrl)
    {
        OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();
        cookieHelper.setCookie(sUrl, "sessionid", sessionid);

        //String result = "";
        String[] userInfo;
        OkHttpClient client = new OkHttpClient.Builder().cookieJar(cookieHelper.cookieJar())
                .build();
        Response response = null;
        Request request = new Request.Builder()
                .url(sUrl)
                .get()
                .build();
        try {
            response = client.newCall(request).execute();
            InputStream in = Objects.requireNonNull(response.body()).byteStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result;
            String line = reader.readLine();
            result = new StringBuilder(line);
            while((line = reader.readLine()) != null) {
                result.append(line);
            }
            System.out.println(result);
            Objects.requireNonNull(response.body()).close();
            //result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "result";
        //String[] psUP = ParseProfileUser(result);
        //return psUP;
    }

    public ArrayList<Message> HttpGetMessage() throws IOException, JSONException {
        RequestBody logoutBody = new FormBody.Builder().build();
        Response response = makeRequest(typeRequest.GET, null, "api/messages/");

        if (response.code() == 200){
            ArrayList<Message> messagesList = new ArrayList<>();

            String jsonString = response.body().string();
            JSONArray jsonArray  = new JSONArray(jsonString); // notice that `"posts": [...]`
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject  = (JSONObject) jsonArray.get(i);
                JSONObject jsonUserObject  = new JSONObject(jsonObject.getString("user"));

                Message tempMessage;
                tempMessage = new Message(
                        jsonObject.getInt("id"),
                        jsonObject.getString("content"),
                        jsonObject.getString("thread"),
                        jsonObject.getString("image"),
                        jsonObject.getInt("likes"),
                        jsonUserObject.getInt("id"),
                        jsonUserObject.getString("username")
                );
                messagesList.add(tempMessage);
            }
            return messagesList;
        }
        return null;
    }


    private Response makeRequest(typeRequest type, RequestBody formBody, String subUrl) {
        OkHttpClient client;
        Request request = null;
        Response response = null;

        if (this.sessionid != null){
            OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();
            cookieHelper.setCookie(serverUrl + subUrl, "sessionid", sessionid);
            client = new OkHttpClient.Builder().cookieJar(cookieHelper.cookieJar()).build();
        }
        else
            client = new OkHttpClient.Builder().build();

        if (type.equals(typeRequest.GET))
            request = makeGetRequest(subUrl);
        else
            request = makePostRequest(formBody, subUrl);

        Call call = client.newCall(request);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
