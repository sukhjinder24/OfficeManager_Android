package com.example.androidtest;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidtest.Entites.EmployeeEntity;

import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText usernameED, passwordED;
    String usernameStr, passwordStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameED = findViewById(R.id.usernameED);
        passwordED = findViewById(R.id.passwordED);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void signInAdmin(View view) throws NoSuchAlgorithmException {

        usernameStr = usernameED.getText().toString();
        passwordStr = passwordED.getText().toString();

        if (validate()) {
            passwordStr = PassHashing.hash(passwordStr);
            doLoginAdmin(usernameStr, passwordStr);
        } else {
            Toast.makeText(getApplicationContext(), "Both fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void signInUser(View view) throws NoSuchAlgorithmException {

        usernameStr = usernameED.getText().toString();
        passwordStr = passwordED.getText().toString();

        if (validate()) {
            passwordStr = PassHashing.hash(passwordStr);
            doLoginUser(usernameStr, passwordStr);
        } else {
            Toast.makeText(getApplicationContext(), "Both fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    private void doLoginAdmin(final String usernameStr, final String passwordStr) {

        RetrofitClient.getInstance().getMyApi().login(usernameStr).enqueue(new Callback<EmployeeEntity>() {
            @Override
            public void onResponse(Call<EmployeeEntity> call, Response<EmployeeEntity> response) {
                if (response.body() != null) {
                    if (response.body().pass.matches(passwordStr)) {
                        if (response.body().status.toUpperCase().matches(("Admin").toUpperCase())) {
                            Intent intent = new Intent(getApplicationContext(), Menu.class);
                            intent.putExtra("empId", response.body().empId);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "You are not Admin", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Username", Toast.LENGTH_SHORT).show();
                }
                usernameED.setText("");
                passwordED.setText("");
            }

            @Override
            public void onFailure(Call<EmployeeEntity> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void doLoginUser(final String usernameStr, final String passwordStr) {

        RetrofitClient.getInstance().getMyApi().login(usernameStr).enqueue(new Callback<EmployeeEntity>() {
            @Override
            public void onResponse(Call<EmployeeEntity> call, Response<EmployeeEntity> response) {
                if (response.body() != null) {
                    if (response.body().pass.matches(passwordStr)) {
                        Intent intent = new Intent(getApplicationContext(), UserUI.class);
                        intent.putExtra("empId", response.body().empId);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Username", Toast.LENGTH_SHORT).show();
                }
                usernameED.setText("");
                passwordED.setText("");
            }

            @Override
            public void onFailure(Call<EmployeeEntity> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Please try later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validate() {
        return !usernameStr.trim().matches("") && !passwordStr.trim().matches("");
    }


    public void uploadDP(View view) {
        Intent intent = new Intent(this, ImageLoader.class);
        startActivity(intent);
    }
}
