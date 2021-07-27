package com.example.proiect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.proiect.Fragments.HomeFragment;
import com.example.proiect.Fragments.NotificationFragment;
import com.example.proiect.Fragments.ProfileFragment;
import com.example.proiect.Fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FirstActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Initialize();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home :
                        selectorFragment = new HomeFragment();
                        break;

                    case R.id.nav_search :
                        selectorFragment = new SearchFragment();
                        break;

                    case  R.id.nav_add :
                        selectorFragment =null;
                        startActivity(new Intent(FirstActivity.this, AdaugaImagini.class));
                        break;

                    case R.id.nav_heart :
                        selectorFragment= new NotificationFragment();
                                break;

                    case  R.id.nav_profile :
                        selectorFragment = new ProfileFragment();
                        break;

                }

                    if(selectorFragment != null){
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectorFragment).commit();
                    }
                return true;
            }
        });

        Bundle intent = getIntent().getExtras();

        if(intent!=null){
            String profileId= intent.getString("publisherId");

            getSharedPreferences("PROFILE",MODE_PRIVATE).edit().putString("profileId",profileId).apply();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.nav_profile);
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        }


    }


    public void Initialize(){
        bottomNavigationView=findViewById(R.id.botton_navigation);

    }
}
