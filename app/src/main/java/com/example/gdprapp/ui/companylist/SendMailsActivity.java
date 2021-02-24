package com.example.gdprapp.ui.companylist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import com.example.gdprapp.R;
import com.example.gdprapp.data.CompanyRepository;
import com.example.gdprapp.data.LoginDataSource;
import com.example.gdprapp.data.LoginRepository;
import com.example.gdprapp.data.MailClient;
import com.example.gdprapp.data.Result;
import com.example.gdprapp.data.model.Company;
import com.example.gdprapp.data.model.LoggedInUser;
import com.example.gdprapp.ui.detailmail.MailBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;

import javax.mail.MessagingException;

public class SendMailsActivity extends AppCompatActivity {

    private Context con;
    private MutableLiveData<List<Company>> text = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mails);
        findViewById(R.id.fab).setEnabled(false);

        con = this; //Das glaube ich noch nicht so ganz richtig

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        text.observe(this, new Observer<List<Company>>() {
            @Override
            public void onChanged(@Nullable List<Company> companies) {
                ListView tbl = findViewById(R.id.companies_list);
                if (companies ==null ){

                    TextView tx = new TextView(con);
                    tx.setText("Loading...");

                    //TODO not working
                    tbl.addView(tx);
                }else {

                    tbl.setAdapter(new CompanyAdapter(con,companies));
                    System.err.println("Was here");
                }

            }
        });
        //irgentwas mit Services implementieren?!?!
        //Network operation on main Thread are fobidden

        //Load Company List
        new Thread(new CompanyRepository(text)).start();

    }


    public void sendEmail(View view) throws MessagingException {

        Company c = (Company) view.getTag();

        Result<LoggedInUser> result = LoginRepository
                .getInstance(new LoginDataSource())
                .login();



        if(result instanceof Result.Success){



            LoggedInUser l = ((Result.Success<LoggedInUser>) result).getData();
            MailClient mc = l.getMailClient();
            System.out.println("Until here");

            Intent intent = new Intent(this, MailBuilder.class);

            intent.putExtra("Company", c);
            intent.putExtra("LoggedInUser", l);

            startActivity(intent);

            //mc.sendMessage(c.getEmail(),"Test Email","Eine Nachricht an de Empfänger");
        } else{
            System.out.println("Error");
        }


    }


}