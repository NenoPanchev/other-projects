package com.example.jsouplogin.init;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.Map;

@Component
public class AppInit implements CommandLineRunner {
    // # Constants used in this example
    final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36";
    final String LOGIN_FORM_URL = "https://github.com/login";
    final String LOGIN_ACTION_URL = "https://github.com/session";
    final String USERNAME = System.getenv("USERNAME");
    final String PASSWORD = System.getenv("PASSWORD");

    @Override
    public void run(String... args) throws Exception {
        // # Go to login page and grab cookies sent by server
            Connection.Response loginForm = Jsoup.connect(LOGIN_FORM_URL)
                .method(Connection.Method.GET)
                .userAgent(USER_AGENT)
                .execute();


        Document loginDoc = loginForm.parse(); // this is the document containing response html
        HashMap<String, String> cookies = new HashMap<>(loginForm.cookies()); // save the cookies to be passed on to next request

// # Prepare login credentials
        String authToken = loginDoc.select("form:nth-child(1) > input:nth-child(1)")
                .first()
                .attr("value");


        HashMap<String, String> formData = new HashMap<>();
        formData.put("commit", "Sign in");
        formData.put("utf8", "e2 9c 93");
        formData.put("login", USERNAME);
        formData.put("password", PASSWORD);
        formData.put("authenticity_token", authToken);

// # Now send the form for login
        Connection.Response homePage = Jsoup.connect(LOGIN_ACTION_URL)
                .cookies(cookies)
                .data(formData)
                .method(Connection.Method.POST)
                .userAgent(USER_AGENT)
                .execute();



        System.out.println(homePage.parse().html());
    }
}