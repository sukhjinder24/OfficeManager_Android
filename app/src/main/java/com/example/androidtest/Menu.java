package com.example.androidtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.androidtest.Fragments.AddEmpFragment;
import com.example.androidtest.Fragments.AddTaskFragment;
import com.example.androidtest.Fragments.AllEmpFragment;
import com.example.androidtest.Fragments.AllTaskFragment;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Menu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    String empId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        empId = String.valueOf(getIntent().getExtras().getInt("empId"));


        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);


        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AllEmpFragment()).commit();
            navigationView.setCheckedItem(R.id.allEmpItem);
        }
        markAttendance();
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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.allEmpItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AllEmpFragment()).commit();
                break;
            case R.id.allTasksItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AllTaskFragment()).commit();
                break;
            case R.id.addEmpsItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddEmpFragment()).commit();
                break;
            case R.id.addTasksItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddTaskFragment()).commit();
                break;
            case R.id.remove:
                Intent i = new Intent(this, Remove.class);
//                i.putExtra("empId",Integer.valueOf(empId));
                startActivity(i);
                break;
            case R.id.user_view:
                Intent intent = new Intent(this, UserUI.class);
                intent.putExtra("empId", Integer.valueOf(empId));
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
