package com.example.androidtest.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtest.Adapters.TasksAdapter;
import com.example.androidtest.ItemClickListener;
import com.example.androidtest.Models.PageResponse;
import com.example.androidtest.Models.TaskModel;
import com.example.androidtest.R;
import com.example.androidtest.RetrofitClient;
import com.example.androidtest.TaskDetailActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllTaskFragment extends Fragment implements ItemClickListener {
    List<TaskModel> tasks;
    TasksAdapter adapter;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_task, container, false);
        recyclerView = view.findViewById(R.id.recycler_tasks_frag);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        tasks = new ArrayList<>();
        adapter = new TasksAdapter(tasks);

        recyclerView.setAdapter(adapter);
        initApi();
        adapter.setClickListener(this::onClick);


        return view;
    }

    private void initApi() {
        Activity activity = getActivity();
        if (activity != null && isAdded()) {
            RetrofitClient retrofitClient = RetrofitClient.getInstance();
            retrofitClient.getMyApi().getTaskModels().enqueue(new Callback<PageResponse<TaskModel>>() {
                @Override
                public void onResponse(Call<PageResponse<TaskModel>> call, Response<PageResponse<TaskModel>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            tasks.addAll(response.body().items);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Connection Failed", Toast.LENGTH_SHORT);
                    }
                }

                @Override
                public void onFailure(Call<PageResponse<TaskModel>> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    @Override
    public void onClick(View view, int position) {
        final TaskModel model = tasks.get(position);
        Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
        intent.putExtra("empId", model.getEmpId());
        intent.putExtra("taskId", model.getTaskId());
        intent.putExtra("status", model.getStatus());
        intent.putExtra("title", model.getTitle());
        intent.putExtra("desc", model.getDescription());
        startActivity(intent);
    }
}

