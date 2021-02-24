package com.example.gdprapp.data;

import androidx.lifecycle.MutableLiveData;

import com.example.gdprapp.data.model.Company;
import com.example.gdprapp.data.model.MailTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MailTemplateRepositorie {

    /**
     * Just return the default Template
     * @param callback
     */
    public void getTemplate(MutableLiveData<MailTemplate> callback){
        getTemplate(callback,"template-mail.txt");
    }

    public void getTemplate(MutableLiveData<MailTemplate> callback,String name){
        new Thread(new Runnable() {
            @Override
            public void run() {

                callback.postValue(
                        getTemplate(name)
                );

            }
        }).start();
    }

    private MailTemplate getTemplate(String template_name){
        try {
            System.out.println("Get Template "+template_name);
            String download = downloadTemplate(template_name);

            return new MailTemplate(download);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    private String downloadTemplate(String template_name) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL("https://raw.githubusercontent.com/MajorDaxx/GdprAppResources/main/"+template_name);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line).append("\n");
        }
        rd.close();
        return result.toString();
    }
}
