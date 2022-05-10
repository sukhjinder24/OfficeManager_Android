package com.example.androidtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtest.Adapters.EmployeesAdapter;
import com.example.androidtest.Models.EmployeeModel;
import com.example.androidtest.Models.PageResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeListActivity extends AppCompatActivity implements ItemClickListener {

    List<EmployeeModel> employees;
    EmployeesAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        recyclerView = findViewById(R.id.recyclerView);
        employees = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EmployeesAdapter(employees);
        initApi();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setClickListener(this);
    }

    private void initApi() {
        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        retrofitClient.getMyApi().getEmployeeModels().enqueue(new Callback<PageResponse<EmployeeModel>>() {
            @Override
            public void onResponse(Call<PageResponse<EmployeeModel>> call, Response<PageResponse<EmployeeModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        employees.addAll(response.body().items);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<PageResponse<EmployeeModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view, int position) {
        final EmployeeModel model = employees.get(position);
        Intent intent = new Intent(EmployeeListActivity.this, EmployeeDetailActivity.class);
        intent.putExtra("empId", model.getEmpId());
        intent.putExtra("empName", model.getFirstName() + " " + model.getLastName());
        intent.putExtra("role", model.getRole());
        intent.putExtra("email", model.getEmail());
        intent.putExtra("status", model.getStatus());
        intent.putExtra("salary", model.getSalary());
        intent.putExtra("gender", model.getGender());
        intent.putExtra("image", model.getImageURL());
        startActivity(intent);
    }
}