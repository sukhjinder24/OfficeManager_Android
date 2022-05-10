package com.example.androidtest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Remove extends AppCompatActivity {
    EditText idED;
//    String empId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);
        idED = findViewById(R.id.ed_ids);
//        empId = String.valueOf(getIntent().getExtras().getInt("empId"));
//        markAttendance();
    }

//    public void addEmployee(View view) {
//        Intent intent = new Intent(AdminUI.this, AddEmployee.class);
//        startActivity(intent);
//    }
//
//    public void assignTask(View view) {
//        Intent intent = new Intent(AdminUI.this, AssignTask.class);
//        startActivity(intent);
//    }
//
//    public void taskList(View view) {
//        Intent intent = new Intent(AdminUI.this, TaskListActivity.class);
//        startActivity(intent);
//    }

    public void removeTask(View view) {
        if (idED.getText().toString().matches("")) {
            Toast.makeText(Remove.this, "Enter the taskId to be deleted.", Toast.LENGTH_SHORT).show();
            return;
        }
        String taskId = idED.getText().toString();
        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        retrofitClient.getMyApi().deleteTask(taskId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(Remove.this, response.body(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void removeEmployee(View view) {
        if (idED.getText().toString().matches("")) {
            Toast.makeText(Remove.this, "Enter the employeeId to be deleted.", Toast.LENGTH_SHORT).show();
            return;
        }
        String empId = idED.getText().toString();
        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        retrofitClient.getMyApi().deleteEmployee(empId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200 && response.body().matches("Employee Deleted Successfully")) {
                    Toast.makeText(getApplicationContext(), "Employee Deleted Successfully", Toast.LENGTH_LONG).show();
                    idED.getText().clear();
                } else if (response.body().startsWith("Cannot delete or update a parent row: a foreign key constraint fails ")) {
                    Toast.makeText(getApplicationContext(), "Failed to delete due to Pending Tasks for respective user", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), response.body(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("OUT", t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

//    public void empList(View view) {
//        Intent intent = new Intent(AdminUI.this, EmployeeListActivity.class);
//        startActivity(intent);
//    }

//    public void markAttendance() {
//        RetrofitClient.getInstance().getMyApi().markAttendance(empId).enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                Toast.makeText(getApplicationContext(), response.body(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
