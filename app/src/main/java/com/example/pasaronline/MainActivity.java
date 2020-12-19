package com.example.pasaronline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.pasaronline.read.HomeFragment;
import com.example.pasaronline.read.KeranjangFragment;
import com.example.pasaronline.read.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    //buat main activity pengguna
    private BottomNavigationView btmNav2;
    private FrameLayout fLayout2;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btmNav2 = findViewById(R.id.btmNavUser);
        btmNav2.setOnNavigationItemSelectedListener(btmNavMethod);

        getSupportFragmentManager().beginTransaction().replace(R.id.containerUser, new HomeFragment()).commit();
        fLayout2 = findViewById(R.id.containerUser);

        String extra = getIntent().getStringExtra("keranjang");
        if (extra != null && extra.equals("keKeranjang")){
            getSupportFragmentManager().beginTransaction().replace(R.id.containerUser, new KeranjangFragment()).commit();
            btmNav2.setSelectedItemId(R.id.keranjang);
        }

        String extraProfile = getIntent().getStringExtra("profile");
        if (extraProfile != null && extraProfile.equals("keProfile")){
            getSupportFragmentManager().beginTransaction().replace(R.id.containerUser,new ProfileFragment()).commit();
            btmNav2.setSelectedItemId(R.id.profilUser);
        }

    }


    private BottomNavigationView.OnNavigationItemSelectedListener btmNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment frag = null;

            switch (menuItem.getItemId()){
                case R.id.home2:
                    frag = new HomeFragment();
                    break;
                case R.id.keranjang:
                    frag = new KeranjangFragment();
                    break;
                case R.id.profilUser:
                    frag = new ProfileFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.containerUser, frag).commit();

            return true;
        }
    };

}