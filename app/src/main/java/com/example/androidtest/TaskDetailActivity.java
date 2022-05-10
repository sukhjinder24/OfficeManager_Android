package com.example.androidtest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TaskDetailActivity extends AppCompatActivity {

    TextView taskIdTV, titleTV, empIdTV, descTV, statusTV;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        Bundle extras = getIntent().getExtras();
        taskIdTV = findViewById(R.id.task_id_tv);
        titleTV = findViewById(R.id.title_tv);
        empIdTV = findViewById(R.id.emp_id_tv);
        descTV = findViewById(R.id.desc_tv);
        statusTV = findViewById(R.id.status_tv);
        if (getIntent().getExtras() != null) {
            taskIdTV.setText(extras.getString("taskId"));
            titleTV.setText(extras.getString("title"));
            empIdTV.setText(extras.getString("empId"));
            descTV.setText(extras.getString("desc"));
            statusTV.setText(extras.getString("status"));
        }
    }
}