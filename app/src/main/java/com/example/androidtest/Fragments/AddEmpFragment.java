package com.example.androidtest.Fragments;


import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.androidtest.Entites.EmployeeEntity;
import com.example.androidtest.PassHashing;
import com.example.androidtest.R;
import com.example.androidtest.RetrofitClient;

import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEmpFragment extends Fragment {
    EditText edFirstName, edLastName, edEmail, edPass;
    Spinner spRole, spSalary, spGender, spUserType;
    String fNameStr, lNameStr, roleStr, salaryStr, genderStr, emailStr, passStr, userTypeStr;
    Button btn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_emp, container, false);

        edFirstName = view.findViewById(R.id.empFirstNameFrag);
        edLastName = view.findViewById(R.id.empLastNameFrag);
        edPass = view.findViewById(R.id.passFrag);
        edEmail = view.findViewById(R.id.emailFrag);
        spUserType = view.findViewById(R.id.userType_spinnerFrag);
        spSalary = view.findViewById(R.id.salary_spinnerFrag);
        spGender = view.findViewById(R.id.gender_spinnerFrag);
        spRole = view.findViewById(R.id.role_spinnerFrag);

        String[] userType = {"Select the user type", "Admin", "User"};

        ArrayAdapter<String> adapterUT = new ArrayAdapter<String>(getActivity(), R.layout.spinner_list, userType);
        adapterUT.setDropDownViewResource(R.layout.spinner_list);
        spUserType.setAdapter(adapterUT);

        String[] roles = {"Select the role", "Senior Software Engineer", "Project Manager", "Junior Software Engineer", "IOS Developer", "Android Developer", "Dot Net Developer", "Front End Developer", "Business Analyst", "Data Analyst", "Accountant", "HR Manager"};
        ArrayAdapter<String> adapterRole = new ArrayAdapter<String>(getActivity(), R.layout.spinner_list, roles);
        adapterRole.setDropDownViewResource(R.layout.spinner_list);
        spRole.setAdapter(adapterRole);

        String[] salary = {"Select the Salary", "18000", "20000", "22000", "23000", "25000", "26000", "24000", "27000", "28000", "29000", "30000", "35000", "40000", "45000", "50000", "60000", "70000", "80000"};

        ArrayAdapter<String> adapterSalary = new ArrayAdapter<String>(getActivity(), R.layout.spinner_list, salary);
        adapterSalary.setDropDownViewResource(R.layout.spinner_list);
        spSalary.setAdapter(adapterSalary);

        String[] gender = {"Select the Gender", "Male", "Female", "Other"};
        ArrayAdapter<String> adapterGender = new ArrayAdapter<String>(getActivity(), R.layout.spinner_list, gender);
        adapterGender.setDropDownViewResource(R.layout.spinner_list);
        spGender.setAdapter(adapterGender);

        btn = view.findViewById(R.id.btnFrag);

        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                try {
                    AddFrag(view);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void AddFrag(View view) throws NoSuchAlgorithmException {

        fNameStr = edFirstName.getText().toString();
        lNameStr = edLastName.getText().toString();
        roleStr = spRole.getSelectedItem().toString();
        salaryStr = spSalary.getSelectedItem().toString();
        genderStr = spGender.getSelectedItem().toString();
        emailStr = edEmail.getText().toString();
        passStr = edPass.getText().toString();
        userTypeStr = spUserType.getSelectedItem().toString();

        if (fNameStr.matches("") || lNameStr.matches("") || userTypeStr.matches("Select the user type") ||
                roleStr.matches("Select the role") || salaryStr.matches("Select the Salary") ||
                genderStr.matches("Select the Gender") || emailStr.matches("") || passStr.matches("")) {

            Toast.makeText(getActivity(), "Please Enter all details", Toast.LENGTH_LONG).show();

        } else {
            RetrofitClient retrofitClient = RetrofitClient.getInstance();

            EmployeeEntity entity = new EmployeeEntity();
            entity.setFirstName(fNameStr);
            entity.setLastName(lNameStr);
            entity.setEmail(emailStr);
            entity.setRole(roleStr);
            passStr = PassHashing.hash(passStr);
            entity.setPass(passStr);
            entity.setStatus(userTypeStr);
            entity.setSalary(Integer.parseInt(salaryStr));
            entity.setGender(genderStr);


            retrofitClient.getMyApi().createEmployee(entity).enqueue(new Callback<EmployeeEntity>() {
                @Override
                public void onResponse(Call<EmployeeEntity> call, Response<EmployeeEntity> response) {
                    if (response.code() == 200) {
                        Toast.makeText(getContext(), "Employee Added", Toast.LENGTH_SHORT).show();

                        edFirstName.setText("");
                        edLastName.setText("");
                        spRole.setSelection(0);
                        spSalary.setSelection(0);
                        spGender.setSelection(0);
                        edEmail.setText("");
                        edPass.setText("");
                        spUserType.setSelection(0);

                    } else {
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<EmployeeEntity> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
