package com.example.gdprapp.ui.companylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gdprapp.R;
import com.example.gdprapp.data.model.Company;

import java.util.Arrays;
import java.util.List;

public class CompanyAdapter extends ArrayAdapter {

    private int resource;
    private List<Company> companies;

    public CompanyAdapter(@NonNull Context context,@NonNull List<Company> companies) {
        super(context, R.layout.email_item,R.id.firstLine,companies);
        this.resource  = R.layout.email_item;
        this.companies = companies;
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

        Company item = (Company) getItem(position);

        TextView tv1 = (TextView) row.findViewById(R.id.firstLine);
        tv1.setText(item.getName());

        TextView tv2 = (TextView) row.findViewById(R.id.secondLine);
        tv2.setText(item.getEmail());

        Button btn = (Button) row.findViewById(R.id.send_mail_company_btn);
        btn.setTag(item);

        return row;
    }
}
