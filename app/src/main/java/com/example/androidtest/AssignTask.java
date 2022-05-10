package com.example.androidtest;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidtest.Entites.TaskEntity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignTask extends AppCompatActivity {
    EditText empIdED, taskIdED, titleED, descED, statusED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_task);
        empIdED = findViewById(R.id.employeeId);
        taskIdED = findViewById(R.id.taskId);
        titleED = findViewById(R.id.title);
        descED = findViewById(R.id.description);
        statusED = findViewById(R.id.status);
    }

    public void submitTask(View view) {
        String empID = empIdED.getText().toString();
        String taskID = taskIdED.getText().toString();
        String title = titleED.getText().toString();
        String description = descED.getText().toString();
        String status = statusED.getText().toString();
        if (empID.matches("") || taskID.matches("") || title.matches("") || description.matches("") || status.matches("")) {
            Toast.makeText(AssignTask.this, "All Fields are required", Toast.LENGTH_SHORT);
        } else {
            //Try  SignUp
            TaskEntity entity = new TaskEntity();
            entity.empId = Integer.valueOf(empID);
            entity.taskId = Integer.valueOf(taskID);
            entity.title = title;
            entity.description = description;
            entity.status = status;


            RetrofitClient retrofitClient = RetrofitClient.getInstance();
            retrofitClient.getMyApi().createTask(entity).enqueue(new Callback<TaskEntity>() {
                @Override
                public void onResponse(Call<TaskEntity> call, Response<TaskEntity> response) {
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            Toast.makeText(AssignTask.this, "Task Assigned Successfully", Toast.LENGTH_SHORT).show();
                            descED.getText().clear();
                            empIdED.getText().clear();
                            taskIdED.getText().clear();
                            titleED.getText().clear();
                            statusED.getText().clear();
                        } else {

                        }
                    } else {
                        Toast.makeText(AssignTask.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TaskEntity> call, Throwable t) {
                    Toast.makeText(AssignTask.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
}
