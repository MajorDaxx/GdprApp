package com.example.gdprapp.data.model;

import com.example.gdprapp.data.MailClient;

import java.io.Serializable;
import java.util.Properties;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser implements Serializable {

    private final String email;
    private final String password;
    private final Properties p;

    public LoggedInUser(String email, String password,Properties p) {
        this.email = email;
        this.password = password;
        this.p = p;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Properties getProperties() {
        return p;
    }

    public MailClient getMailClient(){
        return MailClient.getClient(email,password,p);

    }
}