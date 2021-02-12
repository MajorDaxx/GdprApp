package com.example.gdprapp.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.gdprapp.MainActivity;
import com.example.gdprapp.data.model.LoggedInUser;
import com.example.gdprapp.ui.login.MailLogin;

import java.io.IOException;

import kotlin.NotImplementedError;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 * -----------------------------------------------
 * Use Class to Store User Credetials as Prefereces
 */
public class LoginDataSource {

    private String key_file = "asudjasmdkl";

    public Result<LoggedInUser> login() {
        try {
            // TODO: handle loggedInUser authentication

            Context context = MainActivity.instance;
            SharedPreferences sharedPref = context.getSharedPreferences(key_file, Context.MODE_PRIVATE);

            String email    = sharedPref.getString("email",null);
            String password = sharedPref.getString("email",null);
            if(email ==null | password == null) {
                return new Result.Error(new IOException("No Cred in PrefereceFile"));
            }else{
                LoggedInUser user =
                        new LoggedInUser(
                                email,
                                password);

                return new Result.Success<>(user);
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public Result<LoggedInUser> login(String email, String password) {

        try {
            // TODO: handle loggedInUser authentication

            Context context = MainActivity.instance;
            SharedPreferences sharedPref = context.getSharedPreferences(key_file, Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("email", email);
            editor.putString("password", password);
            editor.apply();

            LoggedInUser user =
                    new LoggedInUser(
                            email,
                            password);

            return new Result.Success<>(user);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        throw new NotImplementedError();
        // TODO: revoke authentication
    }
}