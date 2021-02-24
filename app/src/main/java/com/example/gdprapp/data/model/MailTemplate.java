package com.example.gdprapp.data.model;

//import org.apache.commons.text.StringSubstitutor;
import com.x5.template.Chunk;
import com.x5.template.Theme;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailTemplate {

    Pattern VAR_PATTERN = Pattern.compile("\\{\\$(.*?)\\}");

    String template;
    Map<String,String> params = new HashMap<>();
    Chunk c;
    Set<String> parameter = new HashSet<>();

    public MailTemplate(String template){
        this.template = template;
        //figgure out variables

        Matcher m = VAR_PATTERN.matcher(template);
        System.out.println("Findings Vars");
        while (m.find()) {
            String s = m.group(1);
            parameter.add(s);
            System.out.println(s);
        }

        Theme theme = new Theme();
        c = theme.makeChunk();
        c.append(template);

    }

    public List<String> getParameter(){
        List<String> res = new LinkedList<>();
        res.addAll(parameter);
        return res;
    }

    public MailTemplate setVar(String key, String value){
        c.set(key,value);
        return this;
    }

    public String renderTemplate(){
        return c.toString();
    }

}
