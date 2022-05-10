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

import com.example.androidtest.Adapters.EmployeesAdapter;
import com.example.androidtest.EmployeeDetailActivity;
import com.example.androidtest.ItemClickListener;
import com.example.androidtest.Models.EmployeeModel;
import com.example.androidtest.Models.PageResponse;
import com.example.androidtest.R;
import com.example.androidtest.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllEmpFragment extends Fragment implements ItemClickListener {

    List<EmployeeModel> employees;
    EmployeesAdapter adapter;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_emp, container, false);
        recyclerView = view.findViewById(R.id.recyclerView1);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        employees = new ArrayList<>();
        adapter = new EmployeesAdapter(employees);

        recyclerView.setAdapter(adapter);
        initApi();
        adapter.setClickListener(this::onClick);


        return view;
    }

    private void initApi() {
        Activity activity = getActivity();
        if (activity != null && isAdded()) {
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
                        Toast.makeText(getActivity(), "Connection Failed", Toast.LENGTH_SHORT);
                    }
                }

                @Override
                public void onFailure(Call<PageResponse<EmployeeModel>> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    @Override
    public void onClick(View view, int position) {
        final EmployeeModel model = employees.get(position);
        Intent intent = new Intent(getActivity(), EmployeeDetailActivity.class);
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
