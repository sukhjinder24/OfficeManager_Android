package com.example.androidtest.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.androidtest.Entites.TaskEntity;
import com.example.androidtest.R;
import com.example.androidtest.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTaskFragment extends Fragment {
    EditText empIdED, titleED, descED, statusED;
    Button btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_task, container, false);

        empIdED = view.findViewById(R.id.employeeIdFrag);
        titleED = view.findViewById(R.id.titleFrag);
        descED = view.findViewById(R.id.descriptionFrag);
        statusED = view.findViewById(R.id.statusFrag);
        btn = view.findViewById(R.id.addTaskFrag);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitTask(view);
            }
        });
        return view;
    }

    public void submitTask(View view) {
        String empID = empIdED.getText().toString();
        String title = titleED.getText().toString();
        String description = descED.getText().toString();
        String status = statusED.getText().toString();
        if (empID.matches("") || title.matches("") || description.matches("") || status.matches("")) {
            Toast.makeText(getActivity(), "All Fields are required", Toast.LENGTH_SHORT).show();
        } else {
            //Try  SignUp
            TaskEntity entity = new TaskEntity();
            try {
                entity.empId = Integer.valueOf(empID);
                entity.taskId = Integer.valueOf(101);
                entity.title = title;
                entity.description = description;
                entity.status = status;
            } catch (Exception ex) {
                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }


            RetrofitClient retrofitClient = RetrofitClient.getInstance();
            retrofitClient.getMyApi().createTask(entity).enqueue(new Callback<TaskEntity>() {
                @Override
                public void onResponse(Call<TaskEntity> call, Response<TaskEntity> response) {
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            Toast.makeText(getActivity(), "Task Assigned Successfully", Toast.LENGTH_SHORT).show();
                            descED.getText().clear();
                            empIdED.getText().clear();
                            titleED.getText().clear();
                            statusED.getText().clear();
                        } else {
                            Toast.makeText(getActivity(), "Invalid Employee ID", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TaskEntity> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
}
