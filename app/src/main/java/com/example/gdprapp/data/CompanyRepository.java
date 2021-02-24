package com.example.gdprapp.data;

import androidx.lifecycle.MutableLiveData;

import com.example.gdprapp.data.model.Company;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CompanyRepository implements Runnable{
    MutableLiveData<List<Company>> callback;
    public CompanyRepository(MutableLiveData<List<Company>> callback){
        this.callback = callback;
    }

    @Override
    public void run() {
        callback.postValue(
                getCompanies()
        );
    }


    private List<Company> getCompanies() {
        try {

            String download = downloadCompanies();

            return parseYmal(download);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    private String downloadCompanies() throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL("https://raw.githubusercontent.com/MajorDaxx/GdprAppResources/main/companie-properties.yml");
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

    private List<Company> parseYmal(String s) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        List<Company> companies = mapper.readValue(s,new TypeReference<List<Company>>(){});
        return companies;
    }
}