package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    Home homeFragment = new Home();
    Profile profileFragment = new Profile();
    scholarship scholarshipFragment = new scholarship();
    Consult consultFragment = new Consult();
    savedScholarship savedScholarship = new savedScholarship();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return true;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                        return true;
                    case R.id.scholarship:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, scholarshipFragment).commit();
                        return true;
                    case R.id.consult:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, consultFragment).commit();
                        return true;
                }

                return false;
            }
        });

    }
}