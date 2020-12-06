package com.example.pasaronline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pasaronline.fragment.HomeFragment;
import com.example.pasaronline.fragment.PedagangFragment;
import com.example.pasaronline.fragment.TambahFragment;
import com.example.pasaronline.model.Dagangan;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;

public class MainPedagang extends AppCompatActivity {

    private BottomNavigationView btmNav;
    private FrameLayout fLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pedagang);

        btmNav = findViewById(R.id.bottomNav);
        btmNav.setOnNavigationItemSelectedListener(bottomNavMetod);

        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, new HomeFragment()).commit();
        fLayout = findViewById(R.id.containerLayout);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMetod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment frag = null;

            switch (menuItem.getItemId()){
                case R.id.home:
                    frag = new HomeFragment();
                    break;
                case R.id.tambah:
                    frag = new TambahFragment();
                    break;
                case R.id.profil:
                    frag = new PedagangFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, frag).commit();

            return true;
        }
    };
}