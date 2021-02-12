package com.example.gdprapp.data.model;

import java.util.List;

public class Companies {
    List<Company> companies;

    public Companies(List<Company> companies) {
        this.companies = companies;
    }

    public List<Company> getCompanies() {
        return companies;
    }
}
