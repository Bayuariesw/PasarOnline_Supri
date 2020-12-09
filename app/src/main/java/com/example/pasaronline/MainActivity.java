package com.example.pasaronline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pasaronline.fragment.HomeFragment;
import com.example.pasaronline.fragment.KeranjangFragment;
import com.example.pasaronline.fragment.PedagangFragment;
import com.example.pasaronline.fragment.ProfileFragment;
import com.example.pasaronline.fragment.TambahFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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


//        tv.setText(FirebaseAuth.getInstance().getUid());

        Button logout = findViewById(R.id.btnLogOut2);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
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