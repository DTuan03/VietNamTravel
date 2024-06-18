package com.httt1.vietnamtravel.main.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.httt1.vietnamtravel.R;
import com.httt1.vietnamtravel.home.view.HomeFragment;
import com.httt1.vietnamtravel.main.presenter.MainContract;
import com.httt1.vietnamtravel.main.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    Fragment fragmentHome = new HomeFragment();
    private BottomNavigationView bottomNavigationView;
    private MainContract.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.fragment_home_status_bar));
        Fragment homefragment = new HomeFragment();
        loadFragment(homefragment);
        presenter = new MainPresenter(this, this);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return presenter.onClickItem(menuItem);
            }
        });
    }

    private void init(){
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.activity_main_bnv);
    }

    @Override
    public void loadFragment(Fragment fragment, int setStatusBarColor) {
        loadFragment(fragment);
        getWindow().setStatusBarColor(setStatusBarColor);
    }

    private void loadFragment(Fragment fragment){
        getIntentData(fragment);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_fragment_container, fragment);
        transaction.addToBackStack(null); // theem fragment vao ngan xep backstack de khi an quay lai no se quay lai
        transaction.commit();
    }
    private void getIntentData(Fragment fragment){
        //nhan du lieu
        Intent intent = getIntent();
        if(intent != null){
            String valueUserId = intent.getStringExtra("UserId");
            //truyen du lieu den fragment
            Bundle bundle = new Bundle();
            bundle.putString("UserId", valueUserId);
            fragment.setArguments(bundle);
        }
    }
}