package com.example.androidtest;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidtest.Entites.EmployeeEntity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEmployee extends AppCompatActivity {

    EditText edFirstName, edLastName, edEmail, edPass;
    Spinner spRole, spSalary, spGender, spUserType;
    String fNameStr, lNameStr, roleStr, salaryStr, genderStr, emailStr, passStr, userTypeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emp);
        edFirstName = findViewById(R.id.empFirstName);
        edLastName = findViewById(R.id.empLastName);
        edPass = findViewById(R.id.pass);
        edEmail = findViewById(R.id.email);
        spUserType = findViewById(R.id.userType_spinner);
        spSalary = findViewById(R.id.salary_spinner);
        spGender = findViewById(R.id.gender_spinner);
        spRole = findViewById(R.id.role_spinner);
    }

    public void Add(View view) {

        fNameStr = edFirstName.getText().toString();
        lNameStr = edLastName.getText().toString();
        roleStr = spRole.getSelectedItem().toString();
        salaryStr = spSalary.getSelectedItem().toString();
        genderStr = spGender.getSelectedItem().toString();
        emailStr = edEmail.getText().toString();
        passStr = edPass.getText().toString();
        userTypeStr = spUserType.getSelectedItem().toString();

        if (fNameStr.matches("") || lNameStr.matches("") || userTypeStr.matches("Select the user type") ||
                roleStr.matches("Select the role") || salaryStr.matches("salary") ||
                genderStr.matches("Select the gender") || emailStr.matches("") || passStr.matches("")) {

            Toast.makeText(AddEmployee.this, "Please Enter all details", Toast.LENGTH_LONG).show();

        } else {
            RetrofitClient retrofitClient = RetrofitClient.getInstance();

            EmployeeEntity entity = new EmployeeEntity();
            entity.setFirstName(fNameStr);
            entity.setLastName(lNameStr);
            entity.setEmail(emailStr);
            entity.setRole(roleStr);
            entity.setPass(passStr);
            entity.setStatus(userTypeStr);
            entity.setSalary(Integer.parseInt(salaryStr));
            entity.setGender(genderStr);

//            initially let password of new user be its phone number
//            Hashing yet to be done


            retrofitClient.getMyApi().createEmployee(entity).enqueue(new Callback<EmployeeEntity>() {
                @Override
                public void onResponse(Call<EmployeeEntity> call, Response<EmployeeEntity> response) {
                    if (response.code() == 200) {
                        Toast.makeText(AddEmployee.this, "Employee Added", Toast.LENGTH_SHORT).show();

                        edFirstName.setText("");
                        edLastName.setText("");
                        spRole.setSelection(0);
                        spSalary.setSelection(0);
                        spGender.setSelection(0);
                        edEmail.setText("");
                        edPass.setText("");
                        spUserType.setSelection(0);

                    } else {
                        Toast.makeText(AddEmployee.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<EmployeeEntity> call, Throwable t) {
                    Toast.makeText(AddEmployee.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
