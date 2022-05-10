package com.example.androidtest;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidtest.Entites.EmployeeEntity;

import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class updateProfile extends AppCompatActivity {
    EditText fNameED, lNameED, passED, passED2;
    String empId;
    EmployeeEntity entity;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        Bundle extras = getIntent().getExtras();
        empId = extras.getString("empId");
        fNameED = findViewById(R.id.fname_ed);
        lNameED = findViewById(R.id.lname_ed);
        passED = findViewById(R.id.pass_ed);
        passED2 = findViewById(R.id.pass_ed2);
        btn = findViewById(R.id.update_btn);
        initAPI();
    }

    private void initAPI() {
        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        retrofitClient.getMyApi().getEmployeeByID(empId).enqueue(new Callback<EmployeeEntity>() {
            @Override
            public void onResponse(Call<EmployeeEntity> call, Response<EmployeeEntity> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        entity = (EmployeeEntity) response.body();
                        fNameED.setHint(entity.getFirstName());
                        lNameED.setHint(entity.getLastName());
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<EmployeeEntity> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void update(View view) throws NoSuchAlgorithmException {
        if (fNameED.getText().toString().matches("") && lNameED.getText().toString().matches("") && passED.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "Enter atleast one value to be updated", Toast.LENGTH_SHORT).show();
        } else {
            entity.setFirstName(fNameED.getText().toString().matches("") ? fNameED.getHint().toString() : fNameED.getText().toString());
            entity.setLastName(lNameED.getText().toString().matches("") ? lNameED.getHint().toString() : lNameED.getText().toString());
            if (!passED.getText().toString().matches("")) {
                if (passED.getText().length() < 8) {
                    Toast.makeText(getApplicationContext(), "weak password", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (passED.getText().toString().matches(passED2.getText().toString())) {
                        String pass = passED.getText().toString();
                        String hashed = PassHashing.hash(pass);
                        entity.setPass(hashed);
                    } else {
                        Toast.makeText(getApplicationContext(), "password mismatch", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            RetrofitClient.getInstance().getMyApi().updateEmployee(entity).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_SHORT).show();
                    fNameED.setText("");
                    lNameED.setText("");
                    passED.setText("");
                    passED.setHint("********");
                    fNameED.setHint(entity.getFirstName());
                    lNameED.setHint(entity.getLastName());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
