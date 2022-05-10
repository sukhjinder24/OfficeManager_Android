package com.example.androidtest;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalaryCalculator extends AppCompatActivity {
    TextView cbTV, salaryTV;
    String empId;
    Spinner ySpin, mSpin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_calacilator);
        Bundle extras = getIntent().getExtras();
        empId = extras.getString("empId");
        cbTV = findViewById(R.id.cb_tv);
        salaryTV = findViewById(R.id.salary_tv1);
        ySpin = findViewById(R.id.year_spinner);
        mSpin = findViewById(R.id.month_spinner);

        String[] years = {"Select the year", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004", "2003", "2002", "2001", "2000"};

        ArrayAdapter<String> yAdapter = new ArrayAdapter<String>(this, R.layout.spinner_list, years);
        yAdapter.setDropDownViewResource(R.layout.spinner_list);
        ySpin.setAdapter(yAdapter);

        String[] months = {"Select the month", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, R.layout.spinner_list, months);
        mAdapter.setDropDownViewResource(R.layout.spinner_list);
        mSpin.setAdapter(mAdapter);

        salaryCalculation();
    }

    private void salaryCalculation() {

        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        String m = (cal.get(Calendar.MONTH) + 1) + "";
        m = (m.length() == 2) ? m : ("0" + m);
        String y = (cal.get(Calendar.YEAR)) + "";

        RetrofitClient.getInstance().getMyApi().getSalary(empId, m, y).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    cbTV.setText(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getSalary(View view) {

        String m = mSpin.getSelectedItem().toString();
        String y = ySpin.getSelectedItem().toString();
        if (m.matches("Select the month") || y.matches("Select the year")) {
            Toast.makeText(getApplicationContext(), "Invalid Period", Toast.LENGTH_SHORT).show();
        } else {
            RetrofitClient.getInstance().getMyApi().getSalary(empId, m, y).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.code() == 200) {
                        salaryTV.setText(response.body());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
