package com.example.pasaronline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.pasaronline.read.HomeFragment;
import com.example.pasaronline.read.PedagangFragment;
import com.example.pasaronline.read.ProfileFragment;
import com.example.pasaronline.create.TambahFragment;
import com.example.pasaronline.read.TransaksiFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainPedagang extends AppCompatActivity {
    //buat main activity pedagang
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

        String extraProfile = getIntent().getStringExtra("profile");
        if (extraProfile != null && extraProfile.equals("keProfile")){
            getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout,new ProfileFragment()).commit();
            btmNav.setSelectedItemId(R.id.profil);
        }
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
                case R.id.transaksi:
                    frag = new TransaksiFragment();
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