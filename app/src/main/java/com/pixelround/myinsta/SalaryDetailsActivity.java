package com.pixelround.myinsta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.gson.Gson;

import java.text.DecimalFormat;

public class SalaryDetailsActivity extends AppCompatActivity {

    private RecyclerView searchResultsView;

    private CollectionReference mCompaniesDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_details);

        String salaryJson = getIntent().getExtras().getString("salary");
        Salary salary = new Gson().fromJson(salaryJson, Salary.class);

        TextView departmentName = (TextView) findViewById(R.id.detail_company_name);
        departmentName.setText(salary.getJobType());

        DecimalFormat formatter = new DecimalFormat("#,###.00");

        TextView salaryWomen = (TextView) findViewById(R.id.details_salary_range_women);
        salaryWomen.setText(String.format("$%s to $%s",formatter.format(salary.getWomenSalaryL()), formatter.format(salary.getWomenSalaryH())));

        TextView salaryMen = (TextView) findViewById(R.id.details_salary_range_men);
        salaryMen.setText(String.format("$%s to $%s",formatter.format(salary.getMenSalaryL()), formatter.format(salary.getMenSalaryH())));

        setTitle(salary.getName());
    }
}
