package com.example.gdprapp.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;
import android.util.Patterns;

import com.example.gdprapp.data.LoginRepository;
import com.example.gdprapp.data.Result;
import com.example.gdprapp.data.model.LoggedInUser;
import com.example.gdprapp.R;

import java.util.Properties;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;
    private Properties defaultProperties = new Properties();

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password,Properties p) {
        // can be launched in a separate asynchronous job
        Properties merge = new Properties();
        merge.putAll(defaultProperties);
        merge.putAll(p); // User input overwrites defaults

        //Result<LoggedInUser> result =
        loginRepository.login(username, password,p);

        /**
        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getPassword())));  //?
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
         */
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            setMailDefaults(username);
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(username).matches();

    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null;
    }

    //TODO Genaralisieren of Default providers
    private void setMailDefaults(String email) {
        Log.d("LoginViewModel",email);
        switch (email.split("@")[1]) {
            case "outlook.com":
                defaultProperties.put("mail.smtp.host","smtp-mail.outlook.com");
                defaultProperties.put("mail.smtp.port","587");
                defaultProperties.put("mail.smtp.starttls.enable","true");
                defaultProperties.put("mail.smtp.auth","true");
                break;
            case "gmail.com":
                defaultProperties.put("mail.smtp.host","smtp.gmail.com");
                defaultProperties.put("mail.smtp.port","587");
                defaultProperties.put("mail.smtp.starttls.enable","true");
                defaultProperties.put("mail.smtp.auth","true");
                break;
            default:
                defaultProperties.put("mail.smtp.host","domain.com");
                defaultProperties.put("mail.smtp.port","587");
                defaultProperties.put("mail.smtp.starttls.enable","true");
                defaultProperties.put("mail.smtp.auth","true");

        }
    }

    public Properties getProperties() {
        return defaultProperties;
    }
}