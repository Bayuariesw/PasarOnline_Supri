package com.example.pasaronline.create;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pasaronline.Login;
import com.example.pasaronline.R;
import com.example.pasaronline.model.Dagangan;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;


public class TambahFragment extends Fragment implements View.OnClickListener {
    //buat tambah dagangan
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button btnChoose;
    private ProgressBar pBar;
    private ImageView imgView;
    private Uri mImageUri;
    private Button btnAdd;
    private EditText etNama, etJumlah, etDeskripsi, etHarga;
    private Dagangan dagangan;
    private DatabaseReference mDatabase;
    private StorageReference mstorageRef;
    private StorageTask mUploadTask;


    public TambahFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tambah, container, false);

        mstorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        etNama = view.findViewById(R.id.etNama);
        etJumlah = view.findViewById(R.id.etJumlah);
        etDeskripsi = view.findViewById(R.id.etDesc);
        etHarga = view.findViewById(R.id.etHarga);
        imgView = view.findViewById(R.id.imgView);
        btnChoose = view.findViewById(R.id.btnChoose);
        pBar = view.findViewById(R.id.progBar);

        btnAdd = view.findViewById(R.id.btnAdd);
        btnChoose.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        dagangan = new Dagangan();

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAdd) {
            saveDagangan();
        }
        if (v.getId() == R.id.btnChoose) {
            if (mUploadTask != null && mUploadTask.isInProgress()) {
                Toast.makeText(getContext(), "Upload Foto", Toast.LENGTH_SHORT).show();
            } else {
                chooseImage();
            }
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();

            imgView.setImageURI(mImageUri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //get name user
//        DocumentReference df = FirebaseFirestore.getInstance().collection("Kios").document(FirebaseAuth.getInstance().getUid());
//
//        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if (documentSnapshot.exists()) {
//                    String nama = documentSnapshot.getString("Name");
//                    tvName.setText(nama);
//                } else {
//                    Toast.makeText(getContext(), "Data Tidak Ada", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });

    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void saveDagangan() {
        String namaDgng = etNama.getText().toString().trim();
        String jumlah = etJumlah.getText().toString().trim();
        String deskripsi = etDeskripsi.getText().toString().trim();
        String harga = etHarga.getText().toString().trim();

        boolean isEmptyFields = false;

        //check isi keculai foto
        if (TextUtils.isEmpty(namaDgng)) {
            isEmptyFields = true;
            etNama.setText("Field tidak boleh kosong");
        }

        if (TextUtils.isEmpty(jumlah)) {
            isEmptyFields = true;
            etJumlah.setText("Field tidak boleh kosong");
        }

        if (TextUtils.isEmpty(deskripsi)) {
            isEmptyFields = true;
            etDeskripsi.setText("Field tidak boleh kosong");
        }

        if (TextUtils.isEmpty(harga)) {
            isEmptyFields = true;
            etHarga.setText("Field tidak boleh kosong");
        }

        if (!isEmptyFields) {
            if (mImageUri != null) {
                final StorageReference fileRefrences = mstorageRef.child(System.currentTimeMillis()
                        + "." + getFileExtension(mImageUri));

                mUploadTask = fileRefrences.putFile(mImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        pBar.setProgress(0);
                                    }
                                }, 500);

                                fileRefrences.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Uri downloadUril = uri;

                                        DatabaseReference dbDagang = mDatabase.child("dagangan");
                                        String namaDgng = etNama.getText().toString().trim();
                                        String jumlah = etJumlah.getText().toString().trim();
                                        String deskripsi = etDeskripsi.getText().toString().trim();
                                        String harga = etHarga.getText().toString().trim();
                                        String id = dbDagang.push().getKey();
                                        dagangan.setId(id);
                                        dagangan.setNamaDagangan(namaDgng);
                                        dagangan.setJumlah(jumlah);
                                        dagangan.setDeskripsi(deskripsi);
                                        dagangan.setHarga(harga);
                                        dagangan.setIdKios(FirebaseAuth.getInstance().getUid());
                                        dagangan.setmImageUri(downloadUril.toString());

                                        //insert data
                                        dbDagang.child(id).setValue(dagangan);

                                        Toast.makeText(getContext(), "Data Berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                                        //sementara
                                        etNama.setText("");
                                        etJumlah.setText("");
                                        etDeskripsi.setText("");
                                        etHarga.setText("");
                                        imgView.setImageURI(null);
                                        //finish();

                                    }

                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Gagal mengirim foto", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                double progres = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                pBar.setProgress((int) progres);
                            }
                        });
            } else {
                Toast.makeText(getContext(), "Masukan foto", Toast.LENGTH_SHORT).show();
            }


        }
    }
}