package com.example.gdprapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gdprapp.R;
import com.example.gdprapp.ui.companylist.SendMailsActivity;
import com.example.gdprapp.data.LoginDataSource;
import com.example.gdprapp.data.LoginRepository;
import com.example.gdprapp.data.Result;
import com.example.gdprapp.data.model.LoggedInUser;
import com.example.gdprapp.ui.login.MailLogin;

import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    public static volatile MainActivity instance;

    private void debug(){
        Context context = MainActivity.instance;
        SharedPreferences sharedPref = context.getSharedPreferences("asudjasmdkl", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        //debug();

        Result<LoggedInUser> result = LoginRepository
                .getInstance(new LoginDataSource())
                .login();

        //dirty code :D
        if (is_loggedin(result instanceof Result.Success)){

            LoggedInUser l = (LoggedInUser) ((Result.Success) result).getData();
            System.err.println(l.getEmail());
            TextView tx = (TextView) findViewById(R.id.loggedin_txt);
            tx.setText(l.getEmail());
        }



    }

    private boolean is_loggedin(boolean loggedin) {

        Button register_btn = (Button) findViewById(R.id.register_btn);
        if(loggedin){
            register_btn.setText("Reregister");
        }else{
            register_btn.setText("Register");
        }

        Button send_mail = (Button) findViewById(R.id.start_btn);
        send_mail.setEnabled(loggedin);
        return loggedin;

    }

    public void send_mails(View view) {
        Intent intent = new Intent(this, SendMailsActivity.class);
        startActivity(intent);
    }


    public void registerEmail(View view) {
        Intent intent = new Intent(this, MailLogin.class);
        startActivity(intent);
    }


}