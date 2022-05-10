package com.example.androidtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class TaskListActivity extends AppCompatActivity implements ItemClickListener {

    List<TaskModel> tasks;
    TasksAdapter adapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        recyclerView = findViewById(R.id.recyclerView);
        tasks = new ArrayList<>();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TasksAdapter(tasks);

        adapter.notifyDataSetChanged();
        initApi();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setClickListener(this);
    }

    private void initApi() {
        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        retrofitClient.getMyApi().getTaskModels().enqueue(new Callback<PageResponse<TaskModel>>() {
            @Override
            public void onResponse(Call<PageResponse<TaskModel>> call, Response<PageResponse<TaskModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        tasks.addAll(response.body().items);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<PageResponse<TaskModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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
}