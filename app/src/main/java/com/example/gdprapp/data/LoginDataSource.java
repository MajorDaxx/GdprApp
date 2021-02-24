package com.example.gdprapp.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.gdprapp.ui.main.MainActivity;
import com.example.gdprapp.data.model.LoggedInUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

import javax.mail.MessagingException;

import kotlin.NotImplementedError;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 * -----------------------------------------------
 * Use Class to Store User Credetials as Prefereces
 */

/**
 * Rework to store multiple emails with properties and credetials
 */
public class LoginDataSource {

    private String key_file = "asudjasmdkl";

    public Result<LoggedInUser> load() {
        try {
            // TODO: handle loggedInUser authentication

            Context context = MainActivity.instance;
            SharedPreferences sharedPref = context.getSharedPreferences(key_file, Context.MODE_PRIVATE);

            String email    = sharedPref.getString("email",null);
            String password = sharedPref.getString("password",null);
            String properties = sharedPref.getString("properties",null);
            Log.d(LoginDataSource.class.getName(),properties);
            Properties p = new Properties();
            StringReader sr = new StringReader(properties);
            p.load(sr);

            if(email ==null | password == null) {
                Log.d(LoginDataSource.class.getName(),"No Cred in PrefereceFile");
                return new Result.Error(new IOException("No Cred in PrefereceFile"));
            }else{
                LoggedInUser user =
                        new LoggedInUser(
                                email,
                                password,p);

                return new Result.Success<>(user);
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void store(LoggedInUser l) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    //l.getMailClient().sendMessage(l.getEmail(), "Sucsesfull register Email", "Congrats you register sucessfully this email for your GDPR App");

                    Log.i(LoginDataSource.class.getName(),"Store Logged in User");
                    Context context = MainActivity.instance;
                    SharedPreferences sharedPref = context.getSharedPreferences(key_file, Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("email", l.getEmail());
                    editor.putString("password", l.getPassword());

                    StringWriter sw = new StringWriter();
                    l.getProperties().store(sw,"Mail Properties");
                    editor.putString("properties", sw.toString());

                    editor.apply();
                }catch ( IOException ex){
                    Log.e(LoginDataSource.class.getName(),"Sending Test mail failed");
                    ex.printStackTrace();
                }
            }
        }).start();
    }


    public void logout() {
        throw new NotImplementedError();
        // TODO: revoke authentication
    }
}