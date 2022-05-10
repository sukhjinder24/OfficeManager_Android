package com.example.androidtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidtest.Entites.EmployeeEntity;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {

    String empId;
    EmployeeEntity entity;
    TextView emailTV, fNameTV, lNameTV;
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Bundle extras = getIntent().getExtras();
        empId = extras.getString("empId");
        emailTV = findViewById(R.id.email_tv);
        fNameTV = findViewById(R.id.f_name_tv);
        lNameTV = findViewById(R.id.l_name_tv);
        imageView = findViewById(R.id.imageviewUsrProf);
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
                        fNameTV.setText(entity.getFirstName());
                        lNameTV.setText(entity.getLastName());
                        emailTV.setText(entity.getEmail());
                        Picasso.get().load(entity.getImageURL()).into(imageView);
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

    public void updateProfile(View view) {

        Intent intent = new Intent(getApplicationContext(), updateProfile.class);
        intent.putExtra("empId", empId);
        startActivity(intent);

    }

    public void CheckSalary(View view) {
        Intent intent = new Intent(getApplicationContext(), SalaryCalculator.class);
        intent.putExtra("empId", empId);
        startActivity(intent);
    }

//    public void uploadDP(View view) {
//
//        Intent intent=new Intent(this,ImageGalleryDemoActivity.class);
//        startActivity(intent);
//
////        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), 3);
////        onActivityResult();
//    }
}
