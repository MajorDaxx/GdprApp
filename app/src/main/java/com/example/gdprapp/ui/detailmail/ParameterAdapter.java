package com.example.gdprapp.ui.detailmail;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.gdprapp.R;
import com.example.gdprapp.data.model.MailTemplate;

import java.util.List;

public class ParameterAdapter extends ArrayAdapter {

    private int resource;
    private List<String> parameters;

    private MutableLiveData<MailTemplate> mail_text;

    public ParameterAdapter(@NonNull Context context, @NonNull List<String> parameters, MutableLiveData<MailTemplate> t) {
        super(context, R.layout.fragment_first,R.id.parameter_txt, parameters);
        this.resource  = R.layout.fragment_first;
        this.parameters = parameters;
        this.mail_text = t;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        if (convertView == null) {
            System.err.println("No View provided by ArrayAdapter");
            row = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        } else {
            row = convertView;
        }
        System.out.println("Doing stuff here");

        String item = (String) getItem(position);

        TextView tv1 = (TextView) row.findViewById(R.id.parameter_txt);
        tv1.setText(item);

        EditText ed = row.findViewById(R.id.param_value);
        ed.setTag(item);

        TextWatcher afterTextChangedListener = new TextWatcher() {
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
                mail_text.getValue().setVar(item,s.toString());
            }
        };

        ed.addTextChangedListener(afterTextChangedListener);

        return row;
    }
}
