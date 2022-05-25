package com.example.pregbe;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.pregbe.Fragment.CanhanFragment;
import com.example.pregbe.Fragment.CongcuFragment;
import com.example.pregbe.Fragment.HomeFragment;
import com.example.pregbe.Fragment.LichFragment;
import com.example.pregbe.Fragment.ThongtinFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.nav_bottom);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();
        //set navbar event clicked
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch ( item.getItemId() ) {
                    case R.id.item1:
                        fragment =new  LichFragment();
                        break;

                    case R.id.item2:
                        fragment = new CongcuFragment();
                        break;

                    case R.id.item3:
                        fragment = new HomeFragment();
                        break;

                    case R.id.item4:
                        fragment = new ThongtinFragment();
                        break;

                    case R.id.item5:
                        fragment = new CanhanFragment();
                        break;
                }
                assert fragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                return true;
            }
        });
        //set home selected
        navigationView.setSelectedItemId(R.id.item3);
    }
}






