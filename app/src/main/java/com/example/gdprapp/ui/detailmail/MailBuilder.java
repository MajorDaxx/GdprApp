package com.example.gdprapp.ui.detailmail;

import android.content.Context;
import android.os.Bundle;

import com.example.gdprapp.data.MailTemplateRepositorie;
import com.example.gdprapp.data.model.Company;
import com.example.gdprapp.data.model.LoggedInUser;
import com.example.gdprapp.data.model.MailTemplate;
import com.example.gdprapp.ui.companylist.CompanyAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gdprapp.R;

import java.util.LinkedList;
import java.util.List;

import javax.mail.MessagingException;

public class MailBuilder extends AppCompatActivity {

    Company c;
    LoggedInUser l;

    Context con;

    MutableLiveData<MailTemplate> mail_text = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_builder);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        con = this; //Das glaube ich noch nicht so ganz richtig

        c = (Company) getIntent().getSerializableExtra("Company");
        l = (LoggedInUser) getIntent().getSerializableExtra("LoggedInUser");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    sendMail(view);


                //TODO open redered Mail

            }
        });


        mail_text.observe(this, new Observer<MailTemplate>() {
            @Override
            public void onChanged(MailTemplate s) {
                ListView tbl = findViewById(R.id.parameter_view);
                TextView edt = findViewById(R.id.mail_txt);
                System.out.println("Get Template "+s);
                if (s ==null ){
                    System.out.println("Doing this");
                    TextView tx = new TextView(con);
                    tx.setText("Loading...");

                    //TODO not working
                    tbl.addView(tx);
                    edt.setText("Loading ...");
                }else {
                    System.out.println("Doing that");
                    tbl.setAdapter(new ParameterAdapter(con,s.getParameter(),mail_text));
                    edt.setText(s.renderTemplate());
                }
            }
        });
        //TODO do real Time Changes


        new MailTemplateRepositorie().getTemplate(mail_text);

    }

    private void sendMail(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(MailBuilder.class.getName(),"Try to send mail");
                    l.getMailClient().sendMessage(c.getEmail(),"Test",mail_text.getValue().renderTemplate());
                    Snackbar.make(view, "Mail Send Sucessfull", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } catch (MessagingException e) {
                    Snackbar.make(view, "Mail Send Failed!!!!!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    e.printStackTrace();
                }
            }
        }).start();

    }


}