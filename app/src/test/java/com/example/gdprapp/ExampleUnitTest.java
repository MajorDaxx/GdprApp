package com.example.gdprapp;

import com.example.gdprapp.data.model.Company;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private String getData(){
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream("C:\\Users\\l_kar\\AndroidStudioProjects\\GdprApp\\app\\sampledata\\companies.yaml"), StandardCharsets.UTF_8));) {

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void parseyaml() throws JsonProcessingException {
        String s = getData();

        System.out.println(s);


        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        List<Company> companies = mapper.readValue(s,new TypeReference<List<Company>>(){});


    }
}