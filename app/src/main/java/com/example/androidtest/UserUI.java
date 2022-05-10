package com.example.androidtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtest.Adapters.TasksAdapter;
import com.example.androidtest.Models.PageResponse;
import com.example.androidtest.Models.TaskModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserUI extends AppCompatActivity implements ItemClickListener {
    List<TaskModel> tasks;
    TasksAdapter adapter;
    RecyclerView recyclerView;
    String empId;
    TextView noTaskTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ui);
        recyclerView = findViewById(R.id.recyclerView);
        noTaskTV = findViewById(R.id.no_task_textView);
        noTaskTV.setVisibility(View.INVISIBLE);
        tasks = new ArrayList<>();
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TasksAdapter(tasks);
        adapter.notifyDataSetChanged();
        empId = String.valueOf(getIntent().getExtras().getInt("empId"));
        markAttendance();
        initApi();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setClickListener(this);

    }

    private void initApi() {
        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        try {
            retrofitClient.getMyApi().getTaskModelsByEmpId(empId).enqueue(new Callback<PageResponse<TaskModel>>() {
                @Override
                public void onResponse(Call<PageResponse<TaskModel>> call, Response<PageResponse<TaskModel>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            tasks.addAll(response.body().items);
                            adapter.notifyDataSetChanged();
                            if (tasks.size() == 0) {
                                noTaskTV.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<PageResponse<TaskModel>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view, int position) {
        final TaskModel model = tasks.get(position);
        Intent intent = new Intent(getApplicationContext(), TaskDetailActivity.class);
        intent.putExtra("empId", model.getEmpId());
        intent.putExtra("taskId", model.getTaskId());
        intent.putExtra("status", model.getStatus());
        intent.putExtra("title", model.getTitle());
        intent.putExtra("desc", model.getDescription());
        startActivity(intent);
    }

    public void viewProfile(View view) {
        Intent intent = new Intent(getApplicationContext(), Profile.class);
        intent.putExtra("empId", empId);
        startActivity(intent);
    }

    public void markAttendance() {

        RetrofitClient.getInstance().getMyApi().markAttendance(empId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(getApplicationContext(), response.body(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UserUI.this, MainActivity.class);
        startActivity(intent);
    }
}
