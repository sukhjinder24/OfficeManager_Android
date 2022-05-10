package com.example.androidtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeDetailActivity extends AppCompatActivity {
    TextView idTV, nameTV, emailTV, roleTV, statusTV, salaryTV, genderTV;
    String empId;
    ImageView imageView;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);
        flag = false;
        Bundle extras = getIntent().getExtras();
        roleTV = findViewById(R.id.role_tv);
        emailTV = findViewById(R.id.email_tv);
        nameTV = findViewById(R.id.name_tv);
        idTV = findViewById(R.id.id_tv);
        statusTV = findViewById(R.id.status_tv);
        salaryTV = findViewById(R.id.salary_tv);
        genderTV = findViewById(R.id.gender_tv);
        imageView = findViewById(R.id.imageEmpProf);
        empId = null;
        if (getIntent().getExtras() != null) {
            idTV.setText(extras.getString("empId"));
            empId = extras.getString("empId");
            roleTV.setText(extras.getString("role"));
            nameTV.setText(extras.getString("empName"));
            emailTV.setText(extras.getString("email"));
            statusTV.setText(extras.getString("status"));
            salaryTV.setText(extras.getString("salary"));
            Picasso.get().load(extras.getString("image")).resize(70, 70).into(imageView);
            genderTV.setText(extras.getString("gender"));
        }
    }

    public void Remove(View view) {
        if (!flag) {
            Toast.makeText(getApplicationContext(), "click again to remove the user", Toast.LENGTH_LONG).show();
            flag = true;
            return;
        }
        if (empId != null) {
            RetrofitClient retrofitClient = RetrofitClient.getInstance();
            retrofitClient.getMyApi().deleteEmployee(empId).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.code() == 200 && response.body().matches("Employee Deleted Successfully")) {
                        Toast.makeText(getApplicationContext(), "Employee Deleted Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), Menu.class);
                        startActivity(intent);
                    } else if (response.body().startsWith("Cannot delete or update a parent row: a foreign key constraint fails ")) {
                        Toast.makeText(getApplicationContext(), "Failed to delete due to Pending Tasks for respective user", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), response.body(), Toast.LENGTH_LONG).show();
                    }
                    flag = false;
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}