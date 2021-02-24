package com.example.gdprapp.ui.login;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.gdprapp.R;
import com.example.gdprapp.data.model.Company;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Thers just one Expandable Topic and its the Advaved Setting Group
 * TODO expand for custome settings
 */
public class MailSettingsAdapter extends BaseExpandableListAdapter {
    String mainGroup = "Advanced";
    Context con;

   Properties defaultProperties;
    List<String> keys;
    private String provider;


    private int groupLayout = R.layout.loginsettinggrouplayout;;
    private int itemLayout = R.layout.loginsettingitemlayout;




    public MailSettingsAdapter(@NonNull Context context) {
        this.con  = context;

    }

    public void setProperties(Properties p){

        this.defaultProperties = new Properties();
        defaultProperties.putAll(p);
        keys = new LinkedList<>();
        keys.addAll(defaultProperties.stringPropertyNames());

    }

    @Override
    public int getGroupCount() {

        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        System.out.println("Children Count:"+defaultProperties.size());
        return defaultProperties.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mainGroup;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return keys.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        System.out.println("GetGroupView");

        View row;
        if (convertView == null) {
            System.err.println("No View provided BaseExpandable Adapter");
            row = LayoutInflater.from(con).inflate(groupLayout, parent, false);
        } else {
            row = convertView;
        }

        TextView t= (TextView)row.findViewById(R.id.group_txt);
        t.setText((String) getGroup(groupPosition));
        return row;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        System.out.println("GetChildView");

        View row;
        if (convertView == null) {
            System.err.println("No View provided BaseExpandable Adapter");
            row = LayoutInflater.from(con).inflate(itemLayout, parent, false);
        } else {
            row = convertView;
        }


        TextView t= row.findViewById(R.id.properties_txt);
        t.setText((String) getChild(groupPosition,childPosition));

        EditText e = row.findViewById(R.id.properties_editText);
        e.setTag(defaultProperties.get(getChild(groupPosition,childPosition)));
        e.setHint((String)defaultProperties.get(getChild(groupPosition,childPosition)));

        e.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                defaultProperties.put((String) getChild(groupPosition,childPosition)
                        ,e.getText().toString());
            }
        });

        return row;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public Properties getProperties() {
        return defaultProperties;
    }
}
