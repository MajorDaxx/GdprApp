package com.example.gdprapp.data;

import android.util.Log;

import com.example.gdprapp.data.model.LoggedInUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private Map<String,LoggedInUser> users = new HashMap<>();

    private LoggedInUser user = null;
    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
    private LoggedInUser getLoggedInUser() {
        return user;
    }

    public Result<LoggedInUser> login() {
        // handle login
        if(user !=null){
            return new Result.Success<LoggedInUser>(user);
        }

        Result<LoggedInUser> result = dataSource.load();

        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }

    public void login(String email, String password, Properties properties) {
        // handle login
        LoggedInUser l = new LoggedInUser(email,password,properties);

        Log.d(LoginRepository.class.getName(),"store User");
        dataSource.store(l); //Store if validation is sucessfull
        users.put(l.getEmail(),l); //store User in local cach anyway
        user = l;
    }

    public boolean hasMails(){
        return users.size() > 0;
    }

    public Set<String> getEmails() {
        return users.keySet();
    }
}