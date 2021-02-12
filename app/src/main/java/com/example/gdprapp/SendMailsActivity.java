package com.example.gdprapp;

import android.content.Context;
import android.os.Bundle;


import com.example.gdprapp.data.model.Companies;
import com.example.gdprapp.data.model.Company;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;

import kotlin.Result;

public class SendMailsActivity extends AppCompatActivity {


    private MutableLiveData<Companies> text = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mails);
        findViewById(R.id.fab).setEnabled(false);
        Context con = this;

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


        text.observe(this, new Observer<Companies>() {
            @Override
            public void onChanged(@Nullable Companies companies) {
                TableLayout tbl = findViewById(R.id.mail_table);
                TableRow tr = new TableRow(con);

                for(Company company:companies.getCompanies()){
                    TextView tx = new TextView(con);
                    tx.setText(company.toString());
                    tr.addView(tx);
                    tbl.addView(tr);
                }

            }
        });
        //irgentwas mit Services implementieren?!?!
        //Network operation on main Thread are fobidden
        new Thread(new Runnable() {
            @Override
            public void run() {
                text.postValue(
                        getCompanies()
                );
            }
        }).start();


    }


    private Companies getCompanies() {
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL("https://raw.githubusercontent.com/MajorDaxx/GdprAppResources/main/companie-properties.yml");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            Companies companies = mapper.readValue(result.toString(), Companies.class);
            return companies;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


}